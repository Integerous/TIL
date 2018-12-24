# FrogRiverOne
>https://app.codility.com/programmers/lessons/4-counting_elements/frog_river_one/

### 내 풀이
>int배열의 각 원소는 int의 기본값 0으로 초기화되는 것을 잊고 헤맸다.
~~~java
class Solution {
    public int solution(int X, int[] A) {
        
        int[] B = new int[X+1];
        int count = 0;
        
        for(int i=0; i<A.length; i++) {
            if(B[A[i]] == 0) {
                B[A[i]] = A[i];
                count++;
                
                if(count == X)
                    return i;
            }
        }
        
        return -1;
    }
}
~~~

### 다른사람 풀이
>https://stackoverflow.com/questions/19459197/java-codility-frog-river-one  
>Set은 유일한 값만 추가할 수 있는 성질 활용
~~~java
import java.util.HashSet;
import java.util.Set;

class Solution {
    public int solution(int X, int[] A) {
    
      Set<Integer> values = new HashSet<Integer>();
      
      for (int i = 0; i < A.length; i++) {
          if (values.add(A[i])) X--; 
          if (X == 0) return i;
      }
      return -1;
    }
}
~~~
