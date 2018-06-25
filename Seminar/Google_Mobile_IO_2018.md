# Keynote 1
>Google I/O 2018 최신 기술 총 정리
>>Tian Lim VP of UX &Product for Paly

## #IMakeApps 
g.co/play/imakeapps

##AI for Everyone
1. Making product more useful
  - suggested actions  brighten
  - color pop
  - Adaptive battery
    - 30% 사용량 감소
2. Helping others inovate
  - TPU 3.0
  - Helping diagnose diabetic retinopathy
3. Deigital Well-Being
  - Dashboard
  - App Timer
  - Wind Down
    - 수면 전 사용에 화면을 흑백으로 바꿔서 수면에 도움이 되도록 하는 기능

## Android App Bundle
Avertage APK size is getting bigger
Upload and android App Bundle > Dynamic Delivery > Optimized APK for each device

# Keynote 2
>모바일 개발자를 위한 I/O 2018 : Android의 새로운 기능 소개
>>

지난 10년간 안드로이드의 발전은 스마트폰에 국한되지 않았다.
## Kotlin
35%의 프로 개발자들이 코들린을 사용하고 있다. 그중 95%는 매우 만족했다.

## State of Kotlin
70만 이상의 사용자
110개의 유저그룹
1200 talks 
14000멤버의 슬랙

17000멤버 슬랙
30 코틀린나잇
1000 등록자 KotlinConf 2018

48% 다른 언어에서 코틀린으로 완전히 바꿨따.
5000명 이상의 회사중 20%가 코틀린 사용한다.
2%의 사용자가 그들의 첫번째 언어로 코틀린을 선택했따

어플리케이션 타입은 모바일이 60%

스택오버플로우에서 코틀린이 75.1%로 가장 사랑받는 언어였다.
Baeldung 2018 서베이

## What we're working on
1. Speed improvemetns
2. Toolling support
3. Language evolution
4. Multi-platform support

Kotlin/JVM
  -desktop, 
Kotlin/JS
Kotlin/Native
  - ios, macos, linux, windows raspberry p


Conversion rate decreases as apps get larger


## Android app Bundle
새로운 퍼블리싱 포맷이고co/androidappbub


## Google Play Instant for game developers
>Benjamin Frenkel
>>ANdroid Product Manager

- Google Play Instant is now available to all game developers
- 구글 플레이 인스턴트를 통해 사람들은 다운로드 하기 전에 앱을 사용해볼 수 있다. ex) 캔디크러시 사가
- Bringing instant to games will transform game discovery
- 사람들이 노래를 들어보고 구매하는 것처럼 게임도 해보고 설치하게 될 것이다.
- When media becomse instant, consumption goes up. And we want to do the same for games.
- 최대 20% 까지 user acquisitions가 증가했다.
- Pre-registration coming soon

Bring back churned users

## 게임 출시 단계별 인스턴트 사용 전략
1. Upcoming launches
  - Core gameplay preview
  - 설치된 게임에서 초반에 사용할 수 있는 아이템들보다 더 많은 아이템들을 인스턴트에서 사용하게 하므로서 게임의 재미를 더욱 느끼게 한다. 
3. Late stage games
  - Game highlights

## Tools for game developers
- Instant apps framework
  1. Raised size limit to 10mb for games
  2. enanled progressive download
  3. added gaem engine, NDK, and OpenGL ES
- Unity plug-in (coming soon)
- Unity Beta program (available now)


# Android Jetpack
- Backwords compatible
- Less boilerplate code
- Opinionated
- Integrated with IDE

## Android Slices
>g.co/slices

## App Actions


# Session 1
>[패널토크] Google AI: 누구나 사용할 수 있는 AI의 미래
>>장혜덕(Google Cloud 한국 사업 총괄), 성원용(서울대학교 전기전자공학부 교수), 최종혁(Google Cloud Solutions Architect)

### 성원용 교수의 구글 Cloud 사용 경험
- 교수와 대학원생들은 Sprinter에 가깝다. 때문에 컴퓨터를 매일 사용하는 것이 아니고 학회를 앞두고 한번에 많은 컴퓨팅이 필요하기 때문에 클라우드 컴퓨팅 서비스가 필요하다.
- 구글 클라우드 ML엔진을 사용할때 파라미터 튜닝을 스스로 해줘서 좋았다.

### 최종혁
- 클라우드 ML엔진은 전문적으로 사용하기 적합하지만 프레임워크를 이용해서 코딩을 해야한다는 점 때문에 보다 더 손쉽게 코딩을 하지 않고도 사용할 수 있도록 하기 위해 Google Cloud AutoML Vision을 만들었다.
- TPU (Tensor Proccessing Unit) 라는 하드웨어 가속기
  - 우리가 만든 머신러닝모델을 가속화 시켜주는 모델
  - 구글 클라우드 서비스를 사용하면 TPU를 바로 사용할 수 있다.
