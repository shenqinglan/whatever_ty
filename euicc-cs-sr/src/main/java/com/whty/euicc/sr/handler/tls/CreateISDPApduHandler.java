package com.whty.euicc.sr.handler.tls;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.whty.cache.CacheUtil;
import com.whty.euicc.common.apdu.ToTLV;
import com.whty.euicc.common.constant.ProfileState;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.common.utils.TlsMessageUtils;
import com.whty.euicc.common.utils.UuidUtil;
import com.whty.euicc.data.pojo.EuiccIsdP;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.data.service.EuiccIsdPService;
import com.whty.euicc.data.service.EuiccProfileService;
import com.whty.euicc.handler.base.AbstractHandler;
import com.whty.euicc.profile.util.Tool;
import com.whty.euicc.trigger.SmsTriggerUtil;

/**
 * 下发创建ISD-P的apdu指令
 * @author Administrator
 *
 */
@Service("createISDPApdu")
public class CreateISDPApduHandler extends AbstractHandler{
	private Logger logger = LoggerFactory.getLogger(CreateISDPApduHandler.class);
	
	private final String TAG = "22";
	private final String CLA = "80";
	private final String INS = "E6";
	private final String P1 = "0C";
	private final String P2 = "00";
	private String Lc;
	private final String Le = "00";
	
	private final String LOAD_FILE_LENGTH = "10";
	private final String LOAD_FILE_AID_DEFAULT = "A0000005591010FFFFFFFF8900000D00";//todo
	private String LOAD_FILE_AID;// = "A0000005591010FFFFFFFF8900000D00";//todo
	private final String MODULE_LENGTH = "10";
	private final String MODULE_AID_DEFAULT =    "A0000005591010FFFFFFFF8900000E00";//todo
	private String MODULE_AID;// =    "A0000005591010FFFFFFFF8900000E00";//todo
	private final String ISD_P_AID_LENGTH = "10";
	private String ISD_P_AID;//"A0000005591010FFFFFFFF8900001300";//todo
	private String P_ID;
	private final String PRIVILEGES_LEGTH = "03";
	private final String PRIVILEGES = "80c000";
	private final String INSTALL_PARAM_LEGTH = "06";
	private final String INSTALL_PARAM = "C90481020370";
	private final String INSTALL_TOKEN_LEGTH = "00";
		
	@Autowired
	private EuiccIsdPService isdPService;
	@Autowired
	private EuiccProfileService profileService;
	/**
	 * 下发创建ISD-P指令
	 */
	@Override
	public byte[] handle(String requestStr) {
		String eid = TlsMessageUtils.getEid(requestStr);
		byte[] apdu = createISDPApdu(eid);
		insertIsdP(eid);
		updateTrigger(eid);
		return httpPostResponseNoEnc(new String(apdu));
	}
	
	/**
	 * 检查返回结果,通知前端调用者，更新EIS
	 */
	@Override
	public boolean checkProcessResp(String requestStr) {
		boolean processResult = true;
		SmsTrigger eventTrigger = null;
		try {
			String eid = TlsMessageUtils.getEid(requestStr);
			String sms = CacheUtil.getString(eid);	
		    eventTrigger = new Gson().fromJson(sms,SmsTrigger.class);
			checkResp(requestStr);
			updateEIS(eventTrigger);
		} catch (Exception e) {
			logger.error("checkProcessResp error:{}",e.getMessage());
			eventTrigger.setErrorMsg(e.getMessage());
			e.printStackTrace();
			processResult = false;
		}finally{
			SmsTriggerUtil.notifyProcessResult(eventTrigger,processResult);
		}
		return processResult;
	}

	
	private void updateTrigger(String eid) {
		String sms = CacheUtil.getString(eid);	
		SmsTrigger eventTrigger = new Gson().fromJson(sms,SmsTrigger.class);
		eventTrigger.setIsdPAid(ISD_P_AID);
		CacheUtil.put(eid, new Gson().toJson(eventTrigger));		
	}

