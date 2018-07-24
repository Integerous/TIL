# AWS 서버에 자바 소스코드 배포 및 재배포
>[박재성님 유튜브 강의](https://www.youtube.com/watch?v=--bUO7KNFJ4&t=1047s)를 들으며 실습한 내용
>>회사 고객센터용 카카오톡 챗봇을 만들며 진행한 실습


## 절차
### 1. AWS EC2 인스턴스 생성
### 2. 생성한 인스턴스(서버)에 접속
- 퍼블릭 DNS를 사용하여 인스턴스에 연결 `ssh -i "chatbot-kakao.pem" ubuntu@ec2-52-78-235-134.ap-northeast-2.compute.amazonaws.com`
- Private Key 파일이 공개적으로 표시되어 있을 경우 `chmod 400 chatbot-kakao.pem` 명령 입력
- `sudo locale-gen ko_KR.UTF-8` 혹은 `sudo apt-get install language-pack-ko` 명령으로 인코딩
### 3. 자바 설치
- apt-get으로 설치
  - `apt-get install openjdk-8-jre-headless`
- wget으로 설치할 경우
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
 ### 4. 심볼릭 링크 추가
- `ln -s jdk~~/ java(별칭)`
- `cd java` 명령으로 경로에 접근 가능
 ### 5. 환경변수에 path 설정
- `cd ~`
- `vi .bash_profile`
- `PATH=$PATH:~/java/bin` 작성하고 esc - :wq
- `source .bash_profile` 명령으로 환경변수 파일 리로드
- java -version
### 6. Git 설치 및 clone
- `git --version` 으로 git 설치 확인
- 설치 안되어있을 경우, `sudo apt-get update` 후에 `sudo apt-get install git 명령으로 git 설치
- `git clone 저장소 주소` 명령으로 clone
### 7. compile 및 배포
- `./mvnw clean package` = 기존의 결과물을 날려버리고(clean) 배포할 수 있는 코드로 다시 빌드하는(package) 명령 입력
- 빌드의 결과물은 target 디렉토리에 위치하므로 `cd target` 으로 이동하여 `java -jar ChatBotTest-0.0.1-SNAPSHOT.jar` 명령으로 서버에 띄우기
    - 서버에 띄우는 데에 시간이 너무 오래 걸리면 `java -Djava.security.egd=file:/dev/./urandom -jar ChatBotTest-0.0.1-SNAPSHOT.jar`
- 터미널을 분리하여 
