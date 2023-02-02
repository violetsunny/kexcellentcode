/**
 * LY.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package top.kexcellent.back.code.thread;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * 
 * @author xy23087
 * @version $Id: RingBuffers.java, v 0.1 2016年11月23日 下午7:28:06 xy23087 Exp $
 */
public class RingBuffers {
    /** 处理剩余数量 */
    public int                     workNum;
    /** RingBuffer环 */
    private RingBuffer<BuildEvent> buffer;
    /** workerPool */
    private WorkerPool<BuildEvent> workerPool;
    /** 环的大小 */
    private int                    bufferSize;
    /** 处理线程数 */
    private int                    threadNum;

    /**
     * 构造
     */
    public RingBuffers(int bufferSize, int threadNum, int workNum) {
        this.bufferSize = bufferSize;
        this.threadNum = threadNum;
        this.workNum = workNum;
        buffer = RingBuffer.create(ProducerType.MULTI, BuildEvent.EVENT_FACTORY, bufferSize, new BlockingWaitStrategy()); //定义一个ringBuffer,也就是相当于一个队列
        workerPool = new WorkerPool<BuildEvent>(buffer, buffer.newBarrier(), new IgnoreExceptionHandler(), getHanders());

        //每个消费者，也就是 workProcessor都有一个sequence，表示上一个消费的位置,这个在初始化时都是-1  
        Sequence[] sequences = workerPool.getWorkerSequences();

        //将其保存在ringBuffer中的 sequencer 中，在为生产申请slot时要用到,也就是在为生产者申请slot时不能大于此数组中的最小值,否则产生覆盖  
        buffer.addGatingSequences(sequences);
        workerPool.start(Executors.newFixedThreadPool(threadNum)); //用executor 来启动 workProcessor 线程  
        System.out.println("disruptor started ");
    }

    /**
     * TODO 添加方法注释.
     * 
     * @return boolean
     */
    public boolean isRingBufferAllDone() {
        if (workNum == 0) {
            return true;
        }
        return false;
    }

    /**
     * 向RingBuffer发布数据
     */
    public void publish(List<BuildEvent> events) throws InterruptedException {
        System.out.println("开始生产");
        for (int i = 0; i < events.size(); i++) {
            long next = 0;
            try {
                next = buffer.next();
                BuildEvent event = buffer.get(next);
                event.setItinerary(events.get(i).getItinerary());
                event.setBookPerson(events.get(i).getBookPerson());
            } finally {
                buffer.publish(next);
                Thread.sleep(50);
            }
        }
        System.out.println("生产完毕");
    }

    /**
     * EventHandler需要指定具体实现
     * 
     * @return ValueEventHandler[]
     */
    public WorkHandler[] getHanders() {
        List<WorkHandler> handlers = new ArrayList<WorkHandler>();
        for (int i = 0; i < threadNum; i++) {
            //EventHandler handler = new EventHandler();具体实现
            //handlers.add(handler);
        }
        WorkHandler[] handlersResult = {};
        handlersResult = handlers.toArray(handlersResult);
        return handlersResult;
    }

    /**
     * TODO 添加方法注释.
     * 
     * @return RingBuffer
     */
    public RingBuffer<BuildEvent> getBuffer() {
        return this.buffer;
    }

    /**
     * TODO 添加方法注释.
     * 
     * @param buffer RingBuffer
     */
    public void setBuffer(RingBuffer<BuildEvent> buffer) {
        this.buffer = buffer;
    }

    /**
     * TODO 添加方法注释.
     * 
     * @return threadNum
     */
    public int getThreadNum() {
        return this.threadNum;
    }

    /**
     * TODO 添加方法注释.
     * 
     * @param threadNum threadNum
     */
    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

    /**
     * TODO 添加方法注释.
     * 
     * @return bufferSize
     */
    public int getBufferSize() {
        return this.bufferSize;
    }

    /**
     * TODO 添加方法注释.
     * 
     * @param bufferSize bufferSize
     */
    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    /**
     * TODO 添加方法注释.
     * 
     * @return workNum
     */
    public int getWorkNum() {
        return this.workNum;
    }

    /**
     * TODO 添加方法注释.
     * 
     */
    public synchronized void subWorkNum() {
        this.workNum--;
    }

    /**
     * TODO 添加方法注释.
     * 
     * @param workNum workNum
     */
    public void setWorkNum(int workNum) {
        this.workNum = workNum;
    }
}
