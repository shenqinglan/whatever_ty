package com.whty.euicc.data.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.whty.euicc.common.constant.ProfileFallBack;
import com.whty.euicc.common.constant.ProfileState;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.data.dao.EuiccCardMapper;
import com.whty.euicc.data.dao.EuiccIsdPMapper;
import com.whty.euicc.data.dao.EuiccProfileMapper;
import com.whty.euicc.data.pojo.EuiccCard;
import com.whty.euicc.data.pojo.EuiccIsdP;
import com.whty.euicc.data.pojo.EuiccProfile;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.service.EuiccProfileService;
@Service
@Transactional
public class EuiccProfileServiceImpl implements EuiccProfileService {
	@Autowired
	private EuiccProfileMapper profileMapper;
	
	@Autowired
	private EuiccIsdPMapper isdPMapper;
	
	@Autowired
	private EuiccCardMapper cardMapper;
	
	@Override
	public EuiccProfileWithBLOBs selectByPrimaryKey(String iccid) {
		return profileMapper.selectByPrimaryKey(iccid);
	}

	@Override
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
	public void updateEIS(EuiccProfileWithBLOBs profile, EuiccIsdP isdP) {
		int isd = isdPMapper.updateByEidAndIsdPAid(isdP);
		int pro = profileMapper.updateByPrimaryKeySelective(profile);
		if(isd == 0 || pro == 0){
			throw new EuiccBusiException("0001", "更新profile/isd-p表异常");
		}
	}
	
	
	@Override
	public int checkCount(EuiccProfileWithBLOBs euiccProfile) {
		return profileMapper.countByPrimaryKey(euiccProfile);
	}
	
	@Override
	public EuiccProfileWithBLOBs selectByEidAndIsdPAid(
			EuiccProfileWithBLOBs euiccProfile) {
		return profileMapper.selectByEidAndIsdPAid(euiccProfile);
	}
	
	

