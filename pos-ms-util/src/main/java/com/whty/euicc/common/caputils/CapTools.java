// Copyright (C) 2012 WHTY
package com.whty.euicc.common.caputils;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.whty.euicc.common.utils.CheckEmpty;
import com.whty.euicc.common.utils.Converts;

public class CapTools {

	private static final Logger logger = LoggerFactory
			.getLogger(CapTools.class);

	public LvData packageAID;

	public ArrayList<LvData> appletAID = new ArrayList<LvData>();

	public String packVersion = "";

	public String appletVersion = "";

	public String capData = "";

	public ArrayList<TLVData> componentList = new ArrayList<TLVData>();

	public ArrayList<String> installBlocksList = new ArrayList<String>();

	public int preDateLength = 128;

	/**
	 * 将cap文件数据根据命令长度分包，用;分隔
	 *
	 * @param capDate
	 * @param commandlength
	 * @return
	 */
	public static String getPackageCapData(String capDate, int commandlength) {

		String strRes = "";
		// String lengthTLV = getLengthTLV(capDate.length());
		// strRes = "C4" + lengthTLV + capDate;
		strRes = capDate;
		logger.debug("strRes={}", strRes);
		return strRes;

	}

	/***
	 * 读取CAP文件
	 *
	 * @param myFile
	 * @return
	 */
	public boolean readCapFile2(MultipartFile myFile) {
		byte[] fileByte = null;
		String fileString = null;
		try {
			fileByte = myFile.getBytes();
			fileString = Converts.bytesToString(fileByte);
			String headTag = fileString.substring(0, 2);
			if ("01,02,03,04,05,06,07,08,09,0A,0B,0C".contains(headTag)) {
				this.readCapFile21(fileString);
			} else {
				this.readCapFile23(fileString);
			}
		} catch (Exception e) {
			logger.error("IOException: ", e);
		}
		return true;
	}

	/**
	 * 年代久远的方法，无从改起，直接调用。参考文档：<br>
	 * JavaCard222VMspec.pdf<br>
	 * Java卡虚拟机规范V2.2.1(中文版).doc<br>
	 * Java卡虚拟机规范的FAQ文档.doc<br>
	 * add by chenxin 2012-02-23 17:39
	 *
	 * @param myFile
	 * @return
	 */
	public boolean readCapFile23(String fileString) {
		appletAID.clear();
		componentList.clear();
		// byte[] fileByte = null;
		// String fileString = null;
		String[] sectionStr = null;
		long tmpSize = 0;
		TLVData tmpTLV = null;
		try {
			// fileByte =
			// IOUtils.toByteArray(FileUtils.openInputStream(myFile));
			// fileByte = myFile.getBytes();
			// fileString = Converts.bytesToString(fileByte);
			sectionStr = fileString.split("2E636170", -1);
			for (String section : sectionStr) {
				if (section == null) {
					continue;
				}
				if (section.startsWith("FECA0000")) {
					section = section.substring(8);
				}
				if (Integer.parseInt(section.substring(0, 2), 16) > 12) {
					continue;
				}
				if (section.contains("504B0304")) {
					section = section.substring(0, section.indexOf("504B0304"));
				}
				tmpSize = (Integer.parseInt(section.substring(2, 4), 16)) * 256
						+ (Integer.parseInt(section.substring(4, 6), 16)) + 3;
				tmpTLV = TLVData.setTLV(
						section.substring(0, (int) tmpSize * 2), 2);
				componentList.add(tmpTLV);

				// 取得package的值
				if (tmpTLV.tag == 0x01) {
					LvData tmpLVdata = LvData.setLV(tmpTLV.lv.vdata
							.substring(18));
					packageAID = tmpLVdata;
					packVersion = String.valueOf(Integer.parseInt(
							tmpTLV.lv.vdata.substring(16, 18), 16))
							+ "."
							+ String.valueOf(Integer.parseInt(
									tmpTLV.lv.vdata.substring(14, 16), 16));
					appletVersion = String.valueOf(Integer.parseInt(
							tmpTLV.lv.vdata.substring(10, 12), 16))
							+ "."
							+ String.valueOf(Integer.parseInt(
									tmpTLV.lv.vdata.substring(8, 10), 16));
				}

				// 取得applet和instance的值
				if (tmpTLV.tag == 0x03) {
					for (int i = 0; i < Integer.parseInt(
							tmpTLV.lv.vdata.substring(0, 2), 16); i++) {
						LvData tmpLVdatas = LvData.setLV(tmpTLV.lv.vdata
								.substring(2));
						appletAID.add(tmpLVdatas);
					}
				}
			}
		} catch (Exception e) {
			logger.error("IOException: ", e);
		}
		return true;
	}

