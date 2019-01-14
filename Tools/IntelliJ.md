# IntelliJ 사용기
>IntelliJ를 지원해주는 회사였음에도 사수님이 Eclipse를 고집하셨는데,  
>회사와 결별하게 되어 이제서야 IntelliJ를 시작! (2019/01/12)  
>사용하면서 알아가는 것들을 간단하게 메모할 예정.

### 학습도구
1. [IntelliJ를 시작하시는 분들을 위한 IntelliJ 가이드 - 인프런](https://www.inflearn.com/course/intellij-guide/)
---

## 단축키
>Mac 단축키  
>Window/Linux 단축키

### 1. 생성할 목록 보기
- `Cmd + N`
- `Alt + Insert`

### 2. 코드 템플릿
- 메인 메소드 `psvm`
- System.out.println() `sout`

### 3. 실행환경 실행
- 현재 포커스
  - `Ctrl + Shift + R`
  - `Shift + Ctrl + F10`
- 이전 실행
  - `Ctrl + R`
  - `Shift + F10`

### 4. 라인 복제하기
- `Cmd + D`
- `Ctrl + D`

### 5. 라인 삭제하기
- `Cmd + backspace`
- `Ctrl + Y`

### 6. 문자열 라인 합치기
- `Ctrl + Shift + J`
- `Ctrl + Shift + J`

### 7. 라인 단위로 옮기기
- 자유롭게 이동
  - `Shift + Opt + 방향키`
  - `Shift + Alt + 방향키`

- 구문 안에서만 이동
  - `Shift + Cmd + 방향키`
  - `Shift + Ctrl 방향키`
  
### 8. Element 단위로 옮기기
- `Opt + Shift + Cmd + 방향키`
- `Alt + Ctrl + Shift + 방향키`

### 9. 넣어야 할 인자값 바로 보기
- 괄호() 안에 포인터 두고
- `Cmd + P`
- `Ctrl + P`

### 10. 선언부(구현부) 바로 보기
- 보고싶은 것 위에 포인터 두고
- `Opt + Space`
- `Shift + Ctrl + I`

### 11. Document 바로 보기
- 보고싶은 것 위에 포인터 두고
- `F1`
- `Ctrl + Q`

### 12. 단어별 이동
- `Opt + 방향키`
- `Ctrl + 방향키`

### 13. 문장 끝으로 이동, 페이지 업다운
- `fn + 방향키`

### 14. 포커스 확장
- `Opt + 위쪽 방향키`
- `Ctrl + W`

### 15. 포커스 축소
- `Opt + 아래 방향키`
- `Shift + Ctrl + W`

### 16. 포커스 이동
- 이전 포커스로 이동
  - `Cmd + [`
  - `Ctrl + Alt + 왼쪽 방향키`
- 원래 포커스로 이동
  - `Cmd + ]`
  - `Ctrl + Alt + 오른쪽 방향키-`
  
### 17. 다중 선택
- `Opt 2번 누른채로 + 방향키`
- `Ctrl 2번 누른채로 + 방향키`

### 18. 오류난 곳으로 포커스 이동
- `F2`

### 19. 프로젝트 전체에서 검색(F), 교체(R)
- `Cmd + Shift + F(R)`
- `Ctrl + Shift + F(R)`

### 20. 파일 검색
- `Cmd + Shift + O`
- `Ctrl + Shift + N`

### 21. 메소드 검색
- `Cmd + Alt + O`
- `Ctrl + Shift + Alt + N`

### 22. 액션 검색
>IntelliJ의 모든 이벤트와 옵션 검색 가능
- `Cmd + Shift + A`
- `Ctrl + Shift + A`

### 23. 최근 열었던 파일 목록
- `Cmd + E`
- `Ctrl + E`

### 24. 최근 수정한 파일 목록
- `Cmd + Shift + E`
- `Ctrl + Shift + E`

### 25. 자동완성
- `Ctrl + Space`

### 26. 스마트 자동완성
- `Ctrl + Shift + Space`

### 27. Static 메소드 자동완성
- `Ctrl + Space 두 번`

### 28. Getter/Setter, 생성자 등 자동생성
- `Cmd + N`
- `Alt + Insert`

### 29. Override 메소드 자동생성
- `Ctrl + I`

### 30. Live Template 목록 확인
>포커스 되어있는 위치 기준으로 사용 가능한 목록 반환
- `Cmd + J`
- `Ctrl + J`

### 31. 변수 추출하기
- `Cmd + Opt + V`
- `Ctrl + Alt + V`

### 32. 파라미터 추출하기
- `Cmd + Opt + P`
- `Ctrl + Alt + P`

### 33. 메소드 추출하기
- `Cmd + Opt + M`
- `Ctrl + Alt + M`

### 34. 이너클래스 추출하기
- 추출할 이너클래스명에 포커스를 두고 `F6`

### 35. 변수명/메소드명 일괄 변경
- `Shift + F6`

### 36. 타입 변경
- `Cmd + Shift + F6`
- `Ctrl + Shift + F6`

### 37. 사용하지 않는 import문 제거
- `Ctrl + Opt + O`
- `Ctrl + Alt + O`

### 38. 사용하지 않는 import문 자동 제거
- 액션 검색에서 `optimize import on` 검색
- 엔터키를 눌러서 `OFF`를 `ON`으로 변경

### 39. 코드 정렬
>포커스 위치와 상관 없이
- `Cmd + Opt + L`
- `Ctrl + Alt + L`

### 40. 디버그 모드로 실행
>디버그할 메소드에 포커스를 두고
- `Ctrl + Shift + D`
- 윈도우/리눅스는 단축키 없음

### 41. 디버그 모드로 실행 (이전에 실행한 메소드)
- `Ctrl + D`
- `Shift + F9`

### 42. Resume (다음 브레이크 포인트로 이동)
- `Cmd + Opt + R`
- `F9`

### 43. Step Over (현재 브레이크에서 다음 줄로 이동)
- `F8`

### 44. Step Into (현재 브레이크의 다음 메소드로 이동)
- `F7`

### 45. Step Out (현재 메소드의 밖으로 이동)
- `Shift + F8`

### 46. Evaluate Expression (브레이크 된 상태에서 코드 사용)
- `Opt + F8`
- `Alt + F8`

### 47. 터미널 실행
- `Opt + F12`
- `Alt + F12`
