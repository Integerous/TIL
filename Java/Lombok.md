
# Lombok Project
> 회사에서 Lombok을 사용중이라 공부 시작!
> [Lombok Project](https://projectlombok.org/)에서 4분짜리 데모 영상을 보면 Lombok이 뭔지 간단하게 알 수 있다.
>>2016년 여름 인도네시아 발리로 한달 동안 서핑여행을 갔을 때 Lombok이라는 지역의 파도가 좋았었다...

# 주요 기능
>Lombok Project에서 소개한 [Lombok의 주요 기능 리스트](https://projectlombok.org/features/all)가 가장 유용한 정보였다.
## @Data
  - 사실 @Data 이것만 사용해도 무방한 것 같다.
  - @Data는 밑에 설명할 @Getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode을 한꺼번에 설정해준다.
  - 클래스 레벨에서 @Data 어노테이션을 붙여주면, 모든 필드를 대상으로 접근자와 설정자가 자동으로 생성되고, final 또는 @NonNull 필드 값을 파라미터로 받는 생성자가 만들어지며, toStirng, equals, hashCode 메소드가 자동으로 만들어진다.
# 동작 원리
- java source를 컴파일할 때 annotation processor로 등록된 lombok processor가 parsing 된 AST(Abstract Syntax Tree)를 annotation을 확인하고 그에 적합한 메쏘드를 생성해줘서 parsing된 AST에 적절한 위치에 넣어주어서 byte code로 변환하게 된다. [출처] PROJECT LOMBOK과 그 사용법|작성자 뜻깊은 태클

# Reference
- https://projectlombok.org/features/all
- http://www.daleseo.com/lombok-popular-annotations/
- http://wonwoo.ml/index.php/post/1607
- http://blog.naver.com/PostView.nhn?blogId=rlagyska3319&logNo=220824619962&categoryNo=0&parentCategoryNo=0&viewDate=&currentPage=1&postListTopCurrentPage=1&from=postView
