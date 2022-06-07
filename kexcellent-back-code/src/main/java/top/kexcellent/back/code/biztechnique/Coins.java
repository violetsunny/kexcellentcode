/**
 * LY.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package top.kexcellent.back.code.biztechnique;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

/**
 * @author kanglele01
 * @version $Id: Coins, v 0.1 2020/5/27 9:38 kanglele01 Exp $
 */
public class Coins {

    public static int coin(int[] coins,int sum){
        if(sum <= 0){
            return 1;
        }
        int p = Integer.MAX_VALUE;
        for(int i=0;i<coins.length;i++){
            p = Math.min(p,coin(coins,sum-coins[i])+1);
        }

        if(p == Integer.MAX_VALUE){
            p = -1;
        }
        return p;
    }

    public static void main(String[] args) {
        int[] coins = {2,5,7};
        //System.out.println(coin(coins,27));
        coinPrint(coins,27);
    }


    private static List<Integer> array = Lists.newArrayList();
    private static int[] arrays = new int[20];
    private static int count = 0;
    private static int min = Integer.MAX_VALUE;
    public static void coinPrint(int[] coins,int sum){
        if(sum < 0){
            arrays[count-1]=0;
            return;
        }
        if(sum == 0){
            min = Math.min(min,count);
            System.out.println(Arrays.toString(arrays)+"----"+count+"---"+min);
            return;
        }
        for(int i=0;i<coins.length;i++){
            arrays[count++] = coins[i];
            coinPrint(coins,sum-coins[i]);
            count--;
        }

    }
}
