# 스프링 부트를 이용한 웹 서비스 개발
>작성중  
>[Tacademy 강의](https://tacademy.skplanet.com/live/player/onlineLectureDetail.action?seq=145#sec2)  
>강사: 김지헌(Honeymon, 현 우아한형제들 개발자)

스프링부트는 톰캣과 같은 WAS가 설치된 곳에 WAR파일을 배포하는 것이 아니라  
자기 자신을 배포하고 실행할 수 있는 아키텍쳐를 가지게 되었다.

## 스프링 부트 특징
>스프링부트 프로젝트에 명시된 [Spring boot feature](https://spring.io/projects/spring-boot)
- 단독실행 가능한 스프링 어플리케이션 생성
- 내장 컨테이너로 Tomcat, Jetty, Undertow 중에서 선택가능 (WAR파일로 배포할 필요 없음)
- 스타터(starter)를 통해 간결한 의존성 구성 지원
- 스프링과 써드파티 라이브러리에 대한 자동구성(Auto-Configuration) 제공
- 제품출시(realease) 후, 운영에 필요한 다양한 기능(metrics, health check, externalized configuration) 제공
- XML 구성 필요없음

## 스프링 부트를 한마디로?
>스프링 프레임워크를 기반으로한 개발플랫폼

## 스프링 부트 구성 요소
>아래 4가지 요소가 스프링 부트 개발 플랫폼을 이룬다.

1. 빌드도구 (Gradle vs Maven)
2. 스프링 프레임워크 (4.xx vs 5.xx)
3. 스프링 부트(v1.5 vs v2.0)
4. 스프링 부트 스타터(spring-boot-starter)

## 빌드
Spring Initializr(https://start.spring.io)에서 시작

Eclipes 나 IntelliJ에서 스프링 부트를 사용하는 것도  
사실 Spring Initializr에 url로 GET요청을 날려서 프로젝트를 생성하고
그 파일을 내려받는 방식으로 동작하는 것이다.

스프링 부트가 지원하는 개발 언어는 Java, Kotlin, Groovy가 있고,  
Gradle이 Groovy를 DSL(Domain Specification Language)로 자체언어로 사용하고 있지만 
그 외적인 부분에서는 Groovy의 유저는 많지 않다.

## Gradle

Gradle와 Maven은 각각 프로젝트의 의존성과 기본적인 빌드 동작을 선언하는 스크립트가 있다.  
Gralde은 build.gradle , Maven은 pom.xml  
build.gradle 스크립트 안에는 buildscript 라는 선언부에서 스프링 부트 Gradle 플러그인을 선언한다.

## 프로젝트 기본 코드구조

### Gradle

- build.gradle
- gradle
  - wrapper
    - gradle-wrapper.jar `수동으로 로컬에 설치할 필요없이 프로젝트에 내장을 시켜서 빌드하는 것`
    - gradle-wrapper.properties
- gradlew `리눅스/유닉스 계열에서 실행되는 스크립트`
- gradlew.bat `윈도우에서 실행되는 배치파일`
- settings.gradle `프로젝트의 기본 구성 정의`
- src
  - main
    - Java
      - io.Honeymon
        - t
          - springboot
            - TSrpingBootApplication.java `
    - resources
      - application.yml
      - static
      - templates
  - test
    - java
      - io.honeymon
        - t
          - springboot
            -TSrpingBootApplication.java

>빌드가 실행되면 컴파일 단계에서는 `src-main-java`와 `src-main-resources` 영역만 컴파일되고 패키징되어 배포된다.

## (Excecutable) JAR vs WAR

기존의 배포 형태는 서버에 톰캣과 같은 WAS가 설치되어 있고, WAS의 특정 위치에 springboot.war라는 배포본을 업로드하면 톰캣이 파일을 읽어들여서 실행하는 형태였다면,  

지금의 배포 형태는 springboot.jar파일과 embeded container를 묶어서 패키징하는 bootRepackage가 발생한다. `.jar` 파일을 가지고 `$java -jar springboot.jar`를 명령하면 바로 실행가능한 형태가 된다.

Docker 같은 컨테이너 안에서도 실행가능한 jar파일을 배포하고 java 빌드팩을 설치해서 작은 단위의 컨테이너 안에서도 어플리케이션을 실행할 수 있다.

## 스프링 부트 v1.5 vs v2.0
>스프링 프레임워크 4를 쓰느냐 5를 쓰느냐의 차이  
>2019년 8월 1일부로 스프링부트 v1.5 버전은 끝날 예정이기 때문에 2.0 사용 권장

## 스프링 부트는 Annotation 기반 동작

### 스프링 부트를 구성하는 Annotation
- @SpringBootApplication
- @ComponentScan
- @EnableAutoConfiguration
- @Configuration
- @ConditionalOn~
- @SpringBootConfiguration(= @Configuration)
- @EnableConfigurationProperties
- @ConfigurationProperties


### @SpringBootApplication

~~~java
@SpringBootApplication
public class BootSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootSpringBootApplication.class, args);
    }
}
~~~

1. `public static void main(String[] args)`이라는 entry point(진입점)을 통해서 어플리케이션이 실행되면,
2. `SpringApplication.run(BootSpringBootApplication.class, args);`이라는 클래스가 실행되면서, 이 안에서 Spring IoC Container라는 객체를 관리하는 컨테이너를 실행시키고,
3. `@SpringBootApplication` 어노테이션이 붙은 위치를 기준으로 하향식으로 밑에 있는 패키지를 탐색하는 것이 스프링 부트의 기본적인 동작 방식이다.

## Spring boot starter

스프링 부트 스타터는 `spring-boot-autoconfigure`와 `spring-boot-dependencies` 두 개의 모듈이 합쳐져서 기본 동작을 한다.

그리고 이 스프링 부트 스타터를 기준으로  
`spring-boot-starter-web`, `spring-boot-starter-security` 등 기능별로 의존성을 모아서 정의한다.

또한 스프링부트 스타터를 사용하지 않으면 각 라이브러리 마다 버전을 명시해야하는 불편함이 있다. 하지만 스프링부트 스타터를 사용하면 개발자가 신경써야할 부분이 3가지로 줄어든다.

1. 스프링 부트 버전
2. 사용하려는 라이브러리의 스프링 부트 스타터 지원 여부
3. 지원하지 않는 경우 사용하려는 라이브러리 등록 방법

스프링 부트 개발팀에서 공식적으로 지원하는 스타터의 목록과 커뮤니티에서 제공하는 스타터 목록을 [스프링 프로젝트 깃헙](https://github.com/spring-projects/spring-boot/tree/master/spring-boot-project/spring-boot-starters)에서 확인 가능하다.

## 자동구성(Auto-configuration)
- 스프링 부트가 기술흐름에 따라 제공하는 관례(Convention)적인 구성
- 자동구성이 어떻게 되는지 살펴보려면 스프링 프로젝트 깃헙의 [spring-boot-autoconfigure](https://github.com/spring-projects/spring-boot/tree/master/spring-boot-project/spring-boot-autoconfigure)이라는 모듈을 보면 된다.
- 동작 선언
    - @EnableAutoConfiguration (in @SpringBootApplication)
    - @Configuration
- 사용 애너테이션
    - @Configuration
    - @ConditionalOn

## 외부구성(External Configuration)
>스프링 부트는 어플리케이션 속성을 외부로부터 주입받아서 적용할 수 있다.

### 적용 우선순위 (원래는 13단계)
>1번부터 적용하고 뒤로 갈수록 덮어쓰는 형태

1. 실행 인자 (터미널 상에서 실행하거나 AWS에서 환경변수로 전달하는 경우)
2. SPRING_APPLICATION_JSON (서버 안에 JSON으로 선언한 구성파일을 위치를 지정했을때)
3. 환경변수 (운영체제의 환경변수)
4. 기타 등등
5. application.yml 또는 application.properties
6. application-{defaultprofiles}.yml 또는 application-{defaultprofiles}.properties

## Programming in Spring Environment

- @ComponentScan을 통해 아래의 어노테이션들이 붙은 클래스들을 탐색해서 스프링 빈으로 등록하고 ApplicationContext에 적재
  - @Repository
  - @Component
  - @Service
  - @Controller / @RestController
  - @Configuration
    - @Bean
    - @ConfigurationProperties
- DI, IoC, @Autowired
- @Value vs @ConfigurationProperties
- AOP



