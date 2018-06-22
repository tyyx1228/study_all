package com.ty.study;

import com.google.common.base.Splitter;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * guava示例程序
 *
 * @author relax tongyu
 * @create 2018-06-19 9:15
 **/
@Slf4j
public class GuavaUsefulUtilTest {

    /**
     * 字符串拆分
     */
    @Test
    public void testSplitter(){
        Splitter splitter = Splitter.onPattern(",\\s*");
        Iterable<String> split = splitter.split("12, 323, 22, A, B, C");
        for (String sp: split){
            log.info(sp);
        }
    }


    /**
     * 限流，限速
     */
    @Test
    public void testRateLimiter(){
        //设置一个单位时间内流量为15的限速器， 其中单位时间另外调用限速器上的acquire方法
        RateLimiter limiter = RateLimiter.create(15);

        while(true){
            limiter.acquire(5); // 其中参数5表示 ：以5秒为一个时间单位，若不指定该参数，默认为1s
            log.info("");
        }
    }

    /**
     * guava提供的缓存机制
     */
    @Test
    public void testCacheBuiler(){
        LoadingCache<String, Optional<String>> cache = CacheBuilder.newBuilder()
                .maximumSize(1000)  //设置缓存大小，这里指的是Cache中元素最多有1000个
                .expireAfterWrite(8, TimeUnit.SECONDS)  //设置Cache中每个元素的生命周期
                .build(
                    new CacheLoader<String, Optional<String>>() {
                        public Optional<String> load(String key) throws Exception {
                            //具体可支持外部查询
                            String s = key.hashCode() + "";
                            log.info("loading ...");
                            return s==null ?  Optional.empty(): Optional.of(s);
                        }
                    }
                );

        int seconds = 9;
        while(true){
            try {
                Optional<String> o = cache.get("tongyu");
                log.info(o.isPresent() ? o.get() : "");

                seconds = seconds==9 ? Math.min(9, 2) : Math.max(9, 2);
                TimeUnit.SECONDS.sleep(seconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 正常迭代
     */
    @Test
    public void testIterator(){
        ArrayList<Integer> ints = Lists.newArrayList(1, 3, 2, 3, 4, 5, 7);
        Iterator<Integer> it = ints.iterator();
        while (it.hasNext()){
            log.info("{}", it.next());
        }
    }

}
