# AOP (Aspect Oriented Programming)
- Java는 다중상속을 지원하지 않으므로 다양한 모듈에 상속을 통해 공통기능을 구현할 수 없다.
- 그리고, 기능 구현부분에 핵심기능 코드와 공통기능 코드가 섞여있어 효율성이 떨어진다.
>AOP는 공통기능과 핵심기능을 분리해놓고, 공통기능 중에서 핵심기능에 적용하고자 하는 부분에 적용하는 것

## AOP 관련 용어
- Aspect : 공통기능
- Advice : Aspect의 기능 자체
- JointPoint : Advice를 적용해야하는 부분(필드, 메소드 등)(스프링에서는 메소드만 해당)
- PointCut : JointPoint의 부분으로 실제로 Advice가 적용된 부분
- Weaving : Advice를 핵심 기능에 적용하는 행위

## Spring에서 AOP 구현 방법
- Proxy 이용
- Client(호출부) -> Proxy(대행) -> Target(핵심기능)
- 방식
  - XML 스키마 기반의 AOP 구현
  - @Aspect 어노테이션 기반의 AOP 구현
  
## 작업 순서
1. 의존 설정 (pom.xml)
  ~~~xml
  <!-- AOP -->
  <dependency>
    <groupId
2. 공통 기능의 클래스 제작 - Advice 역할 클래스
3. XML설정 파일에 Aspect 설정
