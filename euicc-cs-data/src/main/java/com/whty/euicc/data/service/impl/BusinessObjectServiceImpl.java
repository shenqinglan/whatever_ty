package com.whty.euicc.data.service.impl;

import com.whty.euicc.common.utils.StringUtils;
import com.whty.euicc.data.dao.base.BusinessObjectMapper;
import com.whty.euicc.data.dto.Criteria;
import com.whty.euicc.data.pojo.BusinessObject;
import com.whty.euicc.data.service.BusinessObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class BusinessObjectServiceImpl implements BusinessObjectService {
	
	
	@Autowired
	private BusinessObjectMapper businessObjectMapper;
	

	@Override
	public int update(BusinessObject record) {
		Criteria criteria = new Criteria() ;
		criteria.put("sessionId" , record.getSessionId()) ;
		if (StringUtils.isNotNull(record.getCardNO())){
			criteria.put("seId" , record.getCardNO()) ;
		}
		return businessObjectMapper.updateByExampleSelective(record , criteria.getCondition());
		
	}

	@Override
	public int insert(BusinessObject record) {
		return businessObjectMapper.insertSelective(record);
	}

	@Override
	public BusinessObject getBusinessObject(String sessionId , String seId) {
		Criteria criteria = new Criteria() ;
		criteria.put("sessionId" , sessionId) ;
		if (StringUtils.isNotNull(seId)){
			criteria.put("seId" , seId) ;
		}
		List<BusinessObject> businessObjects = businessObjectMapper.selectByExample(criteria);
		if (CollectionUtils.isEmpty(businessObjects))
			return null;
		return businessObjects.get(0);
	}

	@Override
	public boolean isExist(String sessionId , String seId) {
		Criteria criteria = new Criteria() ;
		criteria.put("sessionId" , sessionId) ;
		if (StringUtils.isNotNull(seId)){
			criteria.put("seId" , seId) ;
		}
		int count = businessObjectMapper.countByExample(criteria);
		return count>0?true:false ;
	}
}
