/**
 * llkang.com Inc.
 * Copyright (c) 2010-2022 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm;

import top.kexcellent.back.code.model.ListNode;

/**
 * 单链表反转
 *
 * @author kanglele
 * @version $Id: ListReverse, v 0.1 2022/6/9 10:53 kanglele Exp $
 */
public class ListReverse {
    public ListNode reverseNode1(ListNode head){
        ListNode newList = null;
        ListNode curr = head;
        while(curr != null){
            ListNode temp = curr.getNext();
            curr.setNext(newList);//当前值指向新的链表
            newList = curr;
            curr = temp;//给当前链表，下次继续执行
        }
        return newList;
    }


    public ListNode reverseNode2(ListNode head){
        if(head==null || head.getNext() == null) return head;

        ListNode newNode = reverseNode2(head.getNext());
        head.getNext().setNext(head);
        head.setNext(null);
        return newNode;
    }
}
