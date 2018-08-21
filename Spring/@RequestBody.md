# @RequestBody, @ResponseBody
>카카오톡 플러스친구 챗봇을 개발하던 중 @RequestBody를 사용할 일이 생긴 김에 공부하여 정리

## @RequestBody
>클라이언트에서 전송한 XML 데이터나 JSON 또는 기타 데이터를 컨트롤러에서 DOM 객체나 자바 객체로 변환해서 받을 수있는 기능(수신)
- @RequestMapping에 의해 POST 방식으로 전송된 HTTP 요청 데이터를 String 타입의 body 파라미터로 전달한다.
- @RequestBody를 사용하지 않은 경우 : query parameter, form data를 object에 맵핑한다.
- @RequestBody를 사용하는 경우 : body에 있는 data를 HttpMessageConverter를 이용해 선언한 object에 맵핑한다.

### 사용 예시 (카카오톡 플러스친구 챗봇 코드 중 일부)
  ~~~java
  @RequestMapping(value = "/message", method = RequestMethod.POST)
    public ResponseMessageVO message(@RequestBody RequestMessageVO vo) {

      ResponseMessageVO res_vo = new ResponseMessageVO();
      MessageVO mes_vo = new MessageVO();
      String command = vo.getContent();
      
      if(command.equals("메뉴")) {
        
      }
     
      ~~~
      
      res_vo.setMessage(mes_vo);
      return res_vo;
  }
  ~~~
  - 위에서 `@RequestBody`를 사용하여 사용자가 보낸 json 형식의 메세지 데이터를 String 타입의 body 파라미터로 받아서 RequestMessageVO 객체에 담는다.
  - 그리고 객체에 담긴 String 데이터를 if문에서 비교하여 사용한다.

## @ResponseBody
>자바 객체를 XML이나 JSON 또는 기타 형식으로 변환해서 전송할 수 있는 기능(송신)
- @ResponseBody 어노테이션이 @RequestMapping 메서드에서 적용되면 해당 메서드의 리턴 값을 HTTP 응답 데이터로 사용한다.

### 사용 예시
- 임의의 Response 객체 생성
  ~~~java
  public class ResponseTransfer {
      private String text; 

      // standard getters/setters
  }
  ~~~
- @Response 사용
  ~~~java
  @Controller
  @RequestMapping("/post")
  public class ExamplePostController {

      @Autowired
      ExampleService exampleService;

      @PostMapping("/response")
      @ResponseBody
      public ResponseTransfer postResponseController(
        @RequestBody LoginForm loginForm) {
          return new ResponseTransfer("Thanks For Posting!!!");
       }
  }
  ~~~
- 결과
  ~~~java
  {"text":"Thanks For Posting!!!"}
  ~~~

## * Reference
- https://www.baeldung.com/spring-request-response-body
- [장인개발자를 꿈꾸는 :: 기록하는 공간](http://devbox.tistory.com/entry/Spring-RequestBody-%EC%96%B4%EB%85%B8%ED%85%8C%EC%9D%B4%EC%85%98%EA%B3%BC-ReponseBody-%EC%96%B4%EB%85%B8%ED%85%8C%EC%9D%B4%EC%85%98%EC%9D%98-%EC%82%AC%EC%9A%A9)
- [파란하늘 개발공부](http://bluesky-devstudy.blogspot.com/2016/07/spring-mvc-requestbody.html)
