# Arithmetic Progression
>7kyu  
>solved with Java  
>https://www.codewars.com/kata/arithmetic-progression/java

## Instructions
In your class, you have started lessons about arithmetic progression. Since you are also a programmer, you have decided to write a function that will return the first n elements of the sequence with the given common difference d and first element a. Note that the difference may be zero!

The result should be a string of numbers, separated by comma and space.

Example
~~~
# first element: 1, difference: 2, how many: 5
arithmetic_sequence_elements(1, 2, 5) == "1, 3, 5, 7, 9"
~~~

# My Solution
~~~java
class Progression {
  
  public static String arithmeticSequenceElements(int first, int step, long total) {
    
    
    String result = Integer.toString(first);
    int tmp =  first;
    
    for(int i=1; i<total; i++) {
      tmp += step;
      result += ", " + tmp;
    }
    
    return result;
  }
}
~~~

## Result & Review
- comma 처리가 바로 생각이 안났고, for문 사용 시 i를 무조건 사용하려는 습관을 버려야함.
- 람다를 빨리 익히자

## Best Practice
~~~java
import static java.util.stream.IntStream.iterate;
import static java.util.stream.Collectors.joining;

public class Progression {
  
  public static String arithmeticSequenceElements(int a, int d, long n) {
    return iterate(a, t -> t + d).limit(n).mapToObj(Integer::toString).collect(joining(", "));
  }
}
~~~
