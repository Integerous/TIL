# Dev Team Workflow
>협업 환경 = Git + Bitbucket(또는 Github) + Trello + Telegram
>>Bitbucket 대신 Github을 사용해도 똑같습니다.

## 전체 흐름
  ~~~
  0. 최초 설정 (+ Workflow 요약)
  1. Issue 등록
  2. Issue 작업
  3. Pull Request
  4. PR Review
  5. Issue 반영
  6. 저장소 Update
  (번외 #1) CI/CD Tool
  (번외 #2) 함께 결정해야 할 사항들
  ~~~
-----------------------------------
-----------------------------------  
# 0. 최초 설정
### 0.1. 프로젝트 관리자
#### 0.1.1. Bitbucket에 `프로젝트 저장소` 생성
- 프로젝트 저장소 Owner를 관리자 개인으로 하는 경우 : `0.1.4.` 까지
- 프로젝트 저장소 Owner를 팀 계정으로 하고 개인 저장소를 따로 운영하는 경우 : `0.1.5.` 까지
#### 0.1.2. 작업용 `develop` 브랜치 생성 (`master` 브랜치는 배포용)
#### 0.1.3. 작업용 브랜치 생성 이유
  - 프로젝트 저장소의 `Master` 브랜치와 `develop` 브랜치 두 가지를 두고, `master` 브랜치는 아주 안정적인 버전을 릴리즈 할 때 사용하고, `develop` 브랜치는 새로 수정된 코드를 통합할 때 사용한다. 릴리즈 할 수준이 되면 `master` 브랜치를 `develop` 브랜치까지 **fast-forward** 후 버전을 Tag로 추가한다.
  - 더 나아가 `integrate` 브랜치를 추가할 수 있다. master - develop - integrate - feature 브랜치를 만들어 feature에서 작업한 것을 integrate에 merge하여 feature 브랜치가 검증되면 develop 브랜치에 merge 한다. 그리고 develop 브랜치에서 충분히 안정성이 검증되면 그때 master 브랜치에 merge한다.
  - feature 브랜치는 develop/master 브랜치에 merge되면 저장소에서 삭제한다.
  - 릴리즈 버전은 언제든 돌아갈 수 있도록 태그를 다는 것이 좋다. 그리고 배포할 릴리즈 버전이 준비되었다면, git을 사용하지 않는 사람을 위해 소스코드 스냅샷을 압축한다. `$git archive`명령으로 압축할 수 있다.
#### 0.1.4. 프로젝트와 Bitbucket 연결 (Local에 프로젝트가 존재할 경우)
  - Local 프로젝트 위치로 이동 `$ cd /path/to/your/repo`
  - 원격저장소 추가 : `$ git remote add [프로젝트 저장소명/별명] [프로젝트 저장소 주소]`
    - 여기서 `[프로젝트 저장소 주소]`는 저장소의 주소창에 있는 주소가 아니라 아래 예시와 같은 주소.(github과 bitbucket의 주소 모양 다름)
    - ex) `$ git remote add HJS-SERVER https://Ryan_Han@bitbucket.org/hjskorea/hjs-server.git`
  - 원격저장소에 Local 프로젝트 Push
    - `$ git push [프로젝트 저장소명/별명] [branch명]`
    - ex) `$ git push HJS-SERVER develop`
