package com.whty.security.scp03t.dataencryption;

import com.whty.security.aes.AesCmac;

/**
 * 生成过程密钥类
 * 该类可以产生scp03t全流程中所需的参与直接计算的各种过程密钥，
 * 这些密钥大都由初始密钥keyENC，keyMAC，keyDEK，主机随机数，卡的相关信息等计算得到
 * @author Administrator
 *
 */
public class CreateKey {
	/**
	 * 数据推导函数KDF（Key Derivation）
	 * @param KI 数据推导所需密钥
	 * @param label  “标签”，表示所计算的密钥类型
	 * @param context “环境参数”（大部分是由主机随机数与卡随机数拼成的字符串）
	 * @param len  返回值字节数
	 * @return
	 * @throws Exception
	 */
	private static String KDF(String KI,String label,String context,int len) throws Exception {
		int h=8*16;
		int r=16;
		int n;
		if((len*8)%h!=0){
			n=(len*8)/h+1;
		}
		else {
			n=len*8/h;
		}
		String counter="01";
		String result="";
		String K="";
		String separationIndicator="00";
		String L="";
		
	   if( len ==8){L="0040";}
	   else if( len ==16){L="0080";}
	   else if( len ==24){L="00C0";}
	   else if( len ==32){L="0100";}
	   
		String data=label+separationIndicator+L+counter+context;
		
		if(n>(2^r-1)){
			System.out.println("Error!");
			
			return null;
		}
		for(int i=1;i<=n;i++){
			AesCmac one=new AesCmac();
			K=one.calcuCmac1(data,KI);//PRF用CMAC加密
			result=result+K;
			counter="02";
			data=label+separationIndicator+L+counter+context;
		}
		return (String) result.subSequence(0, len*2) ;//result以16进制字符串存储
	}
	/**
	 * 计算S-MAC密钥
	 * @param KI  数据推导所需密钥,这里的值为keyMAC
	 * @param context “环境参数”，这里的值为由主机随机数与卡随机数拼成的字符串
	 * @return
	 * @throws Exception
	 */
	public static String S_MAC(String KI,String context) throws Exception{
		String key="";
		String label="";
		for(int i=0;i<11;i++){
			label=label+"00";
		}
		label=label+"06";
		key=KDF(KI, label, context,16);
		return key;
		
	}
	/**
	 * 计算S-ENC密钥
	 * @param KI  数据推导所需密钥,这里的值为keyENC
	 * @param context “环境参数”，这里的值为由主机随机数与卡随机数拼成的字符串
	 * @return
	 * @throws Exception
	 */
	public static String S_ENC(String KI,String context) throws Exception{
		String key="";
		String label="";
		for(int i=0;i<11;i++){
			label=label+"00";
		}
		label=label+"04";
		key=KDF(KI, label, context,16);
		return key;
	}
	/**
	 * 计算S-RMAC密钥
	 * @param KI  数据推导所需密钥,这里的值为keyMAC
	 * @param context “环境参数”，这里的值为由主机随机数与卡随机数拼成的字符串
	 * @param keyMAC
	 * @return
	 * @throws Exception
	 */
	public static String S_RMAC(String KI,String context) throws Exception{
		String key="";
		String label="";
		for(int i=0;i<11;i++){
			label=label+"00";
		}
		label=label+"07";
		key=KDF(KI, label, context,16);
		return key;
		
	}
	/**
	 * 计算主机密码
	 * @param KI  数据推导所需密钥,这里的值为S-MAC
	 * @param context “环境参数”，这里的值为由主机随机数与卡随机数拼成的字符串
	 * @return
	 * @throws Exception
	 */
	public static String hostCryptogram(String KI,String context) throws Exception{
		String key="";
		String label="";
		for(int i=0;i<11;i++){
			label=label+"00";
		}
		label=label+"01";
		key=KDF(KI, label, context,8);
		return key;
		
	}
	/**
	 * 计算卡随机数
	 * @param KI  数据推导所需密钥,这里的值为keyENC
	 * @param context “环境参数”，这里的值为由通道计数器与卡片当前isdPAID拼成的字符串
	 * @return
	 * @throws Exception
	 */
	public static String cardChallenge(String KI,String context) throws Exception{ //context = IccGp_sqccounter + ISDp_AID
		String key="";
		String label="";
		for(int i=0;i<11;i++){
			label=label+"00";
		}
		label=label+"02";
		key=KDF(KI, label, context,8);
		return key;	
	}
	/**
	 * 计算卡密码
	 * @param KI  数据推导所需密钥,这里的值为S-MAC
	 * @param context “环境参数”，这里的值为由主机随机数与卡随机数拼成的字符串
	 * @return
	 * @throws Exception
	 */
	public static String cardCryp(String KI,String context) throws Exception{
		String key="";
		String label="";
		for(int i=0;i<11;i++){
			label=label+"00";
		}
		label=label+"00";
		key=KDF(KI, label, context,8);
		return key;	
	}
}
