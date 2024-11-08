## [26. 树的子结构](https://leetcode.cn/problems/shu-de-zi-jie-gou-lcof/)


### 题目描述

输入两棵二叉树 A、B，判断 B 是不是 A 的子结构。

我们规定空树不是任何树的子结构。

**样例**

树 A：

```
     8
    / \
   8   7
  / \
 9   2
    / \
   4   7
```

树 B：

```
   8
  / \
 9   2
```

返回 true ,因为 B 是 A 的子结构。

### 解法

递归方式遍历：

- 在树 A 中找到和树 B 的根结点值一样的结点 R；
- 判断树 A 以 R 为根结点的子树是否包含与树 B 一样的结构。

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public boolean hasSubtree(TreeNode pRoot1, TreeNode pRoot2) {
        boolean res = false;
        if (pRoot1 != null && pRoot2 != null) {
            if (pRoot1.val == pRoot2.val) {
                res = isSame(pRoot1, pRoot2);
            } else {
                //不相等就继续看左右子树是否有
                res = hasSubtree(pRoot1.left, pRoot2) || hasSubtree(pRoot1.right, pRoot2);
            }
        }
        return res;

    }

    private boolean isSame(TreeNode root1, TreeNode root2) {
        if (root2 == null) {
            return true;
        }
        if (root1 == null || root1.val != root2.val) {
            return false;
        }
        return isSame(root1.left, root2.left) && isSame(root1.right, root2.right);
    }
}
```