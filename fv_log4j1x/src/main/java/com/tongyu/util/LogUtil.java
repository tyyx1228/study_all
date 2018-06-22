package com.tongyu.util;

import org.apache.log4j.Logger;

/**
 * 日志打印工具
 *
 * @author relax tongyu
 * @create 2018-01-28 18:53
 **/
public class LogUtil {

    /**
     * 根据
     * @param logger
     * @param times
     * @param period
     */
    public static void logOut(Logger logger, long times, long period) throws InterruptedException {
        LogGenerator lg = new LogGenerator(logger);

        for(int i=0; i<times; i++) {
            lg.allLevelLog();
            Thread.sleep(period);
        }
    }

    /**
     *
     * @param logger
     * @param times
     */
    public static void logOut(Logger logger, long times){
        LogGenerator lg = new LogGenerator(logger);
        for(int i=0; i<times; i++) {
            lg.allLevelLog();
        }
    }


}
