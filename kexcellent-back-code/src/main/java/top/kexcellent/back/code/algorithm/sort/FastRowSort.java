/**
 * llkang.com Inc.
 * Copyright (c) 2010-2022 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm.sort;

/**
 * 快排,时间平均O(nlogn)，空间O(1)
 * ①找基准点：一般是数组的第一个元素来充当；
 *
 * ②right：从数组的最后一个元素开始，从右往左，直到找到小于基准点的元素；每次都要right比left先走；
 *
 * ③left：从数组的第一个元素开始，从左往右，直到找到大于基准点的元素；
 *
 * ④交换 left 和 right 所在位置的两个元素；
 *
 * ⑥right 继续往左走，找到小于基准点的元素；left 继续往右走，找到大于基准点的元素；然后 left 和 right 再做交换；循环往复，直到两人相遇；
 *
 * ⑦将相遇点所在位置的元素和基准点所在位置的元素做交换，基准点到了中间位置（此时基准点左边的元素全都小于基准点右边的元素）；
 *
 * ⑧【递归】将基准点左边的所有元素当成一个数组
 *
 * @author kanglele
 * @version $Id: FastRow, v 0.1 2022/6/9 10:49 kanglele Exp $
 */
public class FastRowSort {

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
     *  如果是最左为基准，需要右指针先移动
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
