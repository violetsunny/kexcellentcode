## [10- I. 斐波那契数列](https://leetcode.cn/problems/fei-bo-na-qi-shu-lie-lcof/)

### 题目描述

输入一个整数 n ，求斐波那契数列的第 n 项。

假定从 0 开始，第 0 项为 0。`(n<=39)`

**样例**

```
输入整数 n=5

返回 5
```

### 解法

#### 解法一

采用递归方式，简洁明了，但效率很低，存在大量的重复计算。

```
                  f(10)
               /        \
            f(9)         f(8)
          /     \       /    \
       f(8)     f(7)  f(7)   f(6)
      /   \     /   \
   f(7)  f(6)  f(6) f(5)
```

```java
class Solution {

    /**
     * 求斐波那契数列的第n项，n从0开始
     *
     * @param n 第n项
     * @return 第n项的值
     */
    public int Fibonacci(int n) {
        if (n < 2) {
            return n;
        }
        return Fibonacci(n - 1) + Fibonacci(n - 2);//大量重复计算，会超时
    }
}
```

#### 解法二

从下往上计算，递推，时间复杂度 `O(n)`。可以用数组存储，空间复杂度 `O(n)`；也可以用变量存储，空间复杂度 `O(1)`。

```java
class Solution {

    /**
     * 求斐波那契数列的第n项，n从0开始
     *
     * @param n 第n项
     * @return 第n项的值
     */
    public int Fibonacci(int n) {
        if (n < 2) {
            return n;
        }

        int a = 1, b = 1;
        for (int i = 2; i < n; ++i) {
            b = a + b;//b最新位置
            a = b - a;//a为上次位置
        }
        return b;
    }
}
```

## [0- II. 青蛙跳台阶问题](https://leetcode.cn/problems/qing-wa-tiao-tai-jie-wen-ti-lcof/)

### 题目描述

一只青蛙一次可以跳上`1`级台阶，也可以跳上`2`级。求该青蛙跳上一个`n`级的台阶总共有多少种跳法（先后次序不同算不同的结果）。

### 解法

跳上 `n` 级台阶，可以从 `n-1` 级跳 `1` 级上去，也可以从 `n-2` 级跳 `2` 级上去。所以

```
f(n) = f(n-1) + f(n-2)
```

```java
class Solution {

    /**
     * 青蛙跳台阶
     *
     * @param target 跳上的那一级台阶
     * @return 多少种跳法
     */
    public int JumpFloor(int target) {
        if (target < 3) {
            return target;
        }
        int a = 1, b = 2;
        for (int i = 3; i <= target; ++i) {
            b = a + b;
            a = b - a;
        }
        return b;
    }
}
```

## 10.3 变态跳台阶

### 题目描述

一只青蛙一次可以跳上`1`级台阶，也可以跳上`2`级……它也可以跳上`n`级。求该青蛙跳上一个`n`级的台阶总共有多少种跳法。

### 解法

#### 解法一：数学推导

跳上 `n-1` 级台阶，可以从 `n-2` 级跳 `1` 级上去，也可以从 `n-3` 级跳 `2` 级上去...那么

```
f(n-1) = f(n-2) + f(n-3) + ... + f(0)
```

跳上 `n` 级台阶，可以从 `n-1` 级跳 `1` 级上去，也可以从 `n-2` 级跳 `2` 级上去...那么

```
f(n) = f(n-1) + f(n-2) + ... + f(0)
```

综上可得

```
f(n) - f(n-1) = f(n-1)
```

即

```
f(n) = 2*f(n-1)
```

所以 f(n) 是一个等比数列

```java
class Solution {

    /**
     * 青蛙跳台阶II
     *
     * @param target 跳上的那一级台阶
     * @return 多少种跳法
     */
    public int JumpFloorII(int target) {
        return (int) Math.pow(2, target - 1);//2的target次方
    }
}
```

#### 解法二：动态规划

每当计算 res[i]，把前面所有结果累加起来。

```java
class Solution {

    /**
     * 青蛙跳台阶II
     *
     * @param target 跳上的那一级台阶
     * @return 多少种跳法
     */
    public int JumpFloorII(int target) {
        if (target < 3) {
            return target;
        }
        int[] res = new int[target + 1];
        Arrays.fill(res, 1);//赋初始值
        for (int i = 2; i <= target; ++i) {
            for (int j = 1; j < i; ++j) {//当前i需要将前面所有j的值相加
                res[i] += res[j];
            }
        }
        return res[target];
    }
}
```

## 10.4 矩形覆盖

### 题目描述

我们可以用`2*1`的小矩形横着或者竖着去覆盖更大的矩形。请问用`n`个`2*1`的小矩形无重叠地覆盖一个`2*n`的大矩形，总共有多少种方法？

### 解法

覆盖 `2*n` 的矩形：

- 可以先覆盖 `2*n-1` 的矩形，再覆盖一个 `2*1` 的矩形；
- 也可以先覆盖 `2*(n-2)` 的矩形，再覆盖两个 `1*2` 的矩形。

#### 解法一：利用数组存放结果

```java
class Solution {

    /**
     * 矩形覆盖
     *
     * @param target 2*target大小的矩形
     * @return 多少种覆盖方法
     */
    public int RectCover(int target) {
        if (target < 3) {
            return target;
        }
        int[] res = new int[target + 1];
        res[1] = 1;
        res[2] = 2;
        for (int i = 3; i <= target; ++i) {//f(n)=f(n-1)+f(n-2);
            res[i] = res[i - 1] + res[i - 2];
        }
        return res[target];
    }
}
```

#### 解法二：直接用变量存储结果

```java
class Solution {

    /**
     * 矩形覆盖
     *
     * @param target 2*target大小的矩形
     * @return 多少种覆盖方法
     */
    public int RectCover(int target) {
        if (target < 3) {
            return target;
        }
        int a = 1, b = 2;//有一种或两种
        for (int i = 3; i <= target; ++i) {
            b = a + b;
            a = b - a;
        }
        return b;
    }
}
```