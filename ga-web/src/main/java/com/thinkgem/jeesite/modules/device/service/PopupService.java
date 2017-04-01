package com.thinkgem.jeesite.modules.device.service;

import java.util.List;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.device.entity.GaDeviceAp;
import com.thinkgem.jeesite.modules.device.entity.GaDeviceMote;


/**
 * 下拉列表服务类
 * @author liuwsh
 * @version 2017-02-17
 */
public class PopupService {
	
	public static GaDeviceApService apService  = SpringContextHolder.getBean(GaDeviceApService.class);
	public static GaDeviceMoteService moteService  = SpringContextHolder.getBean(GaDeviceMoteService.class);
	
	
	public static String getApNo(String id) {
		return apService.get(id).getDeviceId();
	}
	
	public static String getMoteNo(String id) {
		return moteService.get(id).getDeviceId();
	}

}
