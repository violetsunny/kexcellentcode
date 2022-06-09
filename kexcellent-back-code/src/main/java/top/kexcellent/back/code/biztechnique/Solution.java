/**
 * LY.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package top.kexcellent.back.code.biztechnique;

import top.kexcellent.back.code.model.ListNode;

/**
 * @author kanglele01
 * @version $Id: PalindRome, v 0.1 2021/3/10 15:01 kanglele01 Exp $
 */
public class Solution {

    /**
     * 找出回文字符
     * @param s
     * @return
     */
    public static String palindrome_1(String s){
        char[] chars = s.toCharArray();
        String res="";
        for(int i=0;i<s.length()-1;i++){
            int a=i;
            int b=i+1;
            boolean flagSuccess = true;
            int l=a;
            int r=b;
            while (b<s.length()&&r>=l) {
                if(chars[l++]!=chars[r--]){
                    b++;
                    l=a;
                    r=b;
                    if(b==s.length()){
                        flagSuccess = false;
                    }
                    //break;
                }
            }
            String res_1=flagSuccess?s.substring(a,b+1):"";
            res = res.length()>res_1.length()?res:res_1;
        }
        return res;
    }

    /**
     * 判断是否回文字符
     * @param x
     * @return
     */
    public static Boolean palindrome_2(int x){
        String s = String.valueOf(x);
        char[] chars = s.toCharArray();
        boolean res= true;
        for(int i=0;i<(s.length()-1)/2;i++){
            if (chars[i] != chars[s.length() - 1 - i]) {
                res = false;
                break;
            }
        }
        return res;
    }

    /**
     * 判断是否回文字符
     * @param x
     * @return
     */
    public static Boolean palindrome_3(int x){
        if(x >= 0 && x < 10){
            return true;
        }
        if(x < 0 || (x%10 == 0)) {
            return false;
        }

        int res = 0;
        while (x > res) {
            res = res * 10 + x % 10;
            x /= 10;
        }
        return x == res || x == res / 10;
    }

    /**
     * 罗马字符转数字
     * @param s
     * @return
     */
    public int romanToInt(String s) {
        s = s.replace("IV","a");
        s = s.replace("IX","b");
        s = s.replace("XL","c");
        s = s.replace("XC","d");
        s = s.replace("CD","e");
        s = s.replace("CM","f");

        int result = 0;
        for (int i=0; i<s.length(); i++) {
            result += which(s.charAt(i));
        }
        return result;
    }

    public int which(char ch) {
        switch(ch) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            case 'a': return 4;
            case 'b': return 9;
            case 'c': return 40;
            case 'd': return 90;
            case 'e': return 400;
            case 'f': return 900;
        }
        return 0;
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

    public static void main(String[] args) {
        String res = palindrome_1("aacxycabaabb");
        System.out.println(res);
    }


}
