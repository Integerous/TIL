# MaxCounters
>https://app.codility.com/programmers/lessons/4-counting_elements/max_counters/

### 내 풀이 77%
>O(N*M)으로 풀어서 그런지 감점 요인은 Timeout

~~~java
class Solution {
    public int[] solution(int N, int[] A) {

        // N+1일때의 연산
            // 최대값으로 counters 통일
        // 1~N일때의 연산
            // 해당 위치의 값 +1
        
        //A의 길이는 연산 횟수
        //N이 반환되는 배열 길이
        
        int[] result = new int[N];
        int max = 0;
        
        for(int i=0; i<A.length; i++) {
            
            if(A[i] <= N) {
                result[A[i]-1]++;
                if(result[A[i]-1] > max) {
                    max = result[A[i]-1];
                }
            }
                 
            if(A[i] == N+1) {
                for(int j=0; j<N; j++) {
                    result[j] = max;
                }
            }      
        }
        return result;    
    }
}
~~~

### 다른사람 풀이 100%
>http://stroot.tistory.com/93  
>O(N+M)  
>max counter 연산이 일어날 때 마다 값을 대입하지 않고, 변수에 저장해두고 그 다음 increase 연산에서 해당 원소가 max값보다 작으면 max값을 대입하고 
해당 원소에 1을 더한다. 아예 increase 연산이 일어나지 않는 원소는 마지막에 for문 돌면서 max 변수에 담아둔 값을 넣어준다.

~~~java
class Solution {
    public int[] solution(int N, int[] A) {
        
        int[] counter = new int[N];
        int tmpMaxCounter = 0;
        int doneMaxCounter = 0;
     
        for (int i = 0; i < A.length; i++) {
            if (A[i] > N) {
                doneMaxCounter = tmpMaxCounter;
            }
            else {
                if (counter[A[i] - 1] < doneMaxCounter) {
                    counter[A[i] - 1] = doneMaxCounter;
                }
     
                counter[A[i] - 1]++;
     
                if (counter[A[i] - 1] > tmpMaxCounter) {
                    tmpMaxCounter = counter[A[i] - 1];
                }
            }
        }
     
        if (doneMaxCounter > 0) {
            for (int i = 0; i < counter.length; i++) {
                if (counter[i] < doneMaxCounter) {
                    counter[i] = doneMaxCounter;
                }
            }
        }
     
        return counter;
    }
}
~~~
