# ActiveMQ의 Virtual Destinations를 활용한 메세지 로드밸런싱
>AmazonMQ(ActiveMQ)의 Virtual Destinations를 사용하여 메시지 중복처리 문제를 해결한 경험에 대한 글이다. 

회사의 서비스 간 컨텐츠 동기화를 위해 사용하던 `AWS SQS`를 `AmazonMQ`로 마이그레이션하면서,  
AmazonMQ(ActiveMQ)의 Topic을 이용한 Pub-Sub 방식의 메시지 브로커를 구성했다.

하지만 모든 Subscriber에 같은 메시지가 전달되기 때문에 메시지 중복처리 문제가 발생했는데,  
이 문제를 ActiveMQ의 **`Virtual Topics`** 를 활용하여 해결한 과정을 정리한다.

## 목차
>1. Queue 방식과 Topic 방식 개념 이해 
>2. 기존 상황 
>3. 변경된 상황
>4. AmazonMQ(ActiveMQ) 마이그레이션
>5. 문제 발생
>6. 해결 방안 1 - Batch 서버를 1대만 사용
>7. 해결 방안 2 - UUID 사용
>8. 해결 방안 3 - Virtual Destinations 활용
>9. Virtual Topics 개념 이해
>10. Virtual Topics 적용 결과 
>11. Virtual Topics 사용 방법 (with Spring Boot)
>12. 맺으며

</br>

## 1. Queue 방식과 Topic 방식 개념 이해
시작하기에 앞서, 앞으로 계속 언급될 Queue 방식과 Topic 방식 메시지 브로킹에 대해 알아본다.

### 1-1. Queue
ActiveMQ의 Queue는 Producer가 보낸 메시지를 1개의 Consumer가 받는다.  
메시지는 Queue에 쌓이고, Consumer는 Queue에서 하나씩 소비한다.  

>1:1 메시지 브로킹

