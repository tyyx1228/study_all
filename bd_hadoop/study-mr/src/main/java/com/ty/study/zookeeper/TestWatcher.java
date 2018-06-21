package cn.itcast.bigdata.zookeeper;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 对一个数据节点注册监听，只会生效一次
 * 注册监听是在获取数据的操作中实现
 * 如果要持续监听，则必须在每次监听事件发生后的回调处理中重新注册
 * @author duanhaitao@itcast.cn
 *
 */
public class TestWatcher {
	
	
	private ZooKeeper zk = null;
	
	
	public void connectZK() throws Exception{
		
		//创建客户端zk连接对象
		zk = new ZooKeeper("hadoop05:2181", 30000, new Watcher() {
			
			@Override
			public void process(WatchedEvent event) {
				try {
					doSomeThing(event);
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}


		});
		
		//获取test节点数据，并注册监听
		byte[] data = zk.getData("/test",true,new Stat());
		//表示程序的业务逻辑
		System.out.println(new String(data));
	}
	
	private void doSomeThing(WatchedEvent event) throws Exception {
		//业务逻辑
		System.out.println(event.getType());
		System.out.println(event.getPath());
		
		//重新注册监听； getData注册的监听器，监听数据更新事件
		byte[] data = zk.getData("/test",true,new Stat());
		System.out.println(new String(data));
		
		
		//getChildren注册的监听器，监听子节点变化事件
		List<String> children = zk.getChildren("/test", true);
		for(String c:children){
			System.out.println(c);
		}
		
		
	}
	
	
	public static void main(String[] args) throws Exception {
		TestWatcher testWatcher = new TestWatcher();
		testWatcher.connectZK();
		System.out.println("-----------");
		Thread.sleep(Long.MAX_VALUE);
	}
	
	
	

}
