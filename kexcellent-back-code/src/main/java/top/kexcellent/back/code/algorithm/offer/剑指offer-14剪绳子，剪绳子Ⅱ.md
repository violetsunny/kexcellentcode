## [14- I. 剪绳子](https://leetcode.cn/problems/jian-sheng-zi-lcof/)


### 题目描述

给你一根长度为 n 绳子，请把绳子剪成 m 段（m、n 都是整数，n>1 并且 m≥1）。

每段的绳子的长度记为 `k[0]、k[1]、……、k[m]`。`k[0]k[1] … k[m]`可能的最大乘积是多少？

例如当绳子的长度是 8 时，我们把它剪成长度分别为 2、3、3 的三段，此时得到最大的乘积 18。

**样例**

```
输入：8

输出：18
```

### 解法

#### 解法一：动态规划法

时间复杂度`O(n²)`，空间复杂度`O(n)`。

```
f(n) = max{f(n), f(i) * f(n - i)}, i = 1,2..n-1
```

- 长度为 2，只可能剪成长度为 1 的两段，因此 f(2)=1
- 长度为 3，剪成长度分别为 1 和 2 的两段，乘积比较大，因此 f(3) = 2
- 长度为 n，在剪第一刀的时候，有 n-1 种可能的选择，剪出来的绳子又可以继续剪，可以看出，原问题可以划分为子问题，子问题又有重复子问题。

```java
class Solution {

    /**
     * 剪绳子求最大乘积
     *
     * @param length 绳子长度
     * @return 乘积最大值
     */
    public int maxProductAfterCutting(int length) {
        if (length < 4) {
            return length - 1;
        }

        int[] res = new int[length + 1];
        res[1] = 1;
        res[2] = 2;
        res[3] = 3;
        for (int i = 4; i <= length; ++i) {
            for (int j = 1; j < i / 2 + 1; ++j) {//暴力检查所有值比较
                res[i] = Math.max(res[i], res[j] * res[i - j]);
            }
        }
        return res[length];
    }
}
```

#### 解法二 贪心算法

时间复杂度`O(1)`，空间复杂度`O(1)`。

贪心策略：

- 当 n>=5 时，尽可能多地剪长度为 3 的绳子
- 当剩下的绳子长度为 4 时，就把绳子剪成两段长度为 2 的绳子。

**证明：**

- 当 n>=5 时，可以证明 2(n-2)>n，并且 3(n-3)>n。也就是说，当绳子剩下长度大于或者等于 5 的时候，可以把它剪成长度为 3 或者 2 的绳子段。
- 当 n>=5 时，3(n-3)>=2(n-2)，因此，应该尽可能多地剪长度为 3 的绳子段。
- 当 n=4 时，剪成两根长度为 2 的绳子，其实没必要剪，只是题目的要求是至少要剪一刀。

```java
class Solution {

    /**
     * 剪绳子求最大乘积
     *
     * @param length 绳子长度
     * @return 乘积最大值
     */
    public int maxProductAfterCutting(int length) {
        if (length < 4) {
            return length - 1;
        }

        int timesOf3 = length / 3;//能有多少个3
        if (length % 3 == 1) {
            --timesOf3;
        }
        int timesOf2 = (length - timesOf3 * 3) >> 1;//剩下的能有多少个2
        return (int) (Math.pow(2, timesOf2) * Math.pow(3, timesOf3));
    }

    /**
     * 易懂写法
     * @param length
     * @return
     */
    public int maxProductAfterCutting(int length) {
        if (length < 4) {
            return length - 1;
        }

        int timesOf3 = length / 3;//能有多少个3
        int mod = length % 3;
        if(mod == 0){
            return (int)Math.pow(3, timesOf3);
        } else if(mod == 1){
            return (int)Math.pow(3, timesOf3 - 1) * 4;
        } else {
            return (int)Math.pow(3, timesOf3) * 2;
        }
    }
}
```

# [14- II. 剪绳子 II](https://leetcode.cn/problems/jian-sheng-zi-ii-lcof/)

## 题目描述

<!-- description:start -->

<p>给你一根长度为 <code>n</code> 的绳子，请把绳子剪成整数长度的 <code>m</code>&nbsp;段（m、n都是整数，n&gt;1并且m&gt;1），每段绳子的长度记为 <code>k[0],k[1]...k[m - 1]</code> 。请问 <code>k[0]*k[1]*...*k[m - 1]</code> 可能的最大乘积是多少？例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18。</p>

<p>答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。</p>

<p>&nbsp;</p>

<p><strong>示例 1：</strong></p>

<pre><strong>输入: </strong>2
<strong>输出: </strong>1
<strong>解释: </strong>2 = 1 + 1, 1 &times; 1 = 1</pre>

<p><strong>示例&nbsp;2:</strong></p>

<pre><strong>输入: </strong>10
<strong>输出: </strong>36
<strong>解释: </strong>10 = 3 + 3 + 4, 3 &times;&nbsp;3 &times;&nbsp;4 = 36</pre>

<p>&nbsp;</p>

<p><strong>提示：</strong></p>

<ul>
	<li><code>2 &lt;= n &lt;= 1000</code></li>
</ul>

<p>注意：本题与主站 343 题相同：<a href="https://leetcode.cn/problems/integer-break/">https://leetcode.cn/problems/integer-break/</a></p>

<!-- description:end -->

## 解法

<!-- solution:start -->

### 方法一：数学（快速幂）

当 $n \lt 4$，此时 $n$ 不能拆分成至少两个正整数的和，因此 $n - 1$ 是最大乘积。当 $n \ge 4$ 时，我们尽可能多地拆分 $3$，当剩下的最后一段为 $4$ 时，我们将其拆分为 $2 + 2$，这样乘积最大。

时间复杂度 $O(\log n)$，空间复杂度 $O(1)$。

```java
class Solution {
    private final int mod = (int) 1e9 + 7;

    public int cuttingRope(int n) {
        if (n < 4) {
            return n - 1;
        }
        if (n % 3 == 0) {
            return qpow(3, n / 3);
        }
        if (n % 3 == 1) {//结果可能超过
            return (int) (4L * qpow(3, n / 3 - 1) % mod);
        }
        return 2 * qpow(3, n / 3) % mod;
    }

    private int qpow(long a, long n) {
        long ans = 1;
        for (; n > 0; n >>= 1) {
            if ((n & 1) == 1) {
                ans = ans * a % mod;
            }
            a = a * a % mod;
        }
        return (int) ans;
    }
}
```