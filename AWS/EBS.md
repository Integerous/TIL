# EBS (Elastic Block Store)
>EC2 인스턴스에 장착하여 사용할 수 있는 가상 저장 장치.

### 1. 사용 이유
- EC2 인스턴스에서 제공하는 기본 용량보다 더 사용해야 할 때
- 운영체제를 중단시키지 않고 용량을 자유롭게 늘리고 싶을 때
- 영구적인 데이터 보관이 필요할 때
- [RAID](https://docs.aws.amazon.com/ko_kr/AWSEC2/latest/UserGuide/raid-config.html) 등 고급 기능이 필요할 때

### 2. 사용 예시
- 빅데이터 분석 엔진(하둡/HDFS 에코시스템, Amazon EMR 클러스터 등)
- 관계형 및 NoSQL 데이터베이스
- 스트림 및 로그 처리 어플리케이션(Kafka, Splunk 등)
- 데이터 웨어하우징 어플리케이션(Vertica, Teradata 등)

### 3. 특징
- EC2에 설치된 OS에서 일반적인 HDD/SDD로 인식
- EBS 볼륨은 AZ의 여러 서버에 자동으로 복제되어 구성요소 장애로부터 보호
- Provisioning한 부분에 대해서만 비용 지불
- 원하는 크기와 성능(IOPS)으로 설정 가능
- EBS 마그네틱 볼륨은 1GB ~ 1TB 크기로 생성 가능.
- EBS General Purpose(SSD)와 Provisioned IOPS(SSD)볼륨은 16TB 크기로 생성 가능
- EC2 인스턴스에 독립적으로 지속(인스턴스가 stop, start, reboot 되어도 EBS는 지속된다.)
- EC2 인스턴스 종료시 Root EBS는 디폴트로 삭제되지만 DeleteOnTermination flag로 수정할 수 있다.
- EC2 인스턴스 종료시 모든 Attached EBS 볼륨들은 지속된다.

### 4. EBS Encryption
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

### 5. EBS Snapshot
- EBS 스냅샷으로 `EBS 볼륨`과 `EC2 인스턴스를 생성할 수 있는 AMI`를 생성할 수 있다.
- EBS 볼륨 자체로는 다른 AZ로 이전할 수 없으므로 EBS 스냅샷을 활용해야 한다.
- EBS 스냅샷은 S3에 저장되어 백업된다.
- EBS 스냅샷은 S3에 저장되면서 압축되어서 EBS 스냅샷의 사이즈는 볼륨 사이즈보다 작다.

### 6. EBS Performance
- 새로 만들어진 EBS 볼륨은 사용 가능한 순간에 최대 성능을 내고, initialization이 필요없다.(pre-warming)
- RAID 0
  - 입출력 성능이 안정성보다 더 중요할 때 사용
  - (장점) 입출력이 stripe 내의 볼륨들에 분산된다.
  - (장점) 볼륨을 하나 추가하면 그 즉시 throughput의 추가와 같다.
  - (단점) 가장 performance가 낮은 볼륨을 기준으로 stripe의 performance가 제한된다.
  - (단점) 한 볼륨의 손실은 데이터 손실을 의미한다.
- RAID 1
  - 안정성이 입출력 성능보다 중요할 때 사용
  - (장점) 데이터 지속성 측면에서 안전하다.
  - (단점) 데이터가 여러 볼륨에 동시에 작성되기 때문에 쓰기 능력을 향상시키려면 non-RAID 설정에 비해 EC2와 EBS 사이에 더 큰 대역폭이 필요하다.


## *어려운 문제들
1. You are running a database on an EC2 instance, with the data stored on Elastic Block Store (EBS) for persistence At times throughout the day, you are seeing large variance in the response times of the database queries Looking into the instance with the isolate command you see a lot of wait time on the disk volume that the database’s data is stored on. What two ways can you improve the performance of the database’s storage while maintaining the current persistence of the data? Choose 2 answers  
  
    a. Move to an SSD backed instance  
    b. **Move the database to an EBS-Optimized Instance**  
    c. **Use Provisioned IOPs EBS**  
    d. Use the ephemeral storage on an m2.4xLarge Instance Instead  

2

## *Reference
- [아마존 웹 서비스를 다루는 기술 4장 - 5.가상 스토리지를 제공하는 EBS](http://pyrasis.com/book/TheArtOfAmazonWebServices/Chapter04/05)
- [AWS 공식설명서 - EBS](https://docs.aws.amazon.com/ko_kr/AWSEC2/latest/UserGuide/AmazonEBS.html)
- [AWS EBS Overview](http://jayendrapatil.com/aws-ec2-ebs-storage/)
- [AWS EBS Performance Tips](http://jayendrapatil.com/aws-ebs-performance/)
- [AWS Storage Options - EBS & Instance Store](http://jayendrapatil.com/aws-storage-options-ebs-instance-store/)
