# Most Digits
>7kyu  
>failed with Java  
>http://www.codewars.com/kata/most-digits/java

## Instructions
Find the number with the most digits.

If two numbers in the argument array have the same number of digits, return the first one in the array.

# My Solution
~~~java
public class MostDigits {
  public static int findLongest(int[] numbers) {
    
    int max = numbers[0];
    
    for(int i=0; i<numbers.length; i++){
      if(Math.log10(numbers[i])+1 > Math.log10(max)+1)
        max = numbers[i];
        
      else if(Math.log10(numbers[i])+1 == Math.log10(max)+1)
        max = max;
    }
    
    return max;
  }
}
~~~

## Result & Review
>Time-out! 시간 초과로 실패!
- 음수 값 때문에 Math.log10()는 사용하면 안되었다. Best Practice에서 처럼 절대값을 문자열로 바꿔서 그 길이를 구해서 비교했어야 했다.

## Best Practice
~~~java
public class MostDigits {
  public static int findLongest(int[] numbers) {
    int index = 0;
    for (int i = 0; i < numbers.length; i++) {
      if (String.valueOf(Math.abs(numbers[i])).length() > String.valueOf(Math.abs(numbers[index])).length()) {
        index = i;
      }
    }
    return numbers[index];
  }
}
~~~
