# EBS (Elastic Block Store)
>EC2 인스턴스에 장착하여 사용할 수 있는 가상 저장 장치.

### 특징
- EC2에 설치된 OS에서 일반적인 HDD/SDD로 인식
- EBS 볼륨은 AZ의 여러 서버에 자동으로 복제되어 구성요소 장애로부터 보호
- Provisioning한 부분에 대해서만 비용 지불
- 원하는 크기와 성능(IOPS)으로 설정 가능
- EBS 마그네틱 볼륨은 1GB ~ 1TB 크기로 생성 가능.
- EBS General Purpose(SSD)와 Provisioned IOPS(SSD)볼륨은 16TB 크기로 생성 가능
- EC2 인스턴스에 독립적으로 지속(인스턴스가 stop, start, reboot 되어도 EBS는 지속된다.)
- EC2 인스턴스 종료시 Root EBS는 디폴트로 삭제되지만 DeleteOnTermination flag로 수정할 수 있다.
- EC2 인스턴스 종료시 모든 Attached EBS 볼륨들은 지속된다.

### EBS 암호화
- 암호화는 EC2 인스턴스를 호스팅하는 서버에서 발생하여 Data-in-transit 암호화를 EC2인스턴스로 부터 EBS로 제공한다.
- 암호화는 AES-256와 AMS Key Management Service (AWS KMS)의 Customer Master Keys(CMK) 사용
- 모든 EBS 볼륨 타입은 암호화 가능
- 암호화된 볼륨의 스냅샷과 암호화된 스냅샷에서 생성된 볼륨은 같은 볼륨 암호키를 사용해서 자동으로 암호화된다.
- 존재하는 암호화되지 않은 볼륨은 바로 암호화 할 수 없다. 대신 아래 3가지 경우에 가능하다.
  1. 볼륨으로부터 암호화되지 않은 스냅샷을 생성
  2. 암호화되지 않은 스냅션의 암호화된 복사본을 생성
  3. 암호화된 스냅샷으로부터 암호화된 볼륨을 생성
- 암호화된 스냅샷은 암호화 안된 스냅샷의 암호화된 복사본을 만듦으로써 생성할 수 있다.
- 암호화된 볼륨으로 암호화 안된 볼륨을 바로 만들수 없고, 마이그레이션이 필요하다.

### EBS 스냅샷
- EBS 스냅샷으로 `EBS 볼륨`과 `EC2 인스턴스를 생성할 수 있는 AMI`를 생성할 수 있다.
- EBS 볼륨 자체로는 다른 AZ로 이전할 수 없으므로 EBS 스냅샷을 활용해야 한다.
- EBS 스냅샷은 S3에 저장되어 백업된다.
- EBS 스냅샷은 S3에 저장되면서 압축되어서 EBS 스냅샷의 사이즈는 볼륨 사이즈보다 작다.

### 사용 이유
- EC2 인스턴스에서 제공하는 기본 용량보다 더 사용해야 할 때
- 운영체제를 중단시키지 않고 용량을 자유롭게 늘리고 싶을 때
- 영구적인 데이터 보관이 필요할 때
- [RAID](https://docs.aws.amazon.com/ko_kr/AWSEC2/latest/UserGuide/raid-config.html) 등 고급 기능이 필요할 때

### 사용 예시
- 빅데이터 분석 엔진(하둡/HDFS 에코시스템, Amazon EMR 클러스터 등)
- 관계형 및 NoSQL 데이터베이스
- 스트림 및 로그 처리 어플리케이션(Kafka, Splunk 등)
- 데이터 웨어하우징 어플리케이션(Vertica, Teradata 등)
