/**
 * LY.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 前序遍历、中序遍历、后序遍历和层序遍历  广度优先搜索（BFS） 深度优先搜索（DFS）
 * @author kanglele
 * @version $Id: BinaryTreeTraversal, v 0.1 2024/10/16 下午4:15 kanglele Exp $
 */
public class BinaryTreeTraversal {

    //前序遍历
    public void preOrderTraversal(TreeNode root) {
        if (root == null) return;
        System.out.print(root.val + " ");
        preOrderTraversal(root.left);
        preOrderTraversal(root.right);
    }
    //中序遍历
    public void inOrderTraversal(TreeNode root) {
        if (root == null) return;
        inOrderTraversal(root.left);
        System.out.print(root.val + " ");
        inOrderTraversal(root.right);
    }
    //后序遍历
    public void postOrderTraversal(TreeNode root) {
        if (root == null) return;
        postOrderTraversal(root.left);
        postOrderTraversal(root.right);
        System.out.print(root.val + " ");
    }
    //层序遍历
    public void levelOrderTraversal(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            System.out.print(current.val + " ");
            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }
    }

    //广度优先搜索（BFS）,时间复杂度：O(n),空间复杂度：O(1)-O(w)，其中 w 是树的最大宽度
    public void breadthFirstSearch(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            System.out.print(current.val + " ");
            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }
    }

    //深度优先搜索（DFS）,时间复杂度：O(n),空间复杂度：O(h)，其中 h 是树的高度
    public void depthFirstSearch(TreeNode root) {
        if (root == null) return;
        System.out.print(root.val + " ");
        depthFirstSearch(root.left);
        depthFirstSearch(root.right);
    }
    //时间复杂度：O(n),空间复杂度：O(w)，其中 w 是树的最大宽度
    public void depthFirstSearchIterative(TreeNode root) {
        if (root == null) return;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            System.out.print(current.val + " ");
            if (current.right != null) stack.push(current.right);
            if (current.left != null) stack.push(current.left);
        }
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
}
