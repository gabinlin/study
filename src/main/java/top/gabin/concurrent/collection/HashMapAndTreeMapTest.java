package top.gabin.concurrent.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class HashMapAndTreeMapTest {

    /**
     * java.util.HashMap add
     * 68323711
     * java.util.TreeMap add
     * 57906627
     * java.util.HashMap access
     * 11411559
     * java.util.TreeMap access
     * 23009452
     * java.util.HashMap remove
     * 88061702
     * java.util.TreeMap remove
     * 65435597
     */
    public static void main(String[] args) {
        Map<Object, Object> hashMap = new HashMap<>();
        Map<Object, Object> treeMap = new TreeMap<>();

        testAdd(hashMap);
        testAdd(treeMap);

        testAccess(hashMap);
        testAccess(treeMap);

        testDel(hashMap);
        testDel(treeMap);
    }

    private static void testDel(Map<Object, Object> map) {
        long start = System.nanoTime();
        Set<Object> objectSet = map.keySet().stream().collect(Collectors.toSet());
        for (Object o : objectSet) {
            map.remove(o);
        }
        System.out.println(map.getClass().getName() + " remove\n" + (System.nanoTime() - start));
    }

    private static void testAccess(Map<Object, Object> map) {
        long start = System.nanoTime();
        for (Object o : map.keySet()) {
            map.get(o);
        }
        System.out.println(map.getClass().getName() + " access\n" + (System.nanoTime() - start));
    }

    private static void testAdd(Map<Object, Object> map) {
        long start = System.nanoTime();
        for (int i = 0; i < 100_000; i++) {
            map.put("key" + i, i);
        }
        System.out.println(map.getClass().getName() + " add\n" + (System.nanoTime() - start));
    }

}
