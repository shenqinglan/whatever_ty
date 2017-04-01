package com.whty.euicc.data.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whty.euicc.data.dao.EuiccScp03Mapper;
import com.whty.euicc.data.pojo.EuiccScp03;
import com.whty.euicc.data.service.EuiccScp03Service;

@Service
@Transactional
public class EuiccScp03ServiceImpl implements EuiccScp03Service{

	@Autowired
	private EuiccScp03Mapper scp03Mapper;
	
	@Override
	public int deleteByPrimaryKey(String scp03Id) {
		return scp03Mapper.deleteByPrimaryKey(scp03Id);
	}
	
	@Override
	public int deleteByEid(String eid) {
		return scp03Mapper.deleteByEid(eid);
	}
	@Override
	public int deleteByEidAndIsdPAid(EuiccScp03 record) {
		return scp03Mapper.deleteByEidAndIsdPAid(record);
	}
	@Override
	public int insert(EuiccScp03 record) {
		return scp03Mapper.insert(record);
	}

	@Override
	public int insertSelective(EuiccScp03 record) {
		return scp03Mapper.insertSelective(record);
	}

	@Override
	public EuiccScp03 selectByPrimaryKey(String scp03Id) {
		return scp03Mapper.selectByPrimaryKey(scp03Id);
	}

	@Override
	public int updateByPrimaryKey(EuiccScp03 record) {
		return scp03Mapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(EuiccScp03 record) {
		return scp03Mapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public EuiccScp03 selectByScp03(EuiccScp03 record) {
		return scp03Mapper.selectByScp03(record);
	}

	@Override
	public List<EuiccScp03> selectListByEid(String eid) {
		return scp03Mapper.selectListByEid(eid);
	}

	@Override
	public int insertListScp03(List<EuiccScp03> scp03s) {
		return scp03Mapper.insertListScp03(scp03s);
	}

	

}
