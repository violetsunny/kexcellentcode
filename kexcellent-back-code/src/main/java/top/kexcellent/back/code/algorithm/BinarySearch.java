/**
 * LY.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm;

/**
 * 二分查找
 * @author kanglele
 * @version $Id: BinarySearch, v 0.1 2024/10/16 上午10:50 kanglele Exp $
 */
public class BinarySearch {
    public static int binarySearch(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            // 防止溢出
            int mid = left + (right - left) / 2;

            // 检查中间元素是否是目标值
            if (nums[mid] == target) {
                return mid; // 找到目标值，返回索引
            }

            // 如果目标值小于中间元素，则在左半部分继续查找
            if (target < nums[mid]) {
                right = mid - 1;
            } else {
                // 如果目标值大于中间元素，则在右半部分继续查找
                left = mid + 1;
            }
        }

        // 如果没有找到目标值，返回 -1
        return -1;
    }

    public static void main(String[] args) {
        int[] nums = {-7, -5, 1, 3, 4, 9, 12};
        int target = 4;

        int result = binarySearch(nums, target);
        if (result != -1) {
            System.out.println("Element found at index " + result);
        } else {
            System.out.println("Element not found.");
        }
    }
}
