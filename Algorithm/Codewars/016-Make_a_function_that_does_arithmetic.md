# Make a Function that does arithmetic!
>7kyu  
>solved with Java  
>https://www.codewars.com/kata/make-a-function-that-does-arithmetic/java

## Instructions
Description:
Given two numbers and an arithmetic operator (the name of it, as a string), return the result of the two numbers having that operator used on them.

a and b will both be positive integers, and a will always be the first number in the operation, and b always the second.

The four operators are "add", "subtract", "divide", "multiply".

A few examples:  
~~~java
arithmetic(5, 2, "add")      => returns 7
arithmetic(5, 2, "subtract") => returns 3
arithmetic(5, 2, "multiply") => returns 10
arithmetic(5, 2, "divide")   => returns 2.5
~~~
~~~java
ArithmeticFunction.arithmetic(5, 2, "add")      => returns 7
ArithmeticFunction.arithmetic(5, 2, "subtract") => returns 3
ArithmeticFunction.arithmetic(5, 2, "multiply") => returns 10
ArithmeticFunction.arithmetic(5, 2, "divide")   => returns 2
~~~
**Try to do it without using if statements!**


# My Solution
~~~java
class ArithmeticFunction {
  public static int arithmetic(int a, int b, String operator) {
    
    return operator=="add"? a+b : (operator=="subtract"? a-b : (operator=="multiply"? a*b : (operator=="divide"? Math.abs(a/b) : null)));

  }
}
~~~

## Result & Review
>Time : 1905ms
- if문으로 답을 작성하고 '뭐 이렇게 쉽지? 이상한데?'라고 생각했다가 description을 제대로 읽지 않은 것을 발견. if문 없이 해결해야한다.
- 삼항연산자를 사용하면서도 무언가 if문 스럽다는 죄책감이 들었는데, Best Practice로 올라온 코드가 switch로 풀어서 내께 더 낫다고 생각했지만,
- if나 switch나 삼항연산자 없이 **Map으로 푼 사람이 있었다...** Map 미만 잡~

## Best Practice
~~~java
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;

/**
 * look: no if
 */
public class ArithmeticFunction {
  static Map<String, BinaryOperator<Integer>> operators = new HashMap<>();

  static {
    operators.put("add", (a, b) -> a + b);
    operators.put("subtract", (a, b) -> a - b);
    operators.put("multiply", (a, b) -> a * b);
    operators.put("divide", (a, b) -> a / b);
  }

  public static int arithmetic(int a, int b, String operator) {
    return arithmetic(a, b, operators.get(operator));
  }

  private static Integer arithmetic(int a, int b, BinaryOperator<Integer> operator) {
    return Optional.ofNullable(operator).orElse((x, y) -> 0).apply(a, b);
  }
}
~~~

