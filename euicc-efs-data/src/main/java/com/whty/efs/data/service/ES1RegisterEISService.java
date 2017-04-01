package com.whty.efs.data.service;

import com.whty.efs.data.pojo.EIS;

/**
 * @author dzmsoft
 * @date 2016-05-30 15:37
 *
 * @version 1.0
 */
public interface ES1RegisterEISService {
	
	/**
	 * 插入记录
	 * @param record
	 * @return
	 */
	int insertEuiccCardSelective(EIS eis);
}
