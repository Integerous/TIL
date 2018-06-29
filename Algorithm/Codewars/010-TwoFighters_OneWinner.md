# Two Fighters, One Winner
>7kyu  
>failed with Java  
>http://www.codewars.com/kata/two-fighters-one-winner/java

## Instructions
Description:
Create a function that returns the name of the winner in a fight between two fighters.

Each fighter takes turns attacking the other and whoever kills the other first is victorious. Death is defined as having health <= 0.

Each fighter will be a Fighter object/instance. See the Fighter class below in your chosen language.

Both health and damagePerAttack (damage_per_attack for python) will be integers larger than 0. You can mutate the Fighter objects.

Example:
~~~
public class Fighter {
  public String name;
  public int health, damagePerAttack;
  public Fighter(String name, int health, int damagePerAttack) {
    this.name = name;
    this.health = health;
    this.damagePerAttack = damagePerAttack;
  }
}
~~~
# My Solution
~~~java
public class Kata {
  public static String declareWinner(Fighter fighter1, Fighter fighter2, String firstAttacker) {
    // Your code goes here. Have fun!
    
    int A = fighter1.health;
    int a = fighter1.damagePerAttack;
    
    int B = fighter2.health;
    int b = fighter2.damagePerAttack;
    
      
    if( firstAttacker == fighter1.name ){
      if( B/a < A/b || B/a == A/b ){
        return fighter1.name;
      }else
        return fighter2.name;
    }else
      if( A/b < B/a || B/a ==A/b){
        return fighter2.name;
      }else
        return fighter1.name;
        
        // 코드 미완성
  }
}
~~~

## Result & Review
>Time-out! 시간 초과로 실패!
- firstAttacker는 사실상 A/b와 B/a가 같을 때만 의미있는 것인데 계속 같이 생각하는 오류를 범했다.
- Best Practice 코드는 O(1)로 최고로 좋지만 순위는 Clever 코드 O(n)가 높았다.
- for문이나 while문을 돌리지 않고 해결할 수 있다고 생각한 것은 잘한 선택이었다.
- 하지만 double형으로 캐스팅하고 이를 Math.ceil로 정수화하는 것을 생각해내지 못하고 조잡하게 풀어나가려했다.

## Best Practice
~~~java
public class Kata {
  public static String declareWinner(Fighter fighter1, Fighter fighter2, String firstAttacker) {
        int moves1 = (int) Math.ceil( (double)fighter2.health / fighter1.damagePerAttack);
        int moves2 = (int) Math.ceil( (double)fighter1.health / fighter2.damagePerAttack);
        if (moves1 > moves2) {
            return fighter2.name;
        } else if (moves1 < moves2) {
            return fighter1.name;
        } else {
            return firstAttacker;
        }
  }
}
~~~

## Clever
~~~java
public class Kata {
  public static String declareWinner(Fighter fighter1, Fighter fighter2, String firstAttacker) {
    Fighter a=fighter1, b=fighter2;
    if (firstAttacker.equals(fighter2.name)) {
      a = fighter2; b = fighter1;
    }    
    while (true) {      
      if ((b.health -= a.damagePerAttack) <= 0) return a.name;  // a wins
      if ((a.health -= b.damagePerAttack) <= 0) return b.name;  // b wins
    }
  }
}
~~~



