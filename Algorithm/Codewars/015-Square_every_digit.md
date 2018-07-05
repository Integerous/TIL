# Square Every Digit
>7kyu  
>solved with Java  
>http://www.codewars.com/kata/square-every-digit/java

## Instructions
Description:
Welcome. In this kata, you are asked to square every digit of a number.

For example, if we run 9119 through the function, 811181 will come out, because 92 is 81 and 12 is 1.

Note: The function accepts an integer and returns an integer.

# My Solution
~~~java
public class SquareDigit {

  public int squareDigits(int n) {
    
   //String num = n + "";
   //String num = String.valueOf(n);
   String num = Integer.toString(n);
   
   StringBuilder sb = new StringBuilder();
   
   char[] array = num.toCharArray();
   
   for(char c : array){
     //x = Character.getNumericValue(c);
     int x = c -'0';
     x *= x;
     sb.append(x);
   }
   
   return Integer.parseInt(sb.toString());
   
  }

}
~~~

## Result & Review
>Time : 1885ms
- StringBuilder를 처음으로 사용해보았다. 좋다.
- Best Practice 코드는 보기에 깔끔하지만 String 객체 사용으로 퍼포먼스가 떨어지는 것 같다.
  - Java에서 String은 immutable(불변)의 속성을 가진다. 때문에 새로운 문자열(String)을 더할 때마다 새로운 인스턴스를 생성하기 때문에 비효율적이다.
- Clever#1 코드를 보니 Lambda를 빨리 공부해야겠다.
- Clever#2 코드가 arithmetic한 해결책이라 가장 옳은 답이라고들 한다.
- Clever#3 코드를 보며 Recursion을 사용해야 하는 상황을 익히면 좋을 것 같다.

## Best Practice
~~~java
public class SquareDigit {

  public int squareDigits(int n) {
    String result = ""; 
    
    while (n != 0) {
      int digit = n % 10 ;
      result = digit*digit + result ;
      n /= 10 ;
    }
    
    return Integer.parseInt(result) ;
  }

}
~~~

## Clever #1
~~~java
import java.util.stream.Collectors;

public class SquareDigit {

    public int squareDigits(int n) {
        return Integer.parseInt(String.valueOf(n)
                                      .chars()
                                      .map(i -> i-'0')
                                      .map(i -> i * i)
                                      .mapToObj(String::valueOf)
                                      .collect(Collectors.joining("")));
    }

}
~~~

## Clever #2
~~~java
public class SquareDigit {
  private static final int BASE = 10;
  
  public int squareDigits(int n) {
    if (n < BASE) {
      return n * n;
    }
    int digit = n % BASE;
    int squaredDigit = digit * digit;
    return squaredDigit + (squaredDigit < BASE ? BASE : BASE * BASE) * squareDigits(n / BASE);
  }
}
~~~

## Clever #3
~~~java
public class SquareDigit {

  public int squareDigits(int n) {
      if (n < 10) return n * n;
      else {
        int h = squareDigits(n / 10);
        int l = n % 10;
        return Integer.parseInt(h + "" + l * l);
      }
  }

}
~~~



