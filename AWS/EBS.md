# EBS (Elastic Block Store)
>EC2 인스턴스에 장착하여 사용할 수 있는 가상 저장 장치.

### 특징
- EC2에 설치된 OS에서 일반적인 HDD/SDD로 인식
- EBS 볼륨은 AZ의 여러 서버에 자동으로 복제되어 구성요소 장애로부터 보호
- Provisioning한 부분에 대해서만 비용 지불
- 원하는 크기와 성능(IOPS)으로 설정 가능

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
