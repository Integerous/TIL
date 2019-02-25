# Git 팁 저장소 (수정중)
>짧게 짧게 알게되는 Git 사용 팁을 저장하는 파일

### 터미널에서 GUI 처럼 브랜치 플로우 확인하기
    $ git log —branches —graph —decorate —oneline 명령으로 gui비슷하게 볼 수 있다.

### Reset과 Revert 차이
    이전 버전으로 되돌아가는 방법 reset과 revert 차이는 여기서 확인
    - https://www.popit.kr/%EA%B0%9C%EB%B0%9C%EB%B0%94%EB%B3%B4%EB%93%A4-git-back-to-the-future/

### Git stash 란?
다른 브랜치로 checkout을 해야하는데 아직 현재 브랜치에서 작업이 끝나지 않은 경우에 커밋을 하기 애매하다. 이런 경우 Stash를 이용하면 작업중이던 파일을 임시로 저장해두고 현재 브랜치의 상태를 마지막 커밋의 상태로 초기화 할 수 있다.그 후에 다른 브랜치로 이동하고 작업을 끝낸 후에 작업중이던 브랜치로 복귀한 후에 이전에 작업하던 내용을 복원할 수 있다. 즉, stash를 사용하여 작업중인 파일을 숨겨둘 수 있다.
  - $ git stash
  - $ git stash list
  - $ git stash apply — 최근의 stash 내용으로 다시 복원
  - $ git stash drop — 최근 stash 삭제
  - $ git stash pop — stash 복원 및 삭제

### 모든 commit에서 특정 파일 제거하기
    $ git filter-branch - -tree-filter 'rm -f passwords.txt' HEAD

### Rebase 주의사항
    - 이미 공개저장소에 push 한 커밋을 rebase하면 절대 안된다.
    - Push 하기 전에 정리하기 위해 rebase를 하는 것은 괜찮다.
    - 절대 공개하지 않고 혼자 rebase하는 경우도 괜찮다.
    - 하지만 이미 공개된 커밋을 rebase하면 망한다.

### Status 짧게 확인하기
    $ git status -s

### 변경사항 확인하기
    $ git diff  //(커밋 전)변경사항 모두 확인
    $ git diff [filename]  //특정 파일 변경사항 확인
    $ git diff --cached  //(커밋 후) 변경사항 확인

### Remote 삭제하기
    $ git remote -v
    > origin  https://github.com/OWNER/REPOSITORY.git (fetch)
    > origin  https://github.com/OWNER/REPOSITORY.git (push)
    > destination  https://github.com/FORKER/REPOSITORY.git (fetch)
    > destination  https://github.com/FORKER/REPOSITORY.git (push)

    $ git remote rm destination
    $ git remote -v
    > origin  https://github.com/OWNER/REPOSITORY.git (fetch)
    > origin  https://github.com/OWNER/REPOSITORY.git (push)
