/**
 * LY.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm.sort;

/**
 * 小顶堆
 * @author kanglele
 * @version $Id: MinHeapSort, v 0.1 2024/10/16 上午11:14 kanglele Exp $
 */
public class MinHeapSort {
    // 构建小顶堆
    public static void buildMinHeap(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int current = i;
            while (current > 0 && arr[current] < arr[(current - 1) / 2]) {
                swap(arr, current, (current - 1) / 2);
                current = (current - 1) / 2;
            }
        }
    }

    // 堆排序
    public static void minHeapSort(int[] arr) {
        buildMinHeap(arr);
        int n = arr.length;
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, n - i, 0);
        }
    }

    // 用于交换数组中的两个元素
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // 用于堆化数组的一部分
    private static void heapify(int[] arr, int n, int i) {
        int smallest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] < arr[smallest]) {
            smallest = left;
        }

        if (right < n && arr[right] < arr[smallest]) {
            smallest = right;
        }

        if (smallest != i) {
            swap(arr, i, smallest);
            heapify(arr, n, smallest);
        }
    }
}
