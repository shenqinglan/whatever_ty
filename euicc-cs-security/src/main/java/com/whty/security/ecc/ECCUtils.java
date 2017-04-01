package com.whty.security.ecc;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.euicc.common.spring.SpringPropertyPlaceholderConfigurer;
import com.whty.security.ecc.EccDllCall.CLibrary;

/**
 * 为isdp个人化和smsr change提供ECC算法
 * 配置文件中ecc_call_type值为"1"时调用服务的方法
 * 否则调用动态库的方法
 * @author Administrator
 *
 */
public class ECCUtils {

	private static final Logger logger = LoggerFactory.getLogger(ECCUtils.class);
	private static final String ecc_call_type = SpringPropertyPlaceholderConfigurer.getStringProperty("ecc_call_type");
	private static final String SERVICE_CALL_TYPE = "1";
	
	private static boolean isServiceCall(){
		return StringUtils.equals(SERVICE_CALL_TYPE, ecc_call_type) ? true : false;
	}
	
	
	/**
	 * 获取密钥对,
	 * @param sp 固定值
	 * @param sa 固定值
	 * @param sb 固定值
	 * @param sGx 固定值
	 * @param sGy 固定值
	 * @param sn 固定值
	 * @param sh 固定值
	 * @return
	 */
	public static String createECCKeyPair(String sp, String sa, String sb, String sGx, String sGy, String sn, String sh) {
		if(isServiceCall()){
			return EccServiceCall.CreateECCKeyPair(sp, sa, sb, sGx, sGy, sn, sh);
		}
		return EccDllCall.createECCKeyPair(sp, sa, sb, sGx, sGy, sn, sh);
	}
	
	/**
	 * 签名
	 * @param sp 固定值
	 * @param sa 固定值
	 * @param sb 固定值
	 * @param sGx 固定值
	 * @param sGy 固定值
	 * @param sn 固定值
	 * @param sh 固定值
	 * @param sM 固定值
	 * @param sDA 签名数据
	 * @return
	 */
	public static String eccECKASign(String sp, String sa, String sb, String sGx, String sGy, String sn, String sh, String sM, String sDA) {
		if(isServiceCall()){
			return EccServiceCall.ECC_ECKA_Sign(sp, sa, sb, sGx, sGy, sn, sh, sM, sDA);
		}
		return EccDllCall.eccECKASign(sp, sa, sb, sGx, sGy, sn, sh, sM, sDA);
	}
	
	
	/**
	 * 协商密钥
	 * @param sp 固定值
	 * @param sa 固定值
	 * @param sb 固定值
	 * @param sGx 固定值
	 * @param sGy 固定值
	 * @param sn 固定值
	 * @param sh 固定值
	 * @param sDA ECASD私钥
	 * @param spbx ECASD公钥前64位
	 * @param spby ECASD公钥后64位
	 * @param sShareInfo 固定值
	 * @param iKeyLen 固定值
	 * @return
	 */
	public static String eccKeyAgreement(String sp,String sa,String sb,String sGx,String sGy,String sn,String sh,String sDA,String spbx,String spby,String sShareInfo,int iKeyLen) {
		if(isServiceCall()){
			return EccServiceCall.ECC_Key_Agreement(sp, sa, sb, sGx, sGy, sn, sh, sDA, spbx, spby, sShareInfo, iKeyLen);
		}
		return EccDllCall.eccKeyAgreement(sp, sa, sb, sGx, sGy, sn, sh, sDA, spbx, spby, sShareInfo, iKeyLen);
	}
	
	/**
	 * 通过数据获取公钥
	 * @param sp 固定值
	 * @param sa 固定值
	 * @param sb 固定值
	 * @param sGx 固定值
	 * @param sGy 固定值
	 * @param sn 固定值
	 * @param sh 固定值
	 * @param sDA 签名数据
	 * @return
	 */
	public static String createECCPubKeyByDa(String sp, String sa, String sb, String sGx, String sGy, String sn, String sh, String sDA) {
		if(isServiceCall()){
			return EccServiceCall.CreateECCPubKeyByDa(sp, sa, sb, sGx, sGy, sn, sh, sDA);
		}
		return EccDllCall.createECCPubKeyByDa(sp, sa, sb, sGx, sGy, sn, sh, sDA);
	}
	
	/**
	 * 验签
	 * @param sp 固定值
	 * @param sa 固定值
	 * @param sb 固定值
	 * @param sGx 固定值
	 * @param sGy 固定值
	 * @param sn 固定值
	 * @param sh 固定值
	 * @param sM 固定值
	 * @param spax 固定值
	 * @param apay 固定值
	 * @param sR 固定值
	 * @param sS 固定值
	 * @return
	 */
	public static boolean eccECKAVerify(String sp, String sa, String sb, String sGx, String sGy, String sn, String sh, String sM, String spax, String spay, String sR, String sS) {
		if(isServiceCall()){
			return EccServiceCall.ECC_ECKA_Verify(sp, sa, sb, sGx, sGy, sn, sh, sM, spax, spay, sR, sS);
		}
		return EccDllCall.eccECKAVerify(sp, sa, sb, sGx, sGy, sn, sh, sM, spax, spay, sR, sS);
	}
}
