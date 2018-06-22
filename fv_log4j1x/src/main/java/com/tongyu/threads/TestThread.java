package com.tongyu.threads;


import com.tongyu.util.LogUtil;
import org.apache.log4j.Logger;

/**
 * 本类用于测试线程中的日志
 *
 * @author relax tongyu
 * @create 2018-01-28 18:43
 **/
public class TestThread implements Runnable{
    private static final Logger logger = Logger.getLogger(TestThread.class);

    private long times=100;

    public TestThread(long times) {
        this.times = times;
    }

    public TestThread() {
    }

    /**
     * 线程中输出日志
     */
    @Override
    public void run() {
        LogUtil.logOut(logger, times);
    }
}
