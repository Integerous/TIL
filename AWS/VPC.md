# AWS - VPC
>[AWS Certified Solutions Architect - Associate 2018](https://www.udemy.com/aws-certified-solutions-architect-associate/) 강의를 듣고 내용 정리

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
- Default VPC의 모든 서브넷들은 인터넷으로 통하는 route가 있다.
