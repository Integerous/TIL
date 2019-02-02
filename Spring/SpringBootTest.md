# Spring Boot Test 정리

# 1. @SpringBootTest
>통합 테스트를 제공

- 실제 구동되는 어플리케이션과 똑같이 ApplicationContext를 로드하여 테스트
  - 하고 싶은 테스트를 모두 수행 가능
  - 어플리케이션에 설정된 bean을 모두 로드하기 때문에 규모가 클수록 느리다. (단위 테스트가 무의미해짐)
- 어플리케이션이 실행될 때의 설정을 임의로 바꾸어 테스트 진행 가능
- 여러 단위 테스트를 하나의 통합된 테스트로 수행할 때 적합
- 메인클래스와 함께 기본 제공

~~~java
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void contextLoads() {
	}

}
~~~

## 1.1 @RunWith
- JUnit에 내장된 Runner를 사용하는 대신 어노테이션에 정의된 Runner 클래스 사용
- @SpringBootTest 를 사용하려면 JUnit 실행에 필요한 SpringJUnit4ClassRunner 클래스를 상속받은 @RunWith(SpringRunner.class)를 붙여야 한다.

## 1.2. @SpringBootTest 의 파라미터들
***value*** : 테스트가 실행되기 전에 적용할 프로퍼티 주입.(기존의 프로퍼티 오버라이드)  
***properties*** : 테스트가 실행되기 전에 {key=value} 형식으로 프로퍼티 추가.  
***classes*** : ApplicationContext에 로드할 클래스 지정. (지정하지 않으면 @SpringBootConfiguration을 찾아서 로드)  
***webEnvironment*** : 어플리케이션이 실행될 때의 웹 환경을 설정. (기본값은 Mock 서블릿을 로드하여 구동)

~~~java
// value와 properties는 함께 사용할 수 없으므로 에러 발생
@RunWith(SpringRunner.class)
@SpringBootTest(value = "value=test"
        , properties = {"property.value=propertyTest"}
        , classes = {DemoApplicationTests.class}
        , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoApplicationTests {

    @Value("${value}")
    private String value;

    @Value("${property.value}")
    private String propertyValue;

    @Test
    public void contextLoads() {
        assertThat(value, is("test"));
        assertThat(propertyValue, is("propertyTest"));
    }
}
~~~

## 1.3. 사용 팁
- 프로파일(개발, QA, 운영) 마다 다른 DataSource를 갖는 경우, `@ActiveProfiles("local")` 을 사용
- `@Transactional`을 사용하면 테스트를 마치고 나서 수정된 데이터가 롤백된다.
- @SpringBootTest는 기본적으로 @SpringBootApplication 이나 @SpringBootConfiguration 을 찾는다. (둘 중 하나는 필수)

# 2. @WebMvcTest
>MVC를 위한 테스트

- 웹에서 테스트하기 힘든 Controller를 테스트하는데 적합
- 웹상에서 요청과 응답에 대해 테스트할 수 있다.
- Security와 Filter까지 자동으로 테스트하며, 수동으로 추가/삭제 가능
- @WebMvcTest 를 사용하면 MVC 관련 설정들만 로드되기 때문에 가볍게 테스트 가능
	- MVC 관련 설정: `@Controller`, `@ControllerAdvice`, `@JsonComponent`, `Filter`, `WebMvcConfigurer`, `HandlerMethodArgumentResolver`

## 2.1. 예시
>BookController에서 책 리스트를 받아오는 테스트

### 2.1.1.Book 클래스 생성
~~~java
@NoArgsConstructor
@Getter
public class Book {

    private Integer idx;
    private String title;
    private LocalDateTime publishedAt;

    @Builder
    public Book(String title, LocalDateTime publishedAt) {
        this.title = title;
        this.publishedAt = publishedAt;
    }
}
~~~

### 2.1.2. BookController 클래스 생성
~~~java
@Controller
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    @GetMapping("/books")
    public String getBookList(Model model) {
        model.addAttribute("bookList", bookService.getBookList());
        return "book";
    }
}
~~~

### 2.1.3. BookService 인터페이스 생성
이 인터페이스를 구현하는 구현체는 만들지 않고, Mock 데이터를 이용하여 테스트 진행
~~~java
public interface BookService {
    List<Book> getBookList();
}
~~~

