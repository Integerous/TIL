# 스프링 부트를 이용한 웹 서비스 개발
>작성중  
>[Tacademy 강의](https://tacademy.skplanet.com/live/player/onlineLectureDetail.action?seq=145#sec2)  
>강사: 김지헌(Honeymon, 현 우아한형제들 개발자)

스프링부트는 톰캣과 같은 WAS가 설치된 곳에 WAR파일을 배포하는 것이 아니라  
자기 자신을 배포하고 실행할 수 있는 아키텍쳐를 가지게 되었다.

## 스프링 부트 특징
>스프링부트 프로젝트에 명시된 [Spring boot feature](https://spring.io/projects/spring-boot)
- 단독실행 가능한 스프링 어플리케이션 생성
- 내장 컨테이너로 Tomcat, Jetty, Undertow 중에서 선택가능 (WAR파일로 배포할 필요 없음)
- 스타터(starter)를 통해 간결한 의존성 구성 지원
- 스프링과 써드파티 라이브러리에 대한 자동구성(Auto-Configuration) 제공
- 제품출시(realease) 후, 운영에 필요한 다양한 기능(metrics, health check, externalized configuration) 제공
- XML 구성 필요없음

## 스프링 부트를 한마디로?
>스프링 프레임워크를 기반으로한 개발플랫폼

## 스프링 부트 구성 요소
>아래 4가지 요소가 스프링 부트 개발 플랫폼을 이룬다.

1. 빌드도구 (Gradle vs Maven)
2. 스프링 프레임워크 (4.xx vs 5.xx)
3. 스프링 부트(v1.5 vs v2.0)
4. 스프링 부트 스타터(spring-boot-starter)

## 빌드
Spring Initializr(https://start.spring.io)에서 시작

Eclipes 나 IntelliJ에서 스프링 부트를 사용하는 것도  
사실 Spring Initializr에 url로 GET요청을 날려서 프로젝트를 생성하고
그 파일을 내려받는 방식으로 동작하는 것이다.

스프링 부트가 지원하는 개발 언어는 Java, Kotlin, Groovy가 있고,  
Gradle이 Groovy를 DSL(Domain Specification Language)로 자체언어로 사용하고 있지만 
그 외적인 부분에서는 Groovy의 유저는 많지 않다.

## Gradle

Gradle와 Maven은 각각 프로젝트의 의존성과 기본적인 빌드 동작을 선언하는 스크립트가 있다.  
Gralde은 build.gradle , Maven은 pom.xml  
build.gradle 스크립트 안에는 buildscript 라는 선언부에서 스프링 부트 Gradle 플러그인을 선언한다.

## 프로젝트 기본 코드구조

### Gradle

- build.gradle
- gradle
  - wrapper
    - gradle-wrapper.jar `수동으로 로컬에 설치할 필요없이 프로젝트에 내장을 시켜서 빌드하는 것`
    - gradle-wrapper.properties
- gradlew `리눅스/유닉스 계열에서 실행되는 스크립트`
- gradlew.bat `윈도우에서 실행되는 배치파일`
- settings.gradle `프로젝트의 기본 구성 정의`
- src
  - main
    - Java
      - io.Honeymon
        - t
          - springboot
            - TSrpingBootApplication.java `
    - resources
      - application.yml
      - static
      - templates
  - test
    - java
      - io.honeymon
        - t
          - springboot
            -TSrpingBootApplication.java

>빌드가 실행되면 컴파일 단계에서는 `src-main-java`와 `src-main-resources` 영역만 컴파일되고 패키징되어 배포된다.

## (Excecutable) JAR vs WAR

기존의 배포 형태는 서버에 톰캣과 같은 WAS가 설치되어 있고, WAS의 특정 위치에 springboot.war라는 배포본을 업로드하면 톰캣이 파일을 읽어들여서 실행하는 형태였다면,  

지금의 배포 형태는 springboot.jar파일과 embeded container를 묶어서 패키징하는 bootRepackage가 발생한다. `.jar` 파일을 가지고 `$java -jar springboot.jar`를 명령하면 바로 실행가능한 형태가 된다.

Docker 같은 컨테이너 안에서도 실행가능한 jar파일을 배포하고 java 빌드팩을 설치해서 작은 단위의 컨테이너 안에서도 어플리케이션을 실행할 수 있다.

## 스프링 부트 v1.5 vs v2.0
>스프링 프레임워크 4를 쓰느냐 5를 쓰느냐의 차이  
>2019년 8월 1일부로 스프링부트 v1.5 버전은 끝날 예정이기 때문에 2.0 사용 권장

## 스프링 부트는 Annotation 기반 동작

### 스프링 부트를 구성하는 Annotation
- @SpringBootApplication
- @ComponentScan
- @EnableAutoConfiguration
- @Configuration
- @ConditionalOn~
- @SpringBootConfiguration(= @Configuration)
- @EnableConfigurationProperties
- @ConfigurationProperties


### @SpringBootApplication

~~~java
@SpringBootApplication
public class BootSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootSpringBootApplication.class, args);
    }
}
~~~

1. `public static void main(String[] args)`이라는 entry point(진입점)을 통해서 어플리케이션이 실행되면,
2. `SpringApplication.run(BootSpringBootApplication.class, args);`이라는 클래스가 실행되면서, 이 안에서 Spring IoC Container라는 객체를 관리하는 컨테이너를 실행시키고,
3. `@SpringBootApplication` 어노테이션이 붙은 위치를 기준으로 하향식으로 밑에 있는 패키지를 탐색하는 것이 스프링 부트의 기본적인 동작 방식이다.

## Spring boot starter

스프링 부트 스타터는 `spring-boot-autoconfigure`와 `spring-boot-dependencies` 두 개의 모듈이 합쳐져서 기본 동작을 한다.

그리고 이 스프링 부트 스타터를 기준으로  
`spring-boot-starter-web`, `spring-boot-starter-security` 등 기능별로 의존성을 모아서 정의한다.

또한 스프링부트 스타터를 사용하지 않으면 각 라이브러리 마다 버전을 명시해야하는 불편함이 있다. 하지만 스프링부트 스타터를 사용하면 개발자가 신경써야할 부분이 3가지로 줄어든다.

1. 스프링 부트 버전
2. 사용하려는 라이브러리의 스프링 부트 스타터 지원 여부
3. 지원하지 않는 경우 사용하려는 라이브러리 등록 방법

스프링 부트 개발팀에서 공식적으로 지원하는 스타터의 목록과 커뮤니티에서 제공하는 스타터 목록을 [스프링 프로젝트 깃헙](https://github.com/spring-projects/spring-boot/tree/master/spring-boot-project/spring-boot-starters)에서 확인 가능하다.

## 자동구성(Auto-configuration)
- 스프링 부트가 기술흐름에 따라 제공하는 관례(Convention)적인 구성
- 자동구성이 어떻게 되는지 살펴보려면 스프링 프로젝트 깃헙의 [spring-boot-autoconfigure](https://github.com/spring-projects/spring-boot/tree/master/spring-boot-project/spring-boot-autoconfigure)이라는 모듈을 보면 된다.
- 동작 선언
    - @EnableAutoConfiguration (in @SpringBootApplication)
    - @Configuration
- 사용 애너테이션
    - @Configuration
    - @ConditionalOn

## 외부구성(External Configuration)
>스프링 부트는 어플리케이션 속성을 외부로부터 주입받아서 적용할 수 있다.

### 적용 우선순위 (원래는 13단계)
>1번부터 적용하고 뒤로 갈수록 덮어쓰는 형태

1. 실행 인자 (터미널 상에서 실행하거나 AWS에서 환경변수로 전달하는 경우)
2. SPRING_APPLICATION_JSON (서버 안에 JSON으로 선언한 구성파일을 위치를 지정했을때)
3. 환경변수 (운영체제의 환경변수)
4. 기타 등등
5. application.yml 또는 application.properties
6. application-{defaultprofiles}.yml 또는 application-{defaultprofiles}.properties

## Programming in Spring Environment

- @ComponentScan을 통해 아래의 어노테이션들이 붙은 클래스들을 탐색해서 스프링 빈으로 등록하고 ApplicationContext에 적재
  - @Repository
  - @Component
  - @Service
  - @Controller / @RestController
  - @Configuration
    - @Bean
    - @ConfigurationProperties
- DI, IoC, @Autowired
- @Value vs @ConfigurationProperties
- AOP

## setting.gradle
>프로젝트의 구성을 정의하는 스크립트

`rootProject.name = 'spring-boot'`  
프로젝트를 import하면 위의 설정에 따라 spring-boot라는 프로젝트 이름으로 선언된다.

- 멀티 프로젝트 구성시 사용
- 프로젝트 이름 설정
- 하위 프로젝트 정의
- 하위 프로젝트 설명(주석으로)


## Gradle Wrapper
>Wrapper가 나오기 전에는 Gradle을 사용하기 위해서는 사용자가 수동으로 다운로드 받아야했다.  
>그래서 Gradle 버전의 차이가 발생했었는데, Wrapper가 도입되어 이런 문제가 줄어들었다.

### 동작 방식
프로젝트 생성하고 Gradle 빌드를 실행하면 (`./gradlew build`)  
Gradle에서 지정된 위치에 Gradle Wrapper가 gradle-wrapper.jar파일과 gradle-wrapper.properties가 있는지 확인하고  
없으면 Gradle 스크립트에 의해서 배포서버에 가서 gradle-wrapper.jar를 내려받고  
압축을 풀어서 특정 위치에 설치하고 jar파일을 기준으로 빌드를 실행한다.


## @Bean vs @Component

@Bean은 개발자가 제어할 수 없는 외부에서 작성된 클래스들을 스프링 Bean으로 등록할 때 사용.    
@Component는 내가 작성한 컴포넌트 클래스에 사용.

### @Component vs @Service
트랜잭션(데이터를 불러와서 조작을 하고 데이터를 저장하는 하나의 큰 흐름)안에서 관리를 할 경우에는 @Service 사용.  
트랜잭션 처리가 필요 없는 경우에는 @Component 사용.

### 스프링 빈(Bean)객체 vs @Bean
스프링 IoC 컨테이너에서 생성하고 호출하고 소멸되기까지 생명주기를 관리하는 객체를 스프링 Bean 객체라 하고,
그러한 객체 중에 하나가 @Bean 애너테이션이 붙어있는 클래스다.


## 의존성 주입(DI)
>개발자가 인스턴스를 호출해서 쓰는 것이 아니라, 프레임워크에 등록을 해놓고 가져다 쓰는 것.


### 라이브러리 사용 (인스턴스 생성해서 사용)
~~~java
public class ObjectMapperTest {

    @Test
    public void test() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        Book book =
            new Book("test-book", "test-isbn13", "test-isbn10");

        String strBook = objectMapper.writeValueAsString(book);
        // 검증 생략
    }
}
~~~

### 프레임워크 사용
>스프링과 같은 IoC컨테이너에서는 위의 예시처럼 ObjectMapper 인스턴스를 선언해서 사용할 필요가 없다.  
>application-context가 구동되는 상황에서 이미 어딘가에서 ObjectMapper를 스프링 빈으로 선언해서 구성해놨기 때문에 아래와 같이 @Autowired를 필드에 정의해놓으면 스프링컨테이너가 @Autowired를 인식하고 ObjectMapper를 주입해준다.

~~~java
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class ObjectMapperTest2 {
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void test() throws JsonProcessingException {
        Book book =
            new Book("test-book", "test-isbn13", "teset-isbn10");

        String strBook = objectMapper.writeValueAsString(book);
        // 검증 생략
    }
}
~~~

### 의존성 주입 방법 3가지

1. 생성자 주입 방식(권장)
>생성자가 1개만 있어야 사용 가능
~~~java
@Service //이 애노테이션을 스프링이 확인하고 관리해야 할 컴포넌트로 인식하여 가지고 있는 것을 주입해준다.
public class BookServiceImpl implements BookService {
    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }
    // 코드 생략
}
~~~

2. 설정자 주입 방식(Setter)
~~~java
@Service
public class BookServiceImpl implements BookService {
    private BookRepository repository;

    @Autowired
    public void setRepository(BookRepository repository) {
        this.repository = repository;
    }
    // 코드 생략
}
~~~

3. 필드에 @Autowired 선언
>처음에는 이 방식을 많이 사용하지만, 점차 테스트나 확장성을 고려하기 시작하면  
>생성자 주입방식과 설정자 주입방식을 사용하게 된다.

~~~java
@Service
public class BookServiceImpl implements BookService {
    
    @Autowired
    private BookRepository repository;
    // 코드 생략
}
~~~

## 일반적인 개발 방향
1. 영속화(Persistence) - @Repository
    - Entity와 Repository 작성
2. 서비스(Service) - @Service
    - Repository를 이용하는 Service 작성
3. 표현(Presentation) - @Controller
    - Service를 이용하는 Controller 작성
4. 계층(Layer) - @Component

## Spring Data JPA
>JPA를 구현한 구현체 중에 Hibernate가 있고, 이를 쉽게 추상화한 라이브러리가 Spring Data JPA
>[김영한님의 JPA 소개 [슬라이드]](https://www.slideshare.net/zipkyh/ksug2015-jpa1-jpa-51213397)  
>[김영한님의 Spring Data JPA [슬라이드]](https://www.slideshare.net/zipkyh/spring-datajpa)

- ORM(Object-Relational Mapping)
 - 대부분 개발언어 플랫폼마다 제공
 - 객체로 관계형 데이터베이스(RDBS)를 관리
- JPA(Java Persistence API)
 - Java 객체 정보를 영속화하는 중간 과정을 처리한다.
 - Entity 객체를 저장하고 변경하고 삭제하면 그에 대응하는 쿼리(Query)를 생성하고 실행한다.

## 비즈니스 로직 구현에만 집중해라!
- 영속화 계층(@Repository)에서는 Entity 관리만
- 비즈니스 로직 구현은 도메인 영역에서
- 서로 다른 도메인 사이에 연계는 서비스 계층(@Service)에서
- 외부요청에 대한 처리는 컨트롤러 계층(@Controller)에서

## 실습

### 1. Book 엔티티 생성
~~~java
import org.springframework.data.jpa.domain.AbstractPersistable;
import javax.persistance.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Book extends AbstractPersistable<Long> {
  
  // @Id
  // @GeneragedValue
  // private Long id; // id는 AbstractPersistable<>에 선언되어있기 때문에 선언할 필요가 없다.
  
  private String name;
  private String isbn13;
  private String isbn10;
  
  //빌더 패턴 생략
}
~~~

### 2. BookRepository 생성
~~~java
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository 이것은 필요없다. SimpleJpaRepository가 가지고 있다. 때문에 DataJpa가 우리가 가지고 있는 Jpa를 구현체로 바꾸면서 SimpleJpaRepository로 프록시처리를 해주기 때문에 @Repository 선언을 하지 않아도 된다.
public interface BookRepository extends JpaRepository<Book, Long> {
  
  // 이것으로 Book 엔티티를 다루는 repository 작성 끝
  
  List<Book> findByNameLike(String name);
}
~~~

### 3. BookRepositoryTest 생성
~~~java
@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {

  @Autowired
  BookRepository repository; //이렇게하면 이 테스트가 실행될 때 BookRepository가 필드에 자동으로 주입된다.
  
  @Test
  public void testSave() { //이 메소드에서 우클릭하고 RunAs -> JUnit Test 실행
    
      Book book = new Book();
      book.setName("boot-spring-boot");
      book.setIsbn10("0123456789");
      book.setIsbn13("012345678912");

      // isNew()는 아직 영속화되지 않은(하이버네이트 엔티티 매니저가 관리하고있지 않은) 것을 확인할 때 많이 사용
      assertThat(book.isNew()).isTrue(); //id값을 가지고있지 않은 새로운 객체라는 것을 테스트

      repository.save(book);

      assertThat(book.isNew()).isFalse(); //위에서 추가했기 때문에 이제는 새로운 객체가 아님
    
    
  @Test
  public void testFindByNameLike() {
      
      Book book = new Book();
      book.setName("boot-spring-boot");
      book.setIsbn10("0123456789");
      book.setIsbn13("012345678912");
      
      repository.save(book);
      
      List<Book> books = repository.findByNameLike("boot");
      assertThat(books).isNotEmpty(); // boot가 들어간 내용이 있으므로 isNotEmpty가 통과
      
      books = repository.findByNameLike("book");
      assertThat(books).isEmpty(); // book으로 저장하지 않았기 때문에 empty가 통과
  
}  
~~~

### @Service
- 트랜잭션(@Transactional) 관리영역 (exception 발생시 데이터 rollback)
- 서로 다른 도메인 연계(DI, @Autowired)작업 영역
- @Controller와 @Repository 사이의 중계


### 4. BookService 생성
>`BookService를 클래스로 생성하냐 vs BookService를 인터페이스로 생성하여 메소드를 작성하고 BookServiceImpl 구현체를 만드냐`의 논쟁이 있음.  
>정답이 없으나 강사는 후자 선호

~~~java
public interface BookService {
    
    Optional<Book> findById(Long Id);
}
~~~


~~~java
@Service  
@Transactional //이 서비스 안의 메소드가 호출될 때 트랜잭션을 관리하겠다. //조회만 할때는 @Transactional 필요없음
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository; //생성자 주입방식으로 쓰기 위해
    
    public BookServiceImpl(BookRepository bookRepository) { // 생성자 주입방식으로 BookRepository를 빈으로 등록
        this.bookRepository = bookRepository;
    }
    
    //아래 2개의 오버라이드는 BookService에 선언된 메서드 
    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }
    
    @Override
    public List<Book> findAll(OffSetPageRequest request) {
        return bookRepository.findAll(request.getPageRequest()).getContent();
    }
}
~~~

### 5. BookServiceTest 생성

~~~java
@RunWith(SpringRunner.class) //@RunWith는 테스트가 실행될 환경을 선언
@SpringBootTest(webEnvironment=WebEnvironment.NONE) //서비스계층은 웹어플리케이션 컨텍스트까지 띄울 필요가 없어서 None으로 지정
public class BookServiceTest {

    @Autowired
    BookService bookService;
    
    @Test(expectd=RuntimeException.class) //이 테스트에서 런타임에러가 생길 것이라 가정하고 테스트.(그럼 예외 발생해도 테스트 통과)
    public void testFindById() {
        Long id = 1L;
        bookService.findById(id)
                      .orElseThrow() -> new RuntimeException("Not found")); //id값을 찾지 못하면 예외를 던지겠다.
~~~

### @Controller
- DispatcherServlet에 등록된 @RequestMapping 호출됨
- 템플릿 엔진이 렌더링할 view 페이지를 지정
- 호출된 API에서 처리한 응답을 반환

### @Controller 예외처리
- @ControllerAdvice를 이용한 처리

~~~java

//@RestController 선언한 컨트롤러에서 예외 발생했을 때 GlobalRestControllerAdvice 클래스가 전담

@ControllerAdvice(annotations = {RestController.class})
@ResponseBody
public class GlobalRestControllerAdvice {

    @ExceptionHandler(BookNotFoundException.class)
    public ApiResponse<Void> handleException(Exception e) { 
        Log.error("Occurred Exception: {}", e);
        return ApiResponse.error(e.getMessage());
~~~


### 6. BookController 생성

~~~java
@RestController
@RequestMapping("/books")
public class BookController {
    
    // Controller에서는 서비스를 여러 개 사용하는 경우가 많아서
    // 생성자 주입 방식보다는 @Autowired 로 스프링빈을 주입받는 방식을 많이 사용한다.
    @Autowired
    BookService bookService;
    
          // 생성자 주입 방식
          //private final BookService service;
          //public BookController(BookService service) {
          //    this.service = service;
          //}
    
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> findAll(Long bookId) {
        Book book = bookService.findById(bookId)
                    .orElseThrow(() -> new RuntimeException("Not found: "+ bookId));
       
        return ResponseEntity.ok(book); //요청에 따라 상태값을 줌
    }
}
~~~

## REST API
- 시스템의 자원(Resource)에 대한 접근 및 제어를 제공하는 API
- 자원(ex: book)에 대한 접근 및 제어
  - GET /books
  - GET /books/{books}
  - POST /books
  - PUT /books/{bookId}
  - DELETE /books/{booksId}
- 스프링에서는 요청에 따라 등록되어 있는 적절한 HttpMessageConverter를 통해서 응답데이터를 반환한다.

## Spring REST DOCs
>컨트롤러와 관련된 테스트코드를 작성하면서 API문서를 만드는 기능
- https://spring.io/projects/spring-restdocs
- Spring MVC test와 Asciidoctor 조합을 통해서 RESTful 서비스에 대한 문서화 지원
- 작성된 테스트코드에 대한 아스키독 조각 생성
- 개발자가 아스키독 조각을 모아 `아스키독 문서`를 작성한다.
- 코드에 침투적이지 않은 노력에 따라 고품질의 코드가 될 수 있음

## @Profile
>@Profile 어노테이션을 이용해서 어플리케이션이 실행되는 환경에 따라서 bean을 등록하거나 제외시킬 수 있다.

### 예시 1 - 클래스에 선언
~~~java
@Profile("local") //local 프로파일이 활성화 되었을 때만 이 구성이 활성화 된다.
@Configuration
public class LocalApiConfig {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    
    @Bean
    public RestTemplate restTemplate() {
        return restTemplateBuilder.build();
    }
}
~~~

### 예시 2 - 메소드에 선언
~~~java
@Configuration
public class LocalApiConfig {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    
    @Profile("local")
    @Bean
    public RestTemplate restTemplate() {
        return restTemplateBuilder.build();
    }
    
    @Profile("!local")
    @Bean
    public RestTemplate restTemplate() {
        return restTemplateBuilder.rootUri("http://test.honeymon.io").build();
    }
}
~~~

## @Profile - application-{profile}.yml
- application-datasource.yml = @Profile("datasource")
- application-api.yml = @Profile("api")
- application.yml


## 스프링부트 어플리케이션 속성 정의
1. 테스트 속성정의
    ~~~java
    @RunWith(SpringRunner.class)
    @SpringBootTest(webEnvironment = DEFINED_PORT, properties = {"server.port=9090"})
    public class BookControllerTest {}
    ~~~
      
2. 실행인자 지정
    ~~~
    $ java -jar spring-boot.0.0.1SNAPSHOT.jar \
      --server.port=9000
    ~~~
    
3. 운영체제 환경변수 지정
    ~~~
    $ SERVER_PORT=9000 \
      SPRING_PROFILES_ACTIVE=local \
      java -jar api-0.0.1-SNAPSHOT.jar
    ~~~
    
4. 속성파일(application.yml or application.properties) 지정
  - @EnableConfigurationProperties
  - @ConfigurationProperties
  
5. 프로그래밍적 코드 구현


# bookstore24 프로젝트 구현해보기

## 프로젝트 모듈 구성
- common : 프로젝트에서 공통으로 사용하는 유틸리티 (예외 등)
- core : 프로젝트 도메인(@Entity, @Repository)
- api : 외부에 정보를 제공하는 REST API 모듈
- admin : 서비스를 관리하기 위한 백오피스
- batch : 정기적으로 실행될 배치 프로그램 모음
- message : 알림톡, SMS, 메일 발송 등 담당

## gitignore.io
>빌드가 되고나면 바이트코드(.class 파일)로 변환된 것들은 버전 관리가 필요없다.

## 프로파일 구성
- local : 개발자 로컬 실행환경
  - 개발자가 자유롭게 초기화 및 구성을 수행할 수 있다.
- test : 통합테스트 환경(주로 빌드 전 실행된다)
  - 테스트 실행때마다 초기화된다.
- dev : 개발서버 실행환경
  - 운영서버와 동일한 환경을 가지며 개발 기능을 확인하는 용도로 사용된다.
- beta : (준)운영서버 실행환경
  - 운영서버와 동일한 환경으로 큰 배포에 앞서서 운영서버의 데이터를 복제하여 정상동작확인
- prod :  운영서버 실행환경
  - 가급적 손대지 않아야 할 환경
  
### application-api.yml

~~~yml
google:
  map:
    api-key: 00000-0000-00000

--- //문서 구분자(---)
spring.profile: dev
google:
  map:
    api-key: 12341-1234-12345
    
---
spring.profile: beta
google:
  map:
    api-key: 12341-1234-41321

---
...
~~~

### application-datasource.yml

~~~yml

~~~
