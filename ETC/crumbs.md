# :bread: 빵 부스러기

>개발 관련 학습 중 하나의 글로 작성하기엔 짧고,  
버리기엔 아까운 부스러기 정보들을 모아두는 곳.
</br>

## 1. 클래스라는 개념이 등장한 기본적인 이유
예를 들어, 전화번호부에서 한 사람의 이름과 전화번호는 항상 붙어다녀야 하는 데이터이다.  
그런데 이 데이터를 별개의 변수에 저장하면, 이름 데이터를 옮길 때 마다 전화번호 데이터도 따로 옮겨줘야 한다.  
만약 이름과 전화번호 외에 더 많은 데이터를 저장해야 한다면, 더 불편해질 것이다.  
그래서 **서로 관련있는 데이터들을 하나의 단위로 묶어두기 위해** 등장한 것이 클래스라는 개념이다.
- Reference - [Java로 배우는 자료구조 - 권오흠](https://www.inflearn.com/course/java-%EC%9E%90%EB%A3%8C%EA%B5%AC%EC%A1%B0/%EB%AC%B8%EC%9E%90%EC%97%B4-%EB%8B%A4%EB%A3%A8%EA%B8%B0-3/)

-----
</br>

## 2. Primitive Type과 Class의 차이점
Class도 하나의 Type인데, int 혹은 double 형 변수를 선언하고 사용하는 것 처럼 User 변수를 선언하고 사용한다.
  - int count = 0;
  - User user = new User();
  
이 때, count라는 이름의 변수에는 그 안에 정수값 0이 저장되지만,  
user라는 이름의 변수는 그 안에 사용자(User)의 정보가 저장되지 않는다.  
사용자의 정보를 저장할 User객체(object)는 new 명령으로 따로 만들고,  
변수 user에는 따로 만든 User 객체의 주소(참조)를 저장하는 것이다.

Primitive 타입이 아닌 모든 변수는 참조 변수다.
- Reference - [Java로 배우는 자료구조 - 권오흠](https://www.inflearn.com/course/java-%EC%9E%90%EB%A3%8C%EA%B5%AC%EC%A1%B0/%EB%AC%B8%EC%9E%90%EC%97%B4-%EB%8B%A4%EB%A3%A8%EA%B8%B0-3/)

-----
</br>

## 3. URL 유효성 체크시, 해당 리소스에서 HTTP HEAD 메소드를 막아 둔 경우

URL을 입력하면 해당 컨텐츠의 이미지와 제목을 파싱하는 프로그램 개발 중,  
아래와 같이 사용자가 입력한 URL의 유효성을 체크하는 코드가 있었다.

~~~java
...

URL url = new URL(inputUrl);

HttpURLConnection huc = (HttpURLConnection) url.openConnection();
huc.setRequestMethod("HEAD");
huc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
        "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
huc.connect();

if(huc.getResponseCode() == HttpURLConnection.HTTP_OK) {
  // 통과
}else
  // 유효하지 않은 URL
~~~ 

위의 코드는 URL이 해당 resource로 제대로 접속되는지 체크하기 위해 HttpURLConnection를 사용했고,  
Http 응답코드가 200 OK를 반환하는지만 빠르게 확인하기 위해 HEAD 메소드로 접속을 시도했다.  

여러 테스트 케이스에서 잘 동작하는듯 보였지만,  
정상적인 URL인 https://meetup.toast.com/posts/86 로 테스트 한 결과,  
접속을 시도해도 403 Forbidden 을 반환하며 접속이 실패했다. 그리고 모든 meetup.toast의 게시물들이 403 Forbidden을 반환했다.

알아 본 결과, **보안상의 이유로 GET메소드나 POST 메소드만 오픈해두는 경우가 있다**는 사실을 알게 되었다.  
그래서 HEAD 메소드로 접속을 시도했을 때, 응답코드로 403 Forbidden을 반환하는 경우  
GET 메소드로 다시 접속을 시도하게끔 코드를 수정했고,  정상적인 결과를 얻을 수 있었다.

-----
</br>

## 4. 다형성(Polymorphism)과 동적 바인딩(Dynamic binding)
다형성을 한마디로 정의하면 **'수퍼클래스 타입의 변수가 서브클래스 타입의 객체를 참조할 수 있다.'** 이다.

~~~java
Computer theComputer = new Notebook(...)
~~~

위의 코드에서 처럼 수퍼클래스인 Computer 타입의 참조변수가 서브클래스인 Notebook 객체를 참조할 수 있는 것이다.

그런데 만약 Computer 클래스의 toString() 메소드가 있고,  
그것을 오버라이딩한 toString() 메소드가 서브클래스인 Notebook 클래스에 있다면,  
아래 코드에서 test.toString()의 toString() 메소드는 어느 메소드일까?

~~~java
public class Notebook extends Computer  {
    
    ...
    
    public String toString() {
        ...
    }
    
    public static void main(String[] args) {
        Computer test = new Notebook(...);
        System.out.println( test.toString() );
    }
~~~

***Static binding*** 의 경우, Computer 객체의 toString() 메소드이고,  
***Dynamic binding*** 의 경우, Notebook 객체의 toString() 메소드이다.  

Static binding은 컴파일러가 어떤 메소드일지 결정하는 것이고,  
Dynamic binding은 런타임에 해당 코드를 실행할 때 어떤 메소드인지 결정하는 방식이다.  

>그리고, **Java는 항상 동적 바인딩(Dynamic binding)을 한다.**  

즉, 위의 코드에서 `test.toString()` 은 Notebook 객체의 toString() 메소드가 호출된다.

- Reference - [Java로 배우는 자료구조 - 권오흠](https://www.inflearn.com/course/java-%EC%9E%90%EB%A3%8C%EA%B5%AC%EC%A1%B0/lecture/7458)

-----
</br>

## 5. Type Parameter와 new 연산자

~~~java
public void addFirst( T item) {

    Node<T> newNode = new Node<T>(item); // possible.
                                         // T = Type Parameter
                                         // T를 Type Parameter로 가지는 객체를 new 연산자로 생성할 수 있다. 하지만,

    T t = new T(); // impossible.
                   // Type Parameter는 가상 클래스이므로 T 타입의 객체를 new 연산자로 생성할 수 없고,
                   
    T[] array = new T[100]; // impossible.
                            // T타입의 배열도 생성할 수 없으며,
                            
    Node<T>[] arr = new Node<T>[100]; // impossible.
                                      // Type Parameter T를 가지는 배열을 new 연산자로 생성할 수도 없다.
}
~~~
    
-----
</br>

## 6. Spring에서 생성자 주입 방식이 권장되는 이유
: ***필수적으로 사용해야하는 레퍼런스 없이는 인스턴스를 만들지 못하도록 강제할 수 있다.***  

예를 들어, 아래 코드에서 OwnerController 클래스는 ownerRepository 없이는 제대로 동작할 수 없다.

~~~java
@Controller
class OwnerController {

    private OwnerRepository ownerRepository;
    
    public OwnerController(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }
~~~
}

하지만 @Autowired를 사용하는 Field injection 이나 Setter injection은  
ownerRepository 없이도 OwnerController 인스턴스를 만들 수 있다.  

이는 단점이 될 수도 있고, 장점이 될 수 있다.  
예를 들어 순환참조가 일어나면 생성자 주입방식은 양쪽 다 인스턴스를 생성할 수 없게 되지만,  
Field injection이나 Setter injection은 순환참조와 상관 없이 동작할 수 있다.  
하지만 순환참조가 일어나지 않게끔 코드를 작성하는 것이 바람직하다.

- Reference - [예제로 배우는 스프링 입문 8. 의존성 주입](https://www.youtube.com/watch?v=IVzYerodIyg&list=PLfI752FpVCS8_5t29DWnsrL9NudvKDAKY&index=8)

-----
</br>

## 7. Annotation을 이용한 간단한 Spring AOP 사용 예시

메서드의 실행시간을 측정하는 기능이 여러 메서드에 필요하다고 가정해보자.  
이 기능이 필요한 모든 메서드에 같은 코드를 붙이는 것은 문제가 많으므로 AOP를 적용할 수 있다.

우선, AOP를 적용할 메서드들에 Annotation을 붙인다. (아직 만들어지지 않은 Annotation)

~~~java
@LogExecutionTime
public String someMethod1() {
    ...
    return something1;
}

@LogExecutionTime
public String someMethod2() {
    ...
    return something2;
}
~~~

그리고 Annotation(@LogExecutionTime)을 아래와 같이 생성한다.  

~~~java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExecutionTime {
}
~~~

`@Target` 으로 `어디에 사용할 지` 설정.  
`@Retention` 으로 어노테이션 정보를 `언제까지 유지할 것인지` 설정.

이제 어노테이션이 붙은 메서드에 어떤 기능을 넣을지 작성한다.

~~~java
@Component
@Aspect
public class LogAspect {
    
    Logger logger = LoggerFactory.getLogger(LogAspect.class);
    
    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        Object proceed = joinPoint.proceed();
        
        stopWatch.stop();
        logger.info(stopWatch.prettyPrint());
        
        return proceed;
    }
}
~~~

위의 코드에서 `joinPoint`가 우리가 생성한 @LogExecutionTime 어노테이션이 붙은 메서드이다.  
그러므로 `joinPoint.proceed();` 부분에서 메서드를 실행하고 그 결과를 반환하되,  
메서드 실행 시간을 측정하는 코드를 메서드 위아래로 붙여주는 것이다.

위 코드의 경우, @LogExecutionTime 이 붙은 메서드들이 실행되면 각 실행시간이 콘솔에 찍힌다.  
Spring 내부에서 프록시 패턴을 사용하는 방식인데, 어떻게 적용되는 것인지는 토비의 스프링3에 자세히 나와있다.

- Reference - [예제로 배우는 스프링 입문, 11 스프링 @AOP](https://www.youtube.com/watch?v=3750wh1wNuY&list=PLfI752FpVCS8_5t29DWnsrL9NudvKDAKY&index=11)

-----
</br>

## 8. [오늘은 더 이상 보지 않기] 버튼 구현

공고를 팝업으로 띄워야하는 업무가 있었다.  
마크업을 보니 `하루 동안 열지 않습니다. [ ]` 버튼을 누르면 오늘 하루는 더 이상 팝업이 나타나지 않도록 구현해야 했다.

기존의 팝업창 로직을 보니
`$.cookie`라는 함수를 사용하여 쿠키값을 변경하는 방법으로 구현했다.  
하지만 `$.cookie is not a function` 이라는 오류를 맞이했는데,  
[이 답변](https://stackoverflow.com/questions/18024539/jquery-cookie-is-not-a-function)에서 알 수 있듯이 $.cookie는 일반적인 jQuery의 함수가 아니라 다운받아야 하는 플러그인이었다.

`jquery.cookie.js` 파일을 추가하고,  
팝업창의 스타일 속성에 `display:none` 을 추가한 후에, 아래와 같은 코드로 팝업창을 제어했다.  

~~~js
<script type="text/javascript">
   
    (function() {
        if($.cookie('merge_notice_popup') !== "true") {
            $('#home_layer_wrap').show();
        }

	var bCheckedNeverOpen = false;
	$('#check').click(function(){
		bCheckedNeverOpen = !bCheckedNeverOpen;
	});
	
	$('.btn_close').click(function(){
            if (bCheckedNeverOpen) {
                $.cookie('merge_notice_popup', 'true', { expires: 1, path: '/' });
                $.cookie('merge_notice_popup', 'true', { expires: 1, path: '/index' });
                $.cookie('merge_notice_popup', 'true', { expires: 1, path: '/Index' });
            }
            $('#home_layer_wrap').hide();
			return false;
	});
    })();	
</script>
~~~

우선, 즉시 실행 함수 `(function(){...})();`로 구현하여 해당 페이지가 호출되면,  
즉시 실행되어 쿠키 유무를 확인하여 팝업창을 보이거나 숨긴다.

그리고 `하루 동안 열지 않습니다.[]` 버튼에 체크하면 boolean 변수를 true로 바꾸고,  
체크된 상태로 닫기 버튼이 클릭되었을 때 `merge_notice_popup`이라는 key와 `true`라는 값, 그리고 만료 시간과 경로를 설정하여 쿠키를 생성한다.

즉, `merge_notice_popup`이라는 쿠키가 생성되면,  
이후 쿠키가 생성된 해당 경로에 접근했을 때 쿠키의 유무에 따라 팝업창이 보이거나 보이지 않는 것이다.


-----
</br>

## 9. Try-with-resources를 이용한 자원해제 처리

> 팀원분에게 코드 리뷰를 받던 중 Try-with-resources를 알게 되어 정리

~~~java
public static String getHtml(String url) {

    try{
		URL targetUrl = new URL(url);
		BufferedReader reader = new BufferedReader(new InputStreamReader(targetUrl.openStream()));
		StringBuffer html = new StringBuffer();
		String tmp = "";

		while ((tmp = reader.readLine()) != null) {
		html.append(tmp);
		}
		reader.close(); // 이 부분 전에 예외가 발생하면 BufferedReader를 닫지 못하고 catch문으로 빠지는 문제
		return html.toString();

		} catch (MalformedURLException e) {
			reader.close(); // 초기에는 추가하지 않았던 코드
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			reader.close(); // 초기에는 추가하지 않았던 코드
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			reader.close(); // 초기에는 추가하지 않았던 코드
			e.printStackTrace();
			throw e;
		}
~~~

공공데이터 API를 사용하기 위해 작성한 나의 ***똥코드***는 보기만해도 지저분했는데,  
지저분한 것은 차치하고, `reader.close()`에 도달하기 전에 예외가 발생할 경우를 대비하지 않은 코드였다.  

그래서 위의 코드와 같이 `reader.close()`를 모든 catch문에 추가하거나,  
아래의 예시와 같이 finally문을 만들어서 처리했어야 했다.

~~~java
SomeResource resource = null;
try {
    resource = getResource();
    use(resource);
} catch(...) {
    ...
} finally {
    if (resource != null) {
        try { resource.close(); } catch(...) { /* 아무것도 안 함 */ }
    }
}
~~~

나의 ***똥코드***를 보신 팀원분께서 JDK7 부터 추가된 `try-with-resources`를 설명해주셨다.

### `Try-with-resources`란?

~~~
try (SomeResource resource = getResource()) {
    use(resource);
} catch(...) {
    ...
}
~~~

위와 같이 try에 자원 객체를 전달하면,  
finally 블록으로 종료 처리를 하지 않아도 try 코드 블록이 끝나면 자동으로 자원을 종료해주는 기능이다.  

이 때, `try( 여기 ){...}` 여기에는 `AutoCloseable` 인터페이스의 구현체만 들어갈 수 있으며,  
`AutoCloseable`은 JDK1.7부터 추가된 인터페이스다.  

~~~java
/**
 * @author Josh Bloch
 * @since 1.7
 */
public interface AutoCloseable {
    void close() throws Exception;
}
~~~

내가 작성한 코드에서는 `BufferedReader`와 `InputStreamReader`클래스가 추상클래스 `Reader`를 상속받았고,  
`Reader`는 `Closeable` 인터페이스를 상속받았으며,  
`Closeable` 인터페이스는 `AutoCloseable`인터페이스를 상속받았다.


때문에 `try-with-resources`를 활용하여 아래와 같이 내가 작성했던 코드를 개선할 수 있었다.

~~~java
public static String getHtml(String url) throws IOException {

	URL targetUrl = new URL(url);

	try (BufferedReader reader = new BufferedReader(new InputStreamReader(targetUrl.openStream()))){
		StringBuffer html = new StringBuffer();
		String tmp;

		while ((tmp = reader.readLine()) != null) {
			html.append(tmp);
		}
		return html.toString();
	}
}
~~~

### Reference
- 최중현 선임님
- [자바7에서 마음에 드는 다섯 가지](https://javacan.tistory.com/entry/my-interesting-java7-five-features)
- [중첩 Try with resources는 어떻게 동작할까?](https://multifrontgarden.tistory.com/192)
- [Do I need to close() both FileReader and BufferedReader?](https://stackoverflow.com/questions/1388602/do-i-need-to-close-both-filereader-and-bufferedreader)

-----
</br>

