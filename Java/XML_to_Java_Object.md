# XML 데이터를 Java 객체로 변환하기 (feat. JAXB)

>XML 데이터를 Java 객체로 변환하여 사용한 경험을 정리해본다.

JSON 데이터만 다루다가 XML을 다루어야 했기 때문에 조금 생소했다. (요즘 같은 세상에 XML 이라니!)  
그런데 아직 XML을 사용하는 API들이 많다는 사실을 알게 되었고, 항공 분야는 거의 모든 API가 XML이라고 한다.  

처음에는 [한국거래소의 주가정보 가져오기](https://m.blog.naver.com/platinasnow/220730608310)라는 블로그 글을 참고하여  
API가 제공해주는 XML 데이터를 JSON Object로 변환하고, 이를 다시 Map 형태로 바꾸는 방법으로 개발했다.

그런데 이 경우 개발은 빠르게 진행할 수 있었지만, 예외 처리와 데이터 가공에 한계가 있었다.  
부팀장님으로부터 XML 데이터를 Java 객체로 변환하여 다루는 `Unmarshalling`이라는 개념을 듣게 되었고,  
이를 지원해주는 Java API인 **JAXB**(Java Architecture for XML Binding)를 알게 되었다.


## JAXB ?

JAXB는 Java 클래스를 XML로 변환해주는 `Marshalling`과 XML을 Java 객체로 변환해주는 `Unmarshalling` 기능을 제공한다.

>JAXB는 JDK1.6에 번들링되어있기 때문에 JDK 1.6 이상의 버전이면 별다른 설정없이 바로 사용할 수 있다.  
(JDK 1.6 이하의 경우 [JAXB 공식 Repo](https://github.com/eclipse-ee4j/jaxb-ri)에서 다운받아서 사용해야 한다는데 안해봐서 모르겠다.)


</br>

<p align="center">
  <img src="https://github.com/Integerous/TIL/blob/master/ETC/images/JAXB_architecture.gif?raw=true">
</p>

이미지 출처:
[IBM Knowledge Center](https://www.ibm.com/support/knowledgecenter/ko/SSEQTP_9.0.0/com.ibm.websphere.base.doc/ae/cwbs_jaxb.html)


간단히 설명하자면 `xjc 스키마 컴파일러`를 통해 XML 스키마를 Java 객체로 변환하고,  
`schemagen 스키마 생성기`를 통해 Java 객체를 XML 스키마로 작성해주는 것이다.


## KRX(한국거래소) 주가정보 API
>[KRX(한국거래소)의 주가정보 API](https://kasp.krx.co.kr/contents/02/02010000/ASP02010000.jsp)가 제공해주는 실시간 시세 데이터는 아래와 같다.  
(임시로 네이버의 종목코드를 사용했다.)

~~~xml
<?xml version="1.0" encoding="utf-8" ?>
<stockprice querytime="2019-06-03 14:18:12" >
	<TBL_DailyStock>
		<DailyStock day_Date="19/06/03" day_EndPrice="115,000" day_Dungrak="2" day_getDebi="3,500" day_Start="113,000" day_High="115,000" day_Low="112,000" day_Volume="270,570" day_getAmount="30,831,869,000"/>
		<DailyStock day_Date="19/05/31" day_EndPrice="111,500" day_Dungrak="5" day_getDebi="500" day_Start="111,500" day_High="112,500" day_Low="110,000" day_Volume="310,649" day_getAmount="34,620,804,500"/>
		<DailyStock day_Date="19/05/30" day_EndPrice="112,000" day_Dungrak="2" day_getDebi="2,500" day_Start="110,000" day_High="114,000" day_Low="109,500" day_Volume="378,379" day_getAmount="42,526,903,757"/>
		<DailyStock day_Date="19/05/29" day_EndPrice="109,500" day_Dungrak="2" day_getDebi="1,000" day_Start="109,000" day_High="111,000" day_Low="108,500" day_Volume="515,196" day_getAmount="56,529,874,000"/>
		<DailyStock day_Date="19/05/28" day_EndPrice="108,500" day_Dungrak="5" day_getDebi="2,000" day_Start="109,500" day_High="112,500" day_Low="108,500" day_Volume="1,490,526" day_getAmount="162,354,790,344"/>
		<DailyStock day_Date="19/05/27" day_EndPrice="110,500" day_Dungrak="5" day_getDebi="3,000" day_Start="112,500" day_High="113,000" day_Low="109,500" day_Volume="369,902" day_getAmount="41,005,007,000"/>
		<DailyStock day_Date="19/05/24" day_EndPrice="113,500" day_Dungrak="2" day_getDebi="500" day_Start="113,000" day_High="115,000" day_Low="112,500" day_Volume="408,081" day_getAmount="46,387,229,000"/>
		<DailyStock day_Date="19/05/23" day_EndPrice="113,000" day_Dungrak="3" day_getDebi="0" day_Start="112,500" day_High="114,000" day_Low="110,000" day_Volume="545,497" day_getAmount="61,145,647,703"/>
		<DailyStock day_Date="19/05/22" day_EndPrice="113,000" day_Dungrak="5" day_getDebi="1,500" day_Start="115,000" day_High="115,000" day_Low="111,000" day_Volume="567,069" day_getAmount="64,006,105,000"/>
		<DailyStock day_Date="19/05/21" day_EndPrice="114,500" day_Dungrak="5" day_getDebi="4,000" day_Start="117,500" day_High="119,500" day_Low="113,500" day_Volume="603,105" day_getAmount="70,058,497,000"/>
	</TBL_DailyStock>
	<TBL_AskPrice>
		<AskPrice member_memdoMem="미래에셋대우" member_memdoVol="55,036" member_memsoMem="모간서울" member_mesuoVol="51,883"/>
		<AskPrice member_memdoMem="키움증권" member_memdoVol="22,730" member_memsoMem="메릴린치" member_mesuoVol="26,117"/>
		<AskPrice member_memdoMem="NH투자증권" member_memdoVol="19,927" member_memsoMem="에스지" member_mesuoVol="15,435"/>
		<AskPrice member_memdoMem="모간서울" member_memdoVol="16,103" member_memsoMem="씨엘" member_mesuoVol="13,549"/>
		<AskPrice member_memdoMem="CS증권" member_memdoVol="15,608" member_memsoMem="미래에셋대우" member_mesuoVol="13,470"/>
	</TBL_AskPrice>
	<TBL_StockInfo JongName="NAVER보통주" CurJuka="115,000" DungRak="2" Debi="3,500" PrevJuka="111,500" Volume="270,570" Money="30,831,869,000" StartJuka="113,000" HighJuka="115,000" LowJuka="112,000" High52="782,000" Low52="106,500" UpJuka="144,500" DownJuka="78,500" Per="25.92" Amount="164,813,395" FaceJuka="100" />
	<TBL_Hoga mesuJan0="3,709" mesuHoka0="113,500" mesuJan1="5,714" mesuHoka1="113,000" mesuJan2="3,504" mesuHoka2="112,500" mesuJan3="4,100" mesuHoka3="112,000" mesuJan4="3,813" mesuHoka4="111,500" medoJan0="4,389" medoHoka0="116,000" medoJan1="5,532" medoHoka1="115,500" medoJan2="14,353" medoHoka2="115,000" medoJan3="6,572" medoHoka3="114,500" medoJan4="734" medoHoka4="114,000"/>
	<TBL_TimeConclude>
		<TBL_TimeConclude time="14:18:20" negoprice="115,000" Dungrak="2" Debi="3,500" sellprice="115,000" buyprice="114,500" amount="4"/>
		<TBL_TimeConclude time="14:18:10" negoprice="114,500" Dungrak="2" Debi="3,000" sellprice="115,000" buyprice="114,500" amount="80"/>
		<TBL_TimeConclude time="14:18:00" negoprice="115,000" Dungrak="2" Debi="3,500" sellprice="115,000" buyprice="114,500" amount="108"/>
		<TBL_TimeConclude time="14:17:50" negoprice="115,000" Dungrak="2" Debi="3,500" sellprice="115,000" buyprice="114,500" amount="95"/>
		<TBL_TimeConclude time="14:17:40" negoprice="115,000" Dungrak="2" Debi="3,500" sellprice="115,000" buyprice="114,500" amount="216"/>
		<TBL_TimeConclude time="14:17:30" negoprice="114,500" Dungrak="2" Debi="3,000" sellprice="115,000" buyprice="114,500" amount="17"/>
		<TBL_TimeConclude time="14:17:20" negoprice="114,500" Dungrak="2" Debi="3,000" sellprice="115,000" buyprice="114,500" amount="15"/>
		<TBL_TimeConclude time="14:17:10" negoprice="114,500" Dungrak="2" Debi="3,000" sellprice="115,000" buyprice="114,500" amount="39"/>
		<TBL_TimeConclude time="14:17:00" negoprice="114,500" Dungrak="2" Debi="3,000" sellprice="115,000" buyprice="114,500" amount="451"/>
		<TBL_TimeConclude time="14:16:50" negoprice="115,000" Dungrak="2" Debi="3,500" sellprice="115,000" buyprice="114,500" amount="503"/>
		</TBL_TimeConclude>
	<stockInfo kosdaqJisu="697.33" kosdaqJisuBuho="2" kosdaqJisuDebi="0.86" starJisu="1328.27" starJisuBuho="2" starJisuDebi="10.63" jisu50="" jisu50Buho="" jisu50Debi="" myNowTime="2019/06/03 14:18:15" myJangGubun="장중" myPublicPrice="" krx100Jisu="4319.08" krx100buho="2" krx100Debi="60.71" kospiJisu="2063.14" kospiBuho="2" kospiDebi="21.40" kospi200Jisu="267.55" kospi200Buho="2" kospi200Debi="3.66"/>
</stockprice>
~~~

이 중 필요한 데이터는 아래와 같았다.

~~~xml
<stockprice querytime="2019-05-22 15:19:56">

    <TBL_DailyStock>
        <DailyStock day_Date="19/05/22" day_EndPrice="113,000" day_Dungrak="5" day_getDebi="1,500" day_Start="115,000" day_High="115,000" day_Low="111,000" day_Volume="521,770" day_getAmount="58,887,446,500"/>
        <DailyStock day_Date="19/05/21" day_EndPrice="114,500" day_Dungrak="5" day_getDebi="4,000" day_Start="117,500" day_High="119,500" day_Low="113,500" day_Volume="603,105" day_getAmount="70,058,497,000"/>
        <DailyStock day_Date="19/05/20" day_EndPrice="118,500" day_Dungrak="5" day_getDebi="2,500" day_Start="121,500" day_High="121,500" day_Low="118,000" day_Volume="449,916" day_getAmount="53,573,016,000"/>
        <DailyStock day_Date="19/05/17" day_EndPrice="121,000" day_Dungrak="2" day_getDebi="2,000" day_Start="120,000" day_High="123,500" day_Low="118,500" day_Volume="439,545" day_getAmount="53,184,383,000"/>
        <DailyStock day_Date="19/05/16" day_EndPrice="119,000" day_Dungrak="5" day_getDebi="1,000" day_Start="120,500" day_High="121,000" day_Low="118,000" day_Volume="484,468" day_getAmount="57,871,810,000"/>
        <DailyStock day_Date="19/05/15" day_EndPrice="120,000" day_Dungrak="5" day_getDebi="2,000" day_Start="121,500" day_High="123,000" day_Low="120,000" day_Volume="460,936" day_getAmount="55,811,604,000"/>
        <DailyStock day_Date="19/05/14" day_EndPrice="122,000" day_Dungrak="5" day_getDebi="500" day_Start="121,000" day_High="123,500" day_Low="119,500" day_Volume="532,037" day_getAmount="64,845,489,250"/>
        <DailyStock day_Date="19/05/13" day_EndPrice="122,500" day_Dungrak="3" day_getDebi="0" day_Start="121,500" day_High="124,000" day_Low="121,500" day_Volume="343,827" day_getAmount="42,101,773,000"/>
        <DailyStock day_Date="19/05/10" day_EndPrice="122,500" day_Dungrak="2" day_getDebi="3,500" day_Start="120,500" day_High="123,000" day_Low="120,000" day_Volume="474,352" day_getAmount="57,723,276,000"/>
        <DailyStock day_Date="19/05/09" day_EndPrice="119,000" day_Dungrak="5" day_getDebi="4,500" day_Start="122,500" day_High="124,000" day_Low="119,000" day_Volume="799,208" day_getAmount="95,972,050,550"/>
    </TBL_DailyStock>

    <TBL_StockInfo
        JongName="NAVER보통주"
        CurJuka="113,000"
        StockDungRak="5"
        Debi="1,500"
        PrevJuka="114,500"
        Volume="521,770"
        Money="58,887,446,500"
        StartJuka="115,000"
        HighJuka="115,000"
        LowJuka="111,000"
        High52="782,000"
        Low52="106,500"
        UpJuka="148,500"
        DownJuka="80,500"
        Per="25.47"
        Amount="164,813,395"
        FaceJuka="100" />

</stockprice>
~~~

즉, 필요한 데이터는 `<TBL_DailyStock>`과 `<TBL_StockInfo>` 단 2개의 테이블이기 때문에 간단하다고 생각했다.  
그런데 이 두 테이블의 모양새가 조금 달랐다.  

`<TBL_DailyStock>`는 최근 10일의 DailyStock 을 가지고 있고, 각 DailyStock 은 9개의 Attribute를 가지고 있는 반면,  
`<TBL_StockInfo>`는 17개의 Attribute만 가지고 있었다.

**JAXB Unmarshalling의 핵심은 이 XML스키마 모양대로 Java 클래스를 생성하면,  
클래스의 각 필드에 해당하는 XML의 데이터가 바인딩되는 것이다.**

그러므로 XML 스키마의 모양새를 정확하게 파악한 후에 Java Class를 생성해야 한다.

## Java Class 생성

우선, 최상위 태그는 `<stockprice>`이므로 아래와 같이 `StockPrice` 클래스를 생성하고,  
JAXB 바인딩 런타임 API가 알 수 있도록 `@XmlRootElement(name = "stockprice")` 어노테이션을 붙여 매핑 정보를 생성했다.

그런데 `<stockprice>`의 경우 Attribute로 `querytime`을 가지고 있고,  
2개의 테이블(`<TBL_DailyStock>`, `<TBL_StockInfo>`)을 내포하고 있다.  

그러므로 querytime, TBL_DailyStock, TBL_StockInfo 를 필드로 만들고 각각  
`@XmlAttribute`와 `@XmlElement` 어노테이션으로 XML 스키마와 모양새를 맞추었다.

그리고 `TBL_DailyStock`과 `TBL_StockInfo`는 내부에 데이터 셋을 가지고 있으므로 Inner Class로 만들고,  
`@XmlRootElement` 어노테이션을 추가했다.

~~~java
/**
 * <stockprice querytime="...">
 *     <TBL_DailyStock></TBL_DailyStock>
 *     <TBL_StockInfo></TBL_StockInfo>
 * </stockprice>
 */
@Getter
@ToString
@XmlRootElement(name = "stockprice")
public class StockPrice {

    @XmlAttribute(name = "querytime")
    private String querytime;

    @XmlElement(name = "TBL_DailyStock")
    private TBL_DailyStock tbl_dailyStock;

    @XmlElement(name = "TBL_StockInfo")
    private TBL_StockInfo tbl_stockInfo;
    
    // 예외처리, 유효성검증 관련 코드 생략

    @Getter
    @ToString
    @XmlRootElement(name = "TBL_DailyStock")
    public static class TBL_DailyStock {

        @XmlElement(name = "DailyStock")
        private List<DailyStock> dailyStocks;
	
	// 예외처리, 유효성검증 관련 코드 생략
    }

    @Getter
    @ToString
    @XmlRootElement(name = "TBL_StockInfo")
    public static class TBL_StockInfo {

        @XmlAttribute(name = "JongName")
        private String jongName;

        @XmlAttribute(name = "CurJuka")
        private String curJuka;

        private String dungRak;

        @XmlAttribute(name = "DungRak")
        public void setDungRak(String dungRak) {

            if("2".equals(dungRak)) {
                this.dungRak = StockDungRak.UP.getName();
            } else if("3".equals(dungRak)) {
                this.dungRak = StockDungRak.FLAT.getName();
            } else if("5".equals(dungRak)) {
                this.dungRak = StockDungRak.DOWN.getName();
            }
        }
	
	// 이하 필드 생략

        // 예외처리, 유효성검증 관련 코드 생략
    }
}
~~~

이 때, 위의 코드의 dungRak 처럼 Setter를 사용하여 데이터를 재가공해야 하는 경우,  
필드 대신 Setter에 @XmlAttribute 어노테이션을 붙여야 데이터가 제대로 바인딩된다.

~~~java
...

@XmlRootElement(name = "TBL_DailyStock")
    public static class TBL_DailyStock {

        @XmlElement(name = "DailyStock")
        private List<DailyStock> dailyStocks;

    }

...
~~~

그런데 위의 코드에서 처럼 DailyStock를 리스트로 가지고 있는 TBL_DailyStock 클래스를 어떻게 생성해야 하는지 조금 헷갈렸었는데,  
DailyStock은 그 자체로 9개의 Attribute를 가지고 있으므로, 아래와 같이 따로 클래스를 생성해야 했다.

~~~java
/**
 * <stockprice>
 *     <TBL_DailyStock>
 *         <DailyStock></DailyStock>
 *         <DailyStock></DailyStock>
 *         ...
 *     </TBL_DailyStock>
 * </stockprice>
 */
@Getter
@ToString
@XmlRootElement(name = "DailyStock")
public class DailyStock {

    @XmlAttribute(name = "day_Date")
    private String day_Date;

    @XmlAttribute(name = "day_EndPrice")
    private String day_EndPrice;

    private String day_getAmount;

    @XmlAttribute(name = "day_getAmount")
    public void setDay_getAmount(String day_getAmount) {

        // 거래대금(백만) 단위 절삭
        Long digit = Long.parseLong(day_getAmount.replaceAll(",", "")) / 1000000;
        this.day_getAmount = String.format("%,d", digit); // "%,d", it tells the method to put comma separator for each 3 digits
    }
    
    // ... 이하 필드 생략
}
~~~

DailyStock은 17개의 Attribute만 내포하고 있으므로, 모든 필드에 @XmlAttribute를 붙인 것을 확인할 수 있다.

## Unmarshalling 실행
>이 예제의 경우 프로젝트 내에서 Map으로만 반환해야 하는 상황이었기 때문에 Map을 사용했지만  
Unmarshalling을 위해 반드시 Map을 사용해야하는 것은 아니다.

XML의 데이터를 바인딩할 Java 클래스를 생성했으니 Unmarshalling을 실행하는 코드를 작성한다.(순서는 상관없다.)  

### Unmarshalling 과정
1. 데이터를 반환할 Map 생성
2. 실시간 시세 XML 데이터를 반환하는 URL을 입력하고, XML 데이터를 String으로 저장한다.
3. `JAXB.newInstance(StockPrice.class)`를 통해 JAXB Context를 생성한다.
4. 생성된 JAXB Context로 Unmarshaller를 생성한다.
5. 생성된 Unmarshaller로 unmarshall 메소드를 호출하여 String에 저장한 XML 데이터를 읽어서 Java 객체(StockPrice)에 바인딩한다.
6. Java 객체를 Map에 담아 화면단에 송출한다.

~~~java
@Controller("/ir_stock")
public Map<String, StockPrice> krxParser(HttpServletRequest request) {

    Map<String, StockPrice> map = new HashMap<>();

    try {
        String html = StockUtil.getHtml("http://asp1.krx.co.kr/servlet/krx.asp.XMLSise?code=035420"); // 035420 = 네이버의 종목코드

        JAXBContext jaxbContext = JAXBContext.newInstance(StockPrice.class); // JAXB Context 생성
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller(); // Unmarshaller Object 생성
        StockPrice stockPrice = (StockPrice) unmarshaller.unmarshal(new StringReader(html)); // unmarshall 메소드 호출

        stockPrice.validation();
        map.put("stockprice", stockPrice);

        return map;

    } catch (JAXBException | IOException e) {
        logger.error("KRX API 예외 발생", e);
        map.put("stockprice", StockPrice.emptyStockPrice()); // 예외 발생 시, 빈 객체 반환
        return map;
    }
}
~~~

위의 코드에서 XML 데이터를 읽어서 String으로 반환해주는 `getHtml()` 메소드는 유틸리티 클래스에 아래와 같이 구현했다.  

~~~java
public static String getHtml(String url) throws IOException {

    URL targetUrl = new URL(url);

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(targetUrl.openStream()))){
        StringBuffer html = new StringBuffer();
        String tmp;

        while ((tmp = reader.readLine()) != null) {
            html.append(tmp);
        }

        return html.toString();
    }
}
~~~

마지막으로, KRX API에서 제공하는 데이터 중 DungRak(등락)의 경우 상승, 동일, 하락을 '2', '3', '5'로 반환한다.  
때문에 숫자만 보고는 상승인지 하락인지 판단할 수 없으므로  
아래와 같이 Enum 클래스를 생성해서 사용하여 의미를 명시했다.

~~~java
/**
 *
 * API에서는
 * 상승 = "2"
 * 변화없음 = "3"
 * 하락 = "5"
 */
public enum StockDungRak {

    FLAT("flat"),
    UP("up"),
    DOWN("down");

    private String name;

    StockDungRak(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
~~~

## 결과

해당 페이지에 접속하면 아래와 같은 정보를 제공할 수 있게 되었다.  

<p align="center">
  <img width="700" src="https://github.com/Integerous/TIL/blob/master/ETC/images/StockInfo.png?raw=true">
</p>

XML 데이터를 Java 객체로 변환하여 사용해보니, (비즈니스 로직이 섞여있어 이 글에는 담지 못했지만)  
확실히 예외 처리와 데이터 가공을 능동적으로 할 수 있어서 좋았다.


## *Reference
- [JAXB Users Guide](https://javaee.github.io/jaxb-v2/doc/user-guide/ch03.html)
- [JAXB](https://www.ibm.com/support/knowledgecenter/ko/SSEQTP_9.0.0/com.ibm.websphere.base.doc/ae/cwbs_jaxb.html)
- [JAXB를 활용한 Java 객체의 XML 자동 변환](http://www.mimul.com/pebble/default/2010/03/23/1269343140000.html)
