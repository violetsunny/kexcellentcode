/**
 * LY.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm;

/**
 * 树状数组，也称作“二叉索引树”（Binary Indexed Tree）或 Fenwick 树。 它可以高效地实现如下两个操作：
 * 1.单点更新 update(x, delta)： 把序列 x 位置的数加上一个值 delta；
 * 2.前缀和查询 query(x)：查询序列 [1,...x] 区间的区间和，即位置 x 的前缀和。
 * 这两个操作的时间复杂度均为 O(log n)。
 *
 * 树状数组最基本的功能就是求比某点 x 小的点的个数（这里的比较是抽象的概念，可以是数的大小、坐标的大小、质量的大小等等）。
 * @author kanglele
 * @version $Id: BinaryIndexedTree, v 0.1 2024/10/22 10:51 kanglele Exp $
 */
public class BinaryIndexedTree {
    private int n;
    private int[] c;

    public BinaryIndexedTree(int n) {
        this.n = n;
        c = new int[n + 1];
    }

    public void update(int x, int delta) {
        while (x <= n) {
            c[x] += delta;
            x += x & -x;
        }
    }

    public int query(int x) {
        int s = 0;
        while (x > 0) {
            s += c[x];
            x -= x & -x;
        }
        return s;
    }
}
