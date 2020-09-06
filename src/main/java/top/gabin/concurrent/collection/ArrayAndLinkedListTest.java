package top.gabin.concurrent.collection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据的类型最终只有
 */
public class ArrayAndLinkedListTest {

    /**
     * 参考日志：4核16G，mbp 16年15.4寸
     * java.util.ArrayList add:
     * 9548992
     * java.util.LinkedList add:
     * 7330571
     * java.util.ArrayList access:
     * 6238440
     * java.util.LinkedList access:
     * 5078531802
     * java.util.ArrayList remove:
     * 94877
     * java.util.LinkedList remove:
     * 47117
     */
    public static void main(String[] args) {
        List arrayList = new ArrayList();
        List linkList = new LinkedList();

        testAdd(arrayList);
        testAdd(linkList);

        testAccess(arrayList);
        testAccess(linkList);

        testDel(arrayList);
        testDel(linkList);
    }

    private static void testDel(List<Object> list) {
        List<Object> objectList = list.stream().collect(Collectors.toList());
        list.removeAll(objectList);
        long start = System.nanoTime();
        System.out.println(list.getClass().getName() + " remove:");
        System.out.println(System.nanoTime() - start);
    }

    private static void testAdd(List list) {
        long start = System.nanoTime();
        for (int i = 0; i < 100_000; i++) {
            list.add(i);
        }
        System.out.println(list.getClass().getName() + " add:");
        System.out.println(System.nanoTime() - start);
    }

    private static void testAccess(List list) {
        long start = System.nanoTime();
        for (int i = 0; i < list.size(); i++) {
            list.get(i);
        }
        System.out.println(list.getClass().getName() + " access:");
        System.out.println(System.nanoTime() - start);
    }
}
