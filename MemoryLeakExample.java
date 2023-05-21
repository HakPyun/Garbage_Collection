import java.util.ArrayList;
import java.util.List;

    public class MemoryLeakExample {
        private List list = new ArrayList<>();

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
    } 
//메모리 leak 해결(example 참조해제)
import java.util.ArrayList;
import java.util.List;

public class MemoryLeakExample {
    private List list = new ArrayList<>();

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
} 
//list를 전역변수가 아닌 지역 변수로 선언
import java.util.ArrayList;
import java.util.List;

public class MemoryLeakExample {
    public void addItems() {
        List list = new ArrayList<>(); // 지역 변수로 선언

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
} 