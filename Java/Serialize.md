# 자바 직렬화 (Java Serialize)
>새로 맡게 된 레거시 프로젝트의 몇몇 클래스들이 Serializable 인터페이스를 왜 상속받는지 알아보는 과정에서 Java 직렬화 개념 정리

## 간단하게
- Java 내부 시스템에서 사용되는(또는 JVM 메모리에 올려진) 객체나 데이터를 외부에서 사용할 수 있도록 Byte 형태로 변환하는 것. (Vice Versa)

## 자세히
- 대부분 OS의 프로세스 구현은 서로 다른 가상메모리주소공간(Virtual Address Space, VAS)를 갖기 때문에 Object 타입의 참조값(주소값) 데이터 인스턴스를 전달할 수 없다.(전달해도 서로 다른 메모리 공간에서는 전달된 참조값이 무의미하다.) 때문에 서로 다른 메모리 공간 사이의 데이터 전달을 위해서는 메모리 공간의 주소값이 아닌 Byte 형태로 직렬화(변환)된 객체 데이터를 전달하면, 사용하는 쪽에서 역직렬화하여 사용할 수 있게 된다.
- Java의 클래스 설계에서는 객체 안에 객체가 존재할 수 있다. 객체 A 안에 들어있는 객체는 B는 객체 B를 참조할 수 있는 주소값인데, 직렬화를 하면 이 주소값이 아니라 객체 B 자체의 데이터를 Primitive 타입(Byte 타입) 데이터로 변환한다. 때문에 직렬화 된 데이터는 모두 Primitive 타입(Byte 타입)의 데이터 묶음이며, 이것이 파일 저장이나 네트워크 전송 시 파싱할 수 있는 유의미한 데이터가 되는 것이다. 즉, 전송/저장 가능한 데이터를 만드는 것이 직렬화(Serialization) 이다.
- JSON, CSV 등의 포맷은 직렬화/역직렬화 시에 특정 라이브러리를 도입해야 쉽게 개발이 가능하며, 구조가 복잡해지면 직접 매핑해줘야 하지만, Java 직렬화는 비교적 복잡한 객체도 큰 작업 없이`java.io.Serializable` 인터페이스만 구현해주면 기본 Java 라이브러리만 사용해도 직렬화/역직렬화 가능

## 직렬화의 조건
- `Java.io.Serializable` 인터페이스를 상속받은 객체와 Primitive 타입의 데이터가 직렬화의 대상이 될 수 있다.
  - 기본자료형(Primitive Type)은 정해진 Byte의 변수이기 때문에 Byte 단위로 변환하는 것에 문제가 없지만,
  - 객체의 크기는 가변적이며, 객체를 구성하는 자료형들의 종류와 수에 따라 객체의 크기가 다양하게 바뀔 수 있기 때문에 객체를 직렬화하기 위해 Serializable 인터페이스를 구현해야 한다.
- 객체의 멤버들 중 Serializable 인터페이스가 구현되지 않은 것이 존재하면 안된다.
- `Transient`가 선언된 멤버는 전송되지 않는다.
  - 객체 내에 Serializable 인터페이스가 구현되지 않은 멤버 때문에 `NonSerializableException`이 발생하는 경우, Transient를 선언해주면 직렬화 대상에서 제외되기 때문에 문제없이 해당 객체를 직렬화 할 수 있다.

## 직렬화가 사용되는 상황은?
- JVM의 메모리에서 상주하는 객체 데이터를 그대로 영속화(Persistence)할 때 사용된다.
  - 시스템이 종료되더라도 사라지지 않으며, 영속화된 데이터이기 때문에 네트워크로 전송도 가능하다.
- Servlet Session
  - Servlet 기반의 WAS들은 대부분 세션의 Java 직렬화를 지원한다.
  - 파일로 저장, 세션 클러스터링, DB를 저장하는 옵션 등을 선택하면 세션 자체가 직렬화되어 저장 및 전달된다.
- Cache
  - 캐시할 부분을 직렬화된 데이터를 저장해서 사용
- Java RMI(Remote Method Invocation)
  - 원격 시스템의 메서드를 호출할 때 전달하는 메세지(객체)를 직렬화하여 사용
  - 메세지(객체)를 전달받은 원격 시스템에서는 메세지(객체)를 역직렬화하여 사용
- 객체가 세션에 저장하지 않는 단순한 데이터 집합이고, 컨트롤러에서 생성되어서 뷰에서 소멸하는 데이터의 전달체라면 객체 직렬화는 고려하지 않아도 된다.
- 세션 관리를 스토리지나 네트워크 자원을 사용한다면 객체 직렬화를 해야 하고, 메모리에서만 관리한다면 객체 직렬화를 할 필요가 없다. 둘 다 고려한다면 직렬화가 필요하다.

