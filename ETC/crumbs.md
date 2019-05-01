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

## 5. 추상(abstract) 클래스가 필요한 기본적인 이유
<Java의 정석>으로 Java의 개념들을 공부한 나는 추상클래스가 언제나 찝찝했다.  
아마 개발을 공부하기 시작한지 얼마 안되어 학습한 내용이었기 때문에 적당한 수준으로 이해하고 넘어갔던 것이다.  
찝찝한 상태로 남아있는 개념들을 하나씩 바로 잡을 계획이었는데, 추상클래스에 대해 조금 더 이해 할 기회가 생겨 정리해본다. 

<Java의 정석>에서는 추상클래스와 추상메서드가 필요한 이유를 명확하게 설명하지 않았다.(아마도 다양한 이유가 있기 때문에 애매하게 설명한 것 같다.) 
때문에 나는 지금까지 추상클래스(메서드)가 필요한 이유를 `자손 클래스에서 추상메서드를 반드시 구현하도록 강요하기 위해서`라고 이해하고 있었다. 
그리고 아직 실무에서 추상클래스(메서드)를 작성할 일이 없었기 때문에 발전이 없는 상태였다.  
하지만 [Java로 배우는 자료구조](https://www.inflearn.com/course/java-%EC%9E%90%EB%A3%8C%EA%B5%AC%EC%A1%B0/lecture/7465) 강의에서는 
조금 다르게 설명했고, 난 이 예제와 설명이 더 와닿았다.


#### 예제를 단순화 해보면,


1. Event 라는 부모클래스가 있고,
    ~~~java
    public class Event {
        ...
    }
    ~~~
2. Event를 상속받은 자식클래스 aEvent, bEvent, cEvent가 있으며, 각각 isRelevant() 메서드를 구현하였다.
    ~~~java
    public class aEvent extends Event {
        ...
        public boolean isRelevant(Data data) {
            return logic.a;
        }
    }
    ~~~
    ~~~java
    public class bEvent extends Event {
        ...
        public boolean isRelevant(Data data) {
            return logic.b;
        }
    }
    ~~~
    ~~~java
    public class cEvent extends Event {
        ...
        public boolean isRelevant(Data data) {
            return logic.c;
        }
    }
    ~~~

3. 그리고 다른 클래스의 어느 메서드에서 Event 타입으로 선언된 배열 events[]의 각 원소를 isRelevant()메소드로 검증하는 로직을 작성했다. (events[] 배열의 각 원소는 aEvent, bEvent, cEvent 타입의 원소들이다.)
    ~~~java
    public class someClass() {
        ...
        Event events[] = new Event[100];
        ...
        
        public void someMethod() {
            for(int i=0; i<n; i++)
                if(events[i].isRelevant(someData)) // 컴파일 에러!!!
                    system.out.println( events[i].toString());
        }
    ~~~

    그런데 isRelevant()메소드에서 컴파일 에러가 발생한다.  
    왜냐하면 events[] 배열은 Event 타입으로 선언되었는데, Event 클래스에는 isRelevant() 메서드가 정의되어 있지 않기 때문이다.  

    배열의 각 원소가 Event 클래스를 상속받은 클래스의 객체이고, 각 클래스는 isRelevant() 메서드가 구현되어 있지만,  
    컴파일러는 Event 클래스에 isRelevant() 메서드가 없는 것을 허용하지 않는 것이다.  

    (`events[i].toString()`에서 컴파일 에러가 나지 않는 이유는 toString() 메서드는 모든 클래스의 조상 클래스인 Object 클래스에 정의된 메서드이기 때문이다.)

4. 이 때, 단순히 컴파일 에러만 피하고자 한다면,  
아래와 같이 Event 객체에 사용될 일 없는 isRelevant() 메서드를 정의할 수 있을 것이다.

    ~~~java
    public class Event {
        ...

        public boolean isRelevant(Data data) {
            return false;
        }
    }
    ~~~

    위와 같이 메서드를 정의하면 컴파일 에러는 사라지고 정상적으로 동작한다.  
    하지만 사용되지 않을 메서드를 구현하는 것은 올바른 해결책이 아니다.  

5. 이 문제를 해결하려면 isRelevant()메서드를 추상 메서드로 만들면 된다.  
(그리고 아래 코드와 같이 추상 메서드가 하나라도 있으면 그 클래스는 추상 클래스가 된다.)

    ~~~java
    public abstract class Event {
        ...

        public abstract boolean isRelevant(Data data);
    }
    ~~~
    
    
이 예제가 이해하기 쉬웠던 이유는 나같은 초보자가 코드를 짜면서 맞이하는 상황과 비슷한 흐름이기 때문이다.  
나의 경우에는, 큰 그림을 보지 못하고 우선 동작하는 코드를 작성한다. 그러다 보면 코드 중복이 발견되고, 그 중복을 해결하기 위해 그제서야 추상화와 상속을 고민한다.

때문에 부모클래스에 추상 메서드를 선언하여 추상클래스로 만들고, 그 클래스를 상속받은 서브클래스들에서 추상메서드를 구현하는 방식으로 설명하는 <Java의 정석>의 예제는 초보개발자 입장에서 제대로 이해할 수 없었던 것이다.

물론 추상 메서드와 추상 클래스의 역할과 활용에 대해서는 더 깊은 이야기들이 있겠지만,  
실무에서 마주하게 되길 바라면서 우선은 이 정도로 정리해본다.

- Reference - [Java로 배우는 자료구조 - 권오흠](https://www.inflearn.com/course/java-%EC%9E%90%EB%A3%8C%EA%B5%AC%EC%A1%B0/lecture/7465)

-----


