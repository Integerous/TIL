# EBS (Elastic Block Store)
>EC2 인스턴스에 장착하여 사용할 수 있는 가상 저장 장치.

### 1. 사용 이유
- 데이터에 빠르게 액세스하고 장기적으로 지속해야 하는 경우
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
- EBS 볼륨의 사이즈와 스토리지 타입은 실행중에도 변경할 수 있다.
- EBS 마그네틱 볼륨은 1GB ~ 1TB 크기로 생성 가능.
- EBS General Purpose(SSD)와 Provisioned IOPS(SSD)볼륨은 16TB 크기로 생성 가능
- EC2 인스턴스에 독립적으로 지속(인스턴스가 stop, start, reboot 되어도 EBS는 지속된다.)
- EC2 인스턴스 종료시 Root EBS는 디폴트로 삭제되지만 DeleteOnTermination flag로 수정할 수 있다.
- EC2 인스턴스 종료시 모든 Attached EBS 볼륨들은 지속된다.

### 4. EBS Encryption
- 암호화는 EC2 인스턴스를 호스팅하는 서버에서 수행되므로 EC2 인스턴스에서 EBS 스토리지로 전송되는 데이터가 암호화된다. 
- 암호화는 AES-256와 AMS Key Management Service (AWS KMS)의 Customer Master Keys(CMK) 사용
- 모든 EBS 볼륨 타입은 암호화 가능
- 암호화된 볼륨의 스냅샷과 암호화된 스냅샷에서 생성된 볼륨은 같은 볼륨 암호키를 사용해서 자동으로 암호화된다.
- 존재하는 암호화되지 않은 볼륨은 바로 암호화 할 수 없다. 대신 아래 3가지 경우에 가능하다.
  1. 볼륨으로부터 암호화되지 않은 스냅샷을 생성
  2. 암호화되지 않은 스냅션의 암호화된 복사본을 생성
  3. 암호화된 스냅샷으로부터 암호화된 볼륨을 생성
- 암호화된 스냅샷은 unencrypted 스냅샷의 암호화된 복사본을 만듦으로써 생성할 수 있다.
- 암호화된 볼륨으로 unencrypted 볼륨을 바로 만들수 없고, 마이그레이션이 필요하다.
- 암호화된 볼륨의 데이터를 공유하려면, 데이터를 unencrypted 볼륨으로 복사하고 공유하면 된다.

### 5. EBS Snapshot
- 볼륨은 EBS에 존재하고 Snapshot은 S3에 존재한다.
- EBS 스냅샷으로 `EBS 볼륨`과 `EC2 인스턴스를 생성할 수 있는 AMI`를 생성할 수 있다.
- AMI는 EBS-backed 인스턴스와 스냅샷 모두에서 만들 수 있다.
- EBS 볼륨 자체로는 다른 AZ로 이전할 수 없으므로 EBS 스냅샷을 활용해야 한다.
- EBS 스냅샷은 S3에 저장되어 백업된다.
- EBS 스냅샷은 S3에 저장되면서 압축되어서 EBS 스냅샷의 사이즈는 볼륨 사이즈보다 작다.
- To create a snapshot for EBS volumes that serve as root devices, yout should stop the instance before taking the snapshot
- However you can take a snap while the instance is running
- 암호화된 스냅샷은 공유할 수 없다.
- 스냅샷은 다른 AWS 계정으로 공유하거나 Public으로 만들 수 있다.

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

### 7. 더 자세한 학습
- [AWS EBS vs Instance Store](http://jayendrapatil.com/aws-ebs-vs-instance-store/)
- [AWS 공식 설명서 - EBS](https://docs.aws.amazon.com/ko_kr/AWSEC2/latest/UserGuide/AmazonEBS.html)

## *어려운 문제들
1. You are running a database on an EC2 instance, with the data stored on Elastic Block Store (EBS) for persistence At times throughout the day, you are seeing large variance in the response times of the database queries Looking into the instance with the isolate command you see a lot of wait time on the disk volume that the database’s data is stored on. What two ways can you improve the performance of the database’s storage while maintaining the current persistence of the data? Choose 2 answers  

    a. Move to an SSD backed instance  
    b. **Move the database to an EBS-Optimized Instance**  
    c. **Use Provisioned IOPs EBS**  
    d. Use the ephemeral storage on an m2.4xLarge Instance Instead  

2. A user has created numerous EBS volumes. What is the general limit for each AWS account for the maximum number of EBS volumes that can be created?  
  
    a. 10000  
    b. **5000**  
    c. 100  
    d. 1000  
3. If an Amazon EBS volume is the root device of an instance, can I detach it without stopping the instance?  
  
    a. Yes but only if Windows instance  
    b. **No**  
    c. Yes  
    d. Yes but only if a Linux instance  
    
4. Can we attach an EBS volume to more than one EC2 instance at the same time?  
  
    a. Yes  
    b. **No**  
    c. Only EC2-optimized EBS volumes.  
    d. Only in read mode.  
    
5. Do the Amazon EBS volumes persist independently from the running life of an Amazon EC2 instance?  
  
    a. **Only if instructed to when created**  
    b. Yes  
    c. No 

6. Can I delete a snapshot of the root device of an EBS volume used by a registered AMI?  
  
    a. Only via API  
    b. Only via Console  
    c. Yes  
    d. **No**  
    
7. Your company policies require encryption of sensitive data at rest. You are considering the possible options for protecting data while storing it at rest on an EBS data volume, attached to an EC2 instance. Which of these options would allow you to encrypt your data at rest? (Choose 3 answers)  
  
    a. **Implement third party volume encryption tools**  
    b. Do nothing as EBS volumes are encrypted by default  
    c. **Encrypt data inside your applications before storing it on EBS**  
    d. **Encrypt data using native data encryption drivers at the file system level**  
    e. Implement SSL/TLS for all services running on the server

8. A user is planning to schedule a backup for an EBS volume. The user wants security of the snapshot data. How can the user achieve data encryption with a snapshot?  
  
    a. **Use encrypted EBS volumes so that the snapshot will be encrypted by AWS**  
    b. While creating a snapshot select the snapshot with encryption  
    c. By default the snapshot is encrypted by AWS  
    d. Enable server side encryption for the snapshot using S3  

9. A user is planning to use EBS for his DB requirement. The user already has an EC2 instance running in the VPC private subnet. How can the user attach the EBS volume to a running instance?  
  
    a. The user must create EBS within the same VPC and then attach it to a running instance.  
    b. **The user can create EBS in the same zone as the subnet of instance and attach that EBS to instance. (Should be in the same AZ)**  
    c. It is not possible to attach an EBS to an instance running in VPC until the instance is stopped.  
    d. The user can specify the same subnet while creating EBS and then attach it to a running instance.  

10. 

## *Reference
- [아마존 웹 서비스를 다루는 기술 4장 - 5.가상 스토리지를 제공하는 EBS](http://pyrasis.com/book/TheArtOfAmazonWebServices/Chapter04/05)
- [AWS 공식설명서 - EBS](https://docs.aws.amazon.com/ko_kr/AWSEC2/latest/UserGuide/AmazonEBS.html)
- [AWS EBS Overview](http://jayendrapatil.com/aws-ec2-ebs-storage/)
- [AWS EBS Performance Tips](http://jayendrapatil.com/aws-ebs-performance/)
- [AWS Storage Options - EBS & Instance Store](http://jayendrapatil.com/aws-storage-options-ebs-instance-store/)
- [A Cloud Guru - Upgrading EBS Volume Types - Lab](https://www.udemy.com/aws-certified-solutions-architect-associate/learn/v4/t/lecture/2050670?start=525)
