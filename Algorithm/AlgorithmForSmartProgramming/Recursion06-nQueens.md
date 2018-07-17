# Recursion 응용 - N Queens Problem
![nQueens](https://github.com/namjunemy/TIL/raw/master/Algorithm/img/n_queens_01.png?raw=true)
- 가로 세로 크기가 n인 2차원 체스보드 위에 n개의 말을 놓을 때,
- 각 말의 상하좌우 대각선의 연장선 위에 어떠한 말도 없도록 말을 놓는 문제


## 상태공간 트리
![상태공간트리](https://github.com/namjunemy/TIL/raw/master/Algorithm/img/n_queens_02.png?raw=true)
- 찾는 해를 포함하는 트리
- 즉 해가 존재한다면 그것은 반드시 이 트리의 어떤 한 노드에 해당한다. 따라서 이 트리를 체계적으로 탐색하면 해를 구할수 있다.
- 하지만 모든 노드를 탐색해야 하는 것은 아니다.
  - 해가 될 수 있는 요건을 충족하지 않는 infeasible 한 노드들의 하위 노드는 탐색할 필요가 없다.

## Backtracking (되추적 기법)
- 상태공간 트리를 **깊이 우선 방식**으로 탐색하여 해를 찾는 알고리즘
- Recursion을 사용하거나 Stack을 사용할 수 있지만, 대부분 recursion으로 구현한다.

## Design Recursion
~~~
return-type queens( arguments) //매개변수는 내가 현재 트리의 어떤 노드에 있는지를 지정해야 한다.
{
  if non-promising(꽝)
    report failure and return;
  else if success
    report answer and return;
  else
    visit children recursively;
}
~~~
~~~java
int[] cols = new int [N+1];
boolean queens( int level )  // 전역변수 cols와 매개변수 level로 노드의 현재 위치를 제공한다.
{
  if (!promising(level))
    return false;
  else if (level == N) {
    for (int i=1; i<=N; i++)
      System.out.println("(" + i + ", " + cols[i] + ")");
    return true;
  }
  for (int i=1; i<=N; i++) {
    cols[level+1] = i;
    if (queens(level+1))
      return true;
  }
  return false;
}
~~~

## Promising Test

~~~java
boolean promising( int level)
{
  for (int i=1; i<level; i++) {
    if (cols[i]==cols[level]) // 같은 열에 놓였는지 검사
      return false;
    else if (level-i == math.abs(cols[level]-cols[i]) // 같은 대각선에 놓였는지 검사
      return false;
  }
  return true;
}
~~~ 
