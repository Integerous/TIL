# SQL 기록소
>작성할 때 생소했던 SQL 기록!

## 1. SQL문 자가복사하여 더미 데이터 만들기
~~~sql
insert into tbl_board (title, content, writer) (select title, content, writer from tbl_board);
~~~
  - 현재 tbl_board 테이블에서 꺼낸 데이터를 다시 tbl_board 테이블에 넣는 방식
  - 여러 번 실행하면 현재 데이터의 배수만큼 들어가므로 100만건의 데이터도 금방 만들 수 있다.

## 2. 페이징을 위해서 데이터 일부만 출력하기
~~~sql
select * from tbl_board whrer bno > 0 order by bno desc limit 0, 10'
~~~
  - 10개씩 데이터를 출력하는 경우
    - 1 page = limit 0, 10
    - 2 page = limit 10, 10
  - 20개씩 데이터를 출력하는 경우
    - 1 page = limit 0, 20
    - 2 page = limit 20, 20
    
## 3. 테이블에 외래키(foreign key) 추가하기
~~~sql
alter table tbl_reply add constraint fk_board foreign key (bno) references tbl_board (bno);
~~~
>형식 : alter table (테이블명) add constraint (외래키 별칭) foreign key (외래키 줄 컬럼명) references (부모 테이블명) (참조할 테이블명) (옵션)


### 3-1. 외래키 동작 옵션
>옵션은 참조당하는 부모 테이블의 컬럼이 삭제되었을 때 외래키가 어떻게 동작할 것인지에 대한 옵션
- on delete **restrict** - 참조하는 부모테이블의 컬럼이 갱신/삭제 시도 -> 갱신/삭제 불가
- on delete **cascade** - 부모테이블 컬럼 삭제 -> 자식테이블 컬럼 모두 삭제
- on delete **set null** - 부모테이블 컬럼 삭제 -> 자식테이블 컬럼이 모두 NULL 된다.
- on delete **no action** - 부모테이블 컬럼 삭제 -> 무시
- on delete **set default** - 부모테이블 컬럼 삭제 -> 지정된 값으로 대체
