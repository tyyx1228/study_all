package com.ty.study.task;

import com.ty.study.model.Page;

import java.io.Serializable;

/**
 * 线程任务：执行爬虫任务
 *
 * @author relax tongyu
 * @create 2018-06-08 11:26
 **/
public interface Task extends Runnable, Serializable{
    void execute(Page page);
}
