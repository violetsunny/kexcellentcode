# [递增的三元子序列](https://leetcode.cn/problems/increasing-triplet-subsequence/description/)

给你一个整数数组 nums ，判断这个数组中是否存在长度为 3 的递增子序列。

如果存在这样的三元组下标 (i, j, k) 且满足 i < j < k ，使得 nums[i] < nums[j] < nums[k] ，返回 true ；否则，返回 false 。


````
示例 1：
输入：nums = [1,2,3,4,5]
输出：true
解释：任何 i < j < k 的三元组都满足题意
示例 2：
输入：nums = [5,4,3,2,1]
输出：false
解释：不存在满足题意的三元组
示例 3：
输入：nums = [2,1,5,0,4,6]
输出：true
解释：三元组 (3, 4, 5) 满足题意，因为 nums[3] == 0 < nums[4] == 4 < nums[5] == 6
````
提示：
````
- 1 <= nums.length <= 5 * 105
- -231 <= nums[i] <= 231 - 1
````
进阶：你能实现时间复杂度为 O(n) ，空间复杂度为 O(1) 的解决方案吗？

## 解法一：遍历找最大最小
找打最大值和最小值，在这之间找到大于最小小于最大的就可以
````java
class Solution {
    public boolean increasingTriplet(int[] nums) {
        int n = nums.length;
        if (n < 3) {
            return false;
        }
        int[] min = new int[n];
        min[0] = nums[0];
        for (int i = 1; i < n; i++) {
            min[i] = Math.min(min[i - 1], nums[i]);
        }
        int[] max = new int[n];
        max[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            max[i] = Math.max(max[i + 1], nums[i]);
        }
        for (int i = 1; i < n - 1; i++) {
            if (nums[i] > min[i - 1] && nums[i] < max[i + 1]) {
                return true;
            }
        }
        return false;
    }
}
````

## 解法二：贪心
遍历过程中将最小的给first，次小的给second，找到比second大的就行
````java
class Solution {
    public boolean increasingTriplet(int[] nums) {
        if(nums.length<3){
            return false;
        }
        int first = nums[0];
        int second = Integer.MAX_VALUE;
        for(int num:nums){
            if(num>second){
                return true;
            } else if(num>first){
                second = num;
            } else {
                first = num;
            }
        }
        return false;
    }
}
````