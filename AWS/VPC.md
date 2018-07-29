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
