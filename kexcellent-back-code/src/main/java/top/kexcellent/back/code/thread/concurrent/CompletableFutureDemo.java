/**
 * LY.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package top.kexcellent.back.code.thread.concurrent;

import com.google.common.collect.Lists;
import top.kdla.framework.common.help.DateHelp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author kll49556
 * @version $Id: CompletableFutureDemo, v 0.1 2018/8/5 17:00 kll49556 Exp $
 *
 * @Description:多线程并发任务,取结果归集
 *
 * 方式一：循环创建CompletableFuture list,调用sequence()组装返回一个有返回值的CompletableFuture，返回结果get()获取
 *
 * 方式二：全流式处理转换成CompletableFuture[]+allOf组装成一个无返回值CompletableFuture，join等待执行完毕。返回结果whenComplete获取。---》推荐
 *
 * 建议：CompletableFuture满足并发执行，顺序完成先手顺序获取的目标。而且支持每个任务的异常返回，配合流式编程，用起来速度飞起。JDK源生支持，API丰富，推荐使用。
 */
public class CompletableFutureDemo {
    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        //结果集
        List<String> list = new ArrayList<String>();
        List<String> list2 = new ArrayList<String>();
        //final CountDownLatch cdl = new CountDownLatch(10);
        //定长10线程池
        ExecutorService exs = Executors.newFixedThreadPool(10);
        List<CompletableFuture<String>> futureList = new ArrayList<>();
        List<Integer> taskList = Lists.newArrayList(2, 1, 3, 4, 5, 6, 7, 8, 9, 10);
        try {
            //方式一：循环创建CompletableFuture list,调用sequence()组装返回一个有返回值的CompletableFuture，返回结果get()获取
            //处理方法不同，可以创建多个CompletableFuture 来处理
            /*for(int i=0;i<taskList.size();i++){
                final int j=i+1;
                CompletableFuture<String> future = CompletableFuture.supplyAsync(()->calc(j), exs)//异步执行
                        .thenApply(e->Integer.toString(e))//Integer转换字符串    thenAccept只接受不返回不影响结果
                        .whenComplete((v, e) -> {//如需获取任务完成先手顺序，此处代码即可
                            System.out.println("任务"+v+"完成!result="+v+"，异常 e="+e+","+DateFormat.zhDate(new Date()));
                            list2.add(v);
                        })
                        ;
                futureList.add(future);
            }
            //流式获取结果
            list = sequence(futureList).get();//[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]此处不理解为什么是这样的顺序？谁知道求告知*/

            //方式二：全流式处理转换成CompletableFuture[]+组装成一个无返回值CompletableFuture，join等待执行完毕。返回结果whenComplete获取
            //这是处理都相同可以用
            CompletableFuture[] cfs = taskList.stream().map(object -> CompletableFuture.supplyAsync(() -> calc(object), exs)
                    .thenApply(i -> Integer.toString(i))//thenApply可以对运行结果进行转化，可用于打印上一阶运行结果
                    .whenComplete((v, e) -> {//如需获取任务完成先后顺序，此处代码即可
                        System.out.println("任务" + v + "完成!result=" + v + "，异常 e=" + e + "," + DateHelp.formatTime(new Date()));
                        list2.add(v);//收集返回值
                    }))
                    .toArray(CompletableFuture[]::new);
            CompletableFuture.allOf(cfs).join();

            System.out.println("list2=" + list2 + "list=" + list + ",耗时=" + (System.currentTimeMillis() - start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exs.shutdown();
        }
    }

    public static Integer calc(Integer i) {
        try {
            if (i == 1) {
                Thread.sleep(3000);//任务1耗时3秒
            } else if (i == 5) {
                Thread.sleep(5000);//任务5耗时5秒
            } else {
                Thread.sleep(1000);//其它任务耗时1秒
            }
            System.out.println("task线程：" + Thread.currentThread().getName() + "任务i=" + i + ",完成！+" + DateHelp.formatTime(new Date()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * @param futures List
     * @return
     * @Description 组合多个CompletableFuture为一个CompletableFuture, 所有子任务全部完成，组合后的任务才会完成。带返回值，可直接get.
     *
     * @since JDK1.8
     */
    public static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
        //1.构造一个空CompletableFuture，子任务数为入参任务list size
        CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        //2.流式（每个子任务join操作后转换为list）往空CompletableFuture中添加结果
        return allDoneFuture.thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.<T>toList()));
    }

    /**
     * @param futures Stream
     * @return
     * @Description Stream流式类型futures转换成一个CompletableFuture, 所有子任务全部完成，组合后的任务才会完成。带返回值，可直接get.
     *
     * @since JDK1.8
     */
    public static <T> CompletableFuture<List<T>> sequence(Stream<CompletableFuture<T>> futures) {
        List<CompletableFuture<T>> futureList = futures.filter(f -> f != null).collect(Collectors.toList());
        return sequence(futureList);
    }
}
