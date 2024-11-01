---
comments: true
difficulty: 简单
edit_url: https://github.com/doocs/leetcode/edit/main/lcof/%E9%9D%A2%E8%AF%95%E9%A2%9850.%20%E7%AC%AC%E4%B8%80%E4%B8%AA%E5%8F%AA%E5%87%BA%E7%8E%B0%E4%B8%80%E6%AC%A1%E7%9A%84%E5%AD%97%E7%AC%A6/README.md
---

<!-- problem:start -->

# [面试题 50. 第一个只出现一次的字符](https://leetcode.cn/problems/di-yi-ge-zhi-chu-xian-yi-ci-de-zi-fu-lcof/)

## 题目描述

<!-- description:start -->

<p>在字符串 s 中找出第一个只出现一次的字符。如果没有，返回一个单空格。 s 只包含小写字母。</p>

<p><strong>示例 1:</strong></p>

<pre>
输入：s = "abaccdeff"
输出：'b'
</pre>

<p><strong>示例 2:</strong></p>

<pre>
输入：s = ""
输出：' '
</pre>

<p>&nbsp;</p>

<p><strong>限制：</strong></p>

<p><code>0 &lt;= s 的长度 &lt;= 50000</code></p>

<!-- description:end -->

## 解法

<!-- solution:start -->

### 方法一：数组或哈希表

我们可以使用哈希表或数组 $cnt$ 来统计每个字符出现的次数，然后再遍历一遍字符串，找到第一个出现次数为 $1$ 的字符。

时间复杂度 $O(n)$，空间复杂度 $O(C)$。其中 $n$ 为字符串长度；而 $C$ 为字符集大小，本题中 $C=26$。

<!-- tabs:start -->

```java
class Solution {
    public char firstUniqChar(String s) {
        int[] cnt = new int[26];
        for (int i = 0; i < s.length(); ++i) {
            ++cnt[s.charAt(i) - 'a'];
        }
        //按s遍历，不会乱。cnt按字母排序的会乱
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (cnt[c - 'a'] == 1) {
                return c;
            }
        }
        return ' ';
    }
}
```


<!-- tabs:end -->

<!-- solution:end -->

<!-- problem:end -->
