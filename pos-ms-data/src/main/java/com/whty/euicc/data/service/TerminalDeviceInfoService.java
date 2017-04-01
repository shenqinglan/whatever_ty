package com.whty.euicc.data.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.data.pojo.RootKeyInfo;
import com.whty.euicc.data.pojo.TerminalDeviceInfo;
import com.whty.euicc.data.pojo.TerminalDeviceInfoExample;

/**
 * @author dzmsoft
 * @date 2016-05-30 15:37
 *
 * @version 1.0
 */
public interface TerminalDeviceInfoService {
		
	/**
     * 根据主键查询记录
     */
	TerminalDeviceInfo selectByPrimaryKey(String sn);
	
	/**
     * 分页查询记录
     */
	PageList<TerminalDeviceInfo> selectByExample(TerminalDeviceInfoExample example,PageBounds pageBounds);
	
	/**
     * 根据主键插入记录
     */
	int insertSelective(TerminalDeviceInfo record);
	
	/**
     * 根据主键生成记录
     */
	int deleteByPrimaryKey(String sn);
	
	/**
     * 根据主键更新记录
     */
	int updateByPrimaryKeySelective(TerminalDeviceInfo record);
	
	/**
	 * 获取rootKey
	 */
	RootKeyInfo selectRootKey(String sn);
}
