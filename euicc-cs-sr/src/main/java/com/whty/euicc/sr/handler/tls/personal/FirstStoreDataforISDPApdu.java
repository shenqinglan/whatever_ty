package com.whty.euicc.sr.handler.tls.personal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.whty.euicc.common.apdu.ToTLV;
import com.whty.euicc.profile.util.Tool;
import com.whty.security.ecc.ECCUtils;

/**
 * 个人化第一条store data的apdu拼装
 * @author lw,zyj
 *
 */
@Service
public class FirstStoreDataforISDPApdu{
	
	private Logger logger = LoggerFactory.getLogger(FirstStoreDataforISDPApdu.class);
	
	private final String STORE_CLA = "80";
	private final String STORE_INS = "E2";
	private final String STORE_P1 = "09";
	private final String STORE_P2 = "00";
	private String STORE_Lc;
	
	private final String DGI = "3A01";
	private final String TAG_93 = "930102";
	private final String TAG_42 = "420102";
	private final String TAG_5F20 = "5F200102";
	private final String TAG_95 = "950188";
	private final String TAG_5F24 = "5F240421450101";
	private final String TAG_73 = "7303C80101";
	private final String TAG_7F49 = "7f4946B041046466E042804FAAC48F839EA53E85D0B8B93F2F015028A472F07F3227AF408170ACFB39D198BA7D0DCFF3DE5032A6CC8F6ACC84EF556BFE530DEC0FF75F2AF59AF00100";//todo
	private final String P="ffffffff00000001000000000000000000000000ffffffffffffffffffffffff";
	private final String A="FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFC";
	private final String B="5ac635d8aa3a93e7b3ebbd55769886bc651d06b0cc53b0f63bce3c3e27d2604b";
	private final String Gx="6b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296";
	private final String Gy="4fe342e2fe1a7f9b8ee7eb4a7c0f9e162bce33576b315ececbb6406837bf51f5";
	private final String N="FFFFFFFF00000000FFFFFFFFFFFFFFFFBCE6FAADA7179E84F3B9CAC2FC632551";
	private final String H="1";
	private StringBuilder data_sign;
	private final String D_CI_ECDSA="CCF97608A5081B8F478FBAB00CFE6F5A50B1C23C4B42E95EFFDDFB2DD1AD6676";
	
	
	public byte[] firstStoreDataForISDPApdu() {
		data_sign=new StringBuilder().append(TAG_93).append(TAG_42).append(TAG_5F20).append(TAG_95).append(TAG_5F24).append(TAG_73).append(TAG_7F49);
		String data_sign_res=ECCUtils.eccECKASign(P, A, B, Gx, Gy, N, H, data_sign.toString(), D_CI_ECDSA);
		if(data_sign_res==null){
			throw new RuntimeException("签名算法出错");
		}
		String data_sign_res_Length=Tool.toHex(String.valueOf(data_sign_res.length()/2));
		StringBuilder TAG_5F37=new StringBuilder().append("5F37").append(data_sign_res_Length).append(data_sign_res);
		StringBuilder cert=new StringBuilder(data_sign).append(TAG_5F37);
		StringBuilder TAG_7F21=new StringBuilder().append("7F21").append(ToTLV.toTLV(cert.toString()));
		String TAG_7F21_Length=Tool.toHex(String.valueOf(TAG_7F21.length()/2));
		StringBuilder TAG_DGI=new StringBuilder().append(DGI).append(TAG_7F21_Length).append(TAG_7F21); 
		STORE_Lc = Tool.toHex(String.valueOf(TAG_DGI.toString().length()/2));
		StringBuilder apdu = new StringBuilder().append(STORE_CLA).append(STORE_INS).append(STORE_P1).append(STORE_P2).append(STORE_Lc).append(TAG_DGI);
		StringBuilder finalApdu=new StringBuilder().append("22").append(ToTLV.toTLV(apdu.toString()));
		logger.info("个人化first store_data ISD-P的APDU指令为:{}",finalApdu.toString());
		return finalApdu.toString().getBytes();
	}

}
