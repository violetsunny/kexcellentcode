/**
 * LY.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package top.kexcellent.back.code.weblimit;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

import java.util.Collections;

/**
 * @author kll49556
 * @version $Id: RedisLimit, v 0.1 2018/7/24 16:01 kll49556 Exp $
 */
public class RedisLimit {
    private JedisCommands jedis;
    private int limit = 200;
    private static final int FAIL_CODE = 0;
    /**
     * lua script
     */
    private String script;

    protected RedisLimit(Builder builder) {
        jedis = builder.jedis;
        limit = builder.limit;
        buildScript();
    }
    /**
     * limit traffic
     * @return if true
     */
    public boolean limit() {
        String key = String.valueOf(System.currentTimeMillis() / 1000);
        Object result = null;
        if (jedis instanceof Jedis) {
            result = ((Jedis) this.jedis).eval(script, Collections.singletonList(key), Collections.singletonList(String.valueOf(limit)));
        } else if (jedis instanceof JedisCluster) {
            result = ((JedisCluster) this.jedis).eval(script, Collections.singletonList(key), Collections.singletonList(String.valueOf(limit)));
        } else {
            //throw new RuntimeException("instance is error") ;
            return false;
        }

        return FAIL_CODE != (Long) result;
    }

    /**
     * read lua script
     */
    private void buildScript() {
        script = ScriptUtil.getScript("lua/limit.lua");
    }

    /**
     *  the builder
     */
    public static class Builder<T extends JedisCommands>{
        private T jedis ;
        private int limit = 200;

        public Builder(T jedis){
            this.jedis = jedis ;
        }

        public Builder limit(int limit){
            this.limit = limit ;
            return this;
        }

        public RedisLimit build(){
            return new RedisLimit(this) ;
        }
    }
}
