package com.whty.euicc.dp.handler;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.constant.ProfileState;
import com.whty.euicc.common.exception.ErrorCode;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.common.https.BaseHttp;
import com.whty.euicc.common.utils.UuidUtil;
import com.whty.euicc.data.pojo.EuiccIsdP;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.pojo.EuiccScp03;
import com.whty.euicc.data.service.EuiccIsdPService;
import com.whty.euicc.data.service.EuiccProfileService;
import com.whty.euicc.data.service.EuiccScp03Service;
import com.whty.euicc.dp.handler.tls.personal.FirstStoreDataforISDPApdu;
import com.whty.euicc.handler.base.HttpHandler;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.DeleteProfileByHttpsReqBody;
import com.whty.euicc.packets.message.request.PersonalAllISDPReqBody;
import com.whty.euicc.packets.message.request.PersonalISDPReqBody;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
import com.whty.security.aes.AesCmac;
import com.whty.security.ecc.ECCUtils;
/**
 * 个人化dp触发流程
 * @author zyj,lw
 *
 */
@Service("personalAllISDP")
public class PersonalAllISDPHandler implements HttpHandler{

	private Logger logger = LoggerFactory.getLogger(PersonalAllISDPHandler.class);
	
	private final String P="ffffffff00000001000000000000000000000000ffffffffffffffffffffffff";
	private final String A="FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFC";
	private final String B="5ac635d8aa3a93e7b3ebbd55769886bc651d06b0cc53b0f63bce3c3e27d2604b";
	private final String Gx="6b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296";
	private final String Gy="4fe342e2fe1a7f9b8ee7eb4a7c0f9e162bce33576b315ececbb6406837bf51f5";
	private final String N="FFFFFFFF00000000FFFFFFFFFFFFFFFFBCE6FAADA7179E84F3B9CAC2FC632551";
	private final String H="1";
	//定义一组ECASD的ECC公私密钥（用于密钥协商）
	private final String Q_ECASD_ECKA="3F157A6242EC54888EB50967BD84D1A885E51D66B2F6CD135354724C91D790EDB48B015F6B272DF50E49EAB2E1383BF0EEB0E9848543B971397D274D88E8EA77";
	private final String Qx_ECASD_ECKA=Q_ECASD_ECKA.substring(0, 64);
	private final String Qy_ECASD_ECKA=Q_ECASD_ECKA.substring(64);
	private final int Keylen=64;
	private final String share="108810";
	private String key;
	private final String eskEcdsa="7C766F81C0C7FD7139EDCB7B51EFBA2E49841D725A45B8B11D9AA144D5076037";
	@Autowired
	private EuiccProfileService profileService;
	@Autowired
	private EuiccIsdPService isdPService;
	@Autowired
	private EuiccScp03Service scp03Service;
	
	@Autowired
	private FirstStoreDataforISDPApdu first;
	
	@Override
	public byte[] handle(String requestStr) {
		RespMessage respMessage = new RespMessage("0000","success");
		PersonalAllISDPReqBody reqBody = null;
		try {
			EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
			reqBody = (PersonalAllISDPReqBody) reqMsgObject.getBody();
			byte[] CERT_DP_ECDSA=first.firstStoreDataForISDPApdu(reqBody.getEid());
			
			String keyPairs=ECCUtils.createECCKeyPair(P, A, B, Gx, Gy, N, H);
			if(StringUtils.isBlank(keyPairs)){
				throw new RuntimeException("生成密钥对算法出错");
			}
			String eSK_DP=keyPairs.substring(0, 64);
			logger.info("eSK_DP_ECKA>>"+eSK_DP);
			String ePK_DP="04"+keyPairs.substring(64);
			logger.info("ePK_DP_ECKA>>"+ePK_DP);
			RespMessage message = allPersonalIsdP(reqBody.getEid(),reqBody.getIccid(),new String(CERT_DP_ECDSA),eskEcdsa,ePK_DP);
			if(!StringUtils.equals(ErrorCode.SUCCESS, message.getCode())){
				//删除ISD-P
				logger.info("认证出错，删除isdp");
				profileDeleteByHttps(reqBody);
				throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
	        }
		
			String receiptFromEuicc = message.getData();
			logger.info("receiptFromEuicc >>> "+receiptFromEuicc);
			key=ECCUtils.eccKeyAgreement(P, A, B, Gx, Gy, N, H, eSK_DP, Qx_ECASD_ECKA, Qy_ECASD_ECKA, share, Keylen);
			logger.info("personal key:{}",key);
			if(StringUtils.isBlank(key)){
				throw new RuntimeException("生成shs算法出错");
			}
			String receipt=key.substring(0, 32);
			
			String calKey="A615900203099501108001888101108201018301309100";
			String receiptExcept=new AesCmac().calcuCmac1(calKey, receipt);//脚本%sReceiptExpect = @CMAC(%sreceipt,%sA6)
			logger.info("receiptExcept >>> "+receiptExcept);
			
			String enc=key.substring(32, 64);
			String mac=key.substring(64,96);
			String dek=key.substring(96,128);
			logger.info("personal enc:{}",enc);
			logger.info("personal mac:{}",mac);
			logger.info("personal dek:{}",dek);
			
			//先放过
			if(!StringUtils.equals(receiptExcept, receiptFromEuicc)){
				throw new EuiccBusiException("0001","check ["+receipt+"] is not equals "+receiptFromEuicc);
			}
			EuiccProfileWithBLOBs profileBean = getProfile(reqBody.getIccid());
			String isdPAID = profileBean.getIsdPAid();
			saveScp03(reqBody.getEid(),isdPAID,enc,mac,dek);
			updateState(reqBody.getEid() ,isdPAID,reqBody.getIccid());
		} catch (Exception e) {
			logger.error("personal isd-p error:{}",e.getMessage());
			//返回失败信息
			if(e instanceof EuiccBusiException){
				EuiccBusiException eb = (EuiccBusiException) e;
				respMessage = new RespMessage(eb.getCode(),eb.getMessage());
			}else{
				respMessage = new RespMessage("9999",e.getMessage());
			}
		}
		
		return new Gson().toJson(respMessage).getBytes();
	}

