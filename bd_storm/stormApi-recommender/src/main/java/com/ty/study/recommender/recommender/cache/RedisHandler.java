package com.ty.study.recommender.recommender.cache;


import com.ty.study.recommender.recommender.utils.MyShardedJedisPool;

/**
 * Describe: 请补充类描述
 * Author:   maoxiangyi
 * Domain:   www.itcast.cn
 * Data:     2015/12/2.
 */
public class RedisHandler {
    public static String getValueByHashField(String key, String field) {
        return MyShardedJedisPool.getResource().hget(key, field);
    }

    public static String getString(String key) {
        return MyShardedJedisPool.getResource().get(key);
    }
}
