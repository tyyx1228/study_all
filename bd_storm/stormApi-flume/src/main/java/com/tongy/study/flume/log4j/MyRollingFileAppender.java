package com.tongy.study.flume.log4j;

import org.apache.log4j.Priority;
import org.apache.log4j.RollingFileAppender;


public class MyRollingFileAppender extends RollingFileAppender
{
    public boolean isAsSevereAsThreshold(Priority priority)
    {
        return getThreshold().equals(priority);
    }
}
