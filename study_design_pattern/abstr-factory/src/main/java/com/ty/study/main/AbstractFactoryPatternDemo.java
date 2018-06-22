package com.ty.study.main;

import com.ty.study.domain.impl.*;
import com.ty.study.factory.AbstractFactory;
import com.ty.study.factory.ColorFactory;
import com.ty.study.factory.FactoryProducer;
import com.ty.study.factory.ShapeFactory;
import com.ty.study.domain.Color;
import com.ty.study.domain.Shape;

/**
 * 抽象工厂模式主程序入口
 *
 * @author relax tongyu
 * @create 2018-01-28 22:52
 **/
public class AbstractFactoryPatternDemo {
    //形状工厂
    private static AbstractFactory shapeFactory;

    //颜色工厂
    private static AbstractFactory colorFactory;

    //抽象工厂初始化
    static {
        shapeFactory = FactoryProducer.getFactory(ShapeFactory.class);
        colorFactory = FactoryProducer.getFactory(ColorFactory.class);
    }

    public void testAllShape() throws NoSuchMethodException {
        //获取形状为 Circle 的对象
        Shape shape1 = shapeFactory.getShape(Circle.class);
        //调用 Circle 的 draw 方法
        shape1.draw();

        //获取形状为 Rectangle 的对象
        Shape shape2 = shapeFactory.getShape(Rectangle.class);
        //调用 Rectangle 的 draw 方法
        shape2.draw();

        //获取形状为 Square 的对象
        Shape shape3 = shapeFactory.getShape(Square.class);
        //调用 Square 的 draw 方法
        shape3.draw();
    }

    public void testAllColor() throws NoSuchMethodException{
        //获取颜色为 Red 的对象
        Color color1 = colorFactory.getColor(Red.class);
        //调用 Red 的 fill 方法
        color1.fill();

        //获取颜色为 Green 的对象
        Color color2 = colorFactory.getColor(Green.class);
        //调用 Green 的 fill 方法
        color2.fill();

        //获取颜色为 Blue 的对象
        Color color3 = colorFactory.getColor(Blue.class);
        //调用 Blue 的 fill 方法
        color3.fill();
    }



    public static void main(String[] args) throws NoSuchMethodException{
        AbstractFactoryPatternDemo abstractFactoryPatternDemo = new AbstractFactoryPatternDemo();

        System.out.println(" ****************** Abstract Factory Patten: Start to test get color ******************");
        abstractFactoryPatternDemo.testAllColor();

        System.out.println("\n****************** Abstract Factory Patten: Start to test get shape ******************");
        abstractFactoryPatternDemo.testAllShape();
    }
}
