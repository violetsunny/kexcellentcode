## 58.1 翻转单词顺序

来源：[AcWing](https://www.acwing.com/problem/content/15/)

### 题目描述

输入一个英文句子，翻转句子中单词的顺序，但单词内字符的顺序不变。

为简单起见，标点符号和普通字母一样处理。

例如输入字符串 `"I am a student."`，则输出 `"student. a am I"`。

**样例**

```
输入："I am a student."

输出："student. a am I"
```

### 解法

先对字符串按空格切割成数组，再逆序数组后，最后将元素拼接并返回。

```java
class Solution {
    /**
     * 翻转单词
     *
     * @param s 字符串
     * @return 翻转后的字符串
     */
    public String reverseWords(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }

        String[] arr = s.split(" ");
        int p = 0, q = arr.length - 1;
        while (p < q) {
            swap(arr, p++, q--);
        }
        return String.join(" ", arr);
    }
    private void swap(String[] arr, int p, int q) {
        String t = arr[p];
        arr[p] = arr[q];
        arr[q] = t;
    }
}
```

## 58.2 左旋转字符串

来源：[AcWing](https://www.acwing.com/problem/content/15/)

### 题目描述

字符串的左旋转操作是把字符串前面的若干个字符转移到字符串的尾部。

请定义一个函数实现字符串左旋转操作的功能。

比如输入字符串 `"abcdefg"` 和数字 2，该函数将返回左旋转 2 位得到的结果 `"cdefgab"`。

**注意：**

- 数据保证 n 小于等于输入字符串的长度。

**样例**

```
输入："abcdefg" , n=2

输出："cdefgab"
```

### 解法

先翻转前 n 个字符，再翻转后面的字符，最后整体翻转。

```java
class Solution {

    /**
     * 左旋转字符串
     *
     * @param str 字符串
     * @param n 左旋的位数
     * @return 旋转后的字符串
     */
    public String leftRotateString(String str, int n) {
        if (str == null || n < 1 || n > str.length()) {
            return str;
        }
        char[] chars = str.toCharArray();
        int len = chars.length;
        reverse(chars, 0, n - 1);
        reverse(chars, n, len - 1);
        reverse(chars, 0, len - 1);
        return new String(chars);
    }

    private void reverse(char[] chars, int p, int q) {
        while (p < q) {
            swap(chars, p++, q--);
        }
    }

    private void swap(char[] chars, int p, int q) {
        char t = chars[p];
        chars[p] = chars[q];
        chars[q] = t;
    }
}
```