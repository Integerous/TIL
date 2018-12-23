# MinAvgTwoSlice
>https://app.codility.com/programmers/lessons/5-prefix_sums/min_avg_two_slice/

## 다른사람 풀이
>http://stroot.tistory.com/97

평균의 성질을 이해하고 풀었어야 하는 문제였다.  
나처럼 코드부터 작성하려고 덤볐다가는 날 밤 샐 문제인 것 같다.

1. 인자 2개의 평균은 인자 중 하나보다 같거나 큰 값이다.
2. 평균값들의 평균은 각 인자들의 평균과 같다.
3. 즉, 평균값들의 평균은 그 인자가 되는 평균들보다 항상 같거나 크다.
4. 그러므로, 인자가 2개인 경우와 3개인 경우만 생각하면 된다.

~~~java
public int solution(int[] A) {
    double minAvg = (A[0] + A[1]) / 2.0;
    int minStartIndex = 0;
 
    for (int i = 2; i < A.length; i++) {
        double avg = (A[i - 2] + A[i - 1] + A[i]) / 3.0;
 
        if (avg < minAvg) {
            minAvg = avg;
            minStartIndex = i - 2;
        }
 
        avg = (A[i - 1] + A[i]) / 2.0;
 
        if (avg < minAvg) {
            minAvg = avg;
            minStartIndex = i - 1;
        }
    }
 
    return minStartIndex;
}
~~~
