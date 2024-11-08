---
comments: true
difficulty: 中等
edit_url: https://github.com/doocs/leetcode/edit/main/lcof/%E9%9D%A2%E8%AF%95%E9%A2%9849.%20%E4%B8%91%E6%95%B0/README.md
---

<!-- problem:start -->

# [丑数](https://leetcode.cn/problems/chou-shu-lcof/)

## 题目描述

<!-- description:start -->

<p>我们把只包含质因子 2、3 和 5 的数称作丑数（Ugly Number）。求按从小到大的顺序的第 n 个丑数。</p>

<p>&nbsp;</p>

<p><strong>示例:</strong></p>

<pre><strong>输入:</strong> n = 10
<strong>输出:</strong> 12
<strong>解释: </strong><code>1, 2, 3, 4, 5, 6, 8, 9, 10, 12</code> 是前 10 个丑数。</pre>

<p><strong>说明:&nbsp;</strong>&nbsp;</p>

<ol>
	<li><code>1</code>&nbsp;是丑数。</li>
	<li><code>n</code>&nbsp;<strong>不超过</strong>1690。</li>
</ol>

<p>注意：本题与主站 264 题相同：<a href="https://leetcode.cn/problems/ugly-number-ii/">https://leetcode.cn/problems/ugly-number-ii/</a></p>

<!-- description:end -->

## 解法

<!-- solution:start -->

### 方法一：优先队列（最小堆）

初始时，将第一个丑数 $1$ 加入堆。每次取出堆顶元素 $x$，由于 $2x$, $3x$, $5x$ 也是丑数，因此将它们加入堆中。为了避免重复元素，可以用哈希表 $vis$ 去重。

时间复杂度 $O(n \times \log n)$，空间复杂度 $O(n)$。

<!-- tabs:start -->

```java
class Solution {
    public int nthUglyNumber(int n) {
        Set<Long> vis = new HashSet<>();
        PriorityQueue<Long> q = new PriorityQueue<>();
        int[] f = new int[] {2, 3, 5};
        q.offer(1L);
        vis.add(1L);
        long ans = 0;
        while (n-- > 0) {
            ans = q.poll();
            for (int v : f) {
                long next = ans * v;
                if (vis.add(next)) {
                    q.offer(next);
                }
            }
        }
        return (int) ans;
    }
}
```

<!-- tabs:end -->

<!-- solution:end -->

<!-- solution:start-->

### 方法二：动态规划

我们定义数组 $dp$，其中 $dp[i-1]$ 表示第 $i$ 个丑数，那么第 $n$ 个丑数就是 $dp[n - 1]$。最小的丑数是 $1$，所以 $dp[0]=1$。

定义 $3$ 个指针 $p_2$, $p_3$ 和 $p_5$，表示下一个丑数是当前指针指向的丑数乘以对应的质因数。初始时，三个指针的值都指向 $0$。

当 $i$ 在 $[1,2..n-1]$ 范围内，我们更新 $dp[i]=\min(dp[p_2] \times 2, dp[p_3] \times 3, dp[p_5] \times 5)$，然后分别比较 $dp[i]$ 与 $dp[p_2] \times 2$, $dp[p_3] \times 3$, $dp[p_5] \times 5$ 是否相等，若是，则对应的指针加 $1$。

最后返回 $dp[n - 1]$ 即可。

时间复杂度 $O(n)$，空间复杂度 $O(n)$。

<!-- tabs:start -->

```java
class Solution {
    public int nthUglyNumber(int n) {
        int[] dp = new int[n];
        dp[0] = 1;
        int p2 = 0, p3 = 0, p5 = 0;
        for (int i = 1; i < n; ++i) {
            int next2 = dp[p2] * 2, next3 = dp[p3] * 3, next5 = dp[p5] * 5;
            dp[i] = Math.min(next2, Math.min(next3, next5));
            if (dp[i] == next2) ++p2;
            if (dp[i] == next3) ++p3;
            if (dp[i] == next5) ++p5;
        }
        return dp[n - 1];
    }
}
```

<!-- tabs:end -->

<!-- solution:end -->

<!-- problem:end -->
