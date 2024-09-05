package com.study.eg.helloword;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * @author relax tongyu
 * @create 2018-02-02 16:25
 **/

@Component("testImpl")
public class TestImpl implements Test {
    private static final Logger logger = LogManager.getLogger(TestImpl.class);

    public TestImpl(){
        logger.info("new instance with TestImpl...... ");
    }

    /**
     * 注解：@PostConstruct加在方法上，则该方法将在类实例化之后，对象对外提供服务之前被调用执行
     */
    @PostConstruct
    private void init(){
        logger.info("TestImpl invoke method: init()......");
    }

    @Override
    public void test() {
        logger.info("override method executed: test()......");
    }
}
