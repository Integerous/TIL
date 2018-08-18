# 정적 블로그 만들기 (feat. Hugo & Github Page)
>주소가 github.io인 개발 블로그들이 눈에 많이 띄었다.  
>그래서 찾아 헤매던 도중 Jekyll, Hexo, Hugo 등 Static Site Generator의 존재를 알게 되었다.  
>Static Site Generator와 Github Page의 조합으로 Devlog로 사용할 개인 블로그를 만들기로 했다.  
>놀다 지친 여름휴가 막바지에 집중공략을 시작했다.

## 1. Static Site Generator 란?
[이 글](https://blog.nacyot.com/articles/2014-01-15-static-site-generator/)이 정적 웹사이트 생성기와 동적 웹사이트 생성기의 차이를 잘 설명해주고 있다.

## 2. Static Site Generator 선택 과정
[이 곳](https://www.staticgen.com/)에서 모든(?) Static Site Generator들을 한눈에 볼 수 있었다.  
[이 글](http://tadakichi.tistory.com/188)에서 가장 많이 사용하는 Jekyll , Hexo, Hugo를 비교하여 참고하였다.

## 3. Hugo, 너로 정했다!
Github Page와 환상의 조합인 Jekyll 대신 **Hugo를 선택한 이유**

1. Go로 제작되었다. (Go를 공부중이다.)
2. Hugo는 런타임에 다른 의존성이 필요하지 않아 빌드시간이 세계에서 제일 빠르다.("Hugo is the world’s fastest static website engine.")
3. 한글 Reference가 거의 없는 오픈소스를 사용하며 오픈소스에 기여할 기회를 찾아보고자 선택.

## 4. Hugo + Github Page 만드는 과정

### 4.1. Hugo 설치
>나도 멋깔나게 `$ brew install hugo`를 mac 터미널에 입력해서 설치하고 싶었다.  
>하지만 현실은 WINDOWS...  
>Giraffe Academy의 [Windows에서 Hugo설치하기](https://gohugo.io/getting-started/installing#windows) 이 영상 하나면 설치는 쉽다. (젊은 형아가 영어로 설명해줌)
- [hugo 공식 깃헙](https://github.com/gohugoio/hugo/releases)에서 운영체제에 맞는 최신버전 다운로드
- `C:\Hugo\bin` 디렉토리 생성해서 다운받은 압축파일 해제
- 어느 위치에서나 Hugo가 실행될 수 있도록`$ set PATH=%PATH%;C:\Hugo\bin` 명령으로 환경변수에 `C:\Hugo\bin`추가
- 명령 프롬프트에 `$ hugo version` 혹은 `$ hugo help`로 동작 확인

### 4.2. Github 저장소 2개 만들기
- 하나는 Hugo의 컨텐츠와 소스파일들을 포함할 `<YOUR-PROJECT>` 저장소 생성 (나의 경우 `blog`라는 이름으로 생성)
- 다른 하나는 렌더링된 버전의 Hugo 웹사이트를 포함할 `<USERNAME>.github.io` 저장소 생성 (`integerous.github.io`)

### 4.3. Directory Structure 구성
- `$ hugo new site blog` 명령으로 로컬에서 컨텐츠를 관리하기 위한 장소(Hugo/blog) 생성
- `C:\Hugo\blog`에서 `$ dir`로 directory structure를 확인할 수 있다.

### 4.4. 테마 다운로드 및 설정
- https://themes.gohugo.io/ 에서 원하는 테마를 선택한다.
- 선택한 테마의 github에서 저장소 주소를 복사한다.
- `C:\Hugo\blog\themes`에 선택한 테마를 clone한다. `$ git clone https://github.com/선택한/테마`
- `config.toml` 파일을 선택한 테마의 설명서에 따라 수정한다.

### 4.5. Remote와 Submodule 설정
- 깃헙에 만든 `blog 저장소`를 local의 blog 디렉토리의 remote로 등록한다.
  - `C:\Hugo\blog` 로 이동
  - `$ git init`
  - `$ git remote add origin git@github.com:integerous/blog.git`
- `integerous.github.io 저장소`를 blog의 submodule로 등록한다.
  - `$ git submodule add -b masater git@github.com:integerous/integerous.github.io.git public`
  - 이렇게 함으로써 `hugo` 명령으로 `public`에 웹사이트를 만들 때, 만들어진 `public` 디렉토리는 다른 remote origin을 가질 것이다.

### 4.6. 컨텐츠 생성
- `$ hugo new post/test1.md` 명령으로 파일을 생성하면 `\content\post\test1.md`

### 4.7. 컨텐츠 업로드 (블로그에)
- Linux는 http://gohugo.io/tutorials/github-pages-blog/ 의 deploy.sh 파일을 사용
- `C:\Hugo\blog`로 이동
- `$ hugo -t 테마이름` 명령을 통해 테마가 적용된 블로그 내용을 public에 생성한다.
- `$ cd public` public 디렉토리로 이동하여
- `$ git add .` 수정된 파일들을 index에 올린다.
- `$ git commit -m "커밋메세지"` 변경 내용을 commit하고
- `$ git push origin master` commit을 Integerous.github.io에 푸시
- `blog 저장소`에도 변경내용 푸시
  - `$ git add .`
  - `$ git commit -m "커밋메세지"`
  - `$ git push origin master`

# 5. Github 스타일 댓글 기능 추가하기
>[아웃사이더님의 블로그 글](https://blog.outsider.ne.kr/1356?category=1)을 참고하여 적용했다.  

### 5.1. 작동 원리
[Utterance 프로젝트](https://utteranc.es/)의 작동 방식을 소개하자면,  
[Github의 이슈 검색 API](https://developer.github.com/v3/search/#search-issues)를 사용해서 각 글에 해당하는 이슈가 생성되고(최초 댓글 작성 시),  
댓글들은 해당 글로 생성된 이슈에 대한 댓글로 추가되는 방식이다. 댓글은 [Primer](https://primer.github.io/)를 이용해서 Github 스타일로 보여진다.

### 5.2. 사용 방법
1. Github에 public 저장소를 만들고(blog-comments 등으로)
2. [Utterance document](https://utteranc.es/)에서 방금 만든 저장소를 입력하고(나의 경우 Integerous/blog-comments)
3. 블로그 글과 Github 이슈를 매핑할 방법 6가지 중 한 가지를 선택하면
4. 밑에 아래와 같은 script를 자동으로 생성해준다.
    ~~~javascript
    <script src="https://utteranc.es/client.js"
            repo="integerous/blog-comments"
            issue-term="pathname"
            crossorigin="anonymous"
            async>
    </script>
    ~~~
5. 위의 script를 본인의 블로그 템플릿중 원하는 위치에 넣으면
6. 끝!


## *Reference
- [Hosting on Github](https://gohugo.io/hosting-and-deployment/hosting-on-github/)
- [Jekyll, Hexo, Hugo 간단 비교 글](http://tadakichi.tistory.com/188)
- [Github Page에 Hugo 올리기](https://github.com/sabzil/blog/blob/master/content/post/tips/hugo.md)
- [페이스북 댓글을 Utterances로 교체하기](https://blog.outsider.ne.kr/1356?category=1)
