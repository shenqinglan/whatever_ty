package com.whty.euicc.packets.message.request.attr;

import com.whty.euicc.packets.message.request.Rapdu;

import java.util.List;
/**
 * 带apdu参数消息体<接口类>
 *
 * @author baojw@whty.com.cn
 * @date 2014年10月11日 下午3:41:29
 */
public interface WithApdu_MsgBody {
	List<Rapdu> getRapdu();

	void setRapdu(List<Rapdu> rApdu);
}
