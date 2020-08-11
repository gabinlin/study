package top.gabin.algorithm;

import java.util.Random;

public class RandomUtils {

    public static int[] getRandomArray(int length) {
        Random random = new Random();
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }

}
