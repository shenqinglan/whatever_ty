package com.whty.efs.packets.message.response;

import com.whty.efs.packets.message.response.attr.AttrProcessId;

/**
 * 银数手机客户端应用申请响应
 * @ClassName: AppApplyRespBody
 * @author: xuliping@whty.com.cn
 * @since:2015年12月24日
 * @Description:TODO
 */
public class AppApplyResp extends BaseRespBody implements AttrProcessId{
	
	public AppApplyResp(){
		super();
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	private String processId;

}
