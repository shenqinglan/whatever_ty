package com.whty.euicc.packets.message.request;
import java.util.List;

import com.whty.euicc.packets.message.MsgType;


/**
 * Https下SM-SR的状态查询请求
 * @author Administrator
 *
 */
@MsgType("getStatusByHttps")
public class GetStatusByHttpsReqBody extends PorReqBody{
	private List<String > iccidList;

	public List<String> getIccidList() {
		return iccidList;
	}

	public void setIccidList(List<String> iccidList) {
		this.iccidList = iccidList;
	}

}
