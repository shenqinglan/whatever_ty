package com.whty.security.scp03t.scp03t;

import java.util.Random;

import com.whty.security.aes.AesCmac;
import com.whty.security.scp03t.dataencryption.CreateC_MAC;
import com.whty.security.scp03t.dataencryption.CreateKey;
import com.whty.security.scp03t.dataencryption.EncData;
import com.whty.security.scp03t.scp03t.bean.CmdApduBean;
import com.whty.security.scp03t.scp03t.bean.ExternalAuthBean;
import com.whty.security.scp03t.scp03t.bean.InitializeUpdateBean;
import com.whty.security.scp03t.scp03t.bean.InitializeUpdateRespBean;
import com.whty.security.scp03t.scp03t.counter.Scp03Counter;
import com.whty.security.util.Converts;
import com.whty.euicc.common.apdu.ToTLV;
import com.whty.euicc.common.exception.EuiccBusiException;
/**
 * SCP03t全步骤类
 * @author Administrator
 *
 */
public class Scp03t {
	/**
	 * scp03第一步initizlizeUpdate命令
	 * @param keyVer
	 * @return
	 */
	public InitializeUpdateBean initializeUpdateCmd(String keyVer) {
		
		
		byte[] b = new byte[8];
		Random random = new Random();
		random.nextBytes(b);
		String hostChallenge = Converts.bytesToHex(b);
		String apdu = "84" + "0A" + keyVer + "00" + hostChallenge.toUpperCase();
		return new InitializeUpdateBean(apdu,hostChallenge);
	}
	
