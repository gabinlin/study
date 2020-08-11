package top.gabin.algorithm;

/**
 * 冒泡排序
 */
public class BubblingSortTest {

    public static void main(String[] args) {
        int length = 1000;
        int[] array = RandomUtils.getRandomArray(length);

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
