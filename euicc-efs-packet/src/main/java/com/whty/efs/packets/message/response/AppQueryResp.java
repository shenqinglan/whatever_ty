package com.whty.efs.packets.message.response;

import java.util.List;

import com.whty.efs.packets.dto.AppQueryInfo;

/**
 * 应用查询请求报文体
 * 
 * @author gaofeng
 *
 */
public class AppQueryResp extends BaseRespBody {

	private List<AppQueryInfo> appQueryInfo;

	public List<AppQueryInfo> getAppQueryInfo() {
		return appQueryInfo;
	}

	public void setAppQueryInfo(List<AppQueryInfo> appQueryInfo) {
		this.appQueryInfo = appQueryInfo;
	}



}
