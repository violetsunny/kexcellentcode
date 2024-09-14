/**
 * llkang.com Inc.
 * Copyright (c) 2010-2023 All Rights Reserved.
 */
package top.kexcellent.back.code.redisson;

import java.util.concurrent.locks.Lock;

/**
 * @author kanglele
 * @version $Id: RdfaDistributeLockFactory, v 0.1 2023/8/17 16:34 kanglele Exp $
 */
@FunctionalInterface
public interface RdfaDistributeLockFactory {
    Lock getLock(String key);
}
