/**
 * llkang.com Inc.
 * Copyright (c) 2010-2022 All Rights Reserved.
 */
package top.kexcellent.back.code.disruptor.reference;

import com.lmax.disruptor.EventTranslatorOneArg;

import java.nio.ByteBuffer;

/**
 * LongEventTranslatorOneArg
 *
 * @author kanglele
 * @version $Id: LongEventTranslatorOneArg, v 0.1 2022/3/25 19:17 kanglele Exp $
 */
public class LongEventTranslatorOneArg implements EventTranslatorOneArg<LongEvent, ByteBuffer> {
    @Override
    public void translateTo(LongEvent event, long sequence, ByteBuffer buffer) {
        event.set(buffer.getLong(0));
    }
}
