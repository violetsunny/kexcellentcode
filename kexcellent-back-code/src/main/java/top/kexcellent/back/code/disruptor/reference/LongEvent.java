/**
 * llkang.com Inc.
 * Copyright (c) 2010-2022 All Rights Reserved.
 */
package top.kexcellent.back.code.disruptor.reference;

import java.io.Serializable;

/**
 * long
 *
 * @author kanglele
 * @version $Id: LongEvent, v 0.1 2022/3/25 18:41 kanglele Exp $
 */
public class LongEvent implements Serializable {
    private long value;

    public void set(long value)
    {
        this.value = value;
    }

    public long get() {
        return this.value;
    }
}
