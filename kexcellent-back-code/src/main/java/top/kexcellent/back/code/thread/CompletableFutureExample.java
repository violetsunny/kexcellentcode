/**
 * llkang.com Inc.
 * Copyright (c) 2010-2022 All Rights Reserved.
 */
package top.kexcellent.back.code.thread;

import com.google.common.collect.Lists;
import top.kdla.framework.common.help.MultiThreadInvokeHelp;
import top.kexcellent.back.code.model.Person;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

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
}
