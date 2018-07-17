# Recursion 응용 - 멱집합 (Powerset)
>멱집합 : 어떤 집합의 모든 부분집합의 집합

- {a,b,c,d,e,f}의 모든 부분집합을 나열하려면
  - a를 제외한 {b,c,d,e,f}의 모든 부분집합들을 나열하고
  - {b,c,d,e,f}의 모든 부분집합에 {a}를 추가한 집합들을 나열하는데, 그럴려면
    - {c,d,e,f}의 모든 부분집합에 {a}를 추가한 집합들을 나열하고
    - {c,d,e,f}의 모든 부분집합에 {a,b}를 추가한 집합들을 나열한다. 그럴려면 ...(생략)

## Powerset
~~~
powerSet(P,S) // Mission: S의 멱집합을 구한 후 각가에 집합 P를 합집합하여 출력하라
if S is an empty set
  print P;
else
  let t be the first element of S;
  powerSet(P, S-{t});
  powerSet(P U {t}, S-{t}); 
~~~

~~~java
private static char data[] = {'a','b','c','d','e','f'};
private static int n=data.length;
private static boolean [] include = new boolean [n];

public static void powerSet(int k) {
  if (k==n) {
    for (int i=0; i<n; i++)
      if (include[i]) System.out.print(data[i] + " ");
    System.out.println();
    return;
  }
  include[k]=false;
  powerSet(k+1);
  include[k]= true;
  powerSet(k+1);
} 

