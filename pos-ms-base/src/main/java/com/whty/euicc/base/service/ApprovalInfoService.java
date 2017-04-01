package com.whty.euicc.base.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.pojo.ApprovalInfo;
import com.whty.euicc.base.pojo.ApprovalInfoExample;
import com.whty.euicc.base.pojo.ApprovalUserInfo;

/**
 * @author liuwei
 * @date 2016-09-20 08:58
 *
 * @version 1.0
 */
public interface ApprovalInfoService {
		
	/**
     * 根据主键查询记录
     */
	ApprovalInfo selectByPrimaryKey(String approvalUserId);
	
	/**
	 * 插入记录
	 */
	int insertSelective(ApprovalInfo record);
	
	/**
     * 根据主键更新记录
     */
	int updateByPrimaryKeySelective(ApprovalInfo record);
	
	/**
     * 分页查询
     */
	PageList<ApprovalUserInfo> selectByExample(ApprovalInfoExample example,PageBounds pageBounds);
	
	/**
     * 根据主键查询
     */
	ApprovalUserInfo selectUserInfoByPrimaryKey(String approvalUserId);
}
