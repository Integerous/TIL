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
