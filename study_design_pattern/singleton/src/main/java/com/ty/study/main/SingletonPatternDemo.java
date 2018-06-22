package com.ty.study.main;

import com.ty.study.singleton.Singleton;
import com.ty.study.singleton.impl.*;

/**
 * 单例模式主程序入口
 *
 * @author relax tongyu
 * @create 2018-01-29 10:25
 **/
public class SingletonPatternDemo {

    private void testLazySingleton(){
        //获取单例对象
        Singleton instance = LazySingleton.getInstance();
        //执行单例对象方法
        instance.service();
    }

    private void testLazySingletonThreadSafe(){
        Singleton instance = LazySingletonThreadSafe.getInstance();
        instance.service();
    }

    private void testIntimesingleton(){
        Singleton instance = Intimesingleton.getInstance();
        instance.service();
    }

    private void testLazySingletonDoubleLock(){
        Singleton instance = LazySingletonDoubleLock.getInstance();
        instance.service();
    }

    private void testLazySingletonRegistor(){
        Singleton instance = LazySingletonRegistor.getInstance();
        instance.service();
    }

    private void testEnumerationSingleton(){
        Singleton instance = EnumerationSingleton.INSTANCE;
        instance.service();
    }


    public static void main(String[] args) {
        SingletonPatternDemo singletonPatternDemo = new SingletonPatternDemo();
        singletonPatternDemo.testLazySingleton();
        singletonPatternDemo.testLazySingletonThreadSafe();
        singletonPatternDemo.testIntimesingleton();
        singletonPatternDemo.testLazySingletonDoubleLock();
        singletonPatternDemo.testLazySingletonRegistor();
        singletonPatternDemo.testEnumerationSingleton();
    }

}
