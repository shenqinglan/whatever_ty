package com.whty.security.ecc;

import static com.whty.euicc.common.apdu.ToTLV.toEccLV;
import static com.whty.euicc.common.apdu.ToTLV.toEccTLV;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.euicc.common.spring.SpringPropertyPlaceholderConfigurer;
import com.whty.euicc.common.utils.SocketHelper;

/**
 * 调用服务
 * 为isdp个人化和smsr change提供ECC算法
 * 包括：生成密钥对，签名和协商密钥
 * @author Administrator
 *
 */
public class EccServiceCall {
	private static Logger logger = LoggerFactory.getLogger(EccServiceCall.class);
	private static final String ecc_tool_ip = SpringPropertyPlaceholderConfigurer.getStringProperty("ecc_tool_ip");
	private static final String  ecc_tool_port = SpringPropertyPlaceholderConfigurer.getStringProperty("ecc_tool_port");
	
	private static final String RESP_SUCCESS_PREFIX = "8100";
	
	/**
	 * 8001约定的接口前缀（80是平台编码，01是接口编码）
	 * @return
	 */
	public static String CreateECCKeyPair(String sp, String sa, String sb,
			String sGx, String sGy, String sn, String sh){
		StringBuilder data = new StringBuilder().append(toEccLV(sp)).append(toEccLV(sa)).append(toEccLV(sb))
				.append(toEccLV(sGx)).append(toEccLV(sGy)).append(toEccLV(sn)).append(toEccLV(sh));
		String str = toEccTLV("8001",data.toString());
		return callEccService(str,RESP_SUCCESS_PREFIX);
	}

	/**
	 * 8002约定的接口前缀（80是平台编码，02是接口编码）
	 * @return
	 */
	public static String CreateECCPubKeyByDa(String sp, String sa, String sb,
			String sGx, String sGy, String sn, String sh, String sDa){
		StringBuilder data = new StringBuilder().append(toEccLV(sp)).append(toEccLV(sa)).append(toEccLV(sb))
				.append(toEccLV(sGx)).append(toEccLV(sGy)).append(toEccLV(sn)).append(toEccLV(sh)).append(toEccLV(sDa));
		String str = toEccTLV("8002",data.toString());
		return callEccService(str,RESP_SUCCESS_PREFIX);
	}
	
	/**
	 * 8003约定的接口前缀（80是平台编码，03是接口编码）
	 * @return
	 */
	public static String ECC_ECKA_Sign(String sp, String sa, String sb,
			String sGx, String sGy, String sn, String sh, String sM,String sDA){
		StringBuilder data = new StringBuilder().append(toEccLV(sp)).append(toEccLV(sa)).append(toEccLV(sb))
				.append(toEccLV(sGx)).append(toEccLV(sGy)).append(toEccLV(sn)).append(toEccLV(sh)).append(toEccLV(sM)).append(toEccLV(sDA));
		String str = toEccTLV("8003",data.toString());
		return callEccService(str,RESP_SUCCESS_PREFIX);
	}
	
	/**
	 * 8004约定的接口前缀（80是平台编码，04是接口编码）
	 * @return
	 */
	public static boolean ECC_ECKA_Verify(String sp, String sa, String sb,
			String sGx, String sGy, String sn, String sh, String sM,
			String spax, String spay, String sR, String sS){
		StringBuilder data = new StringBuilder().append(toEccLV(sp)).append(toEccLV(sa)).append(toEccLV(sb))
				.append(toEccLV(sGx)).append(toEccLV(sGy)).append(toEccLV(sn)).append(toEccLV(sh)).append(toEccLV(sM))
				.append(toEccLV(spax)).append(toEccLV(spay)).append(toEccLV(sR)).append(toEccLV(sS));
		String str = toEccTLV("8004",data.toString());
		return callEccServiceIfSuc(str,RESP_SUCCESS_PREFIX);	
	}

	/**
	 * 8005约定的接口前缀（80是平台编码，05是接口编码）
	 * @return
	 */
	public static String ECC_Key_Agreement(String sp, String sa, String sb,
			String sGx, String sGy, String sn, String sh, String sDA,
			String spbx, String spby, String sShareInfo, int iKeyLen){
		StringBuilder data = new StringBuilder().append(toEccLV(sp)).append(toEccLV(sa)).append(toEccLV(sb))
				.append(toEccLV(sGx)).append(toEccLV(sGy)).append(toEccLV(sn)).append(toEccLV(sh)).append(toEccLV(sDA))
				.append(toEccLV(spbx)).append(toEccLV(spby)).append(toEccLV(sShareInfo)).append(toEccLV(String.valueOf(iKeyLen)));
		String str = toEccTLV("8005",data.toString());
		return callEccService(str,RESP_SUCCESS_PREFIX);
	}
	
	private static boolean callEccServiceIfSuc(String str,String respTar) {
		try {
			byte[] b = SocketHelper.send(ecc_tool_ip, Integer.parseInt(ecc_tool_port), str.getBytes());
			if(b == null){
				logger.error("调用ecc服务返回为空");
				return false;
			}
			String resp = new String(b);
			if(!StringUtils.startsWith(resp, respTar)){
				logger.error("调用ecc服务异常,返回值为:{}",resp);
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("调用ecc服务异常,异常信息:{}",e.getMessage());
		}
		return false;
	}
	
	private static String callEccService(String str,String respTar) {
		try {
			byte[] b = SocketHelper.send(ecc_tool_ip, Integer.parseInt(ecc_tool_port), str.getBytes());
			if(b == null){
				logger.error("调用ecc服务返回为空");
				return null;
			}
			String resp = new String(b);
			if(!StringUtils.startsWith(resp, respTar)){
				logger.error("调用ecc服务异常,返回值为:{}",resp);
				return null;
			}
			return StringUtils.substring(resp, 0,3);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("调用ecc服务异常,异常信息:{}",e.getMessage());
		}
		return null;
	}
	
	
}
