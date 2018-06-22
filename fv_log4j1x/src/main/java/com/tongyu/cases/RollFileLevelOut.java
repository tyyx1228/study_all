package com.tongyu.cases;

import com.tongyu.util.LogUtil;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * 指定日志级别采用文件随时间滚动输出
 * 测试本案例，需要log4j.properties文件中有以下内容的支持
 *      app.root.logger=INFO, MINUTE 或 app.root.logger=INFO, DAILY 或 app.root.logger=INFO, HOUR
 *
 * @author relax tongyu
 * @create 2018-01-28 18:35
 **/
public class RollFileLevelOut {
    private static final Logger logger = Logger.getLogger(RollFileLevelOut.class);

    public static void main(String[] args) throws InterruptedException {
        LogUtil.logOut(logger, 10000, TimeUnit.SECONDS.toMillis(1));
    }

}
