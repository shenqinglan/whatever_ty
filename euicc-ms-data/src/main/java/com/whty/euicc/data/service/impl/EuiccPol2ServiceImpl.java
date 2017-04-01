package com.whty.euicc.data.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.data.dao.EuiccPol2Mapper;
import com.whty.euicc.data.pojo.EuiccPol2;
import com.whty.euicc.data.pojo.EuiccPol2Example;
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
	public PageList<EuiccPol2> selectByExample(EuiccPol2Example example,PageBounds pageBounds){
		return euiccPol2.selectByExample(example, pageBounds);
	}

}
