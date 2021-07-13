package top.gabin.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 算法题：
 * 1、从一个有序数组中
 * 2、去除重复的部分
 * 3、返回当前的数组长度
 *
 * 限制条件：空间复杂度O(1)，即不使用额外空间
 */
public class OrderedArrayRepetition {
    public static void main(String[] args) {
        List<Integer> array = getArray(10);

        Integer[] toArray = array.toArray(new Integer[0]);
        System.out.println(array);
        int len = getRemoveRepetitionNums1(toArray.clone());
        System.out.println(len);
        len = getRemoveRepetitionNums2(toArray.clone());
        System.out.println(len);
        len = getRemoveRepetitionNums3(toArray.clone());
        System.out.println(len);
    }

    private static int getRemoveRepetitionNums1(Integer[] array) {
        // 1、双指针
        int left = 0;
        for (int right = 1; right < array.length; right++) {
            if (!array[left].equals(array[right])) {
                array[++left] = array[right];
            }
        }
        return ++left;
    }

    private static int getRemoveRepetitionNums2(Integer[] array) {
        // 2、重复数
        int left = 0;
        for (int right = 1; right < array.length; right++) {
            if (array[right-1].equals(array[right])) {
                left ++;
            }
        }
        return array.length - left;
    }

    private static int getRemoveRepetitionNums3(Integer[] array) {
        // 3、不等数+1
        int left = 0;
        for (int right = 1; right < array.length; right++) {
            if (!array[right-1].equals(array[right])) {
                left ++;
            }
        }
        return ++left;
    }

    private static List<Integer> getArray(int len) {
        Random random = new Random();
        List<Integer> ints = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            ints.add(random.nextInt(10));
        }
        ints.sort(Integer::compareTo);
        return ints;
    }
}
