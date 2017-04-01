package com.whty.security.scp03t.scp03t.counter;

/**
 * 加密计数器类
 * 加密发送数据包时，每次发送数据包前，加密计数器都加一
 * @author Administrator
 *
 */
public class Scp03Counter {
	public static final String encCounter = "000000";
	
	public static final String perMac="00000000000000000000000000000000" ;
	/**
	 * 增加加密计数器
	 * @param encCounter 自己赋初值，或者由加法计数器得到
	 * @return
	 */
	public static String addEncCounter(String encCounter) {
		String counter="000000"+Integer.toHexString(Integer.parseInt(encCounter, 16)+1);//加密计数器增加
		counter=counter.substring((counter.length()-6), counter.length());
		return counter;
	}
	
	
}
