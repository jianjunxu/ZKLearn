package com.jxlx.jayden.zk;

import com.alibaba.fastjson.JSON;
import com.jxlx.jayden.common.Constant;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * User : jianjun.xu
 * Date : 2016/9/22
 * Time : 16:55
 * Desc :
 */
public class Zookeeper_Exist_API_Sync_Usage implements Watcher {
	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	private static ZooKeeper zooKeeper = null;
	private static String path = "/zk-book";

	public static void main(String[] args) throws Exception {
		zooKeeper = new ZooKeeper(Constant.LOCAL_ZK_ADDRESS, Constant.DEFAULT_ZK_SESSION_TIMEOUT, new Zookeeper_Exist_API_Sync_Usage());
		connectedSemaphore.await();
		Stat stat = zooKeeper.exists(path, true);
		System.out.println(JSON.toJSONString(stat));
	}

	@Override
	public void process(WatchedEvent event) {
		if (Event.KeeperState.SyncConnected == event.getState()) {
			System.out.println("Receive watched event:" + event);
			connectedSemaphore.countDown();
		}
	}
}
