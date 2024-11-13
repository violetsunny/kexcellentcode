/**
 * LY.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm.sort;

/**
 * 大顶堆
 * @author kanglele
 * @version $Id: MaxHeapSort, v 0.1 2024/10/16 上午11:13 kanglele Exp $
 */
public class MaxHeapSort {

    // 对数组进行最大堆排序
    public static void maxHeapSort(int[] arr) {
        int n = arr.length;

        // 构建最大堆
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // 逐个将堆顶元素与末尾元素交换，并重新调整堆
        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // 对交换后的堆进行调整，使其满足最大堆性质
            heapify(arr, i, 0);
        }
    }

    // 调整堆以满足最大堆性质
    private static void heapify(int[] arr, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        // 如果左子节点存在且大于当前最大节点，则更新最大节点为左子节点
        if (l < n && arr[l] > arr[largest]) {
            largest = l;
        }

        // 如果右子节点存在且大于当前最大节点（可能是左子节点更新后的），则更新最大节点为右子节点
        if (r < n && arr[r] > arr[largest]) {
            largest = r;
        }

        // 如果最大节点不是当前节点，则交换它们，并继续调整子树
        if (largest!= i) {
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;

            // 递归调整交换后的子树，使其满足最大堆性质
            heapify(arr, n, largest);
        }
    }

    public static void main(String[] args) {
        int[] arr = {12, 34, 5, 2, 38, 45, 6, 23, 4, 67, 3, 8, 56};
        maxHeapSort(arr);
        System.out.println("Sorted array: ");
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
}
