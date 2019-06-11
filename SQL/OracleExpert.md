# <전설의 SQL 학습서> 내용 정리

<div align=center>
<img src="https://github.com/Integerous/TIL/blob/master/ETC/images/oracleExpert.png?raw=true" width="300" height="300">
</div>


>팀장님이 강력 추천하신 [***전설의 SQL 학습서***](http://www.kyobobook.co.kr/product/detailViewKor.laf?mallGb=KOR&ejkGb=KOR&barcode=9788972806172)로 SQL을 공부하며 내용 정리  
>팀장님은 이 책으로 쿼리ㅄ에서 쿼리 달인이 되셨다고 강력 추천하셨다.  

>책이 매우 올드하고 이미 오래 전에 절판되었지만, [저자의 블로그](http://blog.daum.net/why_i_am/45)에 가보니 실제로 평가도 좋고,  
>책을 구하려는 사람들이 아직도 존재한다.  
>하지만 책 표지를 보면 학습 의욕이 떨어지기 마련이라 책 표지는 가급적 쳐다보지 않는게 좋을 것 같다.


# 1장. 자료의 조회

## 1-1. SELECT 문의 구조

SELECT를 위하여 반드시 기술되어야 하는 절: `SELECT`, `FROM`  
즉, 어떤 테이블(FROM 절)에서 어떤 컬럼(SELECT 절)을 읽어올 것인가는 필수 사항.

그 뒤에는,  
1. 자료에 조건을 부여하여 제한을 주는 `WHERE` 절
2. GROUP 함수를 사용하여 자료를 그룹화할 때 필요한 `GROUP BY` 절
3. GROUP 지은 결과에 조건을 부여하여 제한을 주는 `HAVING` 절 (`GROUP BY` 필요)
4. 마지막으로 도출된 결과를 정렬할 수 있는 ORDER BY 절


>DML vs DDL vs DCL

DML - Data 삽입, 삭제, 수정, 조회  
DDL - Data 정의(CREATE, DROP, ALTER)  
DCL - 사용자에게 부여된 권한을 정의


## 1-2. SELECT 에서의 산술 연산

~~~sql
SELECT name,
       salary/18,
       salary*2/18
FROM   temp;
~~~

~~~sql
SELECT name,
       10000 + salary/18,
       20000 + salary*2/18
FROM   temp;
~~~

## 1-3. NULL의 사용
- DML을 이용할 때는 항상 NULL을 고려해야 한다.  
- NUMBER 형 자료를 NULL과 연산(+, -, *, /)하면 결과는 항상 NULL
- 숫자형 컬럼이나 변수에 NULL이 들어갈 우려가 있다면 0이나 1등 다른 숫자로 치환 후 연산에 사용한다.
- 문자형 컬럼이나 변수에 NULL이 들어갈 우려가 있다면 ' '(스페이스)나 다른 특정 문자 값으로 치환하여 조건 절에 이용한다.
- NULL인지 비교
  - `WHERE a IS NULL`
  - `WHERE a IS NOT NULL`
  - 절대 `a = NULL` 또는 `a <> NULL` 로 사용하면 안된다.
  
~~~sql
SELECT name
FROM temp
WHERE hobby IS NOT NULL;
~~~

## 1-4. Alias

Alias는 이름 뒤에 한 칸 이상 띄고 Alias명을 주는 방법과 as 뒤에 기술하는 방법이 있다.

~~~sql
SELECT id i,
       name as n
FROM temp;
~~~

Alias는 편하기 때문에 사용하는 경우가 대부분이지만 반드시 사용해야 하는 경우도 있다.  

~~~sql
SELECT id,
       code,
       name
FROM temp,
     t_dept
WHERE t_dept.code = temp.code

결과: ERROR (열의 정의가 애매합니다.)
~~~

위의 예시처럼 동일한 컬럼(code)이 복수의 테이블(temp, t_dept)에 존재할 때,  
어떤 테이블의 칼럼인지 명시하지 않으면 에러 발생.

~~~sql
SELECT id,
       temp.code,
       name
FROM temp,
     t_dept
WHERE t_dept.code = temp.code
~~~

그러므로 위와 같이 컬럼 앞에 테이블 명을 명시해야 한다.  
하지만 테이블명이 길면 컬럼마다 테이블 명을 명시하기가 쉽지 않다.  
그래서 아래와 같이 테이블에 Alias를 짧게 주고 사용하면 훨씬 편리하다.

~~~sql
SELECT id,
       a.code,
       b.name
FROM temp a,
     t_dept b
WHERE a.code = b.code;
~~~

### 테이블 Alias를 반드시 사용해야 하는 경우
- Self Join의 경우  

Self Join에서 자기 자신의 테이블과 Join이 일어나는 경우 모든 컬럼이 중복된다.  
이 때, 컬럼 앞에 테이블 명을 명시해도 Self Join에서는 구분되지 않는다.  
때문에 이러한 경우 테이블 Alias를 반드시 사용해야 한다.

### 컬럼 Alias를 반드시 사용해야 하는 경우는
- ROWNUM을 사용하거나
- TREE 구조의 전개시 LEVEL값 등을 사용하는 경우

이러한 값들이 Inline View 안에서 사용된 후, 다시 이들을 FROM 절로 사용하는 Query에서  
Rownum이나 Level을 사용하고자 한다면 반드시 컬럼 Alias를 사용해서 SQL을 작성해야 한다.

~~~sql
SELECT a.id,
       a.name,
       b.boss_id,
       c.name
FROM   temp a,
       t_dept b,
       temp c
WHERE  b.code = a.code
AND    c.id = b.boss_id;
~~~

위의 예시에서 사원번호(id)와 이름(name)을 가져오기 위한 temp와,  
팀장번호(boss_id)와 일치하는 이름(name)을 가져오기 위한 temp는 같은 테이블이지만  
Self Join으로 중복 사용되었으므로 위와 같이 Alias를 사용해야 한다.


## 1-5. Concatenation

Concatenation은 함수의 일종이다.  
복수의 문자열을 연결하여 하나의 문자열을 만들 때 사용한다.  
`CONCAT` 함수를 사용하거나 `합성연산자(||)`를 사용

### 문자와 숫자의 자동 변환

temp의 자료 중 NUMBER형인 id와 VARCHAR2형인 name을 합성연산자로 묶으면,  

~~~sql
SELECT id || name
FROM temp;
~~~

결과는 이상 없이 마치 문자열을 묶은 것 처럼 나온다.  
흔히 숫자형을 문자형으로 바꿀 때는 TO_CHAR라는 함수를 사용하지만 위의 경우처럼 자동변환도 가능하다.


## 1-6. 질의 결과 제한

## WHERE
조건절의 시작을 의미하는 것이 WHERE.  
조건절은 질의문이 반환해야하는 결과값을 제한하는 역할.

조건이 여러 개 연결 될 때는 `AND`나 `OR`로 묶어서 나열 가능.  
2개 이상의 테이블이 어떤 컬럼을 기준으로 join이 걸린다면 그 join 조건도 WHERE 절에 기술.  

조건절에 기술된 컬럼이 INDEX가 존재하면 해당 INDEX가 실행계획에 포함된다.  
단, 컬럼을 함수로 가공하거나 `NOT` 연산자가 쓰이는 경우에는 제외된다.  
Primary Key로 지정된 컬럼은 자동으로 INDEX가 생성된다.

## Optimizer
DML(SELECT, DELETE, UPDATE, INSERT)을 수행할 때 OPTIMISER가 관여한다.  
OPTIMISER는 수행하고자 하는 DML을 가장 효율적으로 처리할 수 있는 최적화 경로를 찾아준다.

OPTIMISER는 최적화 경로를 찾기 위해 다양한 요인을 고려하여 `실행 계획`을 작성한다.
- 어떤 테이블을 먼저 읽을 것인가?
- 테이블을 읽을 때 인덱스를 이용할 것인가?
- 어떤 인덱스를 이용할 것인가?
- Join 이 필요한 경우 어떤 방식으로 Join 할 것인지?

그리고 이 `실행 계획`에 의해 DML이 수행되고,  
OPTIMISER가 선택한 최적화 경로인 수행 경로를 PLAN을 이용해 확인할 수 있다.

## PLAN
DML이 어떤 경로를 통해 DB에 ACCESS 했는지 보여주는 일종의 순서도이다.  

~~~sql
EXPLAIN PLAN SET STATEMENT_ID = '임의지정' FOR
SELECT id, name
FROM temp
WHERE id > 0;
~~~

## ORDER BY
RECORD가 테이블에 저장될 때는 순서가 정해져 있지 않다.  
자료가 DB의 BLOCK 상에 쌓이는 원리는 테이블 CREATE시 지정하는 `PCTFREE`와 `PCTUSED`에 의해 결정된다.  

이렇게 순서없이 저장된 자료를 특정한 순서에 맞게 보기위해 ORDER BY를 사용한다.  
RECORD가 정렬되는 순서를 결정하는 것은 ORDER BY 외에도  
QUERY시 내부적으로 SORT가 일어나는 경우와, INDEX가 사용되는 경우 등이 있다.

~~~sql
SELECT id, name
FROM   temp
ORDER BY id DESC;
~~~

ORDER BY 절은 SELECT 문장의 마지막에 기술한다.  
기본 값은 ASCENDING(오름차순)이며, 컬럼 뒤에 `DESC`를 붙이면 DESCENDING(내림차순)으로 정렬할 수 있다.

~~~sql
SELECT level, id, name
FROM   temp
ORDER BY 1, 2 DESC;

//결과
level로 ASC 정렬 후, id로 DESC 정렬
~~~

위와 같이 ORDER BY에 컬럼명 대신 **컬럼 순서**를 기술해도 된다. (위의 결과를 눈여겨보자)

## DB BLOCK의 구조

BLOCK은 오라클 데이터베이스의 **물리적인 저장 단위**로서 **입출력시의 최소 단위**이다.  
BLOCK의 구조는 위에서부터 차례대로 다음과 같은 부분으로 나뉜다.

1. HEADER
2. TABLE DIRECTORY
3. ROW DIRECTORY
4. FREE SPACE
5. ROW DATA

#### 1. HEADER
- 블록에 대한 일반적인 정보 포함 ex) 블록의 주소

#### 2. TABLE DIRECTORY
- 블록 안에 존재하는 ROW를 소유하는 테이블에 대한 정보 포함

#### 3. ROW DIRECTORY
- 각 ROW의 주소 등 블록 안에 존재하는 ROW들에 대한 실제 정보 포함

#### 4. FREE SPACE
- Null 컬럼의 값이 NOT NULL로 UPDATE 될 때 추가적인 공간을 요구하게 되는데,  
이때 처럼 ROW의 변경이나 새로운 ROW의 삽입 시 필요로 하게 되는 추가적인 공간을 위해 확보된 영역

#### 5. ROW DATA
- 테이블이나 인덱스의 데이터를 물리적으로 저장하는 영역


## PCTFREE와 PCTUSED

#### PCTFREE
- 이미 블록에 쓰여진 ROW의 UPDATE나 INSERT를 위해 예약되는 공간  
예를 들어 `PCTFREE 20`과 같이 값을 잡아주면,  
블록의 공간 중 20%를 이미 블록에 쓰여진 ROW의 UPDATE나 INSERT를 위해 사용하지 않고 남기겠다는 의미.

#### PCTUSED
- PCTUSED에서 지정한 영역만큼만 FREE SPACE가 남게 되면 오라클은 더 이상 해당 블록에 새로운 ROW를 삽입하지 않는다.  
사용 공간이 ROW의 삭제 등으로 PCTUSED에서 지정한 값 아래로 떨어지면,  
그 때 다시 해당 블록에 새로운 ROW가 삽입될 수 있다.

## 연산자

### LIKE 연산자
~~~sql
WHERE ~~~ LIKE 'A%'; // A로 시작되는 ROW 검색
WHERE ~~~ LIKE '%A%'; // A가 들어가는 ROW 검색
WHERE ~~~ LIKE '_A____'; // 6자리 중 2번째 자리에 A가 들어가는 ROW 검색
~~~

### BETWEEN 연산자
~~~sql
WHERE name BETWEEN '가' AND '나' // name이 'ㄱ'으로 시작되는 ROW 검색
~~~

### IN 연산자
- OR 조건으로 연결될 조건을 한 번에 기술하는 역할

~~~sql
WHERE name IN ('홍길동', '김길동');
~~~

위의 IN 연산자로 기술된 문장은 OPTIMIZER에 의해 실행계획 작성 전에 아래와 같이 변경된다.

~~~sql
WHERE name = '홍길동'
OR    name = '김길동';
~~~




## *Reference
- [저자의 블로그에 공개된 책 내용](http://blog.daum.net/why_i_am/45)
