package com.jxlx.jayden.zk;

import org.apache.zookeeper.AsyncCallback;

/**
 * 异步创建node节点需要实现AsyncCallback接口
 * rc - 错误码（0成功 -4客户端与服务端连接断开 -110指定节点已存在 -112会话已过期）
 */
class IStringCallback implements AsyncCallback.StringCallback {
	@Override
	public void processResult(int rc, String path, Object ctx, String name) {
		System.out.println("Create path result:[" + rc + "," + path + "," + ctx + "," + name + "]");
	}
}