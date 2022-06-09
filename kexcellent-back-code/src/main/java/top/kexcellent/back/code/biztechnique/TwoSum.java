/**
 * llkang.com Inc.
 * Copyright (c) 2010-2022 All Rights Reserved.
 */
package top.kexcellent.back.code.biztechnique;

import java.util.HashMap;
import java.util.Map;

/**
 * 两个数字
 *
 * @author kanglele
 * @version $Id: TwoSum, v 0.1 2022/6/9 11:04 kanglele Exp $
 */
public class TwoSum {

    public int[] twoSum(int[] nums,int target){
        Map<Integer,Integer> map = new HashMap<>();
        for(int i = 0;i<nums.length;i++){
            int c = target - nums[i];
            if(map.containsKey(c)){
                return new int[]{map.get(c),i};
            }
            map.put(nums[i],i);
        }

        throw new IllegalArgumentException("无");
    }

}
