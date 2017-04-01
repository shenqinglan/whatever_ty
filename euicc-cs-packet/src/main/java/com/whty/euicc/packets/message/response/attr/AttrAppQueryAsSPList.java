package com.whty.euicc.packets.message.response.attr;

import com.whty.euicc.packets.dto.AppQueryAsSPDto;

import java.util.List;

public interface AttrAppQueryAsSPList {
	
	public void setAppQueryInfo(List<AppQueryAsSPDto> appsRemote);
	
	public void addAppQueryInfo(AppQueryAsSPDto appRemote);

}
