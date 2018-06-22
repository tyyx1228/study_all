package com.tongyu.log4j.ext;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Priority;

public class MyConsoleAppender extends ConsoleAppender {
    @Override
    public boolean isAsSevereAsThreshold(Priority priority) {
        return getThreshold().equals(priority);
    }
}
