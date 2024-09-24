/**
 * LY.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm;

/**
 * arr[start]比arr[end] 消耗少
 * @author kanglele
 * @version $Id: a, v 0.1 2024/9/23 下午3:54 kanglele Exp $
 */
public class RotateArraySearch {
    public static int search(int[] arr, int target) {
        if(arr == null || arr.length == 0){
            return -1;
        }
        if(arr[0] == target){
            return 0;
        }
        int start = 0;
        int end = arr.length-1;
        while(end >= start){
            int mid = start + ((end - start)>>1);//向下取整，中间或偏左
            if(arr[mid]==target){
                while(mid>0&&arr[mid-1]==arr[mid]){//因为有序，如果有肯定相邻相等
                    mid--;
                }
                return mid;
            }
            if(arr[mid] > arr[start]){ //mid在旋转点左，[start,mid]有序
                if(arr[mid]>target&&target>=arr[start]){ //target在[start,mid)
                    end = mid-1;
                } else {
                    start = mid+1;
                }
            } else if(arr[mid] < arr[start]){ //mid在旋转点右,[mid,end]有序
                if(arr[mid]<target&&target<=arr[end]){ //target在(mid,end]
                    start = mid+1;
                } else {
                    end = mid-1;
                }
            } else {
                start++;//相等就往后挪动
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {1,-2};
        System.out.println(search(arr, -2));
    }
}
