# 지뢰찾기 과제
> 어느 회사의 기술면접 사전제출 과제  
> 정작 면접에서 코드 리뷰는 안해줘서 아쉬웠다.


## 과제의 요구사항
지뢰찾기와 유사한 게임을 제작한다고 가정하고, 아래 조건에 따라서 
「각 사각형에 표시될 숫자를 "입력"하는 로직」작성
* 사각형은 가로 10줄 , 세로 10줄임
* 지뢰는 10개이며 가급적 랜덤에 가깝게 배치해야함
* 각 사각형에 표시될 숫자는, 자신을 제외한 주변 8개 사각형에 포함된 폭탄의 갯수임
* 모든 사각형(100개)에 대한 숫자를 구해야함 (지뢰를 별도 falg로 표기하지 말 것)
* 가급적 코드의 깊이(indent)를 얕게 구현할 것
* else 예약어 사용을 최소화할 것 (삼항연산자 및 switch등 분기 연산자 포함)
* 함수는 가급적 작은 기능을 가지도록 할 것

## 접근 방식
- 모든 사각형을 돌면서 주변 지뢰의 갯수를 구하는 것은 비효율적이라는 생각이 들었다.
- 그래서 `지뢰 주변 사각형에 ++연산` 하는 방식으로 풀었다.
- 사각형은 100개인데 지뢰는 10개이므로 지뢰 주변 사각형만 연산하는 것이 훨씬 효율적이다.

## 지뢰 위치 중복 문제 해결
- 지뢰 위치가 중복되지 않을 경우에만 count하며 지뢰 배치
- 지뢰 위치가 중복될 경우, 아무 작업 없이 while문의 다음 반복으로 넘어감
- count가 10개가 될 때까지만 반복하므로 중복되지 않는 지뢰 10개 배치 가능

## 코드

~~~java
public class Racos {

    // 지뢰찾기 판 생성
    static int[][] board = new int[10][10];
    static int[][] mineMap = new int[10][10];

    public static void main(String[] args) {
        Racos homework = new Racos();

        homework.mineGenerator(); // 지뢰 생성 및 사각형에 숫자 입력
        homework.mineMapTest(); // 지뢰 위치 출력 (테스트용)
        homework.resultTest(); // 모든 사각형 숫자 출력 (테스트용)
    }
    
    // 지뢰 생성 및 사각형에 숫자 입력
    void mineGenerator() {
        int count = 0;  // 지뢰위치 중복 방지용 카운터

        while (count < 10) {
            // 지뢰 위치 랜덤 생성
            int x = (int) (Math.random() * 10);
            int y = (int) (Math.random() * 10);

            // 생성된 지뢰 위치가 중복되지 않을 경우에만 진행
            if(mineMap[x][y] < 1) {
                mineMap[x][y] = 1;  // 지뢰 위치에 1 할당 (테스트용)
                numberCounter(x, y); // 지뢰 주변 8개 사각형의 값을 1씩 증가
                count++; // 할당된 지뢰 갯수 카운트
            }
        }
    }

    // 지뢰찾기 판을 벗어나는 위치 체크
    boolean positionCheck(int x, int y) {
        if (x < 0 || y < 0 || x >= 10 || y >= 10) {
            return false;
        }
        return true;
    }
    
    // 지뢰 주변 사각형 숫자++
    void numberCounter(int x, int y) {
        if (positionCheck(x - 1, y - 1)) {
            board[x - 1][y - 1]++;
        }
        if (positionCheck(x - 1, y)) {
            board[x - 1][y]++;
        }
        if (positionCheck(x - 1, y + 1)) {
            board[x - 1][y + 1]++;
        }
        if (positionCheck(x, y - 1)) {
            board[x][y - 1]++;
        }
        if (positionCheck(x, y + 1)) {
            board[x][y + 1]++;
        }
        if (positionCheck(x + 1, y - 1)) {
            board[x + 1][y - 1]++;
        }
        if (positionCheck(x + 1, y)) {
            board[x + 1][y]++;
        }
        if (positionCheck(x + 1, y + 1)) {
            board[x + 1][y + 1]++;
        }
    }
    
    // 지뢰 위치 출력 (테스트용)
    void mineMapTest() {
        System.out.println("=====지뢰 위치=====");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(mineMap[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    // 모든 사각형 숫자 출력 (테스트용)
    void resultTest() {
        System.out.println("==사각형의 숫자들==");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

}
~~~
