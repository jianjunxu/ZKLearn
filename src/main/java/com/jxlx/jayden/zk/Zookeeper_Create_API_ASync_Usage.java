package com.jxlx.jayden.zk;

import com.jxlx.jayden.common.Constant;
import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * User : jianjun.xu
 * Date : 2016/9/22
 * Time : 15:18
 * Desc : zk api创建节点  使用异步
 */
public class Zookeeper_Create_API_ASync_Usage implements Watcher {
	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

	private static String path = "/zk_test_ephemeral-";

	public static void main(String[] args) throws Exception {
		ZooKeeper zooKeeper = new ZooKeeper(Constant.LOCAL_ZK_ADDRESS,
				Constant.DEFAULT_ZK_SESSION_TIMEOUT,
				new Zookeeper_Create_API_ASync_Usage()
		);
		connectedSemaphore.await();
		zooKeeper.create(path, "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new IStringCallback(), "ctx");
		zooKeeper.create(path, "test2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, new IStringCallback(), "ctx");
		Thread.sleep(Integer.MAX_VALUE);
	}

	@Override
	public void process(WatchedEvent event) {
		System.out.println("Receive watched event:" + event);
		if (Watcher.Event.KeeperState.SyncConnected == event.getState()) {
			connectedSemaphore.countDown();
		}
	}
}