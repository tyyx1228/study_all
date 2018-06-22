package com.ty.study.client;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class TestZKclient {
	static ZooKeeper zk = null;

	public static void main(String[] args) throws Exception {
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		zk = new ZooKeeper("mini1:2181", 2000, new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				if (event.getState() == KeeperState.SyncConnected) {

					countDownLatch.countDown();

				}
				System.out.println(event.getPath());
				System.out.println(event.getType());
				try {
					zk.getChildren("/myboys", true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		countDownLatch.await();

		/*
		 * zk.create("/myboys", "丑陋型".getBytes("UTF-8"), Ids.OPEN_ACL_UNSAFE,
		 * CreateMode.PERSISTENT); zk.close();
		 */

		/*
		 * byte[] data = zk.getData("/myboys", true, null);
		 * System.out.println(new String(data,"UTF-8"));
		 * 
		 * Thread.sleep(Long.MAX_VALUE);
		 */

		/*List<String> children = zk.getChildren("/myboys", true);
		for (String child : children) {

			System.out.println(child);
		}*/
		
		
		/*zk.delete("/myboys/wangkai", -1);*/
		
		/*zk.setData("/myboys","sldakfjsd".getBytes(),-1);*/
		
		
		Stat stat = zk.exists("/mywives", true);
		System.out.println(stat==null?"确实不存在":"存在");
		
		zk.close();
		
	}

}