## Java 직렬화 예시
- `Java.io.Serializable` 인터페이스를 상속받는다.
- `serialVersionUID`를 설정한다.
~~~java
@Entity
@AllArgsConstructor
@toString
public class HubContent implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String title;
    private String subtitle;
    ...
~~~

- Java 직렬화는 `java.io.ObjectOutPutStream` 객체를 이용한다.
- 객체를 직렬화하여 Byte 배열 형태로 변환한다.

~~~java
HubContent hubcontent = new HubContent("제목", "부제");
byte[] serializedHubContent;
try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
    try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
        oos.writeObject(hubContent);
        
        // 직렬화된 HubContent 객체
        serializedHubContent = baos.toByteArray();
    }
}
~~~

## 역직렬화 예시
- 역직렬화를 하기 위해서는 직렬화 대상이 된 객체의 클래스가 ClassPath에 존재해야 하며 import 되어있어야 한다.

~~~java
try (ByteArrayInputStream bais = new ByteArrayInputStream(serializedHubContent)) {
    try (ObjectInputStream ois = new ObjectInputStream(bais)) {
        
        // 역직렬화된 HubContent 객체를 읽어온다.
        Object objectHubContent = ois.readObject();
        HubContent hubContent = (HubContent) objectHubContent;
    }
}
~~~

## `serialVersionUID`를 직접 관리하는 이유?
- Java 직렬화 대상 객체는 동일한 `serialVersionUID`를 가지고 있어야 한다.
- 그런데 `serialVersionUID`를 직접 선언하지 않아도, 내부적으로 클래스의 구조 정보를 이용해 자동으로 생성된 해쉬값이 할당된다.
- 때문에 클래스의 멤버 변수가 추가되거나 삭제되면 `serialVersionUID`가 달라지는데,
- 역직렬화 할 때 기존의 `serialVersionUID`와 변경된 `serialVersionUID`가 다르면 `java.io.InvalidClassException` 예외가 발생한다.
- 그러므로 위의 코드(`private static final long serialVersionUID = 1L;`) 처럼 **직접 serialVersionUID 값을 관리해야 클래스가 변경되어도 문제없이 직렬화/역직렬화를 할 수 있다.**
- 하지만 `serialVersionUID`가 같다고 무조건 문제없이 직렬화/역직렬화 할 수 있는 것은 아니다.
  - 클래스의 멤버 변수 타입이 다르면 타입 예외가 발생한다.
  - 멤버 변수를 제거하거나 변수명을 바꾸면 예외는 발생하지 않지만 데이터는 누락된다.
  
## 실무 사용 조언
>[자바 직렬화, 그것이 알고싶다. 실무편](http://woowabros.github.io/experience/2017/10/17/java-serialize2.html)에서 발췌한 내용

1. `serialVersionUID`는 개발 시 직접 관리
2. 역직렬화 대상 클래스의 멤버 변수 타입변경 지양
3. **외부(DB, 캐시 서버, NoSQL 서버 등)에 장기간 저장될 정보는 Java 직렬화 사용 지양** (클래스 변경을 예측할 수 없으므로)
4. 개발자가 직접 컨트롤할 수 없는 클래스(프레임워크 또는 라이브러리에서 제공하는 클래스)는 직렬화 지양
5. 자주 변경되는 클래스는 Java 직렬화를 사용하지 않는 것이 좋다.
6. 역직렬화에 실패하는 상황에 대한 예외처리 필수
7. 직렬화된 데이터는 타입 정보등의 클래스 메타정보를 포함하기 때문에 JSON 포맷에 비해 약 2~10배 더 사이즈가 크다. 특히 직렬화된 데이터를 메모리 서버(Redis, Memcached)에 저장하는 환경에서 트래픽에 따라 네트워크 비용과 캐시 서버 비용이 급증할 수 있으므로, JSON 포맷으로의 변경을 고려해야 한다.

## Reference
- [자바 직렬화, 그것이 알고싶다. 훑어보기편](http://woowabros.github.io/experience/2017/10/17/java-serialize.html)
- [자바 직렬화, 그것이 알고싶다. 실무편](http://woowabros.github.io/experience/2017/10/17/java-serialize2.html)
- [직렬화](https://j.mearie.org/post/122845365013/serialization#_=_)
- [[JAVA] Serializable 과 transient](https://hyeonstorage.tistory.com/254)
- https://okky.kr/article/224715
