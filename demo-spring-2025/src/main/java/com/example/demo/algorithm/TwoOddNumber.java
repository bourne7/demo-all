package com.example.demo.algorithm;

/**
 * 找出数组中，唯二出现过奇数次的数字。
 * https://blog.csdn.net/dc282614966/article/details/122376671
 */
public class TwoOddNumber {

    public static void main(String[] args) {

        int[] arr = {1, 1, 1, 2, 2, 3, 3, 4, 4, 4};

        //通过异或操作，计算出两种奇数个数的数字的值
        int eor = 0;

        for (int i : arr) {
            eor ^= i;
        }

        // 找到任意一个1的位置，用于分组。这里找最右边的。
        // 这里十分巧妙。
        int mostRightOne = eor & (~eor + 1);

        int firstNumber = 0;

        for (int i : arr) {
            if ((i & mostRightOne) != 0) {
                firstNumber ^= i;
            }
        }

        int secondNumber = eor ^ firstNumber;

        System.out.println(firstNumber + " and " + secondNumber);
        // 1 and 4
    }
}
