# :bread: 빵 부스러기

>개발 관련 학습 중 하나의 글로 작성하기엔 짧고,  
버리기엔 아까운 부스러기 정보들을 모아두는 곳.


### 1. 클래스라는 개념이 등장한 기본적인 이유
예를 들어, 전화번호부에서 한 사람의 이름과 전화번호는 항상 붙어다녀야 하는 데이터이다.  
그런데 이 데이터를 별개의 변수에 저장하면, 이름 데이터를 옮길 때 마다 전화번호 데이터도 따로 옮겨줘야 한다.  
만약 이름과 전화번호 외에 더 많은 데이터를 저장해야 한다면, 더 불편해질 것이다.  
그래서 **서로 관련있는 데이터들을 하나의 단위로 묶어두기 위해** 등장한 것이 클래스라는 개념이다.
- Reference - [Java로 배우는 자료구조 - 권오흠](https://www.inflearn.com/course/java-%EC%9E%90%EB%A3%8C%EA%B5%AC%EC%A1%B0/%EB%AC%B8%EC%9E%90%EC%97%B4-%EB%8B%A4%EB%A3%A8%EA%B8%B0-3/)

-----

### 2. Primitive Type과 Class의 차이점
Class도 하나의 Type인데, int 혹은 double 형 변수를 선언하고 사용하는 것 처럼 User 변수를 선언하고 사용한다.
  - int count = 0;
  - User user = new User();
  
이 때, count라는 이름의 변수에는 그 안에 정수값 0이 저장되지만,  
user라는 이름의 변수는 그 안에 사용자(User)의 정보가 저장되지 않는다.  
사용자의 정보를 저장할 User객체(object)는 new 명령으로 따로 만들고,  
변수 user에는 따로 만든 User 객체의 주소(참조)를 저장하는 것이다.

Primitive 타입이 아닌 모든 변수는 참조 변수다.
- Reference - [Java로 배우는 자료구조 - 권오흠](https://www.inflearn.com/course/java-%EC%9E%90%EB%A3%8C%EA%B5%AC%EC%A1%B0/%EB%AC%B8%EC%9E%90%EC%97%B4-%EB%8B%A4%EB%A3%A8%EA%B8%B0-3/)

-----

