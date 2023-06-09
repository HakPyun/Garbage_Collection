# Garbage Collection(Java)

<b>Garbage Collection이란?</b>

Garbage Collection은 자바의 메모리 관리 방법 중의 하나로 JVM의 Heap 영역에서 동적으로 할당했던 메모리 영역 중 필요 없게 된 메모리 영역을 주기적으로 삭제하는 프로세스를 말한다.

Java에서는 JVM에 탑재되어 있는 가비지 컬렉터가 메모리 관리를 대행해주기 때문에 개발자 입장에서 메모리 관리, 메모리 누수(Memory Leak) 문제에서 대해 완벽하게 관리하지 않아도 되어 오롯이 개발에만 집중할 수 있다는 장점이 있다.

<b>Garbage Collection이 필요한 이유</b>

객체들은 Heap영역에서 생성이 되는데 Method, Stack영역에서는 객체의 주소로만 참조한다.

이렇게 생성된 객체들이 메서드가 끝나는 등의 특정 이벤트들로 인하여 Heap 영역 객체의 메모리 주소를 가지고 있는 참조 변수가 삭제되는 현상이 발생하면

Heap영역에서 참조되지 않은 객체들이 발생하게되는데 이러한 객체들을 제거하기 위해 Garbage Collection이 존재한다.

그렇지 않으면 사용되지도 않는데 Heap 영역을 차지하는 쓰레기(Garbage)들이 남아있게 되기 때문이다.(메모리 누수)

# Garbage Collection(Java) 매커니즘

<b>Young, Old, Minor GC, Major GC</b>

JVM의 Heap영역은 처음 설계될 때 다음의 2가지를 전제로 설계되었다.

    대부분의 객체는 금방 접근 불가능한 상태(Unreachable)가 된다.
    오래된 객체에서 새로운 객체로의 참조는 아주 적게 존재한다.
 
즉, 객체는 대부분 일회성이며, 메모리에 오랫동안 남아있는 경우는 드물다는 것이다. 그렇기 때문에 객체의 생존 기간에 따라 물리적인 Heap 영역을 나누게 되었고 Young, Old 총 2가지 영역으로 설계되었다.

    Young 영역
    새롭게 생성된 객체가 할당되는 영역
    대부분의 객체가 금방 Unreachable 상태가 되기 때문에, 많은 객체가 Young 영역에 생성되었다가 사라진다.
    Young 영역에 대한 가비지 컬렉션(Garbage Collection)을 Minor GC라고 부른다.
    
    Old 영역
    Young영역에서 Reachable 상태를 유지하여 살아남은 객체가 복사되는 영역
    Young 영역보다 크게 할당되며, 영역의 크기가 큰 만큼 가비지는 적게 발생한다.
    Old 영역에 대한 가비지 컬렉션(Garbage Collection)을 Major GC라고 부른다.
    
<b>Garbage Collection 동작방식</b>

Young 영역과 Old 영역은 서로 다른 메모리 구조로 되어 있기 때문에, 세부적인 동작 방식은 다르다. 하지만 기본적으로 가비지 컬렉션이 실행된다고 하면 다음의 2가지 공통적인 단계를 따르게 된다.

        Stop The World
        Mark and Sweep
 
<b>1. Stop The World</b>

Stop The World는 가비지 컬렉션을 실행하기 위해 JVM이 애플리케이션의 실행을 멈추는 작업이다. GC가 실행될 때는 GC를 실행하는 쓰레드를 제외한 모든 쓰레드들의 작업이 중단되고, GC가 완료되면 작업이 재개된다. 당연히 모든 쓰레드들의 작업이 중단되면 애플리케이션이 멈추기 때문에, JVM에서도 이러한 문제를 해결하기 위해 다양한 실행 옵션을 제공하고 있다.

<b>2. Mark and Sweep</b>

Mark: 사용되는 메모리와 사용되지 않는 메모리를 식별하는 작업

Sweep: Mark 단계에서 사용되지 않음으로 식별된 메모리를 해제하는 작업

Stop The World를 통해 모든 작업을 중단시키면, GC는 스택의 모든 변수 또는 Reachable 객체를 스캔하면서 각각이 어떤 객체를 참고하고 있는지를 탐색하게 된다. 그리고 사용되고 있는 메모리를 식별하는데, 이러한 과정을 Mark라고 한다. 이후에 Mark가 되지 않은 객체들을 메모리에서 제거하는데, 이러한 과정을 Sweep라고 한다.

<b>Minor GC의 동작 방식</b>

Minor GC를 정확히 이해하기 위해서는 Young 영역의 구조에 대해 이해를 해야 한다. Young 영역은 1개의 Eden 영역과 2개의 Survivor 영역, 총 3가지로 나뉘어진다.

Eden 영역: 새로 생성된 객체가 할당되는 영역

Survivor 영역: 최소 1번의 GC 이상 살아남은 객체가 존재하는 영역
 
