# 실무에서 만난 쉘 스크립트
>실무에서 쓰는 Shell Script를 분석하며 공부해보고자 한다.

## 1. AWS 인스턴스 목록을 검색하고 접근하는 Shell Script
>최초 작성일: 2019년 10월 2일

~~~shell
#!/bin/bash

SERVER_LIST="/home/회사/script/server_list.txt"
FILE_NAME="/home/회사/script/is_list.log"
GREP_STYLE="egrep -v '#|localhost' $SERVER_LIST | grep '[a-z]'"  ### (1)
USER="회사유저"
HOST="zhost.com"

if [ -z "$1" ]; then  ### (2)
  eval $GREP_STYLE > $FILE_NAME  ### (3)
  nl $FILE_NAME
else
  if [[ "$1" == *[a-z,.]* ]]; then  ### (4)
    eval $GREP_STYLE > $FILE_NAME
    nl $FILE_NAME
  else
    IP=`awk NR==$1 $FILE_NAME |awk '{print $1}'`  ### (5)
    echo $IP
    ssh $USER@$IP.$HOST
  fi
fi
~~~

### 1.1 기능
- `is [검색어]`로 해당 서비스 관련 인스턴스 목록을 보여주고, `is [번호]` 명령으로 해당 인스턴스에 접속한다.

### 1.2 동작 과정
0. 파일 3개(`is.sh`, `server_list.txt`, `is_list.log`)를 사용한다.
1. 미리 `server_list.txt`에 인스턴스(서버) 목록을 작성해둔다.
2. `is [검색어]`로 검색하면, 해당 인스턴스(서버) 목록을 `server_list.txt`에서 읽어서
3. `is_list.log` 파일에 덮어쓰고, 인스턴스(서버) 목록을 보여준다.
4. `is [번호]`로 인스턴스(서버)를 선택하면, 해당 인스턴스에 접속한다.

### 1.3 내가 모르는 문법 (1) ~ (5)

~~~shell
(1) egrep -v '#|localhost' $SERVER_LIST | grep '[a-z]'

## SERVER_LIST 파일에서 주석처리(#)되었거나, localhost가 붙은 문자열을 제외한 문자열을 출력한다.
~~~

### `egrep`
- `grep -E` 혹은 `grep --extended-regexp` 과 완전 같다.(**확장 정규표현식을 사용하기 위해 사용**한다.)
- 추가 정규표현식 메타문자를 지원한다. (즉, 아래 문자들은 grep에서와 다르게 escape 처리(앞에 '\'붙이기가 필요없다.)
  - `+` : + 앞의 정규표현식이 1회 이상 나타남
  - `?` : ? 앞의 정규표현식이 0 또는 1회 나타남
  - `|` : 문자열간의 OR 연산자
  - `()` : 정규표현식을 둘러쌈
### `-v`
- `grep [옵션] [정규표현식/문자열] [대상 파일명]` 이런 형태로 사용하며,
- `-v` 옵션은 **[정규표현식/문자열]이 포함되지 않은 라인을 출력**

~~~shell
(2) if [ -z "$1" ]; then

## 명령어의 첫번째 인자($1)가 공백인지 검사
~~~

### `-z "$1"`
- 명령어의 첫번째 인자($1)가 공백인지 검사
- `-n "$1"` 옵션은 인자($1)가 none empty인지 검사

~~~shell
(3) eval $GREP_STYLE > $FILE_NAME

## 변수 $GREP_STYLE의 값을 변수 $FILE_NAME의 값(파일명)인 파일에 저장 (덮어씌움)
~~~

### `eval`
- 변수 치환이 재귀적으로 일어나도록 한다.
- 아래의 예시 처럼 eval은 명령행 해석을 다시 수행한다. (In simple terms: makes an input line to be parsed twice.)
  ~~~shell
  $ set a=$b    #변수 a에 $b를 할당
  $ set b=foo   #변수 b에 foo를 할당
  $ echo $a     #a를 출력하면
  $b            #b 반환

  $ eval echo $a  #eval을 사용하면
  foo             #foo 반환
  ~~~

### `>`
- 표준 출력
- 명령 > 파일
- 명령의 결과를 파일에 저장 (덮어씌움)
- `>>` : 명령의 결과를 파일에 저장 (기존 데이터에 추가)
- `<` : 표준 입력. 파일의 데이터를 명령에 입력
- `명령>&파일` : 결과와 에러를 파일에 출력
- `명령>!파일` : 파일의 존재유무와 상관없이 생성하고, 결과를 파일로 출력
- `명령>&!파일` : 위와 같으나 에러까지 출력
- `명령A|명령B` : 명령A의 출력을 명령B의 입력으로 사용하여 실행

~~~shell
(4) if [[ "$1" == *[a-z,.]* ]]
~~~
### `[[ ]]`
- `[]`에 비해 안정적이고, 다양한 연산자(`<`, `&&`, `||`, `()`)를 사용할 수 있다.
- 더 자세한 내용은 [여기](https://stackoverflow.com/questions/669452/is-double-square-brackets-preferable-over-single-square-brackets-in-ba) 참고

### `$GREP_STYLE > $FILE_NAME`
### `nl $FILE_NAME`
### `awk NR==$1 $FILE_NAME |awk '{print $1}'`

### Reference
- https://unix.stackexchange.com/questions/17949/what-is-the-difference-between-grep-egrep-and-fgrep
- https://unix.stackexchange.com/questions/23111/what-is-the-eval-command-in-bash
- https://stackoverflow.com/questions/669452/is-double-square-brackets-preferable-over-single-square-brackets-in-ba
- https://unabated.tistory.com/entry/grep-egrep-fgrep-%EC%A0%95%EA%B7%9C%EC%8B%9D
- https://www.lesstif.com/pages/viewpage.action?pageId=26083916
- http://throughkim.kr/2016/12/22/linux-5/
