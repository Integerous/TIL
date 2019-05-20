# <전설의 SQL 학습서> 내용 정리

<div align=center>
<img src="https://github.com/Integerous/TIL/blob/master/ETC/images/oracleExpert.png?raw=true" width="300" height="300">
</div>


>팀장님이 강력 추천하신 [***전설의 SQL 학습서***](http://www.kyobobook.co.kr/product/detailViewKor.laf?mallGb=KOR&ejkGb=KOR&barcode=9788972806172)로 SQL을 공부하며 내용 정리  
>팀장님은 이 책으로 쿼리ㅄ에서 쿼리 달인이 되셨다고 강력 추천하셨다.  

>책이 매우 올드하고 이미 오래 전에 절판되었지만, [저자의 블로그](http://blog.daum.net/why_i_am/45)에 가보니 실제로 평가도 좋고,  
>책을 구하려는 사람들이 아직도 존재한다.  
>하지만 책 표지를 보면 학습 의욕이 떨어지기 마련이라 책 표지는 가급적 쳐다보지 않는게 좋을 것 같다.


## 1장. 자료의 조회

### 1. SELECT 문의 구조

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


### 2. SELECT 에서의 산술 연산

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

### 3. NULL의 사용
- DML을 이용할 때는 항상 NULL을 고려해야 한다.  
- NUMBER 형 자료를 NULL과 연산(+, -, *, /)하면 결과는 항상 NULL
- 숫자형 컬럼이나 변수에 NULL이 들어갈 우려가 있다면 0이나 1등 다른 숫자로 치환 후 연산에 사용한다.
- 문자형 컬럼이나 변수에 NULL이 들어갈 우려가 있다면 ' '(스페이스)나 다른 특정 문자 값으로 치환하여 조건 절에 이용한다.
- NULL인지 비교
  - WHERE a IS NULL
  - WHERE a IS NOT NULL
  - 절대 a = NULL 또는 a <> NULL 로 사용하면 안된다.
  
~~~sql
SELECT name
FROM temp
WHERE hobby IS NOT NULL;
~~~

### 4. Alias

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

#### 테이블 Alias를 반드시 사용해야 하는 경우
- Self Join의 경우  

Self Join에서 자기 자신의 테이블과 Join이 일어나는 경우 모든 컬럼이 중복된다.  
이 때, 컬럼 앞에 테이블 명을 명시해도 Self Join에서는 구분되지 않는다.  
때문에 이러한 경우 테이블 Alias를 반드시 사용해야 한다.

#### 컬럼 Alias를 반드시 사용해야 하는 경우는
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



## *Reference
- [저자의 블로그에 공개된 책 내용](http://blog.daum.net/why_i_am/45)
