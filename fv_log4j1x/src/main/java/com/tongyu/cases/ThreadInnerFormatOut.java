package com.tongyu.cases;

import com.tongyu.threads.TestThread;
import com.tongyu.util.LogUtil;
import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试日志中输出线程名字
 *      测试采用console方式输出，   log4j配置文件 app.root.logger=INFO, console， 布局采用record.format2或record.format3
 * @author relax tongyu
 * @create 2018-01-28 18:51
 **/
public class ThreadInnerFormatOut {
    private static final Logger logger = Logger.getLogger(ThreadInnerFormatOut.class);

    public static void main(String[] args) {
        //主线程日志
        LogUtil.logOut(logger, 10);

        //其他线程日志输出测试
        testThreadLog();
    }

    public static void testThreadLog(){
        //需要实例化的线程数量
        int threadInstanceNum = 20;
        //线程池的并行度
        int parallelisim = 8;

        ExecutorService pool = Executors.newFixedThreadPool(parallelisim);
        Random random = new Random();
        try{
            for(int i=0; i<threadInstanceNum; i++){
                pool.execute(new TestThread(random.nextInt(11)));
            }
        }catch (Throwable e){
            logger.error(e);
        }finally {
            logger.info("关闭线程池");
            pool.shutdown();
        }
    }
}
