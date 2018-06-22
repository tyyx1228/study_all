package com.tongyu.diffdir;

import com.tongyu.log4j2.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * Created by relax on 2018/1/25.
 */
public class HelloWorld {
    private static final Logger logger = LogManager.getLogger(HelloWorld.class.getName());
    public static void main(String[] args) {
        logger.info("Hello, World!");

        User user = new User("zhangsan", "1998-12-12 03:23:00");
        /**
         * declared
         * 重复这样做的结果是让代码感觉像是关于日志记录，而不是实际的任务。另外，它会导致记录级别被检查两次;一次调用isDebugEnabled，一次调试方法。更好的选择是：
         */
        if (logger.isDebugEnabled()) {
            logger.debug("Logging in user " + user.getName() + " with birthday " + user.getBirthdayCalendar());
        }

        //实际开发中应该使用下面的方式
        logger.debug("Logging in user {} with birthday {}", user.getName(), user.getBirthdayCalendar());

    }
}
