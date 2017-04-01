package com.whty.euicc.packets.message.response;

import com.whty.euicc.packets.dto.AppQueryDto;
import com.whty.euicc.packets.message.response.attr.AttrAppList;

import java.util.ArrayList;
import java.util.List;


/**
 * 应用查询响应
 * 
 * @author Administrator
 * 
 */
public class AppQueryRespBody extends BaseRespBody implements AttrAppList {

	public AppQueryRespBody() {
		super();
	}

	/**
	 * 查询结果
	 */
	private List<AppQueryDto> apps = new ArrayList<AppQueryDto>();

	public List<AppQueryDto> getApps() {
		return apps;
	}

	public void setApps(List<AppQueryDto> apps) {
		this.apps = apps;
	}

	public void addApp(AppQueryDto app) {
		this.apps.add(app);
	}
}
