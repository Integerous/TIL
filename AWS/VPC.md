# AWS - VPC
>[AWS Certified Solutions Architect - Associate 2018](https://www.udemy.com/aws-certified-solutions-architect-associate/) 강의를 듣고 내용 정리  

![VPC Diagram](https://github.com/Integerous/TIL/blob/master/AWS/img/VPCdiagram.png?raw=true)
## What is a VPC ?
VPC는 클라우드 내의 **가상 데이터 센터**.

## What can you do with a VPC ?
- 내가 선택한 서브넷에 인스턴스를 실행할 수 있다.
- 각각의 서브넷에 커스텀 IP주소 범위를 할당할 수 있다.
- 서브넷 간의 Route tables를 설정할 수 있다.
  - Route Tables : 어떤 서브넷이 다른 서브넷들과 speak 할 수 있는지 정해놓은 것.
- 인터넷 게이트웨이를 생성하고 우리의 VPC에 붙일 수 있다.
- AWS 자원들을 더욱 안전하게 관리할 수 있다.
  - 서브넷 사용, 특정 IP주소 막기, 네트워크 ACLS 등을 통해서.
- 인스턴스 보안그룹
- 서브넷 네트워크 접근 관리 리스트(ACLS)

## Default VPC vs Custom VPC

### Default VPC
- 사용하기 쉽고, 인스턴스를 즉시 배포할 수 있게한다.
- Default VPC의 모든 서브넷들은 인터넷에 연결할 수 있다.
- 프라이빗 서브넷이 없다.
- 각각의 EC2 인스턴스는 퍼블릭 IP와 프라이빗IP를 모두 가지고 있다

### Custom VPC
- 프라이빗 서브넷이 있다.
- 각각의 EC2 인스턴스는 프라이빗 IP만 가지고 있다.

## VPC Peering
- 프라이빗 IP주소들을 이용해서 VPC 끼리 다이렉트 네트워크 루트로 연결한다.
- 인스턴스들은 같은 프라이빗 네트워크에 있는 것처럼 여겨진다.
- 다른 AWS 계정의 VPC와도 연결할 수 있고, 같은 계정의 VPC와도 연결할 수 있다.
- VPC A와 B, A와 C가 연결되어있어도 B와 C가 연결되어있지 않으면 연결되지 않는다. (we don't have transitive peering in VPC)  
![](https://github.com/Integerous/TIL/blob/master/AWS/img/VPCtransitivePeering.png?raw=true)

## Summary
- VPC는 AWS 내의 논리적 데이터센터라 생각하면 된다.
- 구성
  - Internet Gateways 또는 Virtual Private Gateways
  - Route Tables
  - Network Acess Control Lists
  - Subnets
  - Security Groups
- 1 Subnet = 1 Availability Zone
- Security Groups는 Stateful 하다.(네트워크 연결 상태를 추적할 수 있다.)
- Network Access Control Lists는 Stateless 하다.
- NO TRANSITIVE PEERING

## VPC 실습
1. **VPC 만들기**
    - Your VPCs 로 이동
    - Create VPC
    - Name tag 및 IPv4 CIDR block 설정 (가장 큰 범위는 10.0.0.0/16)
    - IPv6 CIDR block = Amazon provided IPv6 CIDR block 으로 설정해야 나중에 더 많은 옵션이 생긴다.
    - Tenancy 선택
      - Default: 다른 고객과 하드웨어를 공유한다.(Multi tenant environment)
      - Dedicate: 독립된 하드웨어를 사용하므로 더 비싸다.  
2. **현재까지 상태**  
![](https://github.com/Integerous/TIL/blob/master/AWS/img/VPC%20with%20Public%20&%20Private%20Subnet1.png?raw=true)
3. **Subnet 만들기**
    - Subnets로 이동
    - Create Subnets
    - VPC 선택
    - Name tag 설정(강사는 CIDR block range와 AZ을 나타내기위해 `10.0.1.0 - us-east-1a` 라고 설정한다.)
    - Availability Zone 설정
    - IPv4 CIDR block 설정 (10.0.1.0/24)
    - IPv6 CIDR block 선택 (강사는 안함)
    - Yes, Create 클릭
4. 각 서브넷 CIDR 블록에서 첫 4개의 IP 주소와 마지막 IP 주소는 사용자가 사용할 수 없으므로 인스턴스에 할당할 수 없습니다. 예를 들어 10.0.0.0/24 CIDR 블록의 서브넷에서는 다음 5개 IP 주소가 예약되어 있습니다.
    - 10.0.0.0: 네트워크 주소.
    - 10.0.0.1: AWS에서 VPC 라우터용으로 예약한 주소.
    - 10.0.0.2: AWS에서 예약한 주소. DNS 서버의 IP 주소는 항상 VPC 네트워크 범위를 기초로 2를 더한 것입니다. 그러나 에서는 각 서브넷 범위를 기초로 2를 더한 것도 예약합니다. CIDR 블록이 여러 개인 VPC의 경우, DNS 서버의 IP 주소가 기본 CIDR에 위치합니다. 자세한 내용은 Amazon DNS 서버 단원을 참조하십시오.
    - 10.0.0.3: AWS에서 앞으로 사용하려고 예약한 주소.
    - 10.0.0.255: 네트워크 브로드캐스트 주소. VPC에서는 브로드캐스트를 지원하지 않으므로, 이 주소를 예약합니다.
5. **두번째 Subnet 만들기**
    - IPv4 CIDR block 설정 (10.0.2.0/24)  
6. **현재까지 상태**    
![](https://github.com/Integerous/TIL/blob/master/AWS/img/VPC%20with%20Public%20&%20Private%20Subnet2.png?raw=true)
7. **Internet Gateway 만들기**
    - Create Internet Gateway
    - Name tag 설정 (강의에서는 MyIGW) 및 Create
    - Attach to VPC
    - VPC 선택 후 Yes, Attach
    - *하나의 Internet Gateway는 하나의 VPC 에만 붙일 수 있다.*
8. **Route Table 만들기**
    - 새로운 서브넷을 만들면 default로 main route table에 연결된다. 하지만 매번 서브넷을 provisioning 할 때마다 main route table이 인터넷 Accessable하기 때문에 새로운 route table을 만든다.
    - Create Route Table
    - Name tag (강의에서는 MyInternetRouteOut) 및 VPC 설정
    - Yes, Create
9. **Route Table의 인터넷 연결 설정하기**
    - 새로 생성한 Route Table 선택
    - 밑의 Routes 선택하고 Edit 클릭
    - Add another route (0.0.0.0/0 , target = 좀전에 생성한 Interent Gateway)
    - Save
    - Add another route (::/0 , target = 위와 동일) 로 IPv6도 설정해준다.
    - 이제 이 route에 연결되는 모든 서브넷은 Public 서브넷이 된다.
10. **Subnet을 Route Table과 연결하기**
    - Route Tables -> 밑에 Subnet Associations 클릭
    - Edit
    - Public Subnet으로 사용할 서브넷(10.0.1.0)을 선택하고 Save. (나머지 한 서브넷은 Private이 된다.)
    - Subnets로 가서 오른쪽 끝부분에 Auto-assign Public IP을 확인하면 새로 추가한 서브넷들은 No로 되어있다.
    - Public Subnet으로 사용할 서브넷을 선택하고
    - Subnet Actions -> Modify auto-assign IP settings -> Enable auto-assign public IPv4 address 체크
    - Save
11. **EC2 생성**
    - EC2로 이동
    - Launch Instance 클릭
    - Amazon Linux AMI 클릭
    - Network를 Default VPC에서 Custom VPC로 변경
    
    
11. **Subnets로 가서 오른쪽 끝부분에 Auto-assign Public IP**
    - 
