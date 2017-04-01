package com.whty.euicc.data.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.data.pojo.EuiccPol2;
import com.whty.euicc.data.pojo.EuiccPol2Example;

public interface EuiccPol2Service {
	EuiccPol2 selectByPrimaryKey(String pol2Id);
	
	PageList<EuiccPol2> selectByExample(EuiccPol2Example example,PageBounds pageBounds);
}