- Kaggle
  - 데이터 사이언스 관련 일종의 공유경제 플랫폼.
  - 사람들이 함께 문제를 해결해나가는 것. 예를 들어 어떤 회사의 문제를 전세계 데이터사이언티스트들과 함께 해결해갈 수 있다.

### 미래의 AI의 모습은?
- 서울대학교 공학전문대학교에 AI 수업을 개강했는데 공학을 전공하지 않은 학생들이 100명이나 수강신청했다.
- 학생들의 백그라운드가 전산학을 전공하지 않았지만, 요즘은 툴 사용이 쉬워졌기 때문에 사용 분야에 대한 인사이트가 더욱 중요하다.
- 때문에 전공자가 아니더라도 AI를 학습하고 이를 본인 분야에 적용하는 것을 추천한다.
- AI 교육의 미래는 어떻게 되어야 하는가?
 - AI를 배우기 가장 좋은 툴은 구글이 인수해서 운영하고 있는 youtube다.
 - 우리나라 사람들은 수학을 빡세게 했기때문에 머신러닝에 들어가는 미적분은 문과생도 할 수 있다.
- 우리가 사용하는 대부분의 제품과 서비스에 AI기술이 탑재될 것이다.

### Question
1. TPU가 빠르다고 하는데 얼마나 빠른 것인지?
  - 속도는 측정을 안해봤고, 전력소모가 굉장히 적다.
2. 전산학을 전공한 개발자입니다. AI를 입문하기에 가장 좋은 프로젝트나 강의가 무엇일까요?
  - 고등학생인 자녀가 집에서 AI를 공부하고 있는데, 구글에서 제공하는 클래스 코스와 Deeplaerning.ai 라는 곳의 코스가 좋다.


# Session 2
>Google Assistant, 내 비즈니스에 어떻게 활용해야 할까
>>이주연(Strategic Partnership Development Manager, Google)

## 구글 Assistant 업데이트 16가지 중 중요한 4가지
1. 구글 assistant가 어디까지 왔는지
  - 총 5억대 이상의 Device에서 구글 Assistant를 사용한다.
  
2. 구글 assistant를 접할 수 있는 distribution 채널들
  - 보통 스마트스피커를 생각하지만 다양한 채널들이 있다.
  - Voice, Visual, Touch를 사용하는 Smart Display
  - Smart Display는 스마트스피커에 시각적 정보가 추가된 device이다.
  - 지금까지는 챗봇같은 형식으로 대화했지만 이제는 일상생활에 필요한 정보들을 My Day라는 형태로 필요한 정보를 한 화면에 표시하는 형태로 진화할 것이다.
  - Google Maps에도 Google Assistant가 들어갈 것이다. 하지만 아직 한국에서는 일정이 잡혀있지 않다.
  
3. 구글 assistant가 얼마나 사람과 대화하기위해 어떤 기술들을 발전시켜왔는지
  - 새로운 종류의 Voice 6가지가 추가되었다.
    - 특히 John Legend의 목소리
    - 특정 사람의 목소리를 구글에서 트레이닝 시켜서 앞으로 연예인이나 특정인의 목소리를 구글 Assistant에서 사용하게 될 것이다.
  - Continued Conversation
    - 언제나 서비스 이름을 불러서 사용해야했다.(Hey google) 하지만 앞으로는 그렇게 매번 부를 필요없다.
    - 즉 문맥을 이해하고 지난 대화에 이어서 자연스럽게 대화를 이어나갈 수 있게 된다.
  - Multiple Action
    - 한 문장에 2가지 이상의 문장을 넣는 것 (케빈듀란트가 이적했을때 캘리포니아 주지사는 누구였니? 그리고 듀란트는 어디로 이적했니? 라는 질문에 한문장으로 대답하는 것)
  - Pretty Please
    - Family Link를 이용해서 아이가 사용하는 중이라면 대화할때 please를 붙이도록 할 수 있다.
  - Google Duplex
  
