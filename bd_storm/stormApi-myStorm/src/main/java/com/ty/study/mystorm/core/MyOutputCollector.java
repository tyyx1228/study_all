package com.ty.study.mystorm.core;

import java.util.concurrent.BlockingQueue;

/**
 * Created by maoxiangyi on 2016/6/7.
 */
public class MyOutputCollector {
    private BlockingQueue outputQueue;

    public MyOutputCollector() {
    }

    public MyOutputCollector(BlockingQueue outputQueue) {
        this.outputQueue = outputQueue;
    }

    public void emit(String message) {
        try {
            outputQueue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
