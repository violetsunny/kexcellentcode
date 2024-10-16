/**
 * LY.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm.sort;

/**
 * 计数排序 时间复杂度为 O(n + k)，其中 n 是数组的长度，k 是整数的范围； 空间复杂度为 O(k)
 * @author kanglele
 * @version $Id: CountingSort, v 0.1 2024/10/16 下午2:59 kanglele Exp $
 */
public class CountingSort {
    public static void countingSort(int[] arr) {
        // 找出数组中的最大值，以确定计数数组的大小
        int max = findMax(arr);

        // 创建计数数组并初始化为0
        int[] count = new int[max + 1];

        // 计算每个元素的出现次数
        for (int i = 0; i < arr.length; i++) {
            count[arr[i]]++;
        }

        // 根据计数数组，将元素放到正确的位置
        int index = 0;
        for (int i = 0; i <= max; i++) {
            while (count[i] > 0) {
                arr[index++] = i;
                count[i]--;
            }
        }
    }

    // 找出数组中的最大值
    private static int findMax(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int[] arr = {170, 45, 75, 90, 802, 24, 2, 66, 3, 2, 0, 1, 992, 9999};
        countingSort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
}
