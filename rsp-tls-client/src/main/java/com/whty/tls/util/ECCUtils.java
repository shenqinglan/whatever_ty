package com.whty.tls.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * 调用动态链接库
 * 为isdp个人化和smsr change提供ECC算法
 * 包括：生成密钥对，签名和协商密钥
 * @author Administrator
 *
 */
public class ECCUtils {

	private static final Logger logger = LoggerFactory.getLogger(ECCUtils.class);
	public interface CLibrary extends Library {
		CLibrary INSTANCE = (CLibrary)Native.loadLibrary("EccArth", CLibrary.class);
		
		public String CreateECCKeyPair(String sp, String sa, String sb, String sGx, String sGy, String sn, String sh);
		public String ECC_ECKA_Sign(String sp, String sa, String sb, String sGx, String sGy, String sn, String sh, String sM, String sDA);
		public String ECC_Key_Agreement(String sp,String sa,String sb,String sGx,String sGy,String sn,String sh,String sDA,String spbx,String spby,String sShareInfo,int iKeyLen);
		public String CreateECCPubKeyByDa(String sp, String sa, String sb, String sGx, String sGy, String sn, String sh, String sDA);
		public boolean ECC_ECKA_Verify(String sp, String sa, String sb, String sGx, String sGy, String sn, String sh, String sM, String spax, String spay, String sR, String sS);
	}
	
	/**
	 * 获取密钥对
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
		return CLibrary.INSTANCE.CreateECCKeyPair(sp, sa, sb, sGx, sGy, sn, sh);
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
		return CLibrary.INSTANCE.ECC_ECKA_Sign(sp, sa, sb, sGx, sGy, sn, sh, sM, sDA);
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
		return CLibrary.INSTANCE.ECC_Key_Agreement(sp, sa, sb, sGx, sGy, sn, sh, sDA, spbx, spby, sShareInfo, iKeyLen);
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
		return CLibrary.INSTANCE.CreateECCPubKeyByDa(sp, sa, sb, sGx, sGy, sn, sh, sDA);
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
		return CLibrary.INSTANCE.ECC_ECKA_Verify(sp, sa, sb, sGx, sGy, sn, sh, sM, spax, spay, sR, sS);
	}
}

