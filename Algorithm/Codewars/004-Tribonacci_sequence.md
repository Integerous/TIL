>이 문제 풀고나서 7급(7kyu)으로 승급!!  
![codewars](https://www.codewars.com/users/Integerous/badges/large)
# Tribonacci Sequence
>6kyu  
>solved with Java  
>https://www.codewars.com/kata/tribonacci-sequence/java

## Instructions
Well met with Fibonacci bigger brother, AKA Tribonacci.

As the name may already reveal, it works basically like a Fibonacci, but summing the last 3 (instead of 2) numbers of the sequence to generate the next. And, worse part of it, regrettably I won't get to hear non-native Italian speakers trying to pronounce it :(

So, if we are to start our Tribonacci sequence with [1, 1, 1] as a starting input (AKA signature), we have this sequence:

[1, 1 ,1, 3, 5, 9, 17, 31, ...]
But what if we started with [0, 0, 1] as a signature? As starting with [0, 1] instead of [1, 1] basically shifts the common Fibonacci sequence by once place, you may be tempted to think that we would get the same sequence shifted by 2 places, but that is not the case and we would get:

[0, 0, 1, 1, 2, 4, 7, 13, 24, ...]
Well, you may have guessed it by now, but to be clear: you need to create a fibonacci function that given a signature array/list, returns the first n elements - signature included of the so seeded sequence.

Signature will always contain 3 numbers; n will always be a non-negative number; if n == 0, then return an empty array and be ready for anything else which is not clearly specified ;)

# My Solution
~~~java
public class Xbonacci {
      
  public double[] tribonacci(double[] s, int n) {
      // hackonacci me
   
      double[] x = new double[n];
      
      if(n>2){
        x[0] = s[0];
        x[1] = s[1];
        x[2] = s[2];
       
        for(int i=3; i<n; i++)
          x[i] = x[i-3] + x[i-2] + x[i-1]; 
      }else if(n==2){
        x[0] = s[0];
        x[1] = s[1];
      }else if(n==1)
        x[0] = s[0];
      
      return x;
  }
}
~~~

## Result & Review
>Time: 2208ms  
- n이 0,1,2 일 때 문제가 발생한다. 밑에 Best Practice보다 Clever로 선정된 코드가 더 마음에 든다. 왜 저런 생각을 못했을까..

## Best Practice
~~~java
import java.util.Arrays;

public class Xbonacci {
  public double[] tribonacci(double[] s, int n) {

      double[] tritab=Arrays.copyOf(s, n);
      for(int i=3;i<n;i++){
        tritab[i]=tritab[i-1]+tritab[i-2]+tritab[i-3];
      }
      return tritab;

    }
}
~~~

## Clever
~~~java
public class Xbonacci {

  public double[] tribonacci(double[] s, int n) {
      double[] r = new double[n];
      for(int i = 0; i < n; i++){
        r[i] = (i<3)?s[i]:r[i-3]+r[i-2]+r[i-1];
      }
      return r;
  }
}
~~~



