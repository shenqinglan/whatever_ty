package com.whty.euicc.common.utils;

/**
 * 字节工具类
 * @author dengzm
 *
 */
public class ByteUtil {
	
	public static byte[] toHex(int i) {
		return toUnsignedByteArray(i, 4);
	}
	
	/**
	 * 把字节转成十六进制字节数组
	 * 
	 * @param i
	 * @return
	 */
	public static byte[] toHex(byte i) {
		return toUnsignedByteArray(i, 2, 4);
	}
	
	/**
	 * Convert the integer to an unsigned number.
	 */
	private static byte[] toUnsignedByteArray(int i, int shift) {
		return toUnsignedByteArray(i, 32, shift);
	}

	private static byte[] toUnsignedByteArray(int i, int capacity, int shift) {

		java.nio.ByteBuffer buf = java.nio.ByteBuffer.allocateDirect(capacity);
		int charPos = capacity;
		int radix = 1 << shift;
		int mask = radix - 1;
		do {
			buf.put(--charPos, digits[i & mask]);
			i >>>= shift;
		} while (i != 0);

		return buf.array();
	}
	
	public static byte[] bytesToHexBytes(byte[] bytes){
		
		return null;
	}
	
	public static void main(String[] args) {
		for (int i=1; i<256; i++){
			System.out.println(toHex((byte)i));
		}
	}

	final static byte[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			'x', 'y', 'z' };
	
	public static byte[] byteToHex(byte bytes) {
		//int hex = Integer.toHexString(arg0);
		//return new byte[]{};
		
		StringBuffer buff = new StringBuffer();
		int i = bytes & 0xFF;

		if (i < 16) {
			buff.append('0');
		}
		buff.append(Integer.toHexString(i));

		return buff.toString().getBytes();
	}
	
	/**
     * 计算字节校验和
     *
     * @param input
     * @return
     */
    public static byte calcLRC(byte[] input) {
        byte ret = 0;
        for (int i = 0; i < input.length; i++) {
            ret ^= input[i];
        }
        return ret;
    }

	/**
	 * 比较两个字节是否相同
	 * @param one
	 * @param two
	 * @return
	 */
	public static boolean compare(byte[] one, byte[] two){
		if (null == one && null == two){
			return true;
		} else if ( (null == one && null != two)
				|| (null == two && null != one)){
			return false;
		} else if (one.length == two.length){
			for (int i=0; i<one.length; i++){
				if (one[i] != two[i])
					return false;
			}
			return true;
		}
		return false;
	}
	/**
	 * 短信中心号码反转
	 * @param orig
	 * @return
	 */
	public static String reverseSmsCenNo(String orig) {
		char[] s = orig.toCharArray();
		int n = s.length - 1;
		int i = 0;
		while (i <= n){
			char temp = s[i];
			s[i] = s[i + 1];
			s[i + 1] = temp;
			i += 2;
		}
		return new String(s);
	}
}
