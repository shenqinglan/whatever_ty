package com.whty.euicc.profile.util;

import static com.whty.euicc.profile.util.Tool.toHex;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.whty.euicc.profile.parent.JavaBean;


/**
 * 工具类 封装了对字符串的
 * @author 李欢
 *
 */
public class Tool {
	/*
	 * 16进制数字字符集
	 */
	private static String hexString = "0123456789ABCDEF";

	/**
	 * 将字符串编码成16进制数字,适用于所有字符（包括中文）
	 * 
	 * @param str
	 * @return
	 */
	public static String encode(String str) {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
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
	 * 将16进制数字解码成字符串,适用于所有字符（包括中文）
	 * 
	 * @param bytes
	 * @return
	 */
	public static String decode(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
					.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}

	
	
	/**
	 * 将OID的元素分解成一元一次或者一元二次的形式。
	 * @param num oid的元素
	 * @return 分解后的系数和余数存在数组中
	 */
	public static String[] compare(int num) {
		int a = num / 128;
		if (a < 128) {
			return new String[] { String.valueOf(a), String.valueOf(num % 128) };
		} else {
			int b = num / (128 * 128);
			int c = (num - b * 128 * 128) / 128;
			int d = (num - b * 128 * 128) % 128;
			return new String[] { String.valueOf(b), String.valueOf(c),
					String.valueOf(d) };
		}

	}
	
	/**
	 * 判断字符串是否是常量
	 * @param value
	 * @return
	 */
	public static boolean isConstant(String value){
		String regex = "^[a-z]\\S*\\d$";
		value.matches(regex);
		return value.matches(regex);
	}
	
	
	
	
	/**
	 * 判断字符串是否可以转成数字
	 * @param num 需要判断的字符串
	 * @return true表示能转 false则不能
	 */
	public static boolean isNumber(String num) {
		Pattern pattern = Pattern.compile("^[0-9]*$");
		Matcher matcher = pattern.matcher(num);
		return matcher.matches();
	}

	/**
	 * 判断传入字符串是否是ASN语法中的字符串
	 * @param num
	 * @return 
	 */
	public static boolean isString(String num) {
		String regEx = "\"|\"";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(num);
		return m.find();
	}

	/**
	 * 将字符串分割成类名和value
	 * @param str
	 * @return 分割后的结果存在数组中，第一个元素为类名。第二个为value
	 */
	public static String[] partition(String str) {

		String[] tlv = new String[2];
		
		if(str.charAt(str.length()-1) == ','){
			str = str.substring(0,str.length()-1);
		}
		
		if(str.contains("{") && str.contains("}")){
			tlv[0] = "identifier";
			tlv[1] = mysubString(str, "{", "}");
		}else if(str.contains(" : ")){
			tlv = str.split(" : ");
		}else if(!str.contains(" ")){
			tlv[0] = "Octet_String";
			tlv[1] = str;
		}else{
			int index = str.indexOf(" ");
			tlv[0] = str.substring(0,index);
			tlv[1] = str.substring(index+1);
		}
		

		return tlv;

	}
	

	/**
	 * 将10进制的数字转化成16进制的数字，
	 * 需要注意的是传入的字符串必须能Integer，
	 * 配合方法isNumber使用
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
	
	public static String intToHex(int num) {
		String hex = Integer.toHexString(num);
		if (hex.length() % 2 != 0) {
			hex = "0" + hex;
		}
		return hex.toUpperCase();
	}



	/**
	 * 判断参数是否是ASN语法中的十六进制的字符串
	 * @param num
	 * @return
	 */
	public static boolean isHexadecimal(String num) {
		String regEx = "'|'H";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(num);
		return m.find();
	}

	/**
	 * 规范参数格式，便于反射
	 * 首字母转大写并且将“-”转成“_”
	 * @param s
	 * @return
	 */
	public static String stringFormat(String s) {
		s = s.replace("-", "_");
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(
					Character.toUpperCase(s.charAt(0))).append(s.substring(1))
					.toString();
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
	public static String mysubString(String str, String str1, String str2) {
		if (str1.equals(str2)) {
			int num1 = str.indexOf(str1) + str1.length();
			int num2 = str.lastIndexOf(str2);
			return str.substring(num1, num2).trim();
		} else {
			int num1 = str.indexOf(str1) + str1.length();
			int num2 = str.indexOf(str2);
			return str.substring(num1, num2).trim();
		}

	}

	/**
	 * 根绝Tag判断是否为复杂类型
	 * @param num
	 * @return TURE不是复杂类型 
	 */
	public static boolean isStart(String tag) {
		String regx = "^0|^1|^4|^5|^8|^9|^D|^C";
		Pattern p = Pattern.compile(regx);
		Matcher m = p.matcher(tag);
		if (m.find()) {
			return true;
		} else {
			return false;
		}

	}
	
	
	/**
	 * 对OID进行编码，编码规则见“ASN.1的OID编码规则”文档
	 * @param value
	 * @return
	 */
	public static String encodOID(String value) {
		String[] oid = value.split(" ");
		String v1 = String.valueOf(Integer.valueOf(oid[0]) * 40
				+ Integer.valueOf(oid[1]));
		v1 = toHex(v1);
		List<String> list = new ArrayList<String>();

		for (int i = 2; i < oid.length; i++) {
			if (Integer.valueOf(oid[i]) < 128) {
				list.add(toHex(oid[i]));
			} else {
				String[] array = Tool.compare(Integer.valueOf(oid[i]));
				for (int j = 0; j < array.length - 1; j++) {
					int a = Integer.valueOf(array[j]) + 128;
					list.add(toHex(String.valueOf(a)));
				}
				list.add(toHex(array[array.length - 1]));

			}
		}
		for (String s : list) {
			v1 = v1 + s;
		}
		return v1;

	}
	
	/**
	 * 将编码后的profile元素打印出来
	 * @param list 存放每个ProfileElement中的元素
	 * @return
	 */
	public static String toDerStr(List<Object> list) {
		StringBuilder sb = new StringBuilder();
		for (Object obj : list) {
			JavaBean b = (JavaBean) obj;
		//	System.out.println(b);
			sb.append(b.getTag() + b.getLength());
			if (b.getValue() == null) {
				sb.append("");
			} else {
				sb.append(b.getValue());
			}
//			System.out.println(sb);
			//sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * 计算简单结构的value和length，并为生成对象赋值
	 * @param obj 通过反射创建的对象
	 * @param V 未编码的value
	 * @param c 加载类后的对象
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void initValue(Object obj, String V, Class c)
			throws Exception {
		// 参数类型列表
		Class[] types = new Class[] { String.class };

		String temp = myValue(V);
		// 参数列表
		Object[] params = new Object[] { temp };

		Method setMethod = c.getMethod("setValue", types);
		setMethod.invoke(obj, params);

		int num = temp.length() / 2;
		String length ="";
		if(num>127){
			length = lengthFormat(num);
		}else{
			length = toHex(String.valueOf(num));
		}
		params = new Object[] { length };
		setMethod = c.getMethod("setLength", types);
		setMethod.invoke(obj, params);
	}

	/**
	 * 将value编码成十六进制的字符串
	 * @param value
	 * @return 返回十六进制字符串
	 */
	public static String myValue(String value) {

		if (isNumber(value)) { // 如果是数字
			return encodInt(value);
		} else if (isString(value)) {// 如果是字符串
			return Tool.encode(mysubString(value, "\"", "\""));
		} else if (isHexadecimal(value)) {// 如果是十六进制串
			return mysubString(value, "'", "'H");
		} else if (value.equals("NULL")) {// 如果是NULL
			return "";
		} else if(isConstant(value) || "milenage".equals(value) || "tuak".equals(value)){//如果是常量
			return encodConstant(value);
		}else { // 是OID
			return encodOID(value);
		}

	}
	
	/**
	 * 对长形的length进行编码
	 * @param num 
	 * @return 返回编码的结果
	 */
	public static String lengthFormat(int num){
		String length = toHex(String.valueOf(num));
//		if (num > 65535) {
//			return "83" + length;
//		}else if (num > 255) {
//			return "82" + length;
//		}
//		else if(num > 127){
//			return "81" + length;
//			
//		}
//		
		int size = length.length()/2;
		return 8+""+size+length;
	}
	
	/**
	 * 对常量进行编码
	 * @param value 
	 * @return 返回编码后的结果
	 */
	@SuppressWarnings("unchecked")
	public static String encodConstant(String value) {
		String result = "";
		try {
			if(value.toUpperCase().contains("PUK")){
				Class c = Class.forName("com.whty.euicc.profile.util.PukCode");
				Object obj = getAttribute(c,value);
				result = encodInt(obj.toString());
			}else{
				Class c = Class.forName("com.whty.euicc.profile.util.PinCode");
				Object obj = getAttribute(c,value);
				result = encodInt(obj.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 对整数进行编码
	 * @param value
	 * @return
	 */
	public static String encodInt(String value){
		int num = Integer.valueOf(value);
		if(num>127){
			return "00"+toHex(value);
		}
		return toHex(value);
		
	}
	
	
	/**
	 * 更具属性名字，获取属性的值
	 * @param c 加载类后的Class对象
	 * @param name 属性名字
	 * @return 返回属性的值
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Object getAttribute(Class c,String name) throws Exception{
		Object obj = c.newInstance();
		Field field = c.getDeclaredField(name);
		return field.get(obj);
	}
	
	/**
	 * 判断length为长类型还是短类型，并计算length的值
	 * @param length length的十进制表示
	 * @param bean 
	 */
	public static void countLength(int length,JavaBean bean ){
		if(length>127){
			bean.setLength(Tool.lengthFormat(length));
		}else{
			bean.setLength(toHex(String.valueOf(length)));
		}
	}

}
