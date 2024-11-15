## [64. 求 1+2+…+n](https://leetcode.cn/problems/qiu-12n-lcof/)


### 题目描述

求 `1+2+…+n`,要求不能使用 `乘除法、for、while、if、else、switch、case` 等关键字及条件判断语句 `A?B:C`。

**样例**

```
输入：10

输出：55
```

### 解法

利用 Stream API。

```java
import java.util.stream.IntStream;

class Solution {

    /**
     * 求1+2+…+n（不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C））
     *
     * @param n 1~n
     * @return 1~n的和
     */
    public int getSum(int n) {
        return IntStream.rangeClosed(1, n).sum();
    }
}
```