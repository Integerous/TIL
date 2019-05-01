# 추상(abstract) 클래스가 필요한 기본적인 이유
><Java의 정석>으로 Java의 개념들을 공부한 나는 추상클래스가 언제나 찝찝했다.  
아마 개발을 공부하기 시작한지 얼마 안되어 학습한 내용이었기 때문에 적당한 수준으로 이해하고 넘어갔던 것이다.  
찝찝한 상태로 남아있는 개념들을 하나씩 바로 잡을 계획이었는데, 추상클래스에 대해 조금 더 이해 할 기회가 생겨 정리해본다. 

<Java의 정석>에서는 추상클래스와 추상메서드가 필요한 이유를 명확하게 설명하지 않았다.  
(아마도 다양한 이유가 있기 때문에 애매하게 설명한 것 같다.) 

때문에 나는 지금까지 추상클래스(메서드)가 필요한 이유를  
`자손 클래스에서 추상메서드를 반드시 구현하도록 강요하기 위해서`라고 이해하고 있었다.  
그리고 아직 실무에서 추상클래스(메서드)를 작성할 일이 없었기 때문에 발전이 없는 상태였다.  

하지만 [Java로 배우는 자료구조](https://www.inflearn.com/course/java-%EC%9E%90%EB%A3%8C%EA%B5%AC%EC%A1%B0/lecture/7465) 강의에서는 
조금 다르게 설명했고, 난 이 예제와 설명이 더 와닿았다.


### 예제를 단순화 해보면,


1. Event 라는 부모클래스가 있고,
    ~~~java
    public class Event {
        ...
    }
    ~~~
2. Event를 상속받은 자식클래스 aEvent, bEvent, cEvent가 있으며, 각각 isRelevant() 메서드를 구현하였다.
    ~~~java
    public class aEvent extends Event {
        ...
        public boolean isRelevant(Data data) {
            return logic.a;
        }
    }
    ~~~
    ~~~java
    public class bEvent extends Event {
        ...
        public boolean isRelevant(Data data) {
            return logic.b;
        }
    }
    ~~~
    ~~~java
    public class cEvent extends Event {
        ...
        public boolean isRelevant(Data data) {
            return logic.c;
        }
    }
    ~~~

3. 그리고 다른 클래스의 어느 메서드에서 Event 타입으로 선언된 배열 events[]의 각 원소를 isRelevant()메소드로 검증하는 로직을 작성했다. (events[] 배열의 각 원소는 aEvent, bEvent, cEvent 타입의 원소들이다.)
    ~~~java
    public class someClass() {
        ...
        Event events[] = new Event[100];
        ...
        
        public void someMethod() {
            for(int i=0; i<n; i++)
                if(events[i].isRelevant(someData)) // 컴파일 에러!!!
                    system.out.println( events[i].toString());
        }
    ~~~

    그런데 isRelevant()메소드에서 컴파일 에러가 발생한다.  
    왜냐하면 events[] 배열은 Event 타입으로 선언되었는데, Event 클래스에는 isRelevant() 메서드가 정의되어 있지 않기 때문이다.  

    배열의 각 원소가 Event 클래스를 상속받은 클래스의 객체이고, 각 클래스는 isRelevant() 메서드가 구현되어 있지만,  
    컴파일러는 Event 클래스에 isRelevant() 메서드가 없는 것을 허용하지 않는 것이다.  

    (`events[i].toString()`에서 컴파일 에러가 나지 않는 이유는 toString() 메서드는 모든 클래스의 조상 클래스인 Object 클래스에 정의된 메서드이기 때문이다.)

4. 이 때, 단순히 컴파일 에러만 피하고자 한다면,  
아래와 같이 Event 객체에 사용될 일 없는 isRelevant() 메서드를 정의할 수 있을 것이다.

    ~~~java
    public class Event {
        ...

        public boolean isRelevant(Data data) {
            return false;
        }
    }
    ~~~

    위와 같이 메서드를 정의하면 컴파일 에러는 사라지고 정상적으로 동작한다.  
    하지만 사용되지 않을 메서드를 구현하는 것은 올바른 해결책이 아니다.  

5. 이 문제를 해결하려면 isRelevant()메서드를 추상 메서드로 만들면 된다.  
(그리고 아래 코드와 같이 추상 메서드가 하나라도 있으면 그 클래스는 추상 클래스가 된다.)

    ~~~java
    public abstract class Event {
        ...

        public abstract boolean isRelevant(Data data);
    }
    ~~~
    
    
이 예제가 이해하기 쉬웠던 이유는 나같은 초보자가 코드를 짜면서 맞이하는 상황과 비슷한 흐름이기 때문이다.  
나의 경우에는, 큰 그림을 보지 못하고 우선 동작하는 코드를 작성한다.  
그러다 보면 코드 중복이 발견되고, 그 중복을 해결하기 위해 그제서야 추상화와 상속을 고민한다.

때문에 부모클래스에 추상 메서드를 선언하여 추상클래스로 만들고, 그 클래스를 상속받은 서브클래스들에서 추상메서드를 구현하는 방식으로 설명하는 <Java의 정석>의 예제는 초보개발자 입장에서 제대로 이해할 수 없었던 것이다.

>물론 추상 메서드와 추상 클래스의 역할과 활용에 대해서는 더 깊은 이야기들이 있겠지만,  
실무에서 마주하게 되길 바라면서 우선은 이 정도로 정리해본다.

### *Reference
- [Java로 배우는 자료구조 - 권오흠](https://www.inflearn.com/course/java-%EC%9E%90%EB%A3%8C%EA%B5%AC%EC%A1%B0/lecture/7465)
