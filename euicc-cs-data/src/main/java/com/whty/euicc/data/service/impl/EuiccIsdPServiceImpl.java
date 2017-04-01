package com.whty.euicc.data.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whty.euicc.data.dao.EuiccIsdPMapper;
import com.whty.euicc.data.pojo.EuiccIsdP;
import com.whty.euicc.data.service.EuiccIsdPService;
@Service
@Transactional
public class EuiccIsdPServiceImpl implements EuiccIsdPService {
	private final String prefix = "A0000005591010FFFFFFFF8900";
	private final String subfix = "00";
	private final String beginCounter="0010";
	private final String endCounter="FFFF";
	
	@Autowired
	private EuiccIsdPMapper isdPMapper;


	@Override
	public int insertSelective(EuiccIsdP isdP) {
		return isdPMapper.insertSelective(isdP);
	}

	@Override
	public EuiccIsdP selectByPrimaryKey(String isdPAid) {
		return isdPMapper.selectByPrimaryKey(isdPAid);
	}

	@Override
	public int updateSelectiveByPrimaryKey(EuiccIsdP isdP) {
		return isdPMapper.updateByPrimaryKeySelective(isdP);
	}

	@Override
	public EuiccIsdP selectFirstByEid(String eid) {
		return isdPMapper.selectFirstByEid(eid);
	}

	@Override
	public EuiccIsdP selectByEidAndIsdPAid(String eid, String isdPAid) {
		return isdPMapper.selectByEidAndIsdPAid(eid, isdPAid);
	}

	@Override
	public String getIsdPAid(String eid) {
		String instanceAid = beginCounter;
		List<EuiccIsdP> isdPs = isdPMapper.selectListByEid(eid);
		for(EuiccIsdP isdP : isdPs){
			String instanceAidDb = isdP.getIsdPAid();
			if(StringUtils.isBlank(instanceAidDb)){
				continue;
			}
			int length = instanceAidDb.length();
			instanceAidDb = StringUtils.substring(instanceAidDb, length-6,length-2);
			instanceAid = compare(instanceAidDb,instanceAid);
		}
		String add = hexAdd(instanceAid);
		return prefix+add+subfix;
	}
	
	/**
	 * 返回较大的那个
	 * @param one
	 * @param two
	 * @return
	 */
	private String compare(String one,String two){
		int oneInt = Integer.parseInt(one, 16);
		int twoInt = Integer.parseInt(two, 16);
		return oneInt > twoInt ? one : two;
	}
	
	/**
	 * 十六进制加1
	 * @param instanceAid
	 * @return
	 */
	private  String hexAdd(String instanceAid){
		String nowCounter;
		int now = Integer.parseInt(instanceAid, 16)+1;
		int end = Integer.parseInt(endCounter, 16);
		if(now > end){
			nowCounter = beginCounter;
		}else{
			String hex = Integer.toHexString(now); 
			String encCounter="000000"+hex;//加密计数器增加
			nowCounter = encCounter.substring((encCounter.length()-4), encCounter.length()).toUpperCase();
		}
		return nowCounter;
	}

	@Override
	public int updateByEidAndIsdPAid(EuiccIsdP record) {
		return isdPMapper.updateByEidAndIsdPAid(record);
	}


	@Override
	public int insertIsdps(List<EuiccIsdP> isdPs) {
		return isdPMapper.insertIsdps(isdPs);
	}

	@Override
	public List<EuiccIsdP> selectListByEid(String eid) {
		return isdPMapper.selectListByEid(eid);
	}

	@Override
	public int deleteByEid(String eid) {
		return isdPMapper.deleteByEid(eid);
	}
	
	@Override
	public int deleteByEidAndIsdPAid(EuiccIsdP record) {
		return isdPMapper.deleteByEidAndIsdPAid(record);
	}
}
