/**
 * LY.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package top.kexcellent.back.code.model;

/**
 * @author kanglele01
 * @version $Id: ListNode, v 0.1 2021/3/8 16:02 kanglele01 Exp $
 */
public class ListNode {
    private int value;

    private ListNode next;

    public ListNode(){}

    public ListNode(int value){
        super();
        this.value=value;
    }

    public int getValue() {
        return this.value;
    }

    public ListNode setValue(int value) {
        this.value = value;
        return this;
    }

    public ListNode getNext() {
        return this.next;
    }

    public ListNode setNext(ListNode next) {
        this.next = next;
        return this;
    }
}
