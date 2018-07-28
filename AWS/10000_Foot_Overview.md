# AWS - 10,000 Foot Overview
>[AWS Certified Solutions Architect - Associate 2018](https://www.udemy.com/aws-certified-solutions-architect-associate/) 강의를 듣고 내용 정리

## PART 1
- `Region` is a geographical area consists of two or more AZs.
- `Availability Zones(AZ)` is one or more discrete data centers.
- `Edge Locations` are endpoints for AWS which are used for caching content. Typically this consists of CloudFront, Amazon's Content Delivery Network(CDN).
  - 예를 들어 런던의 사용자가 사진이나 문서등의 정보를 업로드하고, 시드니의 사용자가 그것을 다운받을 때 런던의 서버에서 받는 것은 비효율적이다. 그러므로 시드니의 Edge Location이 가서 그 정보를 cache해와서 시드니의 사용자가 시드니의 Edge Location에서 사용할 수 있게 한다.

## PART 2
### Compute
- EC2 (Elastic Compute Cloud)
- EC2 Container Service
- Elastic Beanstalk
  - Automatically handles the deployment, from capacity provisioning, load balancing, auto-scaling to application health monitoring based on the code you upload to it
- Lambda
  - Run code without having to worry about provisioning any underlying resources (such as virtual machines, databases, etc.)
- Lightsail
- Batch

### Storage
- S3 (Simple Storage Service)
  - Offers durable, available storage for flat files
- EFS (Elastic File System)
- Glacier
  - Would be the best choice for long term data archival
- Snowball
- Storage Gateway

### Database
- RDS
- DynamoDB
- Elasticache
- RedShift
  - RedShift is used primarily for data warehousing

### Migration
- AWS Migration Hub
- Application Discovery Service
- Database Migration Service
- Server Migration Service
- Snowball

### Networking & Content Delivery
- VPC
- CloudFront
  - CloudFront is used a CDN to distribute content around the world
- Route53
  - Highly scaleable DNS service
- API Gateway
- Direct Connect

### Developer Tools
- CodeStar
- CodeCommit
- CodeBuild
- CodeDeploy
- CodePipeline
- X-Ray
- Cloud9

### Management Tools
- CloudWatch
- CloudFormation
  - Automated provisioning engine designed to deploy entire cloud environments via a JSON script
- CloudTrail
  - If you need to supply auditors with logs detailing the individual users that provision specifix resources on your AWS platform.
- Config
- OpsWork
  - If you need a configuration management service that enables your system administrators to configure and operate yor web applications using Chef.
- Service Catalog
- Systems Manager
- Trusted Advisor
- Managed Services

### Media Services
- Elastic Transcoder
  - If your digital media agency needs to convert their media files in to different formats to suit different devices.
- MediaConvert
- MediaLive
- MediaPackage
- MediaStore
- MediaTailor

### Machine Learning
- SageMaker
- Comprehend
- DeepLens
- Lex
- Machine Learning
- Polly
- Rekognition
- Amazon Translate
- Amazon Trascribe

### Analytics
- Athena
- EMR
- CloudSearch
- ElasticSearchService
- Kinesis
  - Kinesis is used for collating large amounts of data streamed from multiple sources
- Kinesis Video Streams
- QuickSight
- Data Pipeline
- Glue

### Security & Identity & Compliance
- IAM (Identity Access Management)
  - If you need to add users to your AWS account and set password rotation policies for these new users.
- Cognito
- GuardDuty
- Inspector
- Macie
- Certificate Manager
- CloudHSM
- Directory Service
- WAF
- Shield
- Artifact

### Mobile Services
- Mobile Hub
- Pinpoint
- AWS AppSync
- Device Farm
- Mobile Analytics

### AR & VR
- Sumerian

### Application Integration
- Step Functions
- Amazon MQ
- SNS
- SQS
- SWF

### Customer Engagement
- Connect
- Simple Email Service

### Business Productivity
- Alexa For Business
- Chime
- Work Docs
- WorkMail

### Desktop & App Streaming
- Workspaces
- AppStream 2.0

### Internet of Things
- iOT
- iOT Device Management
- Amazon FreeRTOS
- Greengrass

### Game Development
- GameLift
