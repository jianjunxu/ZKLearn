package com.jxlx.jayden.zk;

import com.alibaba.fastjson.JSON;
import com.jxlx.jayden.common.Constant;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * User : jianjun.xu
 * Date : 2016/9/22
 * Time : 16:19
 * Desc : 同步获取节点内容
 */
public class Zookeeper_GetData_API_Sync_Usage implements Watcher {

	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	private static String path = "/zk-book-1";
	private static ZooKeeper zooKeeper = null;
	private static Stat stat = new Stat();

	public static void main(String[] args) throws Exception {
		zooKeeper = new ZooKeeper(Constant.LOCAL_ZK_ADDRESS, Constant.DEFAULT_ZK_SESSION_TIMEOUT, new Zookeeper_GetData_API_Sync_Usage());
		connectedSemaphore.await();
		zooKeeper.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		System.out.println("data:"+new String(zooKeeper.getData(path, true, stat)));
		System.out.println("stat:"+ JSON.toJSONString(stat));
		/** modify data */
		zooKeeper.setData(path,"456".getBytes(), -1);
		Thread.sleep(Integer.MAX_VALUE);
	}

	@Override
	public void process(WatchedEvent event) {
		System.out.println("Receive watched event:" + event);
		if (Event.KeeperState.SyncConnected == event.getState()) {
			if (Event.EventType.None == event.getType() && null == event.getPath()) {
				connectedSemaphore.countDown();
			} else if (event.getType() == Event.EventType.NodeDataChanged) {
				try {
					System.out.println("ReGet data:" + new String(zooKeeper.getData(path, true, stat)));
					System.out.println("stat:"+ JSON.toJSONString(stat));
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
