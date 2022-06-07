/**
 * LY.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package top.kexcellent.back.code.biztechnique;

/**
 * @author kanglele01
 * @version $Id: PalindRome, v 0.1 2021/3/10 15:01 kanglele01 Exp $
 */
public class Palindrome {

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


    public static void main(String[] args) {
        String res = palindrome_1("aacxycabaabb");
        System.out.println(res);
    }
}
