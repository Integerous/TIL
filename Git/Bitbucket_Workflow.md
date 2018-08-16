# Cloudcash workflow with Bitbucket

## 해결해야 할 문제
- 특정 branch만 fork되지 않는 문제
- 특정 branch만 로컬로 clone하기
  - `git clone <url> --branch <branch> --single-branch [<folder>]`
  - 또는 `git clone -b <branchName> <remoteRepoUrl>`
  - 또는 전체를 클론해와서 해당 브랜치로 checkout하기
  - `git clone <url>` , `git checkout -b develop`
- Pull Request 와 Issue 생성 시 템플릿 생성/사용 불가능 문제
  - Issue 중 버그 리포트의 경우
    - 템플릿을 활용하여 server log, response body 등 필요한 정보들을 템플릿에 강제하여 정보 누락을 방지할 수 있고,
    - 마크다운 문법을 템플릿에 적용해놓으면 훨씬 빠르게 리포트 작성 가능하며, 일관된 문서작성 가능. 또한 정제된 리포트 자체를 데이터로 활용 가능.



## Reference
- [Learn about code review in Bitbucket](https://www.atlassian.com/git/tutorials/learn-about-code-review-in-bitbucket-cloud)
