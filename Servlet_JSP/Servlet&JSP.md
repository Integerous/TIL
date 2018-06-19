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
      ~~~java
      @WebServlet(urlPatterns={"/ServletInitParam"}, initParams={@WebInitParam(name="id", value="ryanHan")
      , @WebInitParam(....), @WebInitParam(....) ...} 
      ~~~
    3. ServletConfig 메소드 이용해서 데이터 불러오기
    ~~~java
    String id = request.getInitParameter("id");
    String pw = request.getInitParameter("pw");
    ~~~
## Servlet Context를 활용한 데이터 공유
  - Servlet 초기화 파라미터와 달리 여러 Servlet에서 특정 데이터를 공유해야할 경우,
  - context parameter를 이용해서 web.xml에 기술하고 Servlet에서 공유하면서 사용할 수 있다.
    1. Servlet Class 생성
    2. web.xml에 context parameter 기술
    ~~~xml
    <context-param>
      <param-name>id</param-name>
      <param-value>ryanHan</param-value>
    </context-param>
    <context-param>
      <param-name>pw</param-name>
      <param-value>11111</param-value>
    </context-param>
    ~~~
    3. ServletContext 메소드 이용해서 데이터 불러오기
    ~~~java
    String id = getServletContext().getInitParameter("id");
    String pw = getServletContext().getInitParameter("pw");
    ~~~
## JSP 동작 원리
  1. 클라이언트가 웹브라우져로 hello.jsp 요청하면
  2. JSP 컨테이너가 JSP파일을 Servlet파일(.java)로 변환
  3. Servlet파일은 컴파일 된 후 Class파일(.class)로 변환
      - Servlet을 이미 생성했다면 다시 생성하지않고 바로 Run
  4. 요청한 클라이언트에게 html 형태로 응답
## JSP 내부 객체
  - 개발자가 객체를 생성하지 않아도 바로 사용할 수 있는 객체가 내부 객체
  - 내부 객체는 JSP컨테이너에 의해 Servlet으로 변환될 때 자동으로 객체가 생성된다.
  - 내부 객체의 종류
    - 입출력 객체 : request, response
    - 서블릿 객체 : page, config
    - 세션 객체 : session
    - 예외 객체 : exception
## JSP 태그 종류
  - <%@   지시자, 페이지 속성   %>
  - <%--   주석, 소스보기로 안보임   --%>
  - <%!   선언, 변수 메서드 선언   %>
  - <%=   표현식, 결과값 출력   %>
  - <%   스크립틀릿, java code   %>
  - <jsp:action>   자바 빈 연결   </jsp:action>
## JSP 지시자 태그 <%@ %>
  - JSP 페이지의 전체적인 속성을 지정할 때 사용
  - 종류
    - page: 해당 페이지의 전체적인 속성 지정
    - include: 별도의 페이지를 현재 페이지에 삽입
    - taglib: 태그 라이브러리의 태그 사용'
### <%@ page %>
  - 주로 사용되는 언어 및 import문에 많이 사용
  ~~~jsp
  <%@ page import="java.util.arrays"%>
  <%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
  ~~~
### <%@ include %>
  - file 속성 사용
  ~~~jsp
  <%@ include file="example.jsp"%>
  ~~~
## JSP Request 객체
~~~jsp
<body>
<%!
  String name, id, pw;
  String[] hobbys;
%>
<%
  request.setCharacterEncoding("UTF-8");
  
  name = request.getParameter("name");
  id = request.getParameter("id");
  pw = request.getParameter("pw");
  
  hobbys= request.getParameterValues("hobby");
%>
이름 : <%= name %> <br/>
아이디 : <%= id %> <br/>
비밀번호 : <%= pw %> <br/>
취미 : <%= Arrays.toString(hobbys) %>
</body>
~~~
## JSP Response 객체
~~~jsp
<body>
<%!
  int age;
%>
<%
  String str = request.getParameter("age");
  age = Integer.parseInt(str);
  
  if(age >= 20)
    response.sendRedirect("pass.jsp?age=" +age);
  else
    response.sendRedirect("ng.jsp?age=" +age);
%>
<%= age %>
</body>
~~~

## 쿠키 Cookie
- 웹 브라우저에서 서버로 요청을 보내면 서버에서는 로직을 수행하고 웹브라우저에 응답한다
- 그리고 서버는 웹브라우저와의 관계를 종료한다. (http 프로토콜의 무상태성)
- 연결이 끊겼을 때 어떤 정보를 지속적으로 사용하기 위한 수단으로 쿠키 사용
- 쿠키는 서버에서 생성하여 클라이언트 단에 특정 정보를 저장한다.
- 그리고 서버에 요청할 때 마다 쿠키의 속성값을 참조하거나 변경할 수 있다.
- 쿠키의 용량은 4kb로 제한되며, 300개 까지 데이터 정보를 가질 수 있다.

## 쿠키 문법
1. 쿠키 생성 (쿠키 클래스 생성)
2. 속성 설정 (setter 이용)
3. Response 객체에 쿠키 탑재 (response.addCookie() 이용)
- Cookie cookie = new Cookie("cookieName","cookieValue")
- cookie.setMaxAge(60*60) 60초 * 60
- 삭제할때는 cookie.setMaxAge(0)
- 삭제하고나서 response.addCookie()를 해줘야함
- response.addCookie(cookie);
- Cookie[] cookies = request.getCookies();

## 세션 Session
- 쿠키와 마찬가지로 서버와의 관계를 유지하기 위한 수단
- 쿠키와 달리 서버 상의 객체로 존재
- jsp파일을 통해서만 접근이 가능하기 때문에 보안에 좋고, 저장할 수 있는 데이터의 한계가 없다.
- 브라우저 하나 당 하나의 Session 객체가 생성되고 고유의 id 값이 있다.

## 세션 문법 
1. 클라이언트 요청
2. WAS의 컨테이너에서 자동으로 session 생성
3. session 이라는 내부 객체의 메서드를 사용해서 세션의 속성값 설정
- session.setAttribute("mySessionName","mySessionValue") - 세션에 데이터 저장
- session.getAttribute("mySessionName") - **get으로 받아오면 무조건 Object형으로 온다!**
  - Object obj1 = session.getAttribute("mySessionName");
  - String mySessionName = (String)obj1; - **이렇게 형변환 해서 써야한다**
- getAttributeNames() - 세션에 저장되어있는 모든 데이터의 이름 (유니크한 키값)을 얻는다.
- getId() - 자동생성된 세션의 유니크한 아이디를 얻는다.
- isNew() - 세션이 최초 생성되었는지, 이전에 생성된 세션인지 구분한다.
- getMaxInactiveInterval() - 세션의 유효시간을 얻는다. 가장 최근 요청 시점을 기준으로 카운트.
  - (apache-tomcat-9.0 - conf - web.xml에 30분으로 설정되어있다.
- removeAttribute() - 세션에서 특정 데이터를 제거한다.
- invalidate() - 세션의 모든 데이터를 삭제한다.

## 예외 페이지
1. page 지시자를 사용한 예외 처리
  - 예외가 발생할 수 있는 페이지
  ~~~jsp
  <%@ page errorPage = "errorPage.jsp"%>
  ~~~
  에러 날 경우 errorPage.jsp를 보여주는 지시자
  
  - 예외 페이지
  ~~~jsp
  <%@ page isErrorPage = "true"%> - default값은 false. 이게 있어야 밑의 exception객체와 메서드를 사용할 수 있다.
  <% response.setStatus(200); %> - 200은 정상적인 페이지라는 의미인데 이 설정이 없으면 웹페이지에서 500으로 처리할 때가 있어서 200을 설정해준다
  <%= exception.getMessage()%>
  ~~~
2.  web.xml을 활용한 예외 처리
~~~xml
<errorPage>
  <errorCode> 404 </errorCode>
  <location> /error404.jsp </location>
</errorPage>
<errorPage>
  <errorCode> 500 </errorCode>
  <location> /error500.jsp </location>
</errorPage>
~~~

## Bean
- Java 언어의 데이터(속성)와 기능(메서드)로 이루어진 클래스
- 반복적인 작업을 효율적으로 하기 위해 Bean을 사용한다.
- jsp 페이지를 만들고 액션태그를 사용하여 빈을 사용한다.
- Bean을 만든다는 것은 데이터 객체를 만들기 위한 클래스를 만드는 것.

## Bean 관련 액션태그
1. useBean - 특정 빈을 사용한다고 명시할 때 사용.
  - `<jsp:useBean id="student" class="com.ryemha.ex.Student" scope="page"/>`
  - id는 Student student = new Student(); 할 때의 student
  - Scope
    - page : 페이지 내에서만 사용 가능
    - request : 요청된 페이지 내에서만 사용 가능
    - session : 웹브라우저의 생명주기와 동일하게 사용 가능
    - application : 웹 어플리케이션 생명주기와 동일하게 사용 가능
2. setProperty - setter 역할. 데이터 값을 설정할 때 사용.
  - `<jsp:setProperty name="student" property="name" value="홍길동"/>`
3. getProperty - getter 역할. 데이터 값을 가져올 때 사용
  - `<jsp:getProperty name="student" property="name"/>`
  
## JDBC
- Java 프로그램에서 SQL문을 실행하여 데이터를 관리하기 위한 Java API
- 다양한 데이터베이스에 대해서 별도의 프로그램을 만들 필요 없이, JDBC를 이용하면 하나의 프로그램으로 데이터베이스를 관리할 수 있다.

## 데이터베이스 연결 순서
1. JDBC드라이버 로드 - DriverManager
    - `Class.forName("oracle.jdbc.driver.OracleDriver");` => 메모리에 OracleDriver가 로드된다.
2. 데이터베이스 연결 - Connection
    - `DriverManager.getConnection(JDBC URL, 계정아이디, 비밀번호);` => Connection 객체 생성
3. SQL문 실행 - Statement
    - `connection.createStatment();` => Statement 객체를 통해 SQL문이 실행된다.
4. 데이터베이스 연결 해제 - ResultSet
  - `statement.executeQuery(), statement.executeUpdate() => SQL문의 결과값을 ResultSet 객체로 받는다.(executeQuery()의 경우)
    - statement.executeQuery() - SQL문 실행 후 여러 개의 결과값이 나올 때 사용 (select), 반환형은 resultSet
    - statement.executeUpdate() - SQL문 실행 후 테이블의 내용만 변경되는 경우 사용 (insert, delete, update), 반환형은 int
5. close();

## PreparedStatement 객체
- SQL문 실행을 위해 사용하는 Statement 객체는 중복코드가 많아지는 단점이 있다. PreparedStatement 객체는 이러한 단점을 개선한다.
~~~java
Class.forName(driver);
connection = DriverManager.getConnection(url,uid,upw);
int n;
String query = "insert into memberforpre (id, pw, name, phone) values (?,?,?,?)";
preparedStatement = connection.preparedStatement(query);

preparedStatement.setString(1, "abc");
preparedStatement.setString(2, "123");
preparedStatement.setString(3, "홍길동");
preparedStatement.setString(4, "010-1234-5678");
n = preparedStatement.executeUpdate();
~~~

## DBCP (Data Base Connection Pool)
- 클라이언트에서 다수의 요청이 발생할 경우 데이터베이스에 부하가 발생
- 이러한 문제를 해결하기 위해 미리 커넥션 객체들을 만들어 놓는 커넥션 풀 기법 사용

## DBCP 사용하기
- tomcat container가 데이터베이스 인증을 하도록 context.xml 파일을 열어 아래의 코드를 추가한다.
~~~xml
<Resource
          auth = "Container"
          driverClassName = "oracle.jdbc.driver.OracleDriver"
          url = "jdbc:oracle:thin:@localhost:1521:xe"
          username = "scott"
          password = "tiger"
          name = "jdbc/Oracle11g"
          maxActive = "50"
          maxWait = "1000"
          />
~~~
- 직접 OracleDriver를 로드하던 방식에서 커넥션 풀을 활용하는 방식으로 사용
  - 직접 driver 로드할 때
    - `Class.forName("oracle.jdbc.driver.OracleDriver");`
  - 커넥션 풀 활용
    ~~~java
    private DataSource dataSource;
    Context context = new InitialContext();
    dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
    //connection = DriverManager.getConnection(url, uri, urw); 이렇게 쓰던걸 아래처럼
    connection = datdSource.getConnection();
    ~~~
