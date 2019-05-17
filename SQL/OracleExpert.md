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



## *Reference
- [저자의 블로그에 공개된 책 내용](http://blog.daum.net/why_i_am/45)
