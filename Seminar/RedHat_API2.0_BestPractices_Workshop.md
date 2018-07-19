# RedHat API 2.0 Best Practices Workshop
>2018년 7월 19일 @삼성역 Oakwood Premier

## Session 1. 엔터프라이즈 IT 도전과제
>Steven Willmot
>>Red Hat Senior Director and Head of API Infrastructure

### Changing Business Driver
- Business driver가 바뀌고 있고, 지금은 Data의 시대이다.  
- Demographic - Brand - Utility - Data

### Infrastructure Trends
- Cloud
- Container
  - 혁신적이지만 아직 비싸고 느리다.
  - 하지만 컨테이너는 우리의 미래에 함께할 것이다.

### Inside The Enterprise
- Enterprises face many new challenges in it to stay ahead
  - Integrate SaaS Apps
  - Customer and Partner Access
  - Enable Customer Facing Apps via Many Channels
  - Extend Legacy Apps
  - IoT Devices and Architectures
  - Data Access for Business Users
- **All Require APIs to do well**

### The 3 Pillars of Aile Integration
- Distributed Integration
  - Lightweight
  - Pattern based
  - Event-oriented
  - Community-sourced
- Container
  - Cloud-native solutions
  - Lean artifacts, Individually deployable
  - Container-based scaling & high availability
- APIs
  - Well defined 
  
>RedHat의 CEO James Whitehurst는 "계획은 무의미하다." 라고 말할 정도로 기술은 빠르게 발전하고 있다.


# Session 2 - 성공적인 API 우선 전략 수립 방안
>Steven Willmot
>>Red Hat Senior Director and Head of API Infrastructure

### What do APIs Really Do?  
- Provide Stable Reusable Interfaces

### Most Common API Use Cases
- Mobile & IoT Support
- Customer Integration
- Partner Ecosystem
- Content & Transaction Channel
- APIs as a Business
- Internal Agility

### Campbell's Soup : APIs lead to a breakthrough new customer channel.
### University California at Berkeley Internal and external APIs.

### Best Practices
1. Focus on True Value
    - Jeff Bezos moment?
      - Change the way you work, not the infrastructure you have
    - Outcomes
      - Expected & Desirable = Design
      - Unexpected & Desirable = Innovation
      - Expected & Undesirable = Prohibited
      - Unexpected & Undesirable = Fire Drill
    - Example: Maps Credit Union
      - API를 개발했지만 거의 사용되지 않았다.
    
2. Enable rather than Own
    - Bimodal IT
    
3.  Good Metrics
    - Number of apps supported
    - Number of complete use cases
    - Number of users
    - Dollar value of business
    - Speed of application development
    - Speed of API deployment
    - Time To First Hello World (TTFHW)

# Hands-On 세션 1: 컨테이너화된 워크로드를 작동하는 방법
>김현수 이사
>>한국레드햇 시니어 솔루션 아키텍트

실습은 [워크샵 깃헙 저장소](https://github.com/hguerrero/3scale-api-workshop)의 안내에 따라 진행

### API 개발 라이프사이클
1. Strategy
2. Design
    - 어떤식으로 인풋과 아웃풋을 받을 것인지
    - APICURIO 오픈소스 활용
3. Mock
    - Microcks로 제작
4. Test
5. Implement
6. Deploy
    - Ansible
7. Secure (제어)
    - 접근제어 등
8. Manage
9. Discover
10. Develop
11. Consume
12. Monitor
13. Monetize(과금)

### 세션1 과정
1. API Design - Create an OpenAPI Specification-based Contract
2. API Deployment - Deploying APIs to OpenShift
3. API Management - Take Control of APIs


# Hands-On 세션 2: API를 배포하고 관리하는 방법
>김현수 이사
>>한국레드햇 시니어 솔루션 아키텍트

### 세션2 과정
1. API Security
2. API Developers
3. API Consumption
