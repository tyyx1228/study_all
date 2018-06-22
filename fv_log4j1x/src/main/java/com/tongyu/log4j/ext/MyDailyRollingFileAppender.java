package com.tongyu.log4j.ext;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Priority;

public class MyDailyRollingFileAppender extends DailyRollingFileAppender
{
  @Override
  public boolean isAsSevereAsThreshold(Priority priority)
  {
    return getThreshold().equals(priority);
  }

  @Override
  public void setLayout(Layout layout) {

  }
}
