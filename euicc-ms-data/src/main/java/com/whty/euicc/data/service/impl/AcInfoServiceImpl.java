package com.whty.euicc.data.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.data.pojo.AcInfo;
import com.whty.euicc.data.pojo.AcInfoExample;
import com.whty.euicc.data.dao.AcInfoMapper;
import com.whty.euicc.data.service.AcInfoService;
/**
 * @author dzmsoft
 * @date 2016-05-30 15:37
 *
 * @version 1.0
 */
@Service
@Transactional
public class AcInfoServiceImpl implements AcInfoService{

	@Autowired
	private AcInfoMapper acInfoMapper;

	/**
     * 根据条件插入记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int insertSelective(AcInfo record){
		return acInfoMapper.insertSelective(record);
	}
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public AcInfo selectByPrimaryKey(Integer id){
		return acInfoMapper.selectByPrimaryKey(id);
	}
	
	/**
     * 分页查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public PageList<AcInfo> selectByExample(AcInfoExample example,PageBounds pageBounds){
		return acInfoMapper.selectByExample(example, pageBounds);
	}
	
	/**
     * 根据条件查询记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public List<AcInfo> selectByExample(AcInfoExample example){
		return acInfoMapper.selectByExample(example);
	}
	
	/**
     * 根据主键更新记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int updateByPrimaryKeySelective(AcInfo record){
		return acInfoMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
     * 根据查询条件更新记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int updateByExampleSelective(AcInfo record,
			AcInfoExample example){
		return acInfoMapper.updateByExampleSelective(record, example);
	}
	
	/**
     * 根据主键生成记录
     * @dzmsoftgenerated 2016-05-30 15:37
     */
	public int deleteByPrimaryKey(Integer id){
		return acInfoMapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 根据条件删除字段信息
	 * @dzmsoftgenerated 2016-05-30 15:37
	 * @param example
	 * @return
	 */
	public int deleteByExample(AcInfoExample example){
		return acInfoMapper.deleteByExample(example);
	}
}
