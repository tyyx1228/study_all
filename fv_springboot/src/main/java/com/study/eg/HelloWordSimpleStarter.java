package com.study.eg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * a
 *
 * @author relax tongyu
 * @create 2018-02-02 16:02
 **/
@SpringBootApplication //默认从当前类所在包开始扫描注解
@EnableAsync
public class HelloWordSimpleStarter {


/*
    @Override
    public void run(String... args) {
        System.out.println(this.helloWorldService.getHelloMessage());
        if (args.length > 0 && args[0].equals("exitcode")) {
            throw new ExitException();
        }
    }
*/


    public static void main(String[] args) {
        SpringApplication.run(HelloWordSimpleStarter.class, args);
    }
}
