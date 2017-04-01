package com.whty.efs.euicc.service;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.whty.efs.data.dao.AccessSenderDao;
import com.whty.efs.data.dao.RouterDao;
import com.whty.efs.data.dto.Criteria;
import com.whty.efs.data.pojo.AccessSender;
import com.whty.efs.data.pojo.Router;
import com.whty.efs.euicc.listener.RouterContainer;
public class RouterService {
	private Logger logger  = LoggerFactory.getLogger(RouterService.class);
	
	@Autowired
	private RouterDao routerDao;
	
	@Autowired
	private AccessSenderDao accessSenderDao;
	
	@Autowired
	private RouterContainer routerContainer;
	
	@PostConstruct
	public void initCache(){
		logger.info("init-method");
		//initRouter();
		//initAccessSender();
	}
	
	/**
	 * 初始化路由信息
	 */
	private void initRouter(){
		Criteria example=new Criteria();
		List<Router> routerList = routerDao.getRouter(example);
		for(Router router : routerList){
			routerContainer.addRouter(router.getReceiver(), router);
		}
	}

	/**
	 * 初始化准入sender白名单
	 */
	private void initAccessSender(){
		Criteria example=new Criteria();
		example.put("exprie_time", new Date());
		List<AccessSender> senderList = accessSenderDao.getSender(example);
		for(AccessSender sender : senderList){
			routerContainer.addAccessSender(sender.getSenderName(), true);
		}
	}
}
