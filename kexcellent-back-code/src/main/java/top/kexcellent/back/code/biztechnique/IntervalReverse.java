/**
 * LY.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package top.kexcellent.back.code.biztechnique;

import com.google.common.collect.Lists;
import top.kexcellent.back.code.model.ListNode;

import java.util.List;

/**
 * @author kanglele01
 * @version $Id: IntervalReverse, v 0.1 2021/3/8 15:59 kanglele01 Exp $
 */
public class IntervalReverse {

    static ListNode successor = null; // 后驱节点

    // 反转以 head 为起点的 n 个节点，返回新的头结点
    static ListNode reverseN(ListNode head, int n) {
        if (n == 1) {
            // 记录第 n + 1 个节点
            successor = head.getNext();
            return head;
        }
        // 以 head.next 为起点，需要反转前 n - 1 个节点
        ListNode last = reverseN(head.getNext(), n - 1);
        head.getNext().setNext(head);
        // 让反转之后的 head 节点和后面的节点连起来
        head.setNext(successor);
        return last;
    }

    static ListNode reverseBetween(ListNode head, int m, int n) {
        // base case
        if(m==1){
            return reverseN(head,n);
        }

        // 前进到反转的起点触发 base case
        head.setNext(reverseBetween(head.getNext(),m-1,n-1));
        return head;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode();
        head.setValue(1);
        addListNode(head, 10);


        head = reverseBetween(head,2,8);

        while (head.getNext()!=null){
            System.out.print(head.getValue()+"-");
            head = head.getNext();
        }
        System.out.print(head.getValue());
    }

    static ListNode addListNode(ListNode head,int value) {
        List<ListNode> listNodes = Lists.newArrayList();
        for(int i=2;i<=value;i++){
            ListNode next = new ListNode();
            next.setValue(i);
            listNodes.add(next);
            if(i>2){
                listNodes.get(i-3).setNext(listNodes.get(i-2));
            }
        }

        head.setNext(listNodes.get(0));

        return head;
    }
}
