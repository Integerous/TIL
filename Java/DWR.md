
# DWR (Direct Web Remoting)
> Easy Ajax for JAVA
> 서버의 Java와 브라우저의 Javascript가 서로 쉽게 상호작용하고 콜할 수 있도록 하는 Java 라이브러리

# 사용 순서
1. DWR JAR file 설치
    - dwr.jar 파일을 [다운로드](http://directwebremoting.org/dwr/downloads/index.html)받아 WEB-INF/lib 디렉토리에 추가한다.
2. Commons Logging JAR file 설치
    - DWR은 Commons Logging에 의존하므로 commons-logging.jar를 [다운로드](http://commons.apache.org/proper/commons-logging/)받아서 WEB-INF/lib 디렉토리에 추가한다.
3. web.xml에 DWR servlet definition과 mapping 추가
~~~xml
<servlet>
<display-name>DWR Servlet</display-name>
<servlet-name>dwr-invoker</servlet-name>  
<servlet-class>org.directwebremoting.servlet.DwrServlet</servlet-class>
<init-param>
   <param-name>debug</param-name>
   <param-value>true</param-value>
</init-param>
</servlet>
<servlet-mapping>
<servlet-name>dwr-invoker</servlet-name>
<url-pattern>/dwr/*</url-pattern>
</servlet-mapping>
~~~
4. DWR config file(dwr.xml) 만들기
    - web.xml 옆에 dwr.xml 파일을 아래와 같이 작성한다.
~~~xml
<!DOCTYPE dwr PUBLIC
    "-//GetAhead Limited//DTD Direct Web Remoting 3.0//EN"
    "http://getahead.org/dwr/dwr30.dtd">

<dwr>
  <allow>
    <create creator="new" javascript="JDate">
      <param name="class" value="java.util.Date"/>
    </create>
    <create creator="new" javascript="Demo">
      <param name="class" value="your.java.Bean"/>
    </create>
  </allow>
</dwr>
~~~


# Reference
- http://directwebremoting.org/dwr/index.html
