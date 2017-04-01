package com.whty.euicc.rsp.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * 生产不重复主键工具类
 * @author Administrator
 *
 */
public class RandomUtil {
	public static String createId(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	/**
	 * 类似04386-AGYFT-A74Y8-3F815形式
	 * @return
	 */
	public static String genarateMatchingId() {
		StringBuffer sbBuffer = new StringBuffer().append(generateWord())
				.append("-").append(generateWord())
				.append("-").append(generateWord())
				.append("-").append(generateWord());
		return sbBuffer.toString();
	}

	private static String generateWord() {
		String[] beforeShuffle = new String[] { "0", "1", "2", "3", "4", "5",
				"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
				"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
				"U", "V", "W", "X", "Y", "Z" };
		List<String> list = Arrays.asList(beforeShuffle);
		// 打乱排序
		Collections.shuffle(list);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}

		String afterShuffle = sb.toString();

		// 更改下面两个数字可以去到不同位数的随机数哦
		String result = afterShuffle.substring(5, 10);
		return result;
	}
}