![](https://github.com/Integerous/TIL/blob/master/ETC/images/activemq/queue.png?raw=true)

### 1-2. Topic
ActiveMQ의 Topic은 Producer(Publisher)가 메시지를 발행(publish)하고,  
N개의 Consumer(Subscriber)가 동일한 메시지를 구독(subscribe) 한다.  

>1:N 메시지 브로킹 (Pub-Sub)

![](https://github.com/Integerous/TIL/blob/master/ETC/images/activemq/topics.png?raw=true)

~~~
Topic 방식은 모든 Consumer가 동일한 메시지를 받는다.
~~~

</br>

## 2. 기존 상황
줌인터넷의 여러 서비스 중 [뉴스줌](http://news.zum.com/), [허브줌](http://hub.zum.com/), [TV줌](https://tv.zum.com/)과 관련된 상황이다.  
뉴스줌은 각 언론사로부터 뉴스기사를 송고받아 자체적으로 파싱하여 사용하는데,  
허브줌에서도 파싱된 뉴스기사 중 일부가 필요했다.  

즉, 뉴스줌 - 허브줌 간 컨텐츠 동기화가 필요했고,  
아래와 같이 AWS SQS를 활용해서 뉴스줌-허브줌 간 컨텐츠 동기화를 자동화한 상태였다.  

![](https://github.com/Integerous/TIL/blob/master/ETC/images/activemq/news_hub.png?raw=true)

위의 도식에서 AWS SQS를 사용한 부분만 간단히 표현하면 아래와 같다.

![](https://github.com/Integerous/TIL/blob/master/ETC/images/activemq/aws_sqs.png?raw=true)

</br>

## 3. 변경된 상황
그런데 TV줌에서도 뉴스줌의 컨텐츠가 필요해졌고,  
이제 뉴스줌은 아래와 같이 허브줌과 TV줌 모두에게 파싱된 뉴스 데이터를 제공해야 했다.  

![](https://github.com/Integerous/TIL/blob/master/ETC/images/activemq/aws_sqs_tv.png?raw=true)

</br>

## 4. AmazonMQ(ActiveMQ) 마이그레이션
물론 위의 그림처럼 AWS SQS 큐를 하나 더 생성해서 운영할 수도 있지만,  
뉴스 데이터를 필요로 하는 서비스가 추가될 때 마다 Queue를 생성하고 관리해야 하는 문제가 있었다.  

그래서 **뉴스줌에서는 메시지를 한 번만 발행하고,  
N개의 서비스에서 각자 메시지를 받아갈 수 있는 Pub-Sub 방식의 구성이 필요**했다.  

그것이 앞서 설명한 Topic 방식 메시지 브로킹이며,  
Topic 방식을 사용하기 위해 AmazonMQ(ActiveMQ)로 마이그레이션을 진행했다.  

![](https://github.com/Integerous/TIL/blob/master/ETC/images/activemq/pubsub_activemq.png?raw=true)

AmazonMQ만 Pub-Sub 방식을 지원하는 것은 아니다!  
많은 메시지 브로커가 Pub-Sub 방식을 지원하지만, **팀 내부 회의를 통해 AmazonMQ가 선택**되었다.

### *포털개발팀에서 AmazonMQ를 선택한 이유
>- AWS SQS와 SNS를 혼용하는 방법도 있지만, 어플리케이션의 복잡도가 증가하는 문제 
>- 대용량 메시지 처리가 아닌 컨텐츠 업데이트를 위한 메시지이기 때문에 AmazonMQ로 충분
>- 서버 이중화 및 관리가 필요없기 때문에, 점차적으로 Severless를 지향하는 팀의 방향성에 부합함
>- AmazonMQ의 간단한 설정으로 인해, 최대한 비즈니스 로직에 집중할 수 있었음
>- IDC 환경에서 RabbitMQ를 사용하는 것을 고려했으나, 팀에서 적용중인 클라우드 표준화에 맞추어 AmazonMQ를 선택

</br>

## 5. 문제 발생
AmazonMQ의 Topic 방식으로 마이그레이션 한 후,  
허브줌의 Batch 서버를 돌려보니, **`같은 컨텐츠가 2번씩 처리되는 문제가 발생`**했다.
  
ActiveMQ의 Topic 방식은 모든 Subscriber가 같은 메시지를 받기 때문에,  
**`2대로 구성된 허브줌의 Batch 서버가 각각 동일한 메시지를 받아서 처리한 것`**이다.  

기존에 AWS SQS를 사용할 때는 중복처리 문제가 없었다.  
SQS는 Queue 방식이라서 2대의 Batch 서버가 1개의 Queue로부터 메시지를 소비했기 때문이다.
  
물론 메시지 중복처리가 실제 서비스에는 영향을 끼치지 않았지만,  
2대의 서버가 동일한 메시지를 각각 처리하는 리소스 낭비를 방관하는 것은 정신 건강에 해로웠다.  
그래서 어떻게든 이 문제를 해결하고 싶었다.

</br>

## 6. 해결 방안 1 - Batch 서버를 1대만 사용
주니어 개발자의 좁은 시야로는 다양한 해결방법이 떠오르지 않았다.  
가장 먼저 떠오른 방법은 TV줌 처럼 1대의 Batch 서버만 사용하는 것이다.
  
이 경우, 메시지 중복처리 문제는 빠르고 명확하게 해결된다.  
하지만 서버 2대가 작업을 분산처리해오던 환경에서,  
급작스럽게 하나의 서버에 트래픽을 몰빵(?)하는 것은 위험하다고 생각했다.
  
게다가 허브줌의 Batch 서버들은 뉴스컨텐츠 동기화 이외에도 각종 Batch 작업이 돌아가는 서버다.  
그런데 허브줌을 담당한지 얼마 안된 상황이었기 때문에, `서버 스케일 축소(Scale in)가 가져올 부작용은 예상하기 힘들었다.`

그래서 이 방법은 더 이상 고려하지 않았다.

</br>

## 7. 해결 방안 2 - UUID 사용
만약 허브줌의 Batch 서버들이 동일한 메시지를 받을 수 밖에 없다면,  
뉴스줌에서 메시지(뉴스 데이터)에 UUID를 포함시켜서 Publish 해주는 방법을 생각했다.
    
이 경우 Batch 서버들은 동일한 메시지를 받되,  
메시지를 처리하는 시점에 UUID로 검증해서 중복처리를 방지할 수 있다고 생각했다.

하지만 `UUID를 생성하는 순간, 관련 모든 서비스에 영향을 미치는 관리 포인트가 하나 늘어난다.`  
(Publisher와 Subscriber 양쪽 DB에 UUID컬럼 추가, 관련 클래스에 UUID 검증 로직 추가...등등)  

또한 UUID를 사용해서 중복처리를 방어하는 로직은 DB접근을 비약적으로 증가시키기 때문에,  
결국 서버를 1대만 사용하는것 보다도 비효율적일 수 있다.  
그렇다고 DB접근을 줄이고자 Redis로 UUID를 캐싱하는 것은, 또 다른 관리 포인트를 늘리는 일이었다.

결국, 이 방법도 적절한 해결책은 아니었다.

</br>

## 8. 해결 방안 3 - Virtual Destinations 활용
ActiveMQ 외적으로만 해결 방법을 찾다가,  
ActiveMQ 자체에 이 문제를 해결할 수 있는 기능이 있을 수 있다는 생각에 [ActiveMQ 공식 문서](https://activemq.apache.org/features)를 뒤적거렸다.  

그 결과, Destination Features 카테고리에서 [Virtual Destinations](https://activemq.apache.org/virtual-destinations)를 찾았다.  
내용을 천천히 읽어보니, 메시지 중복처리 문제를 해결해 줄 것 같았다.  

곧바로 Virtual Topics의 개념과 사용 방법을 알아보고,  
QA서버와 로컬서버를 각각 Batch 서버1, 2로 생각하고 테스트를 진행했다.  

그 결과, 메시지 중복처리 문제가 말끔하게 해결되었다!  
(상세 내용은 뒤에서 설명)

</br>

## 9. Virtual Topics 개념 이해
드디어 Virtual Topics에 대해 알아본다.  

![](https://github.com/Integerous/TIL/blob/master/ETC/images/activemq/VirtualTopics.png?raw=true)

Virtual Topics은 기존의 Topic 방식과 유사하지만,  
**핵심은 Logical Topic과 Physical Queue를 생성한다는 점**이다.

Publisher는 가상의(Logical) Topic으로 메시지를 발행하고,   
이 가상의 Topic을 Subscriber가 구독하기 시작하면, (즉, Listening을 시작하면)  
ActiveMQ 브로커에 해당 Subscriber만을 위한 물리적인(Physical) Queue가 자동으로 생성된다.
  
그리고 Subscriber는 자동생성된 물리 Queue로부터 메시지를 소비하는 방식이다.  

또한, 각 Consumer는 아래와 같이 Topic에서 직접 메시지를 받을 수도 있고, Queue로부터 받을 수도 있다.  
아래는 Batch 서버가 1대인 TV줌과 2대인 허브줌이 메시지를 받는 방식을 표현한다. 

~~~sh
[뉴스줌] --> [VirtualTopic.news] --> [TV줌 Batch 1]
                               --> [Consumer.hub.VirtualTopic.news] --> [허브줌 Batch 1]
                                                                    --> [허브줌 Batch 2]
~~~ 

</br>

## 10. Virtual Topics 적용 결과 
앞서 설명한 **`Virtual Topics`**를 사용하면 아래 그림과 같이 동작한다.  

![](https://github.com/Integerous/TIL/blob/master/ETC/images/activemq/virtual_topic.png?raw=true)

뉴스줌은 Topic 방식 그대로 메시지를 Publish하고, (`Topic 이름만 변경`)  
Batch 서버가 1대인 TV줌은 Topic 방식 그대로 메시지를 Subscribe 한다. (`Topic 이름만 변경`) 
 
Batch 서버가 2대인 허브줌은 Topic 방식으로 메시지를 Subscribe 하되,  
Logical Topic을 Listening 할 때 자동으로 생성되는 Physical Queue로부터 메시지를 소비한다.

다시 말해, **허브줌의 Batch 서버 2대가 하나의 Queue로부터 메시지를 소비하도록 구성**할 수 있다.

#### 이 방식은 크게 3가지 장점이 있다.
>1. 기존의 Topic방식을 그대로 사용하기 때문에 관리포인트가 증가하지 않는다. **`Topic 이름만 변경`** 
>2. Pub-Sub 방식이기 때문에 Subscriber가 증가해도 Publisher를 수정할 필요가 없다.
>3. Batch 서버가 999개로 늘어나거나, 1개로 줄어들어도 하나의 Queue에서 메시지를 소비하기 때문에 스케일 out/in 에 자유롭다.

</br>

## 11. Virtual Topics 사용 방법 (with Spring Boot)
사용 방법은 너무나 간단하다. **`out-of-the-box`**(꺼내서 바로 쓰는) 기능이기 때문에  
기존의 Topic 방식에서 Topic 이름만 Convention에 맞게 변경하면 된다.
  
예전에는 **`activemq.xml`**에 **`<DestinationInterceptor>`** 설정도 해야  
해당 Topic 이름으로 들어온 메시지를 Interceptor가 가로채서 VirtualTopic으로 처리했지만,  
이제는 이 설정마저 Default가 되었다.
 
### 11-1. Publisher의 경우
기존 Topic 방식으로 Topic을 생성하되, Topic 이름 앞에 **`VirtualTopic.`**을 붙여서  
**`VirtualTopic.{Topic이름}`**으로 생성하면 끝이다.

~~~java
private JmsTemplate jmsTemplate;

// Message 생성
jmsTemplate.convertAndSend(new ActiveMQTopic("VirtualTopic.토픽이름", 생성한 Message)) 
~~~

### 11-2. 단일 서버 Subscriber의 경우
단일 서버의 경우 Topic으로부터 바로 메시지를 받아도 되므로, Topic 방식으로 메시지를 Listening 한다.  
이 때, destination을 Publisher가 생성한 Topic 이름으로 설정하면 끝이다.

~~~java
@JmsListener(destination = "VirtualTopic.{Topic이름}")
public void amazonMqNewsListener(@Payload MessageDto messageDto) {
    
    // 로직 처리
}
~~~

### 11-3. 로드밸런싱이 필요한 Subscriber의 경우
반드시 jms의 `pub-sub-domain` 설정을 `false`로 변경해야 한다.

~~~yml
# application.yml

spring:
  jms:
    pub-sub-domain: false
~~~

그리고 N개의 서버가 메시지를 나누어 받아야 하므로 기존 Topic 방식으로 메시지를 Listening 하되,  
이 때, Publisher가 생성한 Topic 이름 앞에 **`Consumer.{clientId}.`**를 붙여서  
destination을 **`Consumer.{clientId}.VirtualTopic.{Topic이름}`**으로 설정하면 끝이다.  

만약 clientId를 따로 설정하지 않았다면, 해당 부분에 원하는 이름을 넣어도 무관하다.    

~~~java
@JmsListener(destination = "Consumer.{clientId}.VirtualTopic.{Topic이름}")
public void amazonMqNewsListener(@Payload MessageDto messageDto) {

    // 로직 처리
}
~~~

### 11-4. Topic 이름 생성 규칙을 바꾸고 싶은 경우
이 경우, **`activemq.xml`** 파일에 아래 설정을 추가해서, **`name`**과 **`prefix`**를 원하는 대로 바꾸면 된다.

~~~xml
<destinationInterceptors> 
  <virtualDestinationInterceptor> 
    <virtualDestinations> 
      <virtualTopic name=">" prefix="VirtualTopicConsumers.*." selectorAware="false"/>   
    </virtualDestinations>
  </virtualDestinationInterceptor> 
</destinationInterceptors>
~~~

위의 내용이 Default 설정이며, AmazonMQ에서는 해당 부분이 주석처리된 채로 **`activemq.xml`**이 생성된다.

### 11-5. AmazonMQ의 activemq.xml 설정
기존에는 AmazonMQ에서 Virtual Destinations를 사용하려면  
**`activemq.xml`**에 아래에 동그라미 친 부분과 같이 **`useVirtualTopics="true"`**와  
**`<destinationInterceptors>...</>`**부분의 설정을 추가해야 했다.

![](https://github.com/Integerous/TIL/blob/master/ETC/images/activemq/activemq_xml_2.png?raw=true)

그런데, [이 대화](https://forums.aws.amazon.com/thread.jspa?threadID=268432)의 AWS 엔지니어에 의하면 Virtual Destinations가 기본적으로 enabled 된 상태로 activemq.xml이 생성된다고 한다.

>We've changed the way default configurations are created. By default virtual destinations are now enabled (an empty element is no longer present in the default XML configuration).
즉, Topic 명명규칙을 변경할 생각이 없으면, 아무런 설정이 필요 없다.  

그야말로 **`out-of-the-box`** 기능이다.


</br>

## 12. 맺으며
내가 속한 팀에서는 클라우드 환경을 적극적으로 활용중이고, 앞으로 더 다양한 클라우드 서비스를 적용할 예정이다.
  
팀의 모던한(?) 환경 덕분에, 주니어 개발자로서 다양한 경험들을 하고 있는데,  
이번에는 Message Broker의 사용 경험과 지식이 1도 없는 상태에서  
AmazonMQ의 Virtual Destinations를 활용하여 문제를 해결하는 과정을 경험할 수 있어서 재미있고 유익했다 :)  

언젠가 더 크고 복잡한 환경에서 Message Broker를 사용할 일이 생긴다면, 이 경험이 큰 도움이 될 것 같다. 

>혹시나 틀린 내용이 있다면, 꼭 지적해주시길 바랍니다.

</br>

## * Reference
- [ActiveMQ 공식문서 - Virtual Destinations](http://activemq.apache.org/virtual-destinations.html)
- [Virtual Topics in ActiveMQ](https://tuhrig.de/virtual-topics-in-activemq/)
- [What are Virtual Destinations in ActiveMQ and how do they work?](https://access.redhat.com/solutions/250303)
- [Queues vs. Topics vs. Virtual Topics (in ActiveMQ)](https://tuhrig.de/queues-vs-topics-vs-virtual-topics-in-activemq/)


