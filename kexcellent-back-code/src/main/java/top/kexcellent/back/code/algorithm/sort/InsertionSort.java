/**
 * LY.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm.sort;

import java.util.Arrays;

/**
 * 插入排序的时间复杂度为 O(n^2),稳定的排序算法
 * @author kanglele
 * @version $Id: InsertionSort, v 0.1 2024/10/16 下午3:31 kanglele Exp $
 */
public class InsertionSort {
    // 插入排序
    public static void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;

            // 将arr[i]插入到已排序的序列arr[0...i-1]中
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    public static void main(String[] args) {
        int[] arr = {12, 11, 13, 5, 6, 7};
        insertionSort(arr);
        System.out.println("Sorted array: " + Arrays.toString(arr));
    }
}
