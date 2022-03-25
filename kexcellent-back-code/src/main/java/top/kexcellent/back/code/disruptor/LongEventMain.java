/**
 * llkang.com Inc.
 * Copyright (c) 2010-2022 All Rights Reserved.
 */
package top.kexcellent.back.code.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import top.kexcellent.back.code.disruptor.reference.LongEvent;
import top.kexcellent.back.code.disruptor.reference.LongEventFactory;
import top.kexcellent.back.code.disruptor.reference.LongEventHandler;
import top.kexcellent.back.code.disruptor.reference.LongEventTranslatorOneArg;

import java.nio.ByteBuffer;
import java.util.concurrent.Executors;

/**
 * LongEventMain
 *
 * @author kanglele
 * @version $Id: LongEventMain, v 0.1 2022/3/25 19:17 kanglele Exp $
 */
public class LongEventMain {
    public static void main(String[] args) throws Exception {
        int bufferSize = 1024;
        final Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(
                new LongEventFactory(),
                bufferSize,
                Executors.newSingleThreadExecutor(),
                ProducerType.SINGLE,
                new YieldingWaitStrategy()
        );

        disruptor.handleEventsWith(new LongEventHandler());
        disruptor.start();


        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            bb.putLong(0, l);
            ringBuffer.publishEvent(new LongEventTranslatorOneArg(), bb);
            Thread.sleep(1000);
        }
    }
}
