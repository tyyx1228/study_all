package com.ty.study.optional;

import java.util.Optional;

/**
 * 测试java.util包中类：Optional
 *
 * @author relax tongyu
 * @create 2018-03-01 16:57
 **/
public class TestOptional {

    public static Optional<Man> getObject(int sign){
        return sign==1 ? Optional.of(new Man("校长")) : Optional.empty();
    }

    public static void testOne(){
        Optional<Man> man = getObject(1);
        System.out.println(man.isPresent());
    }

    public static void testNotOne(){
        Optional<Man> man = getObject(0);
        System.out.println(man.isPresent());
    }

    public static void main(String[] args) {
        testOne();
        testNotOne();
    }
}

class Man  {
    private String name;

    public Man(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}