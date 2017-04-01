package com.whty.efs.plugins.tycard.constant;

public abstract class Constant {
	/**
     * 报文的类型
     * 00001001    个人化操作请求
     * 00001002    个人化指令处理
     * 00001003    个人化结束指令，个人化发起
     * 00001004    个人化结束指令，tsm发起
     */
	public interface CmdType{
		//个人化操作请求
		public static final String ONE = "00001001";
		//个人化指令处理
		public static final String TWO = "00001002";
		//个人化结束指令，个人化发起
		public static final String THREE = "00001003";
		//个人化结束指令，tsm发起
		public static final String FOUR = "00001004";
	}
	
	
	/** 每个APDU指令的LV串中长度L占的字符个数 4 */
	public static final int APDU_TLV_LEN = 4;
	/** 普通子TLV串中长度L的占的字符个数 2 */
	public static final int SUB_TLV_LEN = 2;
}
