## [42. 连续子数组的最大和](https://leetcode.cn/problems/lian-xu-zi-shu-zu-de-zui-da-he-lcof/)

### 题目描述

输入一个**非空**整型数组，数组里的数可能为正，也可能为负。
数组中一个或连续的多个整数组成一个子数组。求所有子数组的和的最大值。

要求时间复杂度为`O(n)`。

### 解法

动态规划法。

res[i] 表示以第 i 个数字结尾的子数组的最大和，那么求出 `max(res[i])` 即可。

- `res[i] = array[i]`, if `res[i - 1] < 0`
- `res[i] = res[i - 1] + array[i]`, if `res[i - 1] >= 0`

```java
public class Solution {
    /**
     * 求连续子数组的最大和
     *
     * @param array 数组
     * @return 最大和
     */
    public int FindGreatestSumOfSubArray(int[] array) {
        int n = array.length;
        int[] res = new int[n];
        res[0] = array[0];
        int max = res[0];
        for (int i = 1; i < n; ++i) {
            res[i] = res[i - 1] > 0 ? res[i - 1] + array[i] : array[i];//要是小于0就是减，不如不要
            max = Math.max(max, res[i]);
        }
        return max;
    }
}
```
最优写法
```java
class Solution {
    public int maxSubArray(int[] nums) {
        int ans = Integer.MIN_VALUE;
        int f = 0;
        for (int x : nums) {
            f = Math.max(f, 0) + x;
            ans = Math.max(ans, f);
        }
        return ans;
    }
}
```

# [42.2 乘积最大子数组](https://leetcode.cn/problems/maximum-product-subarray/description/)

给你一个整数数组 nums ，请你找出数组中乘积最大的非空连续
子数组
（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。

测试用例的答案是一个 32-位 整数。
```
示例 1:
输入: nums = [2,3,-2,4]
输出: 6
解释: 子数组 [2,3] 有最大乘积 6。
```
```
示例 2:
输入: nums = [-2,0,-1]
输出: 0
解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。
```


提示:
- 1 <= nums.length <= 2 * 104
- -10 <= nums[i] <= 10
- nums 的任何子数组的乘积都 保证 是一个 32-位 整数

````java
class Solution {
    public int maxProduct(int[] nums) {
        if(nums.length ==1){
            return nums[0];
        }
        int a = 1;
        int n = nums.length;
        int max = nums[0];
        for(int i=0;i<n;i++){ //正着来一遍
            a *= nums[i];
            max = Math.max(a,max);
            if(a == 0){//等于0了，a需要重新计算
                a = 1;
            }
        }

        a = 1;
        for(int i=n-1;i>=0;i--){//反着再来一遍，为了防止负数的情况
            a *= nums[i];
            max = Math.max(a,max);
            if(a == 0){
                a = 1;
            }
        }

        return max;
    }
}
````