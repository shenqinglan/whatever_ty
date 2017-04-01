package com.whty.euicc.data.service;

import com.whty.euicc.data.pojo.EuiccPol2;

public interface EuiccPol2Service {
	EuiccPol2 selectByPrimaryKey(String pol2Id);
	
	EuiccPol2 selectByActionAndQualification(EuiccPol2 record);
}
