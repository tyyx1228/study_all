package com.ty.study.mythread.exit;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 *
 * 已对linux下以下命令测试：kill id / kill -15 id / ctrl+c
 * @author relax
 * @date 2018/8/19 10:49
 */
public class ExitThread {

    public static void main(String[] args) throws InterruptedException {
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                System.out.println("Interrupting threads");
                Set<Thread> runningThreads = Thread.getAllStackTraces().keySet();
                for (Thread runningThread : runningThreads) {
                    // 判断线程是否是我们要测试的线程
                    if (runningThread.getName().equals("InteruptThread")) {
                        // 如果线程没有被中断，我们进行中断他
                        if (!runningThread.isInterrupted()) {
                            System.out.println("InteruptThread is not interrupted, we are going to interupt it");
                            runningThread.interrupt();
                        }
                    }
                }

                System.out.println("Shutdown hook ran!");
            }
        });

        // 创建并启动子线程类

        TimeUnit.SECONDS.sleep(120);

    }
}
