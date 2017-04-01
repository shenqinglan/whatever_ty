package com.whty.euicc.data.service;

import com.whty.euicc.data.pojo.BusinessObject;

public interface BusinessObjectService {
	/**
	 * 更新businessObject
	 * @param record
	 * @param condition
	 * @return
	 */
	public int update(BusinessObject record) ;
	
	/**
	 * 新增businessObject
	 * @param record
	 * @return
	 */
	public int insert(BusinessObject record);
	
	/**
	 * 根据sessionId,seId获取businessObject
	 * @return
	 */
	public BusinessObject getBusinessObject(String sessionId, String cardNO);
	
	/**
	 * 根据sessionId,seId判断businessObject是否存在
	 * @param sessionId
	 * @param seId
	 * @return
	 */
	public boolean isExist(String sessionId, String seId);
}
