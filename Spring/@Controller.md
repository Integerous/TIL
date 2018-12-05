## @Controller
>[이일민(토비)님의 Oracle Code Seoul 2017 강연](https://www.youtube.com/watch?v=BFjrmj4p3_Y)에서 짧게 설명된 @Controller의 역할에 대해 정리.

~~~java
@Controller
public class HelloWorldController {
  @ReqeustMapping("/helloWorld")
  public String helloWorld(Model model) {
    model.addAttribute("message", "Hello World!");
    return "helloWorld";
  }
}
~~~

위 코드에서 @Controller의 역할은  

1. 스프링 빈 스캐너에 의해서 싱글톤 빈으로 등록
2. 빈 이름을 지정하지 않아도 클래스명의 제일 앞글자만 소문자로 바꿔서 helloWorldController으로 빈이름 지정
3. MVC 컨트롤러 기능 담당
4. @Controller가 붙은 클래스에 @RequestMapping가 있으면, 이것을 맵핑정보로 활용해서 웹컨트롤러 메소드로 사용하게 해준다.
