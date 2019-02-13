# JPA 프로그래밍 기본기 다지기
>작성중  
>[Tacademy 강의](https://www.youtube.com/watch?v=WfrSN9Z7MiA&t=58s)  
>강사: 김영한님 (현 우아한형제들 개발자)


지금 시대는 객체를 관계형 DB에 관리

## 1. SQL 중심적인 개발의 문제점
### 1.1. 무한 반복(CRUD)
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
  
### 1.2. Entity 신뢰 문제
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
  
### 1.3. 계층형 아키텍쳐에서 진정한 의미의 계층 분할이 어렵다.
>위에서 처럼 물리적으로는 분할되어있지만 논리적으로는 분할되었는지 모른다.

### 1.4. SQL에 의존적인 개발을 피하기 어렵다.

## 2. 패러다임의 불일치
>객체 vs 관계형 데이터베이스

객체를 영구 보관하는 다양한 저장소가 있지만 현실적인 대안은 RDB와 NoSQL 뿐이다.  
결국 객체를 SQL로 변환(작성)하여 저장하게 된다.  
개발자가 SQL 매퍼일을 너무 많이 한다.

MyBatis Generate 같은 경우에 만들기는 편하지만 유지보수가 잘 안된다.

### 2.1. 객체와 관계형 데이터베이스의 차이
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

### 2.2. ORM?
- Object-relational mapping(객체 관계 매핑)
- 객체는 객체대로 설계
- 관계형 데이터베이스는 관계형 데이터베이스대로 설계
- ORM 프레임워크가 중간에서 매핑
- 대중적인 언어에는 대부분 ORM 기술이 존재

### 2.3. JPA는 표준 명세 
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

### 3.1. 주의점
- EntityManagerFactory는 하나만 생성해서 어플리케이션 전체에서 공유
- EntityManager는 쓰레드 간에 공유하면 안된다. (사용하고 버려야 한다.)
- JPA의 모든 데이터 변경은 트랜잭션 안에서 실행 

## 4. 데이터베이스 스키마 자동 생성하기
- DDL을 어플리케이션 실행 시점에 자동 생성
- 테이블 중심 -> 객체 중심
- 데이터베이스 방언(dialect)를 활용해서 데이터베이스에 맞는 적절한 DDL 생성
- 개발 환경에서만 사용! (운영 환경에서는 다듬어서 사용)

### 4.1. 옵션
>hibernate.hbm2ddl.auto 의 옵션(value)을 아래의 것들로 사용
- create: 기존 테이블 삭제 후 다시 생성 (DROP + CREATE)
- create-drop: create와 같으나 종료시점에 테이블 DROP
- update: 변경된 부분만 반영 (운영 DB에 사용하면 안됌)
- validate: entity와 table이 정상 매핑되었는지만 확인
- none: 사용하지 않음

## 5. 매핑 어노테이션
>데이터베이스에 어떤 식으로 매핑될지에 대한 매핑정보

- @Column
  - @Column(name="EXAMPLE") 옵션을 주면 필드와 매핑할 DB의 컬럼명을 지정한다.
  - 그 외에 insertable, updatable, nullable, unique, length 등의 옵션이 있다.
- @Temporal
  - 날짜 타입 매핑
  - @Temporal(TemporalType.DATE) // 날짜
  - @Temporal(TemporalType.TIME) // 시간
  - @Temporal(TemporalType.TIMESTAMP) // 날짜와 시간
- @Enumerated(EnumType.STRING)
  - 디폴트는 `EnumType.ORDINAL`인데 Enum에 정의된 순서를 숫자로 반환한다.
  - 그런데 만약 순서가 바뀌면 모든게 꼬여버리므로 운영에서는 절대 사용하면 안된다.
  - `EnumType.STRING` 옵션을 주면 Enum에 정의된 글자가 그대로 들어가므로, 이 옵션이 권장된다. 
- @Lob
  - 컨텐츠의 길이가 너무 길 경우에 binary파일로 DB에 넣을 경우 @Lob을 사용한다.
  - CLOB과 BLOB이 있다. CLOB은 Character 형태의 긴 컨텐츠를 저장하는 것이고, BLOB은 Binary 형태의 긴 컨텐츠를 저장하는 것이다.
  - @Lob 어노테이션을 String 타입에 쓰면 CLOB이 되고, Byte 타입에 쓰면 BLOB이 된다.
- @Transient
  - 이 컬럼은 매핑하지 않는다.
  - 컬럼을 DB에는 저장하지 않지만 객체에는 두고 싶을 때 사용 (ex 임시 flag값)

### 5.1. 식별자 매핑 어노테이션
- @Id 직접 매핑
- @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  - 데이터베이스에 위임 (MySQL의 AUTO INCREMENT)
- @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
  - 데이터베이스 시퀀스 오브젝트 사용 (Oracle)
  - @SequenceGenerator 필요
- @Id @GeneratedValue(strategy = GenerationType.TABLE)
  - 키 생성용 테이블 사용, 모든 DB에서 사용
  - @TableGenerator 필요
- @Id @GeneratedValue(strategy = GenerationType.AUTO)
  - 디폴트
  - 방언(dialect)에 따라 위의 방법 중 자동 지정

### 5.2. 권장하는 식별자 전략
- 기본키의 제약 조건: null 아니고, 유일하며, 변하지 않는다.
- 하지만 변하지 않는 것은 없기 때문에(심지어 주민번호도) 대체키를 쓰는 것을 권장한다.
- 대체키는 데이터베이스의 Sequence, Auto Increment, 키 생성 테이블 등 비즈니스와 전혀 관계없는 것을 쓰는 것이 좋다.
- int타입은 10억~20억 사이에서 끝나기 때문에 Long을 쓰는 것을 권장한다.
- 권장: `Long타입 + 대체키 + 키 생성전략` 사용


## 6. 연관관계 매핑

### 6.1. 객체를 테이블에 맞추어 모델링 할 경우

#### 6.1.1. 참조 대신에 외래키를 그대로 사용
~~~java
@Entity
public class Member {
  
  @Id @GeneratedValue
  private Long id;
  
  @Column
  private String name;
  private int age;
  
  @Column(name = "TEAM_ID")
  private Long teamID;
  ...
}

@Entity
public class Team {
  
  @Id @GeneratedValue
  private Long id;
  private String name;
  ...
}
~~~

#### 6.1.2. 외래키 식별자를 직접 다룸

~~~java
// 팀 저장
Team team = new Team();
team.setName("TeamA");
em.persist(team);

// 회원 저장
Member member = new Member();
member.setName("member1");
member.setTeamId(team.getId()); // 이 부분!
em.persist(member);
~~~

#### 6.1.3. 식별자로 다시 조회.
~~~java
// 조회
Member findMember = em.find(Member.class, member.getId());

//Member와 Team이 연관관계가 없음
Team findTeam = em.find(Team.class, team.getId());
~~~

즉, 객체를 테이블에 맞추어 모델링하는 것은 객체지향적인 방법이 아니다.  
객체를 테이블에 맞추어 데이터 중심으로 모델링하면, 협력관계를 만들 수 없다.

- 테이블은 외래키로 조인을 사용해서 연관된 테이블을 찾는다.
- 객체는 참조를 사용해서 연관된 객체를 찾는다.
- 이처럼 테이블과 객체는 큰 격차가 있는데, 위와 같은 방법은 이 격차를 무시한다.

## 7. 단방향 매핑

### 7.1. 객체의 참조(team)와 테이블의 외래키(TEAM_ID)를 매핑 (=연관관계 매핑)
~~~java
@Entity
public class Member {
  
  @Id @GeneratedValue
  private Long id;
  
  @Column
  private String name;
  private int age;
  
  //@Column(name = "TEAM_ID")
  //private Long teamID;
  
  @ManyToOne
  @JoinColumn(name = "TEAM_ID")
  private Team team;
  
  ...
}

@Entity
public class Team {
  
  @Id @GeneratedValue
  private Long id;
  private String name;
  ...
}
~~~

위의 코드에서 만약 `@ManyToOne(fetch = FetchType.LAZY)` 를 주면,  
Member 객체만 조회하고 Team 객체는 실제 사용되는 시점에 DB를 조회한다. (`지연 로딩`)  
그렇다고 Team 객체가 null이 되면 에러가 나기 때문에 Team 객체는 `프록시 객체(가짜 객체)`가 들어간다.
디폴트는 `(fetch = FetchType.EAGER)`로 같이 조회한다.

권장하는 것은 `LAZY`(지연 로딩)이다.  
현업에서는 전부 LAZY로 바르고, 꼭 필요한 곳에서는 쿼리를 날리는 시점에 원하는 것을 미리 최적화해서 가져오는 방법을 쓰게 한다.  
즉, 속단해서 최적화하지 말자는 것이다.
### 7.2. 연관관계 저장

~~~java
// 팀 저장
Team team = new Team();
team.setName("TeamA");
em.persist(team);

// 회원 저장
Member member = new Member();
member.setName("member1");
member.setTeam(team); // 단방향 연관관계 설정, 참조 저장
em.persist(member);
~~~

### 7.3. 참조로 연관관계 조회 - 객체 그래프 탐색
~~~java
// 조회
Member findMember = em.find(Member.class, member.getId());

// 참조를 사용해서 연관관계 조회
Team findTeam = findMember.getTeam();
~~~

## 8. 양방향 매핑

### 8.1. Team 객체에서도 Member 갖도록
~~~java
@Entity
public class Team {
  
  @Id @GeneratedValue
  private Long id;
  private String name;
  
  @OneToMany(mappedBy = "team")
  List<Member> members = new ArrayList<Member>();
  ...
}
~~~

### 8.2. 반대 방향으로 객체 그래프 탐색
~~~java
// 조회
Team findTeam = em.find(Team.class, team.getId());

// 역방향 조회
int memberSize = findTeam.getMembers().size();
~~~

### 8.3. 객체와 테이블이 관계를 맺는 차이
- 객체 연관관계
  - 회원 -> 팀 연관관계 1개 (단방향)
  - 팀 -> 회원 연관관계 1개 (단방향)
- 테이블 연관관계 
  - 회원 <-> 팀 연관관계 1개 (양방향)

### 8.4. 객체의 양방향 관계
- 객체의 양방향 관계는 사실 양방향 관계가 아니라 서로 다른 단방향 관계 2개이다.
- 객체를 양방향으로 참조하려면 단방향 연관관계를 2개 만들어야 한다.

### 8.5. 테이블의 양방향 관계
- 테이블은 외래키 하나로 두 테이블의 연관관계 관리
- MEMBER.TEAM_ID 외래키 하나로 양방향 연관관계를 가짐 (양쪽으로 조인할 수 있다.)

~~~sql
SELECT *
FROM MEMBER M
JOIN TEAM T ON M.TEAM_ID = T.TEAM_ID

SELECT *
FROM TEAM T
JOIN MEMBER M ON T.TEAM_ID = M.TEAM_ID
~~~

### 8.6. 객체의 양방향 관계에서의 문제점
예를 들어 Member 객체에서 Team 객체의 값을 변경시키거나,  
Team 객체에서 members에 member를 추가하는 등의 변화가 양쪽에서 일어난다면 어느쪽을 신뢰해야 하는가?

그래서 둘 중 하나로 외래키를 관리해야 한다.  
즉, 한 쪽을 `연관관계의 주인`으로 만들어주고 나머지 한쪽을 조회만 하도록 하는 것이다.

### 8.7. 양방향 매핑 규칙
- 객체의 두 관계 중 하나를 연관관계의 주인으로 지정
- 연관관계의 주인만이 외래키를 관리 (등록, 수정)
- 주인이 아닌 쪽은 읽기만 가능
- 주인은 mappedBy 속성 사용 X
- 주인이 아니면 mappedBy 속성으로 주인 지정

### 8.8. 누구를 주인으로?
- 외래키가 있는 곳을 주인으로 정해라
- 권장하는 것은 단방향으로 설계를 끝내고 개발하면서 양방향이 필요한 부분이 생기면 코드를 추가하는 방식을 권한다.

### 8.9. 양방향 매핑시 가장 많이하는 실수
- 연관관계의 주인에 값을 입력하지 않는 것.

~~~java
Team team = new Team();
team.setName("TeamA");
em.persist(team);

Member member = new Member();
member.setName("member1");

//역방향(주인이 아닌 방향)만 연관관계 설정
team.getMembers().add(member);
em.persist(member);
~~~

이 경우, TEAM_ID가 null이 된다.  

현업에서는 그냥 양쪽 모두 값을 입력하면 된다.  
객체지향 관점에서도 양쪽 모두 값을 입력하는 것이 맞다.

### 8.10. 양방향 매핑의 장점
- 단방향 매핑만으로도 이미 연관관계 매핑은 완료
- 양방향 매핑은 반대 방향으로 조회(객체 그래프 탐색) 기능이 추가된 것 뿐
- JPQL에서 역방향으로 탐색할 일이 많음
- 단방향 매핑을 잘하고 양방향 매핑은 필요할 때 추가하면 됌. (테이블에 영향 없음)


## 9. JPA 내부 구조 - 영속성 컨텍스트
JPA에서 가장 중요한 2가지
- 객체와 관계형 데이터베이스 매핑하기
- 영속성 컨텍스트(PersistenceContext)

### 9.1. 영속성 컨텍스트 란?
- JPA를 이해하는데 가장 중요한 용어
- `엔티티를 영구 저장하는 환경`이라는 뜻
- 영속성 컨텍스트는 논리적인 개념(눈에 보이지 않는다.)
- 엔티티 매니저를 통해서 영속성 컨텍스트에 접근
- 엔티티 매니저와 영속성 컨텍스트는 1:1 이라서 그냥 `EntityManager = PersistenceContext`라고 이해하면 된다.
  - 스프링 프레임워크 같은 컨테이너 환경에서는 EntityManager와 PersistenceContext가 `N:1`이다.
- 같은 트랜잭션이면 같은 영속성 컨텍스트에 접근하게 된다.  

### 9.2. Entity의 생명주기
- 비영속(new/transient)
  - 영속성 컨텍스트와 전혀 관계가 없는 상태
  - Member 객체를 생성만 한 상태
  ~~~java
  //객체를 생성한 상태(비영속)
  Member member = new Member();
  member.setId("memberId");
  member.setUsername("회원1");
  ~~~
- 영속(managed)
  - 영속성 컨텍스트에 저장된 상태
  - 객체를 생성하고 저장한 상태(영속성 컨텍스트에 의해서 객체가 관리(managed)되는 상태)
  ~~~java
  Member member = new Member();
  member.setId("memberId");
  member.setUsername("회원1");
  
  EntityManager em = emf.createEntityManager();
  em.getTransaction().begin();
  
  //객체를 저장한 상태(영속)
  em.persist(member);
  ~~~
  
- 준영속(detached)
  - 영속성 컨텍스트에 저장되었다가 분리된 상태
  - `em.detach(member);
- 삭제(removed)
  - 삭제된 상태
  - `em.remove(member);`

## 10. 영속성 컨텍스트의 이점

### 10.1. 1차 캐시
- PersistenceContext에는 내부에 1차 캐시가 있다. 일반적인 캐시가 아니라 영속성컨텍스가 생성되고 없어질 때 까지만 잠깐 존재하는 것.
- 1차 캐시에서 조회
~~~java
Member member = new Member();
member.setId("member1");
member.setUsername("회원1");

//1차 캐시에 저장됌
em.persist(member);

//1차 캐시에서 조회
Member findMember = em.find(Member.class, "member1");
~~~
`em.find()`에서 DB로 바로가는 것이 아니라 1차 캐시를 먼저 탐색한다. (존재하면 바로 반환)
- 1차 캐시에 없으면
  - 데이터베이스에서 조회하고
  - 조회된 내용을 1차 캐시에 저장 후에 반환
  
### 10.2. 영속 엔티티의 동일성(identity) 보장
~~~java
Member a = em.find(Member.class, "member1");
Member b = em.find(Member.class, "member2");

System.out.println(a == b); // 동일성 비교 true -> 위에서 보았듯이 1차캐시가 있기 때문에
~~~
- 1차 캐시로 반복 가능한 읽기(REPEATABLE READ) 등급의 트랜잭션 격리 수준을 데이터베이스가 아닌 어플리케이션 차원에서 제공
  
### 10.3. 트랜잭션을 지원하는 쓰기 지연(transactional write-behind) - 버퍼 기능
- 예를 들어 `persist(memberA)` 명령의 경우 memberA를 1차 캐시에 저장하면서 동시에 INSERT SQL을 생성해서 `쓰기 지연 SQL 저장소`에 말아놓는다.
- 이후에 `persist(memberB)` 명령의 경우도 위와 같이 동작하고, 여전히 DB에 넣지 않는다.
- 이후에 `commit()` 명령을 해야 쓰기 지연 SQL 저장소에 있던 INSERT SQL 쿼리 2개를 (옵션에 따라 동시에 혹은 하나씩) DB에 넣는다.
- `쓰기 지연 SQL 저장소`에 있던 쿼리들을 날리는 과정을 `flush`라고 한다.
  - 하지만 flush를 한다고 1차캐시의 내용들이 지워지는 것이 아니라 쿼리를 보내서 DB와 싱크를 맞추는 역할을 한다.
  - `commit()` 이 flush와 commit 두 가지 일을 하는 것이다.
~~~java
EntityManager em = emf.createEntityManager();
EntityTransaction transaction = em.getTransaction();
// 엔티티 매니저는 데이터 변경시 트랜잭션을 시작해야 한다.
transaction.begin();

em.persist(memberA);
em.persist(memberB);
//여기까지 INSERT SQL을 데이터베이스에 보내지 않는다.

//커밋하는 순간 데이터베이스에 INSERT SQL을 보낸다.
transaction.commit(); // [트랜잭션] 커밋
~~~
  
### 10.4. 변경 감지(Dirty Checking)
~~~java
EntityManager em = emf.createEntityManager();
EntityTransaction transaction = em.getTransaction();
// 엔티티 매니저는 데이터 변경시 트랜잭션을 시작해야 한다.
transaction.begin(); // [트랜잭션] 시작

// 영속 엔티티 조회
Member memberA = em.find(Member.class, "memberA");

// 영속 엔티티 데이터 수정
memberA.setName("hjs");
memberA.setAge(10);

// 수정했으니 em.update(member) 이런 코드가 있어야 하지 않을까?

// 하지만 필요없다. 커밋하면 자동으로 업데이트 쿼리가 나간다.
transaction.commit(); // [트랜잭션] 커밋
~~~
- Dirty Checking의 동작 원리
  - JPA는 트랜잭션이 커밋되는 시점에 1차 캐시 뿐만 아니라 스냅샷도 생성한다.
  - commit()명령으로 flush를 하면 영속성 컨텍스트에 의해 관리되는 엔티티들을 스냅샷과 비교해서 바뀐 부분이 있으면 UPDATE 쿼리를 만들어서 DB에 보내고 commit을 한다.
- 이렇게 하는 이유
  - Java 컬렉션에서 값을 가져와서 변경해도 다시 컬렉션에 값을 담지 않는다. 그래도 컬렉션의 값이 바뀐다. 그것과 똑같은 컨셉이다.
  - 마치 Java 컬렉션에서 값을 가져와서 변경하는 것 처럼하기 위해 이런 방식으로 처리한다.
    
### 10.5. 지연 로딩(Lazy Loading)

## 11. Flush

### 11.1. 영속성 컨텍스트를 flush 하는 방법
- em.flush() - 직접 호출
- 트랜잭션 커밋 - 플러시 자동 호출
- JPQL 쿼리 실행 - 플러시 자동 호출
  - JPQL 쿼리 실행시 flush가 자동으로 호출되는 이유
    ~~~java
    em.persist(memberA);
    em.persist(memberB);
    em.persist(memberC);
    ~~~
    
    // 중간에 JPQL 실행
    query = em.createQuery("select m from Member m", Member.class);
    List<Member> members = query.getResultList();
    ~~~
    - 이 상황에서는 DB에서 데이터 조회가 하나도 안된다. flush가 안되었기 때문에.
    - 때문에 JPA에서는 JPQL을 실행하면 flush가 자동으로 호출되도록 했다. (MyBatis나 Spring JDBC와 함께 사용할 때는 flush를 직접 해줘야 한다.)

### 11.2. Flush 옵션
- `em.setFlushMode(FlushModeType.AUTO)` - 디폴트
  - 커밋이나 쿼리를 실행할 때 flush
- `em.setFlushMode(FlushModeType.COMMIT)`
  - 커밋할 때만 flush
  
### 11.3. Flush는!
- 영속성 컨텍스트를 비우지 않음
- 영속성 컨텍스트의 변경내용을 데이터베이스에 동기화하는 것이 flush의 목적
- flush가 가능한 이유는 DB에 트랜잭션이라는 작업 단위가 있기 때문. -> 커밋 직전에만 동기화하면 됨

## 12. 준영속 상태
- 영속 상태의 Entity가 영속성 컨텍스트에서 분리(detached)
- 영속성 컨텍스트가 제공하는 기능을 사용 못함

### 12.1. 준영속 상태로 만드는 방법
- em.detach(entity)
  - 특정 엔티티만 준영속 상태로 전환
- em.clear()
  - 영속성 컨텍스트를 완전히 초기화
- em.close()
  - 영속성 컨텍스트를 종료

### 12.2. 준영속 상태면 지연 로딩을 못쓴다.
지연 로딩을 쓰려면 영속성 컨텍스트가 살아있어야 한다.  
영속성 컨텍스트가 죽어있는데 지연 로딩이 적용된 객체를 터치하면 `LazyInitializationException` 에러가 터진다.(현업에서 자주 만난다.)  
영속성 컨텍스트가 DB커넥션 등을 다 들고있기 때문에 그렇다.

### 12.3. 프록시와 즉시로딩(EAGER) 주의
- 가급적 지연 로딩(LAZY)을 사용
- 즉시 로딩을 적용하면 예상하지 못한 SQL이 발생
- 즉시 로딩은 JPQL에서 N+1 문제를 일으킨다.
- @ManyToOne, @OneToOne은 기본이 즉시 로딩 -> LAZY로 설정할 것.
- @OneToMany, @ManyToMany는 기본이 지연 로딩

## 13. JPQL
- JPA를 사용하면 Entity 객체 중심으로 개발
- 문제는 검색 쿼리
- 검색을 할 때도 테이블이 아닌 Entity 객체를 대상으로 검색
- 모든 DB 데이터를 객체로 변환해서 검색하는 것은 불가능
- 어플리케이션이 필요한 데이터만 DB에서 불러내려면, 결국 검색 조건이 포함된 SQL이 필요

그래서 JPA는 SQL을 추상화한 JPQL이라는 객체지향 쿼리 언어 제공.  
SQL과 문법 유사(SELECT, FROM, WHERE, GROUP BY, HAVING, JOIN).  
JPQL은 Entity 객체를 대상으로 쿼리. (SQL은 데이터베이스 테이블을 대상으로 쿼리)

~~~java
// 검색
String jpql = "select m From Member m where m.name like '%hello%'"; //Member는 객체.

List<Member> result = em.createQuery(jpql, Member.class).getResultList();
~~~

JPQL은 테이블이 아닌 객체를 대상으로 검색하는 객체 지향 쿼리.  
SQL을 추상화해서 특정 데이터베이스 SQL에 의존하지 않는다.  
JPQL을 한마디로 정의하면 객체 지향 SQL

### 13.1. JPQL 문법
- Entity와 속성은 대소문자 구분
- JPQL 키워드는 대소문자 구분 안함(SELECT, FROM, where)
- Entity 이름을 사용, 테이블 이름이 아님
- 별칭은 필수
- 결과 조회 API
  - `query.getResultList()` : 결과가 하나 이상, 리스트 반환
  - `query.getSingleResult()` : 결과가 정확히 하나, 단일 객체 반환(정확히 하나가 아니면 예외 발생)
- 파라미터 바인딩
  ~~~java
  SELECT m FROM Member m where m.username=:username
  query.setParameter("username", usernameParam); //이름 기준 (권장)
  
  SELECT m FROM Member m where m.username=?1
  query.setParameter(1, usernameParam); //위치 기준
  ~~~
- 프로젝션
  - `SELECT m FROM Member m` -> 엔티티 프로젝션
  - `SELECT m.team FROM Member m` -> 엔티티 프로젝션
  - `SELECT username, age FROM Member m` -> 단순 값 프로젝션
  - new 명령어: 단순 값을 DTO로 바로 조회
    - `SELECT new jpabook.jpql.UserDTO(m.username,m.age) FROM Member m`
  - DISTINCT는 중복 제거
- 페이징 API
  - JPA는 페이징을 다음 두 API로 추상화
    - setFirstResult(int startPosition): 조회 시작 위치(0부터 시작)
    - setMaxResults(int maxResult): 조회할 데이터 수
  ~~~java
  //페이징 쿼리
  String jpql = "select m from Member m order by m.name desc";
  List<Member> resultList = em.createQuery(jpql, Member.class)
        .setFirstResult(10)
        .setMaxResults(20)
        .getResultList();
  ~~
- 집합과 정렬
~~~sql
select
    COUNT(m), //회원수
    SUM(m.age), //나이 합
    AVG(m.age), //평균 나이
    MAX(m.age), //최대 나이
    MIN(m.age)  //최소 나이
from Member m
~~~

- 조인 (일반 조인과 문법이 약간 다름)
  - 내부 조인
    - `SELECT m FROM Member m [INNER] JOIN m.team t`
  - 외부 조인
    - `SELECT m FROM Member m LEFT [OUTER] JOIN m.team t`
  - 세타 조인 (= 막 조인. 연관관계 상관 없이 조인)
    - `select count(m) from Member m, Team t where m.username = t.name`
    - 하이버네이트 5.1부터 세타 조인도 외부 조인 가능
  - fetch 조인
    - Entity 객체 그래프를 한번에 조회하는 방법
    - 별칭을 사용할 수 없다.
    - JPQL: `select m from Member m join fetch m.team` - **Member를 조회할 때 Team까지 같이 가지고 오는 것.**
    - SQL: `SELECT M.*, T.* FROM MEMBER T INNER JOIN TEAM T ON M.TEAM_ID=T.ID`
  - fetch 조인 예시
  ~~~java
  String jpql = "select m from Member m join fetch m.team";
  
  List<Member> members = em.createQuery(jpql, Member.class).getResultList();
  
  for (Member member : members) {
      // fetch 조인으로 Member와 Team을 함께 조회해서 지연 로딩 발생 안함
      System.out.println("username = " + member.getUsername() + ", " + "teamname = " + member.getTeam().name());
  ~~~
- 사용자 정의 함수 호출
  - `select function('group_concat', i.name) from Item i`
  - 하이버네이트는 사용 전 방언에 추가해야 한다.

- Named 쿼리
  - 미리 정의해서 이름을 부여해두고 사용하는 JPQL
  - 어노테이션, XML에 정의
  - 어플리케이션 로딩 시점에 초기화 후 재사용
  - **어플리케이션 로딩 시점에 쿼리를 검증**
  
  ~~~java
  @Entity
  @NamedQuery(
          name = "Member.findByUsername",
          query = "select m from Member m where m.username = :username")
  public class Member {
      ...
  }
  ~~~
  ~~~java
  List<Member> resultList =
    em.createNamedQuery("Member.findByUsername", Member.class)
          .setParameter("username", "회원1")
          .getResultList();
  ~~~
  
## 14. Spring Data JPA

### 14.1. Spring Data JPA 적용 전
~~~java
public class MemberRepository {
  
  public void save(Member member) {...}
  public Member findOne(Long id) {...}
  public List<Member> findAll() {...} // 공통
  
  public Member findByName(String name) {...} // 공통 아님
}

public class ItemRepository {

  public void save(Member member) {...}
  public Member findOne(Long id) {...}
  public List<Member> findAll() {...}
}
~~~

위와 같이 결국 CRUD가 반복된다.  
Spring Data JPA는 지루하게 반복되는 CRUD 문제를 **세련된 방법**으로 해결.  
개발자는 인터페이스만 작성하고, 스프링 데이터 JPA가 구현 객체를 동적으로 생성해서 주입.

### 14.2. Spring Data JPA 적용 후
Spring Data JPA가 로딩 시점에 ItemRepository를 확인하고 구현클래스를 생성해준다.  

~~~java
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByName(String name);
}

public interface ItemRepository extends JpaRepository<Item, Long> {
    // 비어있음
}
~~~

공통화 할 수 없었던 `Member findByName(String name);` 을 제외하고는 JpaRepository 인터페이스를 상속받으면 끝이다.  
즉, JpsRepository 인터페이스는 공통 CRUD를 제공해준다.  
제네릭은 `<엔티티, 식별자>`로 설정.

Spring Data JPA는 Spring Data 프로젝트의 인터페이스를 상속 받은 것이다.  
스프링 데이터 JPA의 `JpaRepository` 인터페이스 ->  
스프링 데이터의 `PagingAndSortingRepository` 인터페이스 ->  
`CrudRepository` 인터페이스 ->  
`Repository` 인터페이스

### 14.3. 쿼리 메서드 기능
- 메서드 이름만으로 JPQL 쿼리 생성
  ~~~java
  public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByName(String name); //이렇게만 작성하면 알아서 JPQL 짜줌
  }
  ~~~
  - 예를 들어 `List<Member> member = memberRepository.findByName("hello")`의 경우 실행될 SQL은
    ~~~sql
    SELECT*FROM MEMBER M WHERE M.NAME = 'hello'
    ~~~
  - 이름으로 검색+정렬 하는 경우
    ~~~java
    pulbic interface MemberRepository extends JpaRepository<Member, Long> {
        List<Member> findByName(String name, Sort sort); // Sort가 이미 구현되어 있다.
    }
    ~~~
    ~~~sql
    SELECT * FROM MEMBER M WHERE M.NAME = 'hello' ORDER BY AGE DESC
    ~~~
  - 이름으로 검색+정렬+페이징 하는 경우
  ~~~java
  public interface MemberRepository extends JpaRepository<Member, Long> {
      Page<Member> findByName(String name, Pageable pageable);
  }
  ~~~
  예를 들어 아래와 같이 작성하면 된다.
  ~~~java
  @RequestMapping("/search")
  Page<Member> search(@RequestParam("name") String name, Pageable pageable) {
      PageRequest pageRequest = PageRequest.of(1, 10); // 페이지 번호, 사이즈
      return repository.findByName(name, pageRequest);
  }
  ~~~
  
- @Query 어노테이션으로 쿼리 직접 정의
~~~java
public interface MemberRepository extends JpaRepository<Member, Long> {
  
    @Query("select m from Member m where m.name = ?1")
    Member findByName(String name, Pageable pageable);
}
~~~

### 14.4. 반환 타입 지정
반환 타입을 정할 수 있다.  
~~~java
List<Member> findByName(String name); // 컬렉션
Member findByEmail(String email); // 단건
~~~

### 14.5. Web 페이징과 정렬 기능
컨트롤러에서 페이징 처리 객체를 바로 받을 수 있다.  
`/members?page=0&size=20&sort=name,desc`
~~~java
@RequestMapping(vlaue = "/members", method = RequestMethod.GET)
String list(Pageable pageable, Model model) {...}
~~~

### 14.6. Web 도메인 클래스 컨버터 기능
컨트롤러에서 식별자로 도메인 클래스 찾음  
`/members/100`
~~~java
@RequestMapping("/members/{memberId}")
Member member(@PathVariable("memberId") Member member) {
    return member;
}
~~~

## 15. QueryDSL
- SQL과 JPQL을 코드로 작성할 수 있도록 도와주는 빌더 API
- JPA criteria에 비해서 편리하고 실용적이다.
- 오픈소스

### 15.1. SQL, JPQL의 문제점
- SQL, JPQL은 문자, Type-check 불가능
- 해당 로직 실행 전까지 작동여부 확인 불가(컴파일 시점에 알 수 없다.)

### 15.2. QueryDSL 장점
- 문자가 아닌 코드로 작성
- 컴파일 시점에 문법 오류 발견
- 코드 자동완성(IDE 도움)
- 단순하고 쉬움: 코드 모양이 JPQL과 거의 비슷
- 동적 쿼리

### 15.3. QueryDSL 사용
- JPQL
  - `select m from Member m where m.age > 18`
- QueryDSL
  ~~~java
  JPAFactoryQuery query = new JPAQueryFactory(em);
  QMember m = QMember.member;
  
  List<Member> list = query.selectFrom(m)
                           .where(m.age.gt(18))
                           .orderBy(m.name.desc())
                           .fetch();
  ~~~
  
### 15.4. QueryDSL - 조인
~~~java
JPAQueryFactory query = new JPAQueryFactory(em);
QMember m = QMember.member;
QTeam t = QTeam.team;

List<Member> list = query.selectFrom(m)
                         .join(m.team, t)
                         .where(t.name.eq("teamA"))
                         .fetch();
~~~

### 15.5. QueryDSL - 페이징 API
~~~java
JPAQueryFactory query = new JPAQueryFactory(em);
QMember m = QMember.member;

List<Member> list = query.selectFrom(m)
                         .orderBy(m.age.desc())
                         .offset(10)
                         .limit(20)
                         .fetch();
~~~

### 15.6. QueryDSL - 동적 쿼리
>QueryDSL을 쓰는 가장 큰 이유는 동적 쿼리 때문

~~~java
String name = "memebr";
int age = 9;

QMember m = QMember.member;

BooleanBuildere builder = new BooleanBuilder();
if (name != null) {
  builder.and(m.name.contains(name));
}
if (age != 0) {
  builder.and(m.age.gt(age);
}

List<Member> list = query.selectFrom(m)
                         .where(builder)
                         .fetch();
~~~

특히 DTO로 바로 조회해야 하는 경우 QueryDSL이 특히 유용하다.

### 15.7. QueryDSL - 이것은 자바다!
~~~java
return query.selectFrom(coupon)
            .where(
                coupon.type.eq(typeParam),
                coupon.status.eq("LIVE"), //서비스 필수 제약조건
                marketing.viewCount.lt(marketing.maxCount) //서비스 필수 제약조건
             )
             .fetch();
~~~

위와 같은 상황에서 다음과 같이 제약조건을 조립할 수 있다. (가독성과 재사용성 제고)

~~~java
return query.selectFrom(coupon)
            .where(
                coupon.type.eq(typeParam),
                isServiceable()
            )
            .fetch();

private BooleanExpression isServiceable() {
    return coupon.status.eq("LIST") //서비스 필수 제약조건
           .and(marketing.viewCount.lt(marketing.maxCount)); //서비스 필수 제약조건
}
~~~
