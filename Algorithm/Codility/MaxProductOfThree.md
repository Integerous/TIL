# Max Product Of Three
>https://app.codility.com/programmers/lessons/6-sorting/max_product_of_three/

### 내 풀이 44%
>내가 만든 테스트케이스 중 일부를 통과하지 못했지만 제출해봤다.  
>최대값이 되는 경우를 단순화하지 못했고, for문을 사용할 필요가 전혀 없었다.

~~~java
import java.util.*;

class Solution {
    public int solution(int[] A) {
             
        Arrays.sort(A);
        int result = 0;
        int length = A.length;
        
        for(int i=0; i<length-1; i++) {
            if((A[i]*A[i+1]) > (A[length-2]*A[length-1])) {
                result = A[i]*A[i+1]*A[length-1];
            }else
                result = A[length-3]*A[length-2]*A[length-1];
        }
        return result;
    }
}
~~~

### 다른사람 풀이 100%
>http://reddeco.tistory.com/entry/MaxProductOfThree  
>최대값이 되는 경우를 2가지로 단순화했다.  

~~~java
import java.util.*;

class Solution {
    public int solution(int[] A) {
        
        Arrays.sort(A);
        
        int caseA = A[A.length-1]*A[A.length-2]*A[A.length-3];
        int caseB = A[0]*A[1]*A[A.length-1];
        
        return caseA > caseB ? caseA : caseB;
    }
}
~~~
