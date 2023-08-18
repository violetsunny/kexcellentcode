/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package top.kexcellent.back.code.redisson;

import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

/**
 * @author kll
 * @version $Id: RedissonRedDisLock, v 0.1 2021/7/13 9:41 Exp $
 */
public class RedissonRedDisLock implements DistributeLock {
    private static Logger logger = LoggerFactory.getLogger(RedissonRedDisLock.class);

    private RdfaDistributeLockFactory factory;

    public RedissonRedDisLock(RdfaDistributeLockFactory factory) {
        this.factory = factory;
    }

    private static final String SHADOW_KEY = "R_SHADOW:";

    @Override
    public Lock lock(String lockKey) throws LockFailException{
        return lock(lockKey, null, -1, -1);
    }

    /**保持兼容,全部使用没有超时时间的锁*/
    @Override
    public Lock lock(String lockKey, long timeout) throws LockFailException {
        return lock(lockKey,TimeUnit.SECONDS,-1);
//        return lock(lockKey, TimeUnit.SECONDS, timeout);
    }

    /**保持兼容,全部使用没有超时时间的锁*/
    @Override
    public Lock lock(String lockKey, TimeUnit unit, long timeout) throws LockFailException{
        return lock(lockKey, unit, -1, -1);
//        return lock(lockKey, unit, timeout, -1);
    }

    /**保持兼容,全部使用没有超时时间的锁*/
    @Override
    public Lock lock(String lockKey, TimeUnit unit, long timeout, long leaseTime) throws LockFailException {
//        if (FullChainContext.getInstance().isPress()) {
//            lockKey = SHADOW_KEY + lockKey;
//        }
        logger.info("lock use redlock with name {} start...", lockKey);

        try {
            RedissonRedLock rdfaRedLock = (RedissonRedLock)factory.getLock(lockKey);
            if(rdfaRedLock.tryLock(timeout, leaseTime, unit)){
                return rdfaRedLock;
            }else{
                throw new LockFailException("lock use redlock with name " + lockKey + " failed.");
            }
        } catch (InterruptedException e) {
            logger.warn("lock time expired");
            throw new LockFailException("lock use redlock with name " + lockKey + " failed for " + e.getMessage());
        }
    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime){
        throw new UnsupportedOperationException("Red lock not support the try lock method.");
    }

    @Override
    public void unlock(String lockKey) {
        RedissonRedLock rdfaRedLock = (RedissonRedLock)factory.getLock(lockKey);
        this.unlock(rdfaRedLock);
    }

    @Override
    public void unlock(Lock lock) {
        if(lock==null||!(lock instanceof RedissonRedLock)){
            return;
        }
        RedissonRedLock rdfaRedLock = (RedissonRedLock)lock;
        if(lock!=null){
            rdfaRedLock.unlock();
        }else{
            logger.info("lock is null, don't need to unlock");
        }
    }
}

