package com.ty.study.printfile;

import java.io.File;

/**
 * 批量生成目录
 */
public class CreateDirctory {
    public static void main(String[] args) {
        File file = new File("E:\\maoxiangyi\\课件\\storm\\实时课程");
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for (File f:files){
                File[] secondFiles = f.listFiles();
                for (File second:secondFiles){
                    String root = second.getAbsolutePath();
                    mkdir(root,"PPT");
                    mkdir(root,"讲义");
                    mkdir(root,"视频");
                    mkdir(root,"安装文档");
                    mkdir(root,"安装软件");
                    mkdir(root,"代码");
                }
            }
        }
    }

    private static void mkdir(String root, String dirctoryName) {
        File f1 = new File(root+"//"+dirctoryName);
        if (f1.exists()){
            f1.delete();
        }
        f1.mkdir();
    }
}
