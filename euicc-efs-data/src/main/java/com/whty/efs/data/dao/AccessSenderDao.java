package com.whty.efs.data.dao;

import java.util.List;

import com.whty.efs.data.dto.Criteria;
import com.whty.efs.data.pojo.AccessSender;

public interface AccessSenderDao {
	/**
	 * 渠道类型：厦门和洪城
	 * @param channel_code 
	 */
	public List<AccessSender> getSender(Criteria example);
	
}
