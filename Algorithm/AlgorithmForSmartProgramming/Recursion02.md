# Recursive Thinking 순환적으로 사고하기
>수학함수 뿐만 아니라 다른 많은 문제들을 recursion으로 해결할 수 있다.

### 문자열 길이 계산
~~~
if the string is empty
  return 0;
else
  return 1 plus the length of the string that
    excludes the first character;
~~~
~~~java
public static int length(String str) {
  if (str.equals(""))
    return o;
  else
    return 1+length(str.substring(1));
}
~~~

### 문자열의 프린트
~~~java
public static void printChars(String str) {
  if (str.length()==0)
    return;
  else {
    System.out.print(str.charAt(0));
    printChars(str.substring(1));
  }
}
~~~

### 문자열을 뒤집어 프린트
~~~java
  public static void printCharsReverse(String str) {
    if (str.length() == 0)
      return;
    else {
        printCharsReverse(str.substring(1));
        System.out.print(str.charAt(0));
    }
  }
~~~

### 2진수로 변환하여 출력
- 음이 아닌 정수 n을 이진수로 변환하여 인쇄한다.
~~~java
public void printInBinary(int n) {
  if (n<2)
    System.out.print(n);
  else {
    printInBinary(n/2); ///// n을 2로 나눈 몫을 먼저 2진수로 변환하여 인쇄한 후
    System.out.print(n%2);  ///// n을 2로 나눈 나머지를 인쇄한다.
  }
}
~~~

### 배열의 합 구하기
~~~java
public static int sum(int n, int [] data) {
  if (n<=0)
    return 0;
  else
    return sum(n-1, data) + data[n-1];
}
~~~

### 데이터파일로 부터 n개의 정수 읽어오기
- Scanner in이 참조하는 파일로 부터 n개의 정수를 입력받아 배열 data에 저장한다.
~~~java
public void readFrom(int n, int [] data, Scanner in) {
  if (n==0)
    return;
  else {
    readFrom(n-1, data, in);
    data[n-1] = in.nextInt();
  }
}
~~~

### Recursion vs. Iteration
- 모든 순환함수는 반복문(iteration)으로 변경 가능
- 그 역도 성립한다. 즉, **모든 반복문은 recursion으로 표현 가능**
- 순환함수는 복잡한 알고리즘을 단순하고 알기 쉽게 표현하는 것을 가능하게함
- 하지만 함수 호출에 따른 오버헤드가 있다. (매개변수 전달, Activation Frame 생성 등)
