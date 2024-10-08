/**
 * LY.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package top.kexcellent.back.code.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author kll49556
 * @version $Id: CacheMap, v 0.1 2018/8/29 10:35 kll49556 Exp $
 */
@Slf4j
public class CacheMap {

    /**
     * @desction: 使用google guava缓存处理
     * @author: wangji
     * @date: 2017/11/22 9:59
     */
    private static Cache<String,Object> cache;
    static {
        cache = CacheBuilder.newBuilder().maximumSize(10000)
                .expireAfterWrite(24, TimeUnit.HOURS)
                .initialCapacity(10)
                .removalListener(new RemovalListener<String, Object>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, Object> rn) {
                        if(log.isInfoEnabled()){
                            log.info("被移除缓存{}:{}",rn.getKey(),rn.getValue());
                        }
                    }
                }).build();
    }

    /**
     * @desction: 获取缓存
     * @author: wangji
     * @date: 2017/11/22 9:50
     */
    public  static Object get(String key){
        return StringUtils.isNotEmpty(key)?cache.getIfPresent(key):null;
    }
    /**
     * @desction: 放入缓存
     * @author: wangji
     * @date: 2017/11/22 9:50
     */
    public static void put(String key,Object value){
        if(StringUtils.isNotEmpty(key) && value !=null){
            cache.put(key,value);
        }
    }
    /**
     * @desction: 移除缓存
     * @author: wangji
     * @date: 2017/11/22 9:50
     */
    public static void remove(String key){
        if(StringUtils.isNotEmpty(key)){
            cache.invalidate(key);
        }
    }
    /**
     * @desction: 批量删除缓存
     * @author: wangji
     * @date: 2017/11/22 9:49
     */
    public static void remove(List<String> keys){
        if(keys !=null && keys.size() >0){
            cache.invalidateAll(keys);
        }

    }
}
