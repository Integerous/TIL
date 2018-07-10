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
    - local에서 A 저장소를 원격저장소로 추가(최초1회만) -> `git remote add myfork(이름명명가능) A저장소url`
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
17. `$git log --no-merges issue54..origin/master` - 피처브랜치에서 작업을 마치고 merge할 때 어떤 내용이 merge되는지 git log명령으로 확인. 뒤의 브랜치(origin/master)에 속한 커밋 중 앞의 브랜치(issue54)에 속하지 않은 커밋을 검색
    - `fetch -> log -> merge`의 순서로 작업
18. `$git push -u origin featureB:featureBee`- featureB에서 작업했는데 서버에는 브랜치가 featureBee라고 되어있을 때 이런식으로 refspec 사용
    - `-u` 옵션은 `--set-upstream`의 짧은 표현
19. 내 브랜치와 A저장소 브랜치 충돌 시, 내 브랜치를 origin/master에 rebase해서 충돌을 해결하고 다시 pull request를 보낸다.
    1. `$git checkout featureA`
    2. `$git rebase origin/master`
    3. `$git push -f myfork featureA` - 브랜치를 rebase했기 때문에 push 할 때 `-f`옵션으로 강제로 기존 서버에 있던  featureA브랜치의 내용을 덮어 써야한다. 아니면 새로운 브랜치를 서버에 push한다.

20. featureB의 pull request를 확인한 프로젝트관리자가 일부 수정을 요청했다. featureB 담당자는 하는 김에 featureB 브랜치를 프로젝트의 최신 master 기반으로 옮긴다. featureBv2 브랜치를 새로 만들고, featureB의 커밋들을 모두 Squash해서 merge하고, 만약 충돌이 나면 해결하고 수정 사항을 구현하고 새 브랜치를 push한다.  
~~~git
$git checkout -b featureBv2 origin/master
$git merge --squash featureB
  #(change implementation)
$git commit
$git push myfork featureBv2
~~~  
--squash 옵션은 해당 브랜치의 모든 커밋들을 하나의 커밋으로 합쳐서 merge한다. 이때, merge 커밋은 만들지 않고, 수정할 부분도 수정한 후에 merge 할 수 있다. `--no-commit` 옵션을 추가하면 커밋을 합쳐놓고 자동으로 커밋하지 않는다.
21. 프로젝트 저장소의 Master브랜치와 develop브랜치 두 가지를 두고, master브랜치는 아주 안정적인 버전을 릴리즈 할 때 사용하고, develop 브랜치는 새로 수정된 코드를 통합할 때 사용한다. 릴리즈 할 수준이 되면 master 브랜치를 develop 브랜치까지 fast-forward시킨다.
22. 더 나아가 integrate 브랜치를 추가할 수 있다. master - develop - integrate - feature 브랜치를 만들어 feature에서 작업한 것을 integrate에 merge하여 feature 브랜치가 검증되면 develop 브랜치에 merge 한다. 그리고 develop 브랜치에서 충분히 안정성이 검증되면 그때 master 브랜치에 merge한다.





https://medium.com/axisj/github-fork-%EC%97%90%EC%84%9C-pull-request-%EA%B9%8C%EC%A7%80-%EA%B7%B8%EB%A6%AC%EA%B3%A0-merge-a22bdd097283



>git 초보를 위한 pull request 방법  
https://wayhome25.github.io/git/2017/07/08/git-first-pull-request-story/

>Pull Request를 이용한 개발 흐름의 적용  
https://blog.outsider.ne.kr/1199




# git 실습  
>https://learngitbranching.js.org/


## 원격 저장소 추적하기  
- `$git checkout -b foo origin/master` - 로컬브랜치 foo를 생성하고 원격브랜치 origin/master를 추적하게 설정  
- `$git branch -u origin/master foo` - foo 브랜치가 origin/master를 추적하도록 설정  
- `$git branch -u origin/master` - foo가 현재 작업중인 브랜치라면 생략가능  

## git push의 인자들
- `$git push <remote> <place>` - place 인자는 git에게 '어디서부터 오는 커밋인지', '어디로 가는 커밋인지', 두 저장소 간 동기화 작업을 할 장소지정. <remote>와 <place>를 모두 지정했기 때문에 git은 현재 체크아웃한 브랜치는 무시하고 명령 수행  
- `$git push origin master` - 내 저장소의 master 브랜치에서 모든 커밋을 수집하고, origin의 master 브랜치로 가서 부족한 커밋을 채워넣는다.
- `$git push origin <source>:<destination>` - 커밋의 근원이 되는 source와 목적지가 되는 destination을 다르게 하고싶을 때
- `$git push origin foo^:master` - foo의 부모위치에서 원격으로 커밋을 업로드하고 destination인 master 브랜치를 갱신
- `$git push origin master:newBranch` - push할 destination이 없는 경우 newBranch처럼 새 브랜치명 적으면 만들어줌

## git fetch의 인자들
- `$git fetch origin foo` - 원격저장소의 foo브랜치에서 현재 로컬에 없는 커밋들을 가져와 로컬의 'origin/foo' 브랜치 아래에 추가. 즉, 커밋들을 foo 브랜치에서만 내려받은 후 로컬의 origin/foo 브랜치에만 적용
- `$git fetch origin foo~1:bar` - foo~1을 origin의 place로 지정하고 커밋들을 내려받아 bar(로컬브랜치)에 추가. bar가 없는 경우 알아서 만들어줌
- `$git fetch`- 인자 없이 fetch를 하면 원격저장소에서 모든 원격브랜치들로 커밋들을 내려받는다.

## source 인자 없으면
- `$git push origin :foo` - 원격저장소의 foo브랜치 삭제 (foo에 null을 push한 개념)
- `$git fetch origin :bar` - 로컬저장소에 bar 브랜치를 만든다. (기괴하다..)

## git pull의 인자들
- `$git pull origin master:foo` - 로컬에 foo브랜치를 만들고, 원격저장소의 master로부터 로컬의 foo브랜치에 커밋들을 받는다. 그리고 foo브랜치를 현재 체크아웃된 bar브랜치에 병합한다.


# Git & GitHub Crash Course For Beginners  
>https://www.youtube.com/watch?v=SWYqp7iY_Tc

$ git init    // Initialize local git repository
$ git add <file>    // Add file(s) to index
$ git status    // Check status of working tree
$ git commit    // Commit changes in index
$ git push    // Push to remote repository
$ git pull    // Pull latest from remote repository
$ git clone   // Clone repository into a new directory

- 사용 시작
$ touch index.html  
$ touch app.js  
$ git init  
$ git config --global user.name 'Ryan Han'  
$ git config --global user.email 'ryanhan@cloudcash.kr'  
$ git add index.html  
$ git status  
$ git rm --cached index.html    // to unstage  
$ git status  
$ git add \*.html   // 모든 html파일 add  
$ git add .     // 모든 파일 add  
$ git commit -m 'changed index.html'  
$ git branch login  
$ git commit -m 'another change'  
$ git checkout login  
$ git checkout master    
$ git merge login  
$ git remote    // remote repositorys 확인  
$ git remote add origin https:/github.com/integerous/myappsample.git  
$ git remote
$ git push -u origin master




>카카오의 코드리뷰  
http://tech.kakao.com/2016/02/04/code-review/

>네이버의 Gerrit을 이용한 코드리뷰  
https://d2.naver.com/helloworld/6033708

>코드리뷰 가이드라인  
http://flyburi.com/m/576

