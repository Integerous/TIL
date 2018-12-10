# FrogJmp
>개구리 현재 위치 X, 목적지 Y(또는 그 이상), 점프거리 D 일 때,  
>목적지에 도착하는 최소 점프 횟수 반환  
>X<=Y, X,Y,D의 범위는 1..1,000,000,000

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
