## [29. 顺时针打印矩阵](https://leetcode.cn/problems/shun-shi-zhen-da-yin-ju-zhen-lcof/)


### 题目描述

输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字。

**样例**

```
输入：
[
  [1, 2, 3, 4],
  [5, 6, 7, 8],
  [9,10,11,12]
]

输出：[1,2,3,4,8,12,11,10,9,5,6,7]
```

### 解法

由外往里，一圈圈打印矩阵即可。

```java
class Solution {
    public int[] printMatrix(int[][] matrix) {
        if (matrix == null || matrix.length < 1) {
            return new int[] {};
        }
        int m = matrix.length, n = matrix[0].length;
        int[] res = new int[m * n];
        int[] index = new int[1];
        index[0] = 0;
        int i = 0, j = 0, p = m - 1, q = n - 1;
        while (i <= p && j <= q) {
            add(matrix, res, index, i++, j++, p--, q--);
        }
        return res;
    }

    private void add(int[][] matrix, int[] res, int[] index, int i, int j, int p, int q) {
        if (i == p) {
            for (int m = j; m <= q; ++m) {
                res[index[0]++] = matrix[i][m];
            }
        } else if (j == q) {
            for (int m = i; m <= p; ++m) {
                res[index[0]++] = matrix[m][j];
            }
        } else {
            for (int m = j; m < q; ++m) {
                res[index[0]++] = matrix[i][m];
            }
            for (int m = i; m < p; ++m) {
                res[index[0]++] = matrix[m][q];
            }
            for (int m = q; m > j; --m) {
                res[index[0]++] = matrix[p][m];
            }
            for (int m = p; m > i; --m) {
                res[index[0]++] = matrix[m][j];
            }
        }

    }
}
```

### 解法二

左l=0 右r=n-1 上t=0 下b=m-1

```java
class Solution {
    public int[] printMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return new int[0];
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int[] res = new int[m * n];
        int l = 0, r = n - 1, t = 0, b = m - 1;
        int k = 0;
        while (true) {
            //左往右，l移动
            for (int i = t, j = l; j <= r; j++) {
                res[k++] = matrix[i][j];
            }
            t++;//往下一层
            if (t > b) {
                break;
            }
            //上往下，t移动
            for (int i = t, j = r; i <= b; i++) {
                res[k++] = matrix[i][j];
            }
            r--;//往左一层
            if (l > r) {
                break;
            }
            //右往左，r移动
            for (int i = b, j = r; j >= l; j--) {
                res[k++] = matrix[i][j];
            }
            b--;//往上一层
            if (t > b) {
                break;
            }
            //下往上，b移动
            for (int i = b, j = l; i >= t; i--) {
                res[k++] = matrix[i][j];
            }
            l++;//往右一层
            if (l > r) {
                break;
            }
        }
        return res;
    }
}
```