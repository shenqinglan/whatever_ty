package com.whty.euicc.data.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whty.euicc.data.dao.EuiccIsdRMapper;
import com.whty.euicc.data.pojo.EuiccIsdR;
import com.whty.euicc.data.service.EuiccIsdRService;
@Service
@Transactional
public class EuiccIsdRServiceImpl implements EuiccIsdRService {

	@Autowired
	private EuiccIsdRMapper isdrMapper;
	
	@Override
	public int deleteByPrimaryKey(String rId) {
		return isdrMapper.deleteByPrimaryKey(rId);
	}

	@Override
	public int insert(EuiccIsdR record) {
		return isdrMapper.insert(record);
	}

	@Override
	public int insertSelective(EuiccIsdR record) {
		return isdrMapper.insertSelective(record);
	}

	@Override
	public EuiccIsdR selectByPrimaryKey(String rId) {
		return isdrMapper.selectByPrimaryKey(rId);
	}

	@Override
	public int updateByPrimaryKeySelective(EuiccIsdR record) {
		return isdrMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(EuiccIsdR record) {
		return isdrMapper.updateByPrimaryKey(record);
	}

	@Override
	public EuiccIsdR selectByEid(String eid) {
		return isdrMapper.selectByEid(eid);
	}

	@Override
	public int deleteByEid(String eid) {
		return isdrMapper.deleteByEid(eid);
	}

	@Override
	public EuiccIsdR selectByIsdRAid(String isdRAid) {
		return isdrMapper.selectByIsdRAid(isdRAid);
	}

}
