/**
 * LY.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm.sort;

import java.util.Arrays;

/**
 * 冒泡排序的时间复杂度为O(n^2)，其中n是数组的长度
 * @author kanglele
 * @version $Id: BubbleSort, v 0.1 2024/10/16 下午3:24 kanglele Exp $
 */
public class BubbleSort {
    // 冒泡排序
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            // 每次内循环将最大的元素“冒泡”到数组的末尾
            for (int j = 0; j < n - i - 1; j++) {
                // 相邻元素两两比较，根据升序或降序需求进行元素的位置交换
                if (arr[j] > arr[j + 1]) {
                    // 交换arr[j]和arr[j+1]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            // 如果在这一趟中没有发生交换，说明数组已经有序，直接跳出循环
            if (!swapped) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        bubbleSort(arr);
        System.out.println("Sorted array: " + Arrays.toString(arr));
    }
}
