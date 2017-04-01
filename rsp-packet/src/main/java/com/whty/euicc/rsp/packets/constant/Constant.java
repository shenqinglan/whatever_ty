package com.whty.euicc.rsp.packets.constant;

import java.nio.charset.Charset;

import com.google.gson.Gson;

/**
 * 报文常量
 * @author dengzm
 *
 */
public interface Constant {
	/** 报文起始符 */
    public final static byte[]   MESSAGE_START = new byte[]{48,50};
    /** 字符集 */
    public static Charset UTF8 = Charset.forName("UTF-8");
    /** GSON */
    public static final Gson gson = new Gson();
    /** 报文处理成功*/
    public static final String SUCCESS = "0000";
    /** 报文处理成功*/
    public static final String FAILURE = "0001";
    
    /** F2 域 交换密钥请求 标示 **/
	public static final String F2_TRADEKEY_OBTAIN = "00";
	
	/** F2 域 交易后续请求标示  **/
	public static final String F2_FOLLOW_UP = "01";
	
	/** F2 域 明文请求标示 **/
	public static final String F2_PROCLAIMED_ENCODE = "02";
}
