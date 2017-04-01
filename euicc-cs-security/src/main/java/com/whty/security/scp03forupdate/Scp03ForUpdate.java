package com.whty.security.scp03forupdate;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.whty.euicc.common.apdu.ToTLV;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.common.utils.AnalyseUtils;
import com.whty.security.aes.AesCmac;
import com.whty.security.scp03t.dataencryption.CreateC_MAC;
import com.whty.security.scp03t.dataencryption.CreateKey;
import com.whty.security.scp03t.dataencryption.EncData;
import com.whty.security.scp03t.scp03t.bean.CmdApduBean;
import com.whty.security.scp03t.scp03t.bean.ExternalAuthBean;
import com.whty.security.scp03t.scp03t.bean.InitializeUpdateBean;
import com.whty.security.scp03t.scp03t.counter.Scp03Counter;
import com.whty.security.util.Converts;
/**
 * SCP03远程建立安全通道
 * 用于为ES8接口更新连接参数提供函数
 * @author sql
 *
 */
@Service
public class Scp03ForUpdate {
	public static InitializeUpdateBean initializeUpdate(String kver){
		byte[] b = new byte[8];
		Random random = new Random();
		random.nextBytes(b);//用随机数填充指定字节数组的元素
		String hostChallenge = Converts.bytesToHex(b).toUpperCase();
		String dataString = "8050" + kver + "0008" + hostChallenge;
		return new InitializeUpdateBean(dataString,hostChallenge);
	}
	
	public static String cardChallenge(String keyENC,String sqcCounter,String isdPAID){
		String context = sqcCounter + isdPAID;// 生成“环境”参数
		String cardChallenge = "";
		try {
			cardChallenge = CreateKey.cardChallenge(keyENC, context);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cardChallenge;
	}
	
	public static ExternalAuthBean externalAuthScp03(/*String isdPAid,*/String securedLevel,String hostChallenge,String cardChallenge,String keyMAC,String perMac)throws Exception{
		String context = hostChallenge + cardChallenge;	
		//keyMAC初始key(03t中由数据库提取)
		String S_MAC = CreateKey.S_MAC(keyMAC, context);
		// 计算主机密文
		String hostCryp = CreateKey.hostCryptogram(S_MAC, context);
		String cmd = "84" + "82" + securedLevel + "0010" + hostCryp;
		AesCmac one = new AesCmac();
		String cmdMac = one.calcuCmac1((perMac + cmd), S_MAC);
		cmd = cmd + cmdMac.substring(0, 16);
		return new ExternalAuthBean(cmd,cmdMac,hostChallenge,Scp03Counter.encCounter);//加密计数器
		
	}
	public static CmdApduBean gpApduScp03(String commandData,String hostChallenge,String cardChallenge,String encCounter,String secureLevel,String logicalChannel,String keyENC/*S_ENC*/,String keyMAC,String perMac) throws Exception{
		byte seLevel = Converts.hexToByte(secureLevel);
		String resultStr = "";
		String commandClA = commandData.substring(0, 2);
		String commandInS = commandData.substring(2, 4);
		String commandP1 = commandData.substring(4, 6);
		String commandP2 = commandData.substring(6, 8);
		String commandLc = commandData.substring(8, 10);
		String commandDataField = commandData.substring(10, 10 + (AnalyseUtils.atoi(commandLc)) * 2);
		
		String S_ENC="";
		String S_MAC="";
		String mac = "";
		String counter = "";		
		String context = hostChallenge + cardChallenge;
		
		String resultClA = "";
		String dataForMac = "";
		String claWithLogical ="";
		String commandLcForMac = "";
		try {
			S_ENC = CreateKey.S_ENC(keyENC, context);
			S_MAC = CreateKey.S_MAC(keyMAC, context);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EuiccBusiException("9001","密钥计算错误");
		}
		
		//byte[] commandLevel = (0x03 & seLevel);
		if ((0x3 & seLevel) == 0) {
			claWithLogical = AnalyseUtils.itoa((AnalyseUtils.atoi(commandClA)) + AnalyseUtils.atoi(logicalChannel),1);
		    resultStr = claWithLogical + commandInS + commandP1 + commandP2 + commandLc + commandDataField;
		}else if ((0x3 & seLevel) == 1) {
		    commandLcForMac = AnalyseUtils.itoa(AnalyseUtils.atoi(commandLc) + 8);
			resultClA = AnalyseUtils.itoa(AnalyseUtils.atoi(commandClA) + 4, 1);
			dataForMac = resultClA + commandInS + commandP1 + commandP2 + commandLc + commandDataField;
			CreateC_MAC one=new CreateC_MAC();
			mac = one.create( S_MAC, dataForMac,perMac);//计算MAC
			claWithLogical = AnalyseUtils.itoa((AnalyseUtils.atoi(commandClA)) + AnalyseUtils.atoi(logicalChannel),1);
			resultStr = claWithLogical + commandInS + commandP1 + commandP2 + commandLcForMac + commandDataField + mac;
			
		}else if ((0x3 & seLevel) == 3) {
			
			counter = Scp03Counter.addEncCounter(encCounter);//加密计数器
			EncData enc=new EncData();
			String encData=enc.encryption(S_ENC, commandDataField, counter);//计算密文
			commandLcForMac = AnalyseUtils.itoa((encData.length()/2) + 8);
			resultClA = AnalyseUtils.itoa(AnalyseUtils.atoi(commandClA) + 4, 1);
			dataForMac = resultClA + commandInS + commandP1 + commandP2 + commandLcForMac + encData;
			CreateC_MAC one=new CreateC_MAC();
			mac = one.create( S_MAC, dataForMac,perMac);//permac
			String macForResult = mac.substring(0, 16);//mac
			claWithLogical = AnalyseUtils.itoa((AnalyseUtils.atoi(resultClA)) + AnalyseUtils.atoi(logicalChannel),1);
			resultStr = claWithLogical + commandInS + commandP1 + commandP2 + ToTLV.toTLV(encData +  macForResult);
		}
		return new CmdApduBean(resultStr,counter,mac);
		
	}
	
	/**
	 * 组装Resp响应
	 * @param respData
	 * @param status
	 * @param secureLevel
	 * @param S_RMAC
	 * @return
	 * @throws Exception
	 */
	public static String gpRespScp03(String hostChallenge,String cardChallenge,String encCounter,String respData,String status,String secureLevel,String keyENC,String keyMAC,String perMac) throws Exception {
		byte seLevel = Converts.hexToByte(secureLevel);
		AesCmac oneCmac = new AesCmac();
		String resultStr = "";
		String encData = "";
		String respDataForMac = "";
		String resMac = "";
		String context = hostChallenge + cardChallenge;
		
		String S_rMAC = CreateKey.S_RMAC(keyMAC, context);
		String S_ENC = CreateKey.S_ENC(keyENC, context);
		if ((0x30 & seLevel) == 0x10) {
			respDataForMac = respData + status;
			resMac = oneCmac.calcuCmac1(respDataForMac, S_rMAC);
			resultStr = respData + resMac.substring(0, 16) + status;
		}else if ((0x30 & seLevel) == 0x30) {
			
			if (respData == "") { //待确定
				encData = respData;
			}else {
				encData = EncData.respEncrypt(S_ENC,respData,encCounter);
			}
			
			respDataForMac = perMac + encData + status;
			resMac = oneCmac.calcuCmac1(respDataForMac, S_rMAC);
			resultStr = encData + resMac.substring(0, 16) + status;
		}
		return resultStr;
		
	}
	

}
