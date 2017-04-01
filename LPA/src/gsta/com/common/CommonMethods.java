package gsta.com.common;

import gsta.com.consts.AppConst;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import android.content.Context;
import android.view.ViewDebug.IntToString;

/**
 * 通用工具类,封装了很多常用方法
 * 
 */
public class CommonMethods {
	public static byte[] shortToBytes(short s) {
		byte[] shortBuf = new byte[2];
		for (int i = 0; i < 2; i++) {
			int offset = (shortBuf.length - 1 - i) * 8;
			shortBuf[i] = (byte) ((s >>> offset) & 0xff);
		}
		return shortBuf;
	}

	/**
	 * byte字节数组转换Short类型（未严格测试）
	 * 
	 * @param outBuf
	 * @return
	 */
	public static short bytesToShort(byte[] outBuf) {

		if (outBuf.length < 2) {
			return (short) (outBuf[0] < 0 ? outBuf[0] + 256 : outBuf[0]);
		} else {
			return (short) (((outBuf[0] < 0 ? outBuf[0] + 256 : outBuf[0]) << 8) + (outBuf[1] < 0 ? outBuf[1] + 256
					: outBuf[1]));
		}

	}

	/**
	 * 填充XX数据，如果结果数据块是8的倍数，不再进行追加,如果不是,追加0xXX到数据块的右边，直到数据块的长度是8的倍数。
	 * 
	 * @param data
	 *            待填充XX的数据
	 * @return
	 */
	public static String padding(String data, String inData) {
		int padlen = 8 - (data.length() / 2) % 8;
		if (padlen != 8) {
			String padstr = "";
			for (int i = 0; i < padlen; i++)
				padstr += inData;
			data += padstr;
			return data;
		} else {
			return data;
		}
	}

	/**
	 * 填充80数据，首先在数据块的右边追加一个
	 * '80',如果结果数据块是8的倍数，不再进行追加,如果不是,追加0x00到数据块的右边，直到数据块的长度是8的倍数。
	 * 
	 * @param data
	 *            待填充80的数据
	 * @return
	 */
	public static String padding80(String data) {
		int padlen = 8 - (data.length() / 2) % 8;
		String padstr = "";
		for (int i = 0; i < padlen - 1; i++)
			padstr += "00";
		data = data + "80" + padstr;
		return data;
	}

