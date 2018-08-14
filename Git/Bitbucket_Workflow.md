# Bitbucket Workflow

## 이슈
- 특정 branch만 fork되지 않는 문제
- 특정 branch만 로컬로 clone하기
  - `git clone <url> --branch <branch> --single-branch [<folder>]`
  - 또는 `git clone -b <branchName> <remoteRepoUrl>`
  - 또는 전체를 클론해와서 해당 브랜치로 checkout하기
  - `git clone <url>` , `git checkout -b develop`
