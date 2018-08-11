# Static Site Generator (정적 사이트 생성기)
>주소가 github.io인 개발 블로그들이 눈에 많이 띄었다.  
>그래서 찾아 헤매던 도중 Jekyll, Hexo, Hugo 등 Static Site Generator의 존재를 알게 되었다.  
>놀다 지친 여름휴가 막바지에 집중공략을 시작했다. (블로그도 다시 시작해볼겸)

## 1. Static Site Generator 란?

## 2. Static Site Generator 종류
[이 곳](https://www.staticgen.com/)에서 모든(?) Static Site Generator들을 한눈에 볼 수 있었다.


## 3. Hugo! 너로 정했다!


## 4. Hugo + Github Page 만들어가는 과정

### 4-1. Hugo 설치
나도 멋깔나게 `brew install hugo`를 mac 터미널에 입력해서 설치하고 싶었다. (맥북 너무 사고싶다ㅠ)  
하지만 현실은 Windows.
[Windows에서 Hugo설치](https://gohugo.io/getting-started/installing#windows) 이 영상 하나면 설치는 쉽다. (젊은 형아가 영어로 설명해줌)

- [hugo 공식 깃헙](https://github.com/gohugoio/hugo/releases)에서 운영체제에 맞는 최신버전 다운로드
- `C:\Hugo\bin` 디렉토리 생성해서 다운받은 압축파일 해제
- 어느 위치에서나 hugo가 실행될 수 있도록`set PATH=%PATH%;C:\Hugo\bin`을 환경변수에 추가
- 명령 프롬프트에 `hugo version` 혹은 `hugo help`로 동작 확인
- `C:\Hugo\Sites` 생성
- `cd C:\Hugo\Sites`로 이동
- `hugo new site devlog` 명령으로 로컬에서 컨텐츠를 관리하기 위한 장소(Hugo/Sites/devlog) 생성

## *Reference
- [Jekyll, Hexo, Hugo 간단 비교 글](http://tadakichi.tistory.com/188)

