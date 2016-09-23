package com.jxlx.jayden.zkclient;

import com.jxlx.jayden.common.Constant;
import org.I0Itec.zkclient.ZkClient;

/**
 * User : jianjun.xu
 * Date : 2016/9/23
 * Time : 9:57
 * Desc :
 */
public class Create_Node_Sample {
	public static void main(String[] args) throws InterruptedException {
		ZkClient zkClient = new ZkClient(Constant.LOCAL_ZK_ADDRESS, Constant.DEFAULT_ZK_SESSION_TIMEOUT);
		zkClient.createEphemeral("/zkclient-test");
		Thread.sleep(Integer.MAX_VALUE);
	}
}
