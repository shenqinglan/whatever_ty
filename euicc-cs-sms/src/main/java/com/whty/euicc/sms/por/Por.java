package com.whty.euicc.sms.por;

import java.util.ArrayList;
import java.util.List;

import com.whty.euicc.sms.commandpacket.KeyManager;
import com.whty.euicc.sms.constants.SMSConstants;
import com.whty.euicc.util.StringUtil;
import com.whty.security.aes.AesCbc;
import com.whty.security.aes.AesCmac;

/**
 * 短信上行por解密
 * @author Administrator
 *
 */
public class Por implements SMSConstants{
	private String SPI = "";
	private String KIc = "";
	private String KID = "";
	private String TAR = "";
	private String CNTR = "";

	private String PCNTR = "";

	private String RCCCDS = "";

	private String securedString = "";

	private String plainString = "";
	
	private String RHI = "";
	private String RHL = "";
	private String STATUCODE = "";
	private String porTAR = "";
	private String porCNTR = "";
	private String porPCNTR = "";
	private String PUDHL = "";
	private String PRPI = "";
	private String PRPIL = "";
	private String PRPL = "";

	private static Por instance = null;

	private int keyId = 1;

	private int keyId1 = 1;
	private int cipherAlg = -1;
	private int RCCCAlg = -1;
	private int RCCCDSLength = 0;
	private String keyCipher = "";
	
	public static List<String> smsPpResp(String cmdHeader,String data){
		List res = new ArrayList();
		Por por = Por.getInstance();
		por.setSPI(cmdHeader.substring(0, 4));
		por.setKIc(cmdHeader.substring(4, 6));
		por.setKID(cmdHeader.substring(6, 8));
		por.setTAR(cmdHeader.substring(8, 14));
		por.setCNTR(cmdHeader.substring(14, 24));
		por.setSecuredString(data);
		res.add(Por.getInstance().toString());
		return res;
	}
	
	
	private boolean checkporKid(byte kid,byte[]spi){
		boolean done = false;
	     if((((spi[1] & 0x3) == 0x2) || ((spi[1] & 0x3) == 0x1)) && ((kid & 0x3) == 0x2) ){
	    	 done = true;
	    	 }
	     return done;
	     }
	
	private boolean checkporKic(byte kic,byte[]spi){
		boolean done = false;
		if(((spi[1] & 0x4) == 0x4) && ((kic & 0x2) == 0x2)){
			done = true;
		}
		return done;
		
	}
	/*
	@Override
	public String toString(){
		String res = "";
		if ((this.SPI.equals("")) && (this.KIc.equals(""))
				&& (this.KID.equals("")) && (this.TAR.equals(""))
				&& (this.CNTR.equals("")))
			res = this.securedString;
		else {
			try {
				res = analypoR(this.SPI, this.KIc, this.KID, this.TAR, this.CNTR,
						this.securedString);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return res;
	}
	*/
	public static Por getInstance() {
		if (instance == null) {
			instance = new Por();
		}
		return instance;
	}
	
