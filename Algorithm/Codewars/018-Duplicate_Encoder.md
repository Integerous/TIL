# Duplicate Encoder
>6kyu  
>failed  
>https://www.codewars.com/kata/duplicate-encoder/java

## Instructions
The goal of this exercise is to convert a string to a new string where each character in the new string is '(' if that character appears only once in the original string, or ')' if that character appears more than once in the original string. Ignore capitalization when determining if a character is a duplicate.

Examples:
~~~
"din" => "((("

"recede" => "()()()"

"Success" => ")())())"

"(( @" => "))(("
~~~  

# My Solution
~~~java
시간 내에 못품
~~~

## Result & Review
>Time out! 실패!
- 우선 생각나는대로 for문을 중첩해서 풀어보고 더 나은 방법을 찾아보고자 했다. 하지만 for문 중첩으로도 풀지 못하고 시간 초과로 실패!
- ASCII 코드와 toCharArray()에 대해 제대로 공부할 수 있는 기회였다.
- Best Practice로 뽑힌 코드가 보기에는 좋지만 O(n^2)라는 의견들이 있어, O(n)인 코드를 Best Practice로 선정했다.
- Clever 코드는 비록 O(n^2)지만, 내 뒤통수를 때리는 코드였다.

## Best Practice
~~~java
public class DuplicateEncoder {
	static String encode(String word){
    
  word = word.toLowerCase();
  
  int[] count = new int[256];
  
  for(char c : word.toCharArray()){
    count[c]++;
  }
  
  StringBuilder result = new StringBuilder();
  
  for(char c : word.toCharArray()){
    if(count[c]>1) result.append(")");
    else result.append("(");
  }
    return result.toString();
  }
}
~~~

## Clever
~~~java
public class DuplicateEncoder {
  static String encode(String word){
    word = word.toLowerCase();
    String result = "";
    for (int i = 0; i < word.length(); ++i) {
      char c = word.charAt(i);
      result += word.lastIndexOf(c) == word.indexOf(c) ? "(" : ")";
    }
    return result;
  }
}
~~~
