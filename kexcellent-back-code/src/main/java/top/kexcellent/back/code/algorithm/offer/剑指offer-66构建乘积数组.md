## [66. 构建乘积数组](https://leetcode.cn/problems/gou-jian-cheng-ji-shu-zu-lcof/)


### 题目描述

给定一个数组 `A[0, 1, …, n-1]`，请构建一个数组 `B[0, 1, …, n-1]`，其中 `B` 中的元素 `B[i]=A[0]×A[1]×… ×A[i-1]×A[i+1]×…×A[n-1]`。

不能使用除法。

**样例**

```
输入：[1, 2, 3, 4, 5]

输出：[120, 60, 40, 30, 24]
```

**思考题：**

- 能不能只使用常数空间？（除了输出的数组之外）

### 解法

把 B 的每个元素 `B[i]` 看成两半的乘积，即 `A[0]xA[1]x...xA[i-1]` 和 `A[i+1]xA[i+2]xA[n-1]`。

- 对于左半部分：B[i] = B[i - 1] \* A[i - 1]

```java
class Solution {

    /**
     * 构建乘积数组
     *
     * @param nums 数组A
     * @return 乘积数组B
     */
    public int[] productExceptSelf(int[] nums) {
        int[] res = new int[nums.length];
        res[0]=1;
        //i 先计算0到i-1的乘积
        for(int i=1;i<nums.length;i++){
            res[i] = res[i-1]*nums[i-1];
        }
        //再反哺i+1到n的
        int t = 1;
        for(int i=nums.length-1;i>0;i--){
            t = t*nums[i];
            res[i-1] = t*res[i-1];
        }

        return res;
    }
}
```
