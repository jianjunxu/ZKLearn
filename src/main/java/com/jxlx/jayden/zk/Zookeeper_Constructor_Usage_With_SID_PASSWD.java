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
 * Time : 14:45
 * Desc : 客户端传入sessionID和password的目的是为了复用会话，以维持之前会话的有效性
 */
public class Zookeeper_Constructor_Usage_With_SID_PASSWD implements Watcher {
	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

	public static void main(String[] args) throws IOException, InterruptedException {
		ZooKeeper zooKeeper = new ZooKeeper(Constant.LOCAL_ZK_ADDRESS, Constant.DEFAULT_ZK_SESSION_TIMEOUT, new Zookeeper_Constructor_Usage_With_SID_PASSWD());
		long sessionId = zooKeeper.getSessionId();
		byte[] sessionPasswd = zooKeeper.getSessionPasswd();
		System.out.println("sessionId:" + sessionId + ", sessionPasswd:" + sessionPasswd);
		try {
			connectedSemaphore.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/** Use illegal sid and spw */
		zooKeeper = new ZooKeeper(Constant.LOCAL_ZK_ADDRESS, 5000, new Zookeeper_Constructor_Usage_With_SID_PASSWD(), 123L, "tt".getBytes());
		/** Use correct sid and spw */
		zooKeeper = new ZooKeeper(Constant.LOCAL_ZK_ADDRESS, 5000, new Zookeeper_Constructor_Usage_With_SID_PASSWD(), sessionId, sessionPasswd);
		Thread.sleep(Integer.MAX_VALUE);
	}

	@Override
	public void process(WatchedEvent event) {
		System.out.println("Receive watched event:" + event);
		if (Event.KeeperState.SyncConnected == event.getState()) {
			connectedSemaphore.countDown();
		}
	}
}
