package com.whty.euicc.sms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.whty.euicc.cipher.DESede;
import com.whty.euicc.sms.commandpacket.CMessage;
import com.whty.euicc.sms.commandpacket.Command;
import com.whty.euicc.sms.commandpacket.CommandPacket;
import com.whty.euicc.sms.constants.SMSConstants;
import com.whty.euicc.sms.exception.SMSException;
import com.whty.euicc.sms.exception.SMSRuntimeException;
import com.whty.euicc.sms.parameter.DeliverParameter;
import com.whty.euicc.sms.smscb.CellBroadCastPage;
import com.whty.euicc.sms.smscb.SMSCB;
import com.whty.euicc.sms.smspp.SMSPpDeliver;
import com.whty.euicc.sms.smspp.SMSPpResponse;
import com.whty.euicc.sms.smspp.SMSPpSubmit;
import com.whty.euicc.sms.smspp.SMSPpUPD;
import com.whty.euicc.sms.tpud.IdentifyElement;
import com.whty.euicc.sms.tpud.TPUD;
import com.whty.euicc.sms.util.AnalyseUtils;
import com.whty.euicc.sms.util.SMSUtil;
import com.whty.euicc.util.StringUtil;
/**
 * 短信下行接口
 * 
 * spi:2个字节，
 *     高字节八位组：b8b7b6默认为0，b5b4表示计数器规则，b3表示是否加密，b2b1表示具体使用RC,CC,DS其中一种鉴别方式或不使用
 *     低字节八位组：b8b7b6默认为0，b5表示收条POR是否需要加密，b4b3表示收条POR是否需要RC,CC,DS以及具体使用哪种方式，b2b1表示发送方是否需要收条POR
 * kic：1个字节，表示数据加密时密钥顺序索引以及使用的加密算法（AES/DES...）和算法使用的模式（3DES,CBC...）
 * kid：1个字节，表示计算CC,RC,DS使用的密钥索引和算法（AES/DES...）和算法使用的模式（3DES,CBC...）
 * tar：3个字节，其值唯一地标识卡上的应用
 * cntr：5个字节，计数器的值
 * @author Administrator
 *
 */
@Service
public class Sms {
	private Logger logger = LoggerFactory.getLogger(Sms.class);
	private static int maxUDL = 140;
	private static String lastCmdHeader = "00011111B05000F00000000F";
	
	public static int PacketNum;

