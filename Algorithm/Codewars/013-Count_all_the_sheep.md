# Count all the sheep on farm in the heights of New Zealand
>7kyu  
>solved with Java  
>http://www.codewars.com/kata/count-all-the-sheep-on-farm-in-the-heights-of-new-zealand/java

## Instructions
Every week (Friday and Saturday night), the farmer and his son count amount of sheep returned to the yard of their farm.

They count sheep on Friday night, the same goes for Saturday (suppose that sheep returned on Friday are not feeding back on hills on Saturday).

As sheep are not coming in one flock, you will be given two arrays (one for each night) representing number of sheep as they were returning to the yard during the evenings (entries are positive ints, higher than zero).

Farmer and his son know the total amount of their sheep, you will be given this number as third parameter.

Your goal is to calculate the amount of sheep lost (not returned) to the farm after Saturday night counting.
~~~
Example 1: Input: {1, 2}, {3, 4}, 15 --> Output: 5
Example 2: Input: {3, 1, 2}, {4, 5}, 21 --> Output: 6
~~~
Good luck! :-)

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





