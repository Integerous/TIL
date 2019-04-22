# Path Variable과 Query Parameter는 언제 사용해야 할까? [번역]

>원작자의 허락을 받고 번역한 글입니다.  
>원문: [When Should You Use Path Variable and Query Parameter?](https://medium.com/@fullsour/when-should-you-use-path-variable-and-query-parameter-a346790e8a6d)

>역자 주:  
Spring boot와 Vue.js로 파일럿 프로젝트를 개발하던 중,  
카테고리로 게시물 목록을 필터링할 때 @PathVariable로 카테고리 이름을 전달했더니, '/'가 포함된 카테고리 이름이 문제를 일으켰다.  
알고 보니, 이 글에서 설명하는 것 처럼 Resource를 가져오는 Get 메소드는 PathVariable을 사용하고,  
정렬(sort)이나 필터링을 하는 경우 QueryParam을 사용하는 것이 옳은 방법이었다.

이 글을 통해 PathVariable 과 Query Parameter 가 무엇이고, 어떻게 사용해야 하는지 이야기하고자 한다.

## 1. Query Parameter
당신이 웹 개발자라면, 가장 간단한 방법인 GET 메소드를 사용해서 데이터를 전송하는 방법을 배웠을 것이다.  
소셜 서비스를 만든다고 가정해보자. 사용자 목록을 관리해야 하고, 모든 사용자는 사용자 페이지가 있어야 할 것이다.  

그리고 각각의 사용자를 위한 페이지를 만들려면 페이지 마다 식별된 파라미터 경로가 필요한데,  
다음과 같은 get 파라미터를 사용할 수 있을 것이다.

~~~yml
/users?id=123  # 아이디가 123인 사용자를 가져온다.
~~~

그럼 서버로 부터 id 변수를 얻을 수 있다. 이것이 Query String이 동작하는 방식이다.  
(이 글에서는 서버단에서의 동작까지는 설명하지 않을 것이다.)


## 2. Path Variable
하지만 데이터를 넘기는 방법 중의 하나로 Path Variable도 사용할 수 있다.  
Path Variable은 다음과 같이 사용한다.

~~~yml
/users/123  # 아이디가 123인 사용자를 가져온다.
~~~

이 경우에, 123 을 서버단에 전달한다. 경로를 변수로서 사용하는 것이다.


## 3. 그럼 Path Variable과 Query Parameter를 각각 언제 사용해야 하는가?

만약 어떤 **resource를 식별**하고 싶으면 **Path Variable**을 사용하고,  
**정렬이나 필터링**을 한다면 **Query Parameter**를 사용하는 것이 Best Practice이다.

~~~yml
/users  # 사용자 목록을 가져온다.
/users?occupation=programer  # 프로그래머인 사용자 목록을 가져온다.
/users/123  # 아이디가 123인 사용자를 가져온다.
~~~

또한, 기본적인 CRUD 기능을 위해서 또 다른 URL이나 query parameter를 정의할 필요는 없다.  
대신 원하는 기능에 맞게 HTTP 메소드를 바꾸어야 한다.

~~~yml
/users [GET] # 사용자 목록을 가져온다.
/users [POST] # 새로운 사용자를 생성한다.
/users/123 [PUT] # 사용자를 갱신한다.
/users/123 [DELETE] # 사용자를 삭제한다.
~~~

그렇다! 거의 모든 CRUD 프로세스를 
추가적인 endpoint(예를 들어 users/create) 또는  
query parameter(예를 들어 users?action=create) 없이 수행할 수 있다. 게다가 단순하고 예측 가능하다.


## 4. 정리

앞서 말한 방법대로 구현하지 않아도 동작하는 API를 만들 수는 있다.  
하지만 훨씬 복잡해질 것이고 개발속도를 늦출 것이다.   
개발 효율을 최대한 끌어 올리고 다른 개발자들과의 협업을 고려하면, 표준이 되는 방법과 best practice를 따르는 것이 좋다.

마지막으로, References의 링크들을 통해 RESTful API를 설계하는 또 다른 best practice들을 확인할 수 있다.

## References
- [Best Practices for Designing a Pragmatic RESTful API](http://www.vinaysahni.com/best-practices-for-a-pragmatic-restful-api)
- [RESTful API Designing guidelines — The best practices](https://hackernoon.com/restful-api-designing-guidelines-the-best-practices-60e1d954e7c9)
- [RESTful API Design. Best Practices in a Nutshell](https://phauer.com/2015/restful-api-design-best-practices/)
