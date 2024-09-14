/**
 * LY.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package top.kexcellent.back.code.biztechnique;

import top.kexcellent.back.code.model.ListNode;

import java.util.HashSet;
import java.util.Set;

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

    public static int lengthOfLongestSubstring(String s) {
        int p_len = s.length();
        if(p_len==0){
            return 0;
        }
        if(p_len==1){
            return 1;
        }
        int i = 0;
        int j = 1;
        int x = 1;
        int y = 0;
        char[] chars = s.toCharArray();
        Set<Character> set = new HashSet<>();

        while (i < p_len && j < p_len) {
            if(!set.contains(chars[j])){
                set.add(chars[j]);
                j++;
                x++;
            } else {
                set.clear();
                x = 1;
                i++;
                j=i+1;
            }
            y = Math.max(x,y);
        }
        return y;
    }

    public static int lengthOfLongestSubstring_1(String s){
        int n = s.length();
        if(n==0){
            return 0;
        }
        if(n==1){
            return 1;
        }
        // 初始化128个字符的值为-1
        int[] last = new int[128];
        for(int i = 0; i < 128; i++) {
            last[i] = -1;
        }

        int res = 0;
        int start = 0; // 窗口开始位置
        for(int i = 0; i < n; i++) {
            //转成字符的ASCII码
            int index = s.charAt(i);
            //由于初始值为-1，只有遇到相同的字符才会移动窗口位置
            start = Math.max(start, last[index] + 1);
            //i-start + 1可以得到当前不同字符串的长度
            res   = Math.max(res, i - start + 1);
            //记录字符上一次出现的位置
            last[index] = i;
        }

        return res;
    }



    public static void main(String[] args) {
        int res = lengthOfLongestSubstring("abcdabceabcaea");
        System.out.println(res);

    }


}
