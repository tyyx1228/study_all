package com.ty.study.mq.topic;

import javax.jms.JMSException;

public class ConsumerTest implements Runnable {
	static Thread t1 = null;
	static Thread t2 = null;

	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws InterruptedException
	 * @throws JMSException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		t1 = new Thread(new ConsumerTest());
		t1.setDaemon(false);
		t1.start();
		
		t2 = new Thread(new ConsumerTest());
		t2.setDaemon(false);
		t2.start();

		/*
		 * 如果发生异常，则重启consumer
		 * while (true) { System.out.println(t1.isAlive()); if (!t1.isAlive()) {
		 * t1 = new Thread(new ConsumerTest()); t1.start();
		 * System.out.println("重新启动"); } Thread.sleep(5000); } // 延时500毫秒之后停止接受消息
		 * Thread.sleep(500); consumer.close();
		 */
	}

	public void run() {
		try {
			ConsumerTool consumer = new ConsumerTool();
			consumer.consumeMessage();
			while (ConsumerTool.isconnection) {	
			}
		} catch (Exception e) {
		}

	}
}
