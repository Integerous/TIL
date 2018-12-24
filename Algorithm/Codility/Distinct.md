# Distinct
>https://app.codility.com/programmers/lessons/6-sorting/distinct/


### 내 풀이 100%

~~~java
import java.util.*;

// you can write to stdout for debugging purposes, e.g.
// System.out.println("this is a debug message");

class Solution {
    public int solution(int[] A) {
        
        Set<Integer> filterSet = new HashSet<>();
        
        for(int i=0; i<A.length; i++)
            filterSet.add(A[i]);
            
        return filterSet.size();    
    }
}
~~~
