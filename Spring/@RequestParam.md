> Spring+Mybatis로 게시판 CRUD 구현하면서 공부한 내용

# @RequestParam
- Http 요청 파라미터를 메서드의 파라미터로 전달받을 때 사용

## @RequestParam vs HttpServletRequest
- @RequestParam("bno")는 request.getParameter("bno")처럼 동작한다.
- @RequestParam이 Servlet의 HttpServletRequest와 다른 점은 문자열, 숫자, 날짜 등의 형 변환이 가능하다는 것.
- 비교 예시
~~~java
@RequestMapping("board/confirmId")
public String confirmId (HttpServletRequest request, Model model) {
  String id = request.getParameter("id");
  String pw = request.getParameter("pw");
  model.addAttribute("identify", id);
  model.addAttribute("password", pw);
  
  return "board/confirmId";
}
~~~
```java
@RequestMapping("board/confirmId")
public String confirmId (@RequestParam("id") String id, @RequestParam("pw") int pw, Model model) {
  model.addAttribute("identify", id);
  model.addAttribute("password", pw);
  
  return "board/confimrId";
}
```

## 사용 방법
~~~java
메서드(@RequestParam(PARAM) Obj )
메서드(@RequestParam Map )
~~~
  - PARAM : 전달되는 파라미터의 이름 지정. 이름 외에 기본값(defaultValue), 필수여부(required)를 설정할 수 있다.  
값이 할당될 변수의 타입이 Map 혹은 MultiValueMap일 경우 명시하지 않는다.
  - Obj : PARAM으로 지정된 이름과 일치하는 파라미터의 값을 할당할 변수,  
보통 String 타입을 선언하지만 넘어온 값이 반드시 숫자일 경우에 한해서 int등의 숫자 타입도 가능
- @RequestParam 이 적용된 파라미터가 String이 아닐 경우 자동으로 타입 변환

## 내가 한 삽질  
  ```java
  @RequestMapping("/delete")
  public String deleteByPrimaryKey(@RequestParam(value="artcSeq", defaultValue="1") ArticleKey artcSeq)
  ```
  - 위의 코드에서 artcSeq의 값 정수 15가 String으로 들어가서 4시간동안 삽질함
  
## *Reference
- https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestParam.html
