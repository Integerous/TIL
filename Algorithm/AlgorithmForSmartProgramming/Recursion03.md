# Designing Recursion (순환 알고리즘의 설계)

- Recursion의 조건
  - 적어도 하나의 base case, 즉 순환되지 않고 종료되는 case가 있어야함.
  - 모든 case는 결국 base case로 수렴해야 한다.
- 암시적(implicit) 매개변수를 **명시적(explicit) 매개**변수로 바꿔라.

## 순차 탐색 (Sequential Search)
~~~java
int search(int [] data, int n, int target) {
  for (int i=0; i<n; i++)
    if (data[i]==target)
      return i;
  return -1;
}
~~~
이 함수의 미션은 data[0]에서 data[n-1] 사이에서 target을 검색하는 것이다.  
하지만 검색 구간의 시작 인덱스 0은 보통 생략한다. 즉 **암시적 매개변수**이다.

## 매개변수를 명시화한 순차 탐색
~~~java
int search(int [] data, int begin, int end, int target) {
  if (begin > end)
    return -1;
  else if (target == data[begin])
    return begin;
  else
    return search(data, begin+1, end, target);
}
~~~
이 함수의 미션은 data[begin]에서 data[end] 사이의 target을 검색한다.  
즉, 검색구간의 시작점을 명시적(explicit)으로 지정한다.
  - 이 함수를 search(data, 0, n-1, target)으로 호출한다면 앞 페이지의 함수와 완전히 동일한 일을 한다.
  - 하지만 시작점이 0가 되면 Recursion을 사용할 수 없기 때문에 **설계할 때 부터 0이 아닌 begin으로 설계한다.**

## 순차탐색 다른 버전 #1
>뒤에서 앞으로 찾기
~~~java
int search(int [] data, int begin, int end, int target) {
  if (begin > end)
    return -1;
  else if (target == data[end])
    return end;
  else
    return search(data, begin, end-1, target);
}
~~~
이 함수의 미션은 data[begin]에서 data[end] 사이에서 target을 검색한다.  
즉, 검색구간의 시작점을 명시적(explicit)으로 지정한다.

## 순차탐색 다른 버전 #2
>가운데 나누어서 찾기 (binary search와는 다름)
~~~java
int search(int[] data, int begin, int end, int target) {
  if (begin > end)
    return -1;
  else {
    int middle = (begin+end)/2;
    if (data[middle] == target)
      return middle;
    int index = search(data, begin, middle-1, target);
    if (index != -1)
      return index;
    else
      return search(data, middle+1, end, target);
  }
}
~~~

## 매개변수의 명시화 : 최대값 찾기
~~~java
int findMax(int [] data, int begin, int end) {
  if (begin==end)
    return data[begin];
  else
    return Math.max(data[begin], findMax(data, begin+1, end));
}

## 최대값 찾기 다른 버전
~~~java
int findMax( int [] data, int begin, int end) {
  if (begin==end)
    return data[begin]
  else {
    int middle = (begin+end)/2;
    int max1 = findMax(data, begin, middle);
    int max2 = findMax(data, middel+1, end);
    return Math.max(max1,max2);
}
~~~

## Binary Search 이진검색
>이진검색은 데이터가 크기순으로 정렬되어 있을 때만 사용 가능
~~~java
public static int binarySearch(String[] items, String target, int begin, int end) {
  if (begin>end)
    return -1;
  else {
    int middle = (begin+end)/2;
    int compResult = target.compareTo(items[middle]);
    if (compResult == 0)
      return middle;
    else if (compResult<0)
      return binarySearch(items, target, begin, middel-1);
    else
      return binarySearch(items, target, middle+1, end);
  }
}
~~~
