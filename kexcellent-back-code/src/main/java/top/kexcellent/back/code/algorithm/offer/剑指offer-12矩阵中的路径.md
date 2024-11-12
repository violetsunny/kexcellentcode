## [12. 矩阵中的路径](https://leetcode.cn/problems/ju-zhen-zhong-de-lu-jing-lcof/)

### 题目描述

请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。

路径可以从矩阵中的任意一个格子开始，每一步可以在矩阵中向左，向右，向上，向下移动一个格子。

如果一条路径经过了矩阵中的某一个格子，则之后不能再次进入这个格子。

**注意**：

- 输入的路径不为空；
- 所有出现的字符均为大写英文字母。

**样例**

```
matrix=
[
  ['A','B','C','E'],
  ['S','F','C','S'],
  ['A','D','E','E']
]

str="BCCE" , return "true"

str="ASAE" , return "false"
```

### 解法：回溯

回溯法。首先，任选一个格子作为路径起点。假设格子对应的字符为 ch，并且对应路径上的第 i 个字符。若相等，到相邻格子寻找路径上的第 i+1 个字符。重复这一过程。

```java
class Solution {

    /**
     * 判断矩阵中是否包含某条路径
     *
     * @param matrix 矩阵
     * @param str 路径
     * @return 是否包含某条路径
     */
    public boolean hasPath(char[][] matrix, String str) {
        if (matrix ==  null || matrix.length == 0 || str == null) {
            return false;
        }

        int m = matrix.length, n = matrix[0].length;

        boolean[][] visited = new boolean[m][n];
        int pathLength = 0;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {//如果找不到需要回溯
                if (hasPath(matrix, str, i, j, visited, pathLength)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasPath(char[][] matrix, String str, int i, int j, boolean[][] visited, int pathLength) {
        if (pathLength == str.length()) {
            return true;
        }
        boolean hasPath = false;
        if (i >= 0 && i < matrix.length && j >= 0 && j < matrix[0].length
                && !visited[i][j] && matrix[i][j] == str.charAt(pathLength)) {
            ++pathLength;
            visited[i][j] = true;
            hasPath = hasPath(matrix, str, i + 1, j, visited, pathLength)
                    || hasPath(matrix, str, i - 1, j, visited, pathLength)
                    || hasPath(matrix, str, i, j + 1, visited, pathLength)
                    || hasPath(matrix, str, i, j - 1, visited, pathLength);
            if (!hasPath) {
                --pathLength;
                visited[i][j] = false;
            }
        }
        return hasPath;
    }
}
```