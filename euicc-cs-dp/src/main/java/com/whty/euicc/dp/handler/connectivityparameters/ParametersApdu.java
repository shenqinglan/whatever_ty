package com.whty.euicc.dp.handler.connectivityparameters;

import org.springframework.stereotype.Service;

import com.whty.euicc.common.apdu.ToTLV;
import com.whty.security.aes.AesCbc;
import com.whty.security.scp03forupdate.Scp03ForUpdate;

/**
 * 更新连接参数APDU拼装
 * @author Administrator
 *
 */
@Service
public class ParametersApdu {
	private final String CLA = "80";
	private final String INS = "E2";
	private final String P1 = "88";
	private final String P2 = "00";
	
	
	
	
   public String getScp03SequenceApdu(String kerV) {
		String iniString = Scp03ForUpdate.initializeUpdate(kerV)+"00";
		String commandString = ToTLV.toTLV("AA", ToTLV.toTLV("22", iniString));
		return commandString;
   }
   
	public String cmdDataApdu() {
		String input = "0607913386994211F1";
		String paramIsdp1 = ToTLV.toTLV("A0", input);
		String dataString = ToTLV.toTLV("3A07", paramIsdp1);
		String dataField = ToTLV.toTLV(dataString);
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1).append(P2).append(dataField);
		return apdu.toString();
	}
	public String tlv45IsdpApdu(){
		String input = "495344505344494E01";
		String paramIsdp1 = ToTLV.toTLV("45", input);
		String dataString = ToTLV.toTLV("0070", paramIsdp1);
		String dataField = ToTLV.toTLV(dataString);
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1).append(P2).append(dataField);
		return apdu.toString();
	
		
	}
	public String tlv42IsdpApdu(){
		String input = "4953445001";
		String paramIsdp1 = ToTLV.toTLV("42", input);
		String dataString = ToTLV.toTLV("0070", paramIsdp1);
		String dataField = ToTLV.toTLV(dataString);
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1).append(P2).append(dataField);
		return apdu.toString();
	
	}
	public String tlv5F20IsdpApdu(){
		String input = "47534D4101";
		String paramIsdp1 = ToTLV.toTLV("5F20", input);
		String dataString = ToTLV.toTLV("0070", paramIsdp1);
		String dataField = ToTLV.toTLV(dataString);
		StringBuilder apdu = new StringBuilder().append(CLA).append(INS).append(P1).append(P2).append(dataField);
		return apdu.toString();
	
	}
	public TlvTokenBean tlvToken() throws Exception {
		String tokenKey = "20212223242526270101010101010101";
		String keyVer1 = "70";//脚本上为70
		String keyCheckData = "01010101010101010101010101010101";
		String ivString = "00000000000000000000000000000000";
		String keyDek = "343D0E3E42978620B90CD15DDF087074";//来自脚本
		String tokenId = "01";//脚本为01
		String encryptEncKey = AesCbc.aesCbc1(tokenKey, keyDek, ivString, 0);
		String tempString = AesCbc.aesCbc1(keyCheckData, tokenKey, ivString, 0);
		String KCV = tempString.substring(0,6);
		String tlvKCV = ToTLV.toTLV(KCV);
		String tokenVerKcv = keyVer1 + KCV;
		String tlvEncKey = ToTLV.toTLV(ToTLV.toTLV(encryptEncKey));
		String cmdString = ToTLV.toTLV(keyVer1 + "88" + tlvEncKey + tlvKCV);//脚本缺后括号，取值还不能确定
		String cmdData = "80D800" + tokenId + cmdString;
		return new TlvTokenBean(cmdData, tokenVerKcv);
	}
	
}
