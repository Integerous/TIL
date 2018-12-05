## CyclicRotation
>1-100 길이의 정수 배열 A가 주어졌을 때,  
>1-100사이의 정수 K만큼 배열 각 요소를 오른쪽으로 한 칸씩 이동한 결과 배열 반환  
>성능보다 정확도가 중요

### 내 풀이

~~~java
class Solution {
    public int[] solution(int[] A, int K) {
        
        // correectness! rather than performance
        
        // 배열의 길이
        int length = A.length;
        
        // 임시저장 배열
        int[] tmp = new int[length];
       
        if(length != 0) {
          // 배열 각 원소의 이동 횟수
          int move = K % length;
       
          // 임시배열에 새로운 순서 담기
          for(int i=0; i<length; i++){
              if(i+move+1 <= length) {
                  tmp[i+move] = A[i];
              }else
                  tmp[i+move-length] = A[i]; // 배열 끝부분 만나면 처음으로.
          }
        }
        return tmp;
             
    }
}
~~~

### 다른사람 풀이
>출처 http://blog.naver.com/PostView.nhn?blogId=netrance&logNo=220690184441
~~~java
class Solution {
    public int[] solution(int[] A, int K) {
        
        int[] B = new int[A.length];
        
        for(int i = 0; i < A.length; i++){
          B[(i+K) % A.length] = A[i];
        }
        return B;
    }
}
~~~
