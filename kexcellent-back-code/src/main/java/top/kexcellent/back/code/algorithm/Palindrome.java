/**
 * LY.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 回文
 * @author kanglele
 * @version $Id: T2, v 0.1 2024/11/4 14:04 kanglele Exp $
 */
public class Palindrome {
    public static boolean isPalindrome(String s) {
        if(s == null){
            return false;
        }
        int i = 0;
        int j = s.length() -1;
        char[] array = s.toCharArray();
        while(j>=0 && i <= s.length()-1){
            if(isnumstring(array[i]) && isnumstring(array[j])){
                if(array[i]==array[j]){
                    i++;
                    j--;
                } else if(isstring(array[i]) && isstring(array[j]) && ((array[i]+32)==array[j] || array[i]==(array[j]+32))){
                    i++;
                    j--;
                } else {
                    break;
                }
            } else if(isnumstring(array[i])){
                j--;
            } else if(isnumstring(array[j])){
                i++;
            } else {
                i++;
                j--;
            }
        }
        if(j==-1 || i == s.length()){
            return true;
        }
        return false;
    }

    private static boolean isnumstring(char s){
        if((s>='0' && s<='9') || isstring(s)){
            return true;
        }
        return false;
    }

    private static boolean isstring(char s){
        if((s>='a' && s<='z') || (s>='A' && s<='Z')){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        String test = "aba";
        System.out.println(isPalindrome(test,0,2));

    }

    private List<List<String>> reslist = new ArrayList<>();

    public List<List<String>> partition(String s) {
        palindrome(s,0,new ArrayList<>());
        return reslist;
    }

    private void palindrome(String s,int index,List<String> res){
        if(index == s.length()){
            reslist.add(new ArrayList<>(res));
            return;
        }
        for(int i=index;i<s.length();i++){
            if(isPalindrome(s,index,i)){
                res.add(s.substring(index,i+1));
                palindrome(s,i+1,res);
                res.remove(res.size()-1);
            }
        }

    }

    private static boolean isPalindrome(String s ,int left, int right){
        while (left <= right) {
            if (s.charAt(left++) != s.charAt(right--)) {
                return false;
            }
        }
        return true;
    }


}
