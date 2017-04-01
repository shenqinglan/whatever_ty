package com.whty.euicc.packets.message.response.attr;

import com.whty.euicc.packets.dto.AppQueryDto;

import java.util.List;

public interface AttrAppList {
	
	public void setApps(List<AppQueryDto> apps);
	
	public void addApp(AppQueryDto app);
}
