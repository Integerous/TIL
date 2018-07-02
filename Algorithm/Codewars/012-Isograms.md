# Isograms
>7kyu  
>solved with Java  
>http://www.codewars.com/kata/isograms/java

## Instructions
An isogram is a word that has no repeating letters, consecutive or non-consecutive. Implement a function that determines whether a string that contains only letters is an isogram. Assume the empty string is an isogram. Ignore letter case.
~~~
isIsogram "Dermatoglyphics" == true
isIsogram "moose" == false
isIsogram "aba" == false
~~~

# My Solution
~~~java
public class isogram {
    public static boolean  isIsogram(String str) {
        
    char[] array  = str.toLowerCase().toCharArray();
    boolean answer = true;
    
    for(int i=0; i<array.length; i++){
        for(int j=i+1; j<array.length; j++){
          if(array[i] == array[j])
            answer = false;
        }
    }
          return answer;
 }         
}
~~~

## Result & Review
>Time: 2005ms
- 처음에 j=i+1 이 아닌 j=1로 풀어서 오류가 발생했다.
- for문이 중첩되었으므로 좋은 코드는 아닌 것 같다.

## Best Practice
~~~java
public class isogram {
  public static boolean  isIsogram(String str) {
    return str.length() == str.toLowerCase().chars().distinct().count();
  } 
}
~~~

## Clever
~~~java
import java.util.regex.Pattern;
public class isogram {
    public static boolean  isIsogram(String str) {
        return !Pattern.compile("(?i)\\b\\w*(\\w)\\w*(?=\\1)\\w*\\b").matcher(str).matches();
    }
}
~~~





