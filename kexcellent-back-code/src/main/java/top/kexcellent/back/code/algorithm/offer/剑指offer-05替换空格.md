# [05. 替换空格](https://leetcode.cn/problems/ti-huan-kong-ge-lcof/)

### 题目描述

请实现一个函数，把字符串中的每个空格替换成 `"%20"`。

你可以假定输入字符串的长度最大是 `1000`。
注意输出字符串的长度可能大于 `1000`。

**样例**

```
输入："We are happy."

输出："We%20are%20happy."
```

### 解法

#### 解法一

利用正则匹配替换。

```java
class Solution {

    /**
     * 将字符串中的所有空格替换为%20
     *
     * @param str 字符串
     * @return 替换后的字符串
     */
    public String replaceSpaces(StringBuffer str) {
        return str == null ? null : str.toString().replaceAll(" ", "%20");
    }
}
```

#### 解法二

先遍历原字符串，遇到空格，则在原字符串末尾 `append` 任意两个字符，如两个空格。

用指针 `i` 指向原字符串末尾，`j` 指向现字符串末尾，`i`, `j` 从后往前遍历，当 `i` 遇到空格，`j` 位置依次要赋值为 `'0','2','%'`，若不是空格，直接赋值为 `i` 指向的字符。

**思路扩展：**

在合并两个数组（包括字符串）时，如果从前往后复制每个数字（或字符）需要重复移动数字（或字符）多次，那么我们可以考虑**从后往前**复制，这样就能减少移动的次数，从而提高效率。

```java
class Solution {

    /**
     * 将字符串中的所有空格替换为%20
     *
     * @param str 字符串
     * @return 替换后的字符串
     */
    public String replaceSpaces(StringBuffer str) {
        if (str == null) {
            return null;
        }

        int len = str.length();
        for (int i = 0; i < len; ++i) {
            if (str.charAt(i) == ' ') {
                str.append("  ");//后面追加两个空格，增加长度
            }
        }

        int i = len - 1, j = str.length() - 1;//i是原来的长度，j是新的长度
        for (; i >= 0; --i) {
            char ch = str.charAt(i);//i指向最后字符开始判断
            if (ch == ' ') {
                str.setCharAt(j--, '0');//j从新的长度空间最后往前进行更新替换，也不会覆盖原来的字符因为最后是空格" "
                str.setCharAt(j--, '2');
                str.setCharAt(j--, '%');
            } else {
                str.setCharAt(j--, ch);
            }
        }
        return str.toString();
    }
}
```