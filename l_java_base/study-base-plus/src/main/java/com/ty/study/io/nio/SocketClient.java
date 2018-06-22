package com.ty.study.io.nio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketClient implements Runnable{

	int clientNbr = 0;
	
	@Override
	public void run() {
		try {
			Socket sc = new Socket("localhost", 8383);
			OutputStream os = sc.getOutputStream();
			InputStream in = sc.getInputStream();
			os.write((clientNbr+"").getBytes());
			os.flush();
			byte[] b = new byte[1024];
			int read = in.read(b, 0, 1024);
			System.out.println("from server:" +read+" bytes-->"+ new String(b));
			
			sc.shutdownOutput();
			sc.shutdownInput();
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public static void main(String[] args) {
		new Thread(new SocketClient()).start();
	}

}
