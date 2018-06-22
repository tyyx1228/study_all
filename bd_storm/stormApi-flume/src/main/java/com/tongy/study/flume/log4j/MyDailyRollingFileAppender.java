package com.tongy.study.flume.log4j;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Priority;

public class MyDailyRollingFileAppender extends DailyRollingFileAppender
{
  public boolean isAsSevereAsThreshold(Priority priority)
  {
    return getThreshold().equals(priority);
  }
}
