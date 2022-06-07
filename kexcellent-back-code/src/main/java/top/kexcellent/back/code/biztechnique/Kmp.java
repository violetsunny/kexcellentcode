/**
 * LY.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package top.kexcellent.back.code.biztechnique;

/**
 * @author kanglele01
 * @version $Id: Kmp, v 0.1 2020/5/15 15:12 kanglele01 Exp $
 */
public class Kmp {

    //初始化next数组
    static void GetNext(String p,int next[]){
        int p_len = p.length();
        int i = 0;
        int j = -1;
        next[0] = -1;
        char[] chars = p.toCharArray();

        while (i < p_len) {
            if(j == -1 || chars[i] == chars[j]){
                i++;
                j++;
                //优化： 如果返回位置的两个字母是一样的，没有必要再比较
                if(chars[i] != chars[j]){
                    next[i] = j;
                } else {
                    next[i] = next[j];
                }

            } else {
                j = next[j];
            }
        }
    }

    //进行匹配
    static int kmp(String s,String p,int next[]){
        GetNext(p,next);

        int i = 0;
        int j = 0;
        int s_len = s.length();
        int p_len = p.length();

        char[] chars = s.toCharArray();
        char[] charsP = p.toCharArray();

        while (i < s_len && j < p_len) {
            if(j == -1 || chars[i] == charsP[j]){
                i++;
                j++;
            } else {
                j = next[j];
            }

            if(j == p_len){
                return i-j;
            }
        }

        return -1;
    }


    public static void main(String[] args) {
        int[] next = new int[100];
        String s = "bbc adafafda dafdsfa ddds ddas";
        String p = "fa d";
        int sub = kmp(s,p,next);
        System.out.println(sub);
    }

}
