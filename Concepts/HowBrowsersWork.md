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
### Rendering Engine
  - 종류
    - 파이어폭스는 모질라에서 만든 Gecko 엔진 사용.
    - 사파리와 크롬은 Webkit 엔진 사용.
      - Webkit은 원래 리눅스 플랫폼에서 동작하기 위해 제작된 오프소스 엔진이다.
      - 그런데 애플이 Mac과 Windows에서 사파리 브라우저를 지원하기 위해 수정을 가했다.(webkit.org)
  - 동작 과정
    - Rendering Engine은 네트워크 계층으로부터 요청한 문서를 얻는 것으로 시작.
    - 문서의 내용은 보통 8kb 단위로 전송된다.
    - 다음은 Rendering Engine의 기본 동작 과정  
      
    ![Rendering Engine Basic Flow](https://www.html5rocks.com/en/tutorials/internals/howbrowserswork/flow.png)  
      
    - Rendering Engine은 HTML 문서를 파싱하고 **"content tree"** 라 불리우는 하나의 tree안에 요소들을 DOM nodes로 변환한다.
    - 그리고 외부 CSS 파일들과 style 요소에 포함된 style data를 파싱한다.
    - Style information과 HTML 표시 규칙은 **"render tree"** 라고 부르는 또 다른 트리를 생성하는데 사용된다.
    - Render tree는 색상이나 면적과 같은 시각적 속성이 있는 사각형을 포함하고 있는데 정해진 순서대로 화면에 표시된다.
    - Render tree 생성이 끝나면 "layout" 과정을 거친다. 이것의 의미는 각 노드가 화면의 정확한 위치에 표시되는 것이다.
    - 그 다음 단계는 Painting이다. 이는 UI backend 계층을 사용해서 render tree의 각 노드를 가로지르며 형상을 만들어 내는 그리기 과정이다.
    - 이러한 과정이 점진적으로 진행되는 점을 이해하는 것이 중요하다.
    - 더 나은 UX를 위해서 Redering Engine은 최대한 빨리 컨텐츠를 화면에 뿌려야하므로 모든 HTML이 파싱되는 것을 기다리지 않고 render tree의 빌드와 layout이 시작된다.
    - 네트워크로 부터 나머지 컨텐츠들이 전송될 동안 일부 컨텐트는 파싱되어 화면에 표시하는 것이다.
## *Reference
- https://www.html5rocks.com/en/tutorials/internals/howbrowserswork/
- https://d2.naver.com/helloworld/59361
