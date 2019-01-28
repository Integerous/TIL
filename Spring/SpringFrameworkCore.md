# <스프링 프레임워크 핵심 기술> 강의 내용 정리
>[강의 링크](https://www.inflearn.com/course/spring-framework_core/)

> 작성중

---

# ApplicationContext와 다양한 bean 설정 방법

## 1. 고전적인 방법으로 Spring bean 설정 파일 만들고, bean 주입하기

### 1.1. application.xml
~~~java
<? xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans">
       
  <bean id="bookService"
        class="me.whiteship.springapplicationcontext.BookService"> //1.여기까지만 하면 BookService bean 만들고 끝.
          //2.그러므로 아래와 같이 property를 생성해서 주입해줘야 한다.
        <property name="bookRepository" ref="bookRepository" />
          //3. name은 setter에서 가져온 것이고, ref는 레퍼런스로 다른 bean을 참조한다는 것
          //4. 그러므로 ref에는 name(setter)에 들어갈 수 있는 다른 bean의 id가 와야한다.
  <bean>
  
  <bean id="bookRepository"
         class="me.whiteship.springapplicationcontext.BookService"/>
~~~

위 처럼 bean 설정을 하면, 아래와 같이 ApplicationContext를 만들어서 bean 설정을 사용하면 된다.

### 1.2. DemoApplication.java

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

## 2. Component Scan로 Spring bean 설정파일 만들고, Annotation으로 bean 주입하기

### 2.1. application.xml

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

### 2.2. BookRepository

~~~java
@Repository
public class BookRepository {
}
~~~

### 2.3. BookService

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

## 3. xml파일 대신 java파일로 bean 설정하기

### 3.1. Application.config
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

### 3.2. DemoApplication.java

~~~java
public class DemoApplication {

  public static void main(String[] args) {
    //ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
    ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
    // 1.ApplicationConfig 클래스를 bean 설정으로 사용하겠다는 것.
    // 2.ApplicationConfig에서 @Bean이 달린 bean 정의들을 읽어서 bean들로 등록하고, 코드를 작성한 대로 의존성을 주입해준다.
~~~

하지만, 이 방식의 경우에도 @Bean을 하나씩 정의해야 하므로 불편하다.
그래서 application.xml에서 처럼 Component scan을 이용하는 방법이 있다.

## 4. @ComponentScan 애너테이션을 활용한 bean 설정과 의존성 주입

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

# @Autowired

## 1. 생성자를 통한 의존성 주입
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

## 2. Setter를 통한 의존성 주입
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

## 3. 필드를 통한 의존성 주입
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

## 4. 해당 타입의 빈이 여러 개인 경우

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
