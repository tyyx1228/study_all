package com.ty.study.mystorm.core;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by maoxiangyi on 2016/6/7.
 */
public class MyTopologyBuilder {
    private HashMap<String, Object> component;
    private ExecutorService executorService;

    public MyTopologyBuilder() {
        this.component = new HashMap<String, Object>();
        this.executorService = Executors.newFixedThreadPool(10);
    }

    public void submit() {
        //1、初始化一个list，list中封装多个队列
        List<ArrayBlockingQueue> list = new ArrayList<ArrayBlockingQueue>();
        int queueNum = component.size();
        // queueNum-1 N个组件需要N-1个队列来进行连接
        for (int i = 0; i < queueNum - 1; i++) {
            list.add(new ArrayBlockingQueue<String>(10000));
        }

        //2、迭代出用户提交的component
        for (Map.Entry entry : component.entrySet()) {
            String componentName = (String) entry.getKey();
            if (componentName.contains("spout")) {
                BaseSpout baseSpout = (BaseSpout) entry.getValue();
                baseSpout.open(new HashMap(), new MyContext(), new MySpoutOutputCollector(list.get(0)));
                executorService.submit(baseSpout);
            }
            if (componentName.contains("bolt")) {
                String sequence = componentName.substring(4);
                Integer sequenceIndex = Integer.parseInt(sequence);
                //将组件编号递减1，与数组角标一样
                if (sequenceIndex < list.size() && sequenceIndex >= 1) {
                    BaseBolt baseBolt = (BaseBolt) entry.getValue();
                    //从哪个队列获取数据
                    baseBolt.setInputQueue(list.get(sequenceIndex - 1));
                    baseBolt.prepare(new HashMap(), new MyContext(), new MyOutputCollector(list.get(sequenceIndex)));
                    executorService.submit(baseBolt);
                } else if (sequenceIndex == list.size()) {
                    BaseBolt baseBolt = (BaseBolt) entry.getValue();
                    baseBolt.setInputQueue(list.get(sequenceIndex - 1));
                    baseBolt.prepare(new HashMap(), new MyContext(), new MyOutputCollector());
                    executorService.submit(baseBolt);
                }
            }
        }
    }

    public void setBolt(String boltName, BaseBolt myBolt, int num) {
        component.put(boltName, myBolt);
    }

    public void setSpout(String spoutName, BaseSpout mySpout, int num) {
        component.put(spoutName, mySpout);
    }
}
