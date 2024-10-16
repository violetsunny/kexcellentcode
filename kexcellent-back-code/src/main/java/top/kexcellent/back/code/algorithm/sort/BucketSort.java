/**
 * LY.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * 桶排序，时间复杂度可以达到 O(n)-O(n^2)
 * @author kanglele
 * @version $Id: BucketSort, v 0.1 2024/10/16 下午3:11 kanglele Exp $
 */
public class BucketSort {
    // 桶排序
    public static void bucketSort(float[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        // 找到最大值和最小值
        float max = arr[0];
        float min = arr[0];
        for (float num : arr) {
            if (num < min) {
                min = num;
            } else if (num > max) {
                max = num;
            }
        }

        // 计算桶的数量
        float range = max - min;
        final int bucketSize = 1; // 桶的容量
        int bucketCount = (int) (range / bucketSize) + 1;

        // 创建桶
        ArrayList<Float>[] buckets = new ArrayList[bucketCount];
        for (int i = 0; i < bucketCount; i++) {
            buckets[i] = new ArrayList<Float>();
        }

        // 将数据分配到各个桶中
        for (float num : arr) {
            int index = (int) ((num - min) / bucketSize);
            buckets[index].add(num);
        }

        // 对每个桶进行排序
        for (ArrayList<Float> bucket : buckets) {
            Collections.sort(bucket);
        }

        // 遍历桶并收集数据
        int index = 0;
        for (ArrayList<Float> bucket : buckets) {
            for (float num : bucket) {
                arr[index++] = num;
            }
        }
    }

    public static void main(String[] args) {
        float[] arr = {0.78f, 0.17f, 0.39f, 0.26f, 0.72f, 0.94f, 0.21f, 0.12f, 0.23f, 0.65f};
        bucketSort(arr);
        System.out.println("Sorted array: " + Arrays.toString(arr));
    }
}
