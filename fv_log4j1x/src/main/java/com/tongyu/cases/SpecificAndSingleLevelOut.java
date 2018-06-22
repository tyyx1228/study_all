package com.tongyu.cases;

import com.tongyu.util.LogUtil;
import org.apache.log4j.Logger;

/**
 * 本例演示指定的单一日志级别输出
 *
 * @author relax tongyu
 * @create 2018-01-28 11:43
 **/
public class SpecificAndSingleLevelOut {

    /**
     * 本方式采用log4j.rootLogger来实现
     */
    private static final Logger logger = Logger.getLogger(SpecificAndSingleLevelOut.class);

    /**
     * 本方式需要在log4j.properties中声明且定义名为“SPECIFIC_WARN_LOG”的appender：
     *    log4j.logger.SPECIFIC_WARN_LOG=warn, SPECIFIC_WARN_LOG
     */
//    private static final Logger logger = Logger.getLogger("SPECIFIC_WARN_LOG");

    public static void main(String[] args) throws InterruptedException {
        LogUtil.logOut(logger, 10000);

    }
}
