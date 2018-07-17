# Linux SysAdmin Basics
>https://www.youtube.com/watch?v=bju_FdCo42w&list=PLtK75qxsQaMLZSo7KL-PmiRarU7hrpnwK  
>튜토리얼 내용 중 내가 몰랐던(잊었던) 부분만 정리

- `ls /`  root의 파일리스트
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
