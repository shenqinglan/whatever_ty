package com.whty.euicc.bizflow.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whty.cache.CacheUtil;
import com.whty.euicc.data.pojo.BusinessObject;
import com.whty.euicc.data.service.BusinessObjectService;

/**
 * 业务对象缓存<br>
 * <br>
 * 如果未启用缓存，直接在数据库中读写<br>
 * 如果启用缓存，先在缓存中读写，再在数据中读写<br>
 * 
 * @author bjw@whty.com.cn
 * @date 2015年1月16日 上午10:08:41
 */
@Service
public class BizObjectCache {
	private static final Logger logger = LoggerFactory.getLogger(BizObjectCache.class);
	@Autowired
	private BusinessObjectService businessObjectService;
	
	public static final String BUSINESSOBJECT_REIDS = "_BUSINESSOBJECT";// 业务对象数据

	public boolean deleteBusinessObject(BusinessObject businessObject) {

		// 如果缓存开启，则将数据同步到缓存中

		String cacheKey = buildCacheKey(businessObject.getSessionId(), businessObject.getCardNO());
		logger.debug("cacheKey:{}", cacheKey);
		CacheUtil.remove(cacheKey);
		return true;
	}

	/**
	 * 将BusinessObject从redis缓存中读出
	 * 
	 * @param sessionId
	 * @param cardNO
	 * @return
	 */
	public BusinessObject getBusinessObject(String sessionId, String cardNO) {
		logger.debug("sessionId:{},cardNO:{}", sessionId, cardNO);
		BusinessObject businessObject = null;
		String cacheKey = buildCacheKey(sessionId, cardNO);
		logger.debug("cacheKey:{}", cacheKey);
		businessObject = CacheUtil.get(cacheKey, BusinessObject.class);
		if (null == businessObject) {
			// 如果缓存中不存在，则从数据库中获取
			businessObject = businessObjectService.getBusinessObject(sessionId, cardNO);
		}
		return businessObject;
	}

	/**
	 * 将businessObject写入缓存
	 * 
	 * @param businessObject
	 */
	public void cacheBusinessObject(BusinessObject businessObject) {

		// 如果缓存开启，则将数据同步到缓存中
		String cacheKey = this.buildCacheKey(businessObject.getSessionId(), businessObject.getCardNO());

		logger.debug("cacheKey:{}", cacheKey);
        try{
            CacheUtil.put(cacheKey, businessObject);
        }catch (Exception e){
            logger.error("put cacheKey error:{}",cacheKey);
            e.printStackTrace();
        }


		// 判断数据库中是否存在此对象
//		if (null == businessObject.getCardNO()) {
//			businessObjectService.insert(businessObject);
//		} else {
//			if (businessObjectService.isExist(businessObject.getSessionId(), businessObject.getCardNO())) {
//				businessObjectService.update(businessObject);
//			} else {
//				businessObjectService.insert(businessObject);
//			}
//		}
	}
	
	private String buildCacheKey(String sessionId, String cardNO) {
		StringBuilder sb = new StringBuilder();
		sb.append(sessionId.toUpperCase());
		if (cardNO != null && cardNO.length() > 0) {
			sb.append("_").append(cardNO.toUpperCase());
		}
		sb.append(BUSINESSOBJECT_REIDS);
		return sb.toString();
	}
}
