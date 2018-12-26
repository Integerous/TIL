# Fish
>https://app.codility.com/programmers/lessons/7-stacks_and_queues/fish/

### 내 풀이 37%
>위로 올라가는 생선이 더 잡아먹을 수 있다는 점을 간과하고 작성했다.  
>다시 풀어볼 예정

~~~java
 import java.util.*;

class Solution {
    public int solution(int[] A, int[] B) {
        
        // B[x]의 값이 1이 처음 나왔을때부터 먹고먹히기 시작
        // (배열 크기) - (먹은 횟수) = 남은 생선
        
        int survivor = A.length;
        
        Stack<Integer> stkA = new Stack<>();
        Stack<Integer> stkB = new Stack<>();
        
        for(int i=0; i<A.length; i++) {
            
            if(B[i] == 1) { //현재 생선이 내려가는 경우 스택에 쌓고 끝.
                stkA.push(A[i]);
                stkB.push(B[i]);
            }
            
            else { //현재 생선이 올라가는 경우
                
                if(stkA.isEmpty()) { //첫 번째 생선일 경우
                    stkA.push(A[i]);
                    stkB.push(B[i]);
                }
                else if(stkB.peek() == 1) { //윗생선이 내려오는 경우
                        if(stkA.peek() > A[i]) { // 윗생선이 더 크면 생존자 1감소
                            survivor--;    
                        }
                        else {  // 윗생선이 더 작으면 현재 생선을 스택에 올리고 생존자 1감소
                            stkA.push(A[i]);
                            stkB.push(B[i]);
                            survivor--;
                            
                        }
                }
                //이전 생선도 올라가고 현재 생선도 올라가는 경우, 현재 생선을 스택에 적재
                else if(stkB.peek() == 0) {
                    stkA.push(A[i]);
                    stkB.push(B[i]);
                }
            }
            
        }
        
        return survivor;
    }
}
~~~
