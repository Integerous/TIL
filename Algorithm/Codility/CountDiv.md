# Count Div
>https://app.codility.com/programmers/lessons/5-prefix_sums/count_div/

### 내 풀이 50%
>correctness는 100%였지만 Performance는 0%  
>A, B, K의 범위가 20억인데 배열로 푼 것은 멍청했다.

~~~java
class Solution {
    public int solution(int A, int B, int K) {
        
        int[] tmp = new int[B-A+1];
        int count = 0;
        
        
        for(int i=0; i<B-A+1; i++) {
            tmp[i] = A+i;
            
            if(tmp[i] % K == 0)
                count++;
        }
        
        return count;
    }
}
~~~

### 다른사람 풀이 100%
>http://mingmi-programming.tistory.com/62  
>수학 지식으로 풀 수 있는 문제였다. 공식을 몰랐다면 손으로 규칙을 찾았어야했다.

~~~java
class Solution {
    public int solution(int A, int B, int K) {
                
        if (A == 0)
            return B / K + 1;
        else
            return B / K - (A - 1) / K;
    }
}
~~~
