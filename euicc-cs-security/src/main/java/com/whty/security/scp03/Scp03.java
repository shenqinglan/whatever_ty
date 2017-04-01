package com.whty.security.scp03;

import java.util.Random;

import com.whty.security.aes.AesCmac;
import com.whty.security.scp03.bean.InitializeUpdateBean;
import com.whty.security.scp03.bean.InitializeUpdateRespBean;
import com.whty.security.scp03t.dataencryption.CreateC_MAC;
import com.whty.security.scp03t.dataencryption.CreateKey;
import com.whty.security.scp03t.dataencryption.EncData;
import com.whty.security.util.Converts;
/**
 * SCP03全步骤类
 * @author Administrator
 *
 */
public class Scp03 {

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
		String commandData = "84" + "0A" + keyVer + "00" + hostChallenge;
		return new InitializeUpdateBean(commandData,hostChallenge);
	}

	/**
	 * scp03第一步initizlizeUpdate返回检查
	 * @param message
	 * @param hostChallenge
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
		//String S_MAC = ExternalAuthKeyInitial.S_MAC(keyMAC, context); // 生成过程密钥
		//String S_ENC=ExternalAuthKeyInitial.S_ENC(keyENC, context);
		String S_MAC = CreateKey.S_MAC(keyMAC, context); // 生成过程密钥
		String S_ENC=CreateKey.S_ENC(keyENC, context);
		String S_RMAC = CreateKey.S_RMAC(keyMAC, context);
		String cryptogram = message.substring(46, 62);
		//String testCryptogram = ExternalAuthKeyInitial.cardCryp(S_MAC, context);// 鉴别卡密码
		String testCryptogram = CreateKey.cardCryp(S_MAC, context);// 鉴别卡密码
		boolean checkResult = testCryptogram.equals(cryptogram); 
		return new InitializeUpdateRespBean(checkResult,cardChallenge,S_MAC,S_ENC,hostChallenge);
	}

	/**
	 * scp03第二步externalAuth命令
	 * @param securedLevel
	 * @param hostChallenge 第一步命令initizlizeUpdate产生
	 * @param cardChallenge 第一步命令返回产生
	 * @param S_MAC
	 * @return
	 * @throws Exception
	 */
	public String externalAuthCmd(String securedLevel,String hostChallenge,String cardChallenge,String S_MAC,String perMac) throws Exception {
		// 计算“环境”参数
		String context = hostChallenge + cardChallenge;
				//+ InitializeUpdateResponse.getCardChallenge();
		// 生成过程密钥S-MAC
		//String S_MAC = InitializeUpdateResponse.getS_MAC();
		// 计算主机密文
		//String hostCryp = ExternalAuthKeyInitial.hostCryptogram(S_MAC, context);
		String hostCryp = CreateKey.hostCryptogram(S_MAC, context);
		System.out.println("hostCryp:" + hostCryp);
		String cmd = "85" + "11" + securedLevel + hostCryp;
		AesCmac one = new AesCmac();
		String cmdMac = one.calcuCmac1((perMac + cmd), S_MAC);
		cmd = cmd + cmdMac.substring(0, 16);
		return cmd;
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
	 * @return
	 */
	public String encryptionData(String unEncryData,String encCounter,String S_ENC,String S_MAC,String perMac){
		//String	S_ENC=InitializeUpdateResponse.getS_ENC();
		//String	S_MAC=InitializeUpdateResponse.getS_MAC();//生成过程密钥
		System.out.println("S_ENC:"+S_ENC);
		EncData enc=new EncData();
		String encData=enc.encryption(S_ENC, unEncryData, encCounter);//计算密文
		System.out.println("encData:"+encData);
		int L=(encData.length())/2+8;
		String Lcc=Integer.toHexString(L);
		String cmdData="86"+Lcc+encData;
		CreateC_MAC one=new CreateC_MAC();
		String C_MAC=one.create( S_MAC, cmdData,perMac);//计算MAC
		cmdData=cmdData+C_MAC;					//拼装报文
		return cmdData;
	}
	
	
	/**
	 * todo
	 * @return
	 */
	public String addEncCounter(String counter) {
		String encCounter="000000"+Integer.toHexString(Integer.parseInt(counter, 16)+1);//加密计数器增加
		encCounter=encCounter.substring((encCounter.length()-6), encCounter.length());
		//ExternalAuth.setEncCounter(encCounter);
		return encCounter;
	}

}
