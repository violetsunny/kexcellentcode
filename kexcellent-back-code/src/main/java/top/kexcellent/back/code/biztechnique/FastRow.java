/**
 * llkang.com Inc.
 * Copyright (c) 2010-2022 All Rights Reserved.
 */
package top.kexcellent.back.code.biztechnique;

/**
 * 快排
 *
 * @author kanglele
 * @version $Id: FastRow, v 0.1 2022/6/9 10:49 kanglele Exp $
 */
public class FastRow {

    /**
     * 将数组拆分，递归进行二分查找
     */
    private static void qsort(int[] arr, int low, int high) {
        if (low >= high) {
            return;
        }
        int begin = low;
        int end = high;
        int position = partion(arr, low, high);
        qsort(arr, begin, position-1);
        qsort(arr, position+1, end);
    }

    /**
     *  按照最右侧的元素为基准，返回基准值在low~high范围中的位置 左指针要先移动
     *  最左为基准，需要右指针先移动
     */
    private static int partion(int[] arr, int low, int high) {
        int baseVal = arr[high];
        while (low < high) {
            //从左侧开始查找大于基准值的元素，并移到右侧
            while (low < high && arr[low] <= baseVal) {
                low++;
            }
            arr[high] = arr[low];
            //从右侧开始查找小于基准值的元素，并移到左侧
            while (low < high && arr[high] >= baseVal) {
                high--;
            }
            arr[low] = arr[high];
        }
        //将基准值移到中位
        arr[low] = baseVal;
        //返回中间位置
        return low;
    }
}
