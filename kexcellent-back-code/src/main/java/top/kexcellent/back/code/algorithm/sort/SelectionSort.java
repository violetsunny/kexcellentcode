/**
 * LY.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm.sort;

import java.util.Arrays;

/**
 * 选择排序的时间复杂度为 O(n^2),不稳定的排序算法
 * @author kanglele
 * @version $Id: SelectionSort, v 0.1 2024/10/16 下午3:33 kanglele Exp $
 */
public class SelectionSort {
    // 选择排序
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            // 找到最小元素的索引
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            // 将找到的最小元素与第一个未排序元素交换
            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {64, 25, 12, 22, 11};
        selectionSort(arr);
        System.out.println("Sorted array: " + Arrays.toString(arr));
    }
}
