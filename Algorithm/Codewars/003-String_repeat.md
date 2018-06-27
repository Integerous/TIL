# Get the middle character
>8kyu  
>solved with Java  
>http://www.codewars.com/kata/string-repeat/java

## Instructions
Write a function called repeatStr which repeats the given string string exactly n times.

repeatStr(6, "I") // "IIIIII"
repeatStr(5, "Hello") // "HelloHelloHelloHelloHello"

# My Solution
~~~java
public class Solution {
    public static String repeatStr(final int repeat, final String string) {
        
        String x = "";
        
        for(int i=0; i<repeat; i++)
          x += string;
        
        return x;
    }
}
~~~

## Result & Review
>Time: 2270ms  
- 내 솔루션은 concatenation(+=)으로 해결하므로 매 iteration 마다 String이 새로 만들어지므로 O(n^2)로 비효율적이다.
  - 여기서 concatenation은 삽입된 string 만큼 시간이 소비되므로 1+2+3+4+...+repeat = repeat*(repeat+1)/2 이므로 O(n^2)이다.
- 밑의 Best Practice는 StringBuilder를 사용해서 O(n)이다.

## Best Practice
~~~java
public class Solution {
    public static String repeatStr(final int repeat, final String string) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < repeat; i++) {
            sb.append(string);
        }

        return sb.toString();
    }
}
~~~

## Clever
~~~java
public class Solution {
  public static String repeatStr(final int repeat, final String string) {
    return java.util.stream.IntStream.range(0, repeat).mapToObj(i -> string)
        .collect(java.util.stream.Collectors.joining()).toString();
  }
}
~~~
