# AWS SAA 자격증 준비
>AWS Certified Solution Architect Associate 자격증 준비 방법과 과정을 기록







## 필수 학습 자료
- [A Cloud Guru CSAA 강의](https://www.udemy.com/aws-certified-solutions-architect-associate/) - 시험대비의 정석. 필수로 여겨진다.
- [덤프 문제집](https://www.passleader.com/amazon.html) - 덤프문제집 공부법은 밑에 설명. - [문제 답 참조사이트](https://www.briefmenow.org/amazon/which-of-the-following-is-the-best-method-to-quickly-and-temporarily-deny-access-from-the-specified-ip-address-block/)
- [Jayendra's Blog](http://jayendrapatil.com/) - 학습방법, cheat sheet 등 질 좋은 자료가 많다.
- [AWS FAQs](https://aws.amazon.com/ko/faqs/)의 중요한 토픽들을 읽으면 좋다. 요점을 빠르게 복습할 수 있다.
- [비공식 AWS SAA 수험가이드](https://github.com/serithemage/AWSCertifiedSolutionsArchitectUnofficialStudyGuide) - 슈퍼트랙 강사가 만들었다는 비공식 수험가이드이다. 도움을 받았다는 사람들이 많다.
- [아마존 웹 서비스를 다루는 기술](http://pyrasis.com/private/2014/09/30/publish-the-art-of-amazon-web-services-book) - 저자가 책 원고를 공개했다. 영문으로 공부하기 전에 여기서 해당 파트를 먼저 공부하면 수월.

~~~
400제, 700제는 시중에 $100정도에 구입할 수 있는 AWS SA Dump 중 문항 수가 약 400문제인것과 700문제인 것이 있습니다.  
둘 다 오답이 많아서 아래 두 사이트를 참고해서 정답을 따로 정리해야 합니다.

AWS SA 예상문제 참조사이트:
1. https://www.briefmenow.org/amazon/can-i-move-a-reserved-instance-from-one-region-to-another/
2. http://jayendrapatil.com/aws-solution-architect-associate-exam-learning-path/

참고로, 덤프로 시험 준비하시는 분들께 팁을 드리면, 지문이 긴 문제는 Pro 자격증에서 나올만한 문제들이므로 SA 시험공부에서는 건너뛰시고,  
덤프 문제들을 외우지 말고 정답을 명확하게 이해하는게 좋습니다. 저는 400제 중 짧은 문제만 한 절반 정도 보고 시험 보았습니다.  
그리고, 한글시험은 영어시험에 비해 덤프 적중률이 아주 낮습니다.

출처: http://svrlove.blogspot.com/2017/07/aws-sa.html
~~~

## 추가 학습 자료
- [AWS 공식 한국 블로그](https://aws.amazon.com/ko/blogs/korea/) - 매일 아침 신문 읽듯이 방문
- [AWS This is my architecture](https://aws.amazon.com/ko/this-is-my-architecture/) - 실제 AWS 사용 기업들의 아키텍쳐를 8분 이내의 동영상에서 도표를 통해 설명
- [AWS 용어 알아보기 by DNS전문가님](https://brunch.co.kr/@topasvga/76) - AWS 용어와 기존 인프라 용어를 비교하여 이해하기 쉽다.
- [AWS 용어 알아보기2 by DNS전문가님](https://brunch.co.kr/@topasvga/1)
- 온라인 테스트/문제
    - [예상문제 참조사이트](https://www.briefmenow.org/amazon/which-of-the-following-is-the-best-method-to-quickly-and-temporarily-deny-access-from-the-specified-ip-address-block/)
    - [BrainCert](https://www.braincert.com/)
    - [Aio Testking](http://www.aiotestking.com/amazon/)
    - [Simplilearn 무료 모의시험](https://www.simplilearn.com/aws-solutions-architect-exam-free-practice-test)
- [시험시간 추가 배정 방법](https://blog.naver.com/supertrackedu/221119091699) - 시험 예약시 EXAM DURATION: 110 minutes로 나오면 정상적으로 30분 연장된 것.
## 합격 후기 (유용한 순서로 정렬)
- [A Cloud Guru 수강생들 시험 후기](https://acloud.guru/forums/aws-certified-solutions-architect-associate/discussion/-L7uITWGWEI21g2BXL1_/Exam%20Feedback%20-%20SAA%20February%202018%20Edition) - 시험 내용에 대한 후기가 많아서 유용하다.
- [DNS 전문가님 SA 수험 가이드](https://brunch.co.kr/@topasvga/233) - 블로그 자체에 AWS 관련 도움되는 글들이 많다.
- [Kyupok's AWS Study 블로그](https://kyupokaws.wordpress.com/2017/01/20/aws-certified-solution-architect-associatesaa-%ED%95%A9%EA%B2%A9-%ED%9B%84%EA%B8%B0/) - 시험에 나오는 AWS 공식 문서 모음이 있다.
- [곽규복님 SA 자격증 합격 후기 영상](https://www.youtube.com/watch?v=LFP-hObdBhs) - 블로그 내용과 비슷함.
- [김종선님 SA 자격증 합격 후기 블로그](https://soulsearcher.github.io/blog/2018/04/10/preparing_for_certification/)
- [카오스랩 SA 자격증 합격 후기 블로그](http://svrlove.blogspot.com/2017/07/aws-sa.html)

## 기타 링크
- [합격 후 자격증 로고와 인증서 받는 곳](https://www.certmetrics.com/amazon/candidate/cert_summary.aspx)

## 반드시 학습해야할 주제
### Networking
1. Be sure to create VPC from scratch. This is mandatory.
    - Create VPC and understand whats an CIDR.
    - Create public and private subnets, configure proper routes, security groups, NACLs.
    - Create Bastion for communication with instances
    - Create NAT Gateway or Instances for instances in private subnets to interact with internet
    - Create two tier architecture with application in public and database in private subnets
    - Create three tier architecture with web servers in public, application and database servers in private.
    - Make sure to understand how the communication happens between Internet, Public subnets, Private subnets, NAT, Bastion etc.
2. Understand VPC endpoints and what services it can help interact
3. Understand difference between NAT Gateway and NAT Instance
4. Understand how NAT high availability can be achieved
5. Understand CloudFront as CDN and the static and dynamic caching it provides, what can be its origin (it can point to on-premises sources)
6. Understand Route 53 for routing, health checks and various routing policies it provides and their use cases mainly for high availability
7. Be sure to cover ELB in deep. AWS has introduced ALB and NLB and there are lot of questions on ALB
8. Understand ALB features with its ability for content based and URL based routing with support for dynamic port mapping with ECS

### Storage
1. Understand various storage options S3, EBS, Instance store, EFS, Glacier and what are the use cases and anti patterns for each
2. Would recommend referring Storage Options whitepaper, although a bit dated 90% still holds right
3. Understand various EBS volume types and their use cases in terms of IOPS and throughput. SSD for IOPS and HDD for throughput
4. Understand Burst performance and I/O credits to handle occasional peaks
5. Understand S3 features like different storage classes with lifecycle policies, static website hosting, versioning, Pre-Signed URLs for both upload and download, CORS
6. Understand Glacier as an archival storage with various retrieval patterns
7. Glacier Expedited retrieval now allows object retrieval within mins
8. Understand Storage gateway and its different types

### Compute
1. Understand EC2 as a whole
2. Understand Auto Scaling and ELB, how they work together to provide High Available and Scalable solution
3. Understand EC2 various purchase types – Reserved, On-demand and Spot and their use cases
4. Understand Reserved purchase types with the introduction of Scheduled and Convertible types
5. Understand Lambda and serverless architecture, its features and use cases. How do you benefit from Lambda?
6. Understand ECS with its ability to deploy containers and micro services architecture
7. Know Elastic Beanstalk at a high level, what it provides and its ability to get an application running quickly

### Databases
1. Understand relational and NoSQLs data storage options which include RDS, DynamoDB, Aurora and their use cases
2. Aurora has been added to the exam and most of time the questions refer to Aurora given its abilities for multiple read replicas and replication of data across AZs
3. Understand S3 is not a storage option for database
4. Understand RDS features – Read Replicas for scalability, Multi-AZ for High Availability, Automated Backups, underlying volume types
5. Understand DynamoDB with its low latency performance, DAX
6. Understand DynamoDB provisioned throughput for Read/Writes
7. Know ElastiCache use cases, mainly for caching performance

### Analytics

1. Not much in deep, but understand what the services are and what they can do
2. Understand Redshift as a business intelligence tool
3. Know Kinesis for real time data capture and analytics
4. Atleast know what AWS Glue does, so you can eliminate the answer

### Security

1. Understand IAM as a whole
2. Focus on IAM role and its use case especially with EC2 instance
3. Understand IAM identity providers and federation and use cases
4. Understand MFA and How would implement two factor authentication for your application
5. Understand encryption services
    - KMS for key management and envelope encryption
    - Focus on S3 with SSE, SSE-C, SSE-KMS
    - Know SQS now provides SSE support 
6. Refer Disaster Recovery whitepaper, be sure you know the different recovery types with impact on RTO/RPO.

### Management Tools
1. Understand CloudWatch monitoring to provide operational transparency
2. Know which EC2 metrics it can track. Remember, it cannot track memory and disk space/swap utilization
3. Understand CloudWatch is extendable with custom metrics
4. Understand CloudTrail for Audit
5. Have a basic understanding of CloudFormation, OpsWorks

### Integration Tools
1. Understand SQS as message queuing service and SNS as pub/sub notification service
2. Understand SQS features like visibility, long poll vs short poll
3. Focus on SQS as a decoupling service
4. AWS has released SQS FIFO, make sure you know the differences between standard and FIFO




# * Reference
- [SA 자격증 합격 후기 영상 - 곽규복](https://www.youtube.com/watch?v=LFP-hObdBhs)
- http://svrlove.blogspot.com/2017/07/aws-sa.html
- [AWS Certified Solutions Architect - Associate Feb 2018 Exam Learning Path](http://jayendrapatil.com/aws-solutions-architect-associate-feb-2018-exam-learning-path/)
