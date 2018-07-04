# Simple Encryption #1 - Alternating Split
>6kyu  
>failed  
>https://www.codewars.com/kata/simple-encryption-number-1-alternating-split/java

## Instructions
For building the encrypted string:
Take every 2nd char from the string, then the other chars, that are not every 2nd char, and concat them as new String.
Do this n times!

Examples:

"This is a test!", 1 -> "hsi  etTi sats!"
"This is a test!", 2 -> "hsi  etTi sats!" -> "s eT ashi tist!"
Write two methods:

String encrypt(final String text, final int n)
String decrypt(final String encryptedText, final int n)
For both methods:
If the input-string is null or empty return exactly this value!
If n is <= 0 then return the input text.

This kata is part of the Simple Encryption Series:
Simple Encryption #1 - Alternating Split
Simple Encryption #2 - Index-Difference
Simple Encryption #3 - Turn The Bits Around
Simple Encryption #4 - Qwerty

Have fun coding it and please don't forget to vote and rank this kata! :-)

# My Solution
~~~java
public class Kata {

  public static String encrypt(final String text, final int n) {
    
    char[] x = text.split("[\s\w]").toCharArray();
    int length = x.length;
    
    char[] y = new char[length/2];
    char[] z = new char[length/2];
    
    for(int i=0; i<length/2; i++)
      y[i] = x[2*i+1];
      
    for(int j=0; j<length/2; j++)
      z[i] = x[2*i];
      
    // y[]와 z[] 배열을 합치려 했으나 실패
    
    return null;   
  }
  
  public static String decrypt(final String encryptedText, final int n) {
    // Your code here
    return null;
  }
 
}
~~~

## Result & Review
>Time out! 실패!
- 배열로 푸는 것의 한계를 느낄 수 있었다. (StringBuilder를 공부하고 사용해야겠다.)
- Clever #2 코드의 재귀와 for문은 배울 점이 많다.
- 문자열 내부의 단어를 바꾸기 위한 정규표현식 (.)(.)?, $2 를 알게되었다.
- 단어 위치바꾸기 용례
    ~~~java
    var re = /(\w+)\s(\w+)/;
    var str = "John Smith";
    var newstr = str.replace(re, "$2, $1");
    console.log(newstr);

    // "Smith, John"
    ~~~

## Best Practice
~~~java
public class Kata {

  public static String encrypt(final String text, int n) {
                if (n <= 0 || text == null || text.isEmpty()) {
                        return text;
                }

                StringBuilder firstPart = new StringBuilder();
                StringBuilder secondPart = new StringBuilder();
                for (int i = 0; i < text.length(); i++) {
                        char aChar = text.charAt(i);
                        if (i % 2 == 1) {
                                firstPart.append(aChar);
                        } else {
                                secondPart.append(aChar);
                        }
                }

                return encrypt(firstPart.append(secondPart).toString(), --n);
        }

        public static String decrypt(final String encryptedText, int n) {
                if (n <= 0 || encryptedText == null || encryptedText.isEmpty()) {
                        return encryptedText;
                }

                StringBuilder text = new StringBuilder();
                final int half = encryptedText.length() / 2;
                for (int i = 0; i < half; i++) {
                        text.append(encryptedText.charAt(half + i)).append(encryptedText.charAt(i));
                }
                if (encryptedText.length() % 2 == 1) {
                        text.append(encryptedText.charAt(encryptedText.length() - 1));
                }

                return decrypt(text.toString(), --n);
        }
  
}
~~~

## Clever #1
~~~java
import java.util.*;

public class Kata {

  public static String encrypt(final String text, final int n) {
    if (text == null || text.isEmpty() || n < 1) return text;
    
    return encrypt(text.replaceAll("(.)(.)?", "$2") + text.replaceAll("(.)(.)?", "$1"), n - 1);
  }

  public static String decrypt(final String text, final int n) {
    if (text == null || text.isEmpty() || n < 1) return text;

    StringBuilder output = new StringBuilder();
    Iterator<String> first = Arrays.asList(text.substring(0, text.length()/2).split("")).iterator();
    Iterator<String> last = Arrays.asList(text.substring(text.length()/2).split("")).iterator();
    
    while (first.hasNext() || last.hasNext()) {
      if (last.hasNext()) output.append(last.next());
      if (first.hasNext()) output.append(first.next());
    }
    
    return decrypt(output.toString(), n - 1);
  }
  
}
~~~

## Clever #2
~~~java
public class Kata {

  public static String encrypt(final String text, final int n) {
    if (n<=0) return text;
      StringBuilder str = new StringBuilder();
      for (int i=1; i<text.length(); i+=2)
        str.append(text.substring(i,i+1));
      for (int i=0; i<text.length(); i+=2)
        str.append(text.substring(i,i+1));
    return (n==1)? str.toString() : encrypt(str.toString(), n-1);
  }
  
  public static String decrypt(final String entext, final int n) {
    if (n<=0) return entext;
    StringBuilder str = new StringBuilder();
    str.append(entext.substring(0,entext.length()/2));
    for (int i=entext.length()/2, j=0; i<entext.length(); i++, j+=2)
      str.insert(j,entext.substring(i,i+1)); 
    return (n==1)? str.toString() : decrypt(str.toString(),n-1);
  }
  
}
~~~





