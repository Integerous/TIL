# 텍스트 템플릿 엔진 비교 (작성중)


Template Engine?
템플릿 엔진은 "프로그램 로직" <-> "프리젠테이션" 계층을 분리하기 위한 수단
예를 들어 Controller -> View로 데이터를 던지면 어떻게 계층을 분리하여 쉽게 표현할지를 도와주는 "도구"
프리젠테이션 계층에서 로직을 쉽게 표현하고 개발의 유연성을 향상 시킴 & 유지보수 효율 향상 (과연?)

## Mustache

## Handlebars

- Mustache의 파생인 handlebars.js의 자바 구현체인 handlebars.java 사용
- handlebars.js는 Javascript 라이브러리로 렌더링이 끝난 뒤(즉, HTML Dom이 다 그려진 뒤)에 서버 통신 없이 화면 변경이 필요한 경우를 위한 것(출처 : https://jojoldu.tistory.com/23)
- 컴파일된 템플릿에 모델을 반영하여 뷰를 구성하는 방식이죠. 모델을 한번 뷰에 반영해주고 끝이기 때문에 초기 랜더링 속도면에서 더 유리하지만, 양방향 구조가 아니기 때문에 이미 랜더링 된 뷰의 내용을 바꾸고 해당 내용을 다시 모델에 반영하려면 별도의 작업이 필요합니다.
(출처 : https://tmondev.blog.me/220398995882 )
- 가장 큰 차이는 Mustache가 codeless를 지향하고 있어 코드의 중간 가공에 제약이 많은 반면 Handlebars.js는 사용자 정의 함수라고 볼 수 있는 헬퍼(Helper) 개념을 도입하여 이 제약을 상당히 덜어냈다는 점을 들 수 있겠습니다. 서버에서 받아온 json 데이터를 필요에 따라 간편하게 가공하여 사용할수 있다는 점이 Handlebars.js의 가장 큰 매력이지만, 템플릿 자체에 선언하는 로직은 최소화 함으로써 Mustache가 애초에 표방한 logic-less의 원칙을 크게 훼손하지 않았다는 점도 Handlebars의 매력이라고 할 수 있겠습니다.
(출처 : https://tmondev.blog.me/220398995882 )

- 현재까지도 꾸준하게 업데이트 되고 있는 템플릿 엔진은 Thymeleaf, Handlebars 이며 이 중 하나를 선택하시면 됩니다. 
개인적으로는 Handlebars를 추천합니다. 
(Spring 진영에선 Thymeleaf를 밀고 있습니다.) 
(1) 문법이 다른 템플릿엔진보다 간단하고 
(2) 로직 코드를 사용할 수 없어 View의 역할과 서버의 역할을 명확하게 제한할 수 있으며 
(3) Handlebars.js와 Handlebars.java 2가지가 다 있어, 하나의 문법으로 클라이언트 템플릿/서버 템플릿을 모두 사용할 수 있습니다. 
개인적으로 View 템플릿엔진은 View의 역할에만 충실하면 된다고 생각합니다. 
너무 많은 기능을 제공하면 API와 View템플릿엔진, JS가 서로 로직을 나눠갖게 되어 유지보수하기가 굉장히 어렵습니다.
(출처 : https://jojoldu.tistory.com/23)

## Thymeleaf
