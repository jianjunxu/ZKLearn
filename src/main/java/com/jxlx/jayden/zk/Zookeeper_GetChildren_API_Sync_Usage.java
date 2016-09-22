package com.jxlx.jayden.zk;

import com.jxlx.jayden.common.Constant;
import org.apache.zookeeper.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * User : jianjun.xu
 * Date : 2016/9/22
 * Time : 15:54
 * Desc : 1 创建节点 子节点 2 获取子节点 3 创建另一个子节点 4 Watcher 重新获取节点
 */
public class Zookeeper_GetChildren_API_Sync_Usage implements Watcher {

	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	private static String path = "/zk-book";
	private static ZooKeeper zooKeeper = null;

	public static void main(String[] args) throws Exception {
		zooKeeper = new ZooKeeper(Constant.LOCAL_ZK_ADDRESS, Constant.DEFAULT_ZK_SESSION_TIMEOUT, new Zookeeper_GetChildren_API_Sync_Usage());
		connectedSemaphore.await();
		zooKeeper.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
		zooKeeper.create(path + "/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		List<String> childNodeList = zooKeeper.getChildren(path, true);
		System.out.println(childNodeList);
		zooKeeper.create(path + "/c2", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		Thread.sleep(Integer.MAX_VALUE);
	}

	@Override
	public void process(WatchedEvent event) {
		System.out.println("Receive watched event:" + event);
		if (Event.KeeperState.SyncConnected == event.getState()) {
			if (Event.EventType.None == event.getType() && null == event.getPath()) {
				connectedSemaphore.countDown();
			} else if (event.getType() == Event.EventType.NodeChildrenChanged) {
				try {
					System.out.println("ReGet child:" + zooKeeper.getChildren(event.getPath(), true));
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
