package com.whty.euicc.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.dao.BaseLogsMapper;
import com.whty.euicc.base.dto.LogsUser;
import com.whty.euicc.base.pojo.BaseLogs;
import com.whty.euicc.base.pojo.BaseLogsExample;
import com.whty.euicc.base.service.BaseLogsService;

@Service
@Transactional
public class BaseLogsServiceImpl implements BaseLogsService {

	@Autowired
	private BaseLogsMapper baseLogsMapper;
	
	/**
	 * 选择性插入
	 */
	@Override
	public int insertSelective(BaseLogs baseLogs) {
		return baseLogsMapper.insertSelective(baseLogs);
	}
	
	/**
	 * 根据主键查询
	 */
	@Override
	public BaseLogs selectByPrimaryKey(String id) {
		return baseLogsMapper.selectByPrimaryKey(id);
	}
		
	/**
	 * 根据条件进行查询(分页)
	 * @param example
	 * @return
	 */
	@Override
	public PageList<BaseLogs> selectByExample(BaseLogsExample example,PageBounds pageBounds){
		return baseLogsMapper.selectByExample(example,pageBounds);
	}

	/**
	 * 根据主键进行选择性更新
	 */
	@Override
	public int updateSelectiveByPrimaryKey(BaseLogs record) {
		return baseLogsMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 根据条件进行选择性更新
	 */
	@Override
	public int updateSelectiveByRecord(BaseLogs record,
			BaseLogsExample example) {
		return baseLogsMapper.updateByExampleSelective(record, example);
	}

	/**
	 * 根据主键删除对象
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		return baseLogsMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 根据条件删除对象
	 */
	@Override
    public int deleteByExample(BaseLogsExample example){
    	return baseLogsMapper.deleteByExample(example);
    }

	@Override
	public PageList<LogsUser> selectSystemLog(BaseLogsExample example,
			PageBounds pageBounds) {
		return baseLogsMapper.selectSystemLog(example, pageBounds);
	}

}
