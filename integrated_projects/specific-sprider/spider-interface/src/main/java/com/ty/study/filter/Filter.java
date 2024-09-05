package com.ty.study.filter;

/**
 * 筛选元素
 *
 * @author relax tongyu
 * @create 2018-06-08 10:59
 **/
public interface Filter<T> {

    boolean filter(T t);

}
