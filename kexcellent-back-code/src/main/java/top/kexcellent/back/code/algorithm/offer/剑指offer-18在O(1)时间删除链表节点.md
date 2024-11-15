## [18.1 在 O(1)时间删除链表节点](https://leetcode.cn/problems/shan-chu-lian-biao-de-jie-dian-lcof/)

### 题目描述

给定单向链表的一个节点指针，定义一个函数在 `O(1)` 时间删除该节点。

假设链表一定存在，并且该节点一定不是尾节点。

**样例**

```
输入：链表 1->4->6->8
      删掉节点：第2个节点即6（头节点为第0个节点）

输出：新链表 1->4->8
```

### 解法

判断要删除的节点是否是尾节点：

- 若是，那么需要遍历链表，找到节点的前一个节点，让前一个节点指向 `null`，时间复杂度为 `O(n)`；
- 若不是，把下一个节点的值赋给该节点，该节点指向下下个节点，时间复杂度为 `O(1)`。

题目中说明了节点不是尾节点，那么符合第二种情况。

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

    /**
     * 删除链表的节点
     *
     * @param val 要删除的节点
     */
    public void deleteNode(ListNode head,int val) {
        if(head == null){
            return null;
        }
        if(head.val==val){
            return head.next;
        }
        ListNode temp = head.next;
        ListNode pre = head;
        while(temp!=null){
            if(temp.val == val){
                pre.next = temp.next;
                return head;
            }
            temp = temp.next;
            pre = pre.next;
        }
        
        return head;
    }
}
```

````java
class Solution {
    /**
     * 删除链表的节点
     *
     * @param node 要删除的节点
     */
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }
}
````

## 18.2 删除链表中重复的节点

来源：[AcWing](https://www.acwing.com/problem/content/27/)

### 题目描述

在一个排序的链表中，存在重复的结点，请删除该链表中重复的结点，重复的结点不保留。

**样例 1**

```
输入：1->2->3->3->4->4->5

输出：1->2->5
```

**样例 2**

```
输入：1->1->1->2->3

输出：2->3
```

### 解法

#### 解法一：递归

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

    /**
     * 删除链表重复的节点
     *
     * @param head 链表头节点
     * @return 删除重复节点后的链表
     */
    public ListNode deleteDuplication(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        if (head.next.val == head.val) {
            if (head.next.next == null) {
                return null;
            }
            if (head.next.next.val == head.val) {
                return deleteDuplication(head.next);
            }
            return deleteDuplication(head.next.next);
        }
        head.next = deleteDuplication(head.next);
        return head;
    }
}
```

#### 解法二：非递归

pre 始终指向下一个不重复的节点。

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

    /**
     * 删除链表重复的节点
     *
     * @param head 链表头节点
     * @return 删除重复节点后的链表
     */
    public ListNode deleteDuplication(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode pre = null, cur = head;
        while (cur != null) {
            if (cur.next != null && cur.next.val == cur.val) {
                int val = cur.val;
                while (cur.next != null && cur.next.val == val) {
                    cur = cur.next;
                }
                if (pre == null) {
                    head = cur.next;
                } else {
                    pre.next = cur.next;
                }
            } else {
                pre = cur;
            }
            cur = cur.next;
        }
        return head;
    }
}
```