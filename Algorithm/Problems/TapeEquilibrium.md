## TapeEquilibrium
>문제: https://app.codility.com/programmers/lessons/3-time_complexity/

### 내 풀이
~~~java
class Solution {
    public int solution(int[] A) {
        
        int left = 0;
        int right = 0;
        
        for(int i=0; i<A.length; i++) {
            right += A[i];
        }
        
        int gap = Integer.MAX_VALUE;
        
        for(int i=0; i<A.length-1; i++) {
            left += A[i];
            right -= A[i];
            
            gap = Math.min(gap, Math.abs(left-right));
        }
        
        return gap;   
    }
}
~~~
