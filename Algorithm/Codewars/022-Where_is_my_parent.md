# Where is my parent!?(cry)
>6kyu  
>failed  
>https://www.codewars.com/kata/where-is-my-parent-cry/java

## Instructions
Mothers arranged dance party for children in school.On that party there are only mothers and their children.All are having great fun on dancing floor when suddenly all lights went out.Its dark night and no one can see eachother.But you were flying nearby and you can see in the dark and have ability to teleport people anywhere you want.  
  
### Legend:
-Uppercase letters stands for mothers,lowercase stand for their children. I.E "A" mothers children are "aaaa".
-Function input:String contain only letters,Uppercase letters are unique.

### Task:
Place all people in alphabetical order where Mothers are followed by their children.I.E "aAbaBb" => "AaaBbb".

# My Solution
~~~java
public class MaxProduct {
  public int adjacentElementsProduct(int[] array) {
    
  시간초과로 풀이 실패!
~~~

## Result & Review
>Time out! 시간 초과로 실패!
- 배운 점
  - `Array.sort()`와 `String.CASE_INSENSITIVE_OREDER`의 존재를 모르고 있었다.
  - 문자열 구분을 `text.charAt(i)`이 아닌 `text.split("")`으로도 할 수 있다.
  - 배열 원소들을 붙일 때에는 `String.join("", 배열이름)` 사용 가능
  
## Best Practice
~~~java
import java.util.Arrays;

class WhereIsMyParent {
    static String findChildren(final String text) {
        String[] sorted = text.split("");
        Arrays.sort(sorted); 
        Arrays.sort(sorted, String.CASE_INSENSITIVE_ORDER);
        return String.join("", sorted);
    }
}
~~~
