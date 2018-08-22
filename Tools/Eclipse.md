# Eclipse

## 1. git local 저장소에서 이클립스로 가져오기
- Import - General - Projects from Folder or Archive 선택
- 프로젝트 root 폴더를 지정.
- egit을 사용하지 않을 것이면 import - git - existing local repository 로 가져오면 안됌


## 2. 이클립스 초기 설정 (for Cloud Cash Project)

1. 환경설정 - Server - runtime environments 에 Tomcat 8.5 추가
2. 프로젝트 properties - Java Build Path 에 Libraries 4개 필요
    - Apache Tomcat 8.5
    - JRE System Library [JavaSE- 1.8]
    - Maven Dependencies
    - Web App Libraries
3. Properties - Java Compiler 버전 1.8로 수정
4. Properties - Maven - Project Facets 버전 설정
    - Dynamic Web Module - 3.1
    - Java -1.8
5. Properties - Server - Tomcat 8.5 추가
6. Tomcat에 프로젝트 물리고, servers 탭에서 Tomcat - Modules의 Path를
    - Document base : cash cloud
    - Path : / 로 수정
