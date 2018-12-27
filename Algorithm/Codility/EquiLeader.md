# Equi Leader
>https://app.codility.com/programmers/lessons/8-leader/equi_leader/  


### 내 풀이 0%
>1시간 풀었으나 실패  
>다시 풀어보기


### 다른사람 풀이 100%
>http://androidapptech0.blogspot.com/2017/01/codility-lesson-8-leader-equileader-java.html

~~~java
import java.util.*;
 
class Solution {
    public int solution(int[] A) {
        int leader=-1;
        int countOfLeader=0;
 
        HashMap<Integer, Integer> hashMap=new HashMap<>();
 
        for(int i=0;i<A.length;i++){
            if(hashMap.containsKey(A[i])){
                int count=hashMap.get(A[i]);
                count++;
                hashMap.put(A[i],count);
                if(countOfLeader<count){
                    countOfLeader=count;
                    leader=A[i];
                }
            }else{
                hashMap.put(A[i], 1);
            }
        }
        if(countOfLeader<A.length/2){
            return 0;
        }
 
        int countEquil=0;
        int counterOfrLeaderInRight=0;
        int counterOfrLeaderInLeft=countOfLeader;
        
        for(int i=A.length-1;i>=0;i--){
            if(A[i]==leader){
                counterOfrLeaderInRight++;
                counterOfrLeaderInLeft--;
            }
            
            if(counterOfrLeaderInLeft>i/2 && counterOfrLeaderInRight>((A.length-i)/2)){
                countEquil++;
            }
        }
        return countEquil;
    }
}
~~~
