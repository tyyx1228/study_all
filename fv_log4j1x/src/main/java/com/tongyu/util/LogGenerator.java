package com.tongyu.util;

import org.apache.log4j.Logger;

/**
 * 日志生成器
 *
 * @author relax tongyu
 * @create 2018-01-28 11:47
 **/
public class LogGenerator {
    private Logger logger;

    public LogGenerator(){
        this.logger = Logger.getLogger(LogGenerator.class);
    }

    public LogGenerator(Class clazz){
        this.logger = Logger.getLogger(clazz);
    }

    public LogGenerator(Logger logger){
        this.logger = logger;
    }


    /**
     * 输出所有级别日志
     */
    public void allLevelLog(){
        logger.trace("hellow world trace");
        logger.debug("hello world debug");
        logger.info("hello world info");
        logger.warn("hello world warn");
        logger.error("hello world error");
        logger.fatal("hello world fatal");
    }

    public void allLevelLog(String signal){
        logger.trace("hellow world trace "+signal);
        logger.debug("hello world debug "+signal);
        logger.info("hello world info "+signal);
        logger.warn("hello world warn "+signal);
        logger.error("hello world error "+signal);
        logger.fatal("hello world fatal "+signal);
    }

}
