package com.ty.study.parser;

import com.ty.study.model.Page;

import java.util.List;

/**
 * 页面解析器
 *
 * @author relax tongyu
 * @create 2018-06-08 11:16
 **/
public interface Parser<SOURCE, RESULT> {

    RESULT parse(SOURCE source);

    List<RESULT> parse(List<SOURCE> sources);

}
