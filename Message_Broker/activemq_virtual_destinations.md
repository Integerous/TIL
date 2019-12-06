# ActiveMQ의 Virtual Destinations를 활용한 메세지 로드밸런싱
>실무에서 AmazonMQ(ActiveMQ)로 메세지 브로커를 구성하며 알게 된, `Virtual Destinations` 에 대해 정리해본다.  
>정확하게는, Topic을 이용한 Pub-sub 방식의 메세지 브로킹 환경에서, `Virtual Destinations`를 활용한 메세지 로드밸런싱에 대한 내용이다. 


</br>

## 0. Queue, Topic, Virtual Topic 비교
>Virtual Topic을 설명하기 전에 Queue 방식과 Topic 방식을 소개한다.     

### 1. Queue
>ActiveMQ의 Queue는 Producer가 보낸 메세지를 1개의 Consumer가 받는다.  
>1:1 방식

![](https://github.com/Integerous/TIL/blob/master/ETC/images/activemq/queue.png?raw=true)

### 2. Topic
>ActiveMQ의 Topic은 Producer(Publisher)가 메세지를 발행(publish)하고,  
>N개의 Consumer(Subscriber)가 메세지를 구독(subscribe) 한다.  
>1:N Pub-Sub 방식

![](https://github.com/Integerous/TIL/blob/master/ETC/images/activemq/topics.png?raw=true)

~~~
이 때, 모든 Consumer는 같은 메세지를 받는다.
~~~

### 3. Virtual Topic
>Virtual Topic은 Topic 방식과 유사하나, Publisher는 가상의(논리적인) Topic에 메세지를 발행하고,  
>Subscriber가 가상의 Topic을 구독하면, (즉, Listening을 시작하면)  
>ActiveMQ 브로커에 해당 Subscriber를 위한 물리적인 Queue가 자동으로 생성되고,  
>Subscriber는 생성된 물리 Queue에 주입된 메세지를 소비한다.

![](https://github.com/Integerous/TIL/blob/master/ETC/images/activemq/VirtualTopics.png?raw=true)

~~~
이 때, 각 Consumer는 아래와 같이 Topic에서 직접 메세지를 받을 수도 있고, Queue로부터 받을 수도 있다.  

[producer] --> [VirtualTopic.T] --> [consumerA]
                                --> [VirtualQueueConsumer.VirtualTopicT] --> [ConsumerB]
                                                                         --> [ConsumerC]
~~~



</br>

## 1. Virtual Topics을 사용한 이유
- **상황**
  - 메세지를 뿌리는 `뉴스 서비스`와, 메세지를 받아서 처리해야하는 `TV 서비스`, `Hub 서비스`가 있는 상황
    - 기존에는 뉴스 서비스의 메세지를 Hub 서비스만 받았기 때문에 `AWS SQS로 1:1 메세지 브로킹`을 하고 있었다.
    - 그런데 TV 서비스가 추가되면서, 메세지 Listener가 두 곳이 되어서 SQS Queue를 추가로 생성해야 했다.
  - 현재는 Listener가 2개 뿐이지만, 이후에 추가될 수 있기 때문에 1:1로 메세지를 브로킹하는 Queue 방식은 지양하고,
  - `AmazonMQ(ActiveMQ)의 Topic을 사용하여 1:N Pub-Sub 방식의 메세지 브로커를 구성`하였다.
  
  
- **문제 발생**
  - 멀티 모듈 프로젝트로 구성된 Hub 서비스의 경우, Scheduler라는 모듈이 메세지를 받아서 처리한다.
  - 그런데 2개의 Scheduler 서버가 메세지를 나누어 받아서 처리하고 있었고, 문제가 발생했다.
    - 기존에 AWS SQS에서는 각 서버가 하나의 Queue에서 메세지를 꺼내가기 때문에 문제가 되지 않았다.
  - AmazonMQ(ActiveMQ)의 `Topic 방식은 Subscriber 모두에게 같은 메세지가 전달`된다.
  - 때문에 2개의 Scheduler 서버가 각각 같은 메세지를 전달 받아서 처리하므로, `메세지 중복처리 문제가 발생`했다.
  
  
- **해결 방안 1 (1개의 서버만 사용)**
  - TV 서비스 처럼 1개의 서버만 Subscriber로 사용하면 중복처리 문제는 바로 해결된다.
  - 하지만 추후에 서버 스케일 아웃이 필요한 상황이 도래하면, 똑같은 문제에 봉착하므로 임시방편일 뿐이었다.  
  - 또한, 각종 Batch 로직이 돌고있는 Scheduler 서버가 2개로 구성된 것으로 보아, 서버가 뻗을 경우 비즈니스에 데미지가 있다는 것이기 때문에, 2대의 서버를 1대로 줄이는 것은 위험했다.  
  - 게다가 클라우드가 아닌 IDC에 구성된 서버들이기 때문에 스케일 변경에 더 많은 작업들이 필요했고, 현재 리소스가 부족한 IDC 서버 담당 팀에 부담이 되는 상황이었다.
  - 그래서 패스.
  
  
- **해결 방안 2 (UUID 활용)**
  - 뉴스 서비스에서 메세지(뉴스 데이터)를 Publish 하기 전에 UUID를 포함시켜주면, Subscriber들은 UUID를 활용해서 중복처리를 방지할 수 있다.
  - 하지만 UUID를 생성하는 순간, 관련 모든 서비스에 영향을 미치는 관리 포인트가 하나 늘어난다. (Publisher와 Subscriber 양쪽 DB에 UUID컬럼 추가, 관련 클래스에 UUID 로직 추가 등등)
  - 또한, UUID를 사용해서 중복처리를 방어하는 로직은 DB접근을 비약적으로 증가시키기 때문에, 결국 서버를 1대만 사용하는것 보다도 비효율적이다.
  - 그렇다고 DB접근을 줄이고자 Redis로 UUID를 캐싱하는 것은 또 관리 포인트를 늘리는 일이었다.
  - 그래서 패스.
  
  
- **해결 방안 3 (Virtual Destinations 사용)**
  - 팀원분에게 얼핏 듣기로는, RabbitMQ는 그룹을 나누어 메세지를 전달하도록 설정할 수 있어서, 같은 그룹에서는 중복처리를 방지할 수 있다고 한다. (확인 안해봄)
  - ActiveMQ도 그런 기능이 있을거라는 생각에 [공식 문서](https://activemq.apache.org/features)를 뒤적거리다가, Destination Features 카테고리에서 [Virtual Destinations](https://activemq.apache.org/virtual-destinations)를 찾았다.
  - 결론적으로 Virtual Topic을 사용하면,
    - `뉴스 (Publisher)는 Topic 방식 그대로 메세지를 publish하고, (topic 이름만 변경)`
    - `단일 서버인 TV (Subscirber)는 Topic 방식 그대로 메세지를 subscribe하고, (topic 이름만 변경)`
    - `N개의 서버인 Hub (Subscriber)는 Topic 방식으로 메세지를 subscribe하되, 논리적 topic을 Listening할 때 자동으로 생성되는 물리적 Queue로 부터 메세지를 subscribe 한다.`
  - **이 방법을 해결책으로 선택 !**
  
  
- **결과**
  - 2대의 Scheduler 서버가 하나의 Queue에서 메세지를 꺼내가서 소비하므로 중복처리 문제가 해결되었다.
  - 기존의 Topic방식을 그대로 사용하기 때문에 관리포인트가 증가하지 않았다. (topic 이름만 바꾸면 끝)
  - Pub-Sub 방식을 사용하기 때문에 Subscriber가 증가해도 Publisher를 수정할 필요가 없었다.
  - Scheduler의 서버가 999개로 늘어도 하나의 Queue에서 메세지를 가져가기 때문에 스케일 아웃시에도 중복처리 문제가 발생하지 않는다.
  
</br>

## 2. Virtual Topics 사용 방법 (with Spring Boot)
>사용 방법은 너무나 간단하다. `out-of-the-box`(꺼내서 바로 쓰는) 기능이기 때문에  
>기존의 Topic 방식에서 Topic 명만 Convention에 맞게 변경하면 된다.  
>Topic 방식으로 ActiveMQ를 사용하는 내용은 [AmazonMQ + Spring Boot](https://github.com/Integerous/TIL/blob/master/Spring/AmazonMQ%2BSpringBoot.md)를 참고하면 된다.
  
>예전에는 `activemq.xml`에 `<DestinationInterceptor>` 설정도 해야만,  
>해당 Topic이름으로 들어온 메세지를 Interceptor가 가로채서 VirtualTopic으로 처리했지만,  
>이제는 일반적인 상황에서는 이 마저도 필요 없다. 

  
- **Produer(Publisher)**
  - 기존 Topic 방식으로 Topic을 생성하되, Topic이름 앞에 `VirtualTopic.`을 붙여서  
  `VirtualTopic.{Topic이름}`으로 생성하면 끝이다.
  
    ~~~java
    private JmsTemplate jmsTemplate;

    // Message 생성

    jmsTemplate.convertAndSend(new ActiveMQTopic("VirtualTopic.토픽이름", 생성한 Message), 
    ~~~

- **단일 서버 Consumer(Subscriber)**
  - Topic에서 바로 메세지를 받아도 되므로, 기존 Topic 방식으로 메세지를 Listening 한다.  
  - 이 때, destination을 Producer가 생성한 Topic이름을 설정하면 끝이다.
  
    ~~~java
    @JmsListener(destination = "VirtualTopic.{Topic이름}")
    public void amazonMqNewsListener(@Payload MessageDto messageDto) {
        // 로직 처리
    }
    ~~~

- **로드밸런싱이 필요한 Consumer(Subscriber)**
  - 반드시 jms의 `pub-sub-domain` 설정을 `false`로 변경해야 한다.

    ~~~yml
    # application.yml

    spring:
      jms:
        pub-sub-domain: false
    ~~~
  - N개의 서버가 메세지를 나누어 받아야 하므로, 기존 Topic 방식으로 메세지를 Listening 하되,
  - 이 때, destination을 Producer가 생성한 Topic이름 앞에 `Consumer.{clientId}.`를 붙여서  
  `Consumer.{clientId}.VirtualTopic.{Topic이름}`으로 설정하면 끝이다.
    ~~~java
    @JmsListener(destination = "Consumer.{clientId}.VirtualTopic.{Topic이름}")
    public void amazonMqNewsListener(@Payload MessageDto messageDto) {
        // 로직 처리
    }
    ~~~
  - 만약 clientId를 따로 설정하지 않았다면, 해당 부분에 원하는 이름을 넣어도 무관하다.

- **Topic 이름 생성 규칙을 바꾸고 싶은 경우**
  - 이 경우, `activemq.xml` 파일에 아래 설정을 추가해서, `name`과 `prefix`를 원하는 대로 바꾸면 된다.
  
    ~~~xml
    <destinationInterceptors> 
      <virtualDestinationInterceptor> 
        <virtualDestinations> 
          <virtualTopic name=">" prefix="VirtualTopicConsumers.*." selectorAware="false"/>   
        </virtualDestinations>
      </virtualDestinationInterceptor> 
    </destinationInterceptors>
    ~~~
  - 위의 내용 중 `virtualTopic name=">"`의 `>`는 모든 설정을 Virtual Topic으로 받겠다는 설정이다.
  - 위의 내용이 Default 설정이며, AmazonMQ에서는 해당 부분이 주석처리된 채로 `activemq.xml`이 생성된다.

- **AmazonMQ의 activemq.xml 설정**
  - 기존에는 AmazonMQ에서 Virtual Destinations를 사용하려면, `activemq.xml` 에 아래에 동그라미 친 부분과 같이 `useVirtualTopics="true"`와 `<destinationInterceptors>...</>`부분의 설정을 추가해야 했다.
  
        ![](https://github.com/Integerous/TIL/blob/master/ETC/images/activemq/activemq_xml_2.png?raw=true)
  
  - 그런데, [이 대화](https://forums.aws.amazon.com/thread.jspa?threadID=268432)에서 AWS 엔지니어의 말에 의하면 Virtual Destinations가 기본적으로 enabled 된 상태로 activemq.xml이 생성된다.
    - `We've changed the way default configurations are created. By default virtual destinations are now enabled (an empty element is no longer present in the default XML configuration).`
  - 즉, Topic 명명규칙을 변경할 생각이 없으면, 아무런 설정이 필요 없다.

</br>

## 3. 맺으며
ActiveMQ를 복잡한 환경과 설정으로 사용해 본 것은 아니라서, 나같은 초보에게만 도움이 되었을 내용이다. 하지만 Message Broker 사용 경험과 지식이 1도 없는 상태에서, Virtual Destinations를 이용해서 문제를 해결하기 까지의 과정은 재미있고 유익했다.  

2대의 Scheduler 서버가 Queue에 들어온 메세지를 나누어 가져가는 로그를 확인할 때의 그 기쁨이, 이 내용을 작성하게 한 원동력이 되었다. 언젠가 더 크고 복잡한 서비스에서 Message Broker를 사용할 일이 생긴다면, 이 경험이 큰 도움이 될 것 같다. 

>혹시나 틀린 내용이 있다면, 꼭 지적해주시길 부탁드립니다.

</br>

## * Reference
- [ActiveMQ 공식문서 - Virtual Destinations](http://activemq.apache.org/virtual-destinations.html)
- [Virtual Topics in ActiveMQ](https://tuhrig.de/virtual-topics-in-activemq/)
- [What are Virtual Destinations in ActiveMQ and how do they work?](https://access.redhat.com/solutions/250303)
- [Queues vs. Topics vs. Virtual Topics (in ActiveMQ)](https://tuhrig.de/queues-vs-topics-vs-virtual-topics-in-activemq/)


