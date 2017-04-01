package com.whty.rsp.data.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whty.rsp.data.dao.RspProfileMapper;
import com.whty.rsp.data.pojo.RspProfile;
import com.whty.rsp.data.pojo.RspProfileExample;
import com.whty.rsp.data.pojo.RspProfileWithBLOBs;
import com.whty.rsp.data.service.RspProfileService;

@Service
@Transactional
public class RspProfileServiceImpl implements RspProfileService {

	@Autowired
	private RspProfileMapper rspProfileMapper;
	
	@Override
	public int countByExample(RspProfileExample example) {
		return rspProfileMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(RspProfileExample example) {
		return rspProfileMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(String iccid) {
		int deleteResult = rspProfileMapper.deleteByPrimaryKey(iccid);
		System.out.println("Result of deleting profile : "+(deleteResult == 1 ? "Success" : "Failed"));
		if(deleteResult == 0){
			throw new RuntimeException("Deleting the profile is error.Maybe the data has changed.");
		}
		return deleteResult;
	}

	@Override
	public int insert(RspProfileWithBLOBs record) {
		return rspProfileMapper.insert(record);
	}

	@Override
	public int insertSelective(RspProfileWithBLOBs record) {
		return rspProfileMapper.insertSelective(record);
	}

	@Override
	public List<RspProfileWithBLOBs> selectByExampleWithBLOBs(
			RspProfileExample example) {
		return rspProfileMapper.selectByExampleWithBLOBs(example);
	}

	@Override
	public List<RspProfile> selectByExample(RspProfileExample example) {
		return rspProfileMapper.selectByExample(example);
	}

	@Override
	public RspProfileWithBLOBs selectByPrimaryKey(String iccid) {
		return rspProfileMapper.selectByPrimaryKey(iccid);
	}

	@Override
	public int updateByExampleSelective(RspProfileWithBLOBs record,
			RspProfileExample example) {
		return rspProfileMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExampleWithBLOBs(RspProfileWithBLOBs record,
			RspProfileExample example) {
		return rspProfileMapper.updateByExampleWithBLOBs(record, example);
	}

	@Override
	public int updateByExample(RspProfile record, RspProfileExample example) {
		return rspProfileMapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(RspProfileWithBLOBs record) {
		int updateResult = rspProfileMapper.updateByPrimaryKeySelective(record);
		System.out.println("Result of updating rsp_profile : " + (updateResult == 1 ? "Success" : "Failed"));
		if(updateResult == 0){
			throw new RuntimeException("Updating rsp_profile is error.Maybe the data has changed.");
		}
		return updateResult;
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(RspProfileWithBLOBs record) {
		return rspProfileMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateByPrimaryKey(RspProfile record) {
		return rspProfileMapper.updateByPrimaryKey(record);
	}

	@Override
	public RspProfileWithBLOBs selectByProfileType(String profileType) {
		return rspProfileMapper.selectByProfileType(profileType);
	}

	@Override
	public RspProfileWithBLOBs selectByMatchingId(String matchingId) {
		return rspProfileMapper.selectByMatchingId(matchingId);
	}

	@Override
	public RspProfileWithBLOBs selectByEid(String eid) {
		return rspProfileMapper.selectByEid(eid);
	}

	@Override
	public RspProfileWithBLOBs selectByMsisdn(String msisdn) {
		return rspProfileMapper.selectByMsisdn(msisdn);
	}
	
	

}
