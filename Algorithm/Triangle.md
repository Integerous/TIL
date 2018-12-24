# Triangle
>https://app.codility.com/programmers/lessons/6-sorting/triangle/

### 내 풀이 0%

>배열을 정렬하고, for문으로 A[i]+A[i+1]>A[i+2] 를 만족하면 1을 반환하고 아니면 0을 반환하게끔 작성하다가 스스로 함정에 빠졌다.

문제에 주어진 테스트케이스를 정렬하면 [1, 2, 5, 8, 10, 20] 이고 [5, 8, 10]에 의해 Triangle이 된다.  
나는 [5, 8 ,10] 처럼 연속되는 배열의 원소가 Triangle이 되는 경우가 아닌 케이스 [1, 3, 5, 8, 10, 20]를 고려해서  
[3, 8, 10] 처럼 Triangle 조건을 만족하지만 연속되지 않는 것들을 어떻게 처리해야 할지 고민하는데 시간을 쏟았다.

그런데 [3, 8, 10]이 만족하면 3과 8사이에 어떤 정수라도 조건을 만족하기 때문에  
연속된 원소들(A[i], A[i+1], A[i+2])를 조건문에 사용해서 for문을 돌려도 상관없는 것이었다.

문제를 풀다가 경우가 너무 다양해서 막막해지면, 그 경우들을 어떻게 처리할 지 고민하기보다 경우를 통합하고 간소화할 방법을 먼저 고민해야될 것 같다.

### 다른사람 풀이 100%
>https://wildcatsy.blogspot.com/2017/05/codility-lesson-6-sorting-triangle-100.html

~~~java
import java.util.Arrays;
// you can write to stdout for debugging purposes, e.g.
// System.out.println("this is a debug message");

class Solution {
    public int solution(int[] A) {
        // write your code in Java SE 8
        Arrays.sort(A);
        
        
        for( int i = 0 ; i < A.length-2 ; i++){
            if(A[i] < 0 ) continue;
            if((long)A[i] + A[i+1] > A[i+2]) return 1;
        }
        return 0;
    }
}
~~~
