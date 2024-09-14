/**
 * llkang.com Inc.
 * Copyright (c) 2010-2022 All Rights Reserved.
 */
package top.kexcellent.back.code.biztechnique;

import java.util.Arrays;

/**
 * 归并排序
 *
 * @author kanglele
 * @version $Id: Merge, v 0.1 2022/6/9 10:50 kanglele Exp $
 */
public class Merge {

    public int[] sort(int[] arr){
        if(arr.length<2){
            return arr;
        }
        int mid = arr.length/2;
        int[] left = Arrays.copyOfRange(arr,0,mid);
        int[] right = Arrays.copyOfRange(arr,mid,arr.length);
        return merge(sort(left),sort(right));//先分解在合并  sort就是分解  merge就是合并
    }

    private int[] merge(int[] left,int[] right){ //合并
        int[] temp = new int[left.length+right.length];
        int i = 0;//左序列指针
        int j = 0;//右序列指针

        for(int t=0;t<temp.length;t++){
            if(i>=left.length){
                temp[t] = right[j++];
            }else if(i>=right.length){
                temp[t] = left[i++];
            }else
            if(left[i]>right[j]){
                temp[t]=right[j++];
            }else{
                temp[t]=left[i++];
            }
        }
        return temp;
    }
}
