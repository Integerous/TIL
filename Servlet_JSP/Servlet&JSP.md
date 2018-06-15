# Servlet & JSP 학습 기록
>[신입 프로그래머를 위한 실전 JSP 강좌](https://www.inflearn.com/course/%EC%8B%A4%EC%A0%84-jsp-%EA%B0%95%EC%A2%8C/) 강의를 듣고 학습한 내용을 기록.
## JSP(Java Server Page)
  - HTML 파일 내에 Java언어를 삽인한 문서
  - 동적 웹 어플리케이션 컴포넌트
  - .jsp 확장자
  - 클라이언트의 요청에 동적으로 작동하고, 응답은 html을 이용
  - jsp는 servlet으로 변환되어 실행
  - hello.jsp는 실행될 때 hello_jsp.java로 변환되고, hello_jsp.class로 컴파일된다.
    - 경로는 톰캣 - work - catalina - localhost - 프로젝트명 폴더
## Servlet(Server Applet)
  - Java 언어로 이루어진 웹 프로그래밍 문서
  - 동적 웹 어플리케이션 컴포넌트
  - .java 확장자
  - 클라이언트의 요청에 동적으로 동작하고, 응답은 html을 이용
## Context Path
  - WAS에서 웹어플리케이션을 구분하기 위한 path
  - 이클립스에서 프로젝트를 생성하면, 자동으로 servlet.xml에 추가된다.
## Servlet 작동 순서
  - 클라이언트에서 servlet 요청이 들어오면, 서버에서는 servlet 컨테이너를 만들고 요청이 있을 때마다 쓰레드가 생성된다.
  - 클라이언트 요청 -> 웹서버 -> WAS -> Servlet Container(쓰레드 생성, 서블릿객체 생성)
  - Servlet은 요청이 들어오면 JVM 안에서 쓰레드를 생성하므로 다른 CGI 언어들과 다르게 서버 부하가 적게 발생. (다른 CGI는 요청 있을때마다 객체 생성)
## Servlet 생명 주기
  - 서블릿은 최초 요청시 객체가 만들어져 메모리에 로딩되고, 그 이후의 요청에는 기존 객체를 재활용한다. 때문에 속도가 빠르다.
    - 서블릿 객체 생성 (최초에 한 번)
      - 선처리 할 경우 (@PostConstruct)
    - Init() 호출 (최초에 한 번)
    - Service(), doGet(), doPost() 호출 (요청 시 매 번)
    - destroy() 호출 (마지막 한 번. 자원해제: servlet수정, 서버 재가동 등)
      - 후처리 할 경우 (@PreDestroy)
## Servlet 초기화 파라미터
  - 특정 servlet이 생성될 때 초기에 필요한 데이터들이 있다.(예를 들어 특정 경로 및 아이디 정보)
  - 이러한 데이터들을 초기화 파라미터라고 하며, web.xml에 기술하고 ServletConfig 클래스를 이용해서 접근(사용)한다.
      1. Servlet Class 생성
      ~~~xml
    <servlet>
      <servlet-name>ServletInitParam</servlet-name>
      <servlet-class>com.my.practice.ServletInitParam</servlet-class>
      ~~~
      2. web.xml 파일에 초기화 파라미터 기술
      ~~~xml
    <init-param>
      <param-name>id</param-name>
      <param-value>ryanHan</param-value>
    </init-param>
    <init-param>
      <param-name>pw</param-name>
      <param-value>11111</param-value>
    </init-param>
    </servlet>
    <servlet-mapping>
      <servlet-name>ServletInitParam</servlet-name>
      <url-pattern>/initParam</url-pattern>
    </servlet-mapping>
    ~~~
    3. ServletConfig 메소드 이용해서 데이터 불러오기
    ~~~java
    String id = request.getInitParameter("id");
    String pw = request.getInitParameter("pw");
    ~~~
  - web.xml에 기술하지 않고 Servlet 파일에 직접 기술할 수도 있다.
    1. Servlet Class 생성
    2. @WebInitParam 에 초기화 파라미터 기술
