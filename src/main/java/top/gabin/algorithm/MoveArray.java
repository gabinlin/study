package top.gabin.algorithm;

public class MoveArray {

    public static void main(String[] args) {
        int[] nums = {-1,-100,3,99};
        // 空间复杂度+1
        int k = 2;
        int[] clone = nums.clone();
        for (int i = 0; i < nums.length; i++) {
            int i1 = getIndex(k, nums, i);
            clone[i1] = nums[i];
        }
        print(clone);
    }

    private static int getIndex(int k, int[] clone, int i) {
        int i1 = i + k;
        if (i1 >= clone.length) {
            i1 = i1 % clone.length;
        }
        return i1;
    }

    private static void print(int[] clone) {
        for (int i = 0; i < clone.length; i++) {
            System.out.print(clone[i] + " ");
        }
    }
}
