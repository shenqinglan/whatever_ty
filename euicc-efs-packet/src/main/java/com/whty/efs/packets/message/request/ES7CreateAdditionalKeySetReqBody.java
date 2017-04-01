package com.whty.efs.packets.message.request;

import com.whty.efs.packets.message.MsgBody;
import com.whty.efs.packets.message.MsgType;


/**
 * 创建额外密钥组件请求
 * @author 11
 *
 */
@MsgType("createAdditionalKeySet")
public class ES7CreateAdditionalKeySetReqBody extends EuiccReqBody implements MsgBody{
	private String  ePK_SR_ECKA;

	public String getePK_SR_ECKA() {
		return ePK_SR_ECKA;
	}

	public void setePK_SR_ECKA(String ePK_SR_ECKA) {
		this.ePK_SR_ECKA = ePK_SR_ECKA;
	}
	

}
