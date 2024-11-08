## [3.1 找出数组中重复的数](https://leetcode.cn/problems/shu-zu-zhong-zhong-fu-de-shu-zi-lcof/)

### 题目描述

给定一个长度为 `n` 的整数数组 `nums`，数组中所有的数字都在 `0∼n−1` 的范围内。

数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。

请找出数组中任意一个重复的数字。

**注意**：如果某些数字不在 `0∼n−1` 的范围内，或数组中不包含重复数字，则返回 `-1`；

**样例**

```
给定 nums = [2, 3, 5, 4, 3, 2, 6, 7]。

返回 2 或 3。
```

### 解法

从题目我们可以知道，数组长度为 n，所有数字都在 `0~n-1` 范围内。如果元素不重复，那么数组应该就是 `[0, 1, 2, ...n-1]`（假设给数组排完了序）。也就是说，递增排序后，数组中的元素值与其对应的下标应该是相同的，即下标为 0 的元素值也是 0，以此类推。

首先，我们可以遍历数组，若存在元素不在 `0~n-1` 的范围内，直接返回 -1。

接着，再次遍历数组，若下标 `i` 与对应元素 `nums[i]` 不同，即 `nums[i] != i`，我们应该把 `nums[i]` 这个元素交换到正确的位置 `nums[i]`上。交换前，先判断 `nums[i]` 与 `nums[nums[i]]` 这两个元素是否相同，相同说明存在重复元素，直接返回，否则进行 swap 交换。交换过后，我们需要再次判断 i 位置上的元素，因此，我们使用 while 循环。

可对照下方代码实现，加深理解。

```java
class Solution {

    /**
     * 查找数组中的重复元素
     *
     * @param nums 数组
     * @return 其中一个重复的元素
     */
    public int duplicateInArray(int[] nums) {
        int n = nums.length;

        // 若存在数组元素不在[0, n-1] 的范围内，直接返回-1
        for (int num : nums) {
            if (num < 0 || num >= n) {
                return -1;
            }
        }

        for (int i = 0; i < n; ++i) {
            while (nums[i] != i) {
                if (nums[i] == nums[nums[i]]) {//一定要在nums[i] != i中，因为外面判断可能就是交换后的，这样才是原来的，如果有重复的就一定会进这个判断
                    // 说明位置i与位置nums[i]上的元素相同，直接返回该重复元素
                    return nums[i];
                }
                swap(nums, i, nums[i]);
            }
        }
        return -1;

    }

    private void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }
}
```

## 3.2 不修改数组找出重复的数字

### 题目描述

给定一个长度为 `n+1` 的数组 `nums`，数组中所有的数均在 `1∼n` 的范围内，其中 `n≥1`。

请找出数组中任意一个重复的数，但不能修改输入的数组。

**样例**

```
给定 nums = [2, 3, 5, 4, 3, 2, 6, 7]。

返回 2 或 3。
```

**思考题**：如果只能使用 `O(1)` 的额外空间，该怎么做呢？

### 解法

#### 解法一

创建长度为 `n+1` 的辅助数组，把原数组的元素复制到辅助数组中。如果原数组被复制的数是 `m`，则放到辅助数组第 `m` 个位置。这样很容易找出重复元素。空间复杂度为 `O(n)`。
或者用HashSet。
#### 解法二

数组元素的取值范围是 `[1, n]`，对该范围对半划分，分成 `[1, middle]`, `[middle+1, n]`。计算数组中有多少个(count)元素落在 `[1, middle]` 区间内，如果 count 大于 middle-1+1，那么说明这个范围内有重复元素，否则在另一个范围内。继续对这个范围对半划分，继续统计区间内元素数量。

时间复杂度 `O(n * log n)`，空间复杂度 `O(1)`。

注意，此方法无法找出所有重复的元素。

```java
class Solution {

    /**
     * 不修改数组查找重复的元素，没有则返回0
     *
     * @param nums 数组
     * @return 重复的元素
     */
    public int duplicateInArray(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        int start = 1, end = nums.length - 1;
        while (start <= end) {
            int mid = start + ((end - start) >> 1);
            int cnt = getCountRange(nums, start, mid);
            if (start == end) {
                if (cnt > 1) {
                    // 找到重复的数字
                    return start;
                }
                break;
            }
            if (cnt > mid - start + 1) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return 0;
    }

    /**
     * 计算整个数组中有多少个数的取值在[from, to] 之间
     *
     * @param nums 数组
     * @param from 左边界
     * @param to 右边界
     * @return 数量
     */
    private int getCountRange(int[] nums, int from, int to) {
        int cnt = 0;
        for (int e : nums) {
            if (e >= from && e <= to) {
                ++cnt;
            }
        }
        return cnt;
    }
}
```