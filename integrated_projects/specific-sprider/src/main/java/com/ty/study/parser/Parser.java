package com.ty.study.parser;

import com.ty.study.model.Page;

import java.util.List;

/**
 * 页面解析器
 *
 * @author relax tongyu
 * @create 2018-06-08 11:16
 **/
public interface Parser<T> {

    T parse(Page page);

    List<T> parse(List<Page> pages);

}
