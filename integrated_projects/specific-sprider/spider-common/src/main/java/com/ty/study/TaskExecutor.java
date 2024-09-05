package com.ty.study;

import com.ty.study.pool.TaskPool;
import com.ty.study.task.Task;

import java.util.concurrent.*;

/**
 * Task执行器
 *
 * @author relax tongyu
 * @create 2018-06-08 11:59
 **/
public class TaskExecutor implements TaskPool {

    private ExecutorService threadPool;

    private LinkedBlockingQueue queue;

    private int nThreads=4;

    public TaskExecutor(int nThreads){
        this.nThreads = Math.max(nThreads, this.nThreads);
        queue = new LinkedBlockingQueue<>();
        threadPool =  new ThreadPoolExecutor(this.nThreads, this.nThreads,
                0L, TimeUnit.MILLISECONDS,
                queue);
    }

    //初始化执行器

    @Override
    public void execute(Task task) {
        threadPool.execute(task);
    }

    @Override
    public void close() {
        threadPool.shutdown();
    }
}
