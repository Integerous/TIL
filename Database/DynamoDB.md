# DynamoDB 정리
>실무에서 DynamoDB 사용을 준비하며 학습한 내용을 기록한다.  
>사용하게 된 이후에도 계속 내용을 추가할 예정

</br>

## 1. DynamoDB란?
- AWS에서 호스팅하는 NoSQL 데이터베이스

### 1-1. DynamoDB를 사용하기 좋은 케이스
- 많은 양의 데이터와 엄격한 Strict latency requirements가 존재하는 어플리케이션
- AWS Lambda를 사용하는 Serverless 어플리케이션
- 단순하고, 정해진 접근 패턴을 가진 데이터를 다룰 때

</br>

## 2. 중요한 개념들
>중요한 개념들을 간단하게 이해하고 시작한다. 추후 더 자세히 이해하고 정리할 예정.

### 2-1. Table
- 테이블은 데이터 레코드의 모음이다.
- RDB의 테이블, MongoDB의 Collection과 비슷한 개념이다.

### 2-2. Item
- Item은 테이블에 있는 싱글 데이터다.
- 각 Item은 테이블의 Primary Key에 의해 식별된다.
- 예를 들어, Users 테이블의 각 Item은 User일 것이다.
- RDB의 row, MongoDB의 document와 비슷한 개념이다.

### 2-3. Attributes
- Attributes는 하나의 Item에 포함된 데이터들이다. (Attribute는 Item에 포함된 데이터 중 하나)
- RDB의 column, MongoDB의 field와 비슷한 개념이다.
- Primary Key로 사용되는 Attribute 이외에는 반드시 필요한 것이 아니다.

### 2-4. Primary Key
- 각 Item은 테이블의 Primary Key(이하 PK)에 의해 식별된다.
- PK는 테이블의 생성 시점에 정의되어야 한다.
- 새로운 Item을 추가할 때 PK는 반드시 제공되어야 한다.
- 2가지 종류의 PK가 있다.
    - **Simple Primary Key**
        - Partition Key Only
        - SPK를 사용하는 것은 Memcached와 같은 key-value 저장소와 비슷하고, SQL 테이블에서 PK로 rows에 접근하는 것과 비슷하다.
        - 예시) Username을 PK로 사용하는 Users 테이블
    - **Composite Primary Key**
        - Partition Key + Sort Key
        - Sort Key는 같은 partition에서 items를 정렬하는데 사용된다.
        - 예시) e-commerce 서비스에서 고객의 주문(orders)를 기록하는 Orders 테이블. 이때, Partition Key는 CustomerId, Sort Key는 OrderId.
        - Composite Key가 구성되더라도, 테이블의 각 Item은 Primary Key에 의해 식별된다.
        - 같은 Partition Key를 가진 복수의 Item들은 서로 다른 Sort Key를 가진다.

### 2-5. 그림으로 이해
![](https://d2908q01vomqb2.cloudfront.net/887309d048beef83ad3eabf2a79a64a389ab1c9f/2018/09/10/dynamodb-partition-key-1.gif)

### 2-5. Secondary Indexes
- PK로만 쿼리하는 것에는 한계가 있다. DynamoDB는 추가적인 접근 패턴을 가능하게하는 Secondary Indexes라는 개념이 있다.
- **Local Secondary Index**
    - LSI는 같은 Partition Key와 다른 Sort Key를 사용한다.
    - 예시) 만약 고객의 주문금액 내림차순으로 고객의 주문들(Orders)에 빠르게 접근하고자 한다면, CustomerId를 Partition Key로 사용하고, 주문금액을 Sort Key로 사용하는 Local Secondary Index를 추가하면 된다.
- **Global Secondary Index**
    - 다양한 종류의 쿼리를 수행할 필요가 있는 경우, 하나 이상의 GSI를 생성하고 쿼리할 수 있다.
    - GSI를 사용해서 테이블에 완전히 다른 Primary Key를 정의할 수 있다.
    - 모든 GSI는 Partition Key를 반드시 가져야 하고, Sort Key는 옵션이다.
    - GSI는 기본 테이블로부터 Attributes 모음을 포함한다. 하지만 기본 테이블과는 다른 Primary Key에 의해 구성된다.
    - GSI를 구성해도 기본 테이블의 Primary Key였던 Attributes는 구성한 GSI에도 그대로 프로젝션 된다.
    - 예시) OrderId를 Partition Key로하는 GSI를 구성하면, 주문을 체결한 CustomerId를 몰라도 특정 Order를 가지고 올 수 있다.


</br>

## Reference
- [AWS re:Invent 2018: Amazon DynamoDB Deep Dive: Advanced Design Patterns for DynamoDB (DAT401)](https://www.youtube.com/watch?v=HaEPXoXVf2k)
- [효과적인 NoSQL (Elasticahe / DynamoDB) 디자인 및 활용 방안](https://www.youtube.com/watch?v=8rEsuvdL17s&t=2384s)
- [Choosing the Right DynamoDB Partition Key](https://aws.amazon.com/ko/blogs/database/choosing-the-right-dynamodb-partition-key/)
- [DynamoDB Guide](https://www.dynamodbguide.com/what-is-dynamo-db)
- [Global Secondary Indexes](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/GSI.html)
