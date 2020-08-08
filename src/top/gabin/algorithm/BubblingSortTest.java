package top.gabin.algorithm;

import java.util.Random;

/**
 * 冒泡排序
 */
public class BubblingSortTest {

    public static void main(String[] args) {
        Random random = new Random();
        // 生成一个随机数组，1000
        int length = 1000;
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = random.nextInt();
        }

        for (int i = 0; i < length; i++) {
            for (int j = 1; j < length - i; j++) {
                if (array[j] > array[j-1]) {
                    int temp = array[j];
                    array[j] = array[j-1];
                    array[j-1] = temp;
                }
            }
        }
    }

}