	/**
	 * 启用profile
	 */
	@Override
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
	public void enableProfile(EuiccProfileWithBLOBs targetProfile) {
		if(StringUtils.isBlank(targetProfile.getIsdPAid())){
			throw new EuiccBusiException("0001", "目标profile找不到isd-p-aid");
		}
		//当前profile禁用
		EuiccProfileWithBLOBs record = new EuiccProfileWithBLOBs();
		record.setEid(targetProfile.getEid());
		record.setState(ProfileState.ENABLE);
		EuiccProfileWithBLOBs currentProfile = profileMapper.selectEuiccProfileByStateAndEid(record);
		if(!StringUtils.isBlank(currentProfile.getIsdPAid())){
			
			EuiccProfileWithBLOBs disableRecord = new EuiccProfileWithBLOBs();
			disableRecord.setIccid(currentProfile.getIccid());
			disableRecord.setState(ProfileState.DIS_ENABLE);
			int disable = profileMapper.updateByPrimaryKeySelective(disableRecord);
			if(disable == 0){
				throw new EuiccBusiException("0001", "当前profile禁用异常");
			}
		//当前isd-p禁用	
			EuiccIsdP currentIsdP =  new EuiccIsdP();
			currentIsdP.setEid(currentProfile.getEid());
			currentIsdP.setIsdPAid(currentProfile.getIsdPAid());
			currentIsdP.setIsdPState(ProfileState.DIS_ENABLE);
			currentIsdP.setUpdateDate(new Date());
			int isdPDisable = isdPMapper.updateByEidAndIsdPAid(currentIsdP);
			if(isdPDisable == 0){
				throw new EuiccBusiException("0001", "当前profile关联的isd-p禁用异常");
			}
		}
		//目标profile启用
		EuiccProfileWithBLOBs enableRecord = new EuiccProfileWithBLOBs();
		enableRecord.setIccid(targetProfile.getIccid());
		enableRecord.setState(ProfileState.ENABLE);
		int enable = profileMapper.updateByPrimaryKeySelective(enableRecord);
		if(enable == 0){
			throw new EuiccBusiException("0001", "目标profile启用异常");
		}
		
		//目标isd-p启用
		EuiccIsdP targetIsdP =  new EuiccIsdP();
		targetIsdP.setEid(targetProfile.getEid());
		targetIsdP.setIsdPAid(targetProfile.getIsdPAid());
		targetIsdP.setIsdPState(ProfileState.ENABLE);
		targetIsdP.setUpdateDate(new Date());
		int isdPEnable = isdPMapper.updateByEidAndIsdPAid(targetIsdP);
		if(isdPEnable == 0){
			throw new EuiccBusiException("0001", "目标profile关联的isd-p启用异常");
		}
		//把card表中的phone_no换成已经启用Profile的phone_no
		EuiccCard targetCard = new EuiccCard();
		targetCard.setEid(targetProfile.getEid());
		targetCard.setPhoneNo(targetProfile.getPhoneNo());
		int cardEnable = cardMapper.updateByPrimaryKeySelective(targetCard);
		if(cardEnable == 0){
			throw new EuiccBusiException("0001", "目标profile关联的card启用更新异常");
		}
	}
	/**
	 * 禁用profile
	 */
	@Override
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
	public void disableProfile(EuiccProfileWithBLOBs currentProfile) {
		if(StringUtils.isBlank(currentProfile.getIsdPAid())){
			throw new EuiccBusiException("0001", "当前profile找不到isd-p-aid");
		}
		//当前profile禁用
		EuiccProfileWithBLOBs disableRecord = new EuiccProfileWithBLOBs();
		disableRecord.setIccid(currentProfile.getIccid());
		disableRecord.setState(ProfileState.DIS_ENABLE);
		int disable = profileMapper.updateByPrimaryKeySelective(disableRecord);
		if(disable == 0){
			throw new EuiccBusiException("0002", "当前profile禁用异常");
		}
		//当前isd-p禁用	
		EuiccIsdP currentIsdP =  new EuiccIsdP();
		currentIsdP.setEid(currentProfile.getEid());
		currentIsdP.setIsdPAid(currentProfile.getIsdPAid());
		currentIsdP.setIsdPState(ProfileState.DIS_ENABLE);
		currentIsdP.setUpdateDate(new Date());
		int isdPDisable = isdPMapper.updateByEidAndIsdPAid(currentIsdP);
		if(isdPDisable == 0){
			throw new EuiccBusiException("0002", "当前profile关联的isd-p禁用异常");
		}
		//具有FallBack属性的profile启用
		EuiccProfileWithBLOBs record = new EuiccProfileWithBLOBs();
		record.setEid(currentProfile.getEid());
		record.setFallbackAttribute(ProfileFallBack.FALLBACK_YES);
		List<EuiccProfileWithBLOBs> profile = profileMapper.selectByEidAndFallback(record);
		if(profile == null){
			throw new EuiccBusiException("0002", "当前eUICC卡中没有具有FallBack属性的profile");
		}
		EuiccProfileWithBLOBs fallBackProfile = profile.get(0);
		EuiccProfileWithBLOBs enableRecord = new EuiccProfileWithBLOBs();
		enableRecord.setIccid(fallBackProfile.getIccid());
		enableRecord.setState(ProfileState.ENABLE);
		int enable = profileMapper.updateByPrimaryKeySelective(enableRecord);
		if(enable == 0){
			throw new EuiccBusiException("0002", "具有FallBack属性的profile启用异常");
		}
		//具有FallBack属性的isd-p启用
		EuiccIsdP fallBackIsdP =  new EuiccIsdP();
		fallBackIsdP.setEid(fallBackProfile.getEid());
		fallBackIsdP.setIsdPAid(fallBackProfile.getIsdPAid());
		fallBackIsdP.setIsdPState(ProfileState.ENABLE);
		fallBackIsdP.setUpdateDate(new Date());
		int isdPFallBack = isdPMapper.updateByEidAndIsdPAid(fallBackIsdP);
		if(isdPFallBack == 0){
			throw new EuiccBusiException("0002", "目标profile关联的isd-p启用异常");
		}
		//把card表中的phone_no换成已经启用Profile的phone_no
		EuiccCard fallBackCard = new EuiccCard();
		fallBackCard.setEid(fallBackProfile.getEid());
		fallBackCard.setPhoneNo(fallBackProfile.getPhoneNo());
		int cardFallBack = cardMapper.updateByPrimaryKeySelective(fallBackCard);
		if(cardFallBack == 0){
			throw new EuiccBusiException("0002", "目标profile关联的card禁用更新异常");
		}
		
	}
	/**
	 * 删除profile
	 */
	@Override
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
	public void deleteProfile(EuiccProfileWithBLOBs profile) {
		if(StringUtils.isBlank(profile.getIsdPAid())){
			throw new EuiccBusiException("0003", "当前profile找不到isd-p-aid");
		}
		//目标profile删除
		EuiccProfileWithBLOBs deleteRecord = new EuiccProfileWithBLOBs();
		deleteRecord.setIccid(profile.getIccid());
		deleteRecord.setState(ProfileState.INIT);
		deleteRecord.setIsdPAid("");
		int delete = profileMapper.updateByPrimaryKeySelective(deleteRecord);
		if(delete == 0){
			throw new EuiccBusiException("0003", "当前profile删除异常");
		}
		//目标isd-p删除
		EuiccIsdP isdP = new EuiccIsdP();
		isdP.setEid(profile.getEid());
		isdP.setIsdPAid(profile.getIsdPAid());
		int isdPDelete = isdPMapper.deleteByEidAndIsdPAid(isdP);
		if(isdPDelete == 0){
			throw new EuiccBusiException("0003", "当前profile关联的isd-p删除异常");
		}
		
	}
	/**
	 * 查询及更新profile状态
	 */
	@Override
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
	public void updateStatus(EuiccProfileWithBLOBs profile) {
		if(StringUtils.isBlank(profile.getIsdPAid())){
			throw new EuiccBusiException("0004", "查询的profile找不到isd-p-aid");
		}
		//卡状态
		String cardStatus = profile.getState();
		EuiccProfileWithBLOBs hostRecord = profileMapper.selectByPrimaryKey(profile.getIccid());
		String hostStatus = hostRecord.getState();
		//比较数据库中的数据
		if(!StringUtils.equals(cardStatus,hostStatus) && !StringUtils.equals(cardStatus,"99")){
			//profile状态
			EuiccProfileWithBLOBs statusRecord = new EuiccProfileWithBLOBs();
			statusRecord.setIccid(profile.getIccid());
			statusRecord.setState(cardStatus);
			int status = profileMapper.updateByPrimaryKeySelective(statusRecord);
			if(status == 0){
				throw new EuiccBusiException("0004", "查询的profile更新异常");
			}
			//isdP状态
			EuiccIsdP isdP =  new EuiccIsdP();
			isdP.setEid(profile.getEid());
			isdP.setIsdPAid(profile.getIsdPAid());
			isdP.setIsdPState(cardStatus);
			isdP.setUpdateDate(new Date());
			int isdPStatus = isdPMapper.updateByEidAndIsdPAid(isdP);
			if(isdPStatus == 0){
				throw new EuiccBusiException("0004", "查询的profile关联的isd-p更新异常");
			}
			//若状态更新后当前Profile在卡中的状态为启用，则变更card表中的当前手机号
			//把card表中的phone_no换成已经启用Profile的phone_no
			if(StringUtils.equals(cardStatus,"01")){
				EuiccCard card = new EuiccCard();
				card.setEid(hostRecord.getEid());
				card.setPhoneNo(hostRecord.getPhoneNo());
				int acard = cardMapper.updateByPrimaryKeySelective(card);
				if(acard == 0){
					throw new EuiccBusiException("0004", "查询的profile关联的card更新异常");
				}
			}
			
		}
		
		
	}

