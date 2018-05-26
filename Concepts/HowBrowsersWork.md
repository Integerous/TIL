> *개인적인 학습을 위해 작성한 내용입니다. 옳은 내용만 작성하려 노력했지만 내용에 오류가 있을 수 있습니다.*

>Web이 어떻게 동작하는지 공부하는 과정에서 Browser의 동작 원리가 궁금해서 시작!  

>이 글은 이스라엘 개발자 탈리 가르시엘(Tali Garsiel)이 html5rocks.com에 게시한  
["How Browsers Work: Behind the scenes of modern web browsers"](https://www.html5rocks.com/en/tutorials/internals/howbrowserswork/)을 읽고 **조금씩 꾸준히 작성될 예정**입니다.

>탈리 가르시엘은 몇 년간 브라우저 내부와 관련된 공개 자료를 확인하고, 
C++ 코드 수백만 줄 분량의 WebKit이나 Gecko 같은 오픈소스 렌더링 엔진의 소스 코드를 직접 분석하면서 어떻게 브라우저가 동작하는지 파악했다고 합니다.

# 브라우저는 어떻게 동작하는가?
## 01. 브라우저의 주요 기능
  - 브라우저의 주요 기능은 사용자가 선택한 자원을 서버에 요청하고 브라우저에 표시하는 것이다.
  - 자원은 보통 HTML 문서지만 PDF나 이미지 또는 다른 형태일 수 있다.
  - 자원의 주소는 URI(Uniform Resource Identifier)에 의해 정해진다.

## 02. 브라우저의 기본 구조
  - **User Interface** - 페이지가 출력되는 창을 제외한 나머지.
  - **Browser Engine** - UI와 렌더링 엔진 사이의 동작을 제어.
  - **Rendering Engine** - 요청한 컨텐츠를 출력. HTML을 요청하면 HTML과 CSS를 파싱하여 화면에 출력.
  - **Networking** - HTTP요청과 같은 네트워크 호출에 사용.
  - **UI Backend** - 플랫폼에서 명시하지 않은 일반적인 인터페이스로서 콤보 박스와 창 등 기본적인 장치(위젯)를 그림. OS 사용자 인터페이스 체계를 사용.
  - **JavaScript Interpreter** - 자바스크립트 코드를 파싱하고 실행.
  - **Data Storage** - 자료를 저장하는 계층. 브라우저는 쿠키 같이 모든 종류의 데이터를 로컬에 저장할 필요가 있고, localStorage, IndexedDB, WebSQL 그리고 FileSystem 등의 저장 매커니즘을 지원한다.  
    
    ![Browser components](https://www.html5rocks.com/en/tutorials/internals/howbrowserswork/layers.png)

## *Reference
- https://www.html5rocks.com/en/tutorials/internals/howbrowserswork/
- https://d2.naver.com/helloworld/59361