	public  String analypoR(String sendCntr,String porResponse) throws Exception{
		String error0 = "UDHL not right";
		String error1 = "RPI not right";
		String error2 = "RPIL not right";
		String error3 = "RPL not right";
		String error4 = "RHL not equals 12(mac length = 8)";
		String error5 = "RHL not equals 0A";
		String error6 = "TAR different";
		String error7 = "CNTR different";
		String error8 = "MAC different under ciphering";
		String error9 = "PCNTR different";
		String error10 = "CNTR different under nociphering";
		String error11 = "MAC different under nociphering";
		String cardmac = "";
		String porPcounter = "";
		String lastdata = "";
		String statusCode ="";
		String porMac = "";
		byte[] spiByte = StringUtil.hexToBuffer(SMSConstants.SPI_POR);
		byte kicByte = StringUtil.hexToByte(SMSConstants.KIC);
		byte kidByte = StringUtil.hexToByte(SMSConstants.KID);
		
		String keyStr = KeyManager.getKidKey(this.keyId);
		String keyStr1 = KeyManager.getKicKey(this.keyId1);
		
		
		this.PUDHL = porResponse.substring(0, 2);
		if( !"02".equals(this.PUDHL ) ){
			return error0;
		}
		
		this.PRPI = porResponse.substring(2, 4);
		if(!"71".equals(this.PRPI )){
		    return error1;
		}
		this.PRPIL = porResponse.substring(4, 6);
		if(!"00".equals(this.PRPIL )){
			return error2;
		}
			
		
		
		String responsePacket = porResponse.substring(6);
		String len = StringUtil.intToHex(((int)(responsePacket.length ()/2) - 2)).substring(4,8);
		this.PRPL = responsePacket.substring(0,4);	
		if(!this.PRPL.equals(len )){
			return error3;
		}
		
		
		//cc的rhl = 12,暂只考虑CC的情况
		
		//String porRhl
		this.RHL = responsePacket.substring(4, 6);
		analyRCCCDSAlgDownAes(spiByte, kidByte);
		if(checkporKid(kidByte, spiByte)){
		if(!"12".equals(this.RHL )){//mac and length = 8
			return error4;
		  }
		}else{
			if(!"OA".equals(this.RHL )){
				return error5;
			}
		}
		
		this.porTAR = responsePacket.substring(6, 12);
		if(!SMSConstants.TAR.equals( this.porTAR)){
			return error6;
		}
		String enData = responsePacket.substring(12);
		if(checkporKic(kicByte,spiByte)){
			
			String deData = AesCbc.aesCbc1(enData, keyStr1, ivString, 2);
			this.porCNTR = deData.substring(0, 10);//字节所占长度需要更改
			if(!this.porCNTR.equals(sendCntr)){//todo maybe need to change
				return error7;
			}
			this.porPCNTR = deData.substring(10, 12);
			statusCode = deData.substring(12, 14);
			 cardmac = deData.substring(14, 30);
			 
			 if(checkporKid(kidByte, spiByte)){
					String plainData = deData.substring(0, 14) + deData.substring(30,36);
					String needCalcData = this.PUDHL + this.PRPI + this.PRPIL + this.PRPL + this.RHL + this.porTAR + plainData;
					AesCmac aesMac = new AesCmac();
					 porMac = aesMac.calcuCmac(needCalcData, keyStr); 
					if(!porMac .equals(cardmac)){
						return error8;//mac值不一致
					}		
				}
			 
			    String additionalResppad = deData.substring(30);
				int padWithLen = additionalResppad.length()/2;
				int pcntr = StringUtil.hexToByte(this.porPCNTR);
				int respdataLen = padWithLen - pcntr;
				String additionalRespData = additionalResppad.substring(0, respdataLen * 2);
				
					String padding = additionalResppad.substring(respdataLen * 2);
					int padLen = padding.length()/2;
					if(padLen != pcntr){
						return error9;
					}
					
					lastdata = this.porTAR + this.porCNTR + statusCode + additionalRespData;
					return lastdata;//返回解密后的数据
				 
				
		     }else {//不加密，计算MAC
			
		    this.porCNTR = enData.substring(0, 10);//字节所占长度需要更改
			if(!this.porCNTR.equals(sendCntr)){//todo maybe need to change
				return error10;
			}
			 porPcounter = enData.substring(10, 12);
			
			
			 statusCode = enData.substring(12, 14);
			 cardmac = enData.substring(14, 30);
			 if(checkporKid(kidByte, spiByte)){
					String plainData = enData.substring(0, 14) + enData.substring(30);
					String needCalcData = this.PUDHL + this.PRPI + this.PRPIL + this.PRPL + this.RHL + this.porTAR + plainData;
					AesCmac aesMac = new AesCmac();
					 porMac = aesMac.calcuCmac(needCalcData, keyStr); 
					if(!porMac.equals(cardmac)){
						return error11;//mac值不一致
					}		
				}
			 lastdata = this.porTAR + this.porCNTR + statusCode + enData.substring(30);
			return lastdata;
		}
		
	}
	private void analyRCCCDSAlgDownAes(byte[] spi, byte kid) {
		switch (spi[0] & 0x3) {
		case 0:
			this.RCCCAlg = 0;
			this.RCCCDSLength = 0;
			break;
		case 1:
			//if (((kid & 0x3) == 1) || ((kid & 0x3) == 0)) {
			if((kid & 0x3 )== 2){
				switch (kid & 0xC) {
				case 0:
					this.RCCCAlg = 1;
					this.RCCCDSLength = 2;
					break;
				case 4:
					this.RCCCAlg = 2;
					this.RCCCDSLength = 4;
				case 1:
				case 2:
				case 3:
				}
			}
			break;
		case 2:
			//if (((kid & 0x3) == 1) || ((kid & 0x3) == 0)) {
				if((kid & 0x3 )== 2){
				switch (kid & 0xC) {
				case 0:
					this.RCCCAlg = 3;
					this.RCCCDSLength = 8;
					break;
				case 4:
					this.RCCCAlg = 4;
					this.RCCCDSLength = 8;
					break;
				case 8:
					this.RCCCAlg = 5;
					this.RCCCDSLength = 8;
				}

			}

			break;
		case 3:
		}
	}
	
