/**
 * llkang.com Inc.
 * Copyright (c) 2010-2022 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 8皇后问题
 *
 * @author kanglele
 * @version $Id: Queen8, v 0.1 2022/6/9 11:07 kanglele Exp $
 */
public class Queen8 {
    private List<List<String>> ans = new ArrayList<>();
    int max;
    int[] array;
    int count;

    public List<List<String>> solveNQueens(int n) {
        max = n;
        array = new int[max];
        check(0);
        System.out.println(count);
        return ans;
    }

    public void check(int n) {//从第几行开始
        //终止条件是最后一行已经摆完，由于每摆一步都会校验是否有冲突，所以只要最后一行摆完，说明已经得到了一个正确解
        if (n == max) {
            print();
            return;
        }
        //从第一列开始放值，然后判断是否和本行本列本斜线有冲突，如果OK，就进入下一行的逻辑
        for (int i = 0; i < max; i++) {
            array[n] = i;
            if (judge(n)) {
                check(n + 1);
            }
        }
    }

    private boolean judge(int n) {
        for (int i = 0; i < n; i++) {
            if (array[i] == array[n] || Math.abs(n - i) == Math.abs(array[n] - array[i])) {
                return false;
            }
        }
        return true;
    }

    private void print()  {
        count++;
        List<String> temp = new ArrayList<>();
        for (int k : array) {
//            System.out.print(array[i] + 1 + " ");
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < max; j++) {
                if (k == j) {
                    sb.append("Q");
                } else {
                    sb.append(".");
                }
            }
            temp.add(sb.toString());
        }
        ans.add(temp);
//        System.out.println();
    }

    public static void main(String[] args) {
        Queen8 q = new Queen8();
        List<List<String>> qs = q.solveNQueens(8);
        System.out.println(Arrays.toString(qs.toArray()));
    }
}

 class BaHuangHouWenTi {
    //定义一个max表示共有多少个皇后
    int max = 8;
    //定义数组arry，保存存放的结果
    int[] arr = new int[max];
    static int count = 0;
    public static void main(String[] args) {
        //测试
        BaHuangHouWenTi bhw = new BaHuangHouWenTi();
        bhw.check(0);
        System.out.println("一共有"+count+"种解法");
    }//main()

    //编写一个方法，放置第n个皇后
    private void check(int n){//n表示所在行数，n=0时从 第一行开始
        if (n == max){//n=8,8个皇后已经放好了
            print();
            return;
        }//n == max
        //依次放入皇后并判断是否冲突
        for (int i = 0; i < max; i++){
            //先把当前皇后n，放到该行的第一列（本文创建了一维数组，索引表示行数，值代表列）
            arr[n]=i;//将当前行的皇后放在不同列
            //判断是否冲突
            if(judge(n)){//不冲突
                //接着放
                check(n+1);//递归，逐行判断（索引代表行，即n）
            }
        }//for
    }//check

    //查看当放置第n个皇后，检测是否和前一个已经摆放的皇后冲突
    private boolean judge(int n){//n表示第n个皇后
        for (int i = 0; i < n; i++) {//从第一行比较到当前行
            //表示第n个皇后和前面n减1个在同一列或者对角线从、
            if (arr[i] == arr[n] || Math.abs(n-i) == Math.abs(arr[n]-arr[i])){
                return false;
            }//if
        }//for
        return true;
    }
    //写一个方法,将皇后摆放的位置输出
    private void print(){
        count++;
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println("\n");
    }//print()

}
