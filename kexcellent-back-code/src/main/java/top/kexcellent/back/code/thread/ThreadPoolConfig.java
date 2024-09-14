/**
 * LY.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package top.kexcellent.back.code.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 枚举写单例
 * @author kanglele01
 * @version $Id: ThreadPoolConfig, v 0.1 2020/4/29 15:38 kanglele01 Exp $
 */
//@Configuration
public class ThreadPoolConfig {

    //@Bean("threadPoolExecutor")
    public ThreadPoolExecutor getThreadPool(){
        return ThreadPoolEnum.INSTANCE.getInstance();
    }

    enum ThreadPoolEnum{
        /**
         * 实例
         */
        INSTANCE;

        ThreadPoolExecutor executor;

        ThreadPoolEnum(){
            //获取cpu核数
            int cpuCore = Runtime.getRuntime().availableProcessors();
            executor = new ThreadPoolExecutor(cpuCore+1,cpuCore<<4,0, TimeUnit.SECONDS,new LinkedBlockingQueue<>(),new ThreadPoolExecutor.AbortPolicy());
        }

        private ThreadPoolExecutor getInstance(){
            return executor;
        }
    }

}
