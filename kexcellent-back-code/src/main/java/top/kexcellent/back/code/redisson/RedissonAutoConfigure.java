/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package top.kexcellent.back.code.redisson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kll
 * @version $Id: RedissonAutoConfigure, v 0.1 2021/7/13 9:55 Exp $
 */
@Configuration
@ConditionalOnClass({RedissonRedDisLock.class})
@EnableConfigurationProperties({RedissonConfig.class})
public class RedissonAutoConfigure {
    @Autowired
    private RedissonConfig config;

    public RedissonAutoConfigure() {
    }

    @Bean
    @ConditionalOnMissingBean
    RedissonRedDisLock starterRedissonClient() {
        return new RedissonRedDisLock(this.config);
    }
}

