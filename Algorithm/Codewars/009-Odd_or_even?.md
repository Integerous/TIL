# Odd or Even?
>7kyu  
>solved with Java  
>http://www.codewars.com/kata/odd-or-even/java

## Instructions
Task:
Given an array of numbers (a list in groovy), determine whether the sum of all of the numbers is odd or even.

Give your answer in string format as 'odd' or 'even'.

If the input array is empty consider it as: [0] (array with a zero).

Example:
`oddOrEven([2, 5, 34, 6]) returns "odd".`    
Have fun!
# My Solution
~~~java
public class Codewars {
  public static String oddOrEven (int[] array) {
  
    int length = array.length;
    int sum = 0;
    
    for(int i=0; i<length; i++) {
      sum += array[i];
    }
    
    if(sum%2==0)
      return "even";
    else
      return "odd";
  
  }
}
~~~

## Result & Review
>Time: 1885ms
- Best Practice를 보니 stream 사용법을 공부해야겠다.
- 0은 짝수라고 생각하고 풀었다.

## Best Practice
~~~java
import static java.util.Arrays.stream;

class Codewars {
    static String oddOrEven(final int[] array) {
        return stream(array).sum() % 2 == 0 ? "even" : "odd";
    }
}
~~~

## Clever
~~~java
public class Codewars {
  public static String oddOrEven (int[] array) {
    int sum = 0;
    for (int n : array){
      sum += n;
    }
    return sum%2==0 ? "even" : "odd";
  }
}
~~~

