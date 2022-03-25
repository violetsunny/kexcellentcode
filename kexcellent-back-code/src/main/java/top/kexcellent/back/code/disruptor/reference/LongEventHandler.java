/**
 * llkang.com Inc.
 * Copyright (c) 2010-2022 All Rights Reserved.
 */
package top.kexcellent.back.code.disruptor.reference;

import com.lmax.disruptor.EventHandler;
import top.kdla.framework.common.utils.LocalDateTimeUtils;
import top.kexcellent.back.code.disruptor.reference.LongEvent;

import java.time.LocalDateTime;

/**
 * LongEventHandler
 *
 * @author kanglele
 * @version $Id: LongEventHandler, v 0.1 2022/3/25 19:13 kanglele Exp $
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch)
    {
        System.out.println(LocalDateTimeUtils.toString(LocalDateTime.now()) + ":Event-" + event.get());
    }
}