#### 0.1.5. 팀 계정의 `프로젝트 저장소`를 관리자 개인 계정의 `개인 원격 저장소`로 Fork
  - Fork 기능은 Bitbucket의 `프로젝트 저장소`에서 사용
  - `개인 원격 저장소`를 `개인 local 저장소`의 원격저장소로 지정
    - `$ git remote add origin
  
### 0.2. 프로젝트 팀원
1. `프로젝트 저장소`를 `개인 원격 저장소`로 ***Fork*** (Bitbucket 에서)
2. 각자의 컴퓨터에 `개인 local 저장소`로 사용할 경로 선택 또는 생성
3. `개인 원격 저장소`에서 `개인 local 저장소`로 `develop` 브랜치만 ***Clone***
    - `$ git clone -b develop --single-branch branchURL(반드시 본인 원격저장소 URL)`
4. 모든 브랜치 확인
    - `$ git branch -a`
    - 현재 `개인 local 저장소`에는 `develop` 브랜치만 존재해야 한다. 
5. `프로젝트 저장소`의 최신화된 `develop` 브랜치와 `개인 local 저장소`의 `develop` 브랜치를 동기화하기 위해 ***Remote***(원격저장소) 설정
    - `$ git remote add [프로젝트 저장소명/별명] [프로젝트 저장소 주소]` -> 본인 원격저장소 주소로 하지 말것.
    - `$ git remote -v` 로 원격저장소 확인
    - 확인 결과 `cashcloud`(프로젝트 저장소)와 `origin`(개인 원격 저장소)만 나와야 한다.

### 0.3. 필요한 저장소/브랜치 정리
- ***저장소***
  1. `프로젝트 저장소` - Bitbucket에 관리자가 생성한 `프로젝트 저장소`. 이름은 remote 설정할 때 정한 저장소 별명.
  2. `개인 원격 저장소` - 각 개인이 Bitbucket의 `프로젝트 저장소`를 개인 계정으로 fork 해온 저장소. 이름은 자동으로 origin.
  3. `개인 local 저장소` - 각 개인의 컴퓨터에 `개인 원격 저장소`로부터 clone 해온 저장소. 
- ***브랜치***
  1. `master 브랜치` - develop 브랜치에서 충분히 검증된 버전을 `배포하는 브랜치`
  2. `develop 브랜치` - 각 팀원들의 feature 브랜치를 `통합하고 테스트하는 브랜치`
  3. `feature 브랜치` - 이슈 기반으로 브랜치명을 생성한 `작업용 브랜치`

-----------------------------------
-----------------------------------
## Workflow 요약 (최초 설정 후)
~~~
1. 개인 local 저장소 최신화
2. feature 브랜치 생성
3. 작업    
4. 작업한 feature 브랜치를 개인 원격 저장소에 push
5. 개인 원격 저장소에 push한 feature 브랜치를 프로젝트 저장소의 develop 브랜치로 Pull Reqeust
6. 1~5 반복
~~~
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

1. `개인 local 저장소` 최신화
    - 다른 팀원에 의해 최신화된 `프로젝트 저장소`의 변경 사항을 `개인 local 저장소`에 ***pull*** 혹은 ***fetch & merge*** 하여 최신화
    - 작업 시작 전 `개인 local 저장소`를 최신화하는 습관 필요
2. `feature`브랜치 생성
    - `개인 local 저장소`에서는 `develop` 브랜치를 `master` 브랜치라 생각하고 Issue에 기반한 작업용 feature 브랜치를 따로 생성
    - `$ git checkout -b ryanwork1`
    - `feature` 브랜치의 이름은 등록된 Issue 기반으로 생성하여 `브랜치명 = 작업명`이 되도록하여 브랜치에 정체성 부여
3. 분석/설계 및 개발
4. 개발 내용 확인
    - `$ git status`
5. 개발 내용 ***add, commit, push***
    - `$ git add .`
    - `$ git commit -m '커밋내용'`
    - `$ git push origin ryanwork1` - 반드시 `개인 원격 저장소`(origin)의 작업용 `feature` 브랜치(ryanwork1)에 push 

# 3. Pull Request (이하 PR)
6. `개인 원격 저장소 feature 브랜치`에서 `프로젝트 저장소 develop 브랜치`로 ***Pull Request*** (Bitbucket에서)
    - Pull Request 시, 어떤 Issue에 대한 PR인지 작성해야한다.
    - CI/CD 도구 활용
    - Merge 되면 `feature` 브랜치를 close 하도록 체크
    
# 4. PR Review
7. 관리자는 코드 리뷰 후 `프로젝트 저장소`의 `develop` 브랜치로 ***Merge*** 혹은 ***Decline*** (Bitbucket에서)
8. Merge 된 이후에 `feature` 브랜치가 close 되도록 설정 안했다면 직접 브랜치 삭제 
    - `$ git branch -d ryanwork1`

# 5. Issue 반영
- Bitbucket에서 Issue close 혹은 PR과 연동하여 자동으로 Issue close.(Bitbucket에서는 PR과 연동불가)
- Trello 이슈 카드 이동

# 6. 저장소 Update
9. 팀원들에 의해 업데이트된 `프로젝트 저장소`의 `develop` 브랜치로 부터 `개인 local 저장소`의 `develop` 브랜치 동기화. 
    - 우선 HEAD가 `develop` 브랜치에 위치하도록 `$ git checkout develop`
    - `$ git fetch 프로젝트저장소(별명) develop`
    - `$ git branch -a` 로 브랜치 확인
    - `$ git merge 프로젝트저장소(별명)/develop`
    - 또는 merge 대신 `$ git rebase 프로젝트저장소(별명)/develop`
    - 또는 Pull로 한번에 동기화 `$ git pull 프로젝트저장소(별명) develop`

10. 이후 작업은 다시 1번 부터 진행한다.
-----------------------------------
    
## (번외 #1) CI/CD Tool
>CI/CD Tool 선택된 후 작성 예정

## (번외 #2) 이클립스에서 프로젝트 열기
>[여기](https://github.com/Integerous/TIL/blob/master/Tools/Eclipse.md) 참조

## * Reference
- [Learn about code review in Bitbucket](https://www.atlassian.com/git/tutorials/learn-about-code-review-in-bitbucket-cloud)