	public String getPUDHL() {
		return this.PUDHL;
	}
	public void setPUDHL(String pUDHL) {
		this.PUDHL = pUDHL;
	}
	
	public String getPRPI() {
		return this.PRPI;
	}
	public void setPRPI(String pRPI) {
		this.PRPI = pRPI;
	}
	public String getPRPIL() {
		return this.PRPIL;
	}
	public void setPRPIL(String pRPIL) {
		this.PRPIL = pRPIL;
	}
	
	public String getPRPL() {
		return this.PRPL;
	}
	public void setPRPL(String pRPL) {
		this.PRPL = pRPL;
	}
	public String getRHI() {
		return this.RHI;
	}
	public void setRHI(String rHI) {
		this.RHI = rHI;
	}
	
	public String getRHL() {
		return this.RHL;
	}
	public void setRHL(String rHL) {
		this.RHL = rHL;
	}
	public String getporTAR() {
		return this.porTAR;
	}

	public void setporTAR(String pTAR) {
		this.porTAR = pTAR;
	}

	public String getporCNTR() {
		return this.porCNTR;
	}

	public void setpCNTR(String pCNTR) {
		this.porCNTR = pCNTR;
	}

	public String getpPCNTR() {
		return this.porPCNTR;
	}

	public void setpPCNTR(String porPCNTR) {
		this.porPCNTR = porPCNTR;
	}
	
	public String getSTATUCODE() {
		return this.STATUCODE;
	}

	public void setSTATUCODE(String sTATUCODE) {
		this.STATUCODE = sTATUCODE;
	}
	
	public String getSPI() {
		return this.SPI;
	}

	public void setSPI(String sPI) {
		this.SPI = sPI;
	}

	public String getKIc() {
		return this.KIc;
	}

	public void setKIc(String kIc) {
		this.KIc = kIc;
	}

	public String getKID() {
		return this.KID;
	}

	public void setKID(String kID) {
		this.KID = kID;
	}

	public String getTAR() {
		return this.TAR;
	}

	public void setTAR(String tAR) {
		this.TAR = tAR;
	}

	public String getCNTR() {
		return this.CNTR;
	}

	public void setCNTR(String cNTR) {
		this.CNTR = cNTR;
	}

	public String getPCNTR() {
		return this.PCNTR;
	}

	public void setPCNTR(String pCNTR) {
		this.PCNTR = pCNTR;
	}

	public String getRCCCDS() {
		return this.RCCCDS;
	}

	public void setRCCCDS(String rCCCDS) {
		this.RCCCDS = rCCCDS;
	}

	public String getSecuredString() {
		return this.securedString;
	}

	public void setSecuredString(String securedString) {
		this.securedString = securedString;
	}

	public String getPlainString() {
		return this.plainString;
	}

	public void setPlainString(String plainString) {
		this.plainString = plainString;
	}

	public int getCipherAlg() {
		return this.cipherAlg;
	}

	public int getRCCCAlg() {
		return this.RCCCAlg;
	}

	public int getRCCCDSLength() {
		return this.RCCCDSLength;
	}

	public String getKeyCipher() {
		return this.keyCipher;
	}

}

	


