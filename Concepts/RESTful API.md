> 백엔드(서버) 개발자를 뽑는 채용공고를 보면 76% 확률로 RESTful API 서버 개발 경험자를 찾고있었다.  
그런데 나는 RESTful API의 의미조차 모르고 있었다! 그래서 시작!
# 1. REST 란?
* REpresentational State Transfer.
* 웹의 장점을 최대한 활용할 수 있는 네트워크 기반의 아키텍쳐.
  * (웹의 장점이 무엇인지 공부하고 추가할것)
* ㅉㅉㅉ(World Wide Web)과 같은 분산 시스템을 위한 소프트웨어 아키텍쳐.

## 1-1. REST의 3요소
- Resource
- Method
- Message  
  
예를 들어 **"이름이 Ryan인 사용자를 생성한다"** 라는 호출에서
- Resource = 사용자
- Method = 생성한다 라는 행위
- Message = 이름이 Ryan인 사용자  
  
이를 REST 형태로 표현하면 아래와 같은 형태로 표현된다.  
~~~
HTTP POST, http://myweb/users/  
{  
  "users":{  
    "name":"ryan"  
  }  
}
~~~
  
즉, 생성한다 라는 행위인 Method는 HTTP Post 메서드가 되고,  
생성하고자 하는 대상이 되는 사용자라는 Resource는 http://myweb/users 라는 형태의 URI로 표현되며,  
생성하고자 하는 사용자에 관한 내용은 JSON 문서를 이용해서 표현된다.(JSON은 옵션 중 하나)  

## 1-2. HTTP와 REST
- REST는 결국 URI를 이용해서 제어할 자원을 명시하고, HTTP를 이용해서 제어 명령을 내린다.
- HTTP에는 여러 Method가 있지만 REST에서는 CRUD에 해당하는 4가지 Method만 사용한다.
  - C = Create = **POST**
  - R = Read = **GET**
  - U = Update = UPDATE
  - D = Delete = DELETE
  
상당히 간단하다. 그저 Resource를 URI로 정해준 후에 HTTP Method를 이용해서 CRUD를 구현하고  
Message를 JSON(혹은 XML)로 표현하여 HTTP Body에 실어 보내면 된다.

# 2. REST의 설계 목표
- 컴포넌트들간의 상호연동성 확보
  - 리눅스의 파이프가 상이한 프로세스들을 연동하는 것 처럼, REST API는 복수의 컴포넌트들을 결합함으로써 작업을 더 효율적으로 수행하게 한다.
- 범용 인터페이스 제공
  - REST 모델을 위한 HTTP와 URI는 표준이므로 어디서든 사용가능한 범용 인터페이스를 제공한다. 개발자는 비지니스 로직만 고민하면 된다.
- 각 컴포넌트들의 독립적인 배포
- 지연감소, 보안강화, 레거시 시스템을 인캡슐레이션하는 중간 컴포넌트로의 역할

# 3. REST의 6가지 특성/제약
1. **Stateless (무상태성)**
    - REST는 REpresentational State Transfer의 약어인 만큼 상태를 유지하지 않는 무상태성(Stateless)을 갖는다.
    - 상태가 없다는 것은 사용자나 클라이언트의 Context를 서버쪽에 유지하지 않는다는 의미이다.
    - 즉, HTTP Session과 같은 Context 저장소에 상태 정보를 저장하지 않는다는 것이다.
    - 상태 정보를 저장하지 않으면 각 API 서버는 들어오는 메세지로만 처리하면 되고, Session과 같은 Context 정보를 신경 쓸 필요가 없기 때문에 구현이 단순해진다.
2. **Client-Server (클라이언트-서버 구조)**
    - Client와 Server가 서로 분리되어야한다.
    - 예를 들어, 개발자가 모바일 어플의 Client 단을 수정할 때 서버 단의 데이터베이스 디자인에 영향을 끼치지 않고 수정할 수 있어야 한다는 것이다.
    - Vice Versa
    - 역할이 구분되면 개발자 관점에서 클라이언트와 서버에서 개발해야 할 내용들이 명확해지고 의존성이 줄어든다.
3. **Layered System (계층형 구조)**
    - MVC 모델에서 Model, View, Controller가 각각 역할을 담당하고 서로 상호작용하듯이, REST API에서도 아키텍쳐의 서로 다른 계층이 협업하여 확장성과 유연성을 증가시킨다.
    - 클라이언트 입장에서는 대상 서버에 직접 연결되었는지, 중간 서버를 통해 연결되었는지 알 수 없다. 즉, REST API 서버만 호출한다. 그러나 서버는 다중 계층으로 구성될 수 있다.
    - 그러므로 순수 비즈니스 로직을 수행하는 API 서버와 그 앞단에 사용자 인증(Authentication), 암호화(SSL), 로드밸런싱 등을 하는 계층을 추가해서 구조상의 유연성을 둘 수 있다.
4. **Cacheable (캐시 처리 가능)**
    - REST는 HTTP라는 기존의 웹 표준을 그대로 사용하기 때문에, 웹에서 사용하는 기존의 인프라를 활용 가능하다. 때문에 HTTP 프로토콜 기반의 로드밸런서, SSL, Cache 등을 사용할 수 있다.
    - 무상태성 API는 많은 양의 입출력 호출을 다루다보면 Request가 폭증할 수 있다. 때문에 REST API는 Cacheable Data의 저장공간을 확충하도록 설계되어야 한다.
    - 즉, 데이터가 Cacheable할 때, 그 Response는 그 데이터가 언제까지 저장될 수 있다는 것을 지시해야한다. 이를 통해 API와의 인터렉션 수를 드라마틱하게 줄일 수 있을 뿐만 아니라, 내부 서버 사용을 줄여서 빠르고 효율적인 어플리케이션을 만들도록하는 도구들을 API 사용자들에게 제공한다.
    - Cache를 사용하면 REST 컴포넌트가 위치한 서버에 Transation을 발생시키지 않는다. 때문에 전체 응답시간과 성능, 그리고 서버의 자원 사용률을 비약적으로 향상시킬 수 있다.
5. **Uniform Interface (인터페이스 일관성)**
    - 서버로부터 클라이언트를 분리하는 것의 핵심은 느슨한 결합으로 어플리케이션의 독립적인 개발을 가능하게 하는 Uniform interface를 갖는 것이다.
    - REST는 HTTP 표준에 따르는 것이라면, 어떤 기술이든 사용가능한 인터페이스 스타일이다.
6. **Code on demand(Optional)**
    - REST의 6가지 제약 중 유일하게 필수가 아닌 특성 혹은 제약.
    - 서버는 자바 애플릿이나 Client-side Script인 자바스크립트의 제공을 통해 클라이언트가 실행시킬 수 있는 로직을 전송하여 기능을 확장하거나 커스터마이징 할 수 있다.
# 4. 그래서 RESTful API 란?
  - RESTful API는 HTTP와 URI 기반으로 자원에 접근할 수 있도록 제공하는 어플리케이션 개발 인터페이스다.
  - 기본적으로 개발자는 HTTP Method와 URI만으로 인터넷에 자료를 CRUD 할 수 있다.

# *Reference
* https://en.wikipedia.org/wiki/Resource-oriented_architecture
* https://www.mulesoft.com/resources/api/what-is-rest-api-design
* https://ko.wikipedia.org/wiki/REST
* https://www.joinc.co.kr/w/man/12/rest/about
* http://meetup.toast.com/posts/92
* http://bcho.tistory.com/953
