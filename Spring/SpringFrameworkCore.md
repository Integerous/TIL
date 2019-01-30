# <스프링 프레임워크 핵심 기술> 강의 내용 정리
>[강의 링크](https://www.inflearn.com/course/spring-framework_core/)  
>모든 내용을 정리하지는 않았다.

---

# 1. ApplicationContext와 다양한 bean 설정 방법

### 1.1. 고전적인 방법으로 Spring bean 설정 파일 만들고, bean 주입하기

#### 1.1.1. application.xml
~~~java
<? xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans">
       
  <bean id="bookService"
        class="me.whiteship.springapplicationcontext.BookService">
        //1.여기까지만 하면 BookService bean 만들고 끝.
          //2.그러므로 아래와 같이 property를 생성해서 주입해줘야 한다.
        <property name="bookRepository" ref="bookRepository" />
          //3. name은 setter에서 가져온 것이고, ref는 레퍼런스로 다른 bean을 참조한다는 것
          //4. 그러므로 ref에는 name(setter)에 들어갈 수 있는 다른 bean의 id가 와야한다.
  <bean>
  
  <bean id="bookRepository"
         class="me.whiteship.springapplicationcontext.BookService"/>
~~~

위 처럼 bean 설정을 하면, 아래와 같이 ApplicationContext를 만들어서 bean 설정을 사용하면 된다.

#### 1.1.2. DemoApplication.java

~~~java
public class DemoApplication {

  public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
    
    String[] beanDefinitionNames = context.getBeanDefinitionNames();
    System.out.println(Arrays.toString(beanDefinitionNames);
    
    BookService bookService = (BookService) context.getBean("bookService");
    System.out.println(bookService.bookRepository != null);
    
    // 결과 : true
    // 이유 : bookService는 위의 <bean id="bookService"  이하의 코드에서
             bookRepository 의존성을 주입을 받았기 때문에
~~~


하지만, 이 방법의 단점은 일일히 `<bean id=""...>`처럼 bean으로 등록하는 것이 번거롭다.  
그래서 등장한 것이 `<context:component-scan...>`

### 1.2. Component Scan로 Spring bean 설정파일 만들고, Annotation으로 bean 주입하기

#### 1.2.1. application.xml

~~~java
<? xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans">

  <context:component-scan base-package="me.whiteshp.springapplicationcontext"/>
    // 1.의미 : 나는 me.whiteship... 패키지부터 bean을 스캐닝해서 등록하겠다.
    // 2.@Component를 확장한 애너테이션들(@Repository, @Service)을 사용해서 bean으로 등록할 수 있다.
    // 3.하지만 bean으로만 등록되고 의존성 주입은 안된다.
    // 4.의존성 주입은 @Autowired 나 @Inject를 통해 받을 수 있다.
~~~

#### 1.2.2. BookRepository

~~~java
@Repository
public class BookRepository {
}
~~~

#### 1.2.3. BookService

~~~java
@Service
public class BookService {

  @Autowired
  BookRepository bookRepository;
  
  public void setBookRepository(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }
}
~~~

이제 1.2.에서 작성한 DemoApplication을 실행하면,  
application.xml을 읽어들이기는 하지만, xml에 등록되어있는 component-scan 기능을 사용해서  
bean들을 me.whiteship...패키지 이하에서 애너테이션들을 스캐닝해서 등록해준다.

### 1.3. xml파일 대신 java파일로 bean 설정하기

#### 1.3.1. Application.config
~~~java
@Configuration
public class ApplicationConfig {
  
  @Bean
  public BookRepository bookRepository() {
    return new BookRepository();
  }
  
  // 방식1. 메소드를 호출해서 의존성 주입
  @Bean
  public BookService bookService() {
    BookService bookService = new BookService();
    bookService.setBookRepository(bookRepository());
    return bookService;
  }
  
  // 방식2. 메소드 파라미터로 의존성 주입받기
  @Bean
  public BookService bookService(BookRepository bookRepository) {
    BookService bookService = new BookService();
    bookService.setBookRepository(bookRepository);
    return bookService;
  }
  
  // 방식3. 의존성 주입을 직접하지 않고, @Autowired를 쓰는 방법
  @Bean
  public BookService bookService(BookRepository bookRepository) {
    return new BookService();
  }
      // 방식3. BookService는 아래와 같이 변경
      /* public class BookService {
            @Autowired
            BookRepository bookRepository;
            ...
         } */
 ~~~
 
위와 같이 java 설정 파일로 만든 것을 application context로 사용하기 위한 방법은 아래와 같다.

#### 1.3.2. DemoApplication.java

~~~java
public class DemoApplication {

  public static void main(String[] args) {
    //ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
    ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
    // 1.ApplicationConfig 클래스를 bean 설정으로 사용하겠다는 것.
    // 2.ApplicationConfig에서 @Bean이 달린 bean 정의들을 읽어서 bean들로 등록하고,
       코드를 작성한 대로 의존성을 주입해준다.
~~~

하지만, 이 방식의 경우에도 @Bean을 하나씩 정의해야 하므로 불편하다.
그래서 application.xml에서 처럼 Component scan을 이용하는 방법이 있다.

### 1.4. @ComponentScan 애너테이션을 활용한 bean 설정과 의존성 주입

~~~java
@Configuration
@ComponentScan(basePackagesClasses = DemoApplication.class) //1.DemoApplication의 위치에서부터 ComponentScan을 해라
public class ApplicationConfig {
}
~~~

이 방법이 스프링부트와 가장 가까운 방법이다.  

그런데 스프링부트에서는 `@SpringBootApplication`을 붙이면 DemoApplication에서 ApplicationContext이 필요없고, ApplicationConfig.java도 필요없다.

@SpringBootApplication 안에는 @ComponentScan 과 @Configuration이 이미 붙어있기 때문에,  
아래의 코드가 사실상 bean 설정파일이다.

~~~java
@SpringBootApplication
public class DemoApplication {
 
 public static void main(String[] args) {
  
  }
}
~~~

---

# 2. @Autowired

### 2.1. 생성자를 통한 의존성 주입
~~~java
@Service
public class BookService {
       
    BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRespository) {
           this.bookRepository = bookRepository;
    }
}
~~~

이 상태에서 어플리케이션을 실행하면 BookRepository 빈을 찾지 못해서 실행 실패.  
아래와 같이 @Repository 혹은 @Component 어노테이션을 붙여줘야 한다.

~~~java
@Repository
public class BookRepository {
}
~~~

### 2.2. Setter를 통한 의존성 주입
~~~java
@Service
public class BookService {
       
    BookRepository bookRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
}
~~~

위의 생성자를 통한 의존성 주입 방식은 빈을 못찾아서 실패했지만,  
setter의 경우 BookRepository 없이도 BookService의 인스턴스를 생성을 할 수 있다.  
하지만 @Autowired 때문에 의존성을 주입하려고 시도하기 때문에 실패한다.  

이 때는 `@Autowired(required = false)`를 주면 의존성 주입을 선택적으로 할 수 있다.  
그러면 BookService의 인스턴스는 만들어져서 빈으로 등록이 되고,  
BookRepository는 의존성 주입이 안된 상태로 빈으로 등록이 된 것이다.

### 2.3. 필드를 통한 의존성 주입
~~~java
@Service
public class BookService {

     @Autowired(required = false)
     BookRepository bookRepository;
}
~~~

이 경우 생성자를 사용한 의존성 주입은 빈을 만들때에도 개입이 된다.  
생성자로 전달받아야 하는 타입의 bean이 없으면 무조건 인스턴스를 만들지 못하고, BookService도 등록이 안된다.

반면, setter나 필드로 의존성을 주입할 때는 @Autowired(required = false)를 사용하여  
BookService가 BookRepository의 의존성 없이도 bean으로 등록되도록 할 수 있다.

### 2.4. 해당 타입의 빈이 여러 개인 경우

예를 들어 아래와 같이 2개의 동일한 타입의 Repository가 있을 때,

~~~java
public interface BookRepository {
}
~~~

~~~java
@Repository
public class MyBookRepository implements BookRepository {
}
~~~

~~~java
@Repository
public class KeesunBookRepository implements BookRepository {
}
~~~

아래와 같이 bookRepository 의존성을 주입하려 하면, 주입을 못해준다.  
2개 중에 어떤 것을 주입해야 하는지 모르기 때문에.

~~~java
@Service
public class BookService { 
     
     @Autowired
     BookRepository bookRepository;
}
~~~

이 경우, 아래와 같이 `@Primary` 와 `@Qualifier` 어노테이션으로 정해줄 수 있다.  
- `@Primary` - 여러가지 중에 주로 사용할 것이다.
- `@Qualifier` - bean의 id를 명시해준다. (@Service, @Repository 등의 애노테이션이 붙은 클래스의 bean id는 클래스명과 같고, 앞 글자만 소문자)

~~~java
@Service
public class BookService { 
     
     @Autowired @Primary
     BookRepository bookRepository;
}
~~~

~~~java
@Service
public class BookService { 
     
     @Autowired @Qualifier("keesunBookRepository")
     BookRepository bookRepository;
}
~~~

해당 타입의 빈을 모두 주입받을 수도 있다.

~~~java
@Service
public class BookService { 
     
     @Autowired
     List<BookRepository> bookRepositories; 
}
~~~

# 3. 빈의 스코프

### 3.1. Singleton
디폴트는 싱글톤이다.  
싱글톤은 어플리케이션 전반에 걸쳐서 해당 빈의 인스턴스가 오직 한개 뿐인 것.  

때문에 아래의 어플리케이션 실행 결과 인스턴스의 레퍼런스가 같다.
~~~java
@Component
public class AppRunner implements ApplicationRunner {

       @Autowired
       Single single;

       @Autowired
       Proto proto;

       @Override
       public void run(ApplicationArguments args) throws Exception {
              System.out.println(proto);
              System.out.println(single.getProto());
       }
}
~~~

~~~java
@Component
public class Single {
       
       @Autowired
       private Proto proto;
       
       public Proto getProto() {
              return proto;
       }
}
~~~

~~~java
@Component
public class Proto {
}
~~~

또한 모든 싱글톤 스코프의 빈은 기본값이 applicationContext를 만들 때 만들어지게 되어있다.  
그러므로 어플리케이션이 구동될 때 시간이 더 걸릴 수 있다.

### 3.2. Prototype

대부분의 경우 싱글톤 스코프를 쓰지만, 그 외의 경우(Request, Session, WebSocket, ...)에는 prototype 스코프와 유사하다.  
프로토타입은 매번 새로운 인스턴스를 만들어서 사용하는 것이다.

~~~java
@Component @Scope("prototype")
public class Proto {
}
~~~

이제 Proto의 빈을 받아 올 때마다 새로운 인스턴스가 생성된다. (빈을 받아 올 때만)

~~~java
@Component
public class AppRunner implements ApplicationRunner {
       
       @Autowired
       ApplicationContext ctx;
       
       @Override
       public void run(ApplicationArguments args) throws Exception {
              
              System.out.println(ctx.getBean(Proto.class));
              System.out.println(ctx.getBean(Proto.class));
              System.out.println(ctx.getBean(Proto.class));
              
              System.out.println(ctx.getBean(Single.class));
              System.out.println(ctx.getBean(Single.class));
              System.out.println(ctx.getBean(Single.class));
       }
}
~~~

위에서 Proto는 3번 모두 다른 인스턴스 레퍼런스가 찍힐 것이고,  
Single은 3번 모두 같은 인스턴스 레퍼런스가 찍힐 것이다.

### 3.3. 싱글톤 빈이 프로토타입 빈을 참조하면?

만약 아래 처럼 prototype bean이 singleton bean을 참조하면?
- 아무 문제없다.
- 프로토타입의 빈은 새롭지만 싱글톤의 빈은 항상 같으므로.

~~~java
@Component @Scope("prototype")
public class Proto {

       @Autowired
       Single Single;
~~~

하지만 반대로 Singleton scope의 bean이 prototype scope의 bean을 참조하면?
- 프로토타입 빈이 업데이트가 안된다.


### 3.4. 싱글톤 빈이 참조하는 프로토타입 빈이 업데이트 되도록 하려면?
세 가지 방법이 있다.
- scope-proxy
- Object-Provider
- Provider (표준)

#### 3.4.1. scope-proxy
아래와 같이 proxyMode를 설정할 경우, 프로토타입 빈은 업데이트 된다.  
(proxyMode의 디폴트 값은 proxy를 사용하지 않는 것이다.)

~~~java
@Component @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Proto {
}
~~~

proxyMode를 쓴다는 것은 클래스를 프록시로 감싸는 것이다. 

>왜 프록시로 감싸는 것인가?
싱글톤 인스턴스가 prototype 빈 인스턴스를 직접 참조하면 안되기 때문에.  
프록시를 거쳐서 참조해야 한다.

>왜 프록시를 거쳐서 참조해야 하는가?  
직접 참조하면 이 prototype 빈 인스턴스를 매번 새로운 인스턴스로 바꿔줄 수 없기 때문에.
매 번 새로운 인스턴스로 바꿔줄 수 있는 프록시로 감싸도록 한다.  

prototype 빈을 상속받은 클래스를 만들어서 프록시를 만들어주는 CG라이브러리 라는 third-party 라이브러리가 있다.  
이 라이브러리는 클래스도 프록시를 만들어줄 수 있게 해준다.

원래 Java JDK 안에 있는 다이나믹 프록시는 인터페이스의 프록시 밖에 못만든다.  
때문에 위의 코드에서 처럼 `proxyMode = ScopedProxyMode.TARGET_CLASS`는  
CG라이브러리를 이용해서 클래스를 상속받은 프록시를 만들라고 지시하는 것이다.  

만약 인터페이스가 있었다면 `proxyMode = ScopedProxyMode.INTERFACE`를 지시하여  
JDK의 인터페이스 기반의 프록시를 만들어 사용


#### 3.4.2. Object-Provider

~~~java
@Component
public class Single {

       @Autowired
       private ObjectProvider<Proto> proto;
       
       public Proto getProto() {
              return proto.getIfAvailable();
~~~

이 방법은 코드에 스프링 코드가 들어가기 때문에 선호하지 않는다. 

# 4. Profile

프로파일은 bean들의 묶음이며, 환경이다.

각각의 환경에 따라 bean을 다르게 사용해야 할 경우,
또는 특정 환경에서만 어떠한 bean을 등록해야하는 경우에 사용한다.

프로파일은 ApplicationContext의 Environment를 사용한다.
ApplicationContext가 bean factory의 역할만 하는 것은 아니다.
ApllicationContext가 상속받고있는 EnvironmentCapable 인터페이스의 getEnvironment()를 사용하여 Environment를 가져올 수 있다.
그리고 environment.getActiveProfile()을 통해 현재 활성화된 프로파일을 가져올 수 있다.

~~~java
@Component
public class AppRunner implements ApllicationRunner {

    @Autowired
    ApplicationContext ctx;

    @Override
    public void run(ApplicationArguments args) throws Exception {
       Environment environment = ctx.getEnvironment();
       System.out.println(Arrays.toString(environment.getActiveProfiles());
       System.out.println(Arrays.toString(environment.getDefaultProfiles()); // Default
    }
}
~~~


아래의 예시는 테스트 프로파일일 때만 사용되는 빈 설정 파일이다.  
test 라는 프로파일로 어플리케이션 실행하기 전까지는 아래의 설정이 적용이 안된다.

~~~java
@Configuration
@Profile("test")
public class TestConfiguration {

    @Bean
    public BookRepository bookRepository() {
        return new TestBookRepository();
    }
}
~~~

### 4.1. 프로파일 정의 예시

~~~java
@Repository
@Profile("test")
public class TestBookRepository implements BookRepository {
}
~~~

~~~java
@Repository
@Profile("!Prod") //Prod 프로파일이 아닌 프로파일
public class TestBookRepository implements BookRepository {
}
~~~

~~~java
@Repository
@Profile("!Prod & test") //Prod 프로파일이 아니면서 test인 프로파일
public class TestBookRepository implements BookRepository {
}
~~~

# 5. Property

- 프로퍼티는 다양한 방법으로 정의할 수 있는 설정값
- Environment의 역할은 프로퍼티 소스 설정 및 프로퍼티 값 가져오기

~~~java
@Component
public class AppRunner implements ApplicationRunner {

  @Autowired
  ApplicationContext ctx;
  
  @Autowired
  BookRepository bookRepository;
  
  @Override
  public void run(ApplicationArguments args) throws Exception {
       Environment environment = ctx.getEnvironment();
       System.out.println(environment.getProperty("app.name")); // VM options에 -Dapp.name=spring5 명령
       System.out.println(environment.getProperty("app.about")); //app.properties에 정의된 app.about
  }
}
~~~

~~~java
@SpringBootApplication
@PropertySource("classpath:/app.properties") //Environment를 통해 프로퍼티를 추가하는 방법
public class Demospring51Application {
       
       public static void main(String[] args) {
            SpringApplication.run(Demospring51Application.class, args);
       }
}
~~~

### 5.1. Property 우선 순위
- StandardServletEnvironment의 우선순위
  - ServletConfig 매개변수
  - ServletContext 매개변수
  - JNDI (java:comp/env/)
  - JVM 시스템 프로퍼티 (-Dkey="value")
  - JVM 시스템 환경 변수 (운영 체제 환경 변수)

# 6. Resource 추상화
>org.springframework.core.io.Resource

### 6.1. 특징
- java.net.URL을 추상화 한 것.
- 스프링 내부에서 많이 사용하는 인터페이스

### 6.2. 추상화 한 이유
- 클래스패스 기준으로 리소스 읽어오는 기능 부재
- ServletContext를 기준으로 상대 경로로 읽어오는 기능 부재
- 새로운 핸들러를 등록하여 특별한 URL 접미사를 만들어 사용할 수는 있지만 구현이 복잡하고 편의성 메소드가 부족하다.

### 6.3. 리소스 읽어오기
- Resource의 타입은 location 문자열과 ApplicationContext의 타입에 따라 결정된다.
	- ClassPathXmlApplicationContext -> ClassPathResource
	- FileSystemXmlApplicationContext -> FileSystemResource
  - WebApplicationContext -> ServletContextResource
- ApplicationContext의 타입에 상관없이 리소스 타입을 강제하려면 java.net.URL 접두어(+classpath:) 중 하나를 사용할 수 있다.
  - classpath:me/whiteship/config.xml -> ClassPathResource
  - file:///some/resource/path/config.xml -> FileSystemResource
  
  
# 7. SpEL (스프링 Expression Language)
- 객체 그래프를 조회하고 조작하는 기능 제공
- Unified EL과 비슷하지만, 메소드 호출을 지원하며, 문자열 템플릿 기능도 제공
- OGNL, MVEL, JBOss EL 등 자바에서 사용할 수 있는 여러 EL이 있지만, SpEL은 모든 스프링 프로젝트 전반에 걸쳐 사용할 EL로 만들었다.
- 스프링 3.0부터 지원

### 7.1. 문법
- #{'표현식'}
- ${프로퍼티}
- 표현식은 프로퍼티를 포함할 수 있지만, 프로퍼티는 표현식을 포함할 수 없다.
	- #{${my.data} + 1}

### 7.2. 예시
~~~java
@Component
public class AppRunner implements ApplicationRunner {
	
	@Value("#{1 + 1}")
	int value;

	@Value("#{'hello ' + 'world'}")
	String greeting

	@Value("#{1 eq 1}")
	boolean trueOrFalse;

	@Value("hello")
	String hello;

	@Value("${my.value}")
	int myValue;

	@Value("#{${my.value} eq 100}")
	boolean isMyValue100;

	// bean 참조하기 (sample 이라는 bean의 data)
	@Value("#{sample.data}")
	int sampleData;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println(value);
		System.out.println(greeting);
		System.out.println(trueOrFalse);
		System.out.println(hello);
		System.out.println(myValue);
		System.out.println(isMyValue100);
~~~

# 8. Spring AOP
>Spring AOP는 AOP의 구현체 제공  
>Java의 AOP구현체인 AspectJ와 연동해서 사용할 수 있는 기능 제공  

AOP는 OOP를 보완하는 수단으로, 흩어진 Aspect를 모듈화 할 수 있는 프로그래밍 기법

### 8.1. AOP 주요 개념
- Aspect : 모듈
- Target : 적용되는 대상
- Advice : 해야할 일들
- Join point : 모듈이 끼어드는 지점
- Pointcut : 어디에 적용해야 하는지
- AOP 구현체
    - Java에서는 AspectJ와 Spring AOP


### 8.2. AOP 적용 방법
- 컴파일 시점 (AspectJ)
- 로드 타임 시점 (AspectJ)
- 런타임 시점 (Spring AOP)

### 8.3. Spring AOP 특징
- 프록시 기반의 AOP 구현체
- 스프링 bean에만 AOP를 적용할 수 있다.
- 모든 AOP 기능을 제공하는 것이 목적이 아니라, 스프링 IoC와 연동하여 엔터프라이즈 애플리케이션에서 가장 흔한 문제에 대한 해결책을 제공하는 것이 목적

#### 8.3.1. 프록시 패턴
- 기존 코드 변경없이 접근 제어 또는 부가 기능 추가

#### 8.3.2. 문제점
- 매 번 프록시 클래스를 작성해야 하는가?
- 여러 클래스, 여러 메소드에 적용하려면?
- 객체들 관계도 복잡하고..

#### 8.3.3. 그래서 등장한 것이 Spring AOP
- 스프링 IoC 컨테이너가 제공하는 기반 시설과 Dynamic 프록시를 사용하여 여러 복잡한 문제 해결
- 동적 프록시: 동적으로 프록시 객체를 생성하는 방법
    - 자바가 제공하는 방법은 인터페이스 기반 프록시 생성
    - CGlib은 클래스 기반 프록시도 지원
- 스프링 IoC: 기존 빈을 대체하는 동적 프록시 빈을 만들어 등록 시켜준다.
    - 클라이언트 코드 변경 없음
    - AbstractAutoProxyCreator

#### 8.3.4. 예시

~~~java
@Component
@Aspect
public class PerfAspect {
	
	@Around("@annotation(PerfLogging)") //@PerfLogging 애노테이션이 붙은 곳에 적용
	public Object logPerf(ProceedingJoinPoint pjp) throws Throwable {
		log begin = System.currentTimeMillis();
		Object retVal = pjp.proceed();
		System.out.println(System.currentTimeMillis() - begin);
		return retVal;
	}
	
	@Before("bean(simpleEventService)") //bean의 모든 메서드에 앞서(@Before) 적용
	public void hello() {
		System.out.println("hello");
	}
}
~~~

~~~java
@Documented //javaDoc 만들때 사용 
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS) //이 어노테이션을 .class 파일까지도 유지
public @interface PerfLogging {
}
~~~

