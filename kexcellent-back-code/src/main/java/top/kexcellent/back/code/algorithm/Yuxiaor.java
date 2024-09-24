/**
 * LY.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * 在一个数组中找另一个数组的匹配到的初始位置
 *
 * 如果不要求连续的初始位置
 *
 * @author kanglele01
 * @version $Id: Yuxiaor, v 0.1 2020/6/2 17:23 kanglele01 Exp $
 */
public class Yuxiaor {

    /**
     * kmp O(n+m)
     * @param dist 目标数组
     * @param search 模式数组
     * @return
     */
    public static int indexOf(int[] dist,int[] search){
        if(dist==null || search == null || dist.length==0 || search.length ==0 || search.length > dist.length){
            return -1;
        }

        //kmp算法初始化next数组
        int[] next = new int[search.length];
        getNext(search,next);

        int i = 0;
        int j = 0;
        int distSize = dist.length;
        int searchSize = search.length;

        while (i < distSize && j < searchSize) {
            if(j == -1 || dist[i] == search[j]){
                i++;
                j++;
            } else {
                j = next[j];
            }
        }

        if(j == searchSize){
            return i-j;
        }

        return -1;
    }

    /**
     * 获取next数组
     * @param search 模式数组
     * @param next next数组
     */
    private static void getNext(int[] search , int[] next){
        int size = search.length-1;
        int i = 0;
        int j = -1;
        next[0] = -1;
        while (i < size) {
            //初始值和相同时加1
            if(j==-1 || search[i] == search[j]){
                i++;j++;
                if(search[i] != search[j]){
                    next[i] =j;
                } else {
                    next[i] = next[j];//相同就不用再比较
                }

            } else {
                j = next[j];
            }
        }
    }

//    public static void main(String[] args) {
//        int[] dist = {9,3,5,4,5,4,6,1,5,5,4,6,5,5,4,2,1};
//        int[] search = {5,4,6,5,5,4};
//        System.out.println(indexOf(dist,search));
//        System.out.println("---------------");
//        System.out.println(indexOf2(dist,search));
//    }

    public static void main(String[] args) {
        String a="验证码：DCf12ff";
        String regEx="[^DC0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(a);
        System.out.println( m.replaceAll("").trim());

        String[] r = a.split("DC");

        Pattern pattern=Pattern.compile("[DC0-9]{4,}(?![DC0-9])");
        Matcher matcher=pattern.matcher(a);
        if (matcher.find()){
            String code=matcher.group(0);
            System.out.println( code);
        }

    }

    public static final String NUMBER_TEXT = "^([0-9]+)$";

    public static String getNumber(String str){
        StringBuffer number = new StringBuffer("");

        String[] strArray = str.split("");
        for (int i=1; i<strArray.length; i++) {
            if(isNumberText(strArray[i])){
                number.append(strArray[i]);
            }else{
                break;
            }
        }
        return number.toString();
    }

    public static boolean isNumberText(String str){
        if(StringUtils.isEmpty(str)){
            return false;
        }
        return isMatch(NUMBER_TEXT, str);
    }

    public static boolean isMatch(String regex, CharSequence content){
        return Pattern.matches(regex, content);
    }

    /**
     * 朴素算法 O(n+m)
     * @param dist  目标数组
     * @param search  模式数组
     * @return
     */
    public static int indexOf2(int[] dist,int[] search){
        if(dist==null || search == null || dist.length==0 || search.length ==0 || search.length > dist.length){
            return -1;
        }
        //朴素算法
//        for(int i=0;i<dist.length;i++){
//            int k = i;
//            for(int j=0;j<search.length;j++){
//                if(dist[k]!=search[j]){
//                    break;
//                } else {
//                    k++;
//                    if(j == search.length-1){
//                        return i;
//                    }
//                }
//            }
//        }

        int index = -1;
        int j = 0;
        for(int i=0;i<dist.length;i++){

            while (j < search.length){
                if(dist[i]!=search[j]){
                    break;
                }
                if(j==0){//记录初始匹配时的下标
                    index = i;
                }
                j++;
            }

            if(j == search.length){//当全部匹配到才返回
                return index;
            }

        }

        return -1;
    }

}
