# 아침공부 #10
>자바의 정석(남궁성 저) 2권 학습  
>와이프가 아파서 내 마음도 아팠다.

## 1. Calendar 와 Date (이어서)
### 1.1. 두 날짜간의 차이 구하기
  - 두 날짜를 최소단위인 초단위로 변경한 다음 그 차이를 구한다.
### 1.2. 시간상의 전후 알기
  - 두 날짜간의 차이가 양수인지 음수인지 판단
  - `boolean after(Object when)` 또는 `boolean before(Object when)` 사용
### 1.3. 특정 날짜/시간 기준 일정 기간 전후의 날짜/시간 알기
  - `add(int field, int amount)`
  - `roll(int field, int amount)`
    - `add()`와의 차이점은 다른 필드에 영향 미치지 않음
    - 단, Calendar.DATE가 말일일 때, `roll`로 Calendar.MONTH를 변경하면 Calendar.DATE 필드에 영향을 미친다.
### 1.4. 해당 월의 마지막날 알기
  - 다음 달의 1일에서 하루 빼기
  - `getActualMaximum(Calendar.DATE)` 사용
### 1.5. 일 수 계산
  - Calendar는 1970년 1월 1일을 기준으로 계산
  - 1970년 1월 1일 이전에 날짜에 대해 `getTimeInMillis()`를 호출하면 음수 반환
  
## 2. 형식화 클래스

### 2.1. DecimalFormat
- DecimalFormat을 이용하면 숫자 데이터를 정수, 부동소수점, 금액 등의 다양한 형식으로 표현할 수 있다.
- 반대로 일정한 형식의 텍스트 데이터를 숫자로 쉽게 변환
- 형식화 클래스는 패턴을 정의하는 것이 전부다.
- DecimalFormat 사용법
  - ```java
    double number = 1234567.89;
    DecimalFormat df = new DecimalFormat("#.#E0"); // 1.원하는 출력형식의 패턴을 작성하여 DecimalFormat 인스턴스를 생성
    String result = df.format(number); // 2.출력하고자 하는 문자열로 format 메소드를 호출
    ```
### 2.2. SimpleDateFormat
- 날짜 데이터를 원하는 형태로 다양하게 출력
- 사용방법
  - 원하는 출력 형식의 패턴을 작성하여 SimpleDateFormat 인스턴스를 생성
  - 출력하고자 하는 Date 인스턴스를 가지고 format(Date d)를 호출
  - ~~~java
    Date today = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String result = df.format(today); //오늘 날짜를 yyyy-MM-dd 형태로 반환
    ~~~