	/**
	 * 将scp03相关密钥传入数据库
	 * @param eid
	 * @param enc
	 * @param mac
	 * @param dek
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveScp03(String eid,String isdPAid, String enc, String mac, String dek) {
		EuiccScp03 record = new EuiccScp03();
		record.setEid(eid);
		record.setIsdPAid(isdPAid);
		scp03Service.deleteByEidAndIsdPAid(record);
		
		EuiccScp03 firstScp03=new EuiccScp03();
		firstScp03.setScp03Id(UuidUtil.createId());
		firstScp03.setEid(eid);
		firstScp03.setIsdPAid(isdPAid);
		firstScp03.setId("01");
		firstScp03.setVersion("01");
		firstScp03.setData(enc);
		scp03Service.insert(firstScp03);
		
		EuiccScp03 secondScp03=new EuiccScp03();
		secondScp03.setScp03Id(UuidUtil.createId());
		secondScp03.setEid(eid);
		secondScp03.setIsdPAid(isdPAid);
		secondScp03.setId("02");
		secondScp03.setVersion("01");
		secondScp03.setData(mac);
		scp03Service.insert(secondScp03);
		
		EuiccScp03 thirdScp03=new EuiccScp03();
		thirdScp03.setScp03Id(UuidUtil.createId());
		thirdScp03.setEid(eid);
		thirdScp03.setIsdPAid(isdPAid);
		thirdScp03.setId("03");
		thirdScp03.setVersion("01");
		thirdScp03.setData(dek);
		scp03Service.insert(thirdScp03);
		
	}

	/**
	 * 将修改数据库中相应isdP,Profile状态
	 * @param eid
	 * @param enc
	 * @param mac
	 * @param dek
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void updateState(String eid,String isdPAid, String iccid){
		EuiccIsdP isdP = new EuiccIsdP();
		isdP.setEid(eid);
		isdP.setIsdPAid(isdPAid);
		isdP.setIsdPState(ProfileState.PERSONAL_ISD_P_STATE_SUCCESS);
		isdP.setUpdateDate(new Date());
		isdPService.updateByEidAndIsdPAid(isdP);
		EuiccProfileWithBLOBs record = new EuiccProfileWithBLOBs();
		record.setEid(eid);
		record.setIccid(iccid);
		record.setState(ProfileState.PERSONAL_ISD_P_STATE_SUCCESS);
		profileService.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * DP向SR发送数据
	 * @param eid
	 * @param iccid
	 * @param cERTDPECDSA
	 * @return 
	 * @throws Exception
	 */
	public RespMessage allPersonalIsdP(String eid, String iccid, String certDpEcdsa,String eskEcdsa,String epkDp)throws Exception{
        MsgHeader header = new MsgHeader("personalISDP");
        PersonalISDPReqBody requestBody = new PersonalISDPReqBody();
		requestBody.setEid(eid);
		requestBody.setIccid(iccid);
		requestBody.setCertDpEcdsa(certDpEcdsa);
		requestBody.setEskDp(eskEcdsa);
		requestBody.setEpkDp(epkDp);
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
		return respMessage;
	}
	private EuiccProfileWithBLOBs getProfile(String iccid) {
		return profileService.selectByPrimaryKey(iccid);
	}
	
	/**
	 * DP 向SR发送删除isd-p请求
	 * @param reqBody
	 * @throws Exception 
	 */
	public void profileDeleteByHttps(PersonalAllISDPReqBody reqBody)throws Exception{
        MsgHeader header = new MsgHeader("deleteProfileByHttps");
        DeleteProfileByHttpsReqBody requestBody = new DeleteProfileByHttpsReqBody();
        requestBody.setEid(reqBody.getEid());
		requestBody.setIccid(reqBody.getIccid());
		requestBody.setErrorDeal(true);
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
	}
	

}
