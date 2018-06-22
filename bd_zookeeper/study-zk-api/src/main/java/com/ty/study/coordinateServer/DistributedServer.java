package com.ty.study.coordinateServer;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class DistributedServer {
	private static final String CONNECT_STRING = "mini1:2181,mini2:2181,mini3:2181";
	private static final int SESSION_TIME_OUT = 2000;
	private static final String PARENT_NODE = "/servers";

	private ZooKeeper zk = null;
	
	CountDownLatch countDownLatch = new CountDownLatch(1);

	/**
	 * 创建到zk的客户端连接
	 * 
	 * @throws Exception
	 */
	public void getConnect() throws Exception {

		zk = new ZooKeeper(CONNECT_STRING, SESSION_TIME_OUT, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				if(event.getState()==KeeperState.SyncConnected){
					countDownLatch.countDown();
				}
			}
		});
		countDownLatch.await();

	}

	/**
	 * 向zk集群注册服务器信息
	 * 
	 * @param hostname
	 * @throws Exception
	 */
	public void registerServer(String hostname) throws Exception {
		Stat exists = zk.exists(PARENT_NODE, false);
		if(exists==null) zk.create(PARENT_NODE, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		String path = zk.create(PARENT_NODE + "/server", hostname.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println(hostname + "is online.." + path);

	}

	/**
	 * 业务功能
	 * 
	 * @throws InterruptedException
	 */
	public void handleBussiness(String hostname) throws InterruptedException {
		System.out.println(hostname + "start working.....");
		Thread.sleep(Long.MAX_VALUE);
	}

	public static void main(String[] args) throws Exception {

		DistributedServer server = new DistributedServer();
		// 获取zk连接
		server.getConnect();

		// 利用zk连接注册服务器信息(主机名)
		server.registerServer(args[0]);

		// 启动业务功能
		server.handleBussiness(args[0]);

	}

}
