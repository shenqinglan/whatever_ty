package com.whty.efs.euicc.listener;

import org.springframework.stereotype.Service;

import com.whty.cache.CacheUtil;
import com.whty.efs.data.pojo.Router;
import com.whty.efs.common.constant.Constant;

/**
 * 路由容器
 */
@Service
public class RouterContainer {

	public void addRouter(String receiver, Router router) {
		receiver = Constant.Plugin.ROUTER_INFO + receiver;
		CacheUtil.put(receiver, router);
		CacheUtil.persist(receiver);
	}

	public Router getRouter(String receiver) {
		receiver = Constant.Plugin.ROUTER_INFO + receiver;
		return CacheUtil.get(receiver, Router.class);
	}

	public void addAccessSender(String receiver, Boolean isTrue) {
		receiver = Constant.Plugin.ACCESS_SENDER + receiver;
		CacheUtil.put(receiver, String.valueOf(isTrue));
		CacheUtil.persist(receiver);
	}

	public boolean getAccessSender(String receiver) {
		receiver = Constant.Plugin.ACCESS_SENDER + receiver;
		return CacheUtil.get(receiver, Boolean.class);
	}
}
