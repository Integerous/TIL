# Row Weights
>7kyu  
>passed with java 
>https://www.codewars.com/kata/5abd66a5ccfd1130b30000a9/java

## Instructions
### Scenario
Several people are standing in a row divided into two teams.
The first person goes into team 1, the second goes into team 2, the third goes into team 1, and so on.

### Task 
Given an array of positive integers (the weights of the people), return a new array/tuple of two integers, where the first one is the total weight of team 1, and the second one is the total weight of team 2.

### Notes 
Array size is at least 1.
All numbers will be positive.

### Input >> Output Examples
~~~
1- rowWeights([13, 27, 49])  ==>  return (62, 27)
~~~
Explanation:  
The first element 62 is the total weight of team 1, and the second element 27 is the total weight of team 2.

~~~
2- rowWeights([50, 60, 70, 80])  ==>  return (120, 140)
~~~
Explanation:  
The first element 120 is the total weight of team 1, and the second element 140 is the total weight of team 2.

~~~
3- rowWeights([80])  ==>  return (80, 0)
~~~
Explanation:  
The first element 80 is the total weight of team 1, and the second element 0 is the total weight of team 2.

# My Solution
~~~java
public class Solution {
    public static int[] rowWeights (final int[] weights) {
      
      int result1 = 0;
      int result2 = 0;
      
      for(int i=0; i<weights.length; i++){
        if(i%2 == 0)
          result1 += weights[i];
        else
          result2 += weights[i];
      }
      
      return new int[]{result1, result2}; // Do your magic!
    }
}
~~~

## Result & Review
>Time : 2327 ms
- 쉬운 문제였다.

## Best Practice
~~~java
public class Solution {
  public static int[] rowWeights (final int[] weights) {
    int totals[] = new int[2], idx = 0;
    for (int w : weights) totals[(idx++)%2] += w;
    return totals;
  }
  
}
~~~