4. assistant와 연동할 수 있는 방법들과 추가된 기능들
  - 구글 Assistant 연동 방식
    1. Vertical Program(=Direct Actions)
      - 예를 들어 음악 스트리밍 서비스를 사용중이라면 서비스를 설정해 둘 수 있다. 설정을 해두었다면 구글 Assistant한테 벅스한테 비틀즈의 렛잇비를 틀어줘라 할 필요없이 그냥 비틀즈의 렛잇비를 틀어달라고 하면 된다.
    2. Conversational Actions
      - ex) Hey Google, talk to California Surf Report 이후에는 그 서비스와 직접 대화하는 형태
    3. Content Actions (AMP/SDM) (새로 추가)
    4. App Actions (새로 추가)
      - 기존에 가지고있는 android intent(명령어)를 Assistant에 적용하는 것
      - 아직 한국에서는 진행하고있지 않다.
  - 3rd Party연동을 위한 새로운 기능들 (미국 기준)
    1. 루틴
      - Good morning
      - Leaving home
      - Commuting to work
      - Commuting to home
      - I'm Home
      - Bedtime
      - Todoist
    2. 데일리 업데이트 / 푸쉬 노티피케이션
      - 매일 리마인드 받고싶은 것들(오늘의 명언 등)을 푸시받는다.
    3. 액션 링크
      - 이미 가지고 있는 본인의 웹사이트, sns에 url링크를 걸어서 구글 Assistant로의 이동
    4. 멀티 서베이스 대화
      - 음성 대화에서 사용자가 더 자세한 내용을 보여달라고 했을때 자세한 내용을 사용자의 다른 device로 보내줄 수 있는 것
    5. 결제 (재화 서비스 구매/ 예약 / 디지털 굿즈 구매)
      - 스타벅스의 경우 단순히 결제하는 것에 멈추지 않고, 유저가 평소에 먹는 것과 픽업 장소를 기억하고 제안한다.
      - 예약 서비스도 구글 Assistant에서 진행할 수 있게 될 것

## Question
  - 한국에서 구글 Assistant가 언제 상용화/활성화 되나요?
    - 2018년 하반기에 다양한 발표가 있을 예정

# Session 3
>Android Things: Android 앱 개발 기술로 쉽게 IoT 만들기
>>장인선(Software Engineer, Google)


# Session 4
>Kotlin으로 코딩 시작하기
>>Hadi Hariri(VP of Developer Advocacy, Jetbrains)

# Session 5
>Dialogflow와 ML API를 활용한 챗봇 개발
>>정명훈(Customer Engineer, Google Cloud)

## 챗봇이 급성장하고 있는 이유?
- 스마트 디바이스의 증가
- 브랜드 참여도 전환

## 3가지 주요 사용 사례
- Customer Service
  - 콜센터에서 콜 1통에 드는 비용은 몇 천원에 달한다.
  - 콜센터의 콜을 줄일 수 있다면 획기적으로 비용을 절감할 수 있을 것
- Transactions
  - 사용자의 프로필을 기준으로 맞춤화된 오퍼
  - 보험사에서 Diaflow를 활용하여 상담량을 획기적으로 증가시켰다.
- Getting things done
  - 사용자가 브랜드와 상호 작용 할 수 있는 새로운 방법 제공

## 현대적인 Chatbot 개념
  - Intent, Entity, Context를 파악해서 그에 맞게 응답한다.

## 아들이 영어단어 암기 프로그램을 만들어 달라했다.
  - Chatbot(Dialogflow + Google Assistant 활용)
    - 문제풀이 대화식으로 진행
    - 컴퓨터, 모바일 어디서나 사용가능
    - 대화를 통해 재미 유도 => 자연스럽게 음성 기능 추가
    - Google Sheets에 단어/문제/정답 입력

## Dialogflow
  - 거의 챗봇개발의 표준이 되었다.
  - 한국어 지원이 잘된다.
  - Assistant와 Dialogflow를 같이 사용하면 백엔드를 Dialogflow가 맡게 된다.
  - 규칙 기반이 아니라 문장을 분석해서 머신러닝 학습을 시킨다.
    - 전통적인 규칙기반은 모든 정보를 입력해야했는데 그것은 불가능했기 때문에 한계가 있었다.
    - 때문에 머신러닝 기반으로 40% 정도 향상된 성능을 발휘한다.
    - 예문을 쭉 입력하고 머신러닝을 돌린다.
  - 사전 구축된 에이전트(Built-in agent)가 첫 시작을 쉽게 해준다.
  
## Google ML API
  - TensorFlow
  - Cloud ML
    - Data scientis
  - Pre-built Models
    - App Developer
    - Translation API
    - Natural Language API (자연어 처리)
      - 입력된 문장에 대한 감성 분석, 문법적 분석, 엔티티 추출등을 지원하며, 한국어, 영어, 중국어, 일어 등 여러가지 언어를 지원
      - 학습에 필요한 데이터 입력할때 자연어 처리 API를 사용하면 좋다.
    - Speech API (음성인식)
      - 음성을 인식해서 Text로 변환해주는 API. 구문힌트나 인식모드 등을 통해 정확도 향상
    - Vision API (사진 분석, 텍스트 인식)
      - 사진을 분석해서 엔티티나 text, 사람 및 표정, 장소(랜드마크), 부적절한 이미지 등 검색
    - Video Intelligence API
      - 영상을 분석해서 엔티티를 추출하고 레이블과 해당 내용이 나온 장면을 분석
      - 감시 카메라에 활용
    
    
    
    
    
    
    
    
