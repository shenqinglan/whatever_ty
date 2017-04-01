/**
 * Copyright (c) 2015-2017.
 * All rights reserved.
 * 
 * Created on 2017-3-7
 * Id: Constants.java,v 1.0 2017-3-7 下午4:45:03 Administrator
 */
package com.whty.oauth.platform.common;

/**
 * @ClassName Constants
 * @author Administrator
 * @date 2017-3-7 下午4:45:03
 * @Description TODO(定义了一些常数)
 */
public class Constants {
	
	public static final String REDIS_KEY_NAME_PREFIX = "OAUTH_";
	
	
	public static Integer UUID_RANDOM_NUM_8_BYTE = 8;
	public static Integer UUID_RANDOM_NUM_16_BYTE = 16;

	/**
	 * 接口类型编码
	 */
	public static Integer INTERFACE_TYPE_TRIGGER_REGISTER_IN = 0x61;
	public static Integer INTERFACE_TYPE_TRIGGER_REGISTER = 0x65;
	public static Integer INTERFACE_TYPE_BASE_OAUTH = 0x52;
	public static Integer INTERFACE_TYPE_VALIDCODE_OAUTH = 0x59;
	public static Integer INTERFACE_TYPE_CHANGE_PWD = 0x56;
	public static Integer INTERFACE_TYPE_RESET_PWD = 0x53;
	public static Integer INTERFACE_TYPE_REGISTER_STATE = 0x58;
	public static Integer INTERFACE_TYPE_CLEAR_REGISTER = 0x57;

	/**
	 * 卡上行短信类型编码
	 */
	public static String INTERFACE_TYPE_TRIGGER_REGISTER_MO = "51";
	public static String INTERFACE_TYPE_BASE_OAUTH_MO = "62";
	public static String INTERFACE_TYPE_VALIDCODE_OAUTH_MO = "69";
	public static String INTERFACE_TYPE_CHANGE_PWD_MO = "66";
	public static String INTERFACE_TYPE_RESET_PWD_MO = "63";
	public static String INTERFACE_TYPE_REGISTER_STATE_MO = "68";
	
	/**
	 * 接口调用者
	 */
	public static Integer INTERFACE_USER_FLAG_OUT = 0;
	public static Integer INTERFACE_USER_FLAG_IN = 1;
	
	/**
	 * 接口类型编号
	 */
	public static String INTERFACE_TYPE_CODE_TRIGGER_REGISTER = "01";
	public static String INTERFACE_TYPE_CODE_BASE_OAUTH = "02";
	public static String INTERFACE_TYPE_CODE_VALIDCODE_OAUTH = "03";
	public static String INTERFACE_TYPE_CODE_CHANGE_PWD = "04";
	public static String INTERFACE_TYPE_CODE_RESET_PWD = "05";
	public static String INTERFACE_TYPE_CODE_REGISTER_STATE = "06";
	public static String INTERFACE_TYPE_CODE_CLEAR_REGISTER = "07";
	
	/**
	 * 认证数据编码类型
	 */
	public static String AUTH_ENCODE_FORMAT_ASCII = "04";
	public static String AUTH_ENCODE_FORMAT_UCS2 = "08";
	
	/**
	 * 卡片服务状态
	 */
	public static Integer CARD_REGISTER_NO_ROAM = 0x00;
	public static Integer CARD_REGISTER_ROAM = 0x01;
	public static Integer CARD_REGISTER_NO_SERVICE = 0x02;
	
	/**
	 * 卡片注册状态
	 */
	public static String CARD_REGISTER_NO_REGISTER = "00";
	public static String CARD_REGISTER_FINISH_REGISTER = "01";
	
	/**
	 * 错误码
	 */
	public static String RSP_CODE_SUCCESS = "0000";
	public static String REQ_PARAM_NULL = "0001";
	public static String REQ_PARAM_ERROR = "0002";
	public static String REQ_PARAM_AUTH_FORMAT_ERROR = "0003";
	public static String REQ_PARAM_TYPE_ENCODE_ERROR = "0004";
	public static String REQ_PARAM_SMS_SEND_ERROR = "0005";
	public static String REQ_PARAM_SMS_TRANSACTION_NULL = "0006";
	public static String REQ_PARAM_AUTH_DATA_LENGTH_ERROR = "0007";
	public static String REQ_PARAM_INIT_TRANSACTION_EMPTY = "0008";
	public static String REQ_PARAM_CARD_ALREADY_REGISTER = "0009";
	public static String SCP80_ENCRPY_MSG_ERROR = "0010";
	public static String SCP80_ENCRPY_QERRY_KEY_NULL = "0011";
	public static String SCP80_ENCRPY_PARAMS_ERROR = "0012";
	
	/**
	 * 秘钥index
	 */
	public final static String SCP80_ENCRYP_KIC_INDEX = "01";
	public final static String SCP80_ENCRYP_KID_INDEX = "02";
	
	/**
	 * 秘钥数目
	 */
	public final static int SCP80_ENCRYP_KEYS_NUMBER = 2;
	
}
