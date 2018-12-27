# Stron Wall
>https://app.codility.com/programmers/lessons/7-stacks_and_queues/stone_wall/

### 내 풀이 50%
>스택에 현재 높이보다 높은 것들을 while문을 돌려서 모두 pop()해야 했는데  
>직전의 것만 pop()해서 오답인 경우들이 생겼다.  
>Stack 문제는 while문을 잘 활용해야 할 것 같다.

~~~java
import java.util.*;

class Solution {
    public int solution(int[] H) {
    
        Stack<Integer> stk = new Stack<>();
        
        int count = 0;
        
        for(int i=0; i<H.length; i++) {
            if(stk.isEmpty()) {
                stk.push(H[i]);
                count++;
            }
            else if(H[i] == stk.peek()) {
                continue;
            }
            else if(H[i] > stk.peek()){
                stk.push(H[i]);
                count++;
            }
            else if(H[i] < stk.peek()) {
                
                stk.pop();
                
                if(stk.isEmpty()) {
                    stk.push(H[i]);
                    count++;
                }
                else if(H[i] != stk.peek()) {
                    stk.push(H[i]);
                    count++;
                }
            }
        }
        return count;        
    }
} 
~~~   

### 다른사람 풀이 100%
>http://romanticcode.tistory.com/37

~~~java
import java.util.Stack;

class Solution {
    public int solution(int[] H) {
        
        Stack<Integer> stack = new Stack();
        int blockCnt = 0;
        
        for(int i =0 ; i < H.length; i++){
           
            while(stack.size() > 0 && stack.peek() > H[i]){
                stack.pop();
            }
            
            if(stack.size() == 0 || stack.peek() < H[i]){
                stack.push(H[i]);
                blockCnt++;
            }
        }
        return blockCnt;
    }
}
~~~
