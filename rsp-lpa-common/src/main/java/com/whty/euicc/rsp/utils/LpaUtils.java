package com.whty.euicc.rsp.utils;

public class LpaUtils {
	
	/**
	 * 检查dp地址
	 * @return
	 */
	public static boolean checkDpAddress() {
		return true;
	}
	
	/**
	 * 生成上下文参数
	 * @return
	 */
	public static String generateCtxParams1() {
		return "0102030405060708";
	}
	
	/**
	 * 是否包含PPR
	 * @return
	 */
	public static boolean isContainPPR(String profileMetadata) {
		profileMetadata = profileMetadata.substring(profileMetadata.indexOf("B7"));
		if (profileMetadata.length() > 20) {
			return true;
		}
		return false;
	}
	
	/**
	 * RAT是否允许PPR
	 * @return
	 */
	public static boolean isRATAllowedPPR() {
		return true;
	}
	
	/**
	 * 是否需要确认码
	 * @return
	 */
	public static boolean isConfirmationCodeRequired(String smdpSignature2) {
		return smdpSignature2.endsWith("01");
	}
	
	/**
	 * 检查元数据
	 */
	public static boolean checkMetadata() {
		return true;
	}
}
