# Duplicate Encoder
>6kyu  
>passed with java 
>https://www.codewars.com/kata/your-order-please/java

## Instructions
Your task is to sort a given string. Each word in the String will contain a single number. This number is the position the word should have in the result.

Note: Numbers can be from 1 to 9. So 1 will be the first word (not 0).

If the input String is empty, return an empty String. The words in the input String will only contain valid consecutive numbers.

For an input: "is2 Thi1s T4est 3a" the function should return "Thi1s is2 3a T4est"

Examples:
~~~
your_order("is2 Thi1s T4est 3a")
[1] "Thi1s is2 3a T4est"
~~~  

# My Solution
~~~java
public class Order {
  public static String order(String words) {
 
  if(words == "") return "";
  
// 1. 문장의 숫자만 추출하여 문자열로 생성
  String nums = words.replaceAll("[^0-9]", "");

// 2. 띄어쓰기로 단어 구분해서 배열에 저장
  String[] ws = words.split(" ");
  
// 3. 배열 길이 저장
  int length = ws.length;

// 4. 결과값을 저장할 배열 생성
  String[] result = new String[length];
  
// 5. 배열 내 순서 변경
  for(int i=0; i<length; i++){
    result[Character.getNumericValue(nums.charAt(i))-1] = ws[i];
  }
  
// 6. 결과값 출력 (String.join 메서드 사용 가능)
  return String.join(" ", result);
  
  }
}
~~~

## Result & Review
>Time : 2019 ms
- 오랜만에 문제를 풀어서 그런지 다음 사항들을 검색해야했다.
  - 문자열에서 숫자만 추출하기 `replaceAll()`
  - split()함수가 배열로 반환되는지 `배열로 반환`
  - 배열 길이 구하기 `배열이름.length`
  - Char > int 형변환 `Character.getNumericValue()`
  - String.join 함수 `String.join(구분자, values)`
- 차근차근 풀어나가는 것이 가장 빠른 방법이라는 것을 다시 느꼈다.

## Best Practice
~~~java
import java.util.Arrays;
import java.util.Comparator;

public class Order {
  public static String order(String words) {
    return Arrays.stream(words.split(" "))
      .sorted(Comparator.comparing(s -> Integer.valueOf(s.replaceAll("\\D", ""))))
      .reduce((a, b) -> a + " " + b).get();
  }
}
~~~

## Clever
~~~java
import java.util.Arrays;
public class Order {
  public static String order(String words) {
        String[] strs = words.split(" ");
        Arrays.sort(strs, (String s1, String s2) -> s1.replaceAll("[a-zA-Z]","").compareTo(s2.replaceAll("[a-zA-Z]",""))  );
        String f = "";
        for(String st:strs) f+=st + " ";
        return f.substring(0,f.length()-1);
    }
}
~~~
