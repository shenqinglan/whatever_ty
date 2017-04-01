package com.whty.euicc.data.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.common.utils.UUIDUtil;
import com.whty.euicc.data.constant.ProfileFallBack;
import com.whty.euicc.data.constant.ProfileState;
import com.whty.euicc.data.dao.EuiccCardMapper;
import com.whty.euicc.data.dao.EuiccIsdPMapper;
import com.whty.euicc.data.dao.EuiccProfileMapper;
import com.whty.euicc.data.pojo.CardInfo;
import com.whty.euicc.data.pojo.EuiccCard;
import com.whty.euicc.data.pojo.EuiccIsdP;
import com.whty.euicc.data.pojo.EuiccProfile;
import com.whty.euicc.data.pojo.EuiccProfileExample;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.service.EuiccProfileService;
/**
 * @author dzmsoft
 * @date 2016-08-03 16:15
 *
 * @version 1.0
 */
@Service
@Transactional
public class EuiccProfileServiceImpl implements EuiccProfileService{
	private Logger logger = LoggerFactory.getLogger(EuiccProfileServiceImpl.class);

	@Autowired
	private EuiccProfileMapper euiccProfileMapper;
	
	@Autowired
	private EuiccIsdPMapper euiccIsdPMapper;
	
	private final String LOAD_FILE_AID = "A0000005591010FFFFFFFF8900000D00";//todo
	private final String MODULE_AID =    "A0000005591010FFFFFFFF8900000E00";//todo
	
	private final String prefix = "A0000005591010FFFFFFFF8900";
	private final String subfix = "00";
	private final String beginCounter="0010";
	private final String endCounter="FFFF";
	
	@Autowired
	private EuiccIsdPMapper isdPMapper;
	@Autowired
	private EuiccCardMapper cardMapper;
	
	/**
     * 根据主键查询记录
     * @dzmsoftgenerated 2016-08-03 16:15
     */
	public EuiccProfileWithBLOBs selectByPrimaryKey(String id){
		return euiccProfileMapper.selectByPrimaryKey(id);
	}
	
	/**
     * 分页查询记录
     * @dzmsoftgenerated 2016-08-03 16:15
     */
	public PageList<EuiccProfile> selectByExample(EuiccProfile profile,EuiccProfileExample example,PageBounds pageBounds){
		if(StringUtils.equalsIgnoreCase(profile.getOptType(), "install")){
			return euiccProfileMapper.selectByExampleForInstall(profile.getCardEid(), pageBounds);
		}
		return euiccProfileMapper.selectByExample(example, pageBounds);
	}
	
