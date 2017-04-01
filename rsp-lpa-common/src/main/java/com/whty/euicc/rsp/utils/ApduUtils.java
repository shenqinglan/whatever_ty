package com.whty.euicc.rsp.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.whty.tls.util.ECCUtils;

public class ApduUtils {
	
	private static final String CLA = "80";
	private static final String INS = "E2";
	private static final String P1_11 = "11";
	private static final String P1_91 = "91";
	private static final String P2 = "00";
	private static final int len = 240;
	
	/**
	 * ES10b 获取eUICCInfo1信息
	 * @return
	 */
	public static String getEuiccInfo1Apdu() {
		String data = ToTLV.toTLV("BF20", "");
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1_91).append(P2).append(ToTLV.toTLV(data));
		return apdu.toString();
	}
	
	/**
	 * ES10b 获取eUICCInfo2信息
	 * @return
	 */
	public static String getEuiccInfo2Apdu(){
		String data = ToTLV.toTLV("BF22", "");
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1_91).append(P2).append(ToTLV.toTLV(data));
		return apdu.toString();
	}
	
	/**
	 * ES10b 获取卡片随机数
	 * @return
	 */
	public static String getEUICCChallengeApdu() {
		String data = ToTLV.toTLV("BF2E", "");
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1_91).append(P2).append(ToTLV.toTLV(data));
		return apdu.toString();
	}
	
	/**
	 * ES10b 双向认证
	 * @param smdpSigned1
	 * @param smdpSignature1
	 * @param euiccCiPKIdToBeUsed
	 * @param cert
	 * @param ctxParams1
	 * @return
	 */
	public static List<String> authenticateServerApdu(String smdpSigned1, String smdpSignature1, String euiccCiPKIdToBeUsed, 
			String cert, String ctxParams1) {
		String sctxParams1 = ToTLV.toTLV("A0", ToTLV.toTLV("A0", ToTLV.toTLV("80", ctxParams1) + ToTLV.toTLV("A1", "")));
		String data = ToTLV.toTLV("BF38", smdpSigned1 + smdpSignature1 + euiccCiPKIdToBeUsed + cert + sctxParams1);
		return subCommandData(data, len, false);
	}
	
	/**
	 * ES10b 获取RAT
	 * @return
	 */
	public static String getRATApdu() {
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1_91).append(P2).append(ToTLV.toTLV(ToTLV.toTLV("BF43", "")));
		return apdu.toString();
	}
	
	/**
	 * ES10b 取消会话
	 * @param transactionId TLV形式
	 * @param reason Value形式
	 * @return
	 */
	public static String cancelSessionApdu(String transactionId, String reason) {
		String data = ToTLV.toTLV("BF41",  transactionId + ToTLV.toTLV("81", reason));
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1_91).append(P2).append(ToTLV.toTLV(data));
		return apdu.toString();
	}
	
	/**
	 * ES10b 准备下载
	 * @param smdpSigned2 TLV格式
	 * @param smdpSignature2 TLV格式
	 * @param cert TLV格式
	 * @param code TLV格式
	 * @return
	 */
	public static List<String> prepareDownloadApdu(String smdpSigned2, String smdpSignature2, String cert, String hashCc) {
		StringBuilder data = new StringBuilder().append(smdpSigned2).append(smdpSignature2);
		if (hashCc != null) {
			data.append(ToTLV.toTLV("04",hashCc));
		}
		data.append(cert);
		return subCommandData(ToTLV.toTLV("BF21", data.toString()), len, false);
	}
	
	/**
	 * ES10b 发送SBPP
	 * @param data
	 * @return
	 */
	public static List<String> loadBoundProfilePackageApdu(List<String[]> data) {
		if (data.size() != 5) {
			throw new RuntimeException("SBPP数据错误");
		}
		List<String> SBPPList = new ArrayList<String>();
		//第一条指令InitialiseSecureChannel
		List<String> initialiseSecureChannel = subCommandData(data.get(0)[0], len, true);
		for (String initialiseSecureChannelApdu : initialiseSecureChannel) {
			SBPPList.add(initialiseSecureChannelApdu);
		}
		
		//第二条指令ConfigureISDP
		String configureISDP = data.get(1)[0];
		String configureISDPLength = Tools.toHex(String.valueOf(configureISDP.length()/2));
		StringBuilder configureISDPApdu = new StringBuilder().append(CLA).append(INS).append(P1_91).append(P2).append(configureISDPLength).append(configureISDP);
		SBPPList.add(configureISDPApdu.toString());
		
		//第三条指令StoreMetadata
		String[] storeMetadata = data.get(2);
		for(int i = 0; i < storeMetadata.length; i++) {
			List<String> storeMetadataList = subCommandData(storeMetadata[i], len, true);
			for (String metadataApdu : storeMetadataList) {
				SBPPList.add(metadataApdu);
			}
		}
		
		//第四条指令ReplaceSessionKeys
		String replaceSessionKeys = data.get(3)[0];
		String replaceSessionKeysLength = Tools.toHex(String.valueOf(replaceSessionKeys.length()/2));
		StringBuilder replaceSessionKeysApdu = new StringBuilder().append(CLA).append(INS).append(P1_91).append(P2).append(replaceSessionKeysLength).append(replaceSessionKeys);
		SBPPList.add(replaceSessionKeysApdu.toString());
		
		//第五条指令LoadProfileElements
		String[] loadProfileElements = data.get(4);
		for(int i = 0; i < loadProfileElements.length; i++) {
			List<String> loadProfileElementsList = subCommandData(loadProfileElements[i], len, true);
			for (String loadProfileElementsListApdu : loadProfileElementsList) {
				SBPPList.add(loadProfileElementsListApdu);
			}
		}
		return SBPPList;
	}
	
	/**
	 * ES10b 移除通知
	 * @param seqNumber
	 * @return
	 */
	public static String removeNotificationFromListApdu(String notifycounter) {
		String data = ToTLV.toTLV("BF30", ToTLV.toTLV("80", notifycounter));
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1_91).append(P2).append(ToTLV.toTLV(data));
		return apdu.toString();
	}
	
	/**
	 * ES10b 获取通知列表
	 * @return
	 */
	public static String listNotificationApdu() {
		String data = ToTLV.toTLV("BF28", ToTLV.toTLV("81", "0780"));
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1_91).append(P2).append(ToTLV.toTLV(data));
		return apdu.toString();
	}
	
	/**
	 * 获取通知计数器
	 */
	public static String getNotifyCounter() {
		String data = ToTLV.toTLV("BF28", "");
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1_91).append(P2).append(ToTLV.toTLV(data));
		return apdu.toString();
	}
	
	/**
	 * ES10b 检索通知列表
	 * @param notifyCounter
	 * @return
	 */
	public static String retrieveNotificationsListApdu(String notifyCounter) {
		String data = ToTLV.toTLV("BF2B", ToTLV.toTLV("A0", ToTLV.toTLV("80", notifyCounter)));
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1_91).append(P2).append(ToTLV.toTLV(data));
		return apdu.toString();
	}
	
	/**
	 * ES10b 传输CRL-A
	 * @return
	 */
	public static String loadCRLApdu() {
		String sversion = ToTLV.toTLV("02", "01");
		String ssignature = ToTLV.toTLV("30", "06082A8648CE3D040302");
		String sissuer = ToTLV.toTLV("30", "311D301B06035504030C144368696E6120556E69636F6D206553494D204341310E300C060355040B0C055653454E5331153013060355040A0C0C4368696E6120556E69636F6D310B300906035504060C02434E");
		String sthisUpdate = ToTLV.toTLV("17", "3137303131393038303030305A");
		String snextUpdate = ToTLV.toTLV("17", "3137303132323038303030305A");
		String suserCertificate = ToTLV.toTLV("02", "0158F741F660");
		String srevocationDate = ToTLV.toTLV("17", "3137303131383038303030305A");
		String scrlEntryExtensions = ToTLV.toTLV("30", ToTLV.toTLV("30", "0607678112010200010101FF040F170D3137303231383038303030305A"));
		String srevocationentry1 = ToTLV.toTLV("30", suserCertificate + srevocationDate + scrlEntryExtensions);
		String srevocationentry2 = "";
		String srevokedCertificates = ToTLV.toTLV("30", srevocationentry1 + srevocationentry2);
		String sauthorityKeyIdentifier = ToTLV.toTLV("30", ToTLV.toTLV("06", "551D23") + ToTLV.toTLV("01", "FF") + ToTLV.toTLV("04", ToTLV.toTLV("30", ToTLV.toTLV("80", "3bd3f57b34f44436e08f028b5101c0e0a9b0986e"))));
		String sCRLNumber = ToTLV.toTLV("30", ToTLV.toTLV("06", "551D14") + ToTLV.toTLV("04", ToTLV.toTLV("02", "00")));
		String sissuingDistributionPoint = ToTLV.toTLV("30", ToTLV.toTLV("06", "551D1C") + ToTLV.toTLV("01", "FF") + ToTLV.toTLV("01", ""));
		String stotalPartialCRLNumber = "";
		String spartialCRLNumber = "";
		String sextension = ToTLV.toTLV("30", sauthorityKeyIdentifier + sCRLNumber + sissuingDistributionPoint + stotalPartialCRLNumber + spartialCRLNumber);
		String scrlExtensions = ToTLV.toTLV("A0", sextension);
		String stbsCertList = ToTLV.toTLV("30", sversion + ssignature + sissuer + sthisUpdate + snextUpdate + srevokedCertificates + scrlExtensions);
		String ssignatureAlgorithm = ToTLV.toTLV("30", "06082A8648CE3D040302");
		String sP = "ffffffff00000001000000000000000000000000ffffffffffffffffffffffff";
		String sA = "FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFC";
		String sB = "5ac635d8aa3a93e7b3ebbd55769886bc651d06b0cc53b0f63bce3c3e27d2604b";
		String sG = "6b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c2964fe342e2fe1a7f9b8ee7eb4a7c0f9e162bce33576b315ececbb6406837bf51f5";
		String sN = "FFFFFFFF00000000FFFFFFFFFFFFFFFFBCE6FAADA7179E84F3B9CAC2FC632551";
		String sH = "1";
		String sGx = sG.substring(0, 32);
		String sGy = sG.substring(32, 64);
		String sSK_CI_ECDSA = "7fef0e7d8d1c6b347f5305bf9ddf726fe06080e18810c3b8e808855dc390b935";
		String ssignValue = ECCUtils.eccECKASign(sP, sA, sB, sGx, sGy, sN, sH, sSK_CI_ECDSA, stbsCertList);
		String sRR1 = ssignValue.substring(0, 32);
		String sSS1 = ssignValue.substring(32, 64);
		String sRR = ToTLV.toTLV("02", sRR1);
		String sSS = ToTLV.toTLV("02", sSS1);
		String ssignatureValue = ToTLV.toTLV("03", "00" + ToTLV.toTLV("30", sRR + sSS));
		String sloadCRL0 = ToTLV.toTLV("A0", stbsCertList + ssignatureAlgorithm + ssignatureValue);
		String sloadCRL1 = "";
		String data = ToTLV.toTLV("BF35", sloadCRL0 + sloadCRL1);
		return data;
	}
	
	/**
	 * ES10c 获取profile信息
	 * @param searchCriteria
	 * @param tagList
	 * @return
	 */
	public static String getProfilesInfoApdu(String isdp1) {
		String searchCriteria = "";
		if (!StringUtils.isEmpty(isdp1)) {
			searchCriteria = ToTLV.toTLV("A0", ToTLV.toTLV("4F", isdp1));
		}
		String tagList = ToTLV.toTLV("5C", "5A9F7090");
		String data = ToTLV.toTLV("BF2D", searchCriteria + tagList);
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1_91).append(P2).append(ToTLV.toTLV(data));
		return apdu.toString();
	}
	
	/**
	 * ES10c 卡内存重置
	 * @param resetOptions
	 * @return
	 */
	public static String eUICCMemoryResetApdu(String resetOptions){
		String data = ToTLV.toTLV("BF34", ToTLV.toTLV("82", resetOptions));
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1_91).append(P2).append(ToTLV.toTLV(data));
		return apdu.toString();
	}
	
	/**
	 * ES10c 获取EID
	 * @return
	 */
	public static String getEIDApdu() {
		String data = ToTLV.toTLV("BF3E", ToTLV.toTLV("5C", "5A"));
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1_91).append(P2).append(ToTLV.toTLV(data));
		return apdu.toString();
	}
	
	/**
	 * ES10c 设置昵称
	 * @param iccid
	 * @param profileNickname
	 * @return
	 */
	public static String setNicknameApdu(String iccid, String profileNickname){
		String data = ToTLV.toTLV("BF29", ToTLV.toTLV("5A", iccid) + ToTLV.toTLV("90", profileNickname));
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1_91).append(P2).append(ToTLV.toTLV(data));
		return apdu.toString();
	}
	
	/**
	 * ES10c 启用profile
	 * @param iccidOrISDPaid
	 * @param refreshflag
	 * @return
	 */
	public static String enableProfileApdu(String iccidOrISDPaid, String refreshflag){
		String data = null;
		if(iccidOrISDPaid.length()/2 == 10){
			data = ToTLV.toTLV("BF31", ToTLV.toTLV("A0", ToTLV.toTLV("5A", iccidOrISDPaid)) + ToTLV.toTLV("81", refreshflag));
		}else if(iccidOrISDPaid.length()/2 == 16){
			data = ToTLV.toTLV("BF31", ToTLV.toTLV("A0", ToTLV.toTLV("4F", iccidOrISDPaid)) + ToTLV.toTLV("81", refreshflag));
		}else{
			throw new RuntimeException("参数iccidOrISDPaid长度有误");
		}
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1_91).append(P2).append(ToTLV.toTLV(data));
		return apdu.toString();
	}
	
	/**
	 * ES10c 禁用profile
	 * @param iccidOrISDPaid
	 * @param refreshflag
	 * @return
	 */
	public static String disableProfileApdu(String iccidOrISDPaid, String refreshflag){
		String data = null;
		if(iccidOrISDPaid.length()/2 == 10){
			data = ToTLV.toTLV("BF32", ToTLV.toTLV("A0", ToTLV.toTLV("5A", iccidOrISDPaid)) + ToTLV.toTLV("81", refreshflag));
		}else if(iccidOrISDPaid.length()/2 == 16){
			data = ToTLV.toTLV("BF32", ToTLV.toTLV("A0", ToTLV.toTLV("4F", iccidOrISDPaid)) + ToTLV.toTLV("81", refreshflag));
		}else{
			throw new RuntimeException("参数iccidOrISDPaid长度有误");
		}
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1_91).append(P2).append(ToTLV.toTLV(data));
		return apdu.toString();
	}
	
	/**
	 * ES10c 删除profile
	 * @param iccidOrISDPaid
	 * @return
	 */
	public static String deleteProfileApdu(String iccidOrISDPaid){
		String data = null;
		if(iccidOrISDPaid.length()/2 == 10){
			data = ToTLV.toTLV("BF33", ToTLV.toTLV("5A", iccidOrISDPaid));
		}else if(iccidOrISDPaid.length()/2 == 16){
			data = ToTLV.toTLV("BF33", ToTLV.toTLV("4F", iccidOrISDPaid));
		}else{
			throw new RuntimeException("参数iccidOrISDPaid长度有误");
		}
		System.out.println(data);
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1_91).append(P2).append(ToTLV.toTLV(data));
		return apdu.toString();
	}
	
	/**
	 * ES10a 获取euicc配置地址
	 * @return
	 */
	public static String getEuiccConfiguredAddressesApdu() {
		String data = "BF3C00";
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1_91).append(P2).append(ToTLV.toTLV(data));
		return apdu.toString();
	}
	
	/**
	 * ES10a 设置默认Dp地址
	 * @param dp新地址
	 * @return
	 */
	public static String setDefaultDpAddressApdu(String dpAddrNew){
		String data = ToTLV.toTLV("BF3F", ToTLV.toTLV("80", dpAddrNew));
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1_91).append(P2).append(ToTLV.toTLV(data));
		return apdu.toString();
	}
	
	/**
	 * 获取profile信息
	 * @param iccidOrISDPaid
	 * @return 00表示激活，01表示去激活，6A88表示不存在
	 */
	public static String getProfilesInfo_profileStateApdu(String iccidOrISDPaid){
		String taglist = ToTLV.toTLV("5C", "9F70");
		String searchCriteria = null;
		if(iccidOrISDPaid.length()/2 == 10){
			searchCriteria = ToTLV.toTLV("A0", ToTLV.toTLV("5A", iccidOrISDPaid));
		}else if(iccidOrISDPaid.length()/2 == 16){
			searchCriteria = ToTLV.toTLV("A0", ToTLV.toTLV("4F", iccidOrISDPaid));
		}
		String data = ToTLV.toTLV("BF2D", searchCriteria + taglist);
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1_91).append(P2).append(ToTLV.toTLV(data));
		return apdu.toString();
	}
	
	/**
	 * 获取profile信息
	 * @return ISDPAID
	 */
	public static String getEUICCInfo_spaceApdu(){
		String data = ToTLV.toTLV("BF22", "");
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1_91).append(P2).append(ToTLV.toTLV(data));
		return apdu.toString();
	}
	
	/**
	 * 获取profile信息
	 * @param iccid
	 * @return ISDPAID
	 */
	public static String getProfilesInfo_ISDPaidApdu(String iccid){
		String taglist = ToTLV.toTLV("5C", "4F");
		String searchCriteria = ToTLV.toTLV("A0", ToTLV.toTLV("5A", iccid));
		String data = ToTLV.toTLV("BF2D", searchCriteria + taglist);
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1_91).append(P2).append(ToTLV.toTLV(data));
		return apdu.toString();
	}
	
	/**
	 * 检查当前是否有已激活的profile(启用profile第一步)
	 * @return
	 */
	public static String checkIfAnyEnabledProfileApdu(){
		String taglist = ToTLV.toTLV("5C", "9F70");
		String searchCriteria = "";
		String data = ToTLV.toTLV("BF2D", searchCriteria + taglist);
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1_91).append(P2).append(ToTLV.toTLV(data));
		return apdu.toString();
	}
	
	/**
	 * 此函数执行的前提条件是卡上仅存在一个通知，此函数在每次安装，下载激活去激活删除之后必须执行，才能保证连贯性
	 * @return
	 */
	public static String getNotifyCounterApdu(){
		String data = ToTLV.toTLV("BF28", "");
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1_91).append(P2).append(ToTLV.toTLV(data));
		return apdu.toString();
	}
	
	/**
	 * 此函数执行的前提条件是卡上仅存在一个通知，此函数在每次安装，下载激活去激活删除之后必须执行，才能保证连贯性
	 * @return
	 */
	public static String getNotifyTypeApdu(){
		String data = ToTLV.toTLV("BF28", "");
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1_91).append(P2).append(ToTLV.toTLV(data));
		return apdu.toString();
	}
	
	/**
	 * 此函数执行的前提条件是卡上仅存在一个通知，此函数在每次安装，下载激活去激活删除之后必须执行，才能保证连贯性
	 * @param notifyCounter
	 * @return
	 */
	public static String removeNotificationApdu(String notifyCounter){
		String data = ToTLV.toTLV("BF30", ToTLV.toTLV("80", notifyCounter));
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1_91).append(P2).append(ToTLV.toTLV(data));
		return apdu.toString();
	}
	
	/**
	 * 此函数执行的前提条件是卡上仅存在一个通知，此函数在每次安装，下载激活去激活删除之后必须执行，才能保证连贯性
	 * @param iccGp_notifycounter
	 * @return
	 */
	public static String nextNotifyCounterApdu(String iccGp_notifycounter){
		String nextNotifyCounter = "";
		int inotifycounter = Integer.valueOf(iccGp_notifycounter, 16);
		String inotifycounterHex = Tools.toHex(String.valueOf(++inotifycounter));
		inotifycounterHex = "000000" + inotifycounterHex;
		if(inotifycounter < 127){
			nextNotifyCounter = inotifycounterHex.substring(inotifycounterHex.length()-2);
		}else if(inotifycounter < 32767){
			nextNotifyCounter = inotifycounterHex.substring(inotifycounterHex.length()-4);
		}else if(inotifycounter < 8388607){
			nextNotifyCounter = inotifycounterHex.substring(inotifycounterHex.length()-6);
		}else{
			nextNotifyCounter = inotifycounterHex.substring(inotifycounterHex.length()-8);
		}
		return nextNotifyCounter;
	}
	
	/**
	 * 根据长度截取指令数据
	 * @param data
	 * @param len
	 * @param isLenSub长度是否需要截取
	 * @return
	 */
	private static List<String> subCommandData(String data, int len, boolean isLenSub) {
		List<String> commandDataList = new ArrayList<String>();
		int dataLen = data.length()/2;
		int cP2 = 0;
		while(dataLen != 0) {
			if (dataLen > len) {
				String subData = data.substring(0, 2*len);
				StringBuilder apdu = new StringBuilder();
				if (isLenSub) {
					apdu.append(CLA).append(INS).append(P1_11).append(Tools.toHex(String.valueOf(cP2))).append(Tools.itoa(len, 1)).append(subData);
				} else {
					apdu.append(CLA).append(INS).append(P1_11).append(Tools.toHex(String.valueOf(cP2))).append(Tools.toHex(String.valueOf(len))).append(subData);
				}
				commandDataList.add(apdu.toString());
				data = data.substring(2*len);
				dataLen = dataLen - len;
				cP2++;
			} else {
				StringBuilder apdu = new StringBuilder();
				if (isLenSub) {
					apdu.append(CLA).append(INS).append(P1_91).append(Tools.toHex(String.valueOf(cP2))).append(Tools.itoa(dataLen, 1)).append(data);
				} else {
					apdu.append(CLA).append(INS).append(P1_91).append(Tools.toHex(String.valueOf(cP2))).append(Tools.toHex(String.valueOf(dataLen))).append(data);
				}
				dataLen = 0;
				commandDataList.add(apdu.toString());
			}
		}
		return commandDataList;
	}
	
}