	/**
	 * 快捷认证发送短信拼装
	 * @param data
	 * @param phoneNo
	 * @return
	 */
	public List<String> sendSmsForCTSim(String data,Map<String, String> mapKey) throws Exception{
		String ctSpi = mapKey.get("ctSpi");
		String ctKic = mapKey.get("ctKic");
		String ctKid = mapKey.get("ctKid");
		String ctTar = mapKey.get("ctTar");
		String ctCntr = mapKey.get("ctCntr");
		String kicKey = mapKey.get("ctKicKey");
		String kidKey = mapKey.get("ctKidKey");
		if (StringUtils.isBlank(kidKey) || StringUtils.isBlank(kicKey) ) {//密钥值为空
			logger.info("one or more of the keys null,please check parameters!");
			return null;
		}
		if (kicKey.length() != 32 || kidKey.length() != 32) {//密钥值长度不等于16字节
			logger.info("key's length is not right!please check parameters!");
			return null;
		}
		logger.info("kicKey: " + kicKey);
		logger.info("kidKey: " + kidKey);
		
		
		if (StringUtils.isBlank(ctSpi) ||StringUtils.isBlank(ctKic) || StringUtils.isBlank(ctKid) || StringUtils.isBlank(ctTar)
				 || StringUtils.isBlank(ctCntr)) {//spi-counter有值为空
			logger.info("one or more of parameters since spi to counter is null,please check parameters!");
			return null;
			
		}
		
		StringBuilder cmdHeader = new StringBuilder();
		 cmdHeader
		 .append(ctSpi)
		 .append(ctKic)
		 .append(ctKid)
		 .append(ctTar)
		 .append(ctCntr);
		 logger.info("value of spi to counter: " + cmdHeader.toString());
		return smsPpEnvelope(SMSConstants.UDH, cmdHeader.toString(), SMSConstants.SPLIT_NUM, data,kicKey,kidKey);
		
	}
	/**
	 * 解析卡片上行响应
	 * 当入参spi的值为空时，默认数据为加密带mac方式
	 * @param returnData
	 * @return
	 * @throws SMSException 
	 */
	public String parseSubmitResp(String returnData,String spi,String kicKey,String kidKey) throws SMSException {
		String commandData = "";
		String valueBeginCntr = returnData.substring(18);
		if (StringUtils.isBlank(spi)) {//spi值为空，卡片主动上行注册...
			if (StringUtils.isBlank(kicKey) || StringUtils.isBlank(kidKey)) {
				logger.info("when spi is null,keys cannot be empty!");
				return null;
			}
			//解密
			String keyString = kicKey;//第一组密钥kic
			valueBeginCntr = decryptData(valueBeginCntr,keyString);
			//获取填充字节
			String pcntrStr = valueBeginCntr.substring(10, 12);
			int pcntr = StringUtil.hexToByte(pcntrStr);
			
			//参与计算MAC的数据 RPL（2字节）+RHL（1字节）+TAR（3字节）+CNTR（5字节）+PCNTR（1字节）+上行数据（命令类型、命令长度、 命令数据）
			String keyStr = kidKey;//第一组kid密钥
			commandData = checkCC(returnData, commandData, valueBeginCntr,
					pcntr, keyStr);
		}else {//上行的卡片响应
//			String valueOfSpi2 = spi.substring(2, 4);
			String valueOfSpi2 = "39";
			byte spiSecByte = StringUtil.hexToByte(valueOfSpi2);
			
			if ((spiSecByte & 0x10)  == 0x10) {//上行响应是密文
				if (StringUtils.isBlank(kicKey)) {
					logger.info("spi value indicate the data is encrypted,but now kickey is empty !");
					return null;
				}
				valueBeginCntr = decryptData(valueBeginCntr,kicKey);
			}
			
			String pcntrStr = valueBeginCntr.substring(10, 12);
			int pcntr = StringUtil.hexToByte(pcntrStr);
			
			if ((spiSecByte & 0xc)  == 0x8) {//上行具有mac
				if (StringUtils.isBlank(kidKey)) {
					logger.info("spi value indicate the data need to caculate mac ,but now kidkey is empty !");
					return null;
				}
				commandData = checkCC(returnData, commandData, valueBeginCntr,
						pcntr, kidKey);
				
			}else {
				commandData = valueBeginCntr.substring(14,valueBeginCntr.length() - pcntr * 2);
			}
		}
		return commandData;
	}
	/**
	 * 对卡片上行短信校验mac
	 * @param returnData
	 * @param commandData
	 * @param valueBeginCntr
	 * @param pcntr
	 * @param keyStr
	 * @return
	 * @throws SMSException
	 */
	private String checkCC(String returnData, String commandData,
			String valueBeginCntr, int pcntr, String keyStr)
			throws SMSException {
		String strNeedMac = returnData.substring(6, 18) + valueBeginCntr.substring(0,14) + 
				valueBeginCntr.substring(30);
		
		String res = calcuCC(strNeedMac,keyStr);
		if (res.length() > 16) {
			res = res.substring(res.length() - 16, res.length());
		}
		if (!StringUtils.equals(res,valueBeginCntr.substring(14, 30))) {
			throw new SMSException("MAC comparation failure!");
		}else {
			commandData = valueBeginCntr.substring(30,valueBeginCntr.length() - pcntr * 2);
		}
		return commandData;
		
	}
	/**
	 * 3des-cbc计算mac
	 * @param hexData
	 * @param keyStr
	 * @return
	 */
	private String calcuCC(String hexData,String keyStr) throws SMSException{
		byte[]keyByte =  StringUtil.hexToBuffer(keyStr);
		byte[] iVByte = StringUtil.hexToBuffer("0000000000000000");
		byte[] tmp = null;
		if (hexData.length() / 2 % 8 != 0) {
			int padlen2 = 8 - hexData.length() / 2 % 8;
			hexData = hexData
					+ "0000000000000000".substring(0, padlen2 * 2);
		}
		byte[] data2 = StringUtil.hexToBuffer(hexData);
		DESede.setCipherAlgorithm("DESede/CBC/NoPadding");
		
		try {
			tmp = DESede.encrypt(data2, keyByte, iVByte);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ccStr = StringUtil.bufferToHex(tmp);
		return ccStr;
	}
	/**
	 * 解析卡片上行短信
	 * @param data
	 * @param phoneNo
	 * @return
	 * @throws SMSException
	 */
	public String parSubFromCard(String data,String spi,String kicKey,String kidKey) throws SMSException{
		String resultStr = "";
	    if (data.indexOf("0271") != -1) {
			resultStr = parseSubmitResp(data, spi,kicKey,kidKey);
		}else {
			resultStr = data;
		}
		return resultStr;
	}
	/**
	 * 3des-cbc 2keys解密数据
	 * @param valueBeginCntr
	 * @param keyStr
	 * @return
	 */
	private String decryptData(String valueBeginCntr,String keyStr) throws SMSException{
		byte[] cipherData = StringUtil.hexToBuffer(valueBeginCntr);
		byte[]keyByte =  StringUtil.hexToBuffer(keyStr);
		byte[] tmp = null;
		byte[] iVByte = StringUtil.hexToBuffer("0000000000000000");
		DESede.setCipherAlgorithm("DESede/CBC/NoPadding");
		try {
			tmp = DESede.decrypt(cipherData, keyByte, iVByte);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String resultStr = StringUtil.bufferToHex(tmp);
		return resultStr;
	}
	/**
	 * 解析卡片上行短信（卡片主动上行注册上行场景）
	 * 目前预想卡片上行为该种格式，具体待脚本出来确定
	 * @param data
	 * @return
	 * @throws SMSException 
	 */
	public String parseSubmitSms(String data) throws SMSException{
		String resultStr = "";
		byte[] spiByte = StringUtil.hexToBuffer(data.substring(12,16));
		byte kicByte = StringUtil.hexToByte(data.substring(16,18));
		byte kidByte = StringUtil.hexToByte(data.substring(18,20));
		String valueBeginCntr = data.substring(26);
		if (checkKic(kicByte, spiByte)) {
			String keyString = "11223344556677889910111213141516";//TODO
			valueBeginCntr = decryptData(valueBeginCntr,keyString);
		}
		String pcntrStr = valueBeginCntr.substring(10, 12);
		int pcntr = StringUtil.hexToByte(pcntrStr);
		if (checkKid(kidByte, spiByte)) {
			String strNeedCC = data.substring(6, 26) + valueBeginCntr.substring(0, 12)+
					valueBeginCntr.substring(28);
			String keyStr = "11223344556677889910111213141516";
			String ccStr = calcuCC(strNeedCC,keyStr);
			if (!StringUtils.equals(ccStr, valueBeginCntr.substring(12, 28))) {
				throw new SMSException("MAC校验失败");
			}
			resultStr = valueBeginCntr.substring(28);
			resultStr = resultStr.substring(0,resultStr.length()-pcntr * 2);
		}else {
			resultStr = valueBeginCntr.substring(12);
			resultStr = resultStr.substring(0,resultStr.length()-pcntr * 2);
		}
		return resultStr;
	}
	private boolean checkKic(byte kic,byte[]spi){
		boolean done = false;
		if(((spi[0] & 0x4) == 4) && (((kic & 0x1) == 0) || ((kic & 0x1) == 1))){
			done = true;
		}
		return done;
			
	}
	private boolean checkKid(byte kid,byte[]spi){
		boolean done = false;
	     if(((spi[0] & 0x3) == 2) && (((kid & 0x3) == 2) || ((kid & 0x3) == 1))){
	    	 done = true;
	     }
	     return done;
	}
	
	
	
	/*
	 * 2
	 */
	private static List<String> smsPpEnvelope(String udh, String cmdHeader,String encryptKey,String ccKey,
			String data) {
		lastCmdHeader = cmdHeader;
		List res = new ArrayList();

		if (("".equals(udh)) || (udh == null)) {
			res = smsPpDownloadUnformat(1, "00", "", data);
		} else if (!udh.endsWith("7000")) {
			String refNumber = "";
			if (udh.startsWith("00")) {
				refNumber = udh.substring(4, 6);
				res = smsPpDownloadUnformat(1, "00", refNumber, data);
			} else if (udh.startsWith("08")) {
				refNumber = udh.substring(4, 8);
				res = smsPpDownloadUnformat(1, "08", refNumber, data);
			}

		} else {
			String iEIa = udh.substring(0, 2);
			if (("70".equals(iEIa)) && (udh.length() == 4)) {
				res = smsPpDownloadFormat(1, "70", "", "", cmdHeader, data,encryptKey,ccKey);
			} else if (("00".equals(iEIa)) && (udh.length() == 14)) {
				String refNumber = udh.substring(4, 6);
				res = smsPpDownloadFormat(1, "70", "00", refNumber, cmdHeader,
						data,encryptKey,ccKey);
			} else {
				throw new SMSRuntimeException("TPUDH 格式不对！TPUDH=" + udh);
			}
		}

		return res;
	}
	

	private static List<String> smsPpEnvelope(String udh, String cmdHeader,
			String data, int splitNum) {
		lastCmdHeader = cmdHeader;
		List res = new ArrayList();

		if (("".equals(udh)) || (udh == null)) {
			res = smsPpDownloadUnformat(1, "00", "", data, splitNum);
		} else if (!udh.endsWith("7000")) {
			String refNumber = "";
			if (udh.startsWith("00")) {
				refNumber = udh.substring(4, 6);
				res = smsPpDownloadUnformat(1, "00", refNumber, data, splitNum);
			} else if (udh.startsWith("08")) {
				refNumber = udh.substring(4, 8);
				res = smsPpDownloadUnformat(1, "08", refNumber, data, splitNum);
			}

		} else {
			String iEIa = udh.substring(0, 2);
			if (("00".equals(iEIa)) && (udh.length() == 14)) {
				String refNumber = udh.substring(4, 6);
				res = smsPpDownloadFormat(1, "70", "00", refNumber, cmdHeader,
						data, splitNum);
			} else if (("08".equals(iEIa)) && (udh.length() == 16)) {
				String refNumber = udh.substring(4, 6);
				res = smsPpDownloadFormat(1, "70", "08", refNumber, cmdHeader,
						data, splitNum);
			} else {
				throw new SMSRuntimeException("TPUDH 格式不对！TPUDH=" + udh);
			}
		}

		return res;
	}

	public static List<String> smsPpEnvelope(String udh, String cmdHeader,
			int splitNum, String data,String encryptKey,String ccKey) throws SMSRuntimeException{
		lastCmdHeader = cmdHeader;

		if ((udh != null) && (udh.startsWith("7000")) && (udh.length() > 4)) {
			udh = udh.substring(4) + udh.substring(0, 4);
		}

		if (splitNum <= 1) {
			return smsPpEnvelope(udh, cmdHeader,encryptKey,ccKey, data);
		}
		//splitNum表示级联信息
		return smsPpEnvelope(udh, cmdHeader, data, splitNum);
	}

	public static List<String> smsPpUpd(int recordLen, String udh,
			String cmdHeader, int splitNum, String data) {
		return null;
//		lastCmdHeader = cmdHeader;
//		List res = new ArrayList();
//		List resPadded = new ArrayList();
//
//		if ((udh != null) && (udh.startsWith("7000")) && (udh.length() > 4)) {
//			udh = udh.substring(4) + udh.substring(0, 4);
//		}
//
//		if (("".equals(udh)) || (udh == null)) {
//			if (splitNum <= 1)
//				res = smsPpDownloadUnformat(2, "00", "", data);
//			else
//				res = smsPpDownloadUnformat(2, "00", "", data, splitNum);
//		} else if (!udh.endsWith("7000")) {
//			String refNumber = "";
//			if (udh.startsWith("00"))
//				refNumber = udh.substring(4, 6);
//			else if (udh.startsWith("08")) {
//				refNumber = udh.substring(4, 8);
//			}
//
//			if (splitNum <= 1)
//				res = smsPpDownloadUnformat(2, "00", refNumber, data);
//			else {
//				res = smsPpDownloadUnformat(2, "00", refNumber, data, splitNum);
//			}
//
//		} else {
//			String iEIa = udh.substring(0, 2);
//			if (("70".equals(iEIa)) && (udh.length() == 4)) {
//				if (splitNum <= 1)
//					res = smsPpDownloadFormat(2, "70", "", "", cmdHeader, data);
//				else {
//					res = smsPpDownloadFormat(2, "70", "", "", cmdHeader, data,
//							splitNum);
//				}
//
//			} else if (("00".equals(iEIa)) && (udh.length() == 14)) {
//				String refNumber = udh.substring(4, 6);
//
//				if (splitNum <= 1)
//					res = smsPpDownloadFormat(2, "70", "00", refNumber,
//							cmdHeader, data);
//				else {
//					res = smsPpDownloadFormat(2, "70", "00", refNumber,
//							cmdHeader, data, splitNum);
//				}
//			} else {
//				throw new SMSRuntimeException("TPUDH 格式不对！TPUDH=" + udh);
//			}
//		}
//		for (int i = 0; i < res.size(); i++) {
//			String s = (String) res.get(i);
//			int padLength = recordLen - s.length() / 2;
//			if (s.length() / 2 < recordLen) {
//				for (int j = 0; j < padLength; j++) {
//					s = s + "FF";
//				}
//			}
//			resPadded.add(s);
//		}
//
//		return resPadded;
 }
	
	
	
	/**
	 * 将用户数据（即sendSmsNeedPor（）方法的返回值）拼装成下行短信
	 * @param sStr_in 用户数据
	 * @return 拼装后的短信包
	 * @throws Exception
	 */
	public static String[] SmsPp_mt(String sStr_in) throws Exception {
		 int length = 100;
		 String sStr;
		 String SMSPPDLTag = "D1";
		 String DevId = "82028381";
		 String sAddr;
		 String SMSTPDUTag = "8B";
		 String packet_Userdata[] = new String[length];
		 String Typesms = "40";
		 String TP_DA = "088101560805";
		 String TP_PID = "7F";
		 String TP_DCS = "F6";
		 String TP_SCTS = "20408011535200";
		 String Packet[] = new String[length];
		 int sClaEN = 0x80;
		 int c;

	
		sStr = sStr_in;
		sAddr = AnalyseUtils.totlv("86", "91683108100005F0", 0);
		packet_Userdata = CMessage.ConcatenateMessage(sStr);
		c = PacketNum;

		while (c > 0) {
			
			String TPDU = Typesms + TP_DA + TP_PID + TP_DCS + TP_SCTS
					+ AnalyseUtils.totlv("", packet_Userdata[c - 1], 0);
			TPDU = AnalyseUtils.totlv(SMSTPDUTag, TPDU);
			String Str1 = AnalyseUtils.totlv(SMSPPDLTag, DevId + sAddr + TPDU);
			Packet[c - 1] = AnalyseUtils.itoa(sClaEN) + "C20000"
					+ AnalyseUtils.totlv("", Str1, 0);

			
			c = c - 1;
		}
		return Packet;
		
	}


	public static List<String> smsCbEnvelope(String cbHeader, String cmdHeader,
			String data) {
		lastCmdHeader = cmdHeader;

		List res = new ArrayList();

		IdentifyElement ie = IdentifyElement.getInstance();
		ie.clear();
		CellBroadCastPage cbp = CellBroadCastPage.getInstance();
		cbp.setDefaultValue(false);

		cbp.setSN(cbHeader.substring(0, 4));

		cbp.setMID(cbHeader.substring(4, 8));

		cbp.setDCS(cbHeader.substring(8, 10));

		cbp.setPP("00");

		cbp.setUserData(data);

		Command cmd = Command.getInstance();
		if ((cmdHeader == null) || ("".equals(cmdHeader))) {
			cmd.setSPI("");
			cmd.setKIc("");
			cmd.setKID("");
			cmd.setTAR("");
			cmd.setCNTR("");
		} else {
			cmd.setSPI(cmdHeader.substring(0, 4));
			cmd.setKIc(cmdHeader.substring(4, 6));
			cmd.setKID(cmdHeader.substring(6, 8));
			cmd.setTAR(cmdHeader.substring(8, 14));
			cmd.setCNTR(cmdHeader.substring(14, 24));
		}

		res = SMSCB.getInstance().toStringList();

		return res;
	}

	public static boolean compResData(String realData, String expData,
			String statusCode) {
		Command cmd = Command.getInstance();
		if (lastCmdHeader != null) {
			cmd.setSPI(lastCmdHeader.substring(0, 4));
			cmd.setKIc(lastCmdHeader.substring(4, 6));

			cmd.setKID(lastCmdHeader.substring(6, 8));
			cmd.setTAR(lastCmdHeader.substring(8, 14));
			cmd.setCNTR(lastCmdHeader.substring(14, 24));
		} else {
			cmd.setSPI("");
			cmd.setKIc("");
			cmd.setKID("");
			cmd.setTAR("");
			cmd.setCNTR("");
		}
		SMSPpResponse up = SMSPpResponse.getInstance();
		up.setSC(statusCode);
		up.toObject(realData);
		if ("00".equals(up.getSC())) {
			if ((up.getSC().equals(statusCode))
					&& (SMSUtil.checkData(expData, up.getResponseInfo()))) {
				return true;
			}
			throw new SMSRuntimeException("Error：ResData验证失败! \n实际SC："
					+ up.getSC() + " 期望SC：" + statusCode + "\n" + "实际Data："
					+ up.getResponseInfo() + " 期望Data：" + expData);
		}

		return true;
	}

	public static boolean compSubData(String realData, String expData,
			String statusCode) {
		Command cmd = Command.getInstance();
		cmd.setSPI(lastCmdHeader.substring(0, 4));
		cmd.setKIc(lastCmdHeader.substring(4, 6));

		cmd.setKID(lastCmdHeader.substring(6, 8));
		cmd.setTAR(lastCmdHeader.substring(8, 14));
		cmd.setCNTR(lastCmdHeader.substring(14, 24));

		DeliverParameter dp = DeliverParameter.getInstance();
		SMSPpSubmit sub = SMSPpSubmit.getInstance();
		SMSPpResponse up = SMSPpResponse.getInstance();
		sub.toObject(realData);
		if (!dp.getOA().equals(sub.getDA()))
			throw new SMSRuntimeException("Error：OA与DA值不同。OA值：" + dp.getOA()
					+ "DA值：" + sub.getDA());
		if (!expData.equals(up.getResponseInfo()))
			throw new SMSRuntimeException("Error：实际值与期望值不同。实际值："
					+ up.getResponseInfo() + "期望值：" + expData);
		if (!statusCode.equals(up.getSC()))
			throw new SMSRuntimeException("Error：实际SC与期望SC不同。实际SC："
					+ up.getSC() + "期望SC：" + statusCode);
		if (!sub.getMMRUS().endsWith("1")) {
			throw new SMSRuntimeException("Error：实际MMRUS值不正确。MMRUS："
					+ sub.getMMRUS());
		}
		return true;
	}

	/*
	 * 3
	 */
	//udh+command packet
	private static List<String> smsPpDownloadFormat(int type, String formatTag,
			String concatenatedTag, String refNumber, String cmdHeader,
			String data,String encryptKey,String ccKey) throws SMSRuntimeException{
		List res = new ArrayList();

		IdentifyElement ie = IdentifyElement.getInstance();
		ie.clear();
		ie.setIEIa("70");

		Command cmd = Command.getInstance();
		cmd.setSPI(cmdHeader.substring(0, 4));
		cmd.setKIc(cmdHeader.substring(4, 6));

		cmd.setKID(cmdHeader.substring(6, 8));
		cmd.setTAR(cmdHeader.substring(8, 14));
		cmd.setCNTR(cmdHeader.substring(14, 24));
		cmd.setPlainString(data);
		if (StringUtils.isNotBlank(encryptKey) && StringUtils.isNotBlank(ccKey)) {
			cmd.setEncryptKey(encryptKey);
			cmd.setCcKey(ccKey);
		}
		DeliverParameter.getInstance().setFormate(true);

		int tpudLen = TPUD.getInstance().toString().length() / 2;

		if (tpudLen > maxUDL) {
			String cmdPkg = CommandPacket.getInstance().toString();

			ie.setIEIa(concatenatedTag);
			ie.setIEIb(formatTag);

			int cmdPkgLen = cmdPkg.length() / 2;

			int firstCmdMaxLen = maxUDL - 8;

			int otherCmdMaxLen = maxUDL - 6;

			int lastCmdLen = (cmdPkgLen - firstCmdMaxLen) % otherCmdMaxLen;
			int totalCount = (int) Math.ceil((cmdPkgLen - firstCmdMaxLen)
					* 1.0D / otherCmdMaxLen) + 1;
			String totalCountHex = StringUtil.byteToHex((byte) totalCount);
			int index = 1;

			CommandPacket cp = CommandPacket.getInstance();

			cp.setConatednatedStr(cmdPkg.substring(0, firstCmdMaxLen * 2));

			ie.setIEDa(refNumber + totalCountHex
					+ StringUtil.byteToHex((byte) index));

			res.add(deliverString(type));
			index++;

			for (index = 2; index < totalCount; index++) {
				int start = firstCmdMaxLen * (index - 1);

				int end = start + otherCmdMaxLen;
				cp.setConatednatedStr(cmdPkg.substring(start * 2, end * 2));

				ie.setIEDa(refNumber + totalCountHex
						+ StringUtil.byteToHex((byte) index));

				res.add(deliverString(type));
			}

			int start = firstCmdMaxLen * (index - 1);

			int end = start + lastCmdLen;
			cp.setConatednatedStr(cmdPkg.substring(start * 2, end * 2));

			ie.setIEDa(refNumber + totalCountHex
					+ StringUtil.byteToHex((byte) index));

			res.add(deliverString(type));
		} else {
			ie.setIEIa("70");
			ie.setIEIb("");
			res.add(deliverString(type));
		}

		return res;
	}

	private static List<String> smsPpDownloadUnformat(int type,
			String concatenatedTag, String refNumber, String data) {
		List res = new ArrayList();

		IdentifyElement ie = IdentifyElement.getInstance();
		ie.setIEIa("");
		ie.setIEIb("");

		Command cmd = Command.getInstance();
		cmd.setSPI("");
		cmd.setKIc("");
		cmd.setKID("");
		cmd.setTAR("");
		cmd.setCNTR("");
		cmd.setPlainString(data);

		int tpudLen = TPUD.getInstance().toString().length() / 2;

		if (tpudLen > maxUDL) {//判断是否是级联短信
			DeliverParameter.getInstance().setFormate(false);
			DeliverParameter.getInstance().setCC(true);

			String cmdPkg = CommandPacket.getInstance().toString();

			ie.setIEIa(concatenatedTag);
			ie.setIEIb("");

			int cmdPkgLen = cmdPkg.length() / 2;
			int cmdMaxLen = maxUDL - 6;

			int totalCount = (int) Math.ceil(cmdPkgLen * 1.0D / cmdMaxLen);
			String totalCountHex = StringUtil.byteToHex((byte) totalCount);
			int index = 1;
			CommandPacket cp = CommandPacket.getInstance();

			for (index = 1; index < totalCount; index++) {
				int start = cmdMaxLen * (index - 1);
				int end = start + cmdMaxLen;
				cp.setConatednatedStr(cmdPkg.substring(start * 2, end * 2));

				ie.setIEDa(refNumber + totalCountHex
						+ StringUtil.byteToHex((byte) index));
				res.add(deliverString(type));
			}

			int start = cmdMaxLen * (index - 1);
			cp.setConatednatedStr(cmdPkg.substring(start * 2, cmdPkg.length()));

			ie.setIEDa(refNumber + totalCountHex
					+ StringUtil.byteToHex((byte) index));

			res.add(deliverString(type));
		} else {
			DeliverParameter.getInstance().setFormate(false);
			DeliverParameter.getInstance().setCC(false);
			ie.setIEIa("");
			ie.setIEIb("");
		//	res.add(deliverString(type));
			res.add(CommandPacket.getInstance().toString());
		}

		return res;
	}

	private static String deliverString(int type) {
		String res = null;
		switch (type) {
		case 1:
			res = SMSPpDeliver.getInstance().toString();
			break;
		case 2:
			res = SMSPpUPD.getInstance().toString();
			break;
		default:
			throw new SMSRuntimeException("暂不支持该类型! type=" + type);
		}

		return res;
	}

	public static String getLastCmdHeader() {
		return lastCmdHeader;
	}

	public static void setLastCmdHeader(String lastCmdHeader) {
		lastCmdHeader = lastCmdHeader;
	}

	private static List<String> smsPpDownloadFormat(int type, String formatTag,
			String concatenatedTag, String refNumber, String cmdHeader,
			String data, int splitNum) {
		List res = new ArrayList();

		IdentifyElement ie = IdentifyElement.getInstance();
		ie.clear();
		ie.setIEIa("70");

		Command cmd = Command.getInstance();
		cmd.setSPI(cmdHeader.substring(0, 4));
		cmd.setKIc(cmdHeader.substring(4, 6));

		cmd.setKID(cmdHeader.substring(6, 8));
		cmd.setTAR(cmdHeader.substring(8, 14));
		cmd.setCNTR(cmdHeader.substring(14, 24));
		cmd.setPlainString(data);
		DeliverParameter.getInstance().setFormate(true);

		CommandPacket cmdPkt = CommandPacket.getInstance();
		String cmdPktStr = cmdPkt.toString();

		String myHeader = cmdPkt.getCPI() + cmdPkt.getCPL()
				+ cmd.getCmdHeader();
		String myData = cmdPktStr.substring(myHeader.length());

		ie.setIEIa(concatenatedTag);
		ie.setIEIb(formatTag);

		String totalCountHex = StringUtil.byteToHex((byte) splitNum);

		int eachDataLen = myData.length() / 2 / splitNum;
		int index = 1;

		CommandPacket cp = CommandPacket.getInstance();

		cp.setConatednatedStr(myHeader
				+ myData.substring(0, eachDataLen * index * 2));

		ie.setIEDa(refNumber + totalCountHex
				+ StringUtil.byteToHex((byte) index));

		res.add(deliverString(type));
		index++;

		for (index = 2; index <= splitNum; index++) {
			int start = eachDataLen * (index - 1);

			int end = start + eachDataLen;
			if (index != splitNum)
				cp.setConatednatedStr(myData.substring(start * 2, end * 2));
			else {
				cp.setConatednatedStr(myData.substring(start * 2,
						myData.length()));
			}

			ie.setIEDa(refNumber + totalCountHex
					+ StringUtil.byteToHex((byte) index));

			res.add(deliverString(type));
		}

		return res;
	}

	private static List<String> smsPpDownloadUnformat(int type,
			String concatenatedTag, String refNumber, String data, int splitNum) {
		List res = new ArrayList();

		IdentifyElement ie = IdentifyElement.getInstance();
		ie.setIEIa("");
		ie.setIEIb("");

		Command cmd = Command.getInstance();
		cmd.setSPI("");
		cmd.setKIc("");
		cmd.setKID("");
		cmd.setTAR("");
		cmd.setCNTR("");
		cmd.setPlainString(data);

		DeliverParameter.getInstance().setFormate(false);
		DeliverParameter.getInstance().setCC(true);

		String cmdPkg = CommandPacket.getInstance().toString();

		ie.setIEIa(concatenatedTag);
		ie.setIEIb("");

		int cmdPkgLen = cmdPkg.length() / 2;
		int cmdMaxLen = cmdPkgLen / splitNum;

		int totalCount = (int) Math.ceil(cmdPkgLen * 1.0D / cmdMaxLen);
		if (cmdPkgLen % splitNum != 0) {
			totalCount--;
		}
		String totalCountHex = StringUtil.byteToHex((byte) totalCount);
		int index = 1;
		CommandPacket cp = CommandPacket.getInstance();

		for (index = 1; index < totalCount; index++) {
			int start = cmdMaxLen * (index - 1);
			int end = start + cmdMaxLen;
			cp.setConatednatedStr(cmdPkg.substring(start * 2, end * 2));

			ie.setIEDa(refNumber + totalCountHex
					+ StringUtil.byteToHex((byte) index));
			res.add(deliverString(type));
		}

		int start = cmdMaxLen * (index - 1);
		cp.setConatednatedStr(cmdPkg.substring(start * 2, cmdPkg.length()));

		ie.setIEDa(refNumber + totalCountHex
				+ StringUtil.byteToHex((byte) index));

		res.add(deliverString(type));

		return res;
	}

}