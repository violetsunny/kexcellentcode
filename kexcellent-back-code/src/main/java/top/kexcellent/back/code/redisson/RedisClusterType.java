/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package top.kexcellent.back.code.redisson;

/**
 * @author kll
 * @version $Id: RedisClusterType, v 0.1 2021/7/13 9:49 Exp $
 */
public enum RedisClusterType {
    SINGLE,
    MASTERSLAVE,
    SENTINEL,
    CLUSTER,
    REPLICATE;

    private RedisClusterType() {
    }
}
