# Find The Odd Int
>6kyu  
>failed with Java  
>http://www.codewars.com/kata/find-the-odd-int/java

## Instructions
Given an array, find the int that appears an odd number of times.

There will always be only one integer that appears an odd number of times.

# My Solution
~~~java
public class FindOdd {
	public static int findIt(int[] a) {

   
      for(int i=0; i<a.length; i++){
        int count = 1;
          for(int j=i+1; j<a.length; j++){
            if(a[i] == a[j]){
              count++;
            }
          }
          if(count % 2 == 1){
            return a[i];
          }
      }  

 return a[0];
  }
}
~~~

## Result & Review
>Time-out! 시간 초과로 실패!
- 내 코드가 왜 틀린지 잘 모르겠다. 하지만 매우 구린 코드인 것은 분명하다.
- Best Practice 코드는 XOR을 이용했다. 대박이다.

## Best Practice
~~~java
public class FindOdd {
  public static int findIt(int[] A) {
    int odd=0;
    for (int item: A)
      {
        odd = odd ^ item;
      }
    
    return odd;
  }
}
~~~

## Clever
~~~java
import static java.util.Arrays.stream;

public class FindOdd {
  public static int findIt(int[] arr) {
    return stream(arr).reduce(0, (x, y) -> x ^ y);
  }
}
~~~





