/**
 * LY.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package top.kexcellent.back.code.cache;

import com.google.common.cache.*;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author kll49556
 * @version $Id: GcacheDemo, v 0.1 2018/8/29 10:16 kll49556 Exp $
 */
@Slf4j
public class GcacheDemo {

    private static LoadingCache<Integer, AtomicLong> loadingCache ;
    private final static Integer KEY = 1000;


    private final static LinkedBlockingQueue<Integer> QUEUE = new LinkedBlockingQueue<>(1000);


    private void init() throws InterruptedException {
        loadingCache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .removalListener(new RemovalListener<Object, Object>() {
                    @Override
                    public void onRemoval(RemovalNotification<Object, Object> notification) {
                        log.info("删除原因={"+notification.getCause()+"}，删除 key={"+notification.getKey()+"},删除 value={"+notification.getValue()+"}");
                    }
                })
                .build(new CacheLoader<Integer, AtomicLong>() {
                    @Override
                    public AtomicLong load(Integer key) throws Exception {
                        return new AtomicLong(0);
                    }
                });


        for (int i = 10; i < 15; i++) {
            QUEUE.put(i);
        }
    }

    private void checkAlert(Integer integer) {
        try {

            //loadingCache.put(integer,new AtomicLong(integer));

            TimeUnit.SECONDS.sleep(3);


            log.info("当前缓存值={},缓存大小={}", loadingCache.get(KEY),loadingCache.size());
            log.info("缓存的所有内容={}",loadingCache.asMap().toString());
            loadingCache.get(KEY).incrementAndGet();

        } catch (ExecutionException e ) {
            log.error("Exception", e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        GcacheDemo cacheLoaderTest = new GcacheDemo() ;
        cacheLoaderTest.init();

        while (true) {
            try {
                Integer integer = QUEUE.poll(200, TimeUnit.MILLISECONDS);
                if (null == integer) {
                    break;
                }
                //TimeUnit.SECONDS.sleep(5);
                cacheLoaderTest.checkAlert(integer);
                log.info("job running times={"+integer+"}");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
