# 리눅스 ssh 로그아웃해도 프로세스 유지하기
>챗봇을 만들어보면서 AWS EC2를 사용중인데도 ssh 접속이 끊기면 챗봇 서버도 내려지는 (초보다운)문제가 생겼다.  
`java -jar ChatBot~~~.jar &` 명령을 사용하여 백그라운드에서 동작하게끔 했는데도 로그아웃과 함께 프로세스가 종료되는 이유를 몰랐었다.  
알아보니 `&` 명령은 백그라운드에서 동작하게만 할 뿐, ssh의 로그아웃 신호(HUP 신호)를 막지 않는 것을 알게 되어 이참에 정리하였다.
## 1. nohup
  nohup은 HUP(hangup)신호를 무시하도록 만드는 명령어이며, HUP 신호는 터미널이 의존 프로세스들에게 로그아웃을 알리는 방식이다.  
  즉, **로그아웃 신호를 무시하도록하여 프로세스를 유지시킨다.**
  ~~~
  $ nohup 명령어 &
  ~~~  
  nohup으로 쉘파일을 실행하면 자동으로 `nohup.out` 파일이 생성되고 이 파일에는 Redirection을 사용하지 않은 출력문자열이 자동으로 저장된다.

  ### 주의사항 1
  위의 명령으로 background에 프로그램을 시작하고, 로그아웃 하더라도 프로세스가 정지신호를 보내는 것을 막아서 이 프로그램의 프로세스를 중단하지 않는다. 하지만 표준 입출력 파일(stdin, stdout, stderr)에 대해 입출력을 수신하면 터미널에 행(hang)이 걸리게 된다.

  ### 주의사항 2
  또 다른 문제는 ssh가 로그오프(행)을 거부하는 것인데, 백그라운드 작업들에 대한 데이터 손실을 거부하기 때문이다. 이 문제는 3개의 모든 입출력 스트림을 Redirection 처리함으로써 극복할 수 있다.
  ~~~
  $ nohup ./myprogram > foo.out 2> foo.err < /dev/null &
  ~~~

  ### 주의사항 3
  nohup으로 실행할 쉘스크립트 파일(*.sh)는 현재 퍼미션이 755이상이어야 한다. `chmod 755 deloy.sh`

## 2. foreground에서 프로세스를 실행중 ssh 로그아웃 할 때

  1. `ctrl + z` 명령으로 foreground에서 실행중인 프로세스를 중단.
  2. `bg` 명령으로 중단된 프로세스를 background에서 실행.
  3. `disown -h` 명령으로 작업의 소유권을 shell session에서 해제.
  4. `ssh 로그아웃`

  - `disown`은 bash 셀의 내부 명령어로 job table에서 지정된 프로세스를 삭제하여 SIGHUP 신호가 전송되지 않도록 한다.  
  - `disown [-ar] [-h] [jobspec]`
    - 옵션 없이 사용되면 jobspec은 실행 중인 작업 테이블에서 삭제된다.  
    - `-h` 옵션이 사용되면 jobspec은 테이블에서 삭제되지 않지만, 쉘이 SIGHUP을 수신해도 SIGHUP을 해당 작업에 보내지 않는다.
    - `-a``-r`옵션과 jobspec 모두 지정되지 않으면 현재의 작업이 사용된다.
    - jobspec이 지정되지 않으면, `-a` 옵션은 모든 작업을 테이블에서 삭제하라는 뜻이다.
    - jobspec이 지정되지 않으면, `-r` 옵션은 현재 실행중인 작업에 한정한다는 뜻이다.
    - jobspec이 유효한 작업이라면 리턴값은 0이다.

## *Reference
- https://ko.wikipedia.org/wiki/Nohup
- http://brownbears.tistory.com/174
