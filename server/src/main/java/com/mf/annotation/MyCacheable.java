package com.mf.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyCacheable {

    /**
     *
     * 缓存的名称前缀，完整的缓存名称生成规则: {cacheName}:{key}
     */
    String cacheNames();

    /**
     *
     * 缓存的键值
     */
    String key();

    /**
     * 缓存过期时间，单位为秒，默认不设置过期时间
     *
     */
    int expireInSeconds() default 0;
}
