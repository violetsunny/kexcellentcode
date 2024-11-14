## [22. 链表中倒数第 k 个节点](https://leetcode.cn/problems/lian-biao-zhong-dao-shu-di-kge-jie-dian-lcof/)

### 题目描述

输入一个链表，输出该链表中倒数第 k 个结点。

### 解法
快慢指针：
fast 指针走 `k` 步。之后 slow 指针指向 phead，然后两个指针同时走，直至 fast 指针到达尾结点。

> 当用一个指针遍历链表不能解决问题的时候，可以尝试用两个指针来遍历链表。可以让其中一个指针遍历的速度快一些。

此题需要考虑一些特殊情况。比如 k 的值小于 0 或者大于链表长度。

```java
/*
public class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}*/
public class Solution {
    /**
     * 找出链表倒数第k个节点，k从1开始
     * @param head 链表头部
     * @param k 第k个节点
     * @return 倒数第k个节点
     */
    public ListNode FindKthToTail(ListNode head,int k) {
        if (head == null || k < 1) {
            return null;
        }

        ListNode fast = head;
        ListNode slow = head;
        for (int i = 0; i < k; ++i) {
            if (fast != null) {
                fast = fast.next;
            } else {
                return null;
            }
        }

        
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }
}
```