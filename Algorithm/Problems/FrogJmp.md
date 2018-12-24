# FrogJmp
>https://app.codility.com/programmers/lessons/3-time_complexity/frog_jmp/

~~~java
import java.util.*;

class Solution {
    public int solution(int X, int Y, int D) {
        
        if( (Y-X)%D == 0)
            return (Y-X)/D;
        else
            return (int)Math.floor((Y-X)/D)+1;
    }
}
~~~
