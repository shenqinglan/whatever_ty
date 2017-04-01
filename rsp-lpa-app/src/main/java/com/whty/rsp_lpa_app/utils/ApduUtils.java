package com.whty.rsp_lpa_app.utils;

public class ApduUtils {
	
	private static final String CLA = "80";
	private static final String INS = "E2";
	private static final String P1 = "11";
	private static final String P2 = "00";
	private static final String Le = "00";
	
	/**
	 * 获取eUICCInfo1信息apdu指令
	 * @return
	 */
	public static String getEUICCInfo1Apdu() {
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1).append(P2).append("BF2000").append(Le);
		return apdu.toString();
	}
	
	/**
	 * 获取随机数apdu指令
	 * @return
	 */
	public static String getEUICCChallengeApdu() {
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1).append(P2).append("BF2E00").append(Le);
		return apdu.toString();
	}
	
	/**
	 * 双向认证apdu指令
	 * @param smdpSigned1
	 * @param smdpSignature1
	 * @param euiccCiPKIdToBeUsed
	 * @param cert
	 * @param ctxParams1
	 * @return
	 */
	public static String authenticateServer(String smdpSigned1, String smdpSignature1, String euiccCiPKIdToBeUsed, 
			String cert, String ctxParams1) {
		StringBuilder data = new StringBuilder().append(smdpSigned1).append(smdpSignature1).append(euiccCiPKIdToBeUsed).append(cert).append(ctxParams1);
		String length = Tools.toHex(String.valueOf(data.length()/2));
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1).append(P2).append("BF38").append(length).append(data).append(Le);
		return apdu.toString();
	}
	
	/**
	 * 获取RAT apdu指令
	 * @return
	 */
	public static String getRATApdu() {
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1).append(P2).append("BF4300").append(Le);
		return apdu.toString();
	}
	
	/**
	 * 取消会话apdu指令
	 * @param transactionId
	 * @param reason
	 * @return
	 */
	public static String cancelSessionApdu(String transactionId, String reason) {
		StringBuilder data = new StringBuilder().append(transactionId).append(reason);
		String length = Tools.toHex(String.valueOf(data.length()/2));
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1).append(P2).append("BF41").append(length).append(data).append(Le);
		return apdu.toString();
	}
	
	/**
	 * 准备下载apdu指令
	 * @param smdpSigned2
	 * @param smdpSignature2
	 * @param cert
	 * @param code
	 * @return
	 */
	public static String prepareDownloadApdu(String smdpSigned2, String smdpSignature2, String cert, String code) {
		StringBuilder data = new StringBuilder().append(smdpSigned2).append(smdpSignature2);
		if (code != null) {
			data.append(code);
		}
		data.append(cert);
		String length = Tools.toHex(String.valueOf(data.length()/2));
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1).append(P2).append("BF21").append(length).append(data).append(Le);
		return apdu.toString();
	}
	
	/**
	 * 发送SBPP apdu指令
	 * @param data
	 * @return
	 */
	public static String loadBoundProfilePackageApdu(String data) {
//		String length = Tools.toHex(String.valueOf(data.length()/2));
//		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1).append(P2).append("BF36").append(length).append(data).append(Le);
//		return apdu.toString();
		return null;
	}
	
	/**
	 * 移除通知apdu指令
	 * @param seqNumber
	 * @return
	 */
	public static String removeNotificationFromListApdu(String seqNumber) {
		String length = Tools.toHex(String.valueOf(seqNumber.length()/2));
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1).append(P2).append("BF30").append(length).append(seqNumber).append(Le);
		return apdu.toString();
	}
	
	/**
	 * 获取profile信息apdu指令
	 * @param searchCriteria
	 * @param tagList
	 * @return
	 */
	public static String getProfilesInfoApdu(String searchCriteria, String tagList) {
		StringBuilder data = new StringBuilder();
		if(searchCriteria != null) {
			data.append(searchCriteria);
		}
		if(tagList != null) {
			data.append(tagList);
		}
		String tlvData = null;
		if(data.length() == 0) {
			tlvData = "BF2D00";
		} else {
			String length = Tools.toHex(String.valueOf(data.length()/2));
			tlvData = "BF2D" + length + data.toString();
		}
		
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1).append(P2).append(tlvData).append(Le);
		return apdu.toString();
	}
	
	/**
	 * 获取EID apdu指令
	 * @return
	 */
	public static String getEIDApdu() {
		String data = "5C015A";
		String length = Tools.toHex(String.valueOf(data.length()/2));
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1).append(P2).append("BF3E").append(length).append(data).append(Le);
		return apdu.toString();
	}
	
	/**
	 * 获取euicc配置地址
	 * @return
	 */
	public static String getEuiccConfiguredAddressesApdu() {
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1).append(P2).append("BF3C00").append(Le);
		return apdu.toString();
	}
}
