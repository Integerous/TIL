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
- Lambda
- Lightsail
- Batch

### Storage
- S3 (Simple Storage Service)
- EFS (Elastic File System)
- Glacier
- Snowball
- Storage Gateway

### Database
- RDS
- DynamoDB
- Elasticache
- Red Shift

### Migration
- AWS Migration Hub
- Application Discovery Service
- Database Migration Service
- Server Migration Service
- Snowball

### Networking & Content Delivery
- VPC
- CloudFront
- Route53
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
