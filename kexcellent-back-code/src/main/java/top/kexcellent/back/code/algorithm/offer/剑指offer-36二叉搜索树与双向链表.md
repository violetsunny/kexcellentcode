## [36. 二叉搜索树与双向链表](https://leetcode.cn/problems/er-cha-sou-suo-shu-yu-shuang-xiang-lian-biao-lcof/)


### 题目描述

输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。要求不能创建任何新的结点，只能调整树中结点指针的指向。

### 解法

由于是二叉搜索树，因此中序遍历的结果就是排序的。

中序遍历利用栈来实现。遍历时，前一个结点的 right 指向后一个结点，后一个结点的 left 指向前一个结点。

```java
pre.right = cur;
cur.left = pre;
```

```java
import java.util.Stack;

/**
 public class TreeNode {
 int val = 0;
 TreeNode left = null;
 TreeNode right = null;

 public TreeNode(int val) {
 this.val = val;

 }

 }
 */
public class Solution {
    /**
     * 将二叉搜索树转换为双向链表
     *
     * @param pRootOfTree
     * @return
     */
    public TreeNode Convert(TreeNode pRootOfTree) {
        if (pRootOfTree == null) {
            return null;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = pRootOfTree;
        TreeNode res = null;
        TreeNode pre = null;
        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                cur = stack.pop();
                if (pre == null) {
                    pre = cur;
                    res = pre;
                } else {
                    pre.right = cur;
                    cur.left = pre;
                    pre = cur;
                }
                cur = cur.right;

            }
        }
        return res;
    }
}
```

````java
class Solution {
    
    private Node pre;

    public Node treeToDoublyList(Node root) {
        if (root == null) {
            return null;
        }
        dfs(root);
        root.left = pre;//pre为最后节点，并指向头节点的左
        pre.right = root;
        return head;
    }

    private void dfs(Node cur) {
        if (cur == null) {
            return;
        }
        //左中右
        dfs(cur.left);
        if (pre != null) {
            pre.right = cur;//当前给前一个节点的右
        }
        cur.left = pre;//前一个节点给当前的左
        pre = cur;//当前为下一次的前
        dfs(cur.right);
    }
}
````