/**
 * llkang.com Inc.
 * Copyright (c) 2010-2022 All Rights Reserved.
 */
package top.kexcellent.back.code.disruptor.reference;

import com.lmax.disruptor.EventFactory;
import top.kexcellent.back.code.disruptor.reference.LongEvent;

/**
 * Factory
 *
 * @author kanglele
 * @version $Id: LongEventFactory, v 0.1 2022/3/25 18:42 kanglele Exp $
 */
public class LongEventFactory implements EventFactory<LongEvent> {

    @Override
    public LongEvent newInstance()
    {
        return new LongEvent();
    }
}
