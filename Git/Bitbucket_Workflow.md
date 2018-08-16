# Cloudcash Workflow

## 전체 흐름
  ~~~
  0. 최초 설정
  1. Issue 등록
  2. Issue 작업
  3. Pull Request
  4. PR Review
  5. Issue 반영
  (번외 #1) CI/CD Tool
  (번외 #2) 해결해야 할 문제
  ~~~
  
## 0. 최초 설정
## 1. Issue 등록
>새로운 추가될 가능, 개선 해야할 기능, 버그 등 모든 활동을 Issue로 등록하고 그 Issue를 기반으로 작업을 진행합니다.  
>Issue 등록은 템플릿을 사용하는 방법이 효율적이지만, 현재 Bitbucket에서는 템플릿 기능을 제공하지는 않는 것으로 보입니다.

## 2. Issue 작업
## 3. Pull Request (이하 PR)
## 4. PR Review
## 5. Issue 반영
## (번외 #1) CI/CD Tool
>Bitbucket에서는 클라우드에서 사용하는 CI/CD 도구인 Pipelines을 제공하지만 한달에 50분만 무료입니다.  
>가장 보편적으로 사용하는 오픈소스 CI/CD 도구인 Jenkins를 기준으로 작성했습니다.
## (번외 #2) 해결해야 할 문제
1. Bitbucket에서 특정 branch만 fork되지 않는 문제 해결
    - 전체 Repository를 fork 해온 후,
    - `develop` branch만 local로 clone 후 작업하여 `develop` branch만 Pull Request. 
    - 주의할 점은, 각 개인 원격 Repository에 함께 fork 되어온 `master` branch에 작업하지 않을 것.
2. Pull Request 와 Issue 생성 시 템플릿 생성/사용 불가능 문제 해결
  - 팀 내에서 공용 템플릿 작성하여 Issue 등록 시 직접 복사/붙여넣기 하여 사용
  - 템플릿 사용의 장점
    - 버그 리포트의 경우 템플릿을 활용하여 server log, response body 등 필요한 정보들을 템플릿에 강제하여 정보 누락을 방지할 수 있고,
    - 마크다운 문법을 템플릿에 적용해놓으면 훨씬 빠르게 리포트 작성 가능하며, 일관된 문서작성 가능. 또한 리포트 자체를 데이터로 활용 가능.
  - 템플릿 사용의 단점
    - 기능이 제공되지 않으면 손수 복사/붙여넣기로 사용하기 번거롭다.


1. CloudCash 저장소(원본저장소) 생성
2. Develop 브랜치 생성 (깃헙에서)
3. Develop 브랜치를 각자의 저장소로 Fork (깃헙에서)
4. 각자의 저장소에서 각자의 local 로 develop 브랜치만 Clone
    - $git clone -b develop - -single-branch branchURL(반드시 본인저장소URL ! )
5. 모든 브랜치 확인
    - $git branch -a
6. (최초 1회만)CloudCash 저장소의 최신화된 develop브랜치와 local의 develop브랜치를 동기화해주기 위해 원격저장소 설정
    - $git remote add CCproject(별명) CloudCash저장소URL -> 본인저장소URL로 하지 말것.
    - $git remote -v 로 원격저장소 확인
7. Local에서 develop 브랜치는 master브랜치라 생각하고 작업용 브랜치 생성
    - $git checkout -b ryanwork1
8. 작업
9. 변경된 내용 확인 $git status
10. 변경된 내용 add, commit, push
    - $git add .
    - $git commit -m '커밋내용'
    - $git push origin ryanwork1
11. CloudCash 저장소로 Pull Request (깃헙에서)
12. 관리자는 코드 리뷰 후 develop 브랜치로 Merge (깃헙에서)
13. — 다른 팀원이 원본저장소에 커밋 추가했을시 — 
14. 설정된 원격저장소(CloudCash저장소)의 develop 브랜치로 부터 local의 develop브랜치 동기화. (우선 HEAD가 develop으로 가도록하고!)
    - $git checkout develop
    - $git fetch CCproject(별명) develop
    - $git branch -a 로 브랜치 확인
    - $git merge CCproject/develop
    - 또는 merge 대신 $git rebase CCproject/develop
    - 또는 Pull로 한번에 동기화 $git pull CCproject develop
15. Merge 된 이후에는 ryanwork1 브랜치 삭제
    - $git branch -d ryanwork1
16. 이후 작업은 다시 pull로 CCproject을 local로 동기화한 후 작업용 브랜치를 만드는 6번부터 반복한다.




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
23. feature 브랜치는 develop/master 브랜치에 merge되면 저장소에서 삭제한다.
24. history를 한 줄로 관리하려면 merge보다 rebase나 cherry-pick을 사용할 수 있다. feature 브랜치에서 작업을 마치고 master/develop 브랜치에 merge 할 때 master/develop 브랜치를 기반으로 rebase한다.
25. 릴리즈 버전은 언제든 돌아갈 수 있도록 태그를 다는 것이 좋다. 그리고 배포할 릴리즈 버전이 준비되었다면, git을 사용하지 않는 사람을 위해 소스코드 스냅샷을 압축한다. `$git archive`명령으로 압축할 수 있다.

## Reference
- [Learn about code review in Bitbucket](https://www.atlassian.com/git/tutorials/learn-about-code-review-in-bitbucket-cloud)
