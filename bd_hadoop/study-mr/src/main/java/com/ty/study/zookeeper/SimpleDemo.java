package cn.itcast.bigdata.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class SimpleDemo {
	// 会话超时时间，设置为与系统默认时间一致
	private static final int SESSION_TIMEOUT = 2000;
	// 创建 ZooKeeper 实例
	ZooKeeper zk;
	// 创建 监听器Watcher对象
	Watcher wh = new Watcher() {
		//监听器的回调方法
		public void process(WatchedEvent event)
		{
			System.out.println(event.toString());
		}
	};
	// 初始化 ZooKeeper 实例
	private void createZKInstance() throws IOException
	{
		//创建一个zookeeper的连接客户端
		zk = new ZooKeeper("hadoop05:2181,hadoop06:2181,hadoop07:2181", SimpleDemo.SESSION_TIMEOUT, this.wh);
	}
	
	//zookeeper客户端的基本API操作demo
	private void ZKOperations() throws IOException, InterruptedException, KeeperException
	{
		System.out.println("/n1. 创建 ZooKeeper 节点 (znode ： zoo2, 数据： myData2 ，权限： OPEN_ACL_UNSAFE ，节点类型： Persistent");
		
		//如果是需要把配置文件交给zookeeper保存，可以先读取配置文件
//		String confString = FileUtils.readFileToString(new File("c:/xx.xml"), "utf-8");
		
		zk.create("/zoo2", "myData2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.create("/zoo2/child", "myData2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		
		
		System.out.println("/n2. 查看是否创建成功： ");
		//判断stat是否为null，就可以知道/zoo2是否存在
//		Stat stat = zk.exists("/zoo2", null);
		System.out.println(new String(zk.getData("/zoo2", false, null)));
		
		
		System.out.println("/n3. 修改节点数据 ");
		//注意参数3：version，  为-1时，表示修改所有版本
		zk.setData("/zoo2", "shenlan211314".getBytes(), -1);
		System.out.println("/n4. 查看是否修改成功： ");
		System.out.println(new String(zk.getData("/zoo2", false, null)));
		
		System.out.println("/n5. 删除节点 ");
		zk.delete("/zoo2", -1);
		System.out.println("/n6. 查看节点是否被删除： ");
		System.out.println(" 节点状态： [" + zk.exists("/zoo2", false) + "]");
	}
	private void ZKClose() throws InterruptedException
	{
		zk.close();
	}
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		SimpleDemo dm = new SimpleDemo();
		dm.createZKInstance();
		dm.ZKOperations();
		dm.ZKClose();
	}
}

	
	
	
	
	


