## Perm Missing Elem
>https://app.codility.com/programmers/lessons/3-time_complexity/perm_missing_elem/

### 내 풀이 100%
~~~java
import java.util.*;

class Solution {
    public int solution(int[] A) {

    Arrays.sort(A);
    
    for(int i=0; i<A.length; i++) {
        if(A[i] != i+1) {
            return i+1;
        }
    }
    
    return A.length+1;

    }
}
~~~
