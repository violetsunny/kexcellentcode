## [35. 复杂链表的复制](https://leetcode.cn/problems/fu-za-lian-biao-de-fu-zhi-lcof/)


### 题目描述

输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，另一个特殊指针指向任意一个节点），返回结果为复制后复杂链表的 `head`。（注意，输出结果中请不要返回参数中的节点引用，否则判题程序会直接返回空）

![random-list](../images/random-list.png)

### 解法

- 第一步，在每个节点的后面插入复制的节点；
  ![random-list-step1.png](../images/random-list-step1.png)

- 第二步，对复制节点的 random 链接进行赋值；
  ![random-list-step2.png](../images/random-list-step2.png)

- 第三步，分离两个链表。
  ![random-list-step3.png](../images/random-list-step3.png)

```java
/*
public class RandomListNode {
    int label;
    RandomListNode next = null;
    RandomListNode random = null;

    RandomListNode(int label) {
        this.label = label;
    }
}
*/
public class Solution {
    /**
     * 复杂链表的复制
     * @param pHead 链表头结点
     * @return 复制的链表
     */
    public RandomListNode Clone(RandomListNode pHead) {
        if (pHead == null) {
            return null;
        }
        RandomListNode cur = pHead;
        while (cur != null) {
            RandomListNode node = new RandomListNode(cur.label);
            node.next = cur.next;
            cur.next = node;
            cur = node.next;
        }

        cur = pHead;
        while (cur != null) {
            RandomListNode clone = cur.next;
            if (cur.random != null) {
                clone.random = cur.random.next;
            }
            cur = clone.next;
        }

        cur = pHead;
        RandomListNode cloneHead = pHead.next;
        while (cur.next != null) {
            RandomListNode clone = cur.next;
            if(clone!=null){
              cur.next = clone.next;//从克隆节点下一节点给原来节点，原来节点下一节点给克隆节点，依次往后拆开。
            }
            cur = clone;
        }
        return cloneHead;
    }
}
```