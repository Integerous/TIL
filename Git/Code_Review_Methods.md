>작성중 

# 실무에 사용할 코드리뷰 방법 알아보기

## 1. github을 기반으로 한 코드 리뷰 방법
>코드스쿼드에서 진행한 강의
>https://www.youtube.com/watch?v=a5c9ku-_fok

~~~
프로젝트 원본 저장소 = A 저장소
ryan의 저장소 = B 저장소
A 저장소에 생성될 브랜치 = ryan branch
B 저장소에 생성될 작업용 브랜치 = ryantest1 branch 
~~~

1. A 저장소에 브랜치 "ryan" 생성
2. A 저장소의 ryan 브랜치를 B 저장소로 fork 해오기
3. B 저장소에서 local로 ryan 브랜치만 clone -> `git clone -b ryan --single-branch 브랜치url`
4. 모든 브랜치 확인 -> `git branch -a`
4. local에서는 ryan 브랜치를 master로 생각하고 작업용 branch 추가하기 -> `git checkout -b ryantest1`
5. 변경된 내용 확인 -> `git status`
6. 변경된 내용 commit 준비 -> `git add .`
7. commit 하기 -> `git commit -m "커밋 내용"`
8. origin에 ryantest1 push -> `git push origin ryantest1`
9. B 저장소에서 A 저장소로 pull request (github 에서)
10. A 저장소에서는 코드리뷰 후 merge
11. A 저장소의 최신화된 ryan 브랜치와 local의 구식 ryan 브랜치의 버전을 맞춰주어야 함
  - ryan 브랜치로 이동 -> `git checkout ryan`
  - local에서 A 저장소를 원격저장소로 추가(최초1회만) -> `git remote add upstream(이름명명가능) A저장소url`
  - upstream의 ryan 브랜치만 내 local로 가져오기 `git fetch upstream ryan`
12. upstream의 ryan 브랜치의 코드로 local의 ryan 브랜치 업데이트하기 -> `git rebase upstream/ryan`
13. 여기까지가 한 싸이클이다. 이후에는
  - local에서 ryantest2 branch를 만들고 작업해서 Commit
  - B 저장소로 push
  - A 저장소로 pull request
  - A 저장소에서 merge
  - A 저장소에서 local로 fetch
  - rebase를 통해 A 저장소의 최신화된 ryan 브랜치로 local 브랜치를 최신화

14. history가 꼬였을 때(내가 작업하는 중에 누군가 원격 저장소에 최신버전을 푸시했을 때) 해결 방법
  - 나의 작업을 원격 브랜치의 최신 상태를 기반으로 하게 만든다.
  - 방법은 여러 가지지만 가장 간단한 방법은 `rebase` 이다.
  - `git fetch; git rebase origin/master; git push`
  - 간단하게는 `git pull --rebase; git push;`

15. master 브랜치에 있을때만 push와 pull을 수행하면, master는 항상 원격 브랜치의 상태와 항상 최신의 상태로 유지될 수 있다.
16. 새로운 작업들을 원격 저장소로 push하기 위해서는 저장소의 최근 변경들을 합치기만 하면 된다.
  - 이 말은 rebase와 merge 둘 다 사용가능하다는 것인데 어떤 것을 사용해야 하는가?
  - rebase는 커밋트리가 깔끔하게 한 줄로 정리된다. 때문에 이력 보존이 안된다.
  - 즉, 커밋트리에 관한 입맛에 맞게 사용하면 된다.


https://medium.com/axisj/github-fork-%EC%97%90%EC%84%9C-pull-request-%EA%B9%8C%EC%A7%80-%EA%B7%B8%EB%A6%AC%EA%B3%A0-merge-a22bdd097283



>git 초보를 위한 pull request 방법  
https://wayhome25.github.io/git/2017/07/08/git-first-pull-request-story/

>Pull Request를 이용한 개발 흐름의 적용  
https://blog.outsider.ne.kr/1199

>git 실습  
https://learngitbranching.js.org/

>카카오의 코드리뷰  
http://tech.kakao.com/2016/02/04/code-review/

>네이버의 Gerrit을 이용한 코드리뷰  
https://d2.naver.com/helloworld/6033708

>코드리뷰 가이드라인  
http://flyburi.com/m/576

