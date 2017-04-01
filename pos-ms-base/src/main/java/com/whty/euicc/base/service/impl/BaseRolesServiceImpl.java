package com.whty.euicc.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.dao.BaseRolesMapper;
import com.whty.euicc.base.pojo.BaseRoles;
import com.whty.euicc.base.pojo.BaseRolesExample;
import com.whty.euicc.base.service.BaseRolesService;

@Service
@Transactional
public class BaseRolesServiceImpl implements BaseRolesService {

	@Autowired
	private BaseRolesMapper baseRolesMapper;
	
	/**
	 * 选择性插入
	 */
	@Override
	public int insertSelective(BaseRoles baseRoles) {
		return baseRolesMapper.insertSelective(baseRoles);
	}
	
	/**
	 * 根据主键查询
	 */
	@Override
	public BaseRoles selectByPrimaryKey(String roleId) {
		return baseRolesMapper.selectByPrimaryKey(roleId);
	}
		
	/**
	 * 根据条件进行查询(分页)
	 * @param example
	 * @return
	 */
	@Override
	public PageList<BaseRoles> selectByExample(BaseRolesExample example,PageBounds pageBounds){
		return baseRolesMapper.selectByExample(example,pageBounds);
	}

	/**
	 * 根据主键进行选择性更新
	 */
	@Override
	public int updateSelectiveByPrimaryKey(BaseRoles record) {
		return baseRolesMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 根据条件进行选择性更新
	 */
	@Override
	public int updateSelectiveByRecord(BaseRoles record,
			BaseRolesExample example) {
		return baseRolesMapper.updateByExampleSelective(record, example);
	}

	/**
	 * 根据主键删除对象
	 */
	@Override
	public int deleteByPrimaryKey(String roleId) {
		return baseRolesMapper.deleteByPrimaryKey(roleId);
	}

	/**
	 * 根据条件删除对象
	 */
	@Override
    public int deleteByExample(BaseRolesExample example){
    	return baseRolesMapper.deleteByExample(example);
    }

}
