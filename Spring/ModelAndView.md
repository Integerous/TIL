# ModelAndView 클래스를 이용한 데이터 전달
> Model 객체만 데이터를 전달할 수 있는 것은 아니다.

## 예시
~~~java
@RequestMapping("/board/reply")
public ModelAndView reply() { // ModelAndView import해야됌 
  
    ModelAndView mv = new ModelAndView(); // ModelAndView 객체 생성
    mv.addObject("id", 30); // model 객체에 데이터 담음
    mv.setViewName("board/reply") // View 이름 설정
    
    return mv;
~~~
