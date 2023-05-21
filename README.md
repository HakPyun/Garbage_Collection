# Garbage Collection(Java)

<b>Garbage Collection이란?</b>
Garbage Collection은 자바의 메모리 관리 방법 중의 하나로 JVM의 Heap 영역에서 동적으로 할당했던 메모리 영역 중 필요 없게 된 메모리 영역을 주기적으로 삭제하는 프로세스를 말한다.

Java에서는 JVM에 탑재되어 있는 가비지 컬렉터가 메모리 관리를 대행해주기 때문에 개발자 입장에서 메모리 관리, 메모리 누수(Memory Leak) 문제에서 대해 완벽하게 관리하지 않아도 되어 오롯이 개발에만 집중할 수 있다는 장점이 있다.

<b>Garbage Collection이 필요한 이유</b>

객체들은 Heap영역에서 생성이 되는데 Method, Stack영역에서는 객체의 주소로만 참조한다.

이렇게 생성된 객체들이 메서드가 끝나는 등의 특정 이벤트들로 인하여 Heap 영역 객체의 메모리 주소를 가지고 있는 참조 변수가 삭제되는 현상이 발생하면

Heap영역에서 참조되지 않은 객체들이 발생하게되는데 이러한 객체들을 제거하기 위해 Garbage Collection이 존재한다.

그렇지 않으면 사용되지도 않는데 Heap 영역을 차지하는 쓰레기(Garbage)들이 남아있게 되기 때문이다.(메모리 누수)

# Garbage Collection 매커니즘

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
    
<b>Garbage Collection 동작원리</b>

