package com.whty.efs.euicc.strategy;

import com.whty.efs.packets.message.EuiccEntity;

/**
 * 路由策略接口
 */
public interface RouterStrategy {

	public String getURL(@SuppressWarnings("rawtypes") EuiccEntity tsmEntity);

	public String getYxptUrl(@SuppressWarnings("rawtypes") EuiccEntity tsmEntity);
}
