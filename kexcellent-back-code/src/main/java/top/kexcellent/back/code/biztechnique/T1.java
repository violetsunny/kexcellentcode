/**
 * LY.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package top.kexcellent.back.code.biztechnique;

import top.kdla.framework.common.help.DateHelp;
import top.kdla.framework.common.help.LocalDateTimeHelp;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author kanglele
 * @version $Id: T1, v 0.1 2024/10/31 11:47 kanglele Exp $
 */
public class T1 {
    ScheduledExecutorService reConnect = Executors.newSingleThreadScheduledExecutor();
    AtomicInteger retries = new AtomicInteger(0);
    AtomicBoolean retry = new AtomicBoolean(false);

    public int cal (){
        return retries.getAndIncrement();
    }

    public void reconect(){
        reConnect.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println(cal());
                if (retries.get() <= 10) {
                    System.out.println(LocalDateTimeHelp.toString(LocalDateTime.now()) + "重连");
                    reconect();
                    System.out.println(LocalDateTimeHelp.toString(LocalDateTime.now()) + "重连中。。");
                } else {
                    reConnect.shutdown();
                }
            }

        }, 5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        T1 t1 = new T1();
        t1.reconect();
    }
}
