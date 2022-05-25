/**
 * LY.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package top.kexcellent.back.code.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * Executors
 * @author kll49556
 * @version $Id: ExecutorFactory, v 0.1 2018/9/11 9:33 kll49556 Exp $
 */
public class ExecutorFactory {

    private ExecutorFactory(){}

    private final static int SIZE = 10;

    public static ExecutorService getInstance(){
        return ExecutorEnum.INSTANCE.getInstance();
    }

    private enum ExecutorEnum{
        INSTANCE;

        private ExecutorService factory;

        ExecutorEnum(){
            factory = Executors.newFixedThreadPool(SIZE);
        }//固定大小的线程池

        public ExecutorService getInstance(){
            return factory;
        }
    }
}
