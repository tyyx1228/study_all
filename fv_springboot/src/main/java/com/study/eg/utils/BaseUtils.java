package com.study.eg.utils;


import com.sun.corba.se.impl.ior.OldJIDLObjectKeyTemplate;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 用于提供基础代码支持
 *
 * @author relax tongyu
 * @create 2018-02-09 15:58
 **/
public class BaseUtils {
    protected static Logger log;

    public BaseUtils() {
        log = LogManager.getLogger(this.getClass());
    }


}
