/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-10-31
 * Id: EuiccCardInfoImpl.java,v 1.0 2016-10-31 下午3:45:05 Administrator
 */
package com.whty.efs.data.service;

import java.util.List;

import com.whty.efs.data.pojo.EuiccCard;

/**
 * @ClassName EuiccCardInfoImpl
 * @author Administrator
 * @date 2016-10-31 下午3:45:05
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public interface IEuiccCardInfoService{

	List<EuiccCard> find(String eid);
	EuiccCard selectByPrimaryKey(String eid);

}
