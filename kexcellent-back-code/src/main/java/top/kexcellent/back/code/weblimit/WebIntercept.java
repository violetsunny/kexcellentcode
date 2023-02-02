/**
 * LY.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package top.kexcellent.back.code.weblimit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author kll49556
 * @version $Id: WebIntercept, v 0.1 2018/7/24 15:43 kll49556 Exp $
 */
@Configuration
@Slf4j
public class WebIntercept implements WebMvcConfigurer {

    private static final RedisLimit REDIS_LIMIT = new RedisLimit.Builder(new Jedis()).build();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomInterceptor())
                .addPathPatterns("/**");
    }

    private class CustomInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                                 Object handler) throws Exception {
            if (REDIS_LIMIT == null) {
                throw new NullPointerException("REDIS_LIMIT is null");
            }
            if (handler instanceof HandlerMethod) {
                HandlerMethod method = (HandlerMethod) handler;
                ControllerLimit annotation = method.getMethodAnnotation(ControllerLimit.class);
                if (annotation == null) {
                    //skip
                    return true;
                }
                boolean limit = REDIS_LIMIT.limit();
                if (!limit) {
                    log.warn("request has bean limit");
                    response.sendError(500, "request limit");
                    return false;
                }
            }
            return true;
        }
    }
}
