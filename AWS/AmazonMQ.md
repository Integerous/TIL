# AmazonMQ 사용 예시
>AWS SQS를 사용하던 프로젝트를 AmazonMQ로 바꾸면서 작성한 코드 정리  

## 0. History
- 뉴스팀은 각종 뉴스 매체에서 뉴스 데이터를 받아 파싱하여 뉴스 서비스에 사용하고, 허브팀은 파싱된 뉴스 데이터를 뉴스팀으로부터 받아서 허브 서비스에 사용한다.
- 이 과정에서 파싱된 뉴스 데이터를 전달받기 위해 기존에 AWS SQS의 Queue를 활용한 1:1 메세지 브로킹을 사용해왔다.
- 그런데 TV팀도 뉴스 데이터가 필요해져서, 뉴스에서는 Publish하고, 허브팀과 TV팀이 Subscribe하는 1:N Pub-Sub 방식으로 바꿔야했다.
- AmazonMQ의 Topic을 활용하여 1:N Pub-Sub 방식의 메세지 브로킹을 구현했고, 나(허브팀)는 Subscriber만 구현했다.


## 1. Broker 정보 설정
~~~yml
spring:
  activemq:
    broker-url: ssl://b-xxxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx-x.mq.ap-northeast-2.amazonaws.com:61617
    user: 사용자
    password: 비밀번호
    
    # 로컬 테스트용 MQ
#    broker-url: tcp://localhost:61616
#    user: admin
#    password: admin
~~~
- AWS 콘솔에서 생성한 broker 정보를 세팅한다.
- pub-sub 설정을 application.yml 파일에서 할 수도 있다.
    ~~~yml
    spring:
      jms:
        pub-sub-domain: true
    ~~~
## 2. Jms 설정
~~~java
@EnableJms
@SpringBootApplication
public class SchedulerApplication {

    // 생략
}
~~~

## 3. Jms Listener 설정
~~~java
@Configuration
@EnableJms
@RequiredArgsConstructor
public class AwsMqListenerConfig {

    private final ConnectionFactory connectionFactory;
    private final DefaultJmsListenerContainerFactoryConfigurer configurer;

    @Bean
    public JmsListenerContainerFactory<?> newsJmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

        configurer.configure(factory, connectionFactory);

        factory.setPubSubDomain(true);
        factory.setSessionTransacted(true);
        factory.setErrorHandler(jmsErrorHandler());

        return factory;
    }

    @Bean
    public ErrorHandler jmsErrorHandler() {
        return e -> ZumServiceAnalysisLogger.log(e.getMessage());
    }
}
~~~
- `@EnableJms` 필수
- 위와 같이 `JmsListenerContainerFactory`를 구현해서 각종 설정을 세팅할 수 있다.


## 4. Listener 구현
~~~java
@Slf4j
@Component
public class AwsMqNewsListener {

    @Transactional(value = "hubTransactionManager")
    @JmsListener(destination = "news.topic", containerFactory = "newsJmsListenerContainerFactory")
//    @JmsListener(destination = "for.test", containerFactory = "newsJmsListenerContainerFactory") // 로컬 테스트용 MQ
    public void receiveMessage(@Payload String data) {
    
        // 동기화 및 재처리 로직
    }
}
~~~
- Publisher에서 만든 topic명 (여기서는 `news.topic`)을 destination으로 설정하고, containerFactory를 지정한다. 
