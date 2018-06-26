# Model, Model Map, ModelAndView 차이
  - Model은 interface
  - Model Map은 Class
  - ModelAndView는 ModelMap과 View 객체를 위한 Container. 컨트롤러가 이 둘을 하나의 값으로 리턴할 수 있다.
## 예시
### 1. Model
~~~java
@RequestMapping
public String PrintHello(Model model) {
  model.addattribute("message", "Hello World!");
  return "hello";
~~~
### 2. ModelMap
~~~java
@RequestMapping("/helloworld")
public String hello(ModelMap map) {
    String helloWorldMessage = "Hello world!";
    String welcomeMessage = "Welcome!";
    map.addAttribute("helloMessage", helloWorldMessage);
    map.addAttribute("welcomeMessage", welcomeMessage);
    return "hello";
~~~
### 3. ModelAndView
~~~java
@RequestMapping("/board/reply")
public ModelAndView reply() { // ModelAndView import해야됌 
  
    ModelAndView mv = new ModelAndView(); // ModelAndView 객체 생성
    mv.addObject("id", 30); // model 객체에 데이터 담음
    mv.setViewName("board/reply") // View 이름 설정
    
    return mv;
~~~
~~~java
@RequestMapping("/welcome")
public ModelAndView helloWorld() {
        String message = "Hello World!";
        return new ModelAndView("welcome", "message", message);
    }
~~~

# *Reference
- https://stackoverflow.com/questions/18486660/what-are-the-differences-between-model-modelmap-and-modelandview
