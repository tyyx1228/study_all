package com.tongyu.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;

/**
 *
 * Created by relax on 2018/1/25.
 */
public class UserLogTest {
    public static Logger logger = LogManager.getLogger(UserLogTest.class.getName());

    public static void main(String[] args) {
        User user = new User("zhangsan", "1998-12-12 03:23:00");
        logger.debug("Logging in user {} with birthday {}", user.getName(), user.getBirthdayCalendar());

    }

}
