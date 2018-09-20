# OAuth2 와 JWT
>실무에서 OAuth2와 JWT을 쓰게 되어 학습 내용을 정리한다.


## 한마디로 ?
Json Web Token의

## OAuth2 구현 과정
1. `build.gradle`에 `spring-boot-stater-security` 와 `spring-security-oauth2`를 추가한다.
2. `@EnableAuthorizaionServer` 어노테이션을 붙일 클래스를 만들고, `AuthorizationServerConfigurerAdapter`를 상속받는다.
3. 2번의 클래스 안에 configure(ClientDetailsServiceConfigurer clients) 메소드를 만들어서 API의 요청 클라이언트 정보를 설정한다.
  ~~~java
  @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("some_client_id")
                .secret("some_client_secret")
                .scopes("read:current_user", "read:users")
                .authorizedGrantTypes("client_credentials");
    }
  ~~~
  - `.inmemory()`는 클라이언트 정보를 메모리에 저장하므로 개발환경에 적합하다. (jdbc()는 데이터베이스에 저장하므로 운영환경에 적합)
  - `.withClient()`은 client id값 설정
  - `.secret()`은 client secret값 설정
  - `.authorizedGrantTypes()`는 Access Token을 획득하기 위한 4가지 인증 방법(grant types) 중 하나를 설정
  - Access Token을 획득하기 위한 4가지 인증 방법 (grant types)
    - Authorization Code Grant
    - Client Credentials Grant
    - Implicit Grant
    - Password Grant
4. 사용자 저장소 설정
  - 클라이언트와 토큰 관리는 Spring Security OAuth 모듈이 담당하지만, 사용자 관리는 Spring Security 담당
  ~~~java
  @Configuration
  public class SecurityConfig extends WebSecurityConfigurerAdapter {

      @Override
      protected void configure(AuthenticationManagerBuilder auth) throws Exception {

          auth.inMemoryAuthentication()
                  .withUser("user")
                  .password("password")
                  .roles("USER")
                  .and()
                  .withUser("admin")
                  .password("password")
                  .roles("USER", "ADMIN");
      }
  }
  ~~~
5. ClientDetailsService Bean 설정
## Reference
- https://github.com/auth0/java-jwt
- http://jsonobject.tistory.com/363
- https://www.youtube.com/watch?v=CPbvxxslDTU&list=PL7ej4hCdlMAaA9uvDLANWqL0C0zeRgFbK
