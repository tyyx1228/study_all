package com.ty.study.mystorm.core;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by maoxiangyi on 2016/6/7.
 */
public class MySpoutOutputCollector {

    private ArrayBlockingQueue outputQueue;

    public MySpoutOutputCollector() {
    }

    public MySpoutOutputCollector(ArrayBlockingQueue outputQueue) {
        this.outputQueue = outputQueue;
    }

    public void emit(String message) {
        try {
            this.outputQueue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
