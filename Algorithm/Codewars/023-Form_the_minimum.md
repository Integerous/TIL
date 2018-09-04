# Form the minimum
>7kyu  
>failed  
>https://www.codewars.com/kata/form-the-minimum/java

## Instructions
### Task
Given a list of digits, return the smallest number that could be formed from these digits, using the digits only once ( ignore duplicates).

### Notes :
Only positive integers will be passed to the function (> 0 ), no negatives or zeros.

### Input >> Output Examples
~~~java
1- minValue ({1, 3, 1})  ==> return (13)
~~~
Explanation:
(13) is the minimum number could be formed from {1, 3, 1} , Without duplications

~~~java
2- minValue({5, 7, 5, 9, 7})  ==> return (579)
~~~
Explanation:
(579) is the minimum number could be formed from {5, 7, 5, 9, 7} , Without duplications

~~~java
3- minValue({1, 9, 3, 1, 7, 4, 6, 6, 7}) return  ==> (134679)
~~~
Explanation:
(134679) is the minimum number could be formed from {1, 9, 3, 1, 7, 4, 6, 6, 7} , Without duplications
# My Solution
~~~java
import java.util.Arrays;

public class Minimum{

	public static int minValue(int[] values){
       
    for(int i=0; i<values.length-1; i++){
      for(int j=i+1; j<values.length; j++){
        
        if(values[i] > values[j]){
          int temp;
          temp = values[i];
          values[i] = values[j];
          values[j] = temp;
        }
        else if(values[i] == values[j])
           values[j] = null;
     
      }  
    }
       return Integer.join("",values);
	}

}
  
~~~

## Result & Review
>Time out! 풀이 실패!
- 배운 점
  - String.valueOf()
  - TreeSet()
  - 정규표현식 \\[|\\]|,|\\s
  - .contains()
## Best Practice
~~~java
import java.util.stream.Collectors;
import java.util.*;
public class Minimum{

  public static int minValue(int[] values){
     String s = Arrays.stream(values)
                .sorted()
                .distinct()
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(""));
        return Integer.valueOf(s);
  }

}
~~~

## Clever #1
~~~java
import java.util.Arrays;

public class Minimum{

  public static int minValue(int[] values) {
    return Arrays.stream(values)
      .distinct()
      .sorted()
      .reduce(0, (a, b) -> 10 * a + b);
  }
}
~~~

## Clever #2
~~~java
import java.util.TreeSet;
public class Minimum{

  public static int minValue(int[] values){
    TreeSet set = new TreeSet();
    for(int i:values){
      set.add(i);
    }
    return Integer.valueOf(set.toString().replaceAll("\\[|\\]|,|\\s", ""));  }

}
~~~

## Clever #3
~~~java
import java.util.Arrays;
public class Minimum{

  public static int minValue(int[] values){
  
    String result = "";
        Arrays.sort(values);
        for (int i : values) {
            if(!result.contains(String.valueOf(i))) result+=String.valueOf(i);
        }
        return Integer.parseInt(result);
    }
  }
  ~~~