### 2.1.4. BookControllerTest 생성

~~~java
@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @Test
    public void Book_MVC_TEST() throws Exception {
        Book book = new Book("Spring Boot Book", LocalDateTime.now());
        given(bookService.getBookList()).willReturn(Collections.singletonList(book));

        mvc.perform(get("/books"))
                .andExpect(status().isOk()) // HTTP 상태값이 200인지 테스트
                .andExpect(view().name("book")) // 반환되는 view의 이름이 book인지 테스트
                .andExpect(model().attributeExists("bookList")) // Model의 프로퍼티 중 bookList라는 프로퍼티가 존재하는지 테스트
                .andExpect(model().attribute("bookList", contains(book))); // Model의 프로퍼티 중 bookList 프로퍼티에 book 객체가 담겨져 있는지 테스트
    }
}
~~~

- @WebMvcTest를 사용하기 위해서는 테스트할 컨트롤러 이름을 명시해야 한다. (여기서는 BookContoller)
- MockMvc는 모든 의존성을 로드하지 않고 BookContoller와 관련된 bean만 로드한다.
	- 여기서는 MockMvc를 주입시켰기 때문에 전체 HTTP서버를 실행하지 않고 테스트 가능
- BookService를 구현한 구현체는 없지만 @MockBean으로 BookService를 가짜객체로 대체함
- @DataJpaTest 는 JPA 테스트가 끝날 때 마다 자동으로 사용된 데이터를 롤백
- EntityManager의 대체재로 만들어진 테스트용 TestEntityManager를 사용하여 `persist`, `flush`, `find` 등의 기본적인 JPA 테스트 가능

# 3. @DataJpaTest
>JPA 관련 테스트 설정만 로드

- DataSource의 설정이 정상적인지 테스트
- JPA를 사용하여 데이터를 제대로 생성, 수정, 삭제하는지 테스트
- 인메모리 임베디드 데이터베이스 사용
	- 메인 메모리를 데이터 저장소로 하여 DB를 어플리케이션에 내장하여 운용하는 DB
- @Entity 클래스를 스캔하여 Spring Data JPA Repositories를 구성

## 3.1. 별도의 DataSource 사용하기
>기본 설정된 DataSource를 사용하지 않도록 아래와 같이 설정

~~~java
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("...")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JpaTest {
	...
}
~~~

또는 application.yml 파일을 아래와 같이 수정

~~~yml
spring.test.database.replace: NONE
~~~

## 3.2. 테스트 데이터베이스 선택

### 3.2.1. 프로퍼티 설정
~~~yml
spring.test.database.connection: H2
~~~

### 3.2.2. 어노테이션 설정
~~~java
@AutoConfigureTestDatabase(connection = H2)
...
~~~

## 3.3. 예시

### 3.3.1. Book 클래스에 JPA 관련 어노테이션 추가
~~~java
@NoArgsConstructor
@Getter
@Entity
@Table
public class Book {

    @Id
    @GeneratedValue 
    private Integer idx;
    
    @Column
    private String title;
    
    @Column
    private LocalDateTime publishedAt;

    @Builder
    public Book(String title, LocalDateTime publishedAt) {
        this.title = title;
        this.publishedAt = publishedAt;
    }
}
~~~

### 3.3.2. BookRepository 생성
~~~java
public interface BookRepository extends JpaRepository<Book, Integer> {
}
~~~

### 3.3.3. @DataJpaTest로 테스트 수행하기
~~~java
@RunWith(SpringRunner.class)
@DataJpaTest
public class BookJpaTest {

    private final static String BOOT_TEST_TITLE = "Spring Boot Test Book";

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void BookList_Save_Test() {
        Book book = Book.builder().title(BOOT_TEST_TITLE)
                        .publishedAt(LocalDateTime.now()).build();

        testEntityManager.persist(book);
        assertThat(bookRepository.getOne(book.getIdx()), is(book));
    }

