package com.whty.euicc.sms;

import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.cache.CacheUtil;
import com.whty.euicc.common.spring.SpringPropertyPlaceholderConfigurer;
import com.whty.euicc.data.dao.EuiccCardMapper;
import com.whty.euicc.data.pojo.EuiccCard;
import com.whty.euicc.sms.commandpacket.CMessage;
import com.whty.euicc.sms.commandpacket.Command;
import com.whty.euicc.sms.commandpacket.CommandPacket;
import com.whty.euicc.sms.constants.SMSConstants;
import com.whty.euicc.sms.exception.SMSRuntimeException;
import com.whty.euicc.sms.parameter.DeliverParameter;
import com.whty.euicc.sms.por.Por;
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
	private static int maxUDL = 140;
	private static String lastCmdHeader = "00011111B05000F00000000F";
	
	public static int PacketNum;
	
	private String PUDHL = "";
	private String PRPI = "";
	private String PRPIL = "";
	
	
	private static String lastSpi = SpringPropertyPlaceholderConfigurer.getStringProperty("sms_spi_por");
	private static String lastKic = SpringPropertyPlaceholderConfigurer.getStringProperty("sms_kic");
	private static String lastKid = SpringPropertyPlaceholderConfigurer.getStringProperty("sms_kid");
	private static String lastTar = SpringPropertyPlaceholderConfigurer.getStringProperty("sms_tar");
	private static String lastCntr;
	
	@Autowired
	private EuiccCardMapper cardMapper;
	
	
	/**
	 * 自定义spi，kic，kid，tar，cntr的值
	 * @param spi
	 * @param kic
	 * @param kid
	 * @param tar
	 * @param cntr
	 */
	public void init(String eid,String spi,String kic,String kid,String tar){
		lastSpi = spi;
		lastKic = kic;
		lastKid = kid;
		lastTar = tar;
		lastCntr = getCntr(eid);
		
		
	} 
	/**
	 * 初始化tar值
	 * @param tar
	 */
	public void initTar(String tar){
		lastTar = tar;
	} 
	
	
	
	
	/**
	 * 得到拼装后的tpud，并指定是否需要返回por
	 * @param  data 要通过短信发送的数据
	 * @param isPor true:return por false:not return por
	 * @return
	 */
	public List<String> sendSmsNeedPor(String eid,String data,boolean isPor){
		 String lastCntr = getCntr(eid);
		 lastSpi = isPor ? SpringPropertyPlaceholderConfigurer.getStringProperty("sms_spi_por")
				 : SpringPropertyPlaceholderConfigurer.getStringProperty("sms_spi_nopor");
		 StringBuilder cmdHeader = new StringBuilder();
		 cmdHeader
		 .append(lastSpi)
		 .append(lastKic)
		 .append(lastKid)
		 .append(lastTar)
		 .append(lastCntr);
		
		return smsPpEnvelope(SMSConstants.UDH, cmdHeader.toString(), SMSConstants.SPLIT_NUM, data);
		
	}
	

	private String getCntr(String eid) {
		EuiccCard euiccCard = new EuiccCard();
		EuiccCard card = cardMapper.selectByPrimaryKey(eid);
		int count = 0;
		if(card != null){
			count = card.getCount();
		}
		int nowTimes = new AtomicInteger(count).addAndGet(1);
		String hexTempString = StringUtil.intToHex(nowTimes);
		euiccCard.setEid(eid);
		euiccCard.setCount(nowTimes);
		cardMapper.updateByPrimaryKeySelective(euiccCard);
		return "00"+hexTempString;
	}




	/**
	 * 得到拼装后的tpud，并且不需要返回por
	 * @param data  要通过短信发送的数据
	 * @return
	 */
	public List<String> sendSms(String data){
		 StringBuilder cmdHeader = new StringBuilder();
		 cmdHeader
		 .append(lastSpi)
		 .append(lastKic)
		 .append(lastKid)
		 .append(lastTar)
		 .append(lastCntr);
		return smsPpEnvelope(SMSConstants.UDH, cmdHeader.toString(), SMSConstants.SPLIT_NUM, data);
		
	}
	
	/**
	 * 判断其格式是不是poR,轻量级判断
	 * @param responseData  返回的短信内容
	 * @return
	 */
	public boolean isPoR(String porResponse){
		boolean result = true;
		this.PUDHL = porResponse.substring(0, 2);
		if( !"02".equals(this.PUDHL ) ){
			result = false;
		}
		
		this.PRPI = porResponse.substring(2, 4);
		if(!"71".equals(this.PRPI )){
			result = false;
		}
		this.PRPIL = porResponse.substring(4, 6);
		if(!"00".equals(this.PRPIL )){
			result = false;
		}
			
		return result;
	}
	
	/**
	 * 解析mo短信接到的por
	 * @param responseData  返回的短信内容
	 * @return 
	 * @throws Exception 
	 */
	public static String poR(String eid,String responseData) throws Exception{
		 Por por = new Por();
		 String savedCntr = CacheUtil.getString("smsCounter" + eid);	
		return por.analypoR(savedCntr,responseData);
	}
	
	/**
	 * 解析sms通知机制中由卡片发送的更新通知，返回解析后的通知数据
	 * @param respData 通过sms从卡片接收到的由scp80包装后的更新通知命令包
	 * @return 返回解析后的通知数据
	 */
	public static String smsNotification(String respData){
		Command cmd = new Command();
		String res = null;
		try {
			res = cmd.notifiProcess(respData);
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		return res;
	}
	private static List<String> smsPpEnvelope(String udh, String cmdHeader,
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
				res = smsPpDownloadFormat(1, "70", "", "", cmdHeader, data);
			} else if (("00".equals(iEIa)) && (udh.length() == 14)) {
				String refNumber = udh.substring(4, 6);
				res = smsPpDownloadFormat(1, "70", "00", refNumber, cmdHeader,
						data);
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
			int splitNum, String data) {
		lastCmdHeader = cmdHeader;

		if ((udh != null) && (udh.startsWith("7000")) && (udh.length() > 4)) {
			udh = udh.substring(4) + udh.substring(0, 4);
		}

		if (splitNum <= 1) {
			return smsPpEnvelope(udh, cmdHeader, data);
		}
		//splitNum表示级联信息
		return smsPpEnvelope(udh, cmdHeader, data, splitNum);
	}

	public static List<String> smsPpUpd(int recordLen, String udh,
			String cmdHeader, int splitNum, String data) {
		lastCmdHeader = cmdHeader;
		List res = new ArrayList();
		List resPadded = new ArrayList();

		if ((udh != null) && (udh.startsWith("7000")) && (udh.length() > 4)) {
			udh = udh.substring(4) + udh.substring(0, 4);
		}

		if (("".equals(udh)) || (udh == null)) {
			if (splitNum <= 1)
				res = smsPpDownloadUnformat(2, "00", "", data);
			else
				res = smsPpDownloadUnformat(2, "00", "", data, splitNum);
		} else if (!udh.endsWith("7000")) {
			String refNumber = "";
			if (udh.startsWith("00"))
				refNumber = udh.substring(4, 6);
			else if (udh.startsWith("08")) {
				refNumber = udh.substring(4, 8);
			}

			if (splitNum <= 1)
				res = smsPpDownloadUnformat(2, "00", refNumber, data);
			else {
				res = smsPpDownloadUnformat(2, "00", refNumber, data, splitNum);
			}

		} else {
			String iEIa = udh.substring(0, 2);
			if (("70".equals(iEIa)) && (udh.length() == 4)) {
				if (splitNum <= 1)
					res = smsPpDownloadFormat(2, "70", "", "", cmdHeader, data);
				else {
					res = smsPpDownloadFormat(2, "70", "", "", cmdHeader, data,
							splitNum);
				}

			} else if (("00".equals(iEIa)) && (udh.length() == 14)) {
				String refNumber = udh.substring(4, 6);

				if (splitNum <= 1)
					res = smsPpDownloadFormat(2, "70", "00", refNumber,
							cmdHeader, data);
				else {
					res = smsPpDownloadFormat(2, "70", "00", refNumber,
							cmdHeader, data, splitNum);
				}
			} else {
				throw new SMSRuntimeException("TPUDH 格式不对！TPUDH=" + udh);
			}
		}
		for (int i = 0; i < res.size(); i++) {
			String s = (String) res.get(i);
			int padLength = recordLen - s.length() / 2;
			if (s.length() / 2 < recordLen) {
				for (int j = 0; j < padLength; j++) {
					s = s + "FF";
				}
			}
			resPadded.add(s);
		}

		return resPadded;
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

	//udh+command packet
	private static List<String> smsPpDownloadFormat(int type, String formatTag,
			String concatenatedTag, String refNumber, String cmdHeader,
			String data) {
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
	public String getPUDHL() {
		return this.PUDHL;
	}
	public void setPUDHL(String pUDHL) {
		this.PUDHL = pUDHL;
	}
	
	public String getPRPI() {
		return this.PRPI;
	}
	public void setPRPI(String pRPI) {
		this.PRPI = pRPI;
	}
	public String getPRPIL() {
		return this.PRPIL;
	}
	public void setPRPIL(String pRPIL) {
	
	
		this.PRPIL = pRPIL;
	}

}