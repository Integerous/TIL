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

## 4. 데이터베이스 스키마 자동 생성하기
- DDL을 어플리케이션 실행 시점에 자동 생성
- 테이블 중심 -> 객체 중심
- 데이터베이스 방언(dialect)를 활용해서 데이터베이스에 맞는 적절한 DDL 생성
- 개발 환경에서만 사용! (운영 환경에서는 다듬어서 사용)

### 4.1 옵션
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

### 5.1 식별자 매핑 어노테이션
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

### 5.2 권장하는 식별자 전략
- 기본키의 제약 조건: null 아니고, 유일하며, 변하지 않는다.
- 하지만 변하지 않는 것은 없기 때문에(심지어 주민번호도) 대체키를 쓰는 것을 권장한다.
- 대체키는 데이터베이스의 Sequence, Auto Increment, 키 생성 테이블 등 비즈니스와 전혀 관계없는 것을 쓰는 것이 좋다.
- int타입은 10억~20억 사이에서 끝나기 때문에 Long을 쓰는 것을 권장한다.
- 권장: `Long타입 + 대체키 + 키 생성전략` 사용


## 6. 연관관계 매핑

### 6.1 객체를 테이블에 맞추어 모델링 할 경우

#### 6.1.1 참조 대신에 외래키를 그대로 사용
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

#### 6.1.2 외래키 식별자를 직접 다룸

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

#### 6.1.3 식별자로 다시 조회.
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

### 7.1 객체의 참조(team)와 테이블의 외래키(TEAM_ID)를 매핑 (=연관관계 매핑)
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
Member 객체만 조회하고 Team 객체는 실제 사용되는 시점에 조회한다. (`지연 로딩`)  
디폴트는 `(fetch = FetchType.EAGER)`로 같이 조회한다.

권장하는 것은 `LAZY`(지연 로딩)이다.  
현업에서는 전부 LAZY로 바르고, 꼭 필요한 곳에서는 쿼리를 날리는 시점에 원하는 것을 미리 최적화해서 가져오는 방법을 쓰게 한다.  
즉, 속단해서 최적화하지 말자는 것이다.
### 7.2 연관관계 저장

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

### 7.3 참조로 연관관계 조회 - 객체 그래프 탐색
~~~java
// 조회
Member findMember = em.find(Member.class, member.getId());

// 참조를 사용해서 연관관계 조회
Team findTeam = findMember.getTeam();
~~~

## 8. 양방향 매핑

### 8.1 Team 객체에서도 Member 갖도록
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

### 8.2 반대 방향으로 객체 그래프 탐색
~~~java
// 조회
Team findTeam = em.find(Team.class, team.getId());

// 역방향 조회
int memberSize = findTeam.getMembers().size();
~~~

### 8.3 객체와 테이블이 관계를 맺는 차이
- 객체 연관관계
  - 회원 -> 팀 연관관계 1개 (단방향)
  - 팀 -> 회원 연관관계 1개 (단방향)
- 테이블 연관관계 
  - 회원 <-> 팀 연관관계 1개 (양방향)

### 8.4 객체의 양방향 관계
- 객체의 양방향 관계는 사실 양방향 관계가 아니라 서로 다른 단방향 관계 2개이다.
- 객체를 양방향으로 참조하려면 단방향 연관관계를 2개 만들어야 한다.

### 8.5 테이블의 양방향 관계
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

### 8.6 객체의 양방향 관계에서의 문제점
예를 들어 Member 객체에서 Team 객체의 값을 변경시키거나,  
Team 객체에서 members에 member를 추가하는 등의 변화가 양쪽에서 일어난다면 어느쪽을 신뢰해야 하는가?

그래서 둘 중 하나로 외래키를 관리해야 한다.  
즉, 한 쪽을 `연관관계의 주인`으로 만들어주고 나머지 한쪽을 조회만 하도록 하는 것이다.

### 8.7 양방향 매핑 규칙
- 객체의 두 관계 중 하나를 연관관계의 주인으로 지정
- 연관관계의 주인만이 외래키를 관리 (등록, 수정)
- 주인이 아닌 쪽은 읽기만 가능
- 주인은 mappedBy 속성 사용 X
- 주인이 아니면 mappedBy 속성으로 주인 지정

### 8.8 누구를 주인으로?
- 외래키가 있는 곳을 주인으로 정해라
- 권장하는 것은 단방향으로 설계를 끝내고 개발하면서 양방향이 필요한 부분이 생기면 코드를 추가하는 방식을 권한다.

### 8.9 양방향 매핑시 가장 많이하는 실수
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

### 8.10 양방향 매핑의 장점
- 단방향 매핑만으로도 이미 연관관계 매핑은 완료
- 양방향 매핑은 반대 방향으로 조회(객체 그래프 탐색) 기능이 추가된 것 뿐
- JPQL에서 역방향으로 탐색할 일이 많음
- 단방향 매핑을 잘하고 양방향 매핑은 필요할 때 추가하면 됌. (테이블에 영향 없음)
