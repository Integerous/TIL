# 실무에서 만난 쉘 스크립트
>실무에서 쓰는 Shell Script를 분석하며 공부해보고자 한다.

## 1. AWS 인스턴스 목록을 검색하고 접근하는 Shell Script (2019.10.02)

~~~shell
#!/bin/bash

SERVER_LIST="/home/회사/script/server_list.txt"
FILE_NAME="/home/회사/script/is_list.log"
GREP_STYLE="egrep -v '#|localhost' $SERVER_LIST | grep '[a-z]'"
USER="회사유저"
HOST="zhost.com"

if [ -z "$1" ]; then
  eval $GREP_STYLE > $FILE_NAME
  nl $FILE_NAME
else
  if [[ "$1" == *[a-z,.]* ]]; then
    eval $GREP_STYLE > $FILE_NAME
    nl $FILE_NAME
  else
    IP=`awk NR==$1 $FILE_NAME |awk '{print $1}'`
    echo $IP
    ssh $USER@$IP.$HOST
  fi
fi
~~~

### 기능
- `is [검색어]`로 해당 서비스 관련 인스턴스 목록을 보여주고, `is [번호]` 명령으로 해당 인스턴스에 접속한다.

### 동작 과정
0. 파일 3개(`is.sh`, `server_list.txt`, `is_list.log`)를 사용한다.
1. 미리 `server_list.txt`에 인스턴스(서버) 목록을 작성해둔다.
2. `is [검색어]`로 검색하면, 해당 인스턴스(서버) 목록을 `server_list.txt`에서 읽어서
3. `is_list.log` 파일에 덮어쓰고, 인스턴스(서버) 목록을 보여준다.
4. `is [번호]`로 인스턴스(서버)를 선택하면, 해당 인스턴스에 접속한다.

### 내가 모르는 문법
- `egrep -v`
- `#|localhost`
- `if [ -z "$1" ]`
- `eval`
- `$GREP_STYLE > $FILE_NAME`
- `nl $FILE_NAME`
- `awk NR==$1 $FILE_NAME |awk '{print $1}'`

### Reference
- 
