package com.ty.study.factory;

import com.ty.study.domain.Shape;
import com.ty.study.domain.impl.Circle;
import com.ty.study.domain.impl.Rectangle;
import com.ty.study.domain.impl.Square;

/**
 * 图形工厂
 *
 * @author relax tongyu
 * @create 2018-01-28 21:50
 **/
public class ShapeFactory {
    //使用 getShape 方法获取形状类型的对象
    public static Shape getShape(String shapeType){
        if(shapeType == null){
            return null;
        }
        if(shapeType.equalsIgnoreCase("CIRCLE")){
            return new Circle();
        } else if(shapeType.equalsIgnoreCase("RECTANGLE")){
            return new Rectangle();
        } else if(shapeType.equalsIgnoreCase("SQUARE")){
            return new Square();
        }
        return null;
    }

    public static <T> T getShape(Class<T> clazz) {
        T obj = null;
        try {
            obj = (T) Class.forName(clazz.getName()).newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }

}
