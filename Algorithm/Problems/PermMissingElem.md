## PermMissingElem
>1..N+1 범위의 유일한 원소들이 담긴 길이 N의 배열이 주어질 때, 배열에 1..N+1 범위 중 없는 값 반환  
>N의 범위는 0..100,000

### 내 풀이
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
