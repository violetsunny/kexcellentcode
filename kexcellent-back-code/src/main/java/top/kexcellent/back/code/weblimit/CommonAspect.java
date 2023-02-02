/**
 * LY.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package top.kexcellent.back.code.weblimit;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * @author kll49556
 * @version $Id: CommonAspect, v 0.1 2018/7/24 16:09 kll49556 Exp $
 */
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Slf4j
public class CommonAspect {

    private static final RedisLimit REDIS_LIMIT = new RedisLimit.Builder(new Jedis()).build();

    @Pointcut("@annotation(com.violet.llkang.project.biz.annotation.weblimit.CommonLimit)")
    private void check(){}

    @Before("check()")
    public void before(JoinPoint joinPoint) throws Exception {
        if (REDIS_LIMIT == null) {
            throw new NullPointerException("redisLimit is null");
        }
        boolean limit = REDIS_LIMIT.limit();
        if (!limit) {
            log.warn("request has bean limit");
            throw new RuntimeException("request has bean limit") ;
        }
    }
}
