# <HTTP 완벽 가이드>를 읽고.
>웹개발자의 필독서인 <HTTP 완벽 가이드>를 읽고 중요한 내용을 내 언어로 작성.

1. 사용자가 URL을 입력하면, 웹브라우져가 URL에서 호스트명을 추출하여 DNS를 통해 IP로 변환다.  
또한 URL에서 포트번호를 추출하여, IP와 포트번호를 사용하여 웹서버와 TCP/IP 커넥션을 맺는다.  
이제 웹브라우저는 웹서버에 HTTP 요청을 보내고, 웹서버는 HTTP 응답을 반환한다.  
커넥션이 닫히면 웹브라우저는 사용자가 요청한 문서를 보여준다.