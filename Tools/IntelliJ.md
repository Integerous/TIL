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

### 목록
||||
|---|---|---|
| 1. [생성할 목록 보기](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#1-%EC%83%9D%EC%84%B1%ED%95%A0-%EB%AA%A9%EB%A1%9D-%EB%B3%B4%EA%B8%B0) | 2. [코드 템플릿](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#2-%EC%BD%94%EB%93%9C-%ED%85%9C%ED%94%8C%EB%A6%BF) | 3. [실행환경 실행](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#3-%EC%8B%A4%ED%96%89%ED%99%98%EA%B2%BD-%EC%8B%A4%ED%96%89) |
| 4. [라인 복제하기](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#4-%EB%9D%BC%EC%9D%B8-%EB%B3%B5%EC%A0%9C%ED%95%98%EA%B8%B0) | 5. [라인 삭제하기](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#5-%EB%9D%BC%EC%9D%B8-%EC%82%AD%EC%A0%9C%ED%95%98%EA%B8%B0) | 6. [문자열 라인 합치기](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#6-%EB%AC%B8%EC%9E%90%EC%97%B4-%EB%9D%BC%EC%9D%B8-%ED%95%A9%EC%B9%98%EA%B8%B0) |
| 7. [라인 단위로 옮기기](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#7-%EB%9D%BC%EC%9D%B8-%EB%8B%A8%EC%9C%84%EB%A1%9C-%EC%98%AE%EA%B8%B0%EA%B8%B0) | 8. [Element 단위로 옮기기](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#8-element-%EB%8B%A8%EC%9C%84%EB%A1%9C-%EC%98%AE%EA%B8%B0%EA%B8%B0) | 9. [넣어야 할 인자값 바로 보기](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#9-%EB%84%A3%EC%96%B4%EC%95%BC-%ED%95%A0-%EC%9D%B8%EC%9E%90%EA%B0%92-%EB%B0%94%EB%A1%9C-%EB%B3%B4%EA%B8%B0) |
| 10. [선언부(구현부) 바로 보기](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#10-%EC%84%A0%EC%96%B8%EB%B6%80%EA%B5%AC%ED%98%84%EB%B6%80-%EB%B0%94%EB%A1%9C-%EB%B3%B4%EA%B8%B0) | 11. [Document 바로 보기](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#11-document-%EB%B0%94%EB%A1%9C-%EB%B3%B4%EA%B8%B0) | 12. [단어별 이동](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#12-%EB%8B%A8%EC%96%B4%EB%B3%84-%EC%9D%B4%EB%8F%99) |
| 13. [문장 끝으로 이동, 페이지 업다운](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#13-%EB%AC%B8%EC%9E%A5-%EB%81%9D%EC%9C%BC%EB%A1%9C-%EC%9D%B4%EB%8F%99-%ED%8E%98%EC%9D%B4%EC%A7%80-%EC%97%85%EB%8B%A4%EC%9A%B4) |14. [포커스 확장](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#14-%ED%8F%AC%EC%BB%A4%EC%8A%A4-%ED%99%95%EC%9E%A5) |15. [포커스 축소](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#15-%ED%8F%AC%EC%BB%A4%EC%8A%A4-%EC%B6%95%EC%86%8C)|
|16. [포커스 이동](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#16-%ED%8F%AC%EC%BB%A4%EC%8A%A4-%EC%9D%B4%EB%8F%99) |17. [다중 선택](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#17-%EB%8B%A4%EC%A4%91-%EC%84%A0%ED%83%9D) |18. [오류난 곳으로 포커스 이동](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#18-%EC%98%A4%EB%A5%98%EB%82%9C-%EA%B3%B3%EC%9C%BC%EB%A1%9C-%ED%8F%AC%EC%BB%A4%EC%8A%A4-%EC%9D%B4%EB%8F%99)|
|19. [프로젝트 전체에서 검색(F), 교체(R)](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#19-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%A0%84%EC%B2%B4%EC%97%90%EC%84%9C-%EA%B2%80%EC%83%89f-%EA%B5%90%EC%B2%B4r)|20. [파일 검색](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#20-%ED%8C%8C%EC%9D%BC-%EA%B2%80%EC%83%89)|21. [메소드 검색](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#21-%EB%A9%94%EC%86%8C%EB%93%9C-%EA%B2%80%EC%83%89)|
| 22. [액션 검색](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#22-%EC%95%A1%EC%85%98-%EA%B2%80%EC%83%89)|23. [최근 열었던 파일 목록](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#23-%EC%B5%9C%EA%B7%BC-%EC%97%B4%EC%97%88%EB%8D%98-%ED%8C%8C%EC%9D%BC-%EB%AA%A9%EB%A1%9D)|24. [최근 수정한 파일 목록](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#24-%EC%B5%9C%EA%B7%BC-%EC%88%98%EC%A0%95%ED%95%9C-%ED%8C%8C%EC%9D%BC-%EB%AA%A9%EB%A1%9D)|
|25. [자동완성](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#25-%EC%9E%90%EB%8F%99%EC%99%84%EC%84%B1)|26. [스마트 자동완성](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#26-%EC%8A%A4%EB%A7%88%ED%8A%B8-%EC%9E%90%EB%8F%99%EC%99%84%EC%84%B1)|27. [Static 메소드 자동완성](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#27-static-%EB%A9%94%EC%86%8C%EB%93%9C-%EC%9E%90%EB%8F%99%EC%99%84%EC%84%B1)|
|28. [Getter/Setter, 생성자 등 자동생성](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#28-gettersetter-%EC%83%9D%EC%84%B1%EC%9E%90-%EB%93%B1-%EC%9E%90%EB%8F%99%EC%83%9D%EC%84%B1)|29. [Override 메소드 자동생성](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#29-override-%EB%A9%94%EC%86%8C%EB%93%9C-%EC%9E%90%EB%8F%99%EC%83%9D%EC%84%B1)|30. [Live Template 목록 확인](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#30-live-template-%EB%AA%A9%EB%A1%9D-%ED%99%95%EC%9D%B8)|
|31. [변수 추출하기](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#31-%EB%B3%80%EC%88%98-%EC%B6%94%EC%B6%9C%ED%95%98%EA%B8%B0)|32. [파라미터 추출하기](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#32-%ED%8C%8C%EB%9D%BC%EB%AF%B8%ED%84%B0-%EC%B6%94%EC%B6%9C%ED%95%98%EA%B8%B0)|33. [메소드 추출하기](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#33-%EB%A9%94%EC%86%8C%EB%93%9C-%EC%B6%94%EC%B6%9C%ED%95%98%EA%B8%B0)|
|34. [이너클래스 추출하기](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#34-%EC%9D%B4%EB%84%88%ED%81%B4%EB%9E%98%EC%8A%A4-%EC%B6%94%EC%B6%9C%ED%95%98%EA%B8%B0)|35. [변수명/메소드명 일괄 변경](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#35-%EB%B3%80%EC%88%98%EB%AA%85%EB%A9%94%EC%86%8C%EB%93%9C%EB%AA%85-%EC%9D%BC%EA%B4%84-%EB%B3%80%EA%B2%BD)|36. [타입 변경](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#36-%ED%83%80%EC%9E%85-%EB%B3%80%EA%B2%BD)|
|37. [사용하지 않는 import문 제거](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#37-%EC%82%AC%EC%9A%A9%ED%95%98%EC%A7%80-%EC%95%8A%EB%8A%94-import%EB%AC%B8-%EC%A0%9C%EA%B1%B0)|38. [사용하지 않는 import문 자동 제거](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#38-%EC%82%AC%EC%9A%A9%ED%95%98%EC%A7%80-%EC%95%8A%EB%8A%94-import%EB%AC%B8-%EC%9E%90%EB%8F%99-%EC%A0%9C%EA%B1%B0)|39. [코드 정렬](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#39-%EC%BD%94%EB%93%9C-%EC%A0%95%EB%A0%AC)|
|40. [디버그 모드로 실행](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#40-%EB%94%94%EB%B2%84%EA%B7%B8-%EB%AA%A8%EB%93%9C%EB%A1%9C-%EC%8B%A4%ED%96%89)|41. [디버그 모드로 실행 (이전에 실행한 메소드)](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#41-%EB%94%94%EB%B2%84%EA%B7%B8-%EB%AA%A8%EB%93%9C%EB%A1%9C-%EC%8B%A4%ED%96%89-%EC%9D%B4%EC%A0%84%EC%97%90-%EC%8B%A4%ED%96%89%ED%95%9C-%EB%A9%94%EC%86%8C%EB%93%9C)|42. [Resume (다음 브레이크 포인트로 이동)](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#42-resume-%EB%8B%A4%EC%9D%8C-%EB%B8%8C%EB%A0%88%EC%9D%B4%ED%81%AC-%ED%8F%AC%EC%9D%B8%ED%8A%B8%EB%A1%9C-%EC%9D%B4%EB%8F%99)|
|43. [Step Over (현재 브레이크에서 다음 줄로 이동)](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#43-step-over-%ED%98%84%EC%9E%AC-%EB%B8%8C%EB%A0%88%EC%9D%B4%ED%81%AC%EC%97%90%EC%84%9C-%EB%8B%A4%EC%9D%8C-%EC%A4%84%EB%A1%9C-%EC%9D%B4%EB%8F%99)|44. [Step Into (현재 브레이크의 다음 메소드로 이동)](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#44-step-into-%ED%98%84%EC%9E%AC-%EB%B8%8C%EB%A0%88%EC%9D%B4%ED%81%AC%EC%9D%98-%EB%8B%A4%EC%9D%8C-%EB%A9%94%EC%86%8C%EB%93%9C%EB%A1%9C-%EC%9D%B4%EB%8F%99)|45. [Step Out (현재 메소드의 밖으로 이동)](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#45-step-out-%ED%98%84%EC%9E%AC-%EB%A9%94%EC%86%8C%EB%93%9C%EC%9D%98-%EB%B0%96%EC%9C%BC%EB%A1%9C-%EC%9D%B4%EB%8F%99)|
|46. [Evaluate Expression (브레이크 된 상태에서 코드 사용)](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#46-evaluate-expression-%EB%B8%8C%EB%A0%88%EC%9D%B4%ED%81%AC-%EB%90%9C-%EC%83%81%ED%83%9C%EC%97%90%EC%84%9C-%EC%BD%94%EB%93%9C-%EC%82%AC%EC%9A%A9)|47. [터미널 실행](https://github.com/Integerous/TIL/blob/master/Tools/IntelliJ.md#47-%ED%84%B0%EB%AF%B8%EB%84%90-%EC%8B%A4%ED%96%89)| |


---

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

### 48. Tool Window로 포인터 옮기기
- `F12`

### 49. Editor로 포인터 옮기기
- `ESC`
