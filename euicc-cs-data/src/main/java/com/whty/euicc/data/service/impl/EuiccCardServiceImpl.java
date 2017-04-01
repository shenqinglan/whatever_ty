package com.whty.euicc.data.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.data.dao.EuiccCardMapper;
import com.whty.euicc.data.dao.EuiccProfileMapper;
import com.whty.euicc.data.pojo.EuiccCard;
import com.whty.euicc.data.pojo.EuiccIsdP;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.service.EuiccCardService;
import com.whty.euicc.data.service.EuiccIsdPService;
@Service
@Transactional
public class EuiccCardServiceImpl implements EuiccCardService {
	@Autowired
	private EuiccCardMapper cardMapper;
	
	@Autowired
	private EuiccProfileMapper profileMapper;

	@Autowired
	private EuiccIsdPService isdPService;
	
	@Override
	public int insertSelective(EuiccCard card) {
		return cardMapper.insertSelective(card);
	}

	@Override
	public EuiccCard selectByPrimaryKey(String eid) {
		return cardMapper.selectByPrimaryKey(eid);
	}

	@Override
	public int updateSelectiveByPrimaryKey(EuiccCard card) {
		return cardMapper.updateByPrimaryKeySelective(card);
	}
	
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
	public void updateEIS(EuiccCard card,EuiccProfileWithBLOBs profile,EuiccIsdP isdP){
		int car = cardMapper.updateByPrimaryKeySelective(card);
		int pro = profileMapper.updateByPrimaryKeySelective(profile);
		int isd = isdPService.updateByEidAndIsdPAid(isdP);
		if(car == 0 || pro == 0 || isd == 0){
			throw new EuiccBusiException("0001", "更新card/profile/isd-p表异常");
		}
	}

	@Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
	public void deleteEIS(String eid, String iccid){
		int car = cardMapper.deleteByPrimaryKey(eid);
		int pro = profileMapper.deleteByPrimaryKey(iccid);
		if(car == 0 || pro == 0){
			throw new EuiccBusiException("0002", "删除card/profile表异常");
		}
	}

	@Override
	public int deleteByPrimaryKey(String eid) {
		return cardMapper.deleteByPrimaryKey(eid);
	}
}
