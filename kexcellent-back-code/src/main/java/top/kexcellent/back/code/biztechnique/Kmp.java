/**
 * LY.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package top.kexcellent.back.code.biztechnique;

/**
 * KMP算法，对模式串的前后缀进行初始化next数组。然后进行匹配，减少回退指针。
 * next数组是通过模式串的公共前后缀串的长度来计算。如abcdabd，求倒数第二b的next下标则是求abcda的公共前后缀长度，为1；最后一个d的next数组下标则是求abcdab的公共前后缀长度，为2。
 * @author kanglele01
 * @version $Id: Kmp, v 0.1 2020/5/15 15:12 kanglele01 Exp $
 */
public class Kmp {

    //初始化next数组
    static void getNext(String p, int next[]){
        int p_len = p.length()-1;
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
        getNext(p,next);

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
       String s = "bbc adafafda dafdsfa ddds ddas";
        //String s = "bbbbbb";
        int[] next = new int[s.length()];
        String p = "fa d";
        int sub = kmp(s,p,next);
        System.out.println(sub);
//        getNext(s,next);
//        System.out.print(""+next[0]+next[1]+next[2]+next[3]+next[4]);

    }

}
