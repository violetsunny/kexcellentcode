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
        if (arr == null || arr.length == 0) {
            return;
        }
        int max = arr[0];//找出最大值
        int min = arr[0];//找出最小值
        for (int num : arr) {
            if (num > max) {
                max = num;
            }
            if (num < min) {
                min = num;
            }
        }

        int[] counts = new int[max - min + 1];//max - min缩小空间
        //有数加1
        for (int i : arr) {
            counts[i-min]++;//减去min，防止负数，才是真正的位置
        }
        int index = 0;
        for (int i = min; i <= max; i++) {
            while (counts[i-min] > 0) {
                arr[index++] = i;
                counts[i-min]--;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {170, 45, 75, 90, 802, 24, 2, 66, 3, 2, 0, 1, 992, 9999};
        countingSort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
}
