/**
 * LY.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 桶排序，时间复杂度可以达到 O(n)-O(n^2)
 *
 * @author kanglele
 * @version $Id: BucketSort, v 0.1 2024/10/16 下午3:11 kanglele Exp $
 */
public class BucketSort {
    // 桶排序
    public static void bucketSort(double[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        // 找到数组中的最大值和最小值
        double max = arr[0];
        double min = arr[0];
        for (double num : arr) {
            if (num > max) {
                max = num;
            }
            if (num < min) {
                min = num;
            }
        }

        // 确定桶的数量，这里简单取数组长度
        int bucketCount = arr.length;
        List<List<Double>> buckets = new ArrayList<>();
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        // 将数组元素分配到各个桶中
        for (double num : arr) {
            int bucketIndex = (int) ((num - min) / ((max - min) / bucketCount));
            if (bucketIndex == bucketCount) {
                bucketIndex--;
            }
            buckets.get(bucketIndex).add(num);
        }

        // 对每个桶内部进行排序，这里使用Collections.sort()，它通常采用归并排序或快速排序实现
        for (List<Double> bucket : buckets) {
            Collections.sort(bucket);
        }

        // 将排序好的桶中的元素依次放回原数组
        int index = 0;
        for (List<Double> bucket : buckets) {
            for (double num : bucket) {
                arr[index++] = num;
            }
        }
    }

    public static void main(String[] args) {
//        double[] arr = {0.42, 0.32, 0.33, 0.52, 0.37, 0.47, 0.51, 1.3, 1};
        int[] arr = {170, 45, 75, 90, 802, 24, 2, 66, 3, 2, 0, 1, 992, 9999};
        bucketSort(arr);
        System.out.println("Sorted array: " + Arrays.toString(arr));
    }

    /**
     * 如果int的桶排序就比较简单，但是占空间比较大
     * @param arr
     */
    public static void bucketSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        int max = arr[0];
        int min = arr[0];
        for (int num : arr) {
            if (num > max) {
                max = num;
            }
            if (num < min) {
                min = num;
            }
        }

        int[] buckets = new int[max-min+1];
        for (int j : arr) {
            buckets[j]++;
        }
        int index = 0;
        for (int i = min; i <= max; i++) {
            while (buckets[i] > 0) {
                arr[index++] = i;
                buckets[i]--;
            }
        }
    }
}
