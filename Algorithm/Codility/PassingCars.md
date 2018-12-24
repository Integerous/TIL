# Passing Cars
>https://app.codility.com/programmers/lessons/5-prefix_sums/passing_cars/


### 내 풀이 90%
>passingCars가 음수일 경우에도 -1을 반환하게 했어야했다.  
>근데 테스트케이스에서 어떻게 passingCars가 음수가 될 수 있는거지?

~~~java
class Solution {
    public int solution(int[] A) {
        
        int toEast = 0;
        int passingCars = 0;
        
        for(int i=0; i<A.length; i++) {
            if(A[i] == 0) {
                toEast++;
            }else
                passingCars += toEast;
        }
        
        if(passingCars > 1000000000)
            return -1;
        
        return passingCars;
    }
}
~~~
