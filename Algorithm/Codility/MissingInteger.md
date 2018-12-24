# MissingInteger
>https://app.codility.com/programmers/lessons/4-counting_elements/missing_integer/

### Set 사용 100%
>O(N) or O(N * log(N))
~~~java
import java.util.*;

class Solution {
    public int solution(int[] A) {
        
        Set<Integer> checkSet = new HashSet<>();
        
        for(int i=0; i<A.length; i++) {
            checkSet.add(A[i]);
        }
        
        for(int x=1; x<Integer.MAX_VALUE; x++) {
            if(!checkSet.contains(x))
                return x;
        }
        
        return -1;
    }
}
~~~
