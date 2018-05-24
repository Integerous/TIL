> 면접에서 웹의 동작 원리에 대해 질문을 받았는데, 제대로 답변하지 못하고 Controller-Service-Dao-DB 정도로만 대답했다. 그래서 시작!
# 웹의 동작 원리(How the web works)
0. Web은 분산환경을 기반으로 한다. 분산환경은 Client-Server 구조.
1. 클라이언트가 URL을 입력하면,
2. 브라우져가 DNS서버로 가서 요청한 URL의 실제 주소(IP)를 찾는다.
3. 브라우져는 서버에 웹사이트의 사본을 요청하는 HTTP 요청메세지를 보낸다.
4. 이러한 메세지와 데이터들은 TCP/IP 연결을 통해 클라이언트와 서버 사이에 전송된다.
    - 서버 사이드는 웹서버 - 웹어플리케이션서버(WAS) - 데이터베이스로 구성된다.
      - **웹서버**
        - 브라우져의 요청을 받고 결과를 응답하는 역할
        - 정적인 페이지 처리 담당
        - 요청에 필요한 페이지 로직이나 DB와의 연동을 위해 WAS에 처리를 요청
        - ex) Apache
      - **WAS**
        - 요청된 URL에 맵핑된 페이지를 실행해서 결과를 웹서버에 반환
        - 페이지의 로직이나 데이터베이스와의 연동을 처리하는 역할
        - 동적인 페이지 처리 담당
        - 요청이 로드된 적이 있으면 새로운 쓰레드를 깨우고(Invoke Thread) Service()실행
        - 요청이 로드된 적이 없으면 새로운 인스턴스를 Init() -> Invoke Thread -> Service() -> Timeout되면 Destroy()
        - ex) Tomcat
5. 메세지를 받은 서버는 클라이언트의 요청을 승인하고 "200 OK" 메세지를 클라이언트에 전송한다.
6. 이후에 서버는 웹사이트의 데이터들을 데이터 패킷이라는 작은 덩어리로 뭉쳐서 브라우져에 전송한다.
7. 브라우져는 이 데이터패킷들을 조립해서 완전한 웹사이트를 클라이언트에게 보여준다.

## *Reference
- https://developer.mozilla.org/en-US/docs/Learn/Getting_started_with_the_web/How_the_Web_works
- https://d2.naver.com/helloworld/59361
- https://www.html5rocks.com/en/tutorials/internals/howbrowserswork/
- http://hoonmaro.tistory.com/26
