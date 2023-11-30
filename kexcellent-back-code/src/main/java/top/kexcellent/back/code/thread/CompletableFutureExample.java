/**
 * llkang.com Inc.
 * Copyright (c) 2010-2022 All Rights Reserved.
 */
package top.kexcellent.back.code.thread;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import top.kdla.framework.common.help.MultiThreadInvokeHelp;
import top.kexcellent.back.code.model.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 多线程协作示例
 *
 * @author kanglele
 * @version $Id: CompletableFutureExample, v 0.1 2022/5/17 18:26 kanglele Exp $
 */
public class CompletableFutureExample {

    private void example1() throws Exception {
        List<Supplier<String>> suppliers = Lists.newArrayList(() -> {
            //.....
            Person person = new Person();
            person.setName("阿康");
            return person.getName();
        },() -> {
            //.....
            Person person = new Person();
            person.setName("阿垚");
            return person.getName();
        });

        List<String> msg = MultiThreadInvokeHelp.invokeGetS(suppliers,ExecutorFactory.getInstance());

    }

    private void example2() {
//        CompletableFuture[] futures = workList.stream()
//                .map(integers -> CompletableFuture.runAsync(()->operateDunCaseOwners(adminUserId,integers),ExecutorServiceUtil.executorService))
//                .toArray(CompletableFuture[]::new);
//        CompletableFuture.allOf(futures).join();
//
//        CompletableFuture.supplyAsync()
//                .thenApplyAsync()
//                .thenApplyAsync();
        //这是一个Promise链
//        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(Tests::source, executor1);
//        CompletableFuture<String> cf2 = cf1.thenApplyAsync(Tests::echo, executor2);
//        CompletableFuture<String> cf3_1 = cf2.thenApplyAsync(Tests::echo1, executor3);
//        CompletableFuture<String> cf3_2 = cf2.thenApplyAsync(Tests::echo2, executor3);
//        CompletableFuture<String> cf3_3 = cf2.thenApplyAsync(Tests::echo3, executor3);
//        CompletableFuture<Void> cf3 = CompletableFuture.allOf(cf3_1, cf3_2, cf3_3);
//        CompletableFuture<Void> cf4 = cf3.thenAcceptAsync(x -> print("world"), executor4);
    }

    public static void main(String[] args) throws Exception {
        List<Supplier<String>> suppliers = Lists.newArrayList(() -> {
            //.....
            Person person = new Person();
            person.setName("阿康");
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return person.getName();
        },() -> {
            //.....
            Person person = new Person();
            person.setName("阿垚");
            try {
                Thread.sleep(22);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return person.getName();
        });

        long start3 = System.currentTimeMillis();
        List<String> res3 = suppliers.parallelStream().map(Supplier::get).collect(Collectors.toList());
        System.out.println("打印3"+ JSON.toJSONString(res3)+(System.currentTimeMillis()-start3));

        long start2 = System.currentTimeMillis();
        List<String> res2 = exc2(suppliers);
        System.out.println("打印2"+ JSON.toJSONString(res2)+(System.currentTimeMillis()-start2));


        long start = System.currentTimeMillis();
        List<String> res = exc(suppliers);
        System.out.println("打印"+ JSON.toJSONString(res)+(System.currentTimeMillis()-start));
    }

    private static  <T> List<T> exc(List<Supplier<T>> suppliers) throws Exception {
        List<CompletableFuture<T>> tasks = suppliers.stream().map(supplier -> CompletableFuture.supplyAsync(supplier, Executors.newCachedThreadPool())).collect(Collectors.toList());
        // 转换并执行汇总等待结果 join是运行时异常
        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[tasks.size()])).join();
        List<T> results = new ArrayList<>();
        for (CompletableFuture<T> completableFuture : tasks ) {
            // 等待获取结果  get会抛出检查异常  join是运行时异常
            results.add(completableFuture.get());
        }
        return results;
    }

    private static  <T> List<T> exc2(List<Supplier<T>> suppliers) {
        List<CompletableFuture<T>> tasks = suppliers.stream().map(supplier -> CompletableFuture.supplyAsync(supplier, Executors.newCachedThreadPool())).collect(Collectors.toList());
        return tasks.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }
}
