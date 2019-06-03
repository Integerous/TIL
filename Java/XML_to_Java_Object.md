# XML 데이터의 Java 객체 변환 (feat. JAXB Unmarshal)

>KRX(한국거래소)의 주가정보 API를 사용할 일이 있었다.


작성중


~~~java
@Controller("/ir_stock")
public Map<String, StockPrice> krxParser(HttpServletRequest request) {

    Map<String, StockPrice> map = new HashMap<>();

    try {
        String html = StockUtil.getHtml("http://asp1.krx.co.kr/servlet/krx.asp.XMLSise?code=035420");

        JAXBContext jaxbContext = JAXBContext.newInstance(StockPrice.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StockPrice stockPrice = (StockPrice) unmarshaller.unmarshal(new StringReader(html));

        stockPrice.validation();
        map.put("stockprice", stockPrice);

        return map;

    } catch (JAXBException | IOException e) {
        logger.error("KRX API 예외 발생", e);
        map.put("stockprice", StockPrice.emptyStockPrice());
        return map;
    }
}
~~~

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


~~~java
/**
 * KRX(한국거래소) 주가정보 API
 *
 * <stockprice>
 *     <TBL_DailyStock></TBL_DailyStock>
 *     <TBL_StockINfo></TBL_StockINfo>
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

    public StockPrice() {
        this.querytime = "";
        this.tbl_dailyStock = new TBL_DailyStock();
        this.tbl_stockInfo = new TBL_StockInfo();
    }

    // 빈 객체 반환
    public static StockPrice emptyStockPrice() {
        return new StockPrice();
    }

    // 유효성 검증
    public void validation() throws ValidationException {
        if(ObjectUtils.isEmpty(tbl_dailyStock.dailyStocks)
                || StringUtils.isEmpty(tbl_stockInfo.getJongName())) {

            throw new ValidationException("종목코드 오류");
        }
    }

    @Getter
    @ToString
    @XmlRootElement(name = "TBL_DailyStock")
    public static class TBL_DailyStock {

        @XmlElement(name = "DailyStock")
        private List<DailyStock> dailyStocks;

        // 예외 발생시 빈 객체 반환
        public TBL_DailyStock() {
            dailyStocks = new ArrayList<>();
        }
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

        @XmlAttribute(name = "Debi")
        private String debi;

        //전일 대비 증감율
        private String variation;

        private String prevJuka;

        @XmlAttribute(name = "PrevJuka")
        public void setPrevJuka(String prevJuka) {
            this.prevJuka = prevJuka;

            // 전일대비 증감율 추가
            int diff = Integer.parseInt(debi.replaceAll(",",""));
            int yesterdayJuka = Integer.parseInt(prevJuka.replaceAll(",", ""));
            double fullDigit = (diff / (double) yesterdayJuka) * 100;

            //소수점 이하 마지막 0 절삭
//            double result = Math.round(fullDigit * 100) / 100.0;
            //소수점 이하 0 유지
            String result = String.format("%.2f", fullDigit);

            // 증감율 앞에 +/- 추가
            if("up".equals(dungRak)) {
                this.variation = "+" + result;
            } else if("flat".equals(dungRak)) {
                this.variation = "" + result;
            } else if("down".equals(dungRak)) {
                this.variation = "-" + result;
            }
        }

        @XmlAttribute(name = "Volume")
        private String volume;

        @XmlAttribute(name = "Money")
        private String money;

        @XmlAttribute(name = "StartJuka")
        private String startJuka;

        @XmlAttribute(name = "HighJuka")
        private String highJuka;

        @XmlAttribute(name = "LowJuka")
        private String lowJuka;

        @XmlAttribute(name = "High52")
        private String high52;

        @XmlAttribute(name = "Low52")
        private String low52;

        @XmlAttribute(name = "UpJuka")
        private String upJuka;

        @XmlAttribute(name = "DownJuka")
        private String downJuka;

        @XmlAttribute(name = "Per")
        private String per;

        @XmlAttribute(name = "Amount")
        private String amount;

        @XmlAttribute(name = "FaceJuka")
        private String faceJuka;

        public TBL_StockInfo() {
            this.jongName = "";
            this.curJuka = "";
            this.dungRak = "";
            this.debi = "";
            this.variation = "";
            this.prevJuka = "";
            this.volume = "";
            this.money = "";
            this.startJuka = "";
            this.highJuka = "";
            this.lowJuka = "";
            this.high52 = "";
            this.low52 = "";
            this.upJuka = "";
            this.downJuka = "";
            this.per = "";
            this.amount = "";
            this.faceJuka = "";
        }
    }
}


/*
<stockprice querytime="2019-05-22 15:19:56">

    <TBL_DailyStock>
        <DailyStock
            day_Date="19/05/22"
            day_EndPrice="113,000"
            day_Dungrak="5"
            day_getDebi="1,500"
            day_Start="115,000"
            day_High="115,000"
            day_Low="111,000"
            day_Volume="521,770"
            day_getAmount="58,887,446,500"/>
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
*/
~~~


~~~java
/**
 * KRX(한국거래소) 주가정보 API
 *
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

    private String day_Date;

    @XmlAttribute(name = "day_Date")
    public void setDay_Date(String day_Date) {
        this.day_Date = day_Date.replaceAll("/", ".");
    }

    @XmlAttribute(name = "day_EndPrice")
    private String day_EndPrice;

    private String day_Dungrak;

    @XmlAttribute(name = "day_Dungrak")
    public void setDay_Dungrak(String day_Dungrak) {

        if("2".equals(day_Dungrak)) {
            this.day_Dungrak = StockDungRak.UP.getName();
        } else if("3".equals(day_Dungrak)) {
            this.day_Dungrak = StockDungRak.FLAT.getName();
        } else if("5".equals(day_Dungrak)) {
            this.day_Dungrak = StockDungRak.DOWN.getName();
        }
    }

    @XmlAttribute(name = "day_getDebi")
    private String day_getDebi;

    @XmlAttribute(name = "day_Start")
    private String day_Start;

    @XmlAttribute(name = "day_High")
    private String day_High;

    @XmlAttribute(name = "day_Low")
    private String day_Low;

    @XmlAttribute(name = "day_Volume")
    private String day_Volume;

    private String day_getAmount;

    @XmlAttribute(name = "day_getAmount")
    public void setDay_getAmount(String day_getAmount) {

        // 거래대금(백만) 단위 절삭
        Long digit = Long.parseLong(day_getAmount.replaceAll(",", "")) / 1000000;
        this.day_getAmount = String.format("%,d", digit); // "%,d", it tells the method to put comma separator for each 3 digits
                                                          // 출처 : http://javadevnotes.com/java-integer-to-string-with-commas
    }
}
~~~
        
        
~~~java
/**
 * KRX(한국거래소) 주가정보 API 용 enum
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

~~~ftl
<div class="stock_main_info">
				<div class="info_type_01">
					<div class="title">
						<p class="row">
							<span class="name">줌인터넷</span>
							<span class="num">005930</span>
						</p>
						<span class="date">${stockprice.querytime} 기준</span>
					</div>
					<div class="figure">
						<span class="num">${stockprice.tbl_stockInfo.curJuka}</span>
						<span>전일대비</span>
						<!-- class rate +
                            1. up, 상승
                            2. down, 하락
                            3. (class 없음), 보합
                        -->
						<span <#if (stockprice.tbl_stockInfo.dungRak == "up")>class="rate up"
							  <#elseif (stockprice.tbl_stockInfo.dungRak == "flat")>class="rate"
							  <#elseif (stockprice.tbl_stockInfo.dungRak == "down")>class="rate down"
							      </#if>>
                            ${stockprice.tbl_stockInfo.debi}(${stockprice.tbl_stockInfo.variation}%)
                            </span>
					</div>
				</div>

				<div class="info_type_02">
					<p>
						<span class="label">시가(원)</span>
						<span>${stockprice.tbl_stockInfo.startJuka}</span>
					</p>
					<p>
						<span class="label">고가(원)</span>
						<span>${stockprice.tbl_stockInfo.highJuka}</span>
					</p>
					<p>
						<span class="label">저가(원)</span>
						<span>${stockprice.tbl_stockInfo.lowJuka}</span>
					</p>
					<p>
						<span class="label">거래량(주)</span>
						<span>${stockprice.tbl_stockInfo.volume}</span>
					</p>
				</div>

				<div class="info_type_03">
					<div class="wrap_group">
						<div class="group g_01">
							<p class="row r_01">
								<span class="label">상한가</span>
								<span>${stockprice.tbl_stockInfo.upJuka}</span>
							</p>
							<p class="row r_02">
								<span class="label">하한가</span>
								<span>${stockprice.tbl_stockInfo.downJuka}</span>
							</p>
						</div>
						<div class="group g_02">
							<p class="row r_01">
								<span class="label">액면가</span>
								<span>${stockprice.tbl_stockInfo.faceJuka}</span>
							</p>
							<p class="row r_02">
								<span class="label">PER</span>
								<span>${stockprice.tbl_stockInfo.per}</span>
							</p>
						</div>
						<div class="group g_03">
							<p class="row r_01">
								<span class="label">52주<span class="detail">(종가기준)</span> 최고</span>
								<span>${stockprice.tbl_stockInfo.high52}</span>
							</p>
							<p class="row r_02">
								<span class="label">52주<span class="detail">(종가기준)</span> 최저</span>
								<span>${stockprice.tbl_stockInfo.low52}</span>
							</p>
						</div>
					</div>
					<div class="group g_04">
						<p class="row r_01">
							<span class="label">거래대금</span>
							<span>${stockprice.tbl_stockInfo.money}</span>
						</p>
						<p class="row r_02">
							<span class="label">상장주식수</span>
							<span>${stockprice.tbl_stockInfo.amount}</span>
						</p>
					</div>
				</div>
			</div>

			<div class="stock_table">
				<table cellspacing="0" cellpadding="0">
					<caption>줌인터넷 날짜별 주가를 보여주는 표</caption>
					<colgroup>
						<col style="width:9%">
						<col style="width:12%">
						<col style="width:12%">
						<col style="width:12%">
						<col style="width:12%">
						<col style="width:12%">
						<col style="width:15%">
						<col style="width:16%">
					</colgroup>
					<thead>
					<tr>
						<th class="txt_left">일자</th>
						<th>종가</th>
						<th>전일대비</th>
						<th>시가</th>
						<th>고가</th>
						<th>저가</th>
						<th>거래량</th>
						<th>거래대금(백만)</th>
					</tr>
					</thead>
					<tbody>
					<#list stockprice.tbl_dailyStock.dailyStocks as list>
						<tr>
							<td class="txt_left">${list.day_Date}</td>
							<td>${list.day_EndPrice }</td>
							<!-- class rate +
                                1. up, 상승
                                2. down, 하락
                                3. (class 없음), 보합
                            -->
							<td class="point">
								<i <#if (list.day_Dungrak == "up")>class="icon up"
								   <#elseif (list.day_Dungrak == "flat")>class="icon"
								   <#elseif (list.day_Dungrak == "down")>class="icon down"
										</#if>>
								</i>
								${list.day_getDebi }
							</td>
							<td>${list.day_Start}</td>
							<td>${list.day_High}</td>
							<td>${list.day_Low}</td>
							<td>${list.day_Volume}</td>
							<td>${list.day_getAmount }</td>
						</tr>
					</#list>
					</tbody>
				</table>
			</div>
~~~
