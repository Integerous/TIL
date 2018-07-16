# Recursion 응용 - 미로찾기

## 1. Recursive Thinking
- 현재 위치에서 출구까지 가는 경로가 있으려면
  1. 현재 위치가 출구이거나
  2. 이웃한 셀들 중 하나에서 현재 위치를 지나지 않고 출구까지 가는 경로가 있거나

## 2. Peudo Code (Decision Problem (답이 yes/no인 문제))
~~~java
boolean findPath(x,y)
  if (x,y) is the exit
    return true;
  else
    for each neighbouring cell (x',y') of (x,y) do
      if (x', y') is on the Pathway
        if findePath(x',y')
          return true;
    return false;
~~~
- 위의 코드는 무한루프에 빠질 수 있으므로 아래와 같이 작성한다.
~~~java
boolean findPath(x,y)
  if (x,y) is the exit
    return true;
  else
    mark (x,y) as a visited cell;
    for each neighbouring cell (x',y') of (x,y) do
      if (x', y') is on the Pathway and not visited
        if findePath(x',y')
          return true;
    return false;
~~~
- 아래와 같이 현재 위치를 먼저 확인할 수 있다. 위의 코드와는 취향의 차이이다.
~~~java
boolean findPath(x,y)
  if (x,y) is either on the wall or a visited cell
    return false;
  else if (x,y) is the exit
    return true;
  else
    mark (x,y) as a visited cell;
    for each neighbouring cell (x',y') of (x,y) do
      if findPath(x',y')
          return true;
    return false;
~~~

## 3. Class Maze
~~~java
public class Maze {
  private static int N=8;
  private static int [][] maze = {
        {0,0,0,0,0,0,0,1},
        {0,1,1,0,1,1,0,1},
        {0,0,0,1,0,0,0,1},
        {0,1,0,0,1,1,0,0},
        {0,1,1,1,0,0,1,1},
        {0,1,0,0,0,1,0,1},
        {0,0,0,1,0,0,0,1},
        {0,1,1,1,0,1,0,0},
  };
  
  private static final int PATHWAY_COLOUR = 0;  //white
  private static final int WALL_COLOUR = 1;     //blue
  private static final int BLOCKED_COLOUR = 2;  //red
  private static final int PATH_COLOUR = 3;     //green


  public static boolean findMazePath(int x, int y) {
    if (x<0 || y<0 || x>=N || y>=N)
      return false;
    else if (maze[x][y] != PATHWAY_COLOUR)
      return false;
    else if (x==N-1 && y==N-1) {
      maze[x][y] = PATH_COLOUR;
      return true;
    }
    else {
      maze[x][y] = PATH_COLOUR;
      if (findMazePath(x-1,y) || findMazePath(x,y+1)
          || findMazePath(x+1,y) || findMazePath(x,y-1) {
          return true;
      }
      maze[x][y] = BLOCKED_COLOUR;  //dead end
      return false;
    }
  }

  public static void main(String[] args) {
    printMaze();
    findMazePath(0,0);
    printMaze();
  }

}
~~~
