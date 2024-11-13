/**
 * llkang.com Inc.
 * Copyright (c) 2010-2022 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm.sort;

import java.util.Arrays;

/**
 * 归并排序,时间复杂度O(nlogn)，空间复杂度O(n) out-place 稳定性排序
 *
 * @author kanglele
 * @version $Id: Merge, v 0.1 2022/6/9 10:50 kanglele Exp $
 */
public class MergeSort {

    /**
     * mergeSort 递归 对两个有序节点序列进行合并来实现排序，分治思想
     * @param arr
     * @return
     */
    public static void mergeSort(int[] arr) {
        if (arr == null) {
            return;
        }
        mergeSort(arr, 0, arr.length - 1);
    }

    private static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            // 对左半部分数组进行归并排序
            mergeSort(arr, left, mid);

            // 对右半部分数组进行归并排序
            mergeSort(arr, mid + 1, right);

            // 合并已经排好序的左半部分和右半部分数组
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        // 分别定义左右两个子数组的长度
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // 创建临时数组来存放左右子数组的元素
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        // 将原数组中的元素复制到临时数组中
        for (int i = 0; i < n1; i++) {
            leftArray[i] = arr[left + i];
        }
        for (int i = 0; i < n2; i++) {
            rightArray[i] = arr[mid + 1 + i];
        }

        // 初始化索引，用于遍历临时数组和原数组
        int i = 0, j = 0, k = left;

        // 比较左右子数组的元素，将较小的元素依次放入原数组中
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                arr[k] = leftArray[i];
                i++;
            } else {
                arr[k] = rightArray[j];
                j++;
            }
            k++;
        }

        // 如果左子数组还有剩余元素，将其全部放入原数组中
        while (i < n1) {
            arr[k] = leftArray[i];
            i++;
            k++;
        }

        // 如果右子数组还有剩余元素，将其全部放入原数组中
        while (j < n2) {
            arr[k] = rightArray[j];
            j++;
            k++;
        }
    }

    public static void main(String[] args) {
        int[] arr = {12, 34, 5, 2, 38, 45, 6, 23, 4, 67, 3, 8, 56};
        mergeSort(arr);
        System.out.println("Sorted array: ");
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
}
