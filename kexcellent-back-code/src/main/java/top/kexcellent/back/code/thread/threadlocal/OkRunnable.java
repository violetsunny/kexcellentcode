/**
 * llkang.com Inc.
 * Copyright (c) 2010-2023 All Rights Reserved.
 */
package top.kexcellent.back.code.thread.threadlocal;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author kanglele
 * @version $Id: OkRunable, v 0.1 2023/3/9 9:44 kanglele Exp $
 */
public class OkRunnable implements Runnable{

    private final Runnable runnable;

    private final AtomicReference<Object> capturedRef;

    public OkRunnable(Runnable runnable) {
        this.capturedRef = new AtomicReference<>(OkThreadLocal.capture());
        this.runnable = runnable;
    }


    @Override
    public void run() {
        final Object captured = capturedRef.get();
        if (captured == null || !capturedRef.compareAndSet(captured, null)) {
            throw new IllegalStateException("TTL value reference is released after run!");
        }

        final Object backup = OkThreadLocal.replay(captured);
        try {
            runnable.run();
        } finally {
            OkThreadLocal.restore(backup);
        }
    }

    public static OkRunnable get(Runnable runnable){
        return new OkRunnable(runnable);
    }
}
