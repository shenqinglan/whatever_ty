package com.whty.euicc.data.thread;

import java.util.concurrent.Callable;

import com.whty.euicc.base.pojo.BaseUsers;
import com.whty.euicc.data.pojo.EuiccCardExcelInfo;
import com.whty.euicc.data.service.EuiccCardService;

public class importThread implements Callable<String>{

	private EuiccCardService cardService;
	private BaseUsers currentUser;
	private EuiccCardExcelInfo info;
	
	public importThread(EuiccCardService cardService, BaseUsers currentUser,
			EuiccCardExcelInfo info) {
		this.cardService = cardService;
		this.currentUser = currentUser;
		this.info = info;
	}

	@Override
	public String call() throws Exception {
		return cardService.importEuicc(this.currentUser, this.info);
	}
	
}
