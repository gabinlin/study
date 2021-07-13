package top.gabin.algorithm;

public class MoveArray {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        // 空间复杂度+1
        int k = 3;
        int[] clone = nums.clone();
        for (int i = 0; i < nums.length; i++) {
            int i1 = i + k;
            if (i1 >= nums.length) {
                i1 = i1 - nums.length;
            }
            clone[i1] = nums[i];
        }
        print(clone);

        // 从0开始，把所有被占位的都挪到索引0的位置
        // 通过变量记录住被占位的索引，类似于一种递推方式，比如从0 -> 3 , 3 -> 6，一直用索引0的位置做存储空间
        // 原地算法
        clone = nums.clone();
        int len = clone.length;
        int i = 0;
        while (len-- > 0) {
            int i1 = i + k;
            if (i1 >= clone.length) {
                i1 = i1 - clone.length;
            }
            int tmp = clone[i1];
            clone[i1] = clone[0];
            clone[0] = tmp;
            i = i1;
        }
        System.out.println("");
        print(clone);
    }

    private static void print(int[] clone) {
        for (int i = 0; i < clone.length; i++) {
            System.out.print(clone[i] + " ");
        }
    }
}
