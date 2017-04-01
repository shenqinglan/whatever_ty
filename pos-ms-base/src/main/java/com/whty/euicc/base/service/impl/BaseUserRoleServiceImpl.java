package com.whty.euicc.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.dao.BaseUserRoleMapper;
import com.whty.euicc.base.pojo.BaseUserRoleExample;
import com.whty.euicc.base.pojo.BaseUserRoleKey;
import com.whty.euicc.base.service.BaseUserRoleService;

@Service
@Transactional
public class BaseUserRoleServiceImpl implements BaseUserRoleService {

	@Autowired
	private BaseUserRoleMapper baseUserRoleMapper;
	
	/**
	 * 选择性插入
	 */
	@Override
	public int insertSelective(BaseUserRoleKey BaseUserRole) {
		return baseUserRoleMapper.insertSelective(BaseUserRole);
	}
		
	/**
	 * 根据条件进行查询(分页)
	 * @param example
	 * @return
	 */
	@Override
	public PageList<BaseUserRoleKey> selectByExample(BaseUserRoleExample example,PageBounds pageBounds){
		return baseUserRoleMapper.selectByExample(example,pageBounds);
	}
	
	/**
	 * 根据条件进行选择性更新
	 */
	@Override
	public int updateSelectiveByRecord(BaseUserRoleKey record,
			BaseUserRoleExample example) {
		return baseUserRoleMapper.updateByExampleSelective(record, example);
	}

	/**
	 * 根据条件删除对象
	 */
	@Override
    public int deleteByExample(BaseUserRoleExample example){
    	return baseUserRoleMapper.deleteByExample(example);
    }

}
