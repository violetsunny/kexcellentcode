/**
 * OYO.com Inc.
 * Copyright (c) 2017-2024 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm;

/**
 * 并查集
 * @author kanglele
 * @version $Id: UnionFind, v 0.1 2024/11/6 23:48 user Exp $
 */
public class UnionFind {
    // 存储每个元素的父节点
    private int[] parent;
    // 存储每个树的大小
    private int[] rank;

    // 构造函数，初始化并查集
    public UnionFind(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i; // 初始时，每个元素的父节点是它自己
            rank[i] = 1;   // 初始时，每个树的大小为1
        }
    }

    // 查找元素的根节点，并进行路径压缩
    private int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // 路径压缩
        }
        return parent[x];
    }

    // 合并两个元素所在的集合
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX != rootY) {
            // 按秩合并
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
                rank[rootY] += rank[rootX];
            } else {
                parent[rootY] = rootX;
                rank[rootX] += rank[rootY];
            }
        }
    }

    // 判断两个元素是否在同一个集合中
    public boolean isConnected(int x, int y) {
        return find(x) == find(y);
    }
}
