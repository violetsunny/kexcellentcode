/**
 * LY.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm.sort;

import java.util.Arrays;

/**
 * 基数排序，时间复杂度为 O(nk)
 * @author kanglele
 * @version $Id: RadixSort, v 0.1 2024/10/16 下午3:05 kanglele Exp $
 */
public class RadixSort {
    public static void radixSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        // 找到数组中的最大值，以确定最大的位数
        int max = getMax(arr);

        // 对每一位进行排序，从最低位（个位）开始
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countSort(arr, exp);
        }
    }

    private static int getMax(int[] arr) {
        int max = arr[0];
        for (int num : arr) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    private static void countSort(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];

        // 统计每个桶（对应每个数字0-9）中的元素个数
        for (int i = 0; i < n; i++) {
            int digit = (arr[i] / exp) % 10;
            count[digit]++;
        }

        // 计算每个桶的累积计数，用于确定元素在输出数组中的位置
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // 将元素按照当前位的数字放入对应的桶中，并更新输出数组
        for (int i = n - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }

        // 将排序好的结果复制回原数组
        System.arraycopy(output, 0, arr, 0, n);
    }

    public static void main(String[] args) {
        int[] arr = {170, 45, 75, 90, 802, 24, 2, 66, 3, 2, 0, 1, 992, 9999};
        radixSort(arr);
        System.out.println("Sorted array: " + Arrays.toString(arr));
    }
}
