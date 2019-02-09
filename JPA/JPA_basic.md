# JPA 프로그래밍 기본기 다지기
>작성중  
>[Tacademy 강의](https://www.youtube.com/watch?v=WfrSN9Z7MiA&t=58s)  
>강사: 김영한님 (현 우아한형제들 개발자)


지금 시대는 객체를 관계형 DB에 관리

## 1. SQL 중심적인 개발의 문제점
### 1.1 무한 반복(CRUD)
아래와 같은 상황에서 `private String tel;`필드가 추가되면 모든 tel과 관련된 sql을 다 수정해야 된다.
~~~java
public class member {
  private String memberId;
  private String name;

  ...
}
~~~
~~~sql
INSERT INTO MEMBER(MEMBER_ID, NAME) VALUES
SELECT MEMBER_ID, NAME FROM MEMBER M
UPDATE MEMBER SET ...
~~~
  
### 1.2 Entity 신뢰 문제
아래와 같은 상황에서 Member객체에 Team, Order, Delivery와 연관을 맺어놨을거라는 보장이 없어서 아래처럼 작성할 수 없다.

~~~java
class MemberService {
  ...
  public void process(String id) {
    Member member = memberDAO.find(id);

    member.getTeam(); // ???
    member.getOrder().getDelivery(); // ???
  }
}
~~~
  
### 1.3 계층형 아키텍쳐에서 진정한 의미의 계층 분할이 어렵다.
>위에서 처럼 물리적으로는 분할되어있지만 논리적으로는 분할되었는지 모른다.

### 1.4 SQL에 의존적인 개발을 피하기 어렵다.

## 2. 패러다임의 불일치
>객체 vs 관계형 데이터베이스

객체를 영구 보관하는 다양한 저장소가 있지만 현실적인 대안은 RDB와 NoSQL 뿐이다.  
결국 객체를 SQL로 변환(작성)하여 저장하게 된다.  
개발자가 SQL 매퍼일을 너무 많이 한다.

MyBatis Generate 같은 경우에 만들기는 편하지만 유지보수가 잘 안된다.

### 2.1 객체와 관계형 데이터베이스의 차이
1. 상속
2. 연관관계
  - 객체의 연관관계에는 방향성이 있다.
  - 테이블의 연관관계는 방향성이 없다.
  - 때문에 보통은 아래와 같이 객체를 테이블에 맞추어 모델링한다.
    ~~~java
    class Member {
      String id;      
      Long teamId;  //TEAM_ID FK 컬럼 사용
      String username;
    }
   
    class Team {
      Long id;  //TEAM_ID PK 사용
      String name;
    }
    ~~~
  - 객체다운 모델링은 아래와 같다.
    - Member에 Team의 값이 아니라 오브젝트가 있는것이 더 객체지향적인 것이다.
    ~~~java
    class Member {
      String id;
      Team team;
      String username;
      
      Team getTeam() {
        return team;
      }
    }
    
    class Team {
      Long id;
      String name;
    }
    ~~~
  - 객체는 자유롭게 객체 그래프를 탐색할 수 있어야 한다.

3. 데이터 타입
4. 데이터 식별 방법

객체답게 모델링할수록 매핑 작업만 늘어난다.
객체를 자바 컬렉션에 저장하듯이 DB에 저장할 수는 없을까?

### 2.2 ORM?
- Object-relational mapping(객체 관계 매핑)
- 객체는 객체대로 설계
- 관계형 데이터베이스는 관계형 데이터베이스대로 설계
- ORM 프레임워크가 중간에서 매핑
- 대중적인 언어에는 대부분 ORM 기술이 존재

### 2.3 JPA는 표준 명세 
- JPA는 인터페이스의 모음
- JPA 표준 명세를 구현한 3가지 구현체
  - Hibernate
  - EclipseLink
  - DataNucleus
- 우리가 실제로 쓰는 것은 Hibernate라고 보면 된다.(JPA는 인터페이스)

## 3. 실습

~~~java
public class Main {
  public static void main(String[] args) {
  
  EntityManagerFactory emf = Persistence.CreateEntityManagerFactory("hello");
  // persistence.xml persistence-unit의 name이 hello 
  
  EntityManager em = emf.createEntityManager();
  EntityTransaction tx = em.getTransaction();
  tx.begin();
  
  // 작업
  try {
      Member member = new Member();
      member.setId(100L);
      member.setName("한정수");

      em.persist(member);
      tx.commit();
    
  } catch (Exception e) {
      tx.rollback();
  } finally {
      em.close();
  }
  
  emf.close();
~~~

### 3.1 주의점
- EntityManagerFactory는 하나만 생성해서 어플리케이션 전체에서 공유
- EntityManager는 쓰레드 간에 공유하면 안된다. (사용하고 버려야 한다.)
- JPA의 모든 데이터 변경은 트랜잭션 안에서 실행 



