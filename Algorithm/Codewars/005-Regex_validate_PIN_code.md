# Regex Validate PIN Code
>7kyu  
>solved with Java  
>https://www.codewars.com/kata/tribonacci-sequence/java

## Instructions
ATM machines allow 4 or 6 digit PIN codes and PIN codes cannot contain anything but exactly 4 digits or exactly 6 digits.

If the function is passed a valid PIN string, return true, else return false.

eg:
~~~
Solution.validatePin("1234") === true
Solution.validatePin("12345") === false
Solution.validatePin("a234") === false
~~~
# My Solution
~~~java
public class Solution {

  public static boolean validatePin(String pin) {
    // Your code here...
    
    int length = pin.length();
    
    if(digitCheck(pin) && (length==4 || length==6))
      return true;
    else
      return false; 
  }  
  
    public static boolean digitCheck(String pin){
      
      try{
        Integer.parseInt(pin);
        return true;
      }catch(NumberFormatException e){
        return false;
        }
    }
}
~~~

## Result & Review
>Time: 2038ms  
- 문제 이름에서 알 수 있듯이 정규식으로 푸는 문제였다. 이참에 정규식을 다시 공부했다. 정규식으로 안푼다면 밑에 Clever 코드가 내 코드보다 나아보인다.

## Best Practice
~~~java
import java.util.regex.*;

public class Solution {

  public static boolean validatePin(String pin) {
    return pin.matches("\\d{4}|\\d{6}");
  }

}
~~~

## Clever
~~~java
public class Solution {

  public static boolean validatePin(String pin) {

        if (pin == null || (pin.length()!= 4 && pin.length()!= 6))
            return false;

        
        try {
            Integer.valueOf(pin);
        }catch (Exception e) {
            return false;
        }

        return true;
    }

}
~~~