	/**
	 * fallback状态下的启用profile
	 * 查询fall-back状态，如果是激活状态，启用，不是激活，报错	
	 */
	@Override
	public boolean fallBackEnableProfile(EuiccProfileWithBLOBs profile) {
		EuiccProfileWithBLOBs euiccProfile = selectByEidAndIsdPAid(profile);
		String fallBackValue = euiccProfile.getFallbackAttribute();
		if (!"00".equals(fallBackValue)) {
			return false;
		}

		enableProfile(profile);	
		return true;
	}

	@Override
	public int updateByPrimaryKeySelective(EuiccProfileWithBLOBs record) {
		return profileMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public EuiccProfileWithBLOBs selectEuiccProfileByStateAndEid(String state ,String eid) {
		EuiccProfileWithBLOBs record = new EuiccProfileWithBLOBs();
		record.setEid(eid);
		record.setState(state);
		return profileMapper.selectEuiccProfileByStateAndEid(record);
	}

	@Override
	public List<EuiccProfileWithBLOBs> selectByEid(String eid) {
		return profileMapper.selectByEid(eid);
	}

	@Override
	public int deleteByEid(String eid) {
		return profileMapper.deleteByEid(eid);
	}

	@Override
	public int insertProfiles(List<EuiccProfileWithBLOBs> profiles) {
		return profileMapper.insertProfiles(profiles);
	}

	@Override
	public int updateFallBackAttr(EuiccProfileWithBLOBs profile) {
		return profileMapper.updateByPrimaryKeySelective(profile);
	}

	@Override
	public List<EuiccProfileWithBLOBs> selectByEidAndFallBack(
			EuiccProfileWithBLOBs profile) {
		// TODO Auto-generated method stub
		return profileMapper.selectByEidAndFallback(profile);
	}

	@Override
	public EuiccProfileWithBLOBs selectByEidAndIsdPAid(String eid,
			String isdPAid) {
		
		return profileMapper.selectByEidAndIsdPAid(eid, isdPAid);
	}
	
}