	/***
	 * 读取CAP文件
	 *
	 * @param myFile
	 * @return
	 */
	public boolean readCapFile21(String fileString) {
		/**
		 * tag COMPONENT_Header 1 COMPONENT_Directory 2 COMPONENT_Applet 3
		 * COMPONENT_Import 4 COMPONENT_ConstantPool 5 COMPONENT_Class 6
		 * COMPONENT_Method 7 COMPONENT_StaticField 8
		 * COMPONENT_ReferenceLocation 9 COMPONENT_Export 10
		 * COMPONENT_Descriptor 11 COMPONENT_Debug 12
		 */
		appletAID.clear();
		componentList.clear();
		long tmpSize = 0;
		TLVData tmpTLV = null;
		String fileStrings = fileString;
		try {
			// fileByte = myFile.getBytes();
			// fileString = Converts.bytesToString(fileByte);

			// fileByte = myFile.getBytes();
			// fileString = Converts.bytesToString(fileByte);
			// while (fileStrings != null && !"".equals(fileStrings)) {
			while (CheckEmpty.isNotEmpty(fileStrings)) {
				if (fileStrings.length() < 6) {
					logger.error("CAP文件格式有问题！");
					throw new Exception();
				}
				tmpSize = (Integer.parseInt(fileStrings.substring(2, 4), 16))
						* 256
						+ (Integer.parseInt(fileStrings.substring(4, 6), 16))
						+ 3;
				tmpTLV = TLVData.setTLV(
						fileStrings.substring(0, (int) tmpSize * 2), 2);
				fileStrings = fileStrings.substring((int) tmpSize * 2);
				if (tmpTLV.tag != 0x11 && tmpTLV.tag != 0x12) {
					componentList.add(tmpTLV);
				}
				// 取得package的值
				if (tmpTLV.tag == 0x01) {
					LvData tmpLVdata = LvData.setLV(tmpTLV.lv.vdata
							.substring(18));
					packageAID = tmpLVdata;
					packVersion = String.valueOf(Integer.parseInt(
							tmpTLV.lv.vdata.substring(16, 18), 16))
							+ "."
							+ String.valueOf(Integer.parseInt(
									tmpTLV.lv.vdata.substring(14, 16), 16));
					appletVersion = String.valueOf(Integer.parseInt(
							tmpTLV.lv.vdata.substring(10, 12), 16))
							+ "."
							+ String.valueOf(Integer.parseInt(
									tmpTLV.lv.vdata.substring(8, 10), 16));
				}

				// 取得applet和instance的值
				if (tmpTLV.tag == 0x03) {
					for (int i = 0; i < Integer.parseInt(
							tmpTLV.lv.vdata.substring(0, 2), 16); i++) {
						LvData tmpLVdatas = LvData.setLV(tmpTLV.lv.vdata
								.substring(2));
						appletAID.add(tmpLVdatas);
					}
				}
			}

		} catch (Exception e) {
			logger.error("IOException: ", e);
		}
		return true;
	}

	/**
	 * 获取整个.cap文件的数据部分
	 *
	 * @return
	 */
	public boolean fetchData() {
		String componentData[] = new String[10];
		try {
			for (TLVData component : componentList) {
				if ((component.tag <= 10) && (component.tag >= 1)) {
					componentData[component.tag - 1] = component.tLVdata;
				}
			}
			for (int i = 0; i <= 9; i++) {
				if (componentData[i] == null) {
					componentData[i] = "";
				}
			}
			capData = componentData[0] + componentData[1] + componentData[3]
					+ componentData[2] + componentData[5] + componentData[6]
					+ componentData[7] + componentData[9] + componentData[4]
					+ componentData[8];
			return true;
		} catch (Exception e) {
			logger.error("cap文件分解数据失败,IOException: ", e);
			return false;
		}

	}

	/**
	 * 将byte数组转换成16进制组成的字符串 例如 一个byte数组 b[0]=0x07;b[1]=0x10;...b[5]=0xFB;
	 * byte2hex(b); 将返回一个字符串"0710BE8716FB"
	 *
	 * @param b
	 *            待转换的byte数组
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";

		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	public static String getLengthTLV(int n) {
		int f = n / 2;
		String hex = "";
		if (f < 128) {
			hex = Converts.intToHex(f, 2);
		} else if (f >= 128 && f < 256) {
			hex = "81" + Converts.intToHex(f, 2);
		} else if (f >= 256) {
			hex = "82" + Converts.intToHex(f, 4);
		}
		return hex;
	}

	/**
	 * 将16进制组成的字符串转换成byte数组 例如 hex2Byte("0710BE8716FB"); 将返回一个byte数组
	 * b[0]=0x07;b[1]=0x10;...b[5]=0xFB;
	 *
	 * @param src
	 *            待转换的16进制字符串
	 * @return
	 */
	public static byte[] hex2Byte(String str) {
		int strLg = str.length();
		byte[] strbyte = new byte[strLg / 2];
		for (int i = 0, j = 0; i < str.length(); j++) {
			strbyte[j] = (byte) Integer.parseInt(str.substring(i, i + 2), 16);
			i = i + 2;
		}
		return strbyte;
	}

}
