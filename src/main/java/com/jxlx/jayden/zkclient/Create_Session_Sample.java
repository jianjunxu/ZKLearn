package com.jxlx.jayden.zkclient;

import com.alibaba.fastjson.JSON;
import com.jxlx.jayden.common.Constant;
import org.I0Itec.zkclient.ZkClient;

/**
 * User : jianjun.xu
 * Date : 2016/9/23
 * Time : 9:51
 * Desc :
 */
public class Create_Session_Sample {
	public static void main(String[] args) {
		ZkClient zkClient = new ZkClient(Constant.LOCAL_ZK_ADDRESS, Constant.DEFAULT_ZK_SESSION_TIMEOUT);
		System.out.println("Zookeeper session established.zkClient" + JSON.toJSONString(zkClient));
	}
}
