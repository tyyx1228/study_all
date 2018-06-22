package com.ty.study.loganalyzer.app.dao;

import java.util.HashMap;
import java.util.Map;

/**
 * Describe: 缓存上一分钟的数据，用来做cache
 * 整点的时候，cache层中的数据，需要清理掉
 * Author:   maoxiangyi
 * Domain:   www.itcast.cn
 * Data:     2015/11/17.
 */
public class CacheData {
    //Map<String, Integer>  redisKey->上一分钟的全量值为 value
    private static Map<String, Integer> pvMap = new HashMap<>();
    private static Map<String, Long> uvMap = new HashMap<>();

    public static int getPv(int pv, String indexName) {
        Integer cacheValue = pvMap.get(indexName);
        //什么样的情况下，缓存缓存中的值为null，
        //1、程序刚启动的
        //2、零点
        if (cacheValue == null) {
            cacheValue = 0;
            pvMap.put(indexName, cacheValue);
        }
        //计算增量值
        //正常情况下，当前最新的pv会大于等于上一分钟的
        //1、零点转钟
        //2、或者一分钟pv值没有进行更新
        if (pv > cacheValue.intValue()) {
            pvMap.put(indexName, pv); //将新的值赋值个cacheData
            return pv - cacheValue.intValue();
        }
        return 0;//如果新的值小于旧的值，直接返回0
    }

    public static long getUv(long uv, String indexName) {
        Long cacheValue = uvMap.get(indexName);
        if (cacheValue == null) {
            cacheValue = 0l;
            uvMap.put(indexName, cacheValue);
        }
        if (uv > cacheValue.longValue()) {
            uvMap.put(indexName, uv);//将新的值赋值给cachaData
            return uv - cacheValue;
        }
        return 0;//如果新的值小于旧的值，直接返回0
    }

    public static Map<String, Integer> getPvMap() {
        return pvMap;
    }

    public static void setPvMap(Map<String, Integer> pvMap) {
        CacheData.pvMap = pvMap;
    }

    public static Map<String, Long> getUvMap() {
        return uvMap;
    }

    public static void setUvMap(Map<String, Long> uvMap) {
        CacheData.uvMap = uvMap;
    }
}
