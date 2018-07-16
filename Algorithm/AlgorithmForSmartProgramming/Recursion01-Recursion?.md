>[Infearn <영리한 프로그래밍을 위한 알고리즘 강좌> - 부경대 권오흠 교수](https://www.inflearn.com/course/%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98-%EA%B0%95%EC%A2%8C/)
강의를 듣고 정리한 내용

# 순환(Recursion) 이란?
- 자기 자신을 호출하는 함수
    ~~~
    void func(...)
    {
      ...
      func(...)
      ...
    }
    ~~~
## 무한루프에 빠질 수 있으므로, 빠지지 않게하는 구조로 작성해야 한다.
    ~~~java
    public static void func(int k){
      if(k<=0)    ///////// Base case : 적어도 하나의 recursion에 빠지지 않는 경우가 존재해야한다.
        return;   /////////
      else {
        sysout("hello");
        func(k-1);    ////////// Recursive case : recursion을 반복하다보면 결국 base case로 수렴해야한다.
      }
    }
    ~~~
## Factorial : n! 를 순환으로 구현하기
    ~~~java
    public static int factorial(int n){
      if (n==0)
        return 1;
      else
        return n*factorial(n-1);
    }
    ~~~
## X^n 을 순환으로 구현하기
    ~~~java
    public static double power(double x, int n) {
      if(n==0)
        return 1;
      else
        return x * power(x,n-1);
    }
    ~~~
## Fibonacci Number 순환으로 구현하기
  - Fibonacci Number : F(n) = F(n-1) + F(n-2)
  ~~~java
  public int fibonacci(int n) {
    if (n<2)
      return n;
    else
      return fibonacci(n-1) + fibonacci(n-2);
  ~~~
## 최대공약수 : Euclid Method 순환으로 구현하기
  ~~~
  m>=n 인 두 양의 정수 m과 n에 대해서 m이 n의 배수이면 gcd(m,n)=n 이고,  
  그렇지 않으면 gcd(m,n)= gcd(n,m%n)이다.
  ~~~
    ~~~java
    public static int gcd(int m, int n) {
      if (m<n) {
        int tmp = m;
              m = n;
              n = tmp; // swap m and n
      }
      if (m%n==0)
        return n;
      else
        return gcd(n, m%n);
    }
    ~~~
## 좀 더 단순한 Euclid Method
    ~~~java
    public static int gcd(int p, int q) {
      if (q==0)
        return p;
      else
        return gcd(q,p%q);
    ~~~
