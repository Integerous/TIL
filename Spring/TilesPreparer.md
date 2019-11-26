# Tiles View Preparer
>@SessionAttributes를 사용해도 Tiles로 나누어진 header.jsp나 aside.jsp에서는 세션유지가 안되는 문제 발생하여 시작!  
Tiles의 ViewPreparer를 사용하여 인증된 사용자의 Member 객체를 Model에 저장하여  
layout이 실행될 때 Preparer가 실행되도록 하여 모든 페이지에서 로그인정보 세션유지 가능 !

## 1. Tiles Configurer 설정
- Member 정보를 모든 페이지에서 유지하기 위해
- `tilesConfigurer.setPreparerFactoryClass(SimpleSpringPreparerFactory.class);` 추가
~~~C
@Configuration
public class TilesConfig {

	@Bean
	public TilesConfigurer tilesConfigurer() {
		
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions(new String[] {"/WEB-INF/tiles.xml"});
		tilesConfigurer.setCheckRefresh(true);
		//Preparer 추가 18/5/25
		tilesConfigurer.setPreparerFactoryClass(SimpleSpringPreparerFactory.class);
		return tilesConfigurer;
	}
~~~
## 2. Tiles.xml에 `preparer="com.harusketch.util.MemberPreparer"` 추가
~~~C
<!-- member 템플릿 -->
  <definition name="layoutTemplate" template="/WEB-INF/views/inc/layout.jsp" preparer="com.harusketch.util.MemberPreparer">    
    <put-attribute name="header" value="/WEB-INF/views/inc/header.jsp" />
    <put-attribute name="aside" value="/WEB-INF/views/inc/aside.jsp" />
    <put-attribute name="footer" value="/WEB-INF/views/inc/footer.jsp" />
  </definition>
~~~

## 3. MemberPreparer 
~~~C
@Component
public class MemberPreparer implements ViewPreparer {

	@Autowired
	private MemberDao memberDao;
	
	@Override
	public void execute(Request tilesRequest, AttributeContext attributeContext) 
				throws PreparerException {

		// ********** 로그인 된 상태에서 만 실행되는 코드를 작성할 경우 ******************
		// 현재 로그인 한 사용자의 인증객체를 얻는다.
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		// 인증된 사용자의 Member 정보 얻는 방법
		if (!(auth instanceof AnonymousAuthenticationToken)) {
		
		// 인증객체를 통해서 인증에 사용된 id를 얻는다.
		final UserDetails userDetails = (UserDetails) auth.getPrincipal();
		final String username = userDetails.getUsername();

		// 이제 인증 id를 이용해서 Member 객체를 얻어온다.
		Member member = memberDao.get(username);

		// 위의 member 객체를 model 저장소에 담는다.
		Map<String, Object> model = tilesRequest.getContext("request");
		model.put("member", member);
	}
~~~

## 4. 해당하는 .jsp 페이지에 EL Member 정보 사용하기
`<a href=""> ${member.name} 님 안녕하세요 :)</a>`

# *Reference
- https://tiles.apache.org/framework/tutorial/advanced/preparer.html
- https://stackoverflow.com/questions/25358665/spring-4-tiles-3-2-2-2-any-way-to-access-beans-in-view-context
- https://richardbarabe.wordpress.com/2009/02/19/apache-tiles-2-viewpreparer-example/
