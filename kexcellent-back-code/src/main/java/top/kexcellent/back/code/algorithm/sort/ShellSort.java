/**
 * LY.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm.sort;

/**
 * 希尔排序
 * @author kanglele
 * @version $Id: ShellSort, v 0.1 2024/11/13 17:06 kanglele Exp $
 */
public class ShellSort {
    public static void shellSort(int[] arr) {
        int n = arr.length;
        int gap = n / 2; // 初始间隔设置为数组长度的一半

        // 动态调整间隔，直到间隔为1
        while (gap > 0) {
            // 从第一个间隔元素开始，对每个间隔进行插入排序
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j;

                // 将元素向后移动，直到找到它的正确位置
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = temp;
            }

            // 减少间隔，继续下一轮排序
            gap /= 2;
        }
    }

    public static void main(String[] args) {
        int[] arr = {12, 34, 5, 2, 38, 45, 6, 23, 4, 67, 3, 8, 56};
        shellSort(arr);
        System.out.println("Sorted array: ");
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
}
