package com.whty.euicc.data.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whty.euicc.data.dao.EuiccScp81Mapper;
import com.whty.euicc.data.pojo.EuiccScp81;
import com.whty.euicc.data.service.EuiccScp81Service;

@Service
@Transactional
public class EuiccScp81ServiceImpl implements EuiccScp81Service {
	
	@Autowired
	private EuiccScp81Mapper scp81;
	

	@Override
	public int deleteByPrimaryKey(String scp81Id) {
		return scp81.deleteByPrimaryKey(scp81Id);
	}

	@Override
	public int insert(EuiccScp81 record) {
		return scp81.insert(record);
	}

	@Override
	public int insertSelective(EuiccScp81 record) {
		return scp81.insertSelective(record);
	}

	@Override
	public EuiccScp81 selectByPrimaryKey(String scp81Id) {
		return scp81.selectByPrimaryKey(scp81Id);
	}

	@Override
	public String selectById(String id) {
		return scp81.selectById(id);
	}

	@Override
	public int updateByPrimaryKeySelective(EuiccScp81 record) {
		return scp81.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(EuiccScp81 record) {
		return scp81.updateByPrimaryKey(record);
	}

	@Override
	public EuiccScp81 selectByEid(String eid) {
		return scp81.selectByEid(eid);
	}

	@Override
	public int deleteByEid(String eid) {
		return scp81.deleteByEid(eid);
	}

}
