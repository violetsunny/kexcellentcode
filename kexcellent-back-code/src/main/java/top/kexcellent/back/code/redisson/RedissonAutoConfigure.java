/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package top.kexcellent.back.code.redisson;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * @author kll
 * @version $Id: RedissonAutoConfigure, v 0.1 2021/7/13 9:55 Exp $
 */
@Configuration
@ConditionalOnClass(RedissonRedDisLock.class)
@EnableConfigurationProperties(RedissonConfig.class)
public class RedissonAutoConfigure {

    @Value("${app.id:}")
    private String appId;

    @Bean
    @ConditionalOnMissingBean
    RedissonRedDisLock starterRedissonClient(RdfaDistributeLockFactory rdfaDistributeLockFactory){
        return new RedissonRedDisLock(rdfaDistributeLockFactory);
    }

    @Bean
    RdfaDistributeLockFactory rdfaDistributeLockFactory(RedissonConfig redissonConfig){
        String prefix= this.appId==null||"".equals(this.appId)? UUID.randomUUID().toString():this.appId;
        return new RedissonLockFactory(redissonConfig,prefix);
    }
}