	/**
     * 根据条件查询记录
     * @dzmsoftgenerated 2016-08-03 16:15
     */
	public List<EuiccProfile> selectByExample(EuiccProfileExample example){
		return euiccProfileMapper.selectByExample(example);
	}
	

	
	/**
     * 根据主键生成记录
     * @dzmsoftgenerated 2016-08-03 16:15
     */
	public int deleteByPrimaryKey(String id){
		return euiccProfileMapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 根据条件删除字段信息
	 * @dzmsoftgenerated 2016-08-03 16:15
	 * @param example
	 * @return
	 */
	public int deleteByExample(EuiccProfileExample example){
		return euiccProfileMapper.deleteByExample(example);
	}

	public int updateByPrimaryKeySelective(EuiccProfileWithBLOBs record) {
		return euiccProfileMapper.updateByPrimaryKeySelective(record);
	}
	
	public int updatePol2ByPrimaryKey(EuiccProfile record) {
		return euiccProfileMapper.updatePol2ByPrimaryKey(record);
	}
	
	public int insertSelective(EuiccProfileWithBLOBs record) {
		return euiccProfileMapper.insertSelective(record);
	}
	
	public int insertProfileSelective(EuiccProfile record) {
		return euiccProfileMapper.insertProfileSelective(record);
	}

	public PageList<EuiccProfileWithBLOBs> selectByExampleWithBLOBs(
			EuiccProfileExample example, PageBounds pageBounds) {
		// TODO Auto-generated method stub
		return euiccProfileMapper.selectByExampleWithBLOBs(example, pageBounds);
	}
	
	/**
	 * 删除profile
	 */
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
	public void deleteProfile(EuiccProfileWithBLOBs profile) {
		if(StringUtils.isBlank(profile.getIsdPAid())){
			throw new EuiccBusiException("0001", "当前profile找不到isd-p-aid");
		}
		//当前profile删除
		EuiccProfileWithBLOBs record = new EuiccProfileWithBLOBs();
		record.setIccid(profile.getIccid());
		record.setState(ProfileState.DELETE);
		int delete = euiccProfileMapper.updateByPrimaryKeySelective(record);
				
		//当前isd-p删除
		EuiccIsdP isdP =  new EuiccIsdP();
		isdP.setEid(profile.getEid());
		isdP.setIsdPAid(profile.getIsdPAid());
		isdP.setIsdPState(ProfileState.DELETE);
		isdP.setUpdateDate(new Date());
		int isdPEnable = euiccIsdPMapper.updateByEidAndIsdPAid(isdP);
		
		if(delete == 0 || isdPEnable == 0){
			throw new EuiccBusiException("0001", "deleteProfile异常");
		}
		
	}

	/**
	 * 启用profile
	 */
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
	public void enableProfile(EuiccProfileWithBLOBs profile) {
		if(StringUtils.isBlank(profile.getIsdPAid())){
			throw new EuiccBusiException("0001", "当前profile找不到isd-p-aid");
		}
		//其它profile禁用
		EuiccProfileWithBLOBs disableRecord = new EuiccProfileWithBLOBs();
		disableRecord.setEid(profile.getEid());
		disableRecord.setState(ProfileState.DIS_ENABLE);
		int disable = euiccProfileMapper.updateByEid(disableRecord);
		
				
		//当前profile启用
		EuiccProfileWithBLOBs enableRecord = new EuiccProfileWithBLOBs();
		enableRecord.setIccid(profile.getIccid());
		enableRecord.setState(ProfileState.ENABLE);
		int enable = euiccProfileMapper.updateByPrimaryKeySelective(enableRecord);
		if(enable == 0){
			throw new EuiccBusiException("0001", "当前profile启用异常");
		}
		
		//当前isd-p启用
		
		EuiccIsdP isdP =  new EuiccIsdP();
		isdP.setEid(profile.getEid());
		isdP.setIsdPAid(profile.getIsdPAid());
		isdP.setIsdPState(ProfileState.ENABLE);
		isdP.setUpdateDate(new Date());
		int isdPEnable = euiccIsdPMapper.updateByEidAndIsdPAid(isdP);
		
		if(isdPEnable == 0){
			throw new EuiccBusiException("0001", "当前profile关联的isd-p启用异常");
		}
		//把card表中的phone_no换成已经启用Profile的phone_no
		CardInfo enableCard = new CardInfo();
		enableCard.setEid(profile.getEid());
		enableCard.setPhoneNo(profile.getPhoneNo());
		int cardEnable = cardMapper.updateEuiccCardSelective(enableCard);
		if(cardEnable == 0){
			throw new EuiccBusiException("0001", "目标profile关联的card启用更新异常");
		}
	}

	/**
	 * 禁用
	 */
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
	public void disableProfile(EuiccProfileWithBLOBs profile) {
		if(StringUtils.isBlank(profile.getIsdPAid())){
			throw new EuiccBusiException("0001", "当前profile找不到isd-p-aid");
		}
		//具有fallback属性的启用
		EuiccProfileWithBLOBs query = new EuiccProfileWithBLOBs();
		query.setEid(profile.getEid());
		query.setFallbackAttribute(ProfileFallBack.FALLBACK_YES);
		List<EuiccProfileWithBLOBs> profiles = euiccProfileMapper.selectByEidAndFallback(query);
		if(profiles == null || profiles.size() !=1){
			logger.error("回退属性不存在");
			throw new EuiccBusiException("0001", "回退属性不存在");
		}
		if(profiles == null || profiles.size() !=1){
			logger.error("回退属性有多个");
			throw new EuiccBusiException("0001", "回退属性有多个");
		}
		EuiccProfileWithBLOBs profileBlob = profiles.get(0);
		EuiccProfileWithBLOBs enableRecord = new EuiccProfileWithBLOBs();
		enableRecord.setIccid(profileBlob.getIccid());
		enableRecord.setState(ProfileState.ENABLE);
		int enable = euiccProfileMapper.updateByPrimaryKeySelective(enableRecord);
		
		//当前isd-p禁用
		EuiccIsdP isdP =  new EuiccIsdP();
		isdP.setEid(profile.getEid());
		isdP.setIsdPAid(profile.getIsdPAid());
		isdP.setIsdPState(ProfileState.DIS_ENABLE);
		isdP.setUpdateDate(new Date());
		int isdPDisable = euiccIsdPMapper.updateByEidAndIsdPAid(isdP);
		
		//当前profile禁用
		EuiccProfileWithBLOBs disableRecord = new EuiccProfileWithBLOBs();
		disableRecord.setIccid(profile.getIccid());
		disableRecord.setState(ProfileState.DIS_ENABLE);
		int disable = euiccProfileMapper.updateByPrimaryKeySelective(disableRecord);
		
		if(disable == 0 || enable == 0 || isdPDisable == 0){
			throw new EuiccBusiException("0001", "disableProfile异常");
		}
		//把card表中的phone_no换成已经启用Profile的phone_no
		CardInfo enableCard = new CardInfo();
		enableCard.setEid(profileBlob.getEid());
		enableCard.setPhoneNo(profileBlob.getPhoneNo());
		int cardEnable = cardMapper.updateEuiccCardSelective(enableCard);
		if(cardEnable == 0){
			throw new EuiccBusiException("0002", "目标profile关联的card禁用更新异常");
		}
	}
	

	/**
	 * 查询Profile状态
	 */
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
	public void getStatus(EuiccProfileWithBLOBs profile) {
		if(StringUtils.isBlank(profile.getIsdPAid())){
			throw new EuiccBusiException("0004", "查询的profile找不到isd-p-aid");
		}
		//卡状态
		String cardStatus = profile.getState();
		EuiccProfileWithBLOBs hostRecord = euiccProfileMapper.selectByPrimaryKey(profile.getIccid());
		String hostStatus = hostRecord.getState();
		//比较数据库中的数据
		if(!StringUtils.equals(cardStatus,hostStatus)){
			//profile状态
			EuiccProfileWithBLOBs statusRecord = new EuiccProfileWithBLOBs();
			statusRecord.setIccid(profile.getIccid());
			statusRecord.setState(cardStatus);
			int status = euiccProfileMapper.updateByPrimaryKeySelective(statusRecord);
			if(status == 0){
				throw new EuiccBusiException("0004", "查询的profile删更新异常");
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
		}
	}
	
	/**
	 * 创建ISD-P
	 * @param record
	 */
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
	public void createIsdP(EuiccProfileWithBLOBs record) {
		String isdPAid = getIsdPAid(record.getEid());
		record.setIsdPAid(isdPAid);
		updateByPrimaryKeySelective(record);
		insertIsdP(record.getEid(),isdPAid);
		
	}
	
	private void insertIsdP(String eid,String isdPAid) {
		Date date = new Date();
		EuiccIsdP isdP = new EuiccIsdP();
		isdP.setpId(UUIDUtil.getUuidString());
		isdP.setIsdPAid(isdPAid);
		isdP.setEid(eid);//
		isdP.setIsdPLoadfileAid(LOAD_FILE_AID);
		isdP.setIsdPModuleAid(MODULE_AID);
		isdP.setIsdPState(ProfileState.TEMP);//
		isdP.setCreateDate(date);
		isdP.setUpdateDate(date);
		isdPMapper.insertSelective(isdP);
	}
	
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
	public EuiccProfileWithBLOBs selectEuiccProfileByStateAndEid(String state ,String eid) {
		EuiccProfileWithBLOBs record = new EuiccProfileWithBLOBs();
		record.setEid(eid);
		record.setState(state);
		return euiccProfileMapper.selectEuiccProfileByStateAndEid(record);
	}

	@Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
	public void updateConnectParas(EuiccProfileWithBLOBs profile) {
		// TODO Auto-generated method stub
		
	}
	
}
