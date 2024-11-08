## [53 - I. 在排序数组中查找数字 I](https://leetcode.cn/problems/zai-pai-xu-shu-zu-zhong-cha-zhao-shu-zi-lcof/)


### 题目描述

统计一个数字在排序数组中出现的次数。

例如输入排序数组 `[1, 2, 3, 3, 3, 3, 4, 5]` 和数字 3，由于 3 在这个数组中出现了 4 次，因此输出 4。

**样例**

```
输入：[1, 2, 3, 3, 3, 3, 4, 5] ,  3

输出：4
```

### 解法

找出第一个 k 和最后一个 k 出现的位置。

找第一个 k 时，利用二分法，如果 `nums[m] == k`，判断它的前一个位置是不是也是 k，如果不是，说明这是第一个 k，直接返回。如果是，那么递归在左边查找第一个 k。

找最后一个 k 也同理。

```java
class Solution {
    /**
     * 求数字k在排序数组中出现的次数
     *
     * @param nums 数组
     * @param k 数字k
     * @return k在数组中出现的次数
     */
    public int getNumberOfK(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int start = 0, end = nums.length - 1;
        int first = getFirstK(nums, start, end, k);
        int last = getLastK(nums, start, end, k);
        if (first > -1 && last > -1) {
            return last - first + 1;
        }
        return 0;
    }

    private int getFirstK(int[] nums, int start, int end, int k) {
        if (start > end) {
            return -1;
        }
        int m = start + ((end - start) >> 1);
        if (nums[m] == k) {
            if (m == 0 || (m > 0 && nums[m - 1] != k)) {
                return m;
            } else {
                end = m - 1;
            }
        } else {
            if (nums[m] > k) {
                end = m - 1;
            } else {
                start = m + 1;
            }
        }
        return getFirstK(nums, start, end, k);
    }

    private int getLastK(int[] nums, int start, int end, int k) {
        if (start > end) {
            return -1;
        }
        int m = start + ((end - start) >> 1);
        if (nums[m] == k) {
            if (m == nums.length - 1 || (m < nums.length - 1 && nums[m + 1] != k)) {
                return m;
            } else {
                start = m + 1;
            }
        } else {
            if (nums[m] > k) {
                end = m - 1;
            } else {
                start = m + 1;
            }
        }
        return getLastK(nums, start, end, k);

    }
}
```

## [53 - II. 0 ～ n-1 中缺失的数字](https://leetcode.cn/problems/que-shi-de-shu-zi-lcof/)


### 题目描述

一个长度为 `n-1` 的递增排序数组中的所有数字都是唯一的，并且每个数字都在范围 `0` 到 `n-1` 之内。

在范围 `0` 到 `n-1` 的 `n` 个数字中有且只有一个数字不在该数组中，请找出这个数字。

**样例**

```
输入：[0,1,2,4]

输出：3
```

### 解法

找出第一个与下标不对应的数字即可。

特殊情况：

- 下标都对应，那么应该返回 `最后一个数+1`；
- 缺失的数字是第一个，那么返回 0。

```java
class Solution {
    /**
     * 获取0~n-1缺失的数字
     *
     * @param nums 数组
     * @return 缺失的数字
     */
    public int getMissingNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        int start = 0, end = n - 1;
        while (start <= end) {
            int mid = start + ((end - start) >> 1);
            if (nums[mid] != mid) {
                if (mid == 0 || nums[mid - 1] == mid - 1) {
                    return mid;
                }
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return start == n ? n : -1;

    }
}
```

## 53.3 数组中数值和下标相等的元素


### 题目描述

假设一个单调递增的数组里的每个元素都是整数并且是唯一的。

请编程实现一个函数找出数组中任意一个数值等于其下标的元素。

例如，在数组 `[-3, -1, 1, 3, 5]` 中，数字 3 和它的下标相等。

**样例**

```
输入：[-3, -1, 1, 3, 5]

输出：3
```

**注意**:如果不存在，则返回 -1。

### 解法

二分法查找。

- 当前元素等于对应的下标，直接返回该下标；
- 当前元素大于该下标，在左边查找；
- 当前元素小于该下标，在右边查找。

```java
class Solution {
    /**
     * 找出单调递增数组中数值和下标相等的元素
     *
     * @param nums 数组
     * @return 数值与下标相等的元素
     */
    public int getNumberSameAsIndex(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int start = 0, end = nums.length - 1;
        while (start <= end) {
            int mid = start + ((end - start) >> 1);
            if (nums[mid] == mid) {
                return mid;
            }
            if (nums[mid] < mid) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return -1;
    }
}
```