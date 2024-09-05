package com.ty.study.pool;

import com.ty.study.task.Task;

import java.io.Closeable;

/**
 * 任务执行器
 *
 * @author relax tongyu
 * @create 2018-06-08 11:30
 **/
public interface TaskPool extends Closeable {

    void execute(Task task);

}
