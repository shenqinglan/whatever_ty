package com.whty.efs.webservice.service;

import com.whty.efs.packets.message.EuiccEntity;
import com.whty.efs.packets.message.request.AppApplyBody;
import com.whty.efs.packets.message.request.AppPersonialBody;
import com.whty.efs.packets.message.request.AppQueryBody;
import com.whty.efs.packets.message.response.AppApplyResp;
import com.whty.efs.packets.message.response.AppPersonialResp;
import com.whty.efs.packets.message.response.AppQueryResp;
import com.whty.efs.webservice.es.message.ES1RegisterEISRequest;

/**
 * 标准客户端实现类 所有的客户端均需要实现该抽象类 内含初始化函数
 */
@SuppressWarnings("deprecation")
public interface CommonWsClient {
	/**
	 * 应用个人化
	 * 
	 * @param EuiccEntity
	 * @return
	 * @throws Exception
	 * @Since 1.0.0
	 */
	public EuiccEntity<AppPersonialResp> appPersonial(
			EuiccEntity<AppPersonialBody> EuiccEntity) throws Exception;


	/**
	 * 应用查询
	 * 
	 * @param EuiccEntity
	 * @return
	 * @throws Exception
	 * @Since 1.0.0
	 */
	public EuiccEntity<AppQueryResp> appQuery(EuiccEntity<AppQueryBody> EuiccEntity)throws Exception;


	public EuiccEntity<AppApplyResp> appApply(EuiccEntity<AppApplyBody> EuiccEntity) throws Exception;


	String appQueryToSoap(EuiccEntity<AppQueryBody> Entity) throws Exception;
	
}
