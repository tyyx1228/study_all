package com.study.eg.helloword;

import com.study.eg.utils.BaseUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

/**
 * a
 *
 * @author relax tongyu
 * @create 2018-02-02 16:02
 **/
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan //若不指定basepackage，则从当前目录扫描注解, 注意：只与加注解类的路径有关，与该类实现的接口、继承的父类的路径无关
public class HelloWordSimpleStarter implements CommandLineRunner {
    private static final Logger log = LogManager.getLogger(HelloWorld.class);

    @Autowired
    private HelloWorld helloWorld;

    /**
     * 若果程序要从某个bean的方法开始启动整个应用，则可以让本类实现接口CommandLineRunner中的方法run，
     * 然后在本类中注入该bean，在run方法中调用bean的方法
     *
     * @param args
     */
    @Override
    public void run(String... args) {
        log.info(
                "input args.hasCode=" + args==null ? "None" : args.hashCode() + ", "+ ArrayUtils.toString(args)
        );
        helloWorld.invokeMethod(args);
       /* System.out.println(this.helloWorldService.getHelloMessage());
        if (args.length > 0 && args[0].equals("exitcode")) {
            throw new ExitException();
        }*/
    }


    /**
     * spring-boot 应用程序入口
     *
     * @param args
     */
    public static void main(String[] args) {
        log.info(
                "input args.hasCode=" + args==null ? "None" : args.hashCode() + ", "+ ArrayUtils.toString(args)
         );
        SpringApplication.run(HelloWordSimpleStarter.class, args);
    }
}
