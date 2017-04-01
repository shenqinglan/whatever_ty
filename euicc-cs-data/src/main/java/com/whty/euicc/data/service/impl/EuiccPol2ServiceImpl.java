package com.whty.euicc.data.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whty.euicc.data.dao.EuiccPol2Mapper;
import com.whty.euicc.data.pojo.EuiccPol2;
import com.whty.euicc.data.service.EuiccPol2Service;

@Service("euiccPol2")
public class EuiccPol2ServiceImpl implements EuiccPol2Service {

	@Autowired
	private EuiccPol2Mapper euiccPol2;
	
	@Override
	public EuiccPol2 selectByPrimaryKey(String pol2Id) {
		return euiccPol2.selectByPrimaryKey(pol2Id);
	}

	@Override
	public EuiccPol2 selectByActionAndQualification(EuiccPol2 record) {
		return euiccPol2.selectByActionAndQualification(record);
	}

}
