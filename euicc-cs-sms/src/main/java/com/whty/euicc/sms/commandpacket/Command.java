package com.whty.euicc.sms.commandpacket;


import com.whty.euicc.cipher.CRC;
import com.whty.euicc.cipher.DES;
import com.whty.euicc.cipher.DESede;

import com.whty.euicc.data.cache.Scp80Cache;

import com.whty.euicc.sms.constants.SMSConstants;
import com.whty.euicc.util.StringUtil;
import com.whty.security.aes.AesCbc;
import com.whty.security.aes.AesCmac;

/**
 * 拼装command命令
 * @author Administrator
 *
 */

public class Command implements SMSConstants {
	private String CHI = "";
	private String CHL = "";
	private String SPI = "";
	private String KIc = "";
	private String KID = "";
	private String TAR = "";
	private String CNTR = "";

	private String PCNTR = "";

	private String RCCCDS = "";

	private String securedString = "";

	private String plainString = "";
	

	private static Command instance = null;

	private int keyId = 1;
	private int cipherAlg = -1;
	private int RCCCAlg = -1;
	private int RCCCDSLength = 0;
	private String keyCipher = "";

	public String toString(boolean isFormate) {
		String res = "";
		if ((this.SPI.equals("")) && (this.KIc.equals(""))
				&& (this.KID.equals("")) && (this.TAR.equals(""))
				&& (this.CNTR.equals(""))) 
			res = this.plainString;
		else {
			try {
				res = orgCmdUpForAes(isFormate,this.SPI, this.KIc, this.KID, this.TAR, this.CNTR,
						this.plainString);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return res;
		
	}

	public void toObject() {
		
		
	}

	public void clear() {
		instance = null;
	}

	public static Command getInstance() {
		if (instance == null) {
			instance = new Command();
		}
		return instance;
	}

	public String orgCmdUpForDes(boolean isFormate,String spi, String kic, String kid, String tar,
			String cntr, String plainData) throws Exception{
		byte[] spiByte = StringUtil.hexToBuffer(spi);
		byte kicByte = StringUtil.hexToByte(kic);
		byte kidByte = StringUtil.hexToByte(kid);
		
		String lastcommand = "";

		analyRCCCDSAlgDown(spiByte, kidByte);

		analyCipherAlgDown(spiByte, kicByte);

		int pcntr = StringUtil.hexToByte(this.PCNTR);
		

		String paddedStr = plainData
				+ "0000000000000000".substring(0, pcntr * 2);

		this.CHL = StringUtil.byteToHex((byte) (13 + this.RCCCDSLength));

		String CPL = StringUtil
				.shortToHex((short) (14 + this.RCCCDSLength + paddedStr
						.length() / 2));
		if (!checkKicKidVN(kicByte, kidByte)) {
			String needMacStr = CPL + this.CHL + spi + kic + kid + tar + cntr
					+ this.PCNTR + paddedStr;
     		this.RCCCDS = calcRCCCDS(this.RCCCAlg, needMacStr);
			
			if (this.RCCCDS.length() % 2 != 0) {
				this.RCCCDS = ("0" + this.RCCCDS);
			}

			String needEnCryStr = cntr + this.PCNTR + this.RCCCDS + paddedStr;
			this.securedString = enCrypt(this.cipherAlg, needEnCryStr);

			 
			lastcommand = this.CHI + this.CHL + this.SPI + this.KIc + this.KID
					+ this.TAR + this.securedString;
		}else{
			lastcommand = this.CHI + this.CHL + this.SPI + this.KIc + this.KID + this.TAR
					+ this.plainString;
		}
		
		return isFormate ? CPL + lastcommand : lastcommand;
	}
	
	
	public String orgCmdUpForAes(boolean isFormate,String spi, String kic, String kid, String tar,
			String cntr, String plainData) throws Exception{
		byte[] spiByte = StringUtil.hexToBuffer(spi);
		byte kicByte = StringUtil.hexToByte(kic);
		byte kidByte = StringUtil.hexToByte(kid);
		
		String lastcommand = "";		

		analyRCCCDSAlgDownAes(spiByte, kidByte);

		analyCipherAlgDown(spiByte, kicByte);
		
		int pcntr = StringUtil.hexToByte(this.PCNTR);
	
		String paddedStr = plainData
				+ "0000000000000000".substring(0, pcntr * 2);

		this.CHL = StringUtil.byteToHex((byte) (13 + this.RCCCDSLength));

		String CPL = StringUtil
				.shortToHex((short) (14 + this.RCCCDSLength + paddedStr
						.length() / 2));
		
		if(checkKid(kidByte,spiByte)){
			String keyStrForKid = getKidString(kidByte, spiByte);//从数据库取密钥
			String needMacStr = CPL + this.CHL + spi + kic + kid + tar + cntr
					+ this.PCNTR + paddedStr;
			AesCmac aesMac = new AesCmac();
			this.RCCCDS = aesMac.calcuCmac(needMacStr, keyStrForKid) ;
			if (this.RCCCDS.length() % 2 != 0) {
				this.RCCCDS = ("0" + this.RCCCDS);
			}
		}
		if(checkKic(kicByte, spiByte)){
			String keyStrForKic = getKicString(kicByte,spiByte);//从数据库取密钥
			String needEnCryStr = cntr + this.PCNTR + this.RCCCDS + paddedStr;
			this.securedString =  AesCbc.aesCbc1(needEnCryStr, keyStrForKic, ivString, 0);
			lastcommand = this.CHI + this.CHL + this.SPI + this.KIc + this.KID
					+ this.TAR + this.securedString;
		}else{
			lastcommand = this.CHI + this.CHL + this.SPI + this.KIc + this.KID + this.TAR + cntr + this.PCNTR + this.RCCCDS 
					+ this.plainString;
		}
		
		return isFormate ? CPL + lastcommand : lastcommand;
	}
	
	public  String notifiProcess(String respData) throws Exception{
		String error = "1010";
		String spi = "";
		String kic = "";
		String kid = "";
		String mac = "";
		String pcntr = "";
		spi = respData.substring(12, 16);
		kic = respData.substring(16, 18);
		kid = respData.substring(18, 20);
		
		byte[]spiByte = StringUtil.hexToBuffer(spi);
		byte kicByte = StringUtil.hexToByte(kic);
		byte kidByte = StringUtil.hexToByte(kid);
		
		
		if((spiByte[0] & 0x6) == 6){  //cipher & mac
			
			String keyStrForKic = getKicString(kicByte,spiByte);
			String keyStrForKid = getKidString(kidByte, spiByte);
			String encryped = respData.substring(26);
			String decryped = AesCbc.aesCbc1(encryped, keyStrForKic,ivString ,1);
			pcntr = decryped.substring(10, 12);
			int p = StringUtil.hexToByte(pcntr);
			
			
			mac = decryped.substring(12, 28);
			String needMac = respData.substring(6, 26) + decryped.substring(0,12) + decryped.substring(28);
			AesCmac aesMac = new AesCmac();
			String resultMac = aesMac.calcuCmac(needMac, keyStrForKid) ;
			if(!resultMac.equals(mac)){
				return error;
			}else {
				 
				int sd = decryped.substring(28).length() - (p * 2);
				String res = decryped .substring(28, 28 + sd);
				return res;
			}
			
			
		}else if((spiByte[0] & 0x6) == 2){  //only  mac
			mac = respData.substring(38, 54);
			String keyStrForKid = getKidString(kidByte, spiByte);
			String asString = respData.substring(6, 38) ;
			String ad = respData.substring(54) ;
			String needMac = asString +ad;
			AesCmac aesMac = new AesCmac();
			String resultMac = aesMac.calcuCmac(needMac, keyStrForKid) ;
			if(!resultMac.equals(mac)){
				return error;
			}else return respData.substring(54);
		
		}else{    //no cipher and mac
			return respData.substring(36);
			
		}
	} 
	
	/**
	 * 检测kic，kid的值，明确数据是否需要加密和计算mac
	 * @param kicByte
	 * @param kidByte
	 * @return
	 */
	private boolean checkKicKidVN(byte kicByte, byte kidByte) {
		boolean isNoDeal = false;
		int kicVn = (kicByte & 0xF0) >> 4;//判断选择哪个id密钥决定keyid,选择序列第几个密钥。
		int kidVn = (kidByte & 0xF0) >> 4;
		if ((kicVn != 0) && (kidVn != 0) && (kicVn != kidVn))
			isNoDeal = true;
		else if ((kicVn != 0) && (kidVn == 0))
			this.keyId = kicVn;
		else if ((kicVn == 0) && (kidVn != 0))
			this.keyId = kidVn;
		else if ((kicVn == 0) && (kidVn == 0))
			this.keyId = 1;
		else {
			this.keyId = kicVn;
		}
		return isNoDeal;
	}
	private boolean checkKid(byte kid,byte[]spi){
		boolean done = false;
	     if(((spi[0] & 0x3) == 2) && ((kid & 0x3) == 2)){
	    	 done = true;
	     }
	     return done;
	}
	
	private String getKidString(byte kid,byte[]spi){
	     if(((spi[0] & 0x3) == 2) && ((kid & 0x3) == 2)){
	    	 int keyId;
	    	 int kidVn = (kid & 0xF0) >> 4;
	    	 if(kidVn == 0){
					keyId = 1;
				}else {
					keyId = kidVn;
				}
	    	 String  scp80Id = StringUtil.toHex(keyId);	
	    	 String keyString = "02"+"_"+scp80Id;//“02”表示kid,scp80Id表示密钥序号
			 return Scp80Cache.getScp80(keyString);
	    }
	    return null;
	}
	private boolean checkKic(byte kic,byte[]spi){
		boolean done = false;
		if(((spi[0] & 0x4) == 4) && ((kic & 0x1) == 0)){
			done = true;
		}
		return done;
			
	}
	
	private String getKicString(byte kic,byte[] spi){
		if(((spi[0] & 0x4) == 4) && ((kic & 0x1) == 0)){
			int keyId;
			int kicVn = (kic & 0xF0) >> 4;
			if(kicVn == 0){
				keyId = 1;
			}else {
				keyId = kicVn;
			}
			String  scp80Id = StringUtil.toHex(keyId);
	    	String keyString = "01"+"_"+scp80Id;//“01”表示kic,scp80Id表示密钥序号
			return Scp80Cache.getScp80(keyString);
		}
		return null;
	}

	public void anyMacCipherTag() {
		byte[] spiByte = StringUtil.hexToBuffer(this.SPI);
		byte kicByte = StringUtil.hexToByte(this.KIc);
		byte kidByte = StringUtil.hexToByte(this.KID);
		analyRCCCDSAlgUp(spiByte, kidByte);
		analyCipherAlgUp(spiByte, kicByte);
	}

	/**
	 *判断mac的长度
	 * @param spi
	 * @param kid
	 */
	private void analyRCCCDSAlgDown(byte[] spi, byte kid) {
		switch (spi[0] & 0x3) {
		case 0:
			this.RCCCAlg = 0;
			this.RCCCDSLength = 0;
			break;
		case 1:
			if (((kid & 0x3) == 1) || ((kid & 0x3) == 0)) {
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
			if (((kid & 0x3) == 1) || ((kid & 0x3) == 0)) {
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

	private void analyRCCCDSAlgUp(byte[] spi, byte kid) {
		switch (spi[1] & 0xC) {
		case 0:
			this.RCCCAlg = 0;
			this.RCCCDSLength = 0;
			break;
		case 4:
			if (((kid & 0x3) == 1) || ((kid & 0x3) == 0)) {
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
		case 8:
			if (((kid & 0x3) == 1) || ((kid & 0x3) == 0)) {
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
		case 11:
		}
	}

	
	private void analyCipherAlgDown(byte[] spi, byte kic) {
		int pcntr = 8 - (6 + this.RCCCDSLength + this.plainString.length() / 2) % 8;
		if ((6 + this.RCCCDSLength + this.plainString.length() / 2) % 8 == 0)
			pcntr = 0;
		
		this.PCNTR = "00";

		if ((spi[0] & 0x4) == 4) {
			if (((kic & 0x1) == 1) || ((kic & 0x1) == 0))
				switch (kic & 0xC) {
				case 0:
					this.cipherAlg = 1;
					this.PCNTR = StringUtil.byteToHex((byte) pcntr);
					break;
				case 4:
					this.cipherAlg = 2;
					this.PCNTR = StringUtil.byteToHex((byte) pcntr);
					break;
				case 8:
					this.cipherAlg = 3;
					this.PCNTR = StringUtil.byteToHex((byte) pcntr);
					break;
				case 12:
					this.cipherAlg = 4;
					this.PCNTR = StringUtil.byteToHex((byte) pcntr);
					break;
				default:
					break;
				}
		} else
			this.cipherAlg = -1;
	}

	private void analyCipherAlgUp(byte[] spi, byte kic) {
		int pcntr = 8 - (6 + this.RCCCDSLength + this.plainString.length() / 2) % 8;
		if ((6 + this.RCCCDSLength + this.plainString.length() / 2) % 8 == 0)
			pcntr = 0;
		this.PCNTR = "00";

		if ((spi[1] & 0x10) == 16) {
			if (((kic & 0x1) == 1) || ((kic & 0x1) == 0))
				switch (kic & 0xC) {
				case 0:
					this.cipherAlg = 1;
					this.PCNTR = StringUtil.byteToHex((byte) pcntr);
					break;
				case 4:
					this.cipherAlg = 2;
					this.PCNTR = StringUtil.byteToHex((byte) pcntr);
					break;
				case 8:
					this.cipherAlg = 3;
					this.PCNTR = StringUtil.byteToHex((byte) pcntr);
					break;
				case 12:
					this.cipherAlg = 4;
					this.PCNTR = StringUtil.byteToHex((byte) pcntr);
					break;
				default:
					break;
				}
		} else
			this.cipherAlg = -1;
	}

	public String calcRCCCDS(int RCCCAlg, String hexData) {
		String res = "";

		//String keyStr = KeyManager.getKidKey(this.keyId);
		String keyStr = "86B3308FFAEE177BF925130D89DE2611";
		byte[] keyByte = StringUtil.hexToBuffer(keyStr);
		byte[] tmp = null;
		byte[] iVByte = StringUtil.hexToBuffer("0000000000000000");

		switch (RCCCAlg) {
		case 0:
			break;
		case 1:
			res = CRC.CRC16(StringUtil.hexToBuffer(hexData));
			break;
		case 2:
			res = CRC.CRC32(StringUtil.hexToBuffer(hexData));
			break;
		case 3:
			if (hexData.length() / 2 % 8 != 0) {
				int padlen2 = 8 - hexData.length() / 2 % 8;
				hexData = hexData
						+ "0000000000000000".substring(0, padlen2 * 2);
			}
			byte[] data1 = StringUtil.hexToBuffer(hexData);
			DES.setCipherAlgorithm("DES/CBC/NoPadding");
			try {
				tmp = DES.encrypt(data1, keyByte, iVByte);
			} catch (Exception e) {
				e.printStackTrace();
			}
			res = StringUtil.bufferToHex(tmp);
			break;
		case 4:
		case 5:
			if (hexData.length() / 2 % 8 != 0) {
				int padlen2 = 8 - hexData.length() / 2 % 8;
				hexData = hexData
						+ "0000000000000000".substring(0, padlen2 * 2);
			}
			byte[] data2 = StringUtil.hexToBuffer(hexData);
			DESede.setCipherAlgorithm("DESede/CBC/NoPadding");
			try {
				tmp = DESede.encrypt(data2, keyByte, iVByte);
			} catch (Exception e) {
				e.printStackTrace();
			}
			res = StringUtil.bufferToHex(tmp);
			break;
		}

		if (res.length() > 16) {
			res = res.substring(res.length() - 16, res.length());
		}
		return res.toUpperCase();
	}

	private String enCrypt(int cipherAlg, String hexData) {
		String res = hexData;
		byte[] data = StringUtil.hexToBuffer(hexData);

		String keyStr = KeyManager.getKicKey(this.keyId);
		this.keyCipher = keyStr;
		byte[] keyByte = StringUtil.hexToBuffer(keyStr);
		byte[] tmp = null;
		byte[] iVByte = StringUtil.hexToBuffer("0000000000000000");

		switch (cipherAlg) {
		case 1:
			try {
				DES.setCipherAlgorithm("DES/CBC/NoPadding");
				tmp = DES.encrypt(data, keyByte, iVByte);
				res = StringUtil.bufferToHex(tmp);
			} catch (Exception e) {
				e.printStackTrace();
			}

		case 2:
		case 3:
			DESede.setCipherAlgorithm("DESede/CBC/NoPadding");
			try {
				tmp = DESede.encrypt(data, keyByte, iVByte);
			} catch (Exception e) {
				e.printStackTrace();
			}
			res = StringUtil.bufferToHex(tmp);
			break;
		case 4:
			try {
				DES.setCipherAlgorithm("DES/ECB/NoPadding");
				tmp = DES.encrypt(data, keyByte);
				res = StringUtil.bufferToHex(tmp);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return res;
	}

	public String deCrypt(int cipherAlg, byte kic, String hexData) {
		String res = hexData;
		byte[] data = StringUtil.hexToBuffer(hexData);

		String keyStr = KeyManager.getKicKey((kic & 0xF0) >> 4);
		this.keyCipher = keyStr;
		byte[] keyByte = StringUtil.hexToBuffer(keyStr);
		byte[] tmp = null;
		byte[] iVByte = StringUtil.hexToBuffer("0000000000000000");

		switch (cipherAlg) {
		case 1:
			try {
				DES.setCipherAlgorithm("DES/CBC/NoPadding");
				tmp = DES.decrypt(data, keyByte, iVByte);
				res = StringUtil.bufferToHex(tmp);
			} catch (Exception e) {
				e.printStackTrace();
			}

		case 2:
		case 3:
			DESede.setCipherAlgorithm("DESede/CBC/NoPadding");
			try {
				tmp = DESede.decrypt(data, keyByte, iVByte);
			} catch (Exception e) {
				e.printStackTrace();
			}
			res = StringUtil.bufferToHex(tmp);
			break;
		case 4:
			try {
				DES.setCipherAlgorithm("DES/ECB/NoPadding");
				tmp = DES.decrypt(data, keyByte);
				res = StringUtil.bufferToHex(tmp);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return res;
	}


	public String getCHI() {
		return this.CHI;
	}

	public void setCHI(String cHI) {
		this.CHI = cHI;
	}

	public String getCHL() {
		return this.CHL;
	}

	public void setCHL(String cHL) {
		this.CHL = cHL;
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

	public String getCmdHeader() {
		return this.CHI + this.CHL + this.SPI + this.KIc + this.KID + this.TAR
				+ this.securedString.substring(0, (6 + this.RCCCDSLength) * 2);
	}
}