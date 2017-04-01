package com.whty.euicc.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * @author dengzm
 *
 */
public class RexUtil {

	/**
	 * 判断某个字符串是否满足某个正则表达式
	 * @param regExp
	 * @param target
	 * @return
	 */
	public static boolean isMatch(String regExp, String target) {
		if ((regExp == null) || (target == null)) {
		      return false;
		}
		Pattern pattern = Pattern.compile(regExp);
		Matcher m = pattern.matcher(target);
		return m.matches();
	}
	
	/**
	 * 判断某个字符串是否是邮箱格式
	 * @param target
	 * @return
	 */
	public static boolean isEmail(String target){
		return isMatch("^([\\w]+)(.[\\w]+)*@([\\w-]+\\.){1,5}([A-Za-z]){2,4}$", target);
	}
	
	/**
	 * 判断某个字符串是否包含数字和字母
	 * @param target
	 * @return
	 */
	public static boolean isAlphaAndNumber(String target){
		return isMatch("^[A-Za-z0-9]+$", target);
	}
	
}
