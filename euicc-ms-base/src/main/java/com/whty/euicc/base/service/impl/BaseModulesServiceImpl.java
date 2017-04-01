package com.whty.euicc.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.dao.BaseModulesMapper;
import com.whty.euicc.base.pojo.BaseModules;
import com.whty.euicc.base.pojo.BaseModulesExample;
import com.whty.euicc.base.pojo.BaseUsersExample;
import com.whty.euicc.base.pojo.udf.BaseModulesComplexExample;
import com.whty.euicc.base.service.BaseModulesService;

@Service
@Transactional
public class BaseModulesServiceImpl implements BaseModulesService {

	@Autowired
	private BaseModulesMapper baseModulesMapper;
	
	/**
	 * 选择性插入
	 */
	@Override
	public int insertSelective(BaseModules BaseModules) {
		return baseModulesMapper.insertSelective(BaseModules);
	}
	
	/**
	 * 根据主键查询
	 */
	@Override
	public BaseModules selectByPrimaryKey(String moduleId) {
		return baseModulesMapper.selectByPrimaryKey(moduleId);
	}
		
	/**
	 * 根据条件进行查询(分页)
	 * @param example
	 * @return
	 */
	@Override
	public PageList<BaseModules> selectByExample(BaseModulesExample example,PageBounds pageBounds){
		return baseModulesMapper.selectByExample(example,pageBounds);
	}

	/**
	 * 根据主键进行选择性更新
	 */
	@Override
	public int updateSelectiveByPrimaryKey(BaseModules record) {
		return baseModulesMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 根据条件进行选择性更新
	 */
	@Override
	public int updateSelectiveByRecord(BaseModules record,
			BaseModulesExample example) {
		return baseModulesMapper.updateByExampleSelective(record, example);
	}

	/**
	 * 根据主键删除对象
	 */
	@Override
	public int deleteByPrimaryKey(String moduleId) {
		return baseModulesMapper.deleteByPrimaryKey(moduleId);
	}

	/**
	 * 根据条件删除对象
	 */
	@Override
    public int deleteByExample(BaseModulesExample example){
    	return baseModulesMapper.deleteByExample(example);
    }

	/**
	 * 获取我的菜单
	 * @param userId
	 * @return
	 */
	@Override
	public List<BaseModules> selectMyModules(String userId) {
		BaseModulesComplexExample example = new BaseModulesComplexExample();
		BaseUsersExample.Criteria criteria = example.getBaseUsersExample().createCriteria();
		criteria.andUserIdEqualTo(userId);
		return baseModulesMapper.selectMyModules(example);
	}
	
}
