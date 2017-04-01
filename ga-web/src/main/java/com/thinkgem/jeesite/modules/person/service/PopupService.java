package com.thinkgem.jeesite.modules.person.service;

import java.util.List;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.person.entity.GaAreaInfo;
import com.thinkgem.jeesite.modules.person.entity.GaCardInfo;
import com.thinkgem.jeesite.modules.person.entity.GaGateInfo;
import com.thinkgem.jeesite.modules.person.entity.GaHouseInfo;
import com.thinkgem.jeesite.modules.person.entity.GaPersonInfo;

/**
 * 下拉列表服务类
 * @author liuwsh
 * @version 2017-02-17
 */
public class PopupService {
	
	public static GaAreaInfoService areaInfoService  = SpringContextHolder.getBean(GaAreaInfoService.class);
	public static GaGateInfoService gateInfoService  = SpringContextHolder.getBean(GaGateInfoService.class);
	public static GaHouseInfoService houseService  = SpringContextHolder.getBean(GaHouseInfoService.class);
	public static GaPersonInfoService personService  = SpringContextHolder.getBean(GaPersonInfoService.class);
	public static GaCardInfoService cardInfoService  = SpringContextHolder.getBean(GaCardInfoService.class);
	
	
	public static List<GaAreaInfo> getAreaList() {
		GaAreaInfo a = new GaAreaInfo();
		return areaInfoService.findList(a);
	}
	
	public static List<GaGateInfo> getGateInfoList() {
		List<GaGateInfo> l = gateInfoService.findList(new GaGateInfo());
		for (GaGateInfo g : l) {
			g.setRemarks(g.getBuildingNo() + "-" + g.getUnitNo());
		}
		return l;
	}
	
	public static List<GaHouseInfo> getHouseList() {
		GaHouseInfo a = new GaHouseInfo();
		List<GaHouseInfo> l = houseService.findList(a);
		for (GaHouseInfo g : l) {
			g.setStandardAddress(g.getBuildingNo() + "-" + g.getUnitNo() + "-" + g.getRoomNo());
		}
		return l;
	}
	
	public static List<GaPersonInfo> getPersonList() {
		GaPersonInfo a = new GaPersonInfo();
		return personService.findList(a);
	}
	
	public static List<GaCardInfo> getCardInfoList() {
		GaCardInfo a = new GaCardInfo();
		return cardInfoService.findList(a);
	}

}
