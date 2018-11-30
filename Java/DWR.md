
# DWR (Direct Web Remoting)
> ajax를 기반으로 Client side의 javascript로 Server side의 Java Beans를 interative하게 조작할 수 있는 framework
> 실제 사용해본 느낌은, Controller를 추가로 생성하지 않고 jsp파일의 script에서 바로 service.메소드()를 호출할 수 있어서 편리함.  
> 단점은 좀 더 알아보고 이 곳에 정리할 예정..

### DWR 사용 흐름
![](http://skccdev.pbworks.com/f/20060905_dwr_architecture.jpg)

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
- http://rajalo.tistory.com/entry/%ED%8E%8C-DWR-%EC%84%A4%EB%AA%85%EA%B3%BC-%EC%82%AC%EC%9A%A9%EB%B2%95
