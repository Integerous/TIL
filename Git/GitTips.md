# Git 팁 저장소 (수정중)
- $git log —branches —graph —decorate —oneline 명령으로 gui비슷하게 볼 수 있다.
- 이전 버전으로 되돌아가는 방법 reset과 revert 차이는 여기서 확인
    - https://www.popit.kr/%EA%B0%9C%EB%B0%9C%EB%B0%94%EB%B3%B4%EB%93%A4-git-back-to-the-future/
- Git stash 란?
    - 다른 브랜치로 checkout을 해야하는데 아직 현재 브랜치에서 작업이 끝나지 않은 경우에 커밋을 하기 애매하다. 이런 경우 Stash를 이용하면 작업중이던 파일을 임시로 저장해두고 현재 브랜치의 상태를 마지막 커밋의 상태로 초기화 할 수 있다.그 후에 다른 브랜치로 이동하고 작업을 끝낸 후에 작업중이던 브랜치로 복귀한 후에 이전에 작업하던 내용을 복원할 수 있다. 즉, stash를 사용하여 작업중인 파일을 숨겨둘 수 있다.
        - $git stash
        - $git stash list
        - $git stash apply — 최근의 stash 내용으로 다시 복원
        - $git stash drop — 최근 stash 삭제
        - $git stash pop — stash 복원 및 삭제
- 모든 commit에서 특정 파일 제거하기
    - $git filter-branch - -tree-filter 'rm -f passwords.txt' HEAD
- 이미 공개저장소에 push 한 커밋을 rebase하면 절대 안된다.
- Push 하기 전에 정리하기 위해 rebase를 하는 것은 괜찮다.
- 절대 공개하지 않고 혼자 rebase하는 경우도 괜찮다.
- 하지만 이미 공개된 커밋을 rebase하면 망한다.


# (번외) 개인적으로 생각하는 최고의 협업 workflow
>Github + IntelliJ + Slack + Git GUI Tools

- Github
  - Organization을 생성하면 팀, 프로젝트, 작업 모두 한번에 관리 가능(Trello와 같은 kanban 스타일 보드로 관리 가능)
  - 개발자가 6인 이상이라면 Github이 가장 reasonable한 가격 정책
  - Pull Request 와 Issue 생성시 템플릿 생성/적용 가능
  
- IntelliJ
  - Task 관리를 통해 Github과 연동하면 Github Issue를 기반으로 Branch 생성을 쉽게 할 수 있다.
  - 각 Issue는 고유번호를 가지는데 Branch가 Issue 고유번호를 기반으로 네이밍되어 Issue가 많아져도 Branch는 명확한 작업 의도를 갖게할 수 있다.
  - IntelliJ 내에서 Pull Request 생성 가능
  
- Slack
  - Github과 연동하여 Slack으로 모든 팀, 프로젝트, 작업 관련 모든 notification 집중시킬 수 있다.
  - 그 외에 다양한 기능들
