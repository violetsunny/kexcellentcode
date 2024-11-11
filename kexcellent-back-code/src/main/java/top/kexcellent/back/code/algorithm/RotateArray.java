/**
 * LY.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm;

/**
 * 旋转数组
 * @author kanglele
 * @version $Id: T3, v 0.1 2024/11/11 17:28 kanglele Exp $
 */
public class RotateArray {
    public void rotate(int[] nums, int k) {
        if(nums.length==1||k==0||nums.length==k){
            return;
        }
        if(k>nums.length){
            k = k%nums.length;
        }
        int n = nums.length-k;
        int[] copy = new int[n];
        for(int i=0;i<n;i++){
            copy[i] = nums[i];
        }
        int m = n;
        for(int i=0;i<k;i++){
            int temp = nums[m];
            nums[m] = nums[i];
            nums[i] = temp;
            m++;
        }
        int j = k;
        for(int c:copy){
            nums[j++] = c;
        }

    }

    public static void main(String[] args) {
        RotateArray t = new RotateArray();
        t.rotate(new int[]{1,2,3},2);
    }
}
