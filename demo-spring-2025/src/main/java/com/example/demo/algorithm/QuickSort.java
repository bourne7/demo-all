package com.example.demo.algorithm;

import java.util.Arrays;

public class QuickSort {

    public static void main(String[] args) {
//        int[] array = {7, 8, 9, 4, 5, 6, 1, 2, 3};
//        int[] array = {2, 3, 1};
        int[] array = {2, 1};

        quick_sort(array, 0, array.length - 1);

        System.out.println(Arrays.toString(array));
    }

    private static void quick_sort(int[] array, int begin, int end) {
        // 结束条件
        if (begin >= end) {
            return;
        }

        // 标兵的选取
        int key = array[begin];

        // 前后的指针
        int left_index = begin;
        int right_index = end;

        while (left_index < right_index) {
            /**
             * 从右边遍历数组, 找到应该第一个小于key的位置.
             * 这里一定要注意是从右边, 或者说是从远离标兵的位置开始的.
             * 原因是 left_index 之后会作为相遇位置,并且将会和标兵做交换.
             */
            while (array[right_index] >= key && right_index > left_index) {
                right_index--;
            }

            // 从左边遍历数组, 找到应该第一个大于key的位置.
            while (array[left_index] <= key && left_index < right_index) {
                left_index++;
            }

            // 交换 right_index 和 left_index
            if (left_index < right_index) {
                int temp = array[left_index];
                array[left_index] = array[right_index];
                array[right_index] = temp;
            }
        }

        /**
         * 交换 begin 和 left_index
         * 这里的目的是将最左边的标兵往相遇点插进去, 因为这是标兵唯一移动的机会.
         * 任何的在移动指针的时候做出来的交换, 都不会涉及到标兵的交换.
         */
        array[begin] = array[left_index];
        array[left_index] = key;

        // 这里的 left_index 就是相遇的点, 所以前半部分的递归可以选择到 left_index - 1 或者 left_index.
        quick_sort(array, begin, left_index - 1);
        // 但是后半部分的递归就只能从 left_index + 1 开始了. 因为相遇点是已经和标兵交换了位置的.
        quick_sort(array, left_index + 1, end);

    }

}

