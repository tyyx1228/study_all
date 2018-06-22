package com.ty.study.printfile;

import java.io.File;

/**
 * Created by maoxiangyi on 2016/6/10.
 */
public class unZipCodePackage {
    public static void main(String[] args) {
        File file = new File("E:\\maoxiangyi\\课件\\storm\\实时课程");
        printFile(file);
    }

    public static void printFile(File file) {
        if (file.isDirectory()) {
            String name = file.getAbsolutePath().toString();
            if (name.contains("代码")) {
                System.out.println(name);
            }
            File[] files = file.listFiles();
            for (File f : files) {
                printFile(f);
            }
        }
    }
}
