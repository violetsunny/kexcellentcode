/**
 * llkang.com Inc.
 * Copyright (c) 2010-2022 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm;

import top.kexcellent.back.code.model.ListNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 两个数字
 *
 * @author kanglele
 * @version $Id: TwoSum, v 0.1 2022/6/9 11:04 kanglele Exp $
 */
public class TwoSum {

    public int[] twoSum(int[] nums,int target){
        Map<Integer,Integer> map = new HashMap<>();
        for(int i = 0;i<nums.length;i++){
            int c = target - nums[i];
            if(map.containsKey(c)){
                return new int[]{map.get(c),i};
            }
            map.put(nums[i],i);
        }

        throw new IllegalArgumentException("无");
    }

    /**
     * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
     *
     * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode pre = new ListNode(0);
        ListNode cur = pre;
        int carry = 0;
        while(l1 != null || l2 != null) {
            int x = l1 == null ? 0 : l1.getValue();
            int y = l2 == null ? 0 : l2.getValue();
            int sum = x + y + carry;
            carry = sum / 10;
            sum = sum % 10;
            cur.setNext(new ListNode(sum));
            cur = cur.getNext();
            if(l1 != null)
                l1 = l1.getNext();
            if(l2 != null)
                l2 = l2.getNext();
        }
        if(carry == 1) {
            cur.setNext(new ListNode(carry));
        }
        return pre.getNext();
    }

}
