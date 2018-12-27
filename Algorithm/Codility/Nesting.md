# Nesting
>https://app.codility.com/programmers/lessons/7-stacks_and_queues/nesting/

### 내 풀이 100%
>스택 없이 +-로만 풀어도 될 것 같다.

~~~java
import java.util.*;

class Solution {
    public int solution(String S) {
        
        Stack<Character> stk = new Stack<>();
        
        for(int i=0; i<S.length(); i++) {
            char c = S.charAt(i);
            if(c == '(') {
                stk.push(c);
            }
            else if(stk.isEmpty()) {
                return 0;
            }
            else {
                stk.pop();
            }
        }
        
        if(stk.isEmpty()) {
            return 1;
        }
        else
            return 0;
    }
}
~~~
