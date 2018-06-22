package com.ty.study.main;

import com.ty.study.factory.ShapeFactory;
import com.ty.study.domain.Shape;
import com.ty.study.domain.impl.Circle;
import com.ty.study.domain.impl.Rectangle;
import com.ty.study.domain.impl.Square;

/**
 * 工厂设计模式主程序入口
 *
 * @author relax tongyu
 * @create 2018-01-28 21:53
 **/
public class FactoryPatternDemo {

    public static void main(String[] args) {
        mode2();
        mode2();
    }


    public static void mode1(){

        //获取 Circle 的对象，并调用它的 draw 方法
        Shape shape1 = ShapeFactory.getShape("CIRCLE");
        //调用 Circle 的 draw 方法
        shape1.draw();

        //获取 Rectangle 的对象，并调用它的 draw 方法
        Shape shape2 = ShapeFactory.getShape("RECTANGLE");
        //调用 Rectangle 的 draw 方法
        shape2.draw();

        //获取 Square 的对象，并调用它的 draw 方法
        Shape shape3 = ShapeFactory.getShape("SQUARE");
        //调用 Square 的 draw 方法
        shape3.draw();
    }

    public static void mode2(){

        Shape shape1 = ShapeFactory.getShape(Circle.class);
        shape1.draw();

        Shape shape2 = ShapeFactory.getShape(Rectangle.class);
        shape2.draw();

        Square shape3 = ShapeFactory.getShape(Square.class);
        shape3.draw();
    }


}
