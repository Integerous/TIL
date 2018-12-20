# PermCheck
>https://app.codility.com/programmers/lessons/4-counting_elements/perm_check/

### 내 풀이 75%
>[2,2,2] 상황에서 합이 6인데 걸러내지 못하는 코드다.

~~~java
class Solution {
    public int solution(int[] A) {
        
        // 원소의 합
        int sum = 0;
        int check = 0;
        
        for(int i=0; i<A.length; i++) {
            if(A[i]> A.length) {
                return 0;
            }
            sum += A[i];
            check += i+1;
        }
        
        if(sum == check)
            return 1;
        else
            return 0;
    }
}
~~~

### 다른사람 풀이 100%
>http://stroot.tistory.com/90  
>완전한 순열이 안되게 하는 조건 2가지만 체크하는 방식  
>O(N) or O(N * log(N))

~~~java
import java.util.*;

class Solution {
    public int solution(int[] A) {
        
        Set<Integer> check = new HashSet<>();
        
        for(int i=0; i<A.length; i++) {
            if(A[i] > A.length) {
                return 0;
            }
            
            if(check.contains(A[i])) {
                return 0;
            }
            
            check.add(A[i]);
        }
        return 1;
    }
}
~~~
