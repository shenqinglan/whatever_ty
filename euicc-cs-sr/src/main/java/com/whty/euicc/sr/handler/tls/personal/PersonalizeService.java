package com.whty.euicc.sr.handler.tls.personal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.whty.cache.CacheUtil;
import com.whty.euicc.common.apdu.ToTLV;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.profile.util.Tool;

/**
 * 个人化installIsdpApdu拼装
 * @author lw,zyj
 *
 */
@Service
public class PersonalizeService {
private Logger logger = LoggerFactory.getLogger(PersonalizeService.class);
	
	private final String INSTALL_CLA = "80";
	private final String INSTALL_INS = "e6";
	private final String INSTALL_P1 = "20";
	private final String INSTALL_P2 = "00";
	private String INSTALL_Lc;
	
	private final String DATA1_LENGTH = "00";
	private final String DATA2_LENGTH = "00";
	private final String ISD_P_AID_LENGTH = "10";
	private final String DATA3_LENGTH = "00";
	private final String DATA4_LENGTH = "00";
	private final String DATA5_LENGTH = "00";
	
	private String RC="";
	private String receipt="";
	
	public byte[] installISDPApdu(SmsTrigger eventTrigger) {
		StringBuilder data = new StringBuilder().append(DATA1_LENGTH).append(DATA2_LENGTH)
				.append(ISD_P_AID_LENGTH).append(eventTrigger.getIsdPAid()).append(DATA3_LENGTH).append(DATA4_LENGTH).append(DATA5_LENGTH);
		INSTALL_Lc = Tool.toHex(String.valueOf(data.toString().length()/2));
		StringBuilder apdu = new StringBuilder().append(INSTALL_CLA).append(INSTALL_INS).append(INSTALL_P1).append(INSTALL_P2).append(INSTALL_Lc).append(data);
		String finalApdu = "22"+ToTLV.toTLV(apdu.toString());
		logger.info("个人化install ISD-P的APDU指令为:{}",finalApdu);
		return finalApdu.getBytes();
	}
	
	public boolean checkInstallResp(String eid,String requestStr) {
		// TODO Auto-generated method stub
		//CacheUtil.put("personal"+eid, eid);
		int endOfDbl0D0A=requestStr.indexOf("\r\n\r\n")+4;
		String strData = requestStr.substring(endOfDbl0D0A, requestStr.length()-4);
		logger.info("apdu:{}",strData);
		if(strData.indexOf("9000")==-1){
			throw new EuiccBusiException("0101","卡片返回值不为9000");
		}
		return true;
	}
	
	public boolean checkFirstStoreDataResp(String eid,String requestStr) {
		//可能返回error,形式如何，处理如何
		//String eid = TlsMessageUtils.getEid(requestStr);
		try {
			int endOfDbl0D0A=requestStr.indexOf("\r\n\r\n")+4;
			String strData = requestStr.substring(endOfDbl0D0A, requestStr.length()-4);
			logger.info("apdu:{}",strData);
			if(strData.indexOf("9000")==-1){
				throw new EuiccBusiException("0101","卡片返回值不为9000");
			}else{
				int endOf85=requestStr.indexOf("8510")+"8510".length();
				RC=requestStr.substring(endOf85,endOf85+32);
				System.out.println("RC checking >>>"+RC);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof EuiccBusiException){
				throw new EuiccBusiException("0101","卡片返回值不为9000");
			}
		}
		CacheUtil.put("personal-RC-"+eid, RC);
		return true;
	}
	
	public boolean checkSecondStoreDataResp(String eid,String requestStr) {
		//;这里需要分离出卡片返回内容(0d0a0d0a后面的内容且排除最后的32字节MAC及补齐)赋值给变量
		//;返回receipt，结构为86的TLV
		try{
			int endOfDbl0D0A=requestStr.indexOf("\r\n\r\n")+4;
			System.out.println(endOfDbl0D0A);
			String strData = requestStr.substring(endOfDbl0D0A, requestStr.length()-4);
			logger.info("apdu:{}",strData);
			if(strData.indexOf("9000")==-1){
				throw new EuiccBusiException("0101","卡片返回值不为9000");
			}else{
				int endOf86=strData.indexOf("8610")+"8610".length();
				receipt=strData.substring(endOf86,endOf86+32);
				System.out.println("Receipt checking >>>"+receipt);
			}
		} catch(Exception e) {
			e.printStackTrace();
			if(e instanceof EuiccBusiException){
				throw new EuiccBusiException("0101","卡片返回值不为9000");
			}
			return false;
		}
		CacheUtil.put("personal-recept-"+eid, receipt);
		return true;
	}
}
