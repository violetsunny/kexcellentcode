## [44. 数字序列中某一位的数字](https://leetcode.cn/problems/shu-zi-xu-lie-zhong-mou-yi-wei-de-shu-zi-lcof/)

### 题目描述

数字以 `0123456789101112131415…` 的格式序列化到一个字符序列中。

在这个序列中，第 5 位（从 0 开始计数）是 5，第 13 位是 1，第 19 位是 4，等等。

请写一个函数求任意位对应的数字。

### 解法

举个栗子，求序列第 1001 位。

序列的前 10 位是 `0~9`， 这 10 个只有一位的数字。显然第 1001 位在这 10 个数字之后，因此这 10 个数字可以直接跳过。再从后面序列中找第 991（991=1001-10） 位的数字。接下来有 90 个两位数，共 180 位，由于 991>180，所以继续跳过。从 881 找...最后可以找到对应的数字以及数字的某一位。

```java
public class Solution {
    /**
     * 求数字序列中某一位的数字
     *
     * @param n 第n位
     * @return 第n位的数字
     */
    public int digitAtIndex(int n) {
        if (n < 0) {
            return -1;
        }
        int digits = 1;
        while (true) {
            long numbers = countOfIntegers(digits);
            if (n < digits * numbers) {
                break;
            }
            n -= numbers * digits;
            ++digits;
        }
        return digitAtIndex(digits, n);

    }

    private long countOfIntegers(int digits) {
        return digits == 1
                ? 10
                : (int) (9 * Math.pow(10, digits - 1));
    }

    private int digitAtIndex(int digits, int n) {
        int beginNumber = getBeginNumber(digits);
        int val =  beginNumber + n / digits;
        int indexFromRight = digits - n % digits;
        for (int i = 1; i < indexFromRight; ++i) {
            val /= 10;
        }
        return val % 10;
    }

    private int getBeginNumber(int digits) {
        return digits == 1
                ? 0
                : (int) Math.pow(10, digits - 1);
    }
}
````
### 解法二
位数为 k 的最小整数和最大整数分别为 10^{k-1} 和 10^k-1，因此 k 位数的总位数为 k * 9 * 10^{k-1}。

我们用 k 表示当前数字的位数，用 cnt 表示当前位数的数字的总数，初始时 k=1, cnt=9。

每次将 n 减去 cnt * k，当 n 小于等于 cnt * k 时，说明 n 对应的数字在当前位数的数字范围内，此时可以计算出对应的数字。

具体做法是，首先计算出 n 对应的是当前位数的哪一个数字，然后计算出是该数字的第几位，从而得到该位上的数字。

时间复杂度 O(\log_{10} n)，空间复杂度 O(1)。其中 n 为给定的数字

```java
class Solution {
    public int findNthDigit(int n) {
        int k = 1, cnt = 9;
        while ((long) k * cnt < n) {
            n -= k * cnt;
            ++k;
            cnt *= 10;
        }
        int num = (int) Math.pow(10, k - 1) + (n - 1) / k;
        int idx = (n - 1) % k;
        return String.valueOf(num).charAt(idx) - '0';
    }
}
```