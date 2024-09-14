/**
 * llkang.com Inc.
 * Copyright (c) 2010-2022 All Rights Reserved.
 */
package top.kexcellent.back.code.biztechnique;

/**
 * 斐波拉切数列和青蛙跳台阶
 *
 * n>3:f(n)=f(n-1)+f(n-2)  n=1或n=2时 f(n)=1   1 1 2 3 5 8 13 。。。
 * @author kanglele
 * @version $Id: FiboacciSequence, v 0.1 2022/6/9 11:00 kanglele Exp $
 */
public class FiboacciSequence {

    public static int fib_1(int n) {
        if(n < 1) {
            return 0;
        }else if(n <= 2) {
            return 1;
        }

        return fib_1(n-1) + fib_1(n-2);
    }

    /**
     * 自上而下优化
     * @param n
     * @param cache
     * @return
     */
    public static int fib_2(int n,int[] cache) {
        if(n < 1) {
            return 0;
        }
        if(cache[n] != 0){
            return cache[n];
        }
        if(n <= 2) {
            cache[n] = 1;
        } else {
            cache[n] = fib_2(n-1,cache) + fib_2(n-2,cache);
        }
        return cache[n];
    }

    /**
     * 自下而上优化
     * @param n
     * @return
     */
    public static int fib_3(int n) {
        if(n < 1) {
            return 0;
        }
        int[] memory = new int[n+1];
        for(int i=0;i<=n;i++){
            if(i<=2){
                memory[i]=1;
            }else{
                memory[i]=memory[i-1]+memory[i-2];
            }
        }
        return memory[n];
    }

    /**
     * 变量赋值优化
     * @param n
     * @return
     */
    public static int fib_4(int n) {
        if(n < 1) {
            return 0;
        }
        int memory = 1;
        int memory_1 = 0;
        int memory_2 = 1;
        for(int i=2;i<=n;i++){
            memory=memory_1+memory_2;
            memory_1=memory_2; //前一个数
            memory_2=memory; //现在的数
        }
        return memory;
    }
}
