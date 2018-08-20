# AWS SAA 자격증 준비
>AWS Certified Solution Architect Associate 자격증 준비 방법과 과정을 기록




~~~
400제, 700제는 시중에 $100정도에 구입할 수 있는 AWS SA Dump 중 문항 수가 약 400문제인것과 700문제인 것이 있습니다.  
둘 다 오답이 많아서 아래 두 사이트를 참고해서 정답을 따로 정리해야 합니다.

AWS SA 예상문제 참조사이트:
1. https://www.briefmenow.org/amazon/can-i-move-a-reserved-instance-from-one-region-to-another/
2. http://jayendrapatil.com/aws-solution-architect-associate-exam-learning-path/

참고로, 덤프로 시험 준비하시는 분들께 팁을 드리면, 지문이 긴 문제는 Pro 자격증에서 나올만한 문제들이므로 SA 시험공부에서는 건너뛰시고,  
덤프 문제들을 외우지 말고 정답을 명확하게 이해하는게 좋습니다. 저는 400제 중 짧은 문제만 한 절반 정도 보고 시험 보았습니다.  
그리고, 한글시험은 영어시험에 비해 덤프 적중률이 아주 낮습니다.
~~~


## 학습 자료
- [FAQs](https://aws.amazon.com/ko/faqs/)의 중요한 토픽들을 읽으면 좋다. 요점을 빠르게 복습할 수 있다.
- [Jayendra's Blog](http://jayendrapatil.com/)



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
