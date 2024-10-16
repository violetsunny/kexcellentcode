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
    // 获取数组中最大数的位数
    private static int getMaxDigits(int[] arr) {
        int maxValue = Arrays.stream(arr).max().getAsInt();
        return Integer.toString(maxValue).length();
    }

    // 获取单个数字的某一位（从0开始）
    private static int getDigit(int number, int radix, int index) {
        return (number / (int) Math.pow(radix, index)) % radix;
    }

    // 基数排序
    public static void radixSort(int[] arr) {
        final int RADIX = 10; // 基数为10
        int[] tempArray = new int[arr.length];
        int maxDigits = getMaxDigits(arr);

        for (int index = 0; index < maxDigits; index++) {
            // 对每个位进行计数排序
            countingSortByDigit(arr, tempArray, index, RADIX);
            // 将临时数组复制回原数组
            System.arraycopy(tempArray, 0, arr, 0, tempArray.length);
        }
    }

    // 计数排序（根据某一位）
    private static void countingSortByDigit(int[] arr, int[] output, int index, int radix) {
        int[] count = new int[radix];
        int[] tempArray = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            int digit = getDigit(arr[i], radix, index);
            count[digit]++;
        }

        for (int i = 1; i < radix; i++) {
            count[i] += count[i - 1];
        }

        for (int i = arr.length - 1; i >= 0; i--) {
            int digit = getDigit(arr[i], radix, index);
            tempArray[count[digit] - 1] = arr[i];
            count[digit]--;
        }

        System.arraycopy(tempArray, 0, output, 0, tempArray.length);
    }

    public static void main(String[] args) {
        int[] arr = {170, 45, 75, 90, 802, 24, 2, 66, 3, 2, 0, 1, 992, 9999};
        radixSort(arr);
        System.out.println("Sorted array: " + Arrays.toString(arr));
    }
}
