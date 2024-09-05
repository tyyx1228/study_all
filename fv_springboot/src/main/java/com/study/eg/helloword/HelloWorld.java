package com.study.eg.helloword;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * SpringBeanTest
 *
 * @author relax tongyu
 * @create 2018-02-02 16:03
 **/
@Component
@Scheduled
public class HelloWorld {
    private static final Logger logger = LogManager.getLogger(HelloWorld.class);

    @Autowired
    private TestImpl testImpl;

    public HelloWorld(){
        logger.info("new instance HelloWorld.....");
    }

    public void invokeMethod(Object ...args){
        testImpl.test();
    }


}
