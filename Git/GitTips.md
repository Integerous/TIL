# Git 팁 저장소 (수정중)
>짧게 짧게 알게되는 Git 사용 팁을 저장하는 파일

## 1. 로그 그래프 확인하기
    $ git log --all --decorate --oneline --graph 명령으로 gui비슷하게 볼 수 있다.

## 2. Reset과 Revert 차이
>이전 버전으로 되돌아가는 방법 reset과 revert 차이는 여기서 확인  
https://www.popit.kr/%EA%B0%9C%EB%B0%9C%EB%B0%94%EB%B3%B4%EB%93%A4-git-back-to-the-future/

## 3. Git stash 란?
다른 브랜치로 checkout을 해야하는데 아직 현재 브랜치에서 작업이 끝나지 않은 경우에 커밋을 하기 애매하다. 이런 경우 Stash를 이용하면 작업중이던 파일을 임시로 저장해두고 현재 브랜치의 상태를 마지막 커밋의 상태로 초기화 할 수 있다.그 후에 다른 브랜치로 이동하고 작업을 끝낸 후에 작업중이던 브랜치로 복귀한 후에 이전에 작업하던 내용을 복원할 수 있다. 즉, stash를 사용하여 작업중인 파일을 숨겨둘 수 있다.

    $ git stash
    $ git stash list
    $ git stash apply   //최근의 stash 내용으로 다시 복원
    $ git stash drop   //최근 stash 삭제
    $ git stash pop   //stash 복원 및 삭제

## 4. 모든 commit에서 특정 파일 제거하기
    $ git filter-branch - -tree-filter 'rm -f passwords.txt' HEAD

## 5. Rebase 주의사항
    - 이미 공개저장소에 push 한 커밋을 rebase하면 절대 안된다.
    - Push 하기 전에 정리하기 위해 rebase를 하는 것은 괜찮다.
    - 절대 공개하지 않고 혼자 rebase하는 경우도 괜찮다.
    - 하지만 이미 공개된 커밋을 rebase하면 망한다.

## 6. Status 짧게 확인하기
    $ git status -s

## 7. 변경사항 확인하기
    $ git diff   //(커밋 전)변경사항 모두 확인
    $ git diff [filename]   //특정 파일 변경사항 확인
    $ git diff --cached   //(커밋 후) 변경사항 확인

## 8. Remote 삭제하기
    $ git remote -v
    > origin  https://github.com/OWNER/REPOSITORY.git (fetch)
    > origin  https://github.com/OWNER/REPOSITORY.git (push)
    > destination  https://github.com/FORKER/REPOSITORY.git (fetch)
    > destination  https://github.com/FORKER/REPOSITORY.git (push)

    $ git remote rm destination
    $ git remote -v
    > origin  https://github.com/OWNER/REPOSITORY.git (fetch)
    > origin  https://github.com/OWNER/REPOSITORY.git (push)

## 9. 원격저장소의 branch 확인하고 가져오기
>원격저장소로부터 clone을 받으면 로컬저장소로 모든 브랜치를 다 가져오는 줄 알았는데,  
master 브랜치만 가져온다. 그래서 회사에서 사용하는 git-flow를 위해 필요한 branch들을 가져와야했다.  

    $ git branch   //로컬저장소의 브랜치 확인
    $ git branch -r   //원격저장소의 브랜치 확인
    $ git branch -a   //로컬저장소와 원격저장소의 모든 브랜치 확인
    
    $ git checkout -t [원격저장소의 브랜치 이름]   //원격저장소의 브랜치 이름으로 로컬에 브랜치 생성하면서 checkout
    $ git checkout -b [생성할 브랜치 이름] [원격저장소의 브랜치 이름]   //다른 이름으로 브랜치 생성
    $ git checkout [원격저장소의 브랜치 이름]   //detached HEAD 상태로 소스를 보고 변경 가능
                                                하지만 commit, push 불가능. 다른 브랜치로 checkout하면 사라짐.
    
