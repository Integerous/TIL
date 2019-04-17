
# Call by value vs. Call by reference
>애매하게 이해하고 있었던 Call by value와 Call by reference에 대해 정리해본다.  
>아직 정리 중

## 1. Call by value (값에 의한 호출)

~~~java
// 메인 메소드 생략

static void bubbleSort(int n, int[] data) {
    
    for(int i = n-1; i>0; i--) {
        for(int j=0; j<1; j++) {
            if(data[j] > data[j+1]) {
                swap(data[j], data[j+1]);
            }
        }
    }
}

static void swap(int a, int b) {
    int tmp = a;
    a = b;
    b = tmp;
}
~~~

위의 코드에서 정렬이 제대로 이루어지지 않는다.  
이유는 ***Call by value(값에 의한 호출)*** 이기 때문이다.

그럼, Call by value는 무엇인가?  

우선, 위의 코드에서 data[j]와 data[j+1]을 바꾸기 위해 swap() 메소드를 호출하였다.  
그리고 swap()메소드는 매개변수로 건내받은 두 정수를 swap하는 역할을 한다.  

다시 말해,  
- 호출문 : `swap(data[j], data[j+1]);`  
- 호출된 메소드 :
  ~~~java
  public static void swap(int a, int b) {
    int tmp = a;
    a = b;
    b = tmp;
  }
  ~~~

하지만 `swap(data[j], data[j+1])`의 매개변수인 Actual parameter는
`swap(int a, int b)`의 매개변수인 Formal parameter와 완전히 별개의 변수이다.(메모리에서 다른 위치에 있다.)

때문에 swap() 메소드를 호출하는 순간,  
data[j]와 data[j+1]의 ***값***이 각각 a와 b에 복사될 뿐이다.  

그러므로 a와 b의 값을 변경해도 data[j]와 data[j+1]의 값에는 변화가 없기 때문에  
위의 코드에서 a와 b의 값이 바뀌어도 data[j]와 data[j+1]가 정렬되지 않는 것이다.

이렇게, Actual parameter와 Formal parameter가 별개의 변수일 때의 호출을  
***Call by value(값에 의한 호출)*** 이라고 한다.

`Java와 C언어는 모두 Call by value(값에 의한 호출)만 지원`한다.

## 2. Call by value with Array (배열을 사용할 때의 값에 의한 호출)

하지만 배열을 매개변수로 사용할 때에는 위의 설명과 다르게 동작한다.

예를 들어,

- 호출문: `bubbleSort(data, n);`
- 호출된 메소드:
  ~~~java
  public static void bubbleSort(int[] data, int n) {
    
    for(int i = n-1; i>0; i--) {
        for(int j=0; j<1; j++) {
            if(data[j] > data[j+1]) {
                //swap(data[j], data[j+1]);
                int tmp = data[j];
                data[j] = data[j+1];
                data[j+1] = tmp;
              }
          }
      }
  }
  ~~~
  
위의 상황에서 `bubbleSort(data, n)`메소드를 호출해서 data라는 배열을 매개변수로 넘기면,  
호출된 메소드는 매개변수로 받은 data 배열을 정렬하고, 원래의 배열에도 그대로 반영이 된다.

Primitive 타입의 매개변수는 `Call by value` 때문에  
호출된 메소드에서 값을 변경하더라도 호출한 쪽에 영향을 주지 못한다.

하지만 배열은 Primitive 타입이 아니기 때문에  
배열의 값은 호출된 메서드에서 변경하는 호출한 쪽에서도 변경된다.




## 3. Call by reference (참조에 의한 호출)

그런데 C++ 등의 언어는 Call by reference(참조에 의한 호출)도 지원한다.  

참조에 의한 호출에서는 Actual parameter와 Formal parameter가 별개의 변수가 되지 않고,  
동일한 변수로 취급되지만 이름만 다른 것이다.

때문에 위의 예시 코드에서 `swap(int &a, int &b)`등의 방식으로 참조에 의한 호출을 사용했을 때에는  
a와, b의 값이 바뀌면 data[j]와 data[j+1]의 값도 바뀐다.(같은 변수로 취급되므로)


## * Reference
- [Java로 배우는 자료구조 - 권오흠 교수](https://www.inflearn.com/course/java-%EC%9E%90%EB%A3%8C%EA%B5%AC%EC%A1%B0/%EB%A9%94%EC%84%9C%EB%93%9C-%ED%98%B8%EC%B6%9C%EA%B3%BC-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%A8%EC%9D%98-%EA%B8%B0%EB%8A%A5%EC%A0%81-%EB%B6%84%ED%95%A0-2/)

