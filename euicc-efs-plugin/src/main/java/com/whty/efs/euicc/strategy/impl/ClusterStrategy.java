package com.whty.efs.euicc.strategy.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.cache.CacheUtil;
import com.whty.efs.common.constant.Constant;
import com.whty.efs.euicc.strategy.RouterStrategy;
import com.whty.efs.packets.message.EuiccEntity;

/**
 * TSM集群路由实现
 */
public class ClusterStrategy implements RouterStrategy {

	private static final Logger logger = LoggerFactory
			.getLogger(ClusterStrategy.class);
	
	private static final boolean isDebugEabled = logger.isDebugEnabled();
	
	private int expiredTime = 1800;

	private String[] urls = null;

	@SuppressWarnings("rawtypes")
	@Override
	public String getURL(EuiccEntity tsmEntity ) {
		String tradeRefNo = String.valueOf(tsmEntity.getHeader().getTradeRefNO());
		String router = CacheUtil
				.getString(Constant.Plugin.ROUTER_PREFIX + tradeRefNo);
		if (router == null) {// 缓存获取不到  新交易
			// 随机分配
			router = urls[(int) (Math.random() * urls.length)];
			CacheUtil.put(Constant.Plugin.ROUTER_PREFIX + tradeRefNo, router,
					expiredTime);
		}
		if(isDebugEabled)
			logger.debug("router to {}",router);
		return router;
	}

	public void setUrls(String urls) throws Exception {
		this.urls = urls.split(",");
		if (urls.length() == 0) {
			throw new Exception("路由配置报错");
		}
	}

	public void setExpiredTime(int expiredTime) {
		this.expiredTime = expiredTime;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getYxptUrl(EuiccEntity tsmEntity) {
		
		return null;
	}
}
