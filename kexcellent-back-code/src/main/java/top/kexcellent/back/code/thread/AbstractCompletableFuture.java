/**
 * LY.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package top.kexcellent.back.code.thread;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import top.kdla.framework.common.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/**
 * @author kll49556
 * @version $Id: BaseCompletableFuture, v 0.1 2018/8/8 9:20 kll49556 Exp $
 */
@Slf4j
public abstract class AbstractCompletableFuture<T,E> {

    private final ExecutorService exs = ExecutorFactory.getInstance();

    public List<E> run(List<T> taskList){
        String logs= "<refundchangeapi><BaseCompletableFuture><BaseCompletableFuture><run>";
        Long start = System.currentTimeMillis();
        //结果集
        List<E> resultList = new ArrayList<>();
        try {
            //方式二：全流式处理转换成CompletableFuture[]+组装成一个无返回值CompletableFuture，join等待执行完毕。返回结果whenComplete获取
            @SuppressWarnings("rawtypes")
            CompletableFuture[] cfs = taskList.stream().map(t -> CompletableFuture.supplyAsync(() ->  calc(t), exs)
                    //.thenApply(i -> conversion(i))//可用于结果转化
                    .whenComplete((v, e) -> {
                        if(null != v){
                            resultList.add(v);//收集返回值
                        }
                        log.info(logs + "【执行】>>>" + ",result=" + JSON.toJSONString(v) + ",异常=" + ExceptionUtils.getFullStackTrace(e)+",执行时间=" + DateUtils.format(new Date()));
                    }))
                    .toArray(CompletableFuture[]::new);
            CompletableFuture.allOf(cfs).join();
            log.info(logs + "【执行结束】>>>" + ",入参=" + JSON.toJSONString(taskList) + ",返回=" + JSON.toJSONString(resultList)+",耗时=" + (System.currentTimeMillis() - start));
        } catch (Exception e) {
            log.error(logs + "【执行异常】>>>" + ",入参=" + JSON.toJSONString(taskList) + ",异常=" + ExceptionUtils.getFullStackTrace(e));
        }
        return resultList;
    }

    protected abstract E calc(T t);

    public E conversion(Object o){
        return (E)o;
    }
}
