# @Transient
- @Entity 애노테이션이 붙은 클래스의 모든 속성들은 기본으로 모두 테이블의 필드로 생성하게 된다.  
만약 어떤 속성을 테이블의 필드로 만들고 싶지 않다면, @Transient을 해당 속성 또는 해당 속성의 getter위에 붙이면 된다.
- @Transient 어노테이션을 사용한 필드나 메소드는 DB 테이블에 적용되지 않는다.

## 예를 들어
- 회원가입 시 패스워드를 제대로 입력했는지 확인하기 위해서 한 번 더 입력을 하는데,  
이러한 속성은 DB에 저장할 필요가 없기 때문에, @Transient로 설정해 줄 수 있다.
~~~C
@Entity
public class Member {
    private Long memberId;
    private String password;
    @Transient
    private String confirmPassword;
    ...
}
~~~
# Reference
- http://egloos.zum.com/LuckyChips/v/1692422
- http://whiteship.tistory.com/1169
