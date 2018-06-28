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
      
  
    return sums;
  }

}
~~~
