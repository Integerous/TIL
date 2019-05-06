# :bread: 빵 부스러기

>개발 관련 학습 중 하나의 글로 작성하기엔 짧고,  
버리기엔 아까운 부스러기 정보들을 모아두는 곳.


## 1. 클래스라는 개념이 등장한 기본적인 이유
예를 들어, 전화번호부에서 한 사람의 이름과 전화번호는 항상 붙어다녀야 하는 데이터이다.  
그런데 이 데이터를 별개의 변수에 저장하면, 이름 데이터를 옮길 때 마다 전화번호 데이터도 따로 옮겨줘야 한다.  
만약 이름과 전화번호 외에 더 많은 데이터를 저장해야 한다면, 더 불편해질 것이다.  
그래서 **서로 관련있는 데이터들을 하나의 단위로 묶어두기 위해** 등장한 것이 클래스라는 개념이다.
- Reference - [Java로 배우는 자료구조 - 권오흠](https://www.inflearn.com/course/java-%EC%9E%90%EB%A3%8C%EA%B5%AC%EC%A1%B0/%EB%AC%B8%EC%9E%90%EC%97%B4-%EB%8B%A4%EB%A3%A8%EA%B8%B0-3/)

-----

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
                                      // Type Parameter T를 가지는 객체를 new 연산자로 생성할 수도 없다.
}
~~~
    
