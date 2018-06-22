package com.ty.study.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class NioServer {
	private ByteBuffer readBuffer;
	private Selector selector;

	public static void main(String[] args) {
		NioServer server = new NioServer();
		server.init();
		System.out.println("server started --> 8383");
		server.listen();
	}

	private void init() {
		readBuffer = ByteBuffer.allocate(1024);
		ServerSocketChannel servSocketChannel;

		try {
			// 创建一个socket channel； channel是nio中对通信通道的抽象，不分入站出站方向
			servSocketChannel = ServerSocketChannel.open();
			// 设置通道为非阻塞的方式
			servSocketChannel.configureBlocking(false);
			// 将通道绑定在服务器的ip地址和某个端口上
			servSocketChannel.socket().bind(new InetSocketAddress(8383));

			// 打开一个多路复用器
			selector = Selector.open();
			// 将上面创建好的socket channel注册到selector多路复用器上
			// 对于服务端来说，一定要先注册一个OP_ACCEPT事件用来响应客户端的连接请求
			servSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void listen() {
		while (true) {
			try {
				// 去询问一次selector选择器
				selector.select();
				Iterator<SelectionKey> ite = selector.selectedKeys().iterator();

				while (ite.hasNext()) {
					// 遍历到一个事件key
					SelectionKey key = ite.next();
					ite.remove(); // 确保不重复处理
					// 处理事件
					handleKey(key);
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	private void handleKey(SelectionKey key) throws IOException, ClosedChannelException {
		SocketChannel channel = null;

		try {
			if (key.isAcceptable()) {
				ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
				channel = serverChannel.accept();// 接受连接请求
				channel.configureBlocking(false);
				channel.register(selector, SelectionKey.OP_READ);
			} else if (key.isReadable()) {
				channel = (SocketChannel) key.channel();
				// 先清空一下buffer
				readBuffer.clear();
				/*
				 * 当客户端channel关闭后，会不断收到read事件，但没有消息，即read方法返回-1
				 * 所以这时服务器端也需要关闭channel，避免无限无效的处理
				 */
				int count = channel.read(readBuffer);

				if (count > 0) {
					// 一定需要调用flip函数，否则读取错误数据
					// 简单来说，flip操作就是让读写指针、limit指针复位到正确的位置
					readBuffer.flip();
					/*
					 * 使用CharBuffer配合取出正确的数据;
					 * String question = new String(readBuffer.array());可能会出错，
					 * 因为前面readBuffer.clear();并未真正清理数据 只是重置缓冲区的position,
					 * limit, mark， 而readBuffer.array()会返回整个缓冲区的内容。
					 * decode方法只取readBuffer的position到limit数据。
					 * 例如，上一次读取到缓冲区的是"where", clear后position为0，limit为 1024，
					 * 再次读取“bye"到缓冲区后，position为3，limit不变，
					 * flip后position为0，limit为3，前三个字符被覆盖了，但"re"还存在缓冲区中， 所以 new
					 * String(readBuffer.array()) 返回 "byere",
					 * 而decode(readBuffer)返回"bye"。
					 */
					CharBuffer charBuffer = CharsetHelper.decode(readBuffer);
					String question = charBuffer.toString();
					// 根据客户端的请求，调用相应的业务方法获取业务结果
					String answer = getAnswer(question);
					channel.write(CharsetHelper.encode(CharBuffer.wrap(answer)));
				} else {
					// 这里关闭channel，因为客户端已经关闭channel或者异常了
					channel.close();
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
			if (channel != null) {
				channel.close();
			}
		}
	}

	private String getAnswer(String question) {
		String answer = null;

		switch (question) {
		case "who":
			answer = "我是凤姐\n";
			break;
		case "what":
			answer = "我是来帮你解闷的\n";
			break;
		case "where":
			answer = "我来自外太空\n";
			break;
		case "hi":
			answer = "hello\n";
			break;
		case "bye":
			answer = "88\n";
			break;
		default:
			answer = "请输入 who， 或者what， 或者where";
		}

		return answer;
	}
}