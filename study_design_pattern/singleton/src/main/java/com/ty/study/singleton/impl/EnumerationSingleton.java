package com.ty.study.singleton.impl;

import com.ty.study.singleton.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 枚举方式实现单例，这种方式枚举类中只定义一个实例
 *
 * thread safe
 *
 * @author relax tongyu
 * @create 2018-01-29 10:39
 **/
public enum EnumerationSingleton implements Singleton{
    INSTANCE {
        public final Logger log = LogManager.getLogger(this.getClass());
        @Override
        public void service() {
            log.info("service method execute... and init INSTANCE thread safe");
        }
    }
}
