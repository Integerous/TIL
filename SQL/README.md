# SQL 기록소
>작성할 때 생소했던 SQL 기록!

### 1. SQL문 자가복사하여 더미 데이터 만들기
~~~sql
insert into tbl_board (title, content, writer) (select title, content, writer from tbl_board);
~~~
  - 현재 tbl_board 테이블에서 꺼낸 데이터를 다시 tbl_board 테이블에 넣는 방식
  - 여러 번 실행하면 현재 데이터의 배수만큼 들어가므로 100만건의 데이터도 금방 만들 수 있다.

### 2. 페이징을 위해서 데이터 일부만 출력하기
~~~sql
select * from tbl_board whrer bno > 0 order by bno desc limit 0, 10'
~~~
  - 10개씩 데이터를 출력하는 경우
    - 1 page = limit 0, 10
    - 2 page = limit 10, 10
  - 20개씩 데이터를 출력하는 경우
    - 1 page = limit 0, 20
    - 2 page = limit 20, 20
