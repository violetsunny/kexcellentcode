## 8 二叉树的下一个节点

### 题目描述

给定一棵二叉树的其中一个节点，请找出中序遍历序列的下一个节点。

**注意：**

- 如果给定的节点是中序遍历序列的最后一个，则返回空节点;
- 二叉树一定不为空，且给定的节点一定不是空节点。

**样例**

```
假定二叉树是：[2, 1, 3, null, null, null, null]， 给出的是值等于 2 的节点。

则应返回值等于 3 的节点。

解释：该二叉树的结构如下，2 的后继节点是 3。
  2
 / \
1   3
```

### 解法

对于结点 `p`：

- 如果它有右子树，则**右子树的最左结点**就是它的下一个结点；
- 如果它没有右子树，判断它与父结点 `p.father` 的位置情况：
    - 如果它是父结点的左孩子，那么父结点 `p.father` 就是它的下一个结点；
    - 如果它是父结点的右孩子，一直向上寻找，直到找到某个结点，它是它父结点的左孩子，那么该父结点就是 `p` 的下一个结点。

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode father;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {

    /**
     * 获取二叉树中序遍历结点的下一个结点
     *
     * @param p 某结点
     * @return p的下一个结点
     */
    public TreeNode inorderSuccessor(TreeNode p) {
        if (p == null) {
            return null;
        }

        TreeNode cur = p.right;

        // 右子树不为空
        if (cur != null) {
            while (cur.left != null) {
                cur = cur.left;
            }
            return cur;
        }

        // 右子树为空
        TreeNode father = p.father;
        while (father != null && father.left != p) {//不为空，且它不是它父节点的左节点就继续找，否则就结束。
            p = father;
            father = p.father;
        }
        return father;
    }
}
```