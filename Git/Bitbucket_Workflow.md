# Dev Team Workflow

## 전체 흐름
  ~~~
  0. 최초 설정
  -- 작업 흐름 요약
  1. Issue 등록
  2. Issue 작업
  3. Pull Request
  4. PR Review
  5. Issue 반영
  (번외 #1) CI/CD Tool
  (번외 #2) 해결해야 할 문제
  ~~~
-----------------------------------
-----------------------------------  
# 0. 최초 설정
## 0.1. 프로젝트 관리자
- Bitbucket에 `프로젝트 저장소` 생성
- 작업용 `develop` 브랜치 생성 (`master` 브랜치는 배포용)
  - 프로젝트 저장소의 `Master` 브랜치와 `develop` 브랜치 두 가지를 두고, `master` 브랜치는 아주 안정적인 버전을 릴리즈 할 때 사용하고, `develop` 브랜치는 새로 수정된 코드를 통합할 때 사용한다. 릴리즈 할 수준이 되면 `master` 브랜치를 `develop` 브랜치까지 **fast-forward**시킨다.
  - 더 나아가 `integrate` 브랜치를 추가할 수 있다. master - develop - integrate - feature 브랜치를 만들어 feature에서 작업한 것을 integrate에 merge하여 feature 브랜치가 검증되면 develop 브랜치에 merge 한다. 그리고 develop 브랜치에서 충분히 안정성이 검증되면 그때 master 브랜치에 merge한다.
  - feature 브랜치는 develop/master 브랜치에 merge되면 저장소에서 삭제한다.
  - 릴리즈 버전은 언제든 돌아갈 수 있도록 태그를 다는 것이 좋다. 그리고 배포할 릴리즈 버전이 준비되었다면, git을 사용하지 않는 사람을 위해 소스코드 스냅샷을 압축한다. `$git archive`명령으로 압축할 수 있다.

## 0.2. 프로젝트 팀원
1. `프로젝트 저장소`를 `개인 원격 저장소`로 ***Fork*** (Bitbucket에서)
2. 각자의 컴퓨터에 `개인 로컬 저장소`로 사용할 경로 선택 또는 생성
3. `개인 원격 저장소`에서 `개인 로컬 저장소`로 `develop` 브랜치만 ***Clone***
    - `$ git clone -b develop --single-branch branchURL(반드시 본인 원격저장소 URL)`
4. 모든 브랜치 확인
    - `$ git branch -a`
    - 현재 `개인 로컬 저장소`에는 `develop` 브랜치만 존재해야 한다. 
5. `프로젝트 저장소`의 최신화된 `develop` 브랜치와 `개인 로컬 저장소`의 `develop` 브랜치를 동기화하기 위해 ***Remote***(원격저장소) 설정
    - `$ git remote add cloudcash-ryan(별명) 프로젝트저장소URL` -> 본인 원격저장소URL로 하지 말것.
    - `$ git remote -v` 로 원격저장소 확인
    - 확인 결과 `cashcloud`(프로젝트 저장소)와 `origin`(개인 원격 저장소)만 나와야 한다.

## 0.3. 저장소, 브랜치 정리
### 저장소
1. `프로젝트 저장소` - Bitbucket에 관리자가 생성한 `프로젝트 저장소`.
2. `개인 원격 저장소` - 각 개인이 Bitbucket의 `프로젝트 저장소`를 개인 계정으로 fork 해온 저장소.
3. `개인 로컬 저장소` - 각 개인의 컴퓨터에 `개인 원격 저장소`로부터 clone 해온 저장소.
### 브랜치
1. `master 브랜치` - develop  브랜치에서 충분히 검증된 버전을 `배포하는 브랜치`
2. `develop 브랜치` - 각 팀원들의 feature 브랜치를 `통합하고 테스트하는 브랜치`
3. `feature 브랜치` - 이슈 기반으로 브랜치명을 생성한 `작업용 브랜치`
-----------------------------------
-----------------------------------  
-----------------------------------
# Workflow 요약 (최초 설정 후)

1. `개인 로컬 저장소` 최신화
    - 최신화된 `프로젝트 저장소`의 변경 사항을 `개인 로컬 저장소`에 ***pull**** 혹은 ***fetch & merge*** 하여 최신화
    - 작업 시작 전 `개인 로컬 저장소`를 최신화하는 습관 필요
2. `feature` 브랜치 생성
    - `feature` 브랜치의 이름은 등록된 Issue 기반으로 생성하여 `브랜치명 = 작업명`이 되도록하여 브랜치에 정체성 부여
3. 작업    
4. 작업한 `feature` 브랜치를 `개인 원격 저장소`에 ***push***
5. `개인 원격 저장소`에 push한 `feature` 브랜치를 `프로젝트 저장소`의 `develop` 브랜치로 ***Pull Reqeust***
6. 1~5 반복
-----------------------------------
-----------------------------------
-----------------------------------  
# 1. Issue 등록
>새로운 추가될 가능, 개선 해야할 기능, 버그 등 모든 활동을 Issue로 등록하고 그 Issue를 기반으로 작업을 진행합니다.  

- Bitbucket에서 Issue 등록
  - 개발 항목 선택
  - 개발 담당자 선정
  - 개발 일정 수립
  - 개발 내용 기재

- Issue 등록시 Telegram, 이메일로 팀원들에게 자동 알림

# 2. Issue 작업
>등록된 Issue를 기반으로 branch를 생성하여 개발 작업 실행

1. `feature`브랜치 생성
    - `개인 로컬 저장소`에서는 `develop` 브랜치를 `master` 브랜치라 생각하고 Issue에 기반한 작업용 feature 브랜치를 따로 생성
    - `$ git checkout -b ryanwork1`
2. 분석/설계 및 개발
3. 개발 내용 확인
    - `$ git status`
4. 개발 내용 ***add, commit, push***
    - `$ git add .`
    - `$ git commit -m '커밋내용'`
    - `$ git push origin ryanwork1` - 반드시 개인 원격저장소(origin)의 작업용 브랜치(ryanwork1)에 push 
5. Jenkins로 자동 빌드 후 Test code 실행 및 Test coverage 표시
    - 테스트 통과 실패 시, 코드 재작성 및 테스트

# 3. Pull Request (이하 PR)
6. `개인 원격 저장소 feature 브랜치`에서 `프로젝트 저장소 develop 브랜치`로 ***Pull Request*** (Bitbucket에서)

# 4. PR Review
7. 관리자는 코드 리뷰 후 develop 브랜치로 ***Merge*** 혹은 ***Decline*** (Bitbucket에서)
8. Merge 된 이후에는 작업용 브랜치 직접 삭제 혹은
    - `$ git branch -d ryanwork1`
    

# 5. 저장소 Update
>`프로젝트 저장소`가 다른 팀원에 의해 업데이트 되었을 때는 local 저장소를 업데이트 해야한다.
>>새로운 작업을 하기 전 Local 저장소를 업데이트하는 습관 필요.

9. 팀원들에 의해 업데이트된 `프로젝트 저장소`의 `develop` 브랜치로 부터 Local의 `develop` 브랜치 동기화. 
    - 우선 HEAD가 `develop` 브랜치에 위치하도록 `$ git checkout develop`
    - `$ git fetch 프로젝트저장소(별명) develop`
    - `$ git branch -a` 로 브랜치 확인
    - `$ git merge 프로젝트저장소(별명)/develop`
    - 또는 merge 대신 `$ git rebase 프로젝트저장소(별명)/develop`
    - 또는 Pull로 한번에 동기화 `$ git pull 프로젝트저장소(별명) develop`

10. 이후 작업은 작업용 브랜치를 만드는 1번 부터 진행한다.

# 5. Issue 반영

# (번외 #1) CI/CD Tool
>Bitbucket에서는 클라우드에서 사용하는 CI/CD 도구인 Pipelines을 제공하지만 한달에 50분만 무료입니다.  
>가장 보편적으로 사용하는 오픈소스 CI/CD 도구인 Jenkins를 기준으로 작성했습니다.

# (번외 #2) 함께 결정해야 할 사항들
1. Trello에 [테스트 보드](https://trello.com/b/SnsW6FEh/bitbucket-%EC%97%B0%EB%8F%99-%ED%85%8C%EC%8A%A4%ED%8A%B8)처럼 프로젝트 진행과정을 관리 할 것인지. (현재 Trello는 내용 저장의 용도로만 사용중)
2. 

# (번외 #3) Git 문제 해결
1. history가 꼬였을 때(내가 작업하는 중에 누군가 원격 저장소에 최신버전을 푸시했을 때) 해결 방법
    - 나의 작업을 원격 브랜치의 최신 상태를 기반으로 하게 만든다.
    - 방법은 여러 가지지만 가장 간단한 방법은 `rebase` 이다.
    - `git fetch; git rebase origin/master; git push`
    - 간단하게는 `git pull --rebase; git push;`
    
2. `$git log --no-merges issue54..origin/master`
    - feature 브랜치에서 작업을 마치고 merge할 때 어떤 내용이 merge되는지 git log명령으로 확인. 뒤의 브랜치(origin/master)에 속한 커밋 중 앞의 브랜치(issue54)에 속하지 않은 커밋을 검색
    - `fetch -> log -> merge`의 순서로 작업
3. `$git push -u origin featureB:featureBee`
    - featureB에서 작업했는데 서버에는 브랜치가 featureBee라고 되어있을 때 이런식으로 refspec 사용
    - `-u` 옵션은 `--set-upstream`의 짧은 표현
4. 내 브랜치와 A저장소 브랜치 충돌 시, 내 브랜치를 origin/master에 rebase해서 충돌을 해결하고 다시 pull request를 보낸다.
    1. `$git checkout featureA`
    2. `$git rebase origin/master`
    3. `$git push -f myfork featureA` - 브랜치를 rebase했기 때문에 push 할 때 `-f`옵션으로 강제로 기존 서버에 있던  featureA브랜치의 내용을 덮어 써야한다. 아니면 새로운 브랜치를 서버에 push한다.

5. featureB의 pull request를 확인한 프로젝트관리자가 일부 수정을 요청했다. featureB 담당자는 하는 김에 featureB 브랜치를 프로젝트의 최신 master 기반으로 옮긴다. featureBv2 브랜치를 새로 만들고, featureB의 커밋들을 모두 Squash해서 merge하고, 만약 충돌이 나면 해결하고 수정 사항을 구현하고 새 브랜치를 push한다.  
~~~git
$git checkout -b featureBv2 origin/master
$git merge --squash featureB
  #(change implementation)
$git commit
$git push myfork featureBv2
~~~  
    - --squash 옵션은 해당 브랜치의 모든 커밋들을 하나의 커밋으로 합쳐서 merge한다. 이때, merge 커밋은 만들지 않고, 수정할 부분도 수정한 후에 merge 할 수 있다. `--no-commit` 옵션을 추가하면 커밋을 합쳐놓고 자동으로 커밋하지 않는다.

6. history를 한 줄로 관리하려면 merge보다 rebase나 cherry-pick을 사용할 수 있다. feature 브랜치에서 작업을 마치고 master/develop 브랜치에 merge 할 때 master/develop 브랜치를 기반으로 rebase한다.


## Reference
- [Learn about code review in Bitbucket](https://www.atlassian.com/git/tutorials/learn-about-code-review-in-bitbucket-cloud)
