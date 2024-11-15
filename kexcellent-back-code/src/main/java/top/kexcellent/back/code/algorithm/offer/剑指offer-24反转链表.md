## [24. 反转链表](https://leetcode.cn/problems/fan-zhuan-lian-biao-lcof/)

### 题目描述

输入一个链表，反转链表后，输出新链表的表头。

### 解法

#### 解法一

利用头插法解决。

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode(-1);
        ListNode p = head;
        ListNode q = head.next;
        while (q != null) {
            p.next = dummy.next;
            dummy.next = p;
            p = q;
            q = p.next;
        }
        p.next = dummy.next;
        return p;
    }
}
```

#### 解法二：递归

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode node = reverseList(head.next);
        head.next.next = head;//就是从后往前依次转
        head.next = null;
        return node;
    }
}
```
#### 解法三：原地排序

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode cur = head;//借助一个指针
        ListNode pre = null;
        while(cur!=null){
            ListNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        return pre;
    }
}
```