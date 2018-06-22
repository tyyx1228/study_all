package com.tongyu.log4j2;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by relax on 2018/1/24.
 */
public class FormatLogger {
    private static final Logger logger = LogManager.getFormatterLogger(FormatLogger.class.getName());

    public void demoExtendedLogger() {
        // ...
        logger.trace("the built-in TRACE level");
        logger.debug("the built-in DEBUG level");
        logger.info("the built-in INFO level");
        logger.warn("the built-in WARN level");
        logger.error("the built-in ERROR level");
        logger.fatal("the built-in FATAL level");
        // ...
    }

    public static void main(String[] args) {
        FormatLogger lt = new FormatLogger();
        lt.demoExtendedLogger();

        User user = new User("tong yu", "2018-01-12 00:12:50");
        logger.debug("Logging in user %s with birthday %s", user.getName(), user.getBirthdayCalendar());
        logger.debug("Logging in user %1$s with birthday %2$tm %2$te,%2$tY", user.getName(), Timestamp.valueOf(user.getBirthdayCalendar()));
        logger.debug("Integer.MAX_VALUE = %,d", Integer.MAX_VALUE);
        logger.debug("Long.MAX_VALUE = %,d", Long.MAX_VALUE);



        logger.info(Calendar.getInstance());

        logger.printf(Level.INFO, "Logging in user %s with birthday %2$tm %2$te,%2$tY", user.getName(), Timestamp.valueOf(user.getBirthdayCalendar()));
    }
}
