/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-10-31
 * Id: EuiccCardInfoService.java,v 1.0 2016-10-31 下午3:48:03 Administrator
 */
package com.whty.efs.data.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whty.efs.data.dao.EuiccCardMapper;
import com.whty.efs.data.pojo.EuiccCard;
import com.whty.efs.data.service.IEuiccCardInfoService;

/**
 * @ClassName EuiccCardInfoService
 * @author Administrator
 * @date 2016-10-31 下午3:48:03
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@Transactional
@Service("euiccCardInfoService")
public class EuiccCardInfoServiceImpl implements IEuiccCardInfoService {
	
	@Autowired
	EuiccCardMapper euiccCardMapper;

	/**
	 * 
	 * @author Administrator
	 * @date 2016-11-2
	 * @param eid
	 * @return
	 * @Description TODO(查找卡信息dao)
	 * @see com.whty.efs.data.service.IEuiccCardInfoService#find(java.lang.String)
	 */
	@Override
	public List<EuiccCard>  find(String eid) {
		return euiccCardMapper.findEuiccCardInfo(eid);
	}

	@Override
	public EuiccCard selectByPrimaryKey(String eid) {
		return euiccCardMapper.selectByPrimaryKey(eid);
	}
	
}
