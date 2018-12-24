# OddOccurrencesInArray
>https://app.codility.com/programmers/lessons/2-arrays/odd_occurrences_in_array/


### 내 풀이 0%

~~~java
import java.util.*;

class Solution {
    public int solution(int[] A) {
        
        Arrays.sort(A);
        int result = 0;
        
        for(int i=1; i< Math.ceil(A.length/2); i++) {
            if(A.length == 1){
                result = A[0];
            }
            else if(A[2*i-2]-A[2*i-1] != 0) {
                result = A[2*i-2];
            }
        }
        return result;
    }
}
~~~

### 다른사람 풀이 1
>XOR 이용

~~~java
class Solution {
    public int solution(int[] A) {
        
        int result = 0;
        
        for(int i=0; i<A.length; i++) {
          result = result ^ A[i];
        }
        
        return result;
    }
}
~~~

### 다른사람 풀이 2
>HashSet 이용

~~~java
class Solution {
  public int solution(int[] A) {
  
    Set<Integer> set = new HashSet<>();
    
    for(int i : A) {
      if(set.contains(i)) {
        set.remove(i);
      }else {
        set.add(i);
      }
    }
    
    return set.iterator().next();
  }
}
~~~
