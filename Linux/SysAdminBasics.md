# Linux SysAdmin Basics
>[tutoriaLinux](https://www.youtube.com/watch?v=bju_FdCo42w&list=PLtK75qxsQaMLZSo7KL-PmiRarU7hrpnwK) 유튜브 영상으로 학습하며, 익숙하지 않은 명령어들 정리

- `rmdir 디렉토리명` 디렉토리 삭제
  - 디렉토리가 비어있지 않을 경우 `rm -r 디렉토리명`으로 삭제
- `ctrl + a` = 명령 첫부분으로 가기
- `ctrl + e` = 명령 끝부분으로 가기
- `ctrl + r` = 이전 명령어 검색
- `ctrl + c` = esc 역할
- `ln -s File linkFile` 링크 만들기
  - 링크파일에서 내용 수정하면 원본파일 내용도 수정된다.
- `shutdown -r` = restart
  - `shutdown -h now` = halt now
  - `shutdown -h +60` = 한시간뒤에 종료
- `w` 또는 `who` = 사용자들 확인
- `mkdir -p 경로1/경로2/경로3...` = 여러 단계의 경로 생성
  - `rm -r 경로1` = 경로 삭제 (경로 안의 모든 경로 삭제)
  
