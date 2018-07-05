# Java 문자열 연결 방법들
>CodeWars에서 알고리즘 문제들을 풀다보면 문자열 연결할 일이 많았는데, 퍼포먼스 이슈가 많다보니 정리가 필요했다.
>>StringBuilder / StringBuffer / + 연산자 / Concat메서드 비교
>>>새로 알게되는 내용이 생길 때마다 내용 업데이트 예정.


## 1. Concat 메서드
String 클래스에는 문자열을 이어주는 concat 메서드가 있다. 
~~~java
String sample = "X".concat("Y").concat("Z"); // XYZ
~~~
하지만 Java에서 String 객체는 Immutable(불변)이기 때문에, 새로운 문자열을 더할 때마다 새로운 인스턴스를 생성한다. 그래서 퍼포먼스가 좋지 않다.  
예를 들어 수십번 String이 더해지는 경우, 각 String의 주소값이 stack에 쌓이고,  
가비지콜렉터가 호출되기 전 까지 클래스들은 heap에 지속적으로 쌓이게 된다. 즉, 메모리 관리 측면에서 치명적이다.


## 2. StringBuilder
~~~java
String sample = new StringBuilder().append("X").append("Y").append("Z").toString();
~~~
StringBuilder는 내부적으로 문자열을 가지고 문자열을 변경하는 메서드를 제공한다.  
String 객체가 매 수정 시에 새로운 인스턴스를 만드는 것과 달리, StringBuilder는 문자열을 계속해서 앞뒤로 덧붙이거나 문자열을 삽입, 삭제하는 등 다양한 연산이 가능하다.

## 3. StringBuilder vs. StringBuffer
- 두 클래스가 제공하는 메서드는 같다. 둘의 차이는 멀티쓰레드 환경에서 동기화 보장이 되는냐 안되느냐의 차이이다.
- StringBuffer는 thread-safe 이므로 MultiThreaded 환경에서는 StringBuffer를 사용해야 한다.
  - StringBuffer는 멀티쓰레드 환경에서 다른 값을 변경하지 못하도록 하므로  
  **Web이나 소켓환경과 같이 비동기로 동작하는 경우가 많을 때에는 String Buffer를 사용**하는 것이 안전하다 .

## 3. + 연산자
~~~java
String sample = "X" + "Y" + "Z"; // XYZ
~~~
연산자로도 문자열을 붙일 수 있다. + 연산자는 Java 1.5 이전에는 concat 메서드와 동일하게 새로운 String 인스턴스를 생성했지만,  
Java 1.5 부터는 컴파일 단계에서 StringBuilder로 컴파일 되도록 변경되었기 때문에 StringBuilder와 동일하다고 볼 수 있다.  
간단한 경우에는 가독성이 좋은 + 연산자를 사용하는 것이 좋다. 하지만 + 연산자는 만들 때마다 StringBuilder 인스턴스를 생성하기 때문에,  
반복문에서 문자열을 조합하는 것처럼 StringBuilder 인스턴스를 생성해서 여러 작업을 하는 경우에는 StringBuilder를 쓰는 것이 좋다.
~~~java
List<String> list = Array.asList("foo","bar","baz","qux");

StringBuilder sb = new StringBuilder();
  for(String s : list){
    sb.append(s);
  }
System.out.println(sb);
~~~

## 4. 성능 비교
![성능비교](https://docs.google.com/spreadsheets/d/1dV4Pbe2_ZCsc9TDBYsN9u69a2a3xSjCAzxKR7I6fxzg/pubchart?oid=1847999196&format=image)  
출처 : http://www.pellegrino.link/2015/08/22/string-concatenation-with-java-8.html


# Reference
- http://javahungry.blogspot.com/2013/06/difference-between-string-stringbuilder.html
- http://www.pellegrino.link/2015/08/22/string-concatenation-with-java-8.html
- https://novemberde.github.io/2017/04/15/String_0.html
- https://www.slipp.net/questions/271
- http://futurecreator.github.io/2018/06/02/java-string-concatenation/
