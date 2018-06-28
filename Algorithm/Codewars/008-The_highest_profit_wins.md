# The Highest Profit Wins!
>7kyu  
>solved with Java  
>http://www.codewars.com/kata/the-highest-profit-wins/java

## Instructions
Description:  
Story  
Ben has a very simple idea to make some profit: he buys something and sells it again. Of course, this wouldn't give him any profit at all if he was simply to buy and sell it at the same price. Instead, he's going to buy it for the lowest possible price and sell it at the highest.

Task  
Write a function that returns both the minimum and maximum number of the given list/array.

Examples
~~~
MinMax.minMax(new int[]{1,2,3,4,5}) == {1,5}
MinMax.minMax(new int[]{2334454,5}) == {5, 2334454}
MinMax.minMax(new int[]{1}) == {1, 1}
~~~
Remarks  
All arrays or lists will always have at least one element, so you don't need to check the length. Also, your function will always get an array or a list, you don't have to check for null, undefined or similar.

# My Solution
~~~java
class MinMax {
    public static int[] minMax(int[] arr) {
        
        int min = arr[0];
        int max = arr[0];
        
        for(int i=1; i<arr.length; i++) {
          if( arr[i] > max)
            max = arr[i];
          if( arr[i] < min)
            min = arr[i];
        }
            
        int[] result = new int[2];
            result[0] = min;
            result[1] = max;
            
        return result;
    }
}
~~~

## Result & Review
>Time: 1836ms
- for문 안에 if문을 여러 번 써도 된다는 사실을 잊어서 조금 돌아왔다.
- Best Practice처럼 Arrays.sort(arr) 사용시 시간복잡도 O(NlogN)으로 비효율적이다.
- `return new int[]{min, max}`처럼 반환값에 선언해주면 코드가 더 간결해질 수 있었다.

## Best Practice
~~~java
import java.util.Arrays;

class MinMax {
    public static int[] minMax(int[] arr) {
        // Your awesome code here
         Arrays.sort(arr);
        return new int[]{arr[0],arr[arr.length-1]};
    }
}
~~~

## Clever
~~~java
import java.util.*;
class MinMax {
    public static int[] minMax(int[] arr) {
       return new int[]{Arrays.stream(arr).min().getAsInt(), Arrays.stream(arr).max().getAsInt()};
    }
}
~~~

