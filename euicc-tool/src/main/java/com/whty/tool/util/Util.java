package com.whty.tool.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.whty.tool.bean.PositionBean;
/**
 * 工具类
 * @author Administrator
 *
 */
public class Util {
	/**
	 * list 内数据拼装为字符串
	 * @param list
	 * @return
	 */
	public static String totalStr(List<String> list) {
		String packagesString = "";
		for(String str:list){
			packagesString+=str+"";
		}
		return packagesString;

	}
	/**
	 * 获取C_7Fxx类中的变量名
	 * @param obj
	 * @return
	 */
	public static Field[] getName(Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			fields[i].getName();
		}
		return fields;
	}
	/**
	 * 属性值获取
	 * @param c
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Object getAttribute(Class c,String name) throws Exception{
		Object obj = c.newInstance();
		Field field = c.getDeclaredField(name);
		return field.get(obj);
	}
	
	/**
	 * 截取字符串，根据给定首尾子串，截取中间的子串
	 * 以字符串名称和空格后的“{”作为分割来截取
	 * 故二者之间必须有空格
	 * @param str 需要截取的串
	 * @param str1 以str1开始
	 * @param str2 以str2结束
	 * @return 截取后的字符串
	 */
	public static String getsubString(String str, String str1, String str2) {
			int num1 = str.indexOf(str1);
			int num2 = str.lastIndexOf(str2)+1;
			return str.substring(num1, num2).trim();

	}
	/**
	 * remove /t /n...
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
        String dest = "";
        if (StringUtils.isNotBlank(str)) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
	
	
	
	
	 /** 
     * 返回最后的Value的长度 
     *  
     * @param hexString 
     * @param position 
     * @return  
     */  
	public static PositionBean getLengthAndPosition(String hexString) { // 83 830101 
		int position = 0;
		String tag = getTag(hexString, 0);
		position += tag.length();
		String firstByteString = hexString.substring(position, position + 2);  //01
        int i = Integer.parseInt(firstByteString, 16);  
        String hexLength = "";  
        String hexValue = "";
  
        if (((i >>> 7) & 1) == 0) {  
            hexLength = hexString.substring(position, position + 2); 
            position = position + 2;  
        } else {  
            // 当最左侧的bit位为1的时候，取得后7bit的值，  
            int leftLen = i & 127;  
            position = position + 2;  
            hexLength = hexString.substring(position, position + leftLen * 2);  
            // position表示第一个字节，后面的表示有多少个字节来表示后面的Value值  
            position = position + leftLen * 2;  
        }  
        String tlv = tag + hexString .substring(tag.length(),position);
        return new PositionBean(hexLength, position);  
  
    }  
	
	public String partiTlv(String hexString) { //  C6 09 900140 830101 83010A
		List<String >list = new ArrayList<String>();
		int position = 0;
		String tag = getTag(hexString, 0); //c6
		position += tag.length();
		String length = hexString.substring(position, position + 2);  //0c
        int i = Integer.parseInt(length, 16);  
        String hexLength = "";  
        String hexValue = "";
  
        if (((i >>> 7) & 1) == 0) {  
            hexLength = hexString.substring(position, position + 2); 
            hexValue = hexString.substring((tag + length).length());
            list.add(tag + length + hexValue);
            return partiTlv(hexValue);
//            position = position + 2;  
        } else {  
            // 当最左侧的bit位为1的时候，取得后7bit的值，  
            int leftLen = i & 127;  
            position = position + 2;  
            hexLength = hexString.substring(position, position + leftLen * 2);  
            hexValue = hexString.substring((tag + length).length());
            list.add(tag + length + hexValue);
            partiTlv(hexValue);
            // position表示第一个字节，后面的表示有多少个字节来表示后面的Value值  
//            position = position + leftLen * 2;  
        }  
//        String tlv = tag + hexString .substring(tag.length(),position);
//        return list.toString();
        return partiTlv(hexString.substring((tag + length).length()));
  
    }  
    
    /** 
     * 取得子域Tag标签 
     *  
     * @param hexString 
     * @param position 
     * @return 
     */  
    public static String getTag(String hexString, int position) {  
        String firstByte = StringUtils.substring(hexString, position, position + 2);  
        int i = Integer.parseInt(firstByte, 16);  
        if ((i & 0x1f) == 0x1f) {  
            return hexString.substring(position, position + 4);  
  
        } else {  
            return hexString.substring(position, position + 2);  
        }  
    }  


	/**
	 * 16进制字符串10进制数
	 * @param s
	 * @return
	 */
	public static int atoi(String s) {
		return Integer.valueOf(s, 16);
	}
	/**
	 * 10进制数转为16进制字符串
	 * @param i
	 * @param num
	 * @return
	 * @throws Exception
	 */
	public static String itoa(int i) throws Exception {
		int num = 1;
		String target = Integer.toHexString(i);
		if (target.length() > num * 2 || i < 0) {
			throw new Exception("参数非法");
		}
		if (target.length() % 2 == 1) {
			target = "0" + target;
		}
		while (target.length() < num * 2) {
			for (int j = 0; j < num * 2 - target.length(); j++) {
				target = "0" + target;
			}
		}
		return target.toUpperCase();
	}
	

	
	/**
	 * 组成TLV
	 * @param tag
	 * @param input
	 * @return
	 */
	public static String toTLV(String tag,String input){
		if(StringUtils.isBlank(tag))return toTLV(input);
		return tag+toTLV(input);
	}
	
    public static String toTLV(String input) {
    	int inputLen = input.length()/2;
    	String strInputLenString = toHex(String.valueOf(inputLen));
    	if (inputLen > 65535) {
			input = "83" + strInputLenString + input;
		} else if (inputLen > 255) {
			input = "82" + strInputLenString + input;
		} else if(inputLen > 127) {
			input = "81" + strInputLenString + input;
		} else {
			input = strInputLenString + input;
		}

    	return input;
    }
    private static String toHex(String num) {
		String hex = Integer.toHexString(Integer.valueOf(num));
		if (hex.length() % 2 != 0) {
			hex = "0" + hex;
		}
		return hex.toUpperCase();
	}


}
