package com.ty.study.mythread.deadlock;

/**
 * 死锁示例
 *
 *工作中一定要避免出现死锁情形
 *
 * @author relax tongyu
 * @create 2018-03-15 11:38
 **/
public class DeadLockThread implements Runnable{

    private boolean flag;

    public DeadLockThread(boolean flag) {
        this.flag = flag;
    }

    public void execute() {

        if(flag){
            while (true){
                //让线程sleep的目的，是让多线程发生死锁前有一个和谐的过程
                try {
                    Thread.sleep(51);
                }catch (Exception e){

                }
                synchronized (LockObject.LOCK_A){
                    System.out.println(Thread.currentThread().getName() + " 持有同步锁 "+ LockObject.LOCK_A.name());
                    synchronized (LockObject.LOCK_B){

                    }
                }
            }
        }else{
            while (true){
                //让线程sleep的目的，是让多线程发生死锁前有一个和谐的过程
                try {
                    Thread.sleep(53);
                }catch (Exception e){

                }
                synchronized (LockObject.LOCK_B){
                    System.out.println(Thread.currentThread().getName() + " 持有同步锁 "+ LockObject.LOCK_B.name());
                    synchronized (LockObject.LOCK_A){

                    }
                }
            }
        }


    }

    @Override
    public void run() {
        execute();
    }


    public static void main(String[] args) {
        new Thread(new DeadLockThread(true)).start();  //该线程持有LOCK_A，不释放的情况下访问LOCK_B
        new Thread(new DeadLockThread(false)).start();  //该线程持有LOCK_B，不释放的情况下访问LOCK_A

        //以上当两个线程同时分别持有A,B两个锁时会出现死锁
    }
}
