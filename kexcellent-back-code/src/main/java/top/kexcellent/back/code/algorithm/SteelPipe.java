/**
 * llkang.com Inc.
 * Copyright (c) 2010-2022 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm;

/**
 * 钢管切割
 *
 * @author kanglele
 * @version $Id: SteelPipe, v 0.1 2022/6/9 10:59 kanglele Exp $
 */
public class SteelPipe {

    public static int cut(int[] price,int size){
        if(size == 0){
            return 0;
        }
        int q = Integer.MIN_VALUE;
        for(int i=1;i<=size;i++){
            q=Math.max(q,price[i-1]+cut(price,size-i));//递归拿到所有的可能，通过max比较最大的价值
        }
        return q;
    }

}
