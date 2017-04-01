package com.whty.euicc.base.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.pojo.ApprovalInfo;
import com.whty.euicc.base.pojo.BaseUsers;
import com.whty.euicc.base.pojo.BaseUsersExample;

public interface BaseUsersService {
	
	/**
	 * 保存用户信息
	 * @param baseUsers
	 * @return
	 */
	int insertSelective(BaseUsers baseUsers, ApprovalInfo approvalInfo);
	
	/**
	 * 根据主键查询用户信息
	 * @param userId 用户表主键
	 * @return
	 */
	BaseUsers selectByPrimaryKey(String userId);
	
	/**
	 * 根据账户查询用户信息
	 * @param userAccount 账户
	 * @return
	 */
	BaseUsers selectByUserAccount(String userAccount);
	
	/**
	 * 根据条件查询用户信息
	 * @param example 条件参数map
	 * @return
	 */
	PageList<BaseUsers> selectByExample(BaseUsersExample example, PageBounds pageBounds);
	
	/**
	 * 根据主键修改传入的用户信息
	 * @param baseUsers
	 * @return 0：失败 ， 1：成功
	 */
	int updateSelectiveByPrimaryKey(BaseUsers baseUsers);
	
	/**
	 * 根据条件修改传入的用户信息
	 * @param record 
	 * @param example
	 * @return
	 */
	int updateSelectiveByRecord(BaseUsers record,
                                BaseUsersExample example);
	
	/**
	 * 根据主键删除用户信息
	 * @param userId
	 * @return 0：删除失败 ， 1：删除成功
	 */
	int deleteByPrimaryKey(String userId);
	
	/**
	 * 根据条件删除用户信息
	 * @param example
	 * @return
	 */
	int deleteByExample(BaseUsersExample example);
	
	
	int upateUserStatus(BaseUsers record);
	
	/**
	 * 根据用户名、密码获取用户信息
	 * @param username
	 * @param password
	 * @return
	 */
	BaseUsers selectByUserPwd(String username, String password);
	
}