    @Test
    public void BookList_Save_Search_Test() {
        Book book1 = Book.builder().title(BOOT_TEST_TITLE + "1")
                         .publishedAt(LocalDateTime.now()).build();
        testEntityManager.persist(book1);

        Book book2 = Book.builder().title(BOOT_TEST_TITLE + "2")
                         .publishedAt(LocalDateTime.now()).build();
        testEntityManager.persist(book2);

        Book book3 = Book.builder().title(BOOT_TEST_TITLE + "3")
                         .publishedAt(LocalDateTime.now()).build();
        testEntityManager.persist(book3);

        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList, hasSize(3));
        assertThat(bookList, contains(book1, book2, book3));
    }

    @Test
    public void BookList_Save_Delete_Test() {
        Book book1 = Book.builder().title(BOOT_TEST_TITLE + "1")
                         .publishedAt(LocalDateTime.now()).build();
        testEntityManager.persist(book1);

        Book book2 = Book.builder().title(BOOT_TEST_TITLE + "2")
                         .publishedAt(LocalDateTime.now()).build();
        testEntityManager.persist(book2);

        bookRepository.deleteAll();
        assertThat(bookRepository.findAll(), IsEmptyCollection.empty());
    }
	
}
~~~

# 4. @RestClientTest
>REST 통신의 데이터형으로 사용되는 JSON 형식이 예상대로 응답을 반환하는지 등을 테스트

## 4.1. REST 테스트를 위한 BookRestController
~~~java
@RestController
public class BookRestController {

    @Autowired
    private BookRestService bookRestService;

    @GetMapping(path = "/rest/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public Book getRestBooks() {
        return bookRestService.getRestBook();
    }
}
~~~

`getRestBook()` 메서드의 반환값은 Book 객체이지만 @RestController로 설정되어 있으면 JSON 형식의 String형으로 반환된다.

## 4.2. REST 테스트용 BookRestService 생성
~~~java
@Service
public class BookRestService {

    private final RestTemplate restTemplate;

    public BookRestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.rootUri("/rest/test").build();
    }

    public Book getRestBook() {
        return this.restTemplate.getForObject("/rest/test", Book.class);
    }
}
~~~

## 4.3. @RestClientTest 를 사용한 REST 테스트 코드
~~~java
@RunWith(SpringRunner.class)
@RestClientTest(BookRestService.class)
public class BookRestTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private BookRestService bookRestService;

    @Autowired
    private MockRestServiceServer server;

    @Test
    public void rest_test() {
        this.server.expect(requestTo("/rest/test"))
                .andRespond(withSuccess(new ClassPathResource("/test.json", getClass())
                        , MediaType.APPLICATION_JSON));

        Book book = this.bookRestService.getRestBook();
        assertThat(book.getTitle()).isEqualTo("테스트");
    }

    @Test
    public void rest_error_test() {
        this.server.expect(requestTo("/rest/test"))
                .andRespond(withServerError());
        this.thrown.expect(HttpServerErrorException.class);
        this.bookRestService.getRestBook();
    }
}
~~~

## 4.4. 테스트 코드에 필요한 리소스 파일
~~~json
{"idx":null,"title":"테스트","publishedAt":null}
~~~

# 5. @JsonTest
>JSON의 직렬화(Serialization)와 역직렬화(Deserialization)를 수행하는 라이브러리인 Gson과 Jackson API의 테스트를 제공

JSON 테스트는 두 가지로 나뉜다. 문자열로 나열된 JSON 데이터를 객체로 변환하여 변환된 객체값을 테스트하거나 그 반대.

## 5.1. 예시
~~~java
@RunWith(SpringRunner.class)
@JsonTest
public class BookJsonTest {

    @Autowired
    private JacksonTester<Book> json;

    @Test
    public void json_test() throws Exception {
        Book book = Book.builder()
                        .title("테스트")
                        .build();

        String content = "{\"title\":\"테스트\"}";

        assertThat(this.json.parseObject(content).getTitle()).isEqualTo(book.getTitle());
        assertThat(this.json.parseObject(content).getPublishedAt()).isNull();
        assertThat(this.json.write(book)).isEqualToJson("/test.json");
        assertThat(this.json.write(book)).hasJsonPathStringValue("title");
        assertThat(this.json.write(book)).extractingJsonPathStringValue("title").isEqualTo("테스트");
    }
}
~~~
## *Reference
- <처음 배우는 스프링부트 2> [김영재 저](https://github.com/young891221)
