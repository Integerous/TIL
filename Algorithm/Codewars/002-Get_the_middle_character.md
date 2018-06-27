![codewars](https://www.codewars.com/users/Integerous/badges/large)
# Get the middle character
>7kyu  
>solved with Java  
>https://www.codewars.com/kata/get-the-middle-character/java

## Instructions
You are going to be given a word. Your job is to return the middle character of the word. If the word's length is odd, return the middle character. If the word's length is even, return the middle 2 characters.

#Examples:
~~~
Kata.getMiddle("test") should return "es"

Kata.getMiddle("testing") should return "t"

Kata.getMiddle("middle") should return "dd"

Kata.getMiddle("A") should return "A"
~~~
#Input

A word (string) of length 0 < str < 1000 (In javascript you may get slightly more than 1000 in some test cases due to an error in the test cases). You do not need to test for this. This is only here to tell you that you do not need to worry about your solution timing out.

#Output

The middle character(s) of the word represented as a string.

# My Solution
~~~java
class Kata {
  public static String getMiddle(String word) {
    //Code goes here!

    String x = "";    
    int length = word.length();
    
      if(length%2==0){
        x = word.substring((length/2)-1,(length/2)+1);
      }else{
        x = word.substring((length/2), (length/2)+1);
      }
    return x;
    
  }
}
~~~

## Result & Review
>Passed (Time: 2139ms)  
>밑에 Best Practice 처럼 변수를 만들어서 length/2를 담았으면 더 보기에 깔끔했을 것 같다.  
>길이가 홀수인 경우 word.substring(Math.abs(length/2))를 사용하려 했는데 Math.abs가 안되는 이유를 모르겠다.

## Best Practice
~~~java
class Kata {
  public static String getMiddle(String word) {
    String s = "";
    int length = word.length();
    int half = length/2;

    if (length % 2 == 0) {
      
      s = word.substring(half - 1, half + 1);
      
    } else {
      
      s = word.substring(half, half + 1);
    
    }
    
    return s;
  }
}
~~~

## Clever
~~~java
class Kata {
    public static String getMiddle(String word) {
        int length = word.length();
        return (length % 2 != 0) ?  String.valueOf(word.charAt(length / 2)) : word.substring(length / 2 - 1, length / 2 + 1);
    }
}
~~~
