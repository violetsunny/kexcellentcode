## [07. 重建二叉树](https://leetcode.cn/problems/zhong-jian-er-cha-shu-lcof/)

### 题目描述

输入一棵二叉树前序遍历和中序遍历的结果，请重建该二叉树。

**样例**

```
给定：
前序遍历是：[3, 9, 20, 15, 7]
中序遍历是：[9, 3, 15, 20, 7]

返回：[3, 9, 20, null, null, 15, 7, null, null, null, null]
返回的二叉树如下所示：
    3
   / \
  9  20
    /  \
   15   7
```

### 解法

在二叉树的前序遍历序列中，第一个数字总是根结点的值。在中序遍历序列中，根结点的值在序列的中间，左子树的结点位于根结点左侧，而右子树的结点位于根结点值的右侧。

遍历中序序列，找到根结点，递归构建左子树与右子树。

注意添加特殊情况的 `if` 判断。

```java
/**
 * Definition for a binary tree node.
 * class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {

    /**
     * 重建二叉树
     *
     * @param preorder 前序遍历序列
     * @param inorder 中序遍历序列
     * @return 二叉树根结点
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length == 0 || preorder.length != inorder.length) {
            return null;
        }

        return build(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);
    }

    private TreeNode build(int[] preorder, int[] inorder, int s1, int e1, int s2, int e2) {
        int rootVal = preorder[s1];
        TreeNode root = new TreeNode(rootVal);
        if (s1 == e1) {
            return root;
        }

        int i = s2, cnt = 0;
        for (; i <= e2; ++i) {
            if (inorder[i] == rootVal) {//找到根节点在中序遍历的位置i
                break;
            }
            ++cnt;//左的节点数
        }

        root.left = cnt > 0 ? build(preorder, inorder, s1 + 1, s1 + cnt, s2, i - 1) : null;//s1+1--s1+cnt是根的右到左子树节点数就是前序的左边，s2--i-1是s2到i根左侧是中序的左边
        root.right = i < e2 ? build(preorder, inorder, s1 + cnt + 1, e1, i + 1, e2) : null;//同理前序的右边，中序的右边
        return root;
    }
}
```