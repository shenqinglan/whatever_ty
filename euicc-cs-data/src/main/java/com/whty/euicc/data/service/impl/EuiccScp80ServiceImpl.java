package com.whty.euicc.data.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whty.euicc.data.dao.EuiccScp80Mapper;
import com.whty.euicc.data.pojo.EuiccScp80;
import com.whty.euicc.data.service.EuiccScp80Service;
@Service
@Transactional
public class EuiccScp80ServiceImpl implements EuiccScp80Service {
	@Autowired
    private EuiccScp80Mapper scp80Mapper;
	
	@Override
	public int deleteByPrimaryKey(String Scp80Id) {
		return scp80Mapper.deleteByPrimaryKey(Scp80Id);
	}

	@Override
	public int insert(EuiccScp80 record) {
		return scp80Mapper.insert(record);
	}

	@Override
	public int insertSelective(EuiccScp80 record) {
		return scp80Mapper.insertSelective(record);
	}

	@Override
	public EuiccScp80 selectByPrimaryKey(String scp80Id) {
		return scp80Mapper.selectByPrimaryKey(scp80Id);
	}

	@Override
	public String selectByIdAndVersion(EuiccScp80 record) {
		return scp80Mapper.selectByIdAndVersion(record);
	}

	@Override
	public int updateByPrimaryKeySelective(EuiccScp80 record) {
		return scp80Mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(EuiccScp80 record) {
		return scp80Mapper.updateByPrimaryKey(record);
	}

	@Override
	public List<EuiccScp80> selectLists() {
		return scp80Mapper.selectLists();
	}

	@Override
	public List<EuiccScp80> selectListByEid(String eid) {
		return scp80Mapper.selectListByEid(eid);
	}

	@Override
	public int deleteByEid(String eid) {
		return scp80Mapper.deleteByEid(eid);
	}

	@Override
	public int insertListScp80(List<EuiccScp80> scp80s) {
		return scp80Mapper.insertListScp80(scp80s);
	}

}
