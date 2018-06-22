package com.ty.study.mystorm.core;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by maoxiangyi on 2016/6/7.
 */
public abstract class BaseBolt implements Bolt,Runnable{
    private ArrayBlockingQueue inputQueue;
    public void run() {
        while (true) {
            if (this.inputQueue != null) {
                try {
                    execute((String) inputQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setInputQueue(ArrayBlockingQueue inputQueue) {
        this.inputQueue = inputQueue;
    }
}