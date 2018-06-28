# Highest Scoring Word
>6kyu  
>failed with Java  
>http://www.codewars.com/kata/highest-scoring-word/java

## Instructions
Description:
Given a string of words, you need to find the highest scoring word.

Each letter of a word scores points according to it's position in the alphabet: `a = 1, b = 2, c = 3` etc.

You need to return the highest scoring word as a string.

If two words score the same, return the word that appears earliest in the original string.

All letters will be lowercase and all inputs will be valid.

# My Solution
~~~java
public class Kata {

  public static String high(String s) {
  
    // 빈공간 개수 구하기
    int spaceCount = 0;
    
    for(int i=0; i<s.length; i++){
      if(s.charAt(i) == " ")
        spaceCount++;
    }
       
    // 빈공간 위치 담을 배열생성
    int[] spacePositions;
    // 단어 담을 배열 생성
    String[] words;
    
    for(int i=0; i<spaceCount; i++) {
      spacePositions[i] = s.indexOf(" ", i+1);
      words[i] = s.substring(spacePositions[i]+1, spacePositions[i+1]);
    }  
      words[0] = s.substring(0, s.indexOf(" ", 1));
      
      
    //단어 길이를 구하고 단어의 알파벳들의 합 구하기
      int[] sums;
      for(int i =0; i<words.length; i++){
        for(int j=0; j<words[i].length; j++)
          sums[i] += words[i].charAt(j);
      }
      
    //////// 코드 미완성
    
    return sums;
  }

}
~~~

## Result & Review
>Time-out! 시간 초과로 실패!
- 세상에서 제일 썩은 코드를 작성했다.
- split()과 toCharArray()의 존재를 몰랐다.
- ASCII Table에서 알파벳은 각각 해당하는 10진수 값이 정해져있는 사실을 잊고 있었다.
- 밑의 Clever 코드가 import 없이 풀어서 매우 마음에 든다.

## Best Practice
~~~java
import java.util.*;

public class Kata {
  public static String high(String s) {
    return Arrays.stream(s.split(" "))
                .max(Comparator.comparingInt(
                        a -> a.chars().map(i -> i - 96).sum()
                )).get(); 
  }
}
~~~

## Clever
~~~java
public class Kata {

  public static String high(String s) {
    
    String winner = "";
    int highScore = 0;
    
    for (String word : s.split(" ")) {
        int score = 0;
        for (char c : word.toCharArray()) {
          score += c - 'a' + 1;
        }
        if (score > highScore) {          
          winner = word;
          highScore = score;
        }
    }
    
    return winner;
  }

}
~~~



