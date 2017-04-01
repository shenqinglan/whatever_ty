package com.whty.assistant.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/demo")
public class DemoController {
	
	private Logger logger = LoggerFactory.getLogger(DemoController.class);
	
	@RequestMapping(value="/receiveSms")
	public String receiveSms(String src,String msg) throws Exception{		
		logger.debug("debug src:{},msg:{}",src,msg);
		logger.info("into src:{},msg:{}",src,msg);
		logger.warn("war src:{},msg:{}",src,msg);
		logger.error("error src:{},msg:{}",src,msg);
		return "0000";
	}

}
