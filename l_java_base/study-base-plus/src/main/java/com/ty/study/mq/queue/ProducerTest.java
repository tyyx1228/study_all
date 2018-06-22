package com.ty.study.mq.queue;

import javax.jms.JMSException;

public class ProducerTest {

	/**
	 * @param args
	 * @throws Exception
	 * @throws JMSException
	 */
	public static void main(String[] args) throws JMSException, Exception {
		ProducerTool producer = new ProducerTool();
		for (int i = 30; i < 40; i++) {
			producer.produceMessage("Hello, world!"+i);
		}
		producer.close();
	}
}