	/**
	 * scp03第一步initizlizeUpdate返回检查
	 * @param message
	 * @param hostChallenge 第一步命令initizlizeUpdate产生
	 * @param keyMAC
	 * @return
	 * @throws Exception
	 */
	public InitializeUpdateRespBean checkInitializeUpdateResp(String message,
			String hostChallenge, String keyMAC,String keyENC) throws Exception {
		if (message.length() != 68) {
			System.out.println("响应长度错误!");
			return new InitializeUpdateRespBean(false);
		}
		String cardChallenge = message.substring(30, 46);
		String context = hostChallenge + cardChallenge;// 生成“环境”参数
		String S_MAC = CreateKey.S_MAC(keyMAC, context); // 生成过程密钥
		String S_ENC=CreateKey.S_ENC(keyENC, context);
		String S_RMAC = CreateKey.S_RMAC(keyMAC, context);
		String cryptogram = message.substring(46, 62);
		String testCryptogram = CreateKey.cardCryp(S_MAC, context);// 鉴别卡密码
		boolean checkResult = testCryptogram.equals(cryptogram); 
		return new InitializeUpdateRespBean(checkResult,cardChallenge,S_MAC,S_RMAC,S_ENC,hostChallenge);
	}
	public String cardChallenge(String keyENC,String sqcCounter,String isdPAID){
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
	
	/**
	 * scp03第二步externalAuth命令
	 * @param securedLevel
	 * @param hostChallenge 第一步命令initizlizeUpdate产生
	 * @param cardChallenge 第一步命令返回产生
	 * @param S_MAC 第一步命令返回产生
	 * @param perMac 使用者赋初值
	 * @return
	 * @throws Exception
	 */
	public ExternalAuthBean externalAuthCmd(String securedLevel,String hostChallenge,String cardChallenge,String keyMAC/*S_MAC*/,String perMac) throws Exception {
		// 计算“环境”参数
		String context = hostChallenge + cardChallenge;	
		String S_MAC = CreateKey.S_MAC(keyMAC, context);
		// 计算主机密文
		String hostCryp = CreateKey.hostCryptogram(S_MAC, context);
		String cmd = "85" + "11" + securedLevel + hostCryp;
		AesCmac one = new AesCmac();
		String cmdMac = one.calcuCmac1((perMac + cmd), S_MAC);
		cmd = cmd + cmdMac.substring(0, 16);
		return new ExternalAuthBean(cmd,cmdMac,hostChallenge,Scp03Counter.encCounter);
	}

	/**
	 * scp03第二步externalAuth返回检查
	 * @param response
	 * @return
	 */
	public int checkExternalAuthResp(String response) {
		if("8500".equals(response)){
			return 0;
		}
		if("9F450101".equals(response)){
			return 1;
		}
		if("9F450102".equals(response)){
			return 2;
		}
		return 9;
	}
	
	/**
	 * 加密数据
	 * @param unEncryData
	 * @param encCounter 自己赋初值，或者由加法计数器得到
	 * @param S_ENC 第一步命令返回产生
	 * @param S_MAC 第一步命令返回产生
	 * @param perMac 使用者赋初值或者由上一次加密数据产生
	 * @return
	 */
	public CmdApduBean encryptionData(String unEncryData,String hostChallenge,String cardChallenge,String encCounter,String keyENC/*S_ENC*/,String keyMAC/*S_MAC*/,String perMac){
		String counter = Scp03Counter.addEncCounter(encCounter);
		// 计算“环境”参数
		String context = hostChallenge + cardChallenge;
		String S_ENC="";
		String S_MAC="";
		try {
			S_ENC = CreateKey.S_ENC(keyENC, context);
			S_MAC = CreateKey.S_MAC(keyMAC, context);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new EuiccBusiException("9001","密钥计算错误");
		}
		EncData enc=new EncData();
		String encData=enc.encryption(S_ENC, unEncryData, counter);//计算密文
		String cmdData = "86" + ToTLV.toTLV(encData+"1122334455667788");
		int len = cmdData.length();
		cmdData = cmdData.substring(0, len-16);
		CreateC_MAC one=new CreateC_MAC();
		String mac=one.create( S_MAC, cmdData,perMac);//计算MAC
		cmdData = "86" + ToTLV.toTLV(encData+mac.substring(0, 16));	//拼装报文
		return new CmdApduBean(cmdData,counter,mac);
	}
	/**
	 * 增加加密计数器
	 * @param encCounter 自己赋初值，或者由加法计数器得到
	 * @return
	 */
	public String addEncCounter(String encCounter) {
		String counter="000000"+Integer.toHexString(Integer.parseInt(encCounter, 16)+1);//加密计数器增加
		counter=counter.substring((counter.length()-6), counter.length());
		return counter;
	}
	
	
	/**
	 * scp03 APDU返回检查
	 * @param response
	 * @return
	 */
	public int resAPDU(String response){
		if("8600".equals(response)){
			return 0;
		}
		if("9F460101".equals(response)){
			return 1;
		}
		if("9F460102".equals(response)){
			return 2;
		}
		if(("8600".equals(response))&&(response.substring(0, 2).equalsIgnoreCase("86"))){
			return 100;
		}
		return 9;
	}
	/**
	 * 解密数据
	 * @param response
	 * @param encCounter 自己赋初值，或者由加法计数器得到
	 * @param S_ENC 第一步命令返回产生
	 * @param S_RMAC 第一步命令返回产生
	 * @param perMac 使用者赋初值或者由上一次加密数据产生
	 * @return
	 */
	public String response(String response,String encCounter,String S_ENC,String S_RMAC,String perMac){
		String resData="";
		String Lcc=response.substring(2, 4);
		int len=response.length()/2-2;
		if(Integer.getInteger(Lcc, 16)!=len){
			return "响应报文长度错误";
		}
		String resEncData=response.substring(4, (response.length()-16));
		if((resEncData.length()%32)!=0){
			return "加密响应数据长度错误";
		}
		String R_MAC=response.substring((response.length()-16),response.length());
		//完整性鉴定
		String data=perMac+"86"+Lcc+resEncData;
		
		AesCmac one;
		String checkR_MAC="";
		try {
			one = new AesCmac();
			checkR_MAC=one.calcuCmac1(data, S_RMAC);
			checkR_MAC=checkR_MAC.substring(0, 16);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(R_MAC==checkR_MAC){
			
		EncData enc=new EncData();
		resData=enc.decryption(S_ENC, resEncData, encCounter);
		return resData;
		}
		else{
			return "响应报文的加密数据完整性错误";
		}
		//解密响应加密数据
		
	}
}
