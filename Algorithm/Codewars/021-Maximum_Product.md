# Maximum Product
>7kyu  
>passed with java  
>https://www.codewars.com/kata/maximum-product/java

## Instructions
### Task
Given an array of integers , Find the maximum product obtained from multiplying 2 adjacent numbers in the array.

### Notes
- Array/list size is at least 2 .
- Array/list numbers could be a mixture of positives , ngatives also zeros .

### Input >> Output Examples
~~~java
adjacentElementsProduct(new int[] {1, 2, 3}); ==> return 6
~~~
Explanation:
The maximum product obtained from multiplying 2 * 3 = 6, and they're adjacent numbers in the array.
~~~java
adjacentElementsProduct(new int[] {9, 5, 10, 2, 24, -1, -48}); ==> return 50
~~~
Explanation:
Max product obtained from multiplying 5 * 10 = 50 .
~~~java
adjacentElementsProduct(new int[] {-23, 4, -5, 99, -27, 329, -2, 7, -921}) ==> return -14
~~~
Explanation:
The maximum product obtained from multiplying -2 * 7 = -14, and they're adjacent numbers in the array.

# My Solution
~~~java
public class MaxProduct {
  public int adjacentElementsProduct(int[] array) {
    
    int max = array[0]*array[1];
    
    for(int i=0; i<array.length-1; i++){
      if(max < array[i]*array[i+1])
        max = array[i]*array[i+1];
    }
    return max;
  }
}
~~~

## Result & Review
>Time : 2210 ms
- 쉬운 문제였다.
- 배운 점
  - `Integer.MIN_VALUE`, `Integer.MAX_VALUE`로 정수의 최소값 최대값을 구할 수 있다.
  - `Math.max(비교대상1,비교대상2...)`

## Best Practice
~~~java
public class MaxProduct {
  public int adjacentElementsProduct(int[] array) {
    int ans = Integer.MIN_VALUE;
    for (int i = 0; i < array.length-1; i++)
      ans = Math.max(ans,array[i]*array[i+1]);
    return ans;
  }
}
~~~

## Clever
~~~java
import java.util.stream.IntStream;

public class MaxProduct {
  public int adjacentElementsProduct(int[] array) {
    
    return IntStream.range(0, array.length - 1)
                    .map(x -> array[x]*array[x+1])
                    .max()
                    .orElse(Integer.MIN_VALUE);
  }
}
~~~
