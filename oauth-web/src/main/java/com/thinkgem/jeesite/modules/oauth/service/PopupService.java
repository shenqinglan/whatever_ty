package com.thinkgem.jeesite.modules.oauth.service;

import java.util.List;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.oauth.entity.OauthCard;

/**
 * 下拉列表服务类
 * @author liuwsh
 * @version 2017-02-17
 */
public class PopupService {
	
	public static OauthCardService cardService  = SpringContextHolder.getBean(OauthCardService.class);
	
	public static List<OauthCard> getCardInfoList() {
		OauthCard a = new OauthCard();
		List<OauthCard> l = cardService.findList(a);
		for (OauthCard o : l) {
			if (o.getMsisdn() != null && !"".equals(o.getMsisdn())) {
				o.setRemarks(o.getMsisdn() + "-" + o.getEid() + "-" + o.getIccid());
			} else {
				o.setRemarks(o.getEid() + "-" + o.getIccid());
			}
		}
		return l;
	}

}
