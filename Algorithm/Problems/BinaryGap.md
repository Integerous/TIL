## BinaryGap
>1~21억 사이의 정수를 이진수로 변환했을때 1사이의 거리를 binary gap이라 가정.  
>주어진 정수의 가장 긴 binary gap 반환하기 

### java Integer 클래스 `Integer.toBinaryString()` 사용
~~~java
//import java.util.*;
class Solution {
    public int solution(int N) {
        
        String binaryString = Integer.toBinaryString(N);
        char[] binaryChar = binaryString.toCharArray();
        
        int maxGap =0;
        int gap = 0;
        
        for(int i=0; i<binaryChar.length; i++) {
            if(binaryChar[i] == '1') {
                if(gap > maxGap) {
                    maxGap = gap;
                }
                gap = 0;
            }
            else{
                gap++;
            }
        }
        
        return maxGap;
    }
}
~~~

### 10진수를 2로 거듭 나눠서 2진수 구하기

~~~java
class Solution {
  public int solution(int N) {
    
    int maxGap = 0;
    int gap = 0;
    
    while(N!=0) {
      N = N/2;
      if(gap > maxGap) {
        maxGap = gap;
      }
      if(N%2 == 1) {
        gap = 0;
      }else
        gap++;
    }
   
    return maxGap;
  }
}
~~~
