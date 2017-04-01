package com.whty.euicc.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.dao.BaseRoleModuleMapper;
import com.whty.euicc.base.pojo.BaseRoleModuleExample;
import com.whty.euicc.base.pojo.BaseRoleModuleKey;
import com.whty.euicc.base.service.BaseRoleModuleService;

@Service
@Transactional
public class BaseRoleModuleServiceImpl implements BaseRoleModuleService {

	@Autowired
	private BaseRoleModuleMapper baseRoleModuleMapper;
	
	/**
	 * 选择性插入
	 */
	@Override
	public int insertSelective(BaseRoleModuleKey BaseRoleModule) {
		return baseRoleModuleMapper.insertSelective(BaseRoleModule);
	}
	
		
	/**
	 * 根据条件进行查询(分页)
	 * @param example
	 * @return
	 */
	@Override
	public PageList<BaseRoleModuleKey> selectByExample(BaseRoleModuleExample example,PageBounds pageBounds){
		return baseRoleModuleMapper.selectByExample(example,pageBounds);
	}

	/**
	 * 根据条件进行选择性更新
	 */
	@Override
	public int updateSelectiveByRecord(BaseRoleModuleKey record,
			BaseRoleModuleExample example) {
		return baseRoleModuleMapper.updateByExampleSelective(record, example);
	}

	/**
	 * 根据主键删除对象
	 */
	@Override
	public int deleteByPrimaryKey(BaseRoleModuleKey key) {
		return baseRoleModuleMapper.deleteByPrimaryKey(key);
	}

	/**
	 * 根据条件删除对象
	 */
	@Override
    public int deleteByExample(BaseRoleModuleExample example){
    	return baseRoleModuleMapper.deleteByExample(example);
    }

}
