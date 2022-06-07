/**
 * LY.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package top.kexcellent.back.code.biztechnique;

import java.util.Arrays;

/**
 * @author kanglele01
 * @version $Id: TournamentSort, v 0.1 2021/3/8 13:44 kanglele01 Exp $
 */
public class TournamentSort {

    public static void main(String[] args) {
        int[] array = {9,3,7,1,5,2,8,10,11,19,4};
        tournamentSort(array);
        System.out.println(Arrays.toString(array));
    }




    //结点类
    private static class Node {
        int data;
        int index;

        Node(int index, int data){
            this.index = index;
            this.data = data;
        }
    }

    /**
     * 锦标赛排序 时间复杂度O(nlogn) 空间复杂度O(n)
     * @param array
     */
    public static void tournamentSort(int[] array) {
        Node[] tree = buildTree(array);
        for(int i=0; i<array.length; i++){
            array[i] = tree[0].data;
            if(i<array.length-1) {
                //当前最小元素所对应的叶子结点置空
                tree[tree[0].index] = null;
                //重新选举最小元素
                updateTree(tree[0].index, tree);
            }
        }
    }

    //排序前为数组构建二叉树，并选举最小值到树的根结点
    public static Node[] buildTree(int[] array) {
        //计算叶子层的结点数
        int leafSize = nearestPowerOfTwo(array.length);
        //计算二叉树的总结点数
        int treeSize = leafSize * 2 - 1;

        Node[] tree = new Node[treeSize];
        //填充叶子结点
        for(int i=0; i<array.length; i++){
            tree[i+leafSize-1] = new Node(i+leafSize-1, array[i]);
        }
        //自下而上填充非叶子结点
        int levelSize = leafSize;
        int lastIndex = treeSize-1;
        while(levelSize > 1){
            for(int i=0; i<levelSize; i+=2){
                //每一个父结点下标，都可以由（孩子下标-1）/2 来获得
                Node right = tree[lastIndex-i];
                Node left = tree[lastIndex-i-1];
                Node parent = left;
                //数据对比，选出需要的值进行往父节点赋值
                if(left != null && right != null) {
                    parent = left.data<right.data?left:right;
                }else if (left == null){
                    parent = right;
                }
                if(parent != null){
                    int parentIndex = (lastIndex-i-1)/2;
                    tree[parentIndex] = new Node(parent.index, parent.data);
                }
            }
            //挪动节点到上一层
            lastIndex -= levelSize;
            //两两对比后胜出的节点数
            levelSize = levelSize/2;
        }
        return tree;
    }

    //获得仅大于number的完全平方数
    public static int nearestPowerOfTwo(int number) {
        int square = 1;
        while(square < number){
            square = square<<1;
        }
        return square;
    }

    //重新选举最小元素
    public static void updateTree(int index, Node[] tree){
        while (index != 0) {
            Node node = tree[index];
            Node sibling = null;
            if((index&1) == 1){
                //index为奇数，该结点是左孩子
                sibling = tree[index+1];
            }else {
                //index为偶数，该结点是右孩子
                sibling = tree[index-1];
            }

            Node parent = node;
            int parentIndex = (index-1)/2;
            if(node != null && sibling != null) {
                parent = node.data<sibling.data?node:sibling;
            }else if (node == null){
                parent = sibling;
            }
            //node 节点可能上一个最小节点，被置为null;
            tree[parentIndex] = parent==null ? null : new Node(parent.index, parent.data);
            index = parentIndex;
        }
    }
}
