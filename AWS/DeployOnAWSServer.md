# AWS EC2 서버에 자바 소스코드 배포 및 재배포 (Deploying Java project on AWS EC2)
>회사 고객센터용 카카오톡 챗봇을 만들어 AWS EC2 서버에 배포하기 위해 [박재성님 유튜브 강의](https://www.youtube.com/watch?v=--bUO7KNFJ4&t=1047s)를 들으며 실습한 내용

## 목록
~~~
1. AWS EC2 인스턴스 생성
2. 생성한 인스턴스(서버)에 접속
3. Java 설치
4. Symbolic Link 추가
5. 환경변수에 PATH 설정
6. Git 설치 및 프로젝트 Clone
7. 빌드 및 배포
8. 수정된 소스코드 반영
9. 쉘스크립트로 배포 자동화
~~~
## 1. AWS EC2 인스턴스 생성
## 2. 생성한 인스턴스(서버)에 접속
- 퍼블릭 DNS를 사용하여 인스턴스에 연결 `ssh -i "chatbot-kakao.pem" ubuntu@ec2~~~~~~~~.ap-northeast-2.compute.amazonaws.com`
- Private Key 파일이 공개적으로 표시되어 있을 경우 `chmod 400 chatbot-kakao.pem` 명령 입력
- `sudo locale-gen ko_KR.UTF-8` 혹은 `sudo apt-get install language-pack-ko` 명령으로 인코딩
## 3. Java 설치
- **apt-get으로 설치**
  - `apt-get install openjdk-8-jre-headless`
- **wget으로 설치할 경우**
  - `wget`명령어를 활용하여 특정 주소에 있는 자바 프로그램을 다운로드
  - 구글에 jdk download로 검색하여 오라클에 들어간 후, linux x64 다운로드 파일을 마우스 우클릭으로 `링크주소복사`
  - `wget 복사한 링크주소` 명령 입력
  - 하지만, 오라클 다운로드 페이지에서 약관동의한 것을 적용할 수 없었으므로 다운로드가 제대로 되지 않는다.
  - `rm jdk~~~~~` 명령으로 jdk 파일 삭제 및 `ls -al` 명령으로 삭제 확인
  - `wget --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u181-b13/96a7b8442fe848ef90c96a2fad6ed6d1/jdk-8u181-linux-x64.tar.gz`
- 설치완료 후 `ls -al`로 설치 확인
- `tar -xvf jdk ~~~ tar.gz` 명령으로 압축 해제
- ~/jdk~~/bin 경로로 이동하여 `./java -version`으로 자바 버전 확인
- 아무 위치에서나 자바 프로그램에 접근이 안되므로 `심볼링 링크 설정`과 `환경 변수 설정`을 해야 한다.
## 4. Symbolic Link 추가
- `ln -s jdk~~/ java(별칭)`
- `cd java` 명령으로 경로에 접근 가능
## 5. 환경변수에 PATH 설정
- `cd ~`
- `vi .bash_profile`
- `PATH=$PATH:~/java/bin` 작성하고 esc - :wq
- `source .bash_profile` 명령으로 환경변수 파일 리로드
- java -version
## 6. Git 설치 및 프로젝트 Clone
- `git --version` 으로 git 설치 확인
- 설치 안되어있을 경우, `sudo apt-get update` 후에 `sudo apt-get install git 명령으로 git 설치
- `git clone 저장소 주소` 명령으로 clone
## 7. 빌드 및 배포
- `./mvnw clean package` = 기존의 결과물을 날려버리고(clean) 배포할 수 있는 코드로 다시 빌드하는(package) 명령 입력
  - 만약 각 배포 버전을 남기고 싶다면 clean 없이 `./mvnw package`
- 빌드의 결과물은 target 디렉토리에 위치하므로 `cd target` 으로 이동하여 `nohup java -jar ChatBotTest-0.0.1-SNAPSHOT.jar &` 명령으로 서버에 띄우기
  - 서버에 띄우는 데에 시간이 너무 오래 걸리면 `java -Djava.security.egd=file:/dev/./urandom -jar ChatBotTest-0.0.1-SNAPSHOT.jar`
  - 터미널을 분리하지 않으면 서버에 올리고나서 `Ctrl+c`로 명령어를 작성하게되면 서버가 같이 종료되므로 터미널을 분리해서 서버에 올려야한다. 이를 위해 java -jar 명령의 마지막에 `&` 를 넣어서 터미널을 분리해야한다.
  - [`nohup` 명령을 하는 이유](https://github.com/Integerous/TIL/blob/master/Linux/Maintaining_Process.md) 읽어보기.
- `nohup java -jar ChatBotTest-0.0.1-SNAPSHOT.jar &` 명령으로 실행 후 `Ctrl+c`로 터미널에서 빠져나와서
- `curl http://localhost:8080` 명령으로 화면이 뜨는지 확인
- **배포한 코드 확인**
  - 8080포트는 보통 방화벽에 막혀있기 때문에 EC2의 보안그룹에서 사용자TCP 8080 위치무관을 선택하고 추가한다.
  - 인스턴스의 퍼블릭ip:8080 혹은 퍼블릭DNS:8080 을 주소로 접속하면 접속이 된다.
## 8. 수정된 소스코드 반영
- **수정된 코드 push & pull**
  - `Ctrl+d`로 ssh에서 벗어나서 로컬에서 새로 작업한 내용을 github에 푸시
  - 다시 ssh로 우분투에 접속하여 프로젝트가 있는 디렉토리로 이동하여 `git pull`
- **프로세스 종료 및 서버 재시작**
  - 새로운 추가된 내용을 적용하기 위해 서버를 재시작해야 한다. 즉,
  - 현재 떠있는 서버를 멈춰야 하므로 현재 실행되고 있는 프로그램을 `ps -ef | grep java` 명령으로 확인하거나,
  - `jps` 명령으로 실행되고있는 자바 프로그램과 PID만 확인하여 
  - 배포했던 jar파일의 프로세스아이디(PID, 첫번째 숫자)를 `kill -9 프로세스아이디`로 프로세스를 종료하거나
  - `pkill -f '프로그램명'`으로 프로세스 종료 가능
  - `jps`로 프로세스 종료 확인
- **빌드 및 배포**
  - `./mvnw clean package` 명령으로 수정된 소스코드를 빌드하고,
  - target 디렉토리로 이동하여 `nohup java -jar ChatBotTest-0.0.1-SNAPSHOT.jar &` 명령으로 배포


## 9. 쉘 스크립트로 배포 자동화하기
>http://jojoldu.tistory.com/263?category=635883 참고하여 Gradle이 아닌 Maven 버전으로 작성함

### 9.1. EC2 인스턴스의 `/home/ubuntu` 디렉토리에 `deploy.sh' 파일 생성
  ~~~sh
  nano deploy.sh
  ~~~

### 9.2. `deploy.sh` 파일에 배포 자동화를 위한 쉘스크립트 작성
  ~~~sh
  #!/bin/bash

  REPOSITORY=/home/ubuntu/Chat-Bot-Kakao

  echo "> 프로젝트 저장소로 이동!"
  cd $REPOSITORY

  echo "> Git Pull !!!"
  git pull

  echo "> 실행중인 프로세스 확인!"
  CURRENT_PID=$(pgrep -f ChatBotTest)

  echo "> 실행중인 프로세스 아이디 : $CURRENT_PID"
  if [ -z $CURRENT_PID ]; then
          echo "> 현재 실행중인 어플리케이션이 없으므로 종료하지 않습니다!"
  else
          echo "> 현재 실행중인 어플리케이션 종료! Kill -9 $CURRENT_PID"
          kill -9 $CURRENT_PID
          sleep 5
  fi

  echo "> 프로젝트 Build 시작! (이전 배포 버전 삭제 안함)"
  ./mvnw package

  echo "> 새 배포 버전의 이름은 ??"
  JAR_NAME=$(ls $REPOSITORY/target |grep 'ChatBotTest' |grep -v 'original' | tail -n 1)
  echo "> JAR Name: $JAR_NAME"

  echo "> 새 어플리케이션 배포 !!!"
  nohup java -jar $REPOSITORY/target/$JAR_NAME &
  ~~~
