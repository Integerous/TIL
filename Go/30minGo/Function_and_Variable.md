# 함수와 변수

## 1. 함수와 매개변수
~~~go
pacakage main

import "fmt"

// 1. 매개변수 타입, 리턴 타입은 이름 뒤에 지정한다.
func add1(x int, y int) int {
  return x + y
}

// 2. 매개변수 x, y가 같은 타입일 때에는 타입을 한 번만 명시해 줄 수 있다.
func add2(x, y int) int {
  return x + y
}

func main() {
  fmt.Println("add1(x int, y int)의 결과: ", add1(42, 13))
  fmt.Println("add2(x, y int)의 결과: ", add2(42, 13))
}
~~~  
- 타입은 매개변수 뒤에 표시한다.
- 리턴 타입도 가장 뒤에 표시한다.
- 같은 타입의 변수 여러 개를 선언할 때에는 타입을 한 번만 적어도 된다.

## 2. 함수와 여러가지 리턴 방법
- 함수는 여러 값을 한 번에 return할 수 있다.
  - return 뒤에 리턴 타입을 적어주는 방법
  - return 뒤에 리턴 할 변수를 선언하는 방법
