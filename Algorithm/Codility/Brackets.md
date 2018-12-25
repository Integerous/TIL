# Brakets
>https://app.codility.com/programmers/lessons/7-stacks_and_queues/brackets/

### 내 풀이 0%
>실패  

### 다른사람 풀이 100%
>http://stroot.tistory.com/104

~~~java
public int solution(String S) {
    Stack<Character> stack = new Stack<>();
    
    for (int i = 0; i < S.length(); i++) {
        char c = S.charAt(i);
        
        if (c == '(' || c == '{' || c == '[') {
            stack.push(c);
        }
        else {
            if (stack.isEmpty()) {
                return 0;
            }
            
            char lastC = stack.pop();
            
            if (c == ')' && lastC != '(') {
                return 0;
            }
            
            if (c == '}' && lastC != '{') {
                return 0;
            }
            
            if (c == ']' && lastC != '[') {
                return 0;
            }
        }
    }
    
    if (!stack.isEmpty()) {
        return 0;
    }
    
    return 1;
}
~~~
