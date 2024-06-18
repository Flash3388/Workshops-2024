package question4;

import java.util.ArrayList;
import java.util.List;

public class SomeObject {

    private final List<Number> list;

    public SomeObject() {
        list = new ArrayList<>();
        list.add(5.3);
        list.add(2);
        list.add(4.2);
        list.add(1);
    }

    public void augmentList1() {
        list.add(55);
        list.add(12.1);
    }

    public void printList() {
        System.out.println(list);
    }
}
