package com.whty.euicc.makepacket;

import java.util.Map;

import com.whty.euicc.util.RcccdsBean;
import com.whty.euicc.util.StringUtil;

public class SmsOrgnize {
	private int rcccdsAlg = 0;
	private int rcccdsLength = 0;  
	
	

	/**
	 * 下行短信拼装接口
	 * @param data
	 * @param mapKey
	 * @return
	 */
	public String wrapsendSms(String data,Map<String, String> mapKey) {
		commandPacketStructure();
		return null;
		
	}

	/**
	 * 上行短信解析
	 * @return
	 */
	public String parseCardResp() {
		return null;
	}
	/**
	 * 指令拼装
	 */
	private void commandPacketStructure(String spi, String kic, String kid, String tar,
			String cntr,String plainData) {
		byte[] spiByte = StringUtil.hexToBuffer(spi);
		byte kicByte = StringUtil.hexToByte(kic);
		byte kidByte = StringUtil.hexToByte(kid);
		
		String lastcommand = "";

		analyRCCCDSAlgDown(spiByte, kidByte);

		analyCipherAlgDown(spiByte, kicByte,plainData);

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
	
	/**
	 *判断mac的长度
	 * @param spi
	 * @param kid
	 */
	private static void analyRCCCDSAlgDown(byte[] spi, byte kid) {
		RcccdsBean ccBean = new RcccdsBean();
		switch (spi[0] & 0x3) {
		case 0://no rc cc ds 
			ccBean.setRcccdsAlg(0);
			ccBean.setRcccdsLength(0);
			break;
		case 1: //rc
			break;
		case 2://cc
			if ((kid & 0x3) == 1 /*|| ((kid & 0x3) == 0)*/) { //des
				switch (kid & 0xC) {
				case 0://common des
					ccBean.setRcccdsAlg(3);
					ccBean.setRcccdsLength(8);
					break;
				case 4://3des-cbc 2keys
					ccBean.setRcccdsAlg(4);
					ccBean.setRcccdsLength(8);
					break;
				case 8://3des-cbc 3keys
					ccBean.setRcccdsAlg(5);
					ccBean.setRcccdsLength(8);
				}

			}else if ((kid & 0x3) == 2) {//aes
				switch (kid & 0xC) {
				case 0://aes cbc
					ccBean.setRcccdsAlg(6);
					ccBean.setRcccdsLength(8);
					break;
				case 4:
					break;
				case 8:
				}
			}

			break;
		case 3://ds
		}
	}
	
	private void analyCipherAlgDown(byte[] spi, byte kic,String plainStr) {
		int pcntr = 8 - (6 + rcccdsLength + plainStr.length() / 2) % 8;
		if ((6 + rcccdsLength + plainStr.length() / 2) % 8 == 0)
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
}
