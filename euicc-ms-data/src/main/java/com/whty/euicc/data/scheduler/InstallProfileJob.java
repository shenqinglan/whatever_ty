package com.whty.euicc.data.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class InstallProfileJob {
	private Logger logger = LoggerFactory.getLogger(InstallProfileJob.class);
	
	//@Scheduled(cron = "0 0/1 * * * ?")
	public void intsllProfile(){
		logger.info("profile install begin");
	}
}