	/**
	 * 获取当前时间相隔N天的日期,格式yyyymmdd
	 * 
	 * @param distance
	 *            和今天的间隔天数
	 * @return 获取的日期,格式yyyymmdd
	 */
	public static String getDateString(int distance) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, distance);
		//
		return new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
	}

	/**
	 * 生成16位的动态链接库鉴权十六进制随机数字符串
	 * 
	 * @return String
	 */
	public static String yieldHexRand() {
		StringBuffer strBufHexRand = new StringBuffer();
		Random rand = new Random(System.currentTimeMillis());
		int index;
		// 随机数字符
		char charArrayHexNum[] = { '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'0', 'A', 'B', 'C', 'D', 'E', 'F' };
		for (int i = 0; i < 16; i++) {
			index = Math.abs(rand.nextInt()) % 16;
			if (i == 0) {
				while (charArrayHexNum[index] == '0') {
					index = Math.abs(rand.nextInt()) % 16;
				}
			}
			strBufHexRand.append(charArrayHexNum[index]);
		}
		return strBufHexRand.toString();
	}

	/**
	 * 分析类名
	 * 
	 * @param strName
	 *            String
	 * @return String
	 */
	public static String analyseClassName(String strName) {
		String strTemp = strName.substring(strName.lastIndexOf(".") + 1,
				strName.length());
		return strTemp.substring(strTemp.indexOf(" ") + 1, strTemp.length());
	}

	static public String convertInt2String(int n, int len) {
		String str = String.valueOf(n);
		int strLen = str.length();

		String zeros = "";
		for (int loop = len - strLen; loop > 0; loop--) {
			zeros += "0";
		}

		if (n >= 0) {
			return zeros + str;
		} else {
			return "-" + zeros + str.substring(1);
		}
	}

	static public int convertString2Int(String str, int defaultValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	
	static public int convertString2HexInt(String str, int defaultValue) {
		try {
			return	Integer.parseInt(str,16);
		}catch (Exception e) {
			return defaultValue;
		}
	}

	/** yyyyMMddhhmmss */
	public static String getDateTimeString2() {
		Calendar cal = Calendar.getInstance();

		return new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss")
				.format(cal.getTime());

	}

	public static String exchange(String str) throws Exception {
		if (str == null || "".equals(str) || str.length() % 2 != 0) {
			throw new Exception("the input string's format cann't do exchange!");
		}
		char[] chars = str.toCharArray();
		char[] ret = new char[chars.length];
		for (int i = 0; i < chars.length; i = i + 2) {
			ret[i] = chars[i + 1];
			ret[i + 1] = chars[i];
		}
		return new String(ret);
	}

	public static String bytesToHexString(byte[] bytes) {
		if (bytes == null) {
			return "";
		}
		StringBuffer buff = new StringBuffer();
		int len = bytes.length;
		for (int j = 0; j < len; j++) {
			if ((bytes[j] & 0xff) < 16) {
				buff.append('0');
			}
			buff.append(Integer.toHexString(bytes[j] & 0xff));
		}
		return buff.toString();
	}

	/**
	 * usage: str2bytes("0710BE8716FB"); it will return a byte array, just like
	 * : b[0]=0x07;b[1]=0x10;...b[5]=0xfb;
	 */
	public static byte[] str2bytes(String src) {
		if (src == null || src.length() == 0 || src.length() % 2 != 0) {
			return null;
		}
		int nSrcLen = src.length();
		byte byteArrayResult[] = new byte[nSrcLen / 2];
		StringBuffer strBufTemp = new StringBuffer(src);
		String strTemp;
		int i = 0;
		while (i < strBufTemp.length() - 1) {
			strTemp = src.substring(i, i + 2);
			byteArrayResult[i / 2] = (byte) Integer.parseInt(strTemp, 16);
			i += 2;
		}
		return byteArrayResult;
	}

	public static int strcpy(byte d[], byte s[], int from, int maxlen) {
		int i;
		for (i = 0; i < maxlen; i++) {
			d[i + from] = s[i];
		}

		d[i + from] = 0;
		return i;
	}

	public static int memcpy(byte d[], byte s[], int from, int maxlen) {
		int i;
		for (i = 0; i < maxlen; i++) {
			d[i + from] = s[i];
		}
		return i;
	}

	public static void BytesCopy(byte[] dest, byte[] source, int offset1,
			int offset2, int len) {
		for (int i = 0; i < len; i++) {
			dest[offset1 + i] = source[offset2 + i];
		}
	}

	/**
	 * usage: input: n = 1000000000 ( n = 0x3B9ACA00) output: byte[0]:3b
	 * byte[1]:9a byte[2]:ca byte[3]:00 notice: the scope of input integer is [
	 * -2^32, 2^32-1] ; **In CMPP2.0,the typeof msg id is ULONG,so,need
	 * ulong2Bytes***
	 */
	public static byte[] int2Bytes(int n) {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.putInt(n);
		return bb.array();
	}

	public static byte[] long2Bytes(long l) {
		ByteBuffer bb = ByteBuffer.allocate(8);
		bb.putLong(l);
		return bb.array();
	}

	/**
	 * 将整数转为16进行数后并以指定长度返回（当实际长度大于指定长度时只返回从末位开始指定长度的值）
	 * 
	 * @param val
	 *            int 待转换整数
	 * @param len
	 *            int 指定长度
	 * @return String
	 */
	public static String Int2HexStr(int val, int len) {
		String result = Integer.toHexString(val).toUpperCase();
		int r_len = result.length();
		if (r_len > len) {
			return result.substring(r_len - len, r_len);
		}
		if (r_len == len) {
			return result;
		}
		StringBuffer strBuff = new StringBuffer(result);
		for (int i = 0; i < len - r_len; i++) {
			strBuff.insert(0, '0');
		}
		return strBuff.toString();
	}

	public static String Long2HexStr(long val, int len) {
		String result = Long.toHexString(val).toUpperCase();
		int r_len = result.length();
		if (r_len > len) {
			return result.substring(r_len - len, r_len);
		}
		if (r_len == len) {
			return result;
		}
		StringBuffer strBuff = new StringBuffer(result);
		for (int i = 0; i < len - r_len; i++) {
			strBuff.insert(0, '0');
		}
		return strBuff.toString();
	}

	public static String getResString(Context context, int stringId) {
		return context.getResources().getString(stringId);
	}

	/**
	 * 字符串转换为字节数组
	 * <p>
	 * stringToBytes("0710BE8716FB"); return: b[0]=0x07;b[1]=0x10;...b[5]=0xfb;
	 */
	public static byte[] stringToBytes(String string) {
		if (string == null || string.length() == 0 || string.length() % 2 != 0) {
			return null;
		}
		int stringLen = string.length();
		byte byteArrayResult[] = new byte[stringLen / 2];
		StringBuffer sb = new StringBuffer(string);
		String strTemp;
		int i = 0;
		while (i < sb.length() - 1) {
			strTemp = string.substring(i, i + 2);
			byteArrayResult[i / 2] = (byte) Integer.parseInt(strTemp, 16);
			i += 2;
		}
		return byteArrayResult;
	}

	/**
	 * 字节数组转为16进制
	 * 
	 * @param bytes
	 *            字节数组
	 * @return
	 */
	public static String bytesToHex(byte[] bytes) {
		if (bytes == null) {
			return "";
		}
		StringBuffer buff = new StringBuffer();
		int len = bytes.length;
		for (int j = 0; j < len; j++) {
			if ((bytes[j] & 0xff) < 16) {
				buff.append('0');
			}
			buff.append(Integer.toHexString(bytes[j] & 0xff));
		}
		return buff.toString();
	}
	
	public static String byteToHex(byte bytes) {
		
		StringBuffer buff = new StringBuffer();
		if ((bytes & 0xff) < 16) {
				buff.append('0');
		}
		buff.append(Integer.toHexString(bytes & 0xff));		
		return buff.toString();
	}

	/**
	 * 
	 * ********************************************************************<br>
	 * 方法功能：将用户圈存金额先转换为分，在把分转为16进制，再前补0组装为4字节圈存金额 如1元 为:"00000064" 参数说明：<br>
	 * 作 者：杨明<br>
	 * 开发日期：2013-9-18 上午11:53:56<br>
	 * 修改日期：<br>
	 * 修改人：<br>
	 * 修改说明：<br>
	 * ********************************************************************<br>
	 */
	/**
	 * 将长整数转为16进行数后并以指定长度返回（当实际长度大于指定长度时只返回从末位开始指定长度的值）
	 * 
	 * @param val
	 *            int 待转换长整数
	 * @param len
	 *            int 指定长度
	 * @return String
	 */
	public static String longToHex(long val, int len) {
		String result = Long.toHexString(val).toUpperCase();
		int rLen = result.length();
		if (rLen > len) {
			return result.substring(rLen - len, rLen);
		}
		if (rLen == len) {
			return result;
		}
		StringBuffer strBuff = new StringBuffer(result);
		for (int i = 0; i < len - rLen; i++) {
			strBuff.insert(0, '0');
		}
		return strBuff.toString();
	}
	
	/**
	 * 
	 * @param text
	 * 
	 * @return
	 */

	public static String SHA1Digest(byte[] b) {
		String pwd = "";
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			md.update(b);
			pwd = new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return pwd;

	}
	
	/**
	 * 
	 * 功能：将1字节长度的hex转成相应的int值<br>
	 * 作者：杨琳<br>
	 * 时间：2015-12-1 下午6:12:45<br>
	 * 部门：系统研发部<br>
	 * 项目：电信U盾<br>
	 * @param hex
	 * @return
	 */
	public static int hexToInt(String hex){
		int value = 0;
		try {
			int data = Integer.parseInt(hex, 16);
			value = data;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	
	public static int commonResConvert(String result){
		
		String Head = result.substring(0, 3);
		String Tail = result.substring(3,4);
		if( Head.equals("63c")){
			String str = "00A" + Tail;			
			int res = Integer.parseInt(str,16);
			return res;
		}
		
		switch (result){
		case AppConst.SUCCESS:
			return 0x0000;
			
		case AppConst.CHANNLE_ERROR:
			return 0x0003;
			
		case AppConst.NOAPP_ERROR:
			return 0x0005;
			
		case AppConst.INPUTLEN_ERROR:
			return 0x0006;
			
		case AppConst.MAC_ERROR:
			return 0x0007;
			
		case AppConst.INPUTENC_ERROR:
			return 0x0008;
						
		case AppConst.NOSPACE_ERROR:
			return 0x0011;
			
		case AppConst.SAFESTATE_ERROR:
			return 0x0013;
			
		case AppConst.APPLOCK_ERROR:
			return 0x0014;
			
		case AppConst.NORAND_ERROR:
			return 0x0015;
			
			
			
		default:
			return 0x00FF;
			
		}
	}



public static int guomiResConvert(String result){
	
	String Head = result.substring(0, 3);
	if(Head.equals("63c")){
		String str = "10A" + result.substring(3);			
		int res = Integer.parseInt(str,16);
		return res;
	}
	
	switch (result){
	case AppConst.SUCCESS:
		return 0x0000;
		
	case AppConst.GUOMI_NOSPACE_ERROR:
		return 0x1002;
		
	case AppConst.GUOMI_UIDLEN_ERROR:
		return 0x1003;
		
	case AppConst.GUOMI_CONTAINERID_ERROR:
		return 0x1004;
		
	case AppConst.GUOMI_UNOTEXIST_ERROR:
		return 0x1005;
		
	case AppConst.GUOMI_CONTANTNOTEXIST_ERROR:
		return 0x1006;
		
	case AppConst.GUOMI_KEYPAIRNOTEXIST_ERROR:
		return 0x1007;
		
	case AppConst.GUOMI_CERNOTEXIST_ERROR:
		return 0x1008;
		
	case AppConst.GUOMI_ASYTYPE_ERROR:
		return 0x1009;
		
	case AppConst.GUOMI_SIGEN_ERROR:
		return 0x1014;
		
	case AppConst.GUOMI_DENCRY_ERROR:
		return 0x1015;
		
	case AppConst.GUOMI_PINLOCK_ERROR:
		return 0x1016;
		
	case AppConst.GUOMI_ULOCK_ERROR:
		return 0x1017;
		
	default:
		return 0x10FF;
		
	}
}


public static int rsaResConvert(String result){
	String Head = result.substring(0, 3);
	if(Head.equals("63c")){
		String str = "20A" + result.substring(3);			
		int res = Integer.parseInt(str,16);
		return res;
	}
	
	switch (result){
	case AppConst.RSA_NOSUPPORT_ERROR:
		return 0x2001;
		
	case AppConst.RSA_NOSPACE_ERROR:
		return 0x2002;
		
	case AppConst.RSA_UIDLEN_ERROR:
		return 0x2003;
		
	case AppConst.RSA_CONTAINERID_ERROR:
		return 0x2004;
		
	case AppConst.RSAI_UNOTEXIST_ERROR:
		return 0x2005;
		
	case AppConst.RSA_CONTANTNOTEXIST_ERROR:
		return 0x2006;
		
	case AppConst.RSA_KEYPAIRNOTEXIST_ERROR:
		return 0x2007;
		
	case AppConst.RSA_CERNOTEXIST_ERROR:
		return 0x2008;
		
	case AppConst.RSA_ASYTYPE_ERROR:
		return 0x2009;
		
	case AppConst.RSA_SIGEN_ERROR:
		return 0x2014;
		
	case AppConst.RSA_DENCRY_ERROR:
		return 0x2015;
		
	case AppConst.RSA_PINLOCK_ERROR:
		return 0x2016;
		
	case AppConst.RSA_ULOCK_ERROR:
		return 0x2017;
		

		
	default:
		return 0x20FF;	
		
	}
	
}


public static int sigleResConvert(String result){
	String Head = result.substring(0, 3);
	String tail = result.substring(3);
	if(Head.equals("69a")){
		String str = "30A" + tail;		
		int res = Integer.parseInt(str,16);
		return res;
	}
	
	switch (result){
	case AppConst.SIGLE_DATALOCK_ERROR:
		return 0x3001;
		
	case AppConst.SIGLE_WRITEDATA_ERROR:
		return 0x3003;
		
	case AppConst.SIGLE_DATANOTEXSIT_ERROR:
		return 0x3004;
		
	case AppConst.SIGLE_DATAIDLEN_ERROR:
		return 0x3006;

		
	default:
		return 0x30FF;	
		
	}
	
}




}
	




