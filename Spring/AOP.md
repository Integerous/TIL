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
  
## XML 기반의 AOP 구현 작업 순서
1. 의존 설정 (pom.xml)
  ~~~xml
  <!-- AOP -->
  <dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.7.4</version>
  </dependency>
  ~~~
2. 공통 기능의 클래스 제작 - Advice 역할 클래스
~~~java
public class LogAop {
  public Object loggerAop(ProceedingJoinPoint joinpoint) throws Throwable{
    String signatureStr = jointpoint.getSignature().toShortString();
    System.out.println(signatureStr + "is start.");
    long st = System.currentTimeMillis();
  
  try{
    Object obj = jointpoint.proceed();
    return obj;
  } finally{
    long et = System.currentTimeMillis();
    System.out.println(signatureStr + "is finished.");
    System.out.println(signatureStr + "경과 시간 :" +(et - st));
    }
  }
}
~~~
3. XML설정 파일에 Aspect 설정
~~~xml
<bean id="logAop" class="com.javalec.ex.LogAop"/>

<aop:config>
  <aop:aspect id="logger" ref="logAop">
    <aop:pointcut id="publicM" expression="within(com.javalec.ex.*)"/>
    <aop:around pointcut-ref="publicM" method="loggerAop"/>
  </aop:aspect>
<aop:config>
~~~

## Advice 종류
- <aop:before> : 메소드 실행 전에 advice 실행
- <aop:after-returning> : 정상적으로 메소드 실행 후에 advice
- <aop:after-throwing> : 메소드 실행 중 exception 발생시 advice 실행
- <aop:after> : 메소드 실행 중 exception이 발생해도 advice 실행
- <aop:around> : 메소드 실행 전/후 및 exception 발생 시 advice 실행

## @Aspect를 이용한 AOP 구현 작업 순서
>코드는 [Spring-for-junior-dev repo]()에!
1. 의존 설정(pom.xml)
2. @Aspect 클래스를 이용한 Aspect 클래스 제작
3. XML파일에 <aop:aspectj-autoproxy /> 설정

## Aspectj pointcut 표현식
- pointcut을 지정할 때 사용하는 표현식으로 Asepctj 문법을 사용
- 표현식
  - Execution
  ~~~java
    - @Pointcut("execution(public void get*(..))") //public void인 모든 get메소드
    - @Pointcut("execution(* com.javalec.ex.*.*())") //com.javalec.ex 패키지에 파라미터가 없는 모든 메소드
    - @Pointcut("execution(* com.javalec.ex..*.*())") //com.javalec.ex 패키지 & com.javalec.ex 하위 패키지에 파라미터가 없는 모든 메서드
    - @Pointcut("execution(* com.javalec.ex.Worker.*())") //com.javalec.ex.Worker 안의 모든 메소드
  ~~~
  - within
  ~~~java
    - @Pointcut("within(com.javalec.ex.*)") // com.javelec.ex 패키지 안에 있는 모든 메소드
    - @Pointcut("within(com.javalec.ex..*)") // com.javelec.ex 패키지 및 하위 패키지 안에 있는 모든 메소드
    - @Pointcut("within(com.javalec.ex.Worker)") // com.javelec.ex.Worker 패키지 안에 있는 모든 메소드
  ~~~
  - bean
  ~~~java
    - @Pointcut("bean(student)") // student bean에만 적용
    - @Pointcut("bean(*ker)") // ~ker로 끝나는 bean에만 적용
  ~~~
  
  # Reference
  - 신입 개발자를 위한 Spring Framework 강의
