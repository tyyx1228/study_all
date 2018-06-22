package com.ty.study.mySerializable;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * 序列化对象到硬盘
 * @author coderblack
 *
 */
public class SerTask {

	public static void main(String[] args) throws Exception {
		Task t = new Task();
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("c://tasks"));
		oos.writeObject(t);
		oos.close();
	}
}