#### Reference
- https://blog.outsider.ne.kr/641   

## 10. 원격저장소의 branch 삭제하기
>원격저장소에 step3라는 브랜치가 존재하는 상황에서, 로컬저장소의 step3 브랜치에서 작업한 내용으로 원격저장소의 step3 브랜치를 최신화하고 싶었다. 
이 때, 원격저장소의 step3 브랜치를 pull 받아서 충돌을 해결하고 원격저장소에 push하면 되지만, 너무 많은 내용이 달라졌기 때문에 시원하게 기존의 원격저장소의 step3 브랜치를 날려버리고, 로컬저장소의 step3 브랜치를 새롭게 push 하고 싶었다.(브랜치명이 반드시 step3이어야 했다.) 그래서 찾아봤다. 로컬저장소의 브랜치는 놔두고, 원격저장소의 브랜치를 로컬에서 삭제하기.

    $ git push origin --delete {브랜치명}

#### Reference
- https://www.lesstif.com/pages/viewpage.action?pageId=20776547

## 11. 서브모듈 사용해서 프로젝트 모아두기
>TDD 강의에서 만든 4개의 과제 저장소를 [한 곳](https://github.com/Integerous/tdd-practice-in-java)에 모아두기 위해 서브모듈을 사용해보았다.  
중요한 점은 서브모듈은 브랜치 상에 있는 것이 아니라, 단지 서브모듈 저장소의 **특정 커밋을 가리키는 포인터로서의 역할**을 한다는 것이다.  
이 점 때문에 혼동되는 점들이 있으므로 사용시에 주의해야 한다.

    (부모 프로젝트 경로에서)
    $ git submodule add [저장소주소]
    
    (특정 브랜치를 서브모듈화)
    $ git submodule add -b [브랜치명] [저장소주소]
    
#### Reference
- https://github.blog/2016-02-01-working-with-submodules/
- https://stackoverflow.com/questions/1777854/how-can-i-specify-a-branch-tag-when-adding-a-git-submodule

## 12. 브랜치 이름 변경하기
>개발한 프로젝트의 버전(기존 2.11.7)을 올리기 위해 release/2.11.8 브랜치를 생성했는데,  
기능이 추가된 버전업이기 때문에 브랜치명을 release/2.12.0 으로 수정해야 했다.  
(Jenkins에서 브랜치명을 기준으로 빌드를 시작하기 때문에 배포할 브랜치 이름은 중요하다.)

#### 1. 브랜치 이름 변경
    $ git branch -m {기존 이름} {변경할 이름}
    
    만약 현재 변경할 브랜치에 체크아웃한 상태라면, 아래의 명령으로도 가능하다.
    $ git branch -m {변경할 이름}

#### 2. 원격의 기존 브랜치 삭제
    $ git push origin :{기존 브랜치 이름}
    
#### 3. 상태 확인
    $ git status
    
    On branch release/2.12.0  
    Your branch is based on 'origin/release/2.11.8', but the upstream is gone.  
    (use "git branch --unset-upstream" to fixup)
    
    상태를 확인하면, 현재 로컬 브랜치가 이미 삭제된 원격 브랜치(origin/release/2.11.8)에 기반되어 있음을 알 수 있다.

#### 3. 변경된 브랜치 원격에 Push
    $ git push origin {변경된 브랜치 이름}

#### 4. Upstream을 새 이름의 브랜치로 변경
    $ git push origin -u {변경된 브랜치 이름}
    
#### 5. 상태 확인
    $ git status
    
    Upstream을 새로운 이름의 브랜치로 변경하고 상태를 확인해보면,  
    아래 메세지와 같이 origin/release/2.12.0에 기반을 두고있는 것을 확인할 수 있다. 끝.
    
    On branch release/2.12.0  
    Your branch is up to date with 'origin/release/2.12.0'.
    
#### Reference
- https://stackoverflow.com/a/40034426/8586944
