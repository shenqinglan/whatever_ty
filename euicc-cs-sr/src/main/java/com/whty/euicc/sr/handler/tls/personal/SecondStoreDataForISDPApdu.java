package com.whty.euicc.sr.handler.tls.personal;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.whty.euicc.common.apdu.ToTLV;
import com.whty.euicc.profile.util.Tool;
import com.whty.security.ecc.ECCUtils;

/**
 * 个人化第二条store data的apdu拼装
 * @author lw,zyj
 *
 */
@Service
public class SecondStoreDataForISDPApdu {
	
	private Logger logger = LoggerFactory.getLogger(SecondStoreDataForISDPApdu.class);
	
	private final String CLA = "80";
	private final String INS = "E2";
	private final String P1 = "89";
	private final String P2 = "01";
	private String Lc;
	private final String Le = "00";
	
	private final String DGI_3A02 = "3A02";
	private final String TAG_A6 = "A6";
	private final String TAG_90 = "90020309";
	private final String TAG_95 = "950110";
//	private final String TAG_96 = "960100";
	private final String TAG_80 = "800188";
	private final String TAG_81 = "810110";
	private final String TAG_82 = "820101";
	private final String TAG_83 = "830130";
	private final String TAG_91 = "9100";

	private final String P="ffffffff00000001000000000000000000000000ffffffffffffffffffffffff";
	private final String A="FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFC";
	private final String B="5ac635d8aa3a93e7b3ebbd55769886bc651d06b0cc53b0f63bce3c3e27d2604b";
	private final String Gx="6b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296";
	private final String Gy="4fe342e2fe1a7f9b8ee7eb4a7c0f9e162bce33576b315ececbb6406837bf51f5";
	private final String N="FFFFFFFF00000000FFFFFFFFFFFFFFFFBCE6FAADA7179E84F3B9CAC2FC632551";
	private final String H="1";
	
	public byte[] secondStoreDataForISDPApdu(String rc,String ePK,String eSK) {
		StringBuilder CRT=new StringBuilder().append(TAG_90).append(TAG_95).append(TAG_80).append(TAG_81).append(TAG_82).append(TAG_83).append(TAG_91);
		String CRT_Length=Tool.toHex(String.valueOf(CRT.length()/2));
		StringBuilder key_set=new StringBuilder().append(TAG_A6).append(CRT_Length).append(CRT);
		String TAG_A6_Length=Tool.toHex(String.valueOf(key_set.length()/2));
		StringBuilder TAG_3A02=new StringBuilder().append(DGI_3A02).append(TAG_A6_Length).append(key_set);
		StringBuilder TAG_7F49=new StringBuilder().append("7F49").append(Tool.toHex(String.valueOf(ePK.length()/2))).append(ePK);
		String RC=rc;
		StringBuilder TAG_0085=new StringBuilder("0085").append(Tool.toHex(String.valueOf(RC.length()/2))).append(RC);
		StringBuilder data_sign=new StringBuilder(TAG_3A02).append(TAG_7F49).append(TAG_0085);
		String data_sign_res=ECCUtils.eccECKASign(P, A, B, Gx, Gy, N, H, data_sign.toString(), eSK);
		if (StringUtils.isBlank(data_sign_res)) {
			throw new RuntimeException("签名算法出错");
		}
		StringBuilder TAG_5F37=new StringBuilder("5F37").append(Tool.toHex(String.valueOf(data_sign_res.length()/2))).append(data_sign_res);
		StringBuilder CmdMessage=new StringBuilder(TAG_3A02).append(TAG_7F49).append(TAG_5F37);
		Lc = Tool.toHex(String.valueOf(CmdMessage.length()/2));
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1).append(P2).append(Lc).append(CmdMessage);
		StringBuilder finalApdu=new StringBuilder().append("22").append(ToTLV.toTLV(apdu.toString()));
		logger.info("个人化second store_data ISD-P的APDU指令为:{}",finalApdu.toString());
//		apdu = new StringBuilder().append("2281A580E28901A03A0217A6019002030995011080018881011082010183013091007F49402F316ACFA02A122DADB585ACB17209A8627E8B45963D0925175FBD34F960EE16B6CED5BAD91E7682F938E7F18C4680160EE32F2158648D8AE58681E46CF0985F5F37403DC793B5AFD6EB786B639E585FD777140B21960FF04D8611A80642848EB239B09B5E1E1A10AE8E22962B22B41FDBE5AD97FF1982EC7857AB1CD5AF4A61BC2735");
//		System.out.println("个人化second store_data ISD-P的APDU"+apdu.toString());
		return finalApdu.toString().getBytes();
	}

}
