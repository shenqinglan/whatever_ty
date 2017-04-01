package com.whty.euicc.data.service;

import java.util.List;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.data.pojo.AcInfo;
import com.whty.euicc.data.pojo.AcInfoExample;

/**
 * @author dzmsoft
 * @date 2016-05-30 15:37
 *
 * @version 1.0
 */
public interface AcInfoService {
	
	/**
     * 根据条件插入记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int insertSelective(AcInfo record);
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	AcInfo selectByPrimaryKey(Integer id);
	
	/**
     * 分页查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	PageList<AcInfo> selectByExample(AcInfoExample example,PageBounds pageBounds);
	
	/**
     * 根据条件查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	List<AcInfo> selectByExample(AcInfoExample example);
	
	/**
     * 根据主键更新记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int updateByPrimaryKeySelective(AcInfo record);
	
	/**
     * 根据查询条件更新记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int updateByExampleSelective(AcInfo record,
			AcInfoExample example);
	
	/**
     * 根据主键生成记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	int deleteByPrimaryKey(Integer id);
	
	/**
	 * 根据条件删除字段信息
	 * @dzmsoftgenerated 2016-05-30 15:37
	 * @param example
	 * @return
	 */
	int deleteByExample(AcInfoExample example);
	
	
}
