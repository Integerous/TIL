# Try-with-resources를 이용한 자원해제 처리

> 코드 리뷰를 받던 중 JDK1.7부터 추가된 Try-with-resources를 알게 되어 정리

코드 리뷰를 받기 전 코드는 아래와 같았다.  

~~~java
public static String getHtml(String url) {

    try{
        URL targetUrl = new URL(url);
        BufferedReader reader = new BufferedReader(new InputStreamReader(targetUrl.openStream()));
        StringBuffer html = new StringBuffer();
        String tmp = "";

        while ((tmp = reader.readLine()) != null) {
            html.append(tmp);
        }
        reader.close(); // 이 부분 전에 예외가 발생하면 BufferedReader를 닫지 못하고 catch문으로 빠지는 문제
        return html.toString();

    } catch (MalformedURLException e) {
        reader.close(); // 초기에는 추가하지 않았던 코드
        e.printStackTrace();
        throw e;
    } catch (IOException e) {
        reader.close(); // 초기에는 추가하지 않았던 코드
        e.printStackTrace();
        throw e;
    } catch (Exception e) {
        reader.close(); // 초기에는 추가하지 않았던 코드
        e.printStackTrace();
        throw e;
    }
~~~

한국거래소 API를 사용하기 위해 url을 입력받아 해당 리소스의 html을 String으로 반환하는 메소드를 작성했다.  
이 ***똥코드***는 보기만해도 지저분했는데,  
지저분한 것은 차치하고, `reader.close()`에 도달하기 전에 예외가 발생할 경우를 대비하지 않은 코드였다.  

그래서 위의 코드와 같이 `reader.close()`를 모든 catch문에 추가하거나,  
아래의 예시와 같이 finally문을 만들어서 처리했어야 했다.

~~~java
SomeResource resource = null;
try {
    resource = getResource();
    use(resource);
} catch(...) {
    ...
} finally {
    if (resource != null) {
        try { resource.close(); } catch(...) { /* 아무것도 안 함 */ }
    }
}

(출처: 최범균님 블로그)
~~~

하지만 나의 ***똥코드***를 보신 팀원분께서 JDK7 부터 추가된 `try-with-resources`를 설명해주셨다.

## 그래서 `Try-with-resources` 는 무엇?

`Try-with-resources`는 아래의 코드와 같이  
**try에 자원 객체를 전달하면, try 코드 블록이 끝나면 자동으로 자원을 종료해주는 기능**이다.

즉, 따로 finally 블록이나 모든 catch 블록에 종료 처리를 하지 않아도 된다.

~~~java
try (SomeResource resource = getResource()) {
    use(resource);
} catch(...) {
    ...
}
~~~

그런데 이 때, try에 전달할 수 있는 자원은 `AutoCloseable` 인터페이스의 구현체로 한정된다.

>`AutoCloseable`은 JDK1.7부터 추가된 인터페이스다.

~~~java
/**
 * @author Josh Bloch
 * @since 1.7
 */
public interface AutoCloseable {
    void close() throws Exception;
}
~~~

그리고 아래와 같이 `try()` 안에 복수의 자원 객체를 전달할 수 있다. 

~~~java
try(Something1 s1 = new Something1();
    Something2 s2 = new Something2()) {

} catch(...) {
    ...
}
~~~


내가 작성한 코드에서는 `BufferedReader`와 `InputStreamReader`클래스가 추상클래스 `Reader`를 상속받았고,  
`Reader`는 `Closeable` 인터페이스를 상속받았으며,  
`Closeable` 인터페이스는 `AutoCloseable`인터페이스를 상속받았다.


때문에 `try-with-resources`를 활용하여 아래와 같이 내가 작성했던 코드를 개선할 수 있었다.

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

코드리뷰를 받으며 배운 것이 많아 행복한 하루였다 :)

### Reference
- 최중현 선임님!
- [자바7에서 마음에 드는 다섯 가지](https://javacan.tistory.com/entry/my-interesting-java7-five-features)
- [중첩 Try with resources는 어떻게 동작할까?](https://multifrontgarden.tistory.com/192)
- [Do I need to close() both FileReader and BufferedReader?](https://stackoverflow.com/questions/1388602/do-i-need-to-close-both-filereader-and-bufferedreader)
