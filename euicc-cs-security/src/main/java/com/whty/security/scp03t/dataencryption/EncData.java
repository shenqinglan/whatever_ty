package com.whty.security.scp03t.dataencryption;

import com.whty.security.aes.AesCbc;


/**
 * 数据加密/解密类
 * scp03t流程中的数据传输步骤中的对数据进行AES CBC算法加密的整个过程
 * 其中使用了原始待加密数据，S-ENC过程密钥，ICV偏转参数（可以根据已知参数计算），
 * 如果对数据有分包的话，为了区分包的顺序，还有加密计数器参数，
 * 该参数在同一个scp03t流程中，在每次的加密数据传输前都会加1，该参数会参与计算出ICV偏转参数，来区分数据包的顺序
 * @author Administrator
 *
 */


public class EncData {
	/**
	 * 加密数据方法
	 * @param S_ENC 使用keyENC初始密钥通过KDF函数计算出来的过程密钥
	 * @param data  原始待加密数据
	 * @param encCounter  当前加密计数器
	 * @return
	 * @throws Exception
	 */
	public String encryption(String S_ENC,String data,String encCounter) {
			
		String IV="";
		for(int i=0;i<16;i++){
			IV=IV+"00";
			}
		String block="";
		for(int i=0;i<13;i++){
			block=block+"00";
		}
		block=block+encCounter;
		String ICV="";
		try {
			ICV = AesCbc.aesCbc1(block, S_ENC, IV,0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//计算ICV
		
//填充数据方法：先在数据块右边添加‘80’，再看得到的是不是16bytes的倍数，若是则不再添加；
//若不是则再右边继续补零直到最终得到的是16bytes的倍数		
		data=data+"80";
		int len=data.length();
		while(len%32!=0){
			data=data+"0";           
			len++;            
		}
		
		//System.out.println("ICV:"+ICV);
		//System.out.println("paddingData:"+data);
		try {
			data=AesCbc.aesCbc1(data,S_ENC, ICV,0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		//加密数据
		
			
	return data;
	
	}
	/**
	 * 解密数据方法
	 * @param S_ENC 使用keyENC初始密钥通过KDF函数计算出来的过程密钥
	 * @param encData  原始待解密数据
	 * @param encCounter  当前加密计数器
	 * @return
	 * @throws Exception
	 */
	public String decryption(String S_ENC,String encData,String encCounter) {
		
		String IV="";
		for(int i=0;i<16;i++){
			IV=IV+"00";
			}
		String block="";
		for(int i=0;i<13;i++){
			block=block+"00";
		}
		block=block+encCounter;
		String ICV="";
		try {
			ICV = AesCbc.aesCbc1(block, S_ENC, IV,0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//计算ICV
																	//填充数据
		String data="";
		try {
			data=AesCbc.aesCbc1(encData,S_ENC, ICV,1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		//加密数据
		while("00".equals(data.substring((data.length()-2), data.length()))){
			data=data.substring(0, (data.length()-2));
		}
		if("80".equals(data.substring((data.length()-2), data.length()))){
			data=data.substring(0, (data.length()-2));
			}	
		else{
			System.out.println("响应数据填充错误");
		}
	return data;
	
	}
	
	public static String respEncrypt(String S_ENC,String data,String encCounter) {

		String IV="";
		for(int i=0;i<16;i++){
			IV=IV+"00";
		}
		String block="";
		for(int i=0;i<13;i++){
			block=block+"00";
		}
		block=block+encCounter;
		String ICV="";
		try {
			ICV = AesCbc.aesCbc1(block, S_ENC, IV,0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//计算ICV
		data=data+"80";
		int len=data.length();
		while(len%32!=0){
			data=data+"0";           
			len++;            
		}
		try {
			data=AesCbc.aesCbc1(data,S_ENC, ICV,0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return data;
	}//加密数据

}
