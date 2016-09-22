package com.jxlx.jayden.zk;

import com.jxlx.jayden.common.Constant;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * User : jianjun.xu
 * Date : 2016/9/22
 * Time : 14:26
 * Desc : 创建一个最基本的Zookeeper会话实例
 */
public class Zookeeper_Constructor_Usage_Simple implements Watcher {

	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

	public static void main(String[] args) throws IOException {
		ZooKeeper zooKeeper = new ZooKeeper(Constant.LOCAL_ZK_ADDRESS, Constant.DEFAULT_ZK_SESSION_TIMEOUT, new Zookeeper_Constructor_Usage_Simple());
		System.out.println("zookeeper state:" + zooKeeper.getState());
		try {
			connectedSemaphore.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Zookeeper session established.");
	}

	@Override
	public void process(WatchedEvent event) {
		System.out.println("Receive watched event:" + event);
		if (Event.KeeperState.SyncConnected == event.getState()) {
			connectedSemaphore.countDown();
		}
	}
}