	private void insertIsdP(String eid) {
		Date date = new Date();
		EuiccIsdP isdP = new EuiccIsdP();
		isdP.setpId(P_ID);
		isdP.setIsdPAid(ISD_P_AID);
		isdP.setEid(eid);//
		isdP.setIsdPLoadfileAid(LOAD_FILE_AID);
		isdP.setIsdPModuleAid(MODULE_AID);
		isdP.setIsdPState(ProfileState.TEMP);//
		isdP.setCreateDate(date);
		isdP.setUpdateDate(date);
		EuiccIsdP isdPByDb = isdPService.selectByEidAndIsdPAid(eid,ISD_P_AID);
		if(isdPByDb != null){
			throw new RuntimeException(ISD_P_AID+"The database has been in existence");
		}
		isdPService.insertSelective(isdP);
	}

	
	/**
	 * 人为抛异常为检查不合格
	 */
	private void checkResp(String requestStr) {
		logger.info("card resp:{}",requestStr);
		int endOfDbl0D0A=requestStr.indexOf("\r\n\r\n")+4;
		System.out.println(endOfDbl0D0A);
		String strData = requestStr.substring(endOfDbl0D0A, requestStr.length()-4);
		logger.info("apdu:{}",requestStr);
		String dataCheck = paserCardContent(strData);
		String errorNote = "";
		if(StringUtils.equals(dataCheck,"9000")){   //相应返回值为‘9000’，则命令执行成功
			errorNote = "Command execution success";
		}else if(StringUtils.equals(dataCheck,"6A88")){  //相应返回值为‘6A88’，则引用数据无法找到
			errorNote = "Referenced data not found";
		}else if(StringUtils.equals(dataCheck,"6A84")){  //相应返回值为‘6A84’，则内存空间不够
			errorNote = "Not enough memory space";
		}else if(StringUtils.equals(dataCheck,"6A80")){  //相应返回值为‘6A80’，则APDU指令data域参数不正确
			errorNote = "Incorrect parameters in data field";
		}else if(StringUtils.equals(dataCheck,"6581")){  //相应返回值为‘6581’，则存储失败
			errorNote = "Memory failure";
		}else{
			errorNote = dataCheck + "The returned value of currently Enabled Profile has other error";
		}
		logger.info("card check:{}",errorNote);	
		if(!StringUtils.equals(dataCheck,"9000")){
			throw new EuiccBusiException("8010","卡片返回值不为9000");
		}
		
	}

	private byte[] createISDPApdu(String eid) {
		EuiccIsdP isdP = isdPService.selectFirstByEid(eid);
		if(isdP == null){
			LOAD_FILE_AID = LOAD_FILE_AID_DEFAULT;
			MODULE_AID = MODULE_AID_DEFAULT;
		}else{
			LOAD_FILE_AID = StringUtils.defaultIfBlank(isdP.getIsdPLoadfileAid(),LOAD_FILE_AID_DEFAULT);
			MODULE_AID = StringUtils.defaultIfBlank(isdP.getIsdPModuleAid(),MODULE_AID_DEFAULT);
		}
		ISD_P_AID = isdPService.getIsdPAid(eid);
		//ISD_P_AID = "A0000005591010FFFFFFFF8900001300";
		P_ID = UuidUtil.createId();
		StringBuilder data = new StringBuilder().append(LOAD_FILE_LENGTH).append(LOAD_FILE_AID)
				.append(MODULE_LENGTH).append(MODULE_AID).append(ISD_P_AID_LENGTH).append(ISD_P_AID)
				.append(PRIVILEGES_LEGTH).append(PRIVILEGES).append(INSTALL_PARAM_LEGTH).append(INSTALL_PARAM).append(INSTALL_TOKEN_LEGTH);
		Lc = Tool.toHex(String.valueOf(data.toString().length()/2));
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1).append(P2).append(Lc).append(data).append(Le);
		//<--! %sCommand[1] = @totlv(22,%sCommand[1])
		String lenValue = ToTLV.toTLV(apdu.toString());
		StringBuilder apduFinal = new StringBuilder().append(TAG).append(lenValue);
		logger.info("下发的创建ISD-P的APDU指令为:{}",apduFinal.toString());
		return apduFinal.toString().getBytes();
	}

	
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
	private void updateEIS(SmsTrigger eventTrigger) {
		EuiccIsdP isdP = new EuiccIsdP();
		isdP.setEid(eventTrigger.getEid());
		isdP.setIsdPAid(eventTrigger.getIsdPAid());
		isdP.setIsdPState(ProfileState.CREATE_ISD_P_STATE_SUCCESS);
		isdP.setUpdateDate(new Date());
		isdPService.updateByEidAndIsdPAid(isdP);
		
		EuiccProfileWithBLOBs record = new EuiccProfileWithBLOBs();
		record.setEid(eventTrigger.getEid());
		record.setIccid(eventTrigger.getIccid());
		record.setIsdPAid(eventTrigger.getIsdPAid());
		logger.info("record.Iccid(): " + record.getIccid());
		record.setState(ProfileState.CREATE_ISD_P_STATE_SUCCESS);
		logger.info("record.state: " + record.getState());
		profileService.updateByPrimaryKeySelective(record);
	}
	/**
	 * 截取返回码
	 * @param responseData
	 * @return
	 */
	private String paserCardContent(String responseData){
		String IsdPCheck = "1111";
		if(!responseData.substring(2, 12).equals("0D0AAF8023")){
			return "9999";
		}
		int offSize = responseData.indexOf("00000D0A30");
		IsdPCheck = responseData.substring(offSize-4, offSize);
		return IsdPCheck;
	}


}