객체가 새롭게 생성되면 Young 영역 중에서도 Eden 영역에 할당이 된다. 그리고 Eden 영역이 꽉 차면 Minor GC가 발생하게 되는데, 사용되지 않는 메모리는 해제되고 Eden 영역에 존재하는 객체는 Survivor 영역으로 옮겨지게 된다. Survivor 영역은 총 2개이지만 반드시 1개의 영역에만 데이터가 존재해야 하는데, Young 영역의 동작 순서를 자세히 살펴보도록 하자.

        1. 새로 생성된 객체가 Eden 영역에 할당된다.
        2. 객체가 계속 생성되어 Eden 영역이 꽉차게 되고 Minor GC가 실행된다.
        (1)Eden 영역에서 사용되지 않는 객체의 메모리가 해제된다.
        (2)Eden 영역에서 살아남은 객체는 1개의 Survivor 영역으로 이동된다.
        3. 1~2번의 과정이 반복되다가 Survivor 영역이 가득 차게 되면 Survivor 영역의 살아남은 객체를 다른 Survivor 영역으로 이동시킨다.(1개의 Survivor 영역은 반드시 빈 상태가 된다.)
        4. 이러한 과정을 반복하여 계속해서 살아남은 객체는 Old 영역으로 이동(Promotion)된다.
 
객체의 생존 횟수를 카운트하기 위해 Minor GC에서 객체가 살아남은 횟수를 의미하는 age를 Object Header에 기록한다. 그리고 Minor GC 때 Object Header에 기록된 age를 보고 Promotion 여부를 결정한다.

또한 Survivor 영역 중 1개는 반드시 사용이 되어야 한다. 만약 두 Survivor 영역에 모두 데이터가 존재하거나, 모두 사용량이 0이라면 현재 시스템이 정상적인 상황이 아님을 파악할 수 있다.

<b>Major GC의 동작 방식</b>

Young 영역에서 오래 살아남은 객체는 Old 영역으로 Promotion됨을 확인할 수 있었다. 그리고 Major GC는 객체들이 계속 Promotion되어 Old 영역의 메모리가 부족해지면 발생하게 된다.

Young 영역은 일반적으로 Old 영역보다 크키가 작기 때문에 GC가 보통 0.5초에서 1초 사이에 끝난다. 그렇기 때문에 Minor GC는 애플리케이션에 크게 영향을 주지 않는다. 

하지만 Old 영역은 Young 영역보다 크며 Young 영역을 참조할 수도 있다. 그렇기 때문에 Major GC는 일반적으로 Minor GC보다 시간이 오래걸리며, 10배 이상의 시간을 사용한다. 

참고로 Young 영역과 Old 영역을 동시 처리하는 GC는 Full GC라고 한다.

<b>두 줄 요약</b>

        Minor GC는 Young 영역이 꽉 찼을 때 실행되며, 속도는 빠르다.
        Majot GC는 Old 영역이 꽉 찼을 때 실행되며, 속도는 느리다.(Old 영역이 더 크기 때문)
        
# Example Questions
<b>GC로도 메모리 leak이 발생하는 경우</b>
    
<pre><code>import java.util.ArrayList;
    import java.util.List;

    public class MemoryLeakExample {
        private List<String> list = new ArrayList<>();

     public void add(String item) {
         list.add(item);
     }

       public static void main(String[] args) {
            MemoryLeakExample example = new MemoryLeakExample();

          for (int i = 0; i < 1000000; i++) {
             example.add("Item " + i);
          }

          // example 객체는 더 이상 필요하지 않지만, list에 대한 참조를 가지고 있음
          // 따라서 가비지 컬렉션이 발생해도 example 객체는 메모리에서 제거되지 않음
     }
    } </code></pre>

<b>제대로 동작되게 코드 작성</b>
    
<pre><code>import java.util.ArrayList;
import java.util.List;

public class MemoryLeakExample {
    private List<String> list = new ArrayList<>();

    public void add(String item) {
        list.add(item);
    }

    public static void main(String[] args) {
        MemoryLeakExample example = new MemoryLeakExample();

        for (int i = 0; i < 1000000; i++) {
            example.add("Item " + i);
        }

        example = null; // example 객체에 대한 참조 제거

        // 이후에는 example 객체에 대한 참조가 없으므로 가비지 컬렉션이 동작하여 메모리에서 제거될 수 있음
    }
} </code></pre>

example 객체 참조를 null로 해줘서 참조해제하면 제대로 동작 가능
                                                       
<b>제대로 동작되게 코드 작성2(다른 방법)</b>
       
<pre><code>import java.util.ArrayList;
import java.util.List;

public class MemoryLeakExample {
    public void addItems() {
        List<String> list = new ArrayList<>(); // 지역 변수로 선언

        for (int i = 0; i < 1000000; i++) {
            list.add("Item " + i);
        }

        // list를 사용한 작업 수행

        list = null; // 지역 변수 참조 해제
    }

    public static void main(String[] args) {
        MemoryLeakExample example = new MemoryLeakExample();
        example.addItems();

        // 이후에는 addItems() 메서드의 로컬 변수인 list에 대한 참조가 없으므로 가비지 컬렉션이 동작하여 메모리에서 제거될 수 있음
    }
} </code></pre>


전역변수는프로그램의 수명 주기 동안 계속해서 참조함.
                                                       
이를 지역변수로 선언해서 방지한다.
                                                       
자료 출처 : https://mangkyu.tistory.com/118 | https://itkjspo56.tistory.com/285 | https://coding-factory.tistory.com/829 | Chat GPT
