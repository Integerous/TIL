# Fish
>https://app.codility.com/programmers/lessons/7-stacks_and_queues/fish/

### 내 풀이 37%
>위로 올라가는 생선이 더 잡아먹을 수 있다는 점을 풀어내지 못했다.    
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

### 다른사람 풀이 1 100%
>http://stroot.tistory.com/105  
>위의 내 풀이에서는 올라가는 생선을 처리하지 못했는데, 이 코드는 while을 활용해서 풀어냈다.  
>스택에 A배열과 B배열의 값이 아닌 인덱스를 넣어서 스택을 2개 구현할 필요가 없었다.  
>continue와 break가 헷갈려서 찾아봤다.  
>continue는 반복문의 끝으로 이동, break는 가장 가까운 반복문 벗어나기


~~~java
public int solution(int[] A, int[] B) {
    int aliveCount = 0;
    Stack<Integer> downFishes = new Stack<>();
    
    for (int i = 0; i < A.length; i++) {
        if (B[i] == 0) {    // up fish
            aliveCount++;
            
            if (downFishes.isEmpty()) {
                continue;
            }
            
            int downFish = downFishes.peek();
            
            while (true) {
                aliveCount--;
                
                if (A[downFish] < A[i]) {
                    downFishes.pop();
                    
                    if (downFishes.isEmpty()) {
                        break;
                    }
                    
                    downFish = downFishes.peek();
                }
                else {
                    break;
                }
            }
        }
        else {    // down fish
            downFishes.add(i);
            aliveCount++;
        }
    }
    
    return aliveCount;
}
~~~


### 다른사람 풀이 2 100%
>http://reddeco.tistory.com/entry/Fish  
>내려가는 생선을 Stack에 담고 올라오는 생선과 while문으로 비교한다.  
>생선은 올라가는데 Stack이 비어있을때만 올라가는 생선 숫자를 ++하여 살아서 올라가는 생선 수와 Stack(살아서내려가는 생선들)의 사이즈를 합하여 반환한다.  
>위의 코드보다는 직관적이어서 보는 입장에서 이해하기가 더 쉽다.

~~~java
import java.util.*;
 
class Solution {
    public int solution(int[] A, int[] B) {
        // A : fish size
        // B : direction (0-up, 1-down)
         
        int count = 0;
        Stack<integer> down = new Stack<integer>();
         
        for (int i = 0; i < A.length; i++) {
            if (B[i] == 0) { // up
                if (down.empty()) {
                    count++;
                } else {
                    while (!down.empty()) {
                        if (down.peek() > A[i]) {
                            break;
                        } else {
                            down.pop();
                        }
                    }
                    if (down.empty()) {
                        count++;
                    }
                }
            } else { // down
                down.push(A[i]);
            }
        }
         
        return count + down.size();
    }
}
~~~
