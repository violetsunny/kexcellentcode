/**
 * llkang.com Inc.
 * Copyright (c) 2010-2023 All Rights Reserved.
 */
package top.kexcellent.back.code.thread.threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author kanglele
 * @version $Id: OkThreadLocalTest, v 0.1 2023/3/9 13:34 kanglele Exp $
 */
public class OkThreadLocalTest {

    public static void main(String[] args) {
        run1();
    }

    private static void run1(){
        OkThreadLocal<String> test = new OkThreadLocal<String>();
        test.set("hello");
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Runnable task = () -> {test.set("hello t1");System.out.println(test.get());};
        executorService.submit(OkRunnable.get(task));
        Runnable task2 = () -> {System.out.println(test.get());};
        executorService.submit(OkRunnable.get(task2));
    }

    private static void run2(){
        ThreadLocal<String> test = new ThreadLocal<String>();
        test.set("hello");
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Runnable task = () -> {test.set("hello t1");System.out.println(test.get());};
        executorService.submit(task);
        Runnable task2 = () -> {System.out.println(test.get());};
        executorService.submit(task2);
    }

}
