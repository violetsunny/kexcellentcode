## [17. 打印从 1 到最大的 n 位数](https://leetcode.cn/problems/da-yin-cong-1dao-zui-da-de-nwei-shu-lcof/)

### 题目描述

输入数字 `n`，按顺序打印出从 `1` 最大的 `n` 位十进制数。比如输入 `3`，则打印出 `1、2、3` 一直到最大的 3 位数即 999。

### 解法

此题需要注意 n 位数构成的数字可能超出最大的 int 或者 long long 能表示的范围。因此，采用字符数组来存储数字。

### 解法三：pow
小数直接用这个
````java
class Solution {
    public int[] countNumbers(int cnt) {
        int n = (int)Math.pow(10,cnt);
        int[] res = new int[n-1];
        for(int i=1;i<n;i++){
            res[i-1]=i;
        }
        return res;
    }
}
````

### 解法一

- 对字符数组表示的数进行递增操作；
- 输出数字（0 开头的需要把 0 去除）。

```java
class Solution {

    /**
     * 打印从1到最大的n位数
     *
     * @param n n位数
     */
    public void print1ToMaxOfNDigits(int n) {
        if (n < 1) {
            return;
        }
        char[] chars = new char[n];
        Arrays.fill(chars, '0');
        while (increment(chars)) {
            printNumber(chars);
        }
    }


    /**
     * 打印字符数组表示的数字（需要省略前n个0）
     *
     * @param chars 字符数组
     */
    private void printNumber(char[] chars) {
        int i = 0, n = chars.length;
        for (; i < n; ++i) {
            if (chars[i] != '0') {
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (; i < n; ++i) {
            sb.append(chars[i]);
        }
        System.out.println(sb.toString());
    }

    //初始化n位数组，chars每次加1后打印，每一位只加到9
    private boolean increment(char[] chars) {
        int n = chars.length;
        int carry = 1;
        for (int i = n - 1; i >= 0; --i) {
            int sum = chars[i] - '0' + carry;//chars[i] - '0' 计算ASCII码去除后的值
            if (sum > 9) {
                if (i == 0) {
                    return false;
                }
                chars[i] = '0';
            } else {
                ++chars[i];
                break;
            }
        }
        return true;
    }
}
```

### 解法二

利用递归全排列，设置每一位，设置完之后，打印出来。
数据大用这个，效率更好
```java
class Solution {

    /**
     * 打印从1到最大的n位数
     *
     * @param n n位数
     */
    public void print1ToMaxOfNDigits(int n) {
        if (n < 1) {
            return;
        }
        char[] chars = new char[n];
        print1ToMaxOfNDigits(chars, n, 0);
    }

    private void print1ToMaxOfNDigits(char[] chars, int n, int i) {
        if (i == n) {//[0,0,0,0...],为了从最右侧开始计算
            printNumber(chars);
            return;
        }

        // 每一位分别设置从0到9
        for (int j = 0; j < 10; ++j) {
            chars[i] = (char) (j + '0');//j+48的ASCII码再转成char字符，j直接转会乱码
            print1ToMaxOfNDigits(chars, n, i + 1);
        }
    }


    /**
     * 打印字符数组表示的数字（需要省略前n个0）
     *
     * @param chars 字符数组
     */
    private void printNumber(char[] chars) {
        int i = 0, n = chars.length;
        for (; i < n; ++i) {
            if (chars[i] != '0') {
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (; i < n; ++i) {
            sb.append(chars[i]);
        }
        System.out.println(sb.toString());
    }
}
```