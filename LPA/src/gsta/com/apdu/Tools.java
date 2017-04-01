package gsta.com.apdu;


public class Tools {
	
	/**
	 * 将10进制的数字转化成16进制的数字
	 * @param num
	 * @return
	 */
	public static String toHex(String num) {
		String hex = Integer.toHexString(Integer.valueOf(num));
		if (hex.length() % 2 != 0) {
			hex = "0" + hex;
		}
		return hex.toUpperCase();
	}
	
	/**
	 * 求数据前加上tag、length之后的总长度，比如加两个字节A0 7F或三个字节A0 81 80或四个字节A0 82 02 A0 
	 * @return
	 */
	public static String add8182(int len, int returnFlag) {
		String slen8182 = "";
		if (len < 128) {
			slen8182 = itoa(len, 1);
			len += 2;
		} else if (len < 256) {
			slen8182 = "81" + itoa(len, 1);
			len += 3;
		} else if (len < 65536) {
			slen8182 = "82" + itoa(len, 2);
			len += 4;
		}
		
		if (returnFlag == 0) {
			return String.valueOf(len);
		} else {
			return slen8182;
		}
	}
	
	public static String itoa(int value, int len) {
		String result = Integer.toHexString(value).toUpperCase(); // EEEEEEEEE
        int rLen = result.length();
        len = 2 * len;
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
}
