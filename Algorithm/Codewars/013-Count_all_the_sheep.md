# Count all the sheep on farm in the heights of New Zealand
>7kyu  
>solved with Java  
>http://www.codewars.com/kata/count-all-the-sheep-on-farm-in-the-heights-of-new-zealand/java

## Instructions
An isogram is a word that has no repeating letters, consecutive or non-consecutive. Implement a function that determines whether a string that contains only letters is an isogram. Assume the empty string is an isogram. Ignore letter case.
~~~
isIsogram "Dermatoglyphics" == true
isIsogram "moose" == false
isIsogram "aba" == false
~~~

# My Solution
~~~java
public class Kata {
  public static int lostSheeps(int[] fridayNightCounting, int[] saturdayNightCounting, int sheepsTotal) {
    
    int friSum = 0;
    int SatSum = 0;
    
    for(int x : fridayNightCounting){
      friSum += x;
    }
    
    for(int y : saturdayNightCounting){
      SatSum += y;
    }
    
    return sheepsTotal - friSum - SatSum;
  }
}
~~~

## Result & Review
>Time: 2036ms
- Best Practice 코드 처럼 변수는 count와 num 두 개면 충분한데 난 4개난 만들었다.
- 쉬운 문제였다.

## Best Practice
~~~java
public class Kata {
  public static int lostSheeps(int[] fridayNightCounting, int[] saturdayNightCounting, int sheepsTotal) 
  {
    int count = 0;
    for(int num : fridayNightCounting)
    {
      count += num;
    }
    for(int num : saturdayNightCounting)
    {
      count+= num;
    }
     return sheepsTotal - count;
  }
}
~~~

## Clever
~~~java
import java.util.*;

public class Kata {
  public static int lostSheeps(int[] f, int[] s, int t) {
    return t - Arrays.stream(f).sum() - Arrays.stream(s).sum();
  }
}
~~~





