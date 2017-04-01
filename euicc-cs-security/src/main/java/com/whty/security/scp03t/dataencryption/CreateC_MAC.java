package com.whty.security.scp03t.dataencryption;

import com.whty.security.aes.AesCmac;


/**
 * 生成C-MAC类
 * @author Administrator
 *
 */
public class CreateC_MAC {
	/**
	 * 计算C-MAC
	 * C-MAC是使用S-MAC、加密后数据、上次CMAC计算后得到的MAC链使用AES CMAC加密算法共同计算出来的，
	 * 作用是用作检验数据的完整性。
	 * @param S_MAC 使用keyMAC通过KDF函数计算得到的过程密钥
	 * @param perMac  上一次对数据进行AES CMAC计算后得到的MAC链，用于下一次CMAC计算，
	 * 且该值得初始值为16个字节的‘00’
	 * @return
	 */
	public String create(String S_MAC,String data,String perMac){//MACChaining长度16bytes，MAC为其前8bytes
		
		String macData=perMac+data;	//为数据加上MAC链值
		AesCmac one;
		
		String mac = "";
		try {
			one = new AesCmac();
			mac = one.calcuCmac1(macData, S_MAC);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	//为得到链值
		
		//得到的链值即为下一次的链值
	
		
		
		return mac;
	}
	
}
