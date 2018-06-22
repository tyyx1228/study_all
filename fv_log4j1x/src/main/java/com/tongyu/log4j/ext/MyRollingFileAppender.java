package com.tongyu.log4j.ext;

import org.apache.log4j.Priority;
import org.apache.log4j.RollingFileAppender;


public class MyRollingFileAppender extends RollingFileAppender
{
    @Override
    public boolean isAsSevereAsThreshold(Priority priority)
    {
        return getThreshold().equals(priority);
    }
}
