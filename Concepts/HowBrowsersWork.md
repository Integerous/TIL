>Web이 어떻게 동작하는지 공부하는 과정에서 Browser의 동작 원리가 궁금해서 시작!  

>이 글은 이스라엘 개발자 탈리 가르시엘(Tali Garsiel)이 html5rocks.com에 게시한  
["How Browsers Work: Behind the scenes of modern web browsers"](https://www.html5rocks.com/en/tutorials/internals/howbrowserswork/)을 읽고 **조금씩 꾸준히 작성될 예정**입니다.

>탈리 가르시엘은 몇 년간 브라우저 내부와 관련된 공개 자료를 확인하고, 
C++ 코드 수백만 줄 분량의 WebKit이나 Gecko 같은 오픈소스 렌더링 엔진의 소스 코드를 직접 분석하면서 어떻게 브라우저가 동작하는지 파악했다고 합니다.

# 브라우저는 어떻게 동작하는가?
## 1. 브라우저의 주요 기능
  - 브라우저의 주요 기능은 사용자가 선택한 자원을 서버에 요청하고 브라우저에 표시하는 것이다.
  - 자원은 보통 HTML 문서지만 PDF나 이미지 또는 다른 형태일 수 있다.
  - 자원의 주소는 URI(Uniform Resource Identifier)에 의해 정해진다.

## *Reference
- https://www.html5rocks.com/en/tutorials/internals/howbrowserswork/
- https://d2.naver.com/helloworld/59361
