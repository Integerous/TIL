# ActiveMQ의 Virtual Destinations를 활용한 로드밸런싱
>실무에서 ActiveMQ로 메세지브로커를 구성하는 과정에서 습득한 Virtual Destinations 활용 지식을 정리해본다.

## 0. Topic, Queue, Virtual Topic 비교


## 1. Virtual Topic을 사용한 이유
- **상황**
  - 메세지를 뿌리는 `뉴스 서비스`와, 메세지를 받아서 처리해야하는 `TV 서비스`, `Hub 서비스`가 있는 상황
    - 기존에는 뉴스 서비스의 메세지를 Hub 서비스만 받았기 때문에 `AWS SQS로 1:1 메세지 브로킹`을 하고 있었다.
  - 현재는 메세지 Listener가 두 곳이지만, 추후에 Listener가 추가될 수 있기 때문에 1:1로 메세지를 브로킹하는 Queue 방식은 지양하고,
  - `AmazonMQ(ActiveMQ)의 Topic을 사용하여 1:N Pub-Sub 방식의 메세지 브로커를 구성`하였다.

- **문제 발생**
  - 멀티 모듈 프로젝트로 구성된 Hub 서비스의 경우, Scheduler라는 모듈이 메세지를 받아서 처리하고 있었다.
  - 그런데 2개의 Scheduler 서버가 메세지를 나누어 받아서 처리하고 있었고, 기존에 AWS SQS로 메세지 브로킹을 할 때에는 각 서버가 하나의 Queue에서 메세지를 꺼내가기 때문에 문제가 되지 않았다.
  - 그런데 AmazonMQ(ActiveMQ)의 `Topic 방식은 Subscriber 모두에게 같은 메세지가 전달`된다.
  - 때문에 2개의 Scheduler 서버가 각각 같은 메세지를 전달 받아서 처리하므로, `메세지 중복처리 문제가 발생`했다.

- **해결 방안 1 (1개의 서버만 사용)**
  - TV 서비스 처럼 1개의 서버만 Subscriber로 사용하면 중복처리 문제는 바로 해결된다.
  - 하지만 추후에 서버 스케일 업이 필요한 상황이 도래하면, 똑같은 문제에 봉착하므로 임시방편일 뿐이었다.  
  - 또한, Scheduler 서버는 클라우드가 아닌 IDC에 구성된 서버들이기 때문에, 더 많은 작업들이 필요했다.
  - 그래서 패스.
  
- **해결 방안 2 (UUID 활용)**
  - 뉴스 서비스에서 메세지(뉴스 데이터)를 Publish 하기 전에 UUID를 포함시켜주면, Subscriber들은 UUID를 활용해서 중복처리를 방지할 수 있다.
  - 하지만 UUID를 생성하는 순간, 관련 모든 서비스에 영향을 미치는 관리 포인트가 하나 늘어난다. (Publisher와 Subscriber 양쪽 DB에 UUID컬럼 추가, 관련 클래스에 UUID 로직 추가 등등)
  - 또한, UUID를 사용해서 중복처리를 방어하는 로직은 DB접근을 비약적으로 증가시키기 때문에, 결국 서버를 1대만 사용하는것 보다도 비효율적이다.
  - 그렇다고 DB접근을 줄이고자 Redis로 UUID를 캐싱하는 것은 또 관리 포인트를 늘리는 일이었다.
  - 그래서 패스.

- **해결 방안 3 (Virtual Destinations 사용)**
  - 팀원분에게 얼핏 듣기로는, RabbitMQ는 그룹을 나누어 메세지를 전달하도록 설정할 수 있어서, 같은 그룹에서는 중복처리를 방지할 수 있다고 한다. (확인 안해봄)
  - ActiveMQ도 그런 기능이 있을거라는 생각에 [공식 문서](https://activemq.apache.org/features)를 뒤적거리다가 Destination Features 카테고리에서 [Virtual Destinations](https://activemq.apache.org/virtual-destinations)를 찾았다.
  - 결론적으로 Virtual Topic을 사용하면,
    - `뉴스 서비스(Publisher)는 Topic 방식 그대로 메세지를 publish하고, (topic 이름만 변경)`
    - `단일 서버인 TV 서비스(Subscirber)는 Topic 방식 그대로 메세지를 subscribe하고, (topic 이름만 변경)`
    - `N개의 서버인 Hub 서비스(Subscriber)는 Topic 방식으로 메세지를 subscribe하되, 논리적 topic을 Listening할 때 자동으로 생성되는 물리적 Queue로 부터 메세지를 subscribe 한다.`
  - 이 방법을 해결책으로 선택한 이유는,  
    - 기존의 Topic방식을 그대로 사용하기 때문에 관리포인트가 증가하지 않는다. (topic 이름만 바꾸면 끝)
    - Pub-Sub 방식을 사용하기 때문에 Subscriber가 증가해도 Publisher를 수정할 필요가 없다.
    - Scheduler의 서버가 999개로 늘어도 하나의 Queue에서 메세지를 가져가기 때문에 중복처리 문제가 발생하지 않는다.
    
    
## 2. Virtual Destinations 사용 방법
>사용 방법은 너무나 간단하다. out-of-the-box 

- **AmazonMQ 설정**
  - 기존에는 Virtual Destinations를 사용하기 위해 `activemq.xml` 에서 설정을 수정해야 했지만, [이 대화](https://forums.aws.amazon.com/thread.jspa?threadID=268432)에 의하면 Virtual Destinations가 기본적으로 enabled 된 상태로 activemq.xml이 생성된다.
    - `We've changed the way default configurations are created. By default virtual destinations are now enabled (an empty element is no longer present in the default XML configuration).`
  - 또한 

### Reference
- [ActiveMQ 공식문서 - Virtual Destinations](http://activemq.apache.org/virtual-destinations.html)
- [Virtual Topics in ActiveMQ](https://tuhrig.de/virtual-topics-in-activemq/)
- [What are Virtual Destinations in ActiveMQ and how do they work?](https://access.redhat.com/solutions/250303)
- [Queues vs. Topics vs. Virtual Topics (in ActiveMQ)](https://tuhrig.de/queues-vs-topics-vs-virtual-topics-in-activemq/)
