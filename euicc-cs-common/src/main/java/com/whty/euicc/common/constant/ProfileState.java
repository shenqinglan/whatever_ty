package com.whty.euicc.common.constant;
/**
 * Profile状态常量
 * @author Administrator
 *
 */
public class ProfileState {
	
	public final static String TEMP = "99";//创建过程中临时存储
	public final static String INIT = "00";//初始状态
	public final static String ENABLE = "01";//启用
	public final static String DIS_ENABLE = "02";//禁用
	public final static String DELETE = "03";//删除
	
	public final static String PROCESS_WAITING = "10";
	public final static String CREATE_ISD_P_STATE_SUCCESS = "11";
	public final static String PERSONAL_ISD_P_STATE_SUCCESS = "12";
	public final static String INSTALL_ISD_P_STATE_SUCCESS = "13";
	public final static String INITTAGS_ISD_P_STATE_SUCCESS = "14";
	public final static String CREATE_ISD_P_STATE_ERROR = "21";
	public final static String PERSONAL_ISD_P_STATE_ERROR = "22";
	public final static String INSTALL_ISD_P_STATE_ERROR = "23";
	public final static String INITTAGS_ISD_P_STATE_ERROR = "24";

}
