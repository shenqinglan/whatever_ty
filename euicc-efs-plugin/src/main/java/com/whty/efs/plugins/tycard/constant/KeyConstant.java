// Copyright (C) 2012 WHTY
package com.whty.efs.plugins.tycard.constant ;

/**
 * 报文字段名称 (参数MAP里的键名称，其他参数不要在这里维护) 参数键名统一大写（避免大小写问题造成取到的键值不一样。ctrl+shift+X）
 * 
 * @author Administrator
 * 
 */
public abstract class KeyConstant {

	/**
	 * command中间过程参数
	 * 
	 * @author dengjun
	 * @since 2014-10-11
	 * 
	 */
	public interface CommandMiddle {

		/**
		 * 应用所在地 1为本地应用，2为第三方应用 task使用清单： 1、SelectIsdTask 写入，读出
		 * 
		 */
		public final static String APPLITE = "appSite" ;

		/**
		 * 卡片批次信息 task使用清单： 1、IsValidSETask 写入 2、 SelectIsdTask 读出
		 */
		public final static String CARDBATCHID0 = "cardBatchId" ;

		/**
		 * 卡分类
		 */
		public final static String CATEGORY = "category" ;

		/**
		 * 应用类型 task使用清单： 1、IsValidSETask 写入
		 */
		public final static String APPTYPE = "appType" ;

		/**
		 * 安全域密钥
		 */
		//public final static String SDKEYS = "SdKeys";
		
		public final static String PRIVILEGE0 = "privilege";
		
		/**以下字段为状态同步使用*/
		public static final String PAMID = "PAMID";
		public static final String SSDAID = "SSDAID"; // 辅助安全域密钥
		public static final String STS = "Sts";
	    public static final String PROCESSCODE_SUCCESS   = "0000"; // 成功
	    public static final String PROCESSCODE_FAILURE   = "0001"; // 失败
	    public static final String PROCESSCODE_RESERVE   = "0009"; // 保留
		public static final String RJCTCD = "RjctCd";
		public static final String RJCTINF = "RjctInf";
		public static final String PAIDLIST = "PaidList";
		public static final String SSDAIDLIST0 = "SsdAidList";
		public static final String CANCLELIST0 = "CancleList";
		public static final String DELETESSDFORSELIST = "DeleteSsdForSeList";
	}

	/**
	 * 是否常量
	 * 
	 * @author dengjun
	 * 
	 */
	public interface TrueOrFalseBool {

		public final static String FALSE = "0" ;

		public final static String TRUE = "1" ;
	}

	public interface TrueOrFalseStr {

		public final static String FALSE = "false" ;

		public final static String TRUE = "true" ;
	}

	// /** 命令类型 */
	// public final static String commandType = "COMMAND_TYPE";
	// /** 交易序列号 */
	// public final static String tradeNo = "TRADE_NO";
	// /** 交易时间 */
	// public final static String tradeTime = "TRADE_TIME";
	// /** 应用管理终端类型 */
	// public final static String deviceType = "DEVICE_TYPE";
	// /** SEID */
	// public final static String seId = "SEID";

	/** COS版本 */
	public final static String cosVersion = "COS_VERSION" ;

	/** 补丁版本 */
	public final static String patchVersion = "PATCH_VERSION" ;

	/** 是否更新补丁 */
	public final static String isUpdate = "IS_UPDATE" ;

	/** 循环次数 */
	public final static String num = "NUM" ;

	/** 安全载体执行指令后的结果是否正确 */
	public static final String flag = "FLAG" ;

	/** 安全载体执行指令后的结果 */
	public static final String resp = "RESP" ;
	public static final String resc = "RESC" ;
	public static final String fail = "FAIL" ;

	/** 响应码 */
	public static final String respCode = "RESP_CODE" ;

	/** 响应指令 */
	public static final String respApdu = "RESP_APDU" ;

	// /** 应用AID */
	// public final static String aId = "AID";
	// /** 应用版本 */
	// public final static String appVersion = "APP_VERSION";
	// /** 响应码 */
	// public final static String responseCode = "RESPONSE_CODE";
	// /** 交易结束标识:00未结束，01结束 */
	// public final static String endFlag = "END_FLAG";
	// /** 发卡方TSM的网络地址 */
	// public final static String coTsmUrl = "CO_TSM_URL";
	// /** 响应数据 */
	// public final static String seResponse = "SE_RESPONSE";
	// /** 机构ID */
	// public final static String orgId = "ORG_ID";
	// /** 机构名称 */
	// public final static String orgName = "ORG_NAME";
	// /** 机构状态 */
	// public final static String orgStatus = "ORG_STATUS";
	// /** 机构简介 */
	// public final static String orgSummary = "ORG_SUMMARY";
	// /** 申请人 */
	// public final static String userName = "USER_NAME";
	// /** DN信息 */
	// public final static String dn = "DN";
	// /** 证书开始时间 */
	// public final static String startTime = "START_TIME";
	// /** 证书截止时间 */
	// public final static String endTime = "END_TIME";
	// /** PKCS#10 */
	// public final static String pkcs10 = "PKCS10";
	// /** 成功标志： 0000证书申请成功 FFFF申请失败 */
	// public final static String repCode = "REP_CODE";
	// /**
	// * 证书信息： 只有REPCODE为0时才附带证书信息。为X509证书格式的数字证书，其格式参见 《信息安全技术 公钥基础设施 数字证书格式
	// */
	// public final static String cert = "CERT";
	// /** 参考号 */
	// public final static String refNumber = "REF_NUMBER";
	// /** 授权码 */
	// public final static String authCode = "AUTH_CODE";
	// /** PKCS10请求长度 */
	// public final static String pkcs10Len = "PKCS10_LEN";
	// /** PKCS#10请求 */
	// public final static String pkcs10Request = "PKCS10_REQUEST";
	// /** 证书长度 */
	// public final static String certLen = "CERT_LEN";
	// /** SE的X509证书 */
	// public final static String seCert = "SE_CERT";
	// /** 应用描述 */
	// public final static String appDesc = "APP_DESC";
	// /** 应用检测报告 */
	// public final static String appTestReport = "APP_TEST_REPORT";
	// /** 应用名称 */
	// public final static String appName = "APP_NAME";
	// /** 应用类型 */
	// public final static String appType = "APP_TYPE";
	// /** 应用编号 */
	// public final static String appNo = "APP_NO";
	// /** SE发行方TSM机构ID */
	// public final static String srcOrgId = "SRCORGID";
	// /** 安全域AID */
	// public final static String ssdAid = "SSD_AID";
	// /** 安全域密钥版本号 */
	// public final static String ssdKeyVersion = "SSD_KEY_VERSION";
	// /** 安全域密钥索引号 */
	// public final static String ssdKeyIndex = "SSD_KEY_INDEX";
	// /** 安全域密钥值 */
	// public final static String ssdKey = "SSD_KEY";
	// /** 有效期 */
	// public final static String validPeriod = "VALID_PERIOD";
	// /** 根密钥索引 */
	// public final static String keyIndex = "KEY_INDEX";
	// /** Enc密钥（敏感数据） */
	// public final static String ssdkeyEnc = "SSDKEY_ENC";
	// /** Mac密钥（敏感数据） */
	// public final static String ssdkeyMac = "SSDKEY_MAC";
	// /** DEK密钥（敏感数据） */
	// public final static String ssdkeyDek = "SSDKEY_DEK";
	// /** 证书编号 */
	// public final static String certificateserialNo = "CERTIFICATESERIAL_NO";
	// /** 返回状态：0000成功 FFFF失败 */
	// public final static String repStatus = "REP_STATUS";
	//
	// public final static String issuerCode = "ISSURE_CODE";
	// /** 最多18个字节，不足18个字节的右填空格补齐 */
	// public final static String userIdNo = "USER_ID_NO";
	// /** 01-身份证，以16进制形式表示 */
	// public final static String userIdType = "USER_ID_TYPE";
	// /** 安全载体状态：01锁定02解锁 03 终止 */
	// public final static String state = "STATE";
	// /** 发起方TSM机构ID */
	// public final static String srcTsmId = "SRC_TSM_ID";
	// /** 统一服务平台机构ID */
	// public final static String desTsmId = "DES_TSM_ID";
	// /** 0000下载许可 */
	// public final static String authenticateRes = "AUTHENTICATE_RES";
	// /** 验证结果，0000通过验证，FFFF验证失败 */
	// public final static String validationCode = "VALIDATION_CODE";
	// /** 安全与权限 */
	// public final static String ssdPrevilage = "SSD_PREVILAGE";
	// /** Load指令hash */
	// public final static String loadHash = "LOAD_HASH";
	// /** install指令hash */
	// public final static String installHash = "INSTALL_HASH";
	// /** load指令token */
	// public final static String loadToken = "LOAD_TOKEN";
	// /** install指令token */
	// public final static String installToken = "INSTALL_TOKEN";
	// /** 操作结果 */
	// public final static String opResult = "OP_RESULT";
	// /** 操作类型 */
	// public final static String opType = "OP_TYPE";
	// /** 最多18个字节，不足18个字节的右填空格补齐 */
	// // public final static String userIdNO = "USER_ID_NO";
	// /** 认证结果：0000认证通过0001认证中，FFFF认证失败； */
	// public final static String validationResult = "VALIDATION_RESULT";
	// /** 当需要统一服务平台验证SE时，生成验证SE的指令； */
	// public final static String responseApdu = "RESPONSE_APDU";
	// /** 最多16个字节，不足16个字节的右填空格补齐 */
	// public final static String msisdn = "MSISDN";
	// /** 响应码：000000表示成功 */
	// public final static String resultCode = "RESULT_CODE";
	// /** 激活结果:0000激活成功，0001激活中，FFFF激活失败 */
	// public final static String registerResult = "REGISTER_RESULT";
	//
	// public final static String imei = "IMEI";
	//
	// public final static String imsi = "IMSI";
	// /** 验证码 */
	// public final static String verifyCode = "VERIFY_CODE";
	// /** 响应消息 */
	// public final static String resultMessage = "RESULT_MESSAGE";
	//
	// public final static String appInstallTag = "APP_INSTALLED_TAG";
	// /** 应用提供商标识 */
	// public final static String providerId = "PROVIDER_ID";
	// /** 应用提供商名称 */
	// public final static String providerName = "PROVIDER_NAME";
	// /** 应用状态 00表示上线，FF表示下架 */
	// public final static String appState = "APP_STATE";
	// /** 卡上应用状态 */
	// public final static String appStatus = "APP_STATUS";
	// /** 授权码 */
	// public final static String authenticationCode = "AUTHENTICATION_CODE";
	//
	// /** Token计算用到的数据单元 */
	// public final static String tokenData = "TOKEN_DATA";
	// /** 指令的TOKEN值 */
	// public final static String token = "TOKEN";
	//
	// public final static String cardNum = "CARD_NUM";
	//
	// /** 卡片类型 */
	// public final static String cardType = "CARD_TYPE";
	// /** 卡片状态 */
	// public final static String cardStatus = "CARD_STATUS";
	// /** 卡片失效日期 */
	// public final static String disposeDate = "DISPOSE_DATE";
	// /** 卡片所属TSM */
	// public final static String tsmCode = "TSM_CODE";
	//
	// /** 请求参数(不带命令类型) */
	// public final static String requestMsg = "REQUEST_MSG";
	// /** 实际命令类型(通过这个决定走哪个步骤) */
	// public final static String refCommandType = "REF_COMMAND_TYPE";
	// /** 响应参数(不带命令类型) */
	// public final static String responseMsg = "RESPONSE_MSG";
	//
	// // public final static String sessionInfo = "SESSION_INFO";
	//
	// // 列表类型*****************************************
	// public final static String desTsmIdList = "DES_TSM_ID_LIST";
	// public final static String appList = "APP_LIST";
	// public final static String tokenDataList = "TOKEN_DATA_LIST";
	// public final static String tokenList = "TOKEN_LIST";
	// public final static String ssdKeyList = "SSD_KEY_LIST";
	// 已不再使用，多条指令按照一条方式进行传输
	// public final static String responseApduList = "RESPONSE_APDU_LIST";

	// *****************************************

	// public final static String batchPushTaskId = "BATCH_PUSH_TASK_ID";
	//
	// /** 终端发过来的交易号 */
	// public final static String termTradeNo = "TERM_TRADE_NO";
	// /** 寻址，TSM地址 */
	// public final static String orgAddress = "ORG_ADDRESS";
	//
	// // 原子任务之间的参数传递，参数名称定义及注释,Made by cy
	// // 2012-06-09*****************************************************
	// // 原子任务在执行SE操作前，需要先设置SE需要同步操作参数，在设置同步原子任务里根据参数来更新数据库
	// /** 原子任务之间的参数传递 安全通道协议 */
	// public final static String scp = "SCPXX";
	//
	// public final static String cardSpaceTag = "CARD_SPACE_TAG";
	//
	// /** sdkeys 生存期？ */
	// public final static String sd_keys = "SD_KEYS";
	// /** STOREDATA 指令带上的数据 生存期？ */
	// public final static String datas = "DATAS";
	// /** token私钥 生存期？ */
	// public final static String tokenKey = "tokenKey";
	// /** 发送的to list参数， 生存期：一次消息触发过程 */
	// public final static String to = "to";
	//
	// public final static String sd_key = "SD_KEY";
	// /** 安全级别 目前只支持0x00、0x01、0x03三种安全级别 */
	// public final static String security="security";
	// /** 安全通道协议 目前只支持0x01、0x02 */
	// public final static String protocol="protocol";
	// /** 安全策略 目前只支持0x05、0x15 */
	// public final static String policy="policy";
	// /**会话密钥*/
	// public final static String sessionMac="sessionMac";
	//

	// /** DAP密钥（） */
	// public final static String ssdkeyDAp = "SSDKEY_DAP";
	// /** TOKEN密钥（） */
	// public final static String isdkeyToken = "ISDKEY_TOKEN";
	// /** 应用个人化APDU指令 */
	// public final static String appPersionApdu = "APPPERSIONAPDU";
	// /** 原子任务之间的参数传递 存会话内容 */
	// public final static String sessionInfo = "SESSIONINFO";
	// // session
	// // key--持久(跨多个消息触发，需要存以下参数)--------------------------------
	// // 还包括se_id,在sessionInfo里有一个备份
	// public final static String s_hostrand = "S_HOSTRAND";
	// /** C-MAC会话密钥 */
	// public final static String s_sessionMac = "S_SESSIONMAC";
	// /** 加密会话密钥 */
	// public final static String s_sessionEnc = "S_SESSIONENC";
	// /** 数据加密会话密钥 */
	// public final static String s_sessionDek = "S_SESSIONDEK";
	// /** 最新ICV 默认为8字节的0x00 */
	// public final static String s_icv = "S_ICV";
	// /** 计算MAC的初始值是否动态变化，false 固定为设置值，true 动态变化 (后续计算使用前一次得到的MAC作为初始值 默认) */
	// public final static String s_variable = "S_VARIABLE";
	// /** 安全级别 目前只支持0x00、0x01、0x03三种安全级别 */
	// public final static String s_security = "S_SECURITY";
	// /** 安全通道协议 目前只支持0x01、0x02 */
	// public final static String s_protocol = "S_PROTOCOL";
	// /** 安全策略 目前只支持0x05、0x15 */
	// public final static String s_i = "S_I";
	// /** 主安全域AID */
	// public final static String s_isdAid = "S_ISDAID";
	// /** 静态密钥DEK */
	// public final static String s_kdek = "S_KDEK";
	// /** 外部认证用到，当前外部认证的安全域AID */
	// public final static String s_sdAid = "S_SDAID";
	//
	// public final static String session_dek_div = "SESSION_DEK_DIV";
	// public final static String session_enc_div = "SESSION_ENC_DIV";
	// public final static String session_mac_div = "SESSION_MAC_DIV";
	// public final static String session_flag = "SESSION_FLAG";
	// public final static String key_ver = "KEY_VER";
	//
	// /** 跟卡端交互的上一个命令类型 执行一次请求后需要清除的参数，每次有新的终端请求就清空 */
	// public final static String last_Command = "LAST_COMMAND";
	// /** 原子任务的期望响应码(参数里存的是LV)，每次有新的终端请求就清空 */
	// public final static String last_Command_rCode = "LAST_COMMAND_RCODE";
	//
	// /** 同步原子任务中需要处理的步骤 hashmap */
	// public final static String syncSteps = "SYNCSTEPS";
	// /** 当前同步步骤 */
	// public final static String syncCurrentStep = "SYNCCURRENTSTEP";
	//
	// /** 原子任务之间的参数传递 外部认证，主机产生的随机数 */
	// public final static String exau_rand = "EXAU_RAND";
	// public final static String key_version = "KEY_VERSION";
	// // public final static String isd_key_version = "ISD_KEY_VERSION";
	// public final static String key_Identifier = "KEY_IDENTIFIER";
	//
	// /** 原子任务之间的参数传递 setSEStatus="SET_SE_STATUS" */
	// public final static String setSEStatus = "SET_SE_STATUS";
	// /** 原子任务之间的参数传递 setSDStatus="SET_SD_STATUS" */
	// public final static String setSDStatus = "SET_SD_STATUS";
	// /** 原子任务之间的参数传递 setAPPStatus="SET_APP_STATUS" */
	// public final static String setAPPStatus = "SET_APP_STATUS";
	// /** 原子任务之间的参数传递 缓存GETSTATUS命令的P2参数第B2位参数，根据B2位不同，响应消息解析方式不同 */
	// public final static String getstatusP2B2 = "GETSTATUS_P2B2";
	// // /**卡片ID*/
	// // public final static String cardId = "CARDID";
	//
	// public final static String s_verifyCode = "S_VERIFYCODE";
	// /** 应用提供方存储APDU的数组队列 */
	// public final static String apTokenApdu = "APTOKENAPDU";
	// /** 需要提交获取TOKEN的数据单元 */
	// public final static String apTokenData = "APTOKENDATA";
	//
	// /** 流程终止的错误代码 */
	// public final static String breakErrorCode = "S_BREAK_ERROR_CODE";
	// /** 流程终止的错误代码(临时存放) */
	// public final static String breakErrorTemp = "S_BREAK_ERROR_TEMP";
	// /**
	// * GETDATA命令P1P2的list,值是字符串以逗号<br>
	// * 每个值格式：p1p2(2字节)+分隔符(|)+aid(应用AID或安全域AID)
	// */
	// public final static String getDataList = "GETDATALIST";
	//
	// public static final String tokenKeyVersion = "TOKEN_KEY_VERSION";
	// public static final String tokenKeyId = "TOKEN_KEY_ID";
	// public static final String dapKeyVersion = "DAP_KEY_VERSION";
	// // 71 双芯片卡|||73
	// // 移动
	// public static final String dapKeyId = "DAP_KEY_ID";
	// public static final String sencKeyId = "SENC_KEY_ID";
	// public static final String smacKeyId = "SMAC_KEY_ID";
	// public static final String sdekKeyId = "SDEK_KEY_ID";
	// public static final String sKeyVersion = "SKEY_VERSION"; // 10 双芯片卡|||01
	// 移动
	// /**应用下载所需loadFiles*/
	// public static final String loadFiles = "DOWN_LOAD_FIELS" ;
	//
	// public static final String appNewStatus = "APPNEWSTATUS";
	// public static final String seNewStatus = "SENEWSTATUS";
	// public static final String sdNewStatus = "SDNEWSTATUS";
	//
	// public static final String cardBatch = "CARDBATCH";
	// /**上线时间**/
	// public static final String onlineDate = "ONLINE_DATE";
	// /**文件大小**/
	// public static final String fileSize = "FILE_SIZE";
	// /**应用LOGO下载地址**/
	// public static final String appLogoUrl = "APP_LOGO_URL";
	// /**应用下载地址**/
	// public static final String appAddr = "APP_ADDR";
	//
	// public static final String Businessapdu = "BUSINESS_APDU";
	// // session key
	// // --持久------------------------------------------------------------------
	//
	// // **********************************************************
	//
	// public final static String progress = "PROGRESS";
	// public final static String hasSteps = "HASSTEPS";
	// public final static String progressed = "PROGRESSED";
	//
	//
	// public final static String orderId = "ORDERID";
	// public final static String orderStatus = "ORDERSTATUS";
	//
	// public final static String businessId = "BUSINESSID";
	//
	// public final static String category ="CATEGORY";

	public final static String apk = "APK" ;

	public final static String apkName = "APKNAME" ;

	public final static String deviceId = "DEVICEID" ;

	/** 命令类型 */
	public final static String commandType = "COMMAND_TYPE" ;

	/** 交易序列号 */
	public final static String tradeNo = "TRADE_NO" ;

	/** 交易时间 */
	public final static String tradeTime = "TRADE_TIME" ;

	/** 应用管理终端类型 */
	public final static String deviceType = "DEVICE_TYPE" ;

	/** SEID */
	public final static String seId = "seID" ;

	/** 应用AID */
	public final static String aId = "AID" ;

	/** 应用版本 */
	public final static String appVersion = "APP_VERSION" ;

	/** 响应码 */
	public final static String responseCode0 = "RESPONSE_CODE" ;

	/** 交易结束标识:00未结束，01结束 */
	public final static String endFlag = "END_FLAG" ;

	/** 发卡方TSM的网络地址 */
	public final static String coTsmUrl = "CO_TSM_URL" ;

	/** 响应数据 */
	public final static String seResponse = "SE_RESPONSE" ;

	/** 机构ID */
	public final static String orgId = "ORG_ID" ;

	/** 机构名称 */
	public final static String orgName = "ORG_NAME" ;

	/** 机构状态 */
	public final static String orgStatus = "ORG_STATUS" ;

	/** 机构简介 */
	public final static String orgSummary = "ORG_SUMMARY" ;

	/** 申请人 */
	public final static String userName = "USER_NAME" ;

	/** DN信息 */
	public final static String dn = "DN" ;

	/** 证书开始时间 */
	public final static String startTime = "START_TIME" ;

	/** 证书截止时间 */
	public final static String endTime = "END_TIME" ;

	/** PKCS#10 */
	public final static String pkcs10 = "PKCS10" ;

	/** 成功标志： 0000证书申请成功 FFFF申请失败 */
	public final static String repCode = "REP_CODE" ;

	/**
	 * 证书信息： 只有REPCODE为0时才附带证书信息。为X509证书格式的数字证书，其格式参见 《信息安全技术 公钥基础设施 数字证书格式
	 */
	public final static String cert = "CERT" ;

	/** 参考号 */
	public final static String refNumber = "REF_NUMBER" ;

	/** 授权码 */
	public final static String authCode = "AUTH_CODE" ;

	/** PKCS10请求长度 */
	public final static String pkcs10Len = "PKCS10_LEN" ;

	/** PKCS#10请求 */
	public final static String pkcs10Request = "PKCS10_REQUEST" ;

	/** 证书长度 */
	public final static String certLen = "CERT_LEN" ;

	/** SE的X509证书 */
	public final static String seCert = "SE_CERT" ;

	/** 应用描述 */
	public final static String appDesc = "APP_DESC" ;

	/** 应用检测报告 */
	public final static String appTestReport = "APP_TEST_REPORT" ;

	/** 应用名称 */
	public final static String appName = "APP_NAME" ;

	/** 应用类型 */
	public final static String appType = "APP_TYPE" ;

	/** 应用编号 */
	public final static String appNo = "APP_NO" ;

	/** SE发行方TSM机构ID */
	public final static String srcOrgId = "SRCORGID" ;

	/** 安全域AID */
	public final static String ssdAid0 = "SSD_AID" ;

	/** 安全域密钥版本号 */
	public final static String ssdKeyVersion = "SSD_KEY_VERSION" ;

	/** 安全域密钥索引号 */
	public final static String ssdKeyIndex = "SSD_KEY_INDEX" ;

	/** 安全域密钥值 */
	public final static String ssdKey = "SSD_KEY" ;

	/** 有效期 */
	public final static String validPeriod = "VALID_PERIOD" ;

	/** 根密钥索引 */
	public final static String keyIndex = "KEY_INDEX" ;

	/** Enc密钥（敏感数据） */
	public final static String ssdkeyEnc = "SSDKEY_ENC" ;

	/** Mac密钥（敏感数据） */
	public final static String ssdkeyMac = "SSDKEY_MAC" ;

	/** DEK密钥（敏感数据） */
	public final static String ssdkeyDek = "SSDKEY_DEK" ;

	/** 证书编号 */
	public final static String certificateserialNo = "CERTIFICATESERIAL_NO" ;

	/** 返回状态：0000成功 FFFF失败 */
	public final static String repStatus = "REP_STATUS" ;

	public final static String issuerCode = "ISSURE_CODE" ;

	/** 最多18个字节，不足18个字节的右填空格补齐 */
	public final static String userIdNo = "USER_ID_NO" ;

	/** 01-身份证，以16进制形式表示 */
	public final static String userIdType = "USER_ID_TYPE" ;

	/** 安全载体状态：01锁定02解锁 03 终止 */
	public final static String state = "STATE" ;

	/** 发起方TSM机构ID */
	public final static String srcTsmId = "SRC_TSM_ID" ;

	/** 统一服务平台机构ID */
	public final static String desTsmId = "DES_TSM_ID" ;

	/** 0000下载许可 */
	public final static String authenticateRes = "AUTHENTICATE_RES" ;

	/** 验证结果，0000通过验证，FFFF验证失败 */
	public final static String validationCode = "VALIDATION_CODE" ;

	/** 安全与权限 */
	public final static String ssdPrevilage = "SSD_PREVILAGE" ;

	/** Load指令hash */
	public final static String loadHash = "LOAD_HASH" ;

	/** install指令hash */
	public final static String installHash = "INSTALL_HASH" ;

	/** load指令token */
	public final static String loadToken = "LOAD_TOKEN" ;

	/** install指令token */
	public final static String installToken = "INSTALL_TOKEN" ;

	/** 操作结果 */
	public final static String opResult = "OP_RESULT" ;

	/** 操作类型 */
	public final static String opType = "OP_TYPE" ;

	/** 最多18个字节，不足18个字节的右填空格补齐 */
	// public final static String userIdNO = "USER_ID_NO";
	/** 认证结果：0000认证通过0001认证中，FFFF认证失败； */
	public final static String validationResult = "VALIDATION_RESULT" ;

	/** 当需要统一服务平台验证SE时，生成验证SE的指令； */
	public final static String responseApdu0 = "RESPONSE_APDU" ;

	/** 最多16个字节，不足16个字节的右填空格补齐 */
	public final static String msisdn = "MSISDN" ;

	/** 响应码：000000表示成功 */
	public final static String resultCode = "RESULT_CODE" ;

	/** 激活结果:0000激活成功，0001激活中，FFFF激活失败 */
	public final static String registerResult = "REGISTER_RESULT" ;

	public final static String imei = "IMEI" ;

	public final static String imsi = "IMSI" ;

	/** 验证码 */
	public final static String verifyCode = "VERIFY_CODE" ;

	/** 响应消息 */
	public final static String resultMessage = "RESULT_MESSAGE" ;

	public final static String appInstallTag = "APP_INSTALLED_TAG" ;

	/** 应用提供商标识 */
	public final static String providerId = "PROVIDER_ID" ;

	/** 应用提供商名称 */
	public final static String providerName = "PROVIDER_NAME" ;

	/** 应用状态 00表示上线，FF表示下架 */
	public final static String appState = "APP_STATE" ;

	/** 卡上应用状态 */
	public final static String appStatus = "APP_STATUS" ;

	/** 授权码 */
	public final static String authenticationCode = "AUTHENTICATION_CODE" ;

	/** Token计算用到的数据单元 */
	public final static String tokenData = "TOKEN_DATA" ;

	/** 指令的TOKEN值 */
	public final static String token = "TOKEN" ;

	public final static String cardNum = "CARD_NUM" ;

	/** 卡片类型 */
	public final static String cardType = "CARD_TYPE" ;

	/** 应用运营商类型 */
	public final static String apType = "AP_TYPE" ;

	/** 卡片状态 */
	public final static String cardStatus = "CARD_STATUS" ;

	/** 卡片失效日期 */
	public final static String disposeDate = "DISPOSE_DATE" ;

	/** 卡片所属TSM */
	public final static String tsmCode = "TSM_CODE" ;

	/** 请求参数(不带命令类型) */
	public final static String requestMsg = "REQUEST_MSG" ;

	/** 实际命令类型(通过这个决定走哪个步骤) */
	public final static String refCommandType = "REF_COMMAND_TYPE" ;

	/** 响应参数(不带命令类型) */
	public final static String responseMsg = "RESPONSE_MSG" ;

	// public final static String sessionInfo = "SESSION_INFO";

	// 列表类型*****************************************
	public final static String desTsmIdList = "DES_TSM_ID_LIST" ;

	public final static String appList = "APP_LIST" ;

	public final static String tokenDataList = "TOKEN_DATA_LIST" ;

	public final static String tokenList = "TOKEN_LIST" ;

	public final static String ssdKeyList = "SSD_KEY_LIST" ;
	
	public static final String tokenKeyVersion = "TOKEN_KEY_VERSION" ;

	public static final String tokenKeyId = "TOKEN_KEY_ID" ;

	public static final String dapKeyVersion = "DAP_KEY_VERSION" ;

	// 已不再使用，多条指令按照一条方式进行传输
	// public final static String responseApduList = "RESPONSE_APDU_LIST";

	// *****************************************

	public final static String batchPushTaskId = "BATCH_PUSH_TASK_ID" ;

	/** 终端发过来的交易号 */
	public final static String termTradeNo = "TERM_TRADE_NO" ;

	/** 寻址，TSM地址 */
	public final static String orgAddress = "ORG_ADDRESS" ;

	// 原子任务之间的参数传递，参数名称定义及注释,Made by cy
	// 2012-06-09*****************************************************
	// 原子任务在执行SE操作前，需要先设置SE需要同步操作参数，在设置同步原子任务里根据参数来更新数据库
	/** 原子任务之间的参数传递 安全通道协议 */
	//public final static String scp = "SCPXX" ;

	//public final static String cardSpaceTag = "CARD_SPACE_TAG" ;

	/** sdkeys 生存期？ */
	public final static String sd_keys = "SD_KEYS" ;

	/** STOREDATA 指令带上的数据 生存期？ */
	public final static String datas = "DATAS" ;

	/** token私钥 生存期？ */
	public final static String tokenKey = "tokenKey" ;

	/** 发送的to list参数， 生存期：一次消息触发过程 */
	public final static String to = "to" ;

	public final static String sd_key = "SD_KEY" ;

	// /** 安全级别 目前只支持0x00、0x01、0x03三种安全级别 */
	// public final static String security="security";
	// /** 安全通道协议 目前只支持0x01、0x02 */
	// public final static String protocol="protocol";
	// /** 安全策略 目前只支持0x05、0x15 */
	// public final static String policy="policy";
	// /**会话密钥*/
	// public final static String sessionMac="sessionMac";
	//

	/** DAP密钥（） */
	public final static String ssdkeyDAp = "SSDKEY_DAP" ;

	/** TOKEN密钥（） */
	public final static String isdkeyToken = "ISDKEY_TOKEN" ;

	/** 应用个人化APDU指令 */
	public final static String appPersionApdu = "APPPERSIONAPDU" ;

	/** 原子任务之间的参数传递 存会话内容 */
	public final static String sessionInfo = "SESSIONINFO" ;

	// session
	// key--持久(跨多个消息触发，需要存以下参数)--------------------------------
	// 还包括se_id,在sessionInfo里有一个备份
	public final static String s_hostrand = "S_HOSTRAND" ;

	/** C-MAC会话密钥 */
	public final static String s_sessionMac = "S_SESSIONMAC" ;

	/** 加密会话密钥 */
	public final static String s_sessionEnc = "S_SESSIONENC" ;

	/** 数据加密会话密钥 */
	public final static String s_sessionDek = "S_SESSIONDEK" ;

	/** 最新ICV 默认为8字节的0x00 */
	public final static String s_icv0 = "S_ICV" ;

	/** 计算MAC的初始值是否动态变化，false 固定为设置值，true 动态变化 (后续计算使用前一次得到的MAC作为初始值 默认) */
	public final static String s_variable = "S_VARIABLE" ;

	/** 安全级别 目前只支持0x00、0x01、0x03三种安全级别 */
	public final static String s_security = "S_SECURITY" ;

	/** 安全通道协议 目前只支持0x01、0x02 */
	public final static String s_protocol = "S_PROTOCOL" ;

	/** 安全策略 目前只支持0x05、0x15 */
	public final static String s_i = "S_I" ;

	/** 主安全域AID */
	public final static String s_isdAid0 = "S_ISDAID" ;

	/** ARA应用AID */
	public final static String ara_Aid = "ARA_AID" ;

	/** 静态密钥DEK */
	public final static String s_kdek = "S_KDEK" ;

	/** 外部认证用到，当前外部认证的安全域AID */
	public final static String s_sdAid0 = "S_SDAID" ;

	//public final static String session_dek_div0 = "SESSION_DEK_DIV" ;

	public final static String session_enc_div = "SESSION_ENC_DIV" ;

	public final static String session_mac_div = "SESSION_MAC_DIV" ;

	public final static String session_flag = "SESSION_FLAG" ;

	public final static String key_ver = "KEY_VER" ;

	/** 跟卡端交互的上一个命令类型 执行一次请求后需要清除的参数，每次有新的终端请求就清空 */
	//public final static String last_Command = "LAST_COMMAND" ;

	/** 原子任务的期望响应码(参数里存的是LV)，每次有新的终端请求就清空 */
	//public final static String last_Command_rCode = "LAST_COMMAND_RCODE" ;

	/** 同步原子任务中需要处理的步骤 hashmap */
	public final static String syncSteps = "SYNCSTEPS" ;

	/** 当前同步步骤 */
	public final static String syncCurrentStep = "SYNCCURRENTSTEP" ;

	/** 原子任务之间的参数传递 外部认证，主机产生的随机数 */
	//public final static String exau_rand = "EXAU_RAND" ;

	public final static String key_version = "KEY_VERSION" ;

	// public final static String isd_key_version = "ISD_KEY_VERSION";
	public final static String key_Identifier = "KEY_IDENTIFIER" ;

	/** 原子任务之间的参数传递 setSEStatus="SET_SE_STATUS" */
	public final static String setSEStatus = "SET_SE_STATUS" ;

	/** 原子任务之间的参数传递 setSDStatus="SET_SD_STATUS" */
	public final static String setSDStatus = "SET_SD_STATUS" ;

	/** 原子任务之间的参数传递 setAPPStatus="SET_APP_STATUS" */
	public final static String setAPPStatus = "SET_APP_STATUS" ;

	/** 原子任务之间的参数传递 缓存GETSTATUS命令的P2参数第B2位参数，根据B2位不同，响应消息解析方式不同 */
	public final static String getstatusP2B2 = "GETSTATUS_P2B2" ;

	// /**卡片ID*/
	// public final static String cardId = "CARDID";

	public final static String s_verifyCode = "S_VERIFYCODE" ;

	/** 应用提供方存储APDU的数组队列 */
	public final static String apTokenApdu = "APTOKENAPDU" ;

	/** 需要提交获取TOKEN的数据单元 */
	public final static String apTokenData = "APTOKENDATA" ;

	/** 流程终止的错误代码 */
	public final static String breakErrorCode = "S_BREAK_ERROR_CODE" ;

	/** 流程终止的错误代码(临时存放) */
	public final static String breakErrorTemp = "S_BREAK_ERROR_TEMP" ;

	/**
	 * GETDATA命令P1P2的list,值是字符串以逗号<br>
	 * 每个值格式：p1p2(2字节)+分隔符(|)+aid(应用AID或安全域AID)
	 */
	public final static String getDataList = "GETDATALIST" ;

	// 71 双芯片卡|||73
	// 移动
	public static final String dapKeyId = "DAP_KEY_ID" ;

	public static final String sencKeyId = "SENC_KEY_ID" ;

	public static final String smacKeyId = "SMAC_KEY_ID" ;

	public static final String sdekKeyId = "SDEK_KEY_ID" ;

	public static final String sKeyVersion = "SKEY_VERSION" ; // 10 双芯片卡|||01 移动

	/** 应用下载所需loadFiles */
	public static final String loadFiles = "DOWN_LOAD_FIELS" ;

	public static final String appNewStatus = "APPNEWSTATUS" ;

	public static final String seNewStatus = "SENEWSTATUS" ;

	public static final String sdNewStatus = "SDNEWSTATUS" ;

	//public static final String cardBatch = "CARDBATCH" ;

	/** 上线时间 **/
	public static final String onlineDate = "ONLINE_DATE" ;

	/** 文件大小 **/
	public static final String fileSize = "FILE_SIZE" ;

	/** 应用LOGO下载地址 **/
	public static final String appLogoUrl = "APP_LOGO_URL" ;

	/** 应用下载地址 **/
	public static final String appAddr = "APP_ADDR" ;

	public static final String Businessapdu = "BUSINESS_APDU" ;

	// session key
	// --持久------------------------------------------------------------------

	// **********************************************************

	public final static String progress = "PROGRESS" ;

	public final static String hasSteps = "HASSTEPS" ;

	public final static String progressed = "PROGRESSED" ;

	public final static String orderId = "ORDERID" ;

	public final static String orderStatus = "ORDERSTATUS" ;

	public final static String businessId = "BUSINESSID" ;

	public final static String category = "CATEGORY" ;

	// TSM服务端应用下载接口 zhenghao
	public final static String sk_id = "SK_ID" ;

	public final static String se_id = "SE_ID" ;

	public final static String sk = "SK" ;

	// 指令会话密钥
	public final static String sk_mac = "SK_MAC" ;

	public final static String SK_ENC = "SK_ENC" ;

	public final static String SK_LASTICV = "SK_LASTICV" ;

	public final static String APDU_NUM = "Apdu_Num" ;

	// 8583协议用到的键值对
	public final static String BITELEMENT_REQUEST = "BIT_ELEMENT_REQUEST" ;// 位元素

	public final static String BITELEMENT_RESPONSE = "BIT_ELEMENT_RESPONSE" ;// 位元素

	public final static String MAINPAN = "MAIN_PAN" ;// 主账号

	public final static String TRADECODE = "TRADE_CODE" ;// 交易处理码

	/**
	 * device TYPE
	 */
	public final static String SERVERTYPECODE = "SERVER_TYPE_CODE" ;// 服务点输入方式码
																	// 可以用来判断
																	// 是客户端直连，还是平台直连？

	public final static String RESPONSECODES = "RESPONSE_CODES" ;// 响应代码 39

	// (个人化)
	public final static String PRIVATEINFO = "PRIVATE_INFO" ;// 私有附加数据

	public final static String PRIVATEINFO_USERNAME = "PRIVATE_INFO_USERNAME" ;// 私有附加数据
																				// 持卡人姓名

	public final static String PRIVATEINFO_IDTYPE = "PRIVATE_INFO_IDTYPE" ;// 私有附加数据
																			// 证件类型

	public final static String PRIVATEINFO_IDNUM = "PRIVATE_INFO_IDNUM" ;// 私有附加数据
																			// 证件号码

	public final static String PRIVATEINFO_PHONENUM = "PRIVATE_INFO_PHONENUM" ;// 私有附加数据
																				// 证件类型

	public final static String PRIVATEINFO_RESERVED = "PRIVATE_INFO_RESERVED" ;// 私有附加数据
																				// 证件号码

	/*** 单位号（由通卡分配16位） */
	public final static String SERVERQUEST = "SERVERQUEST" ;// 服务请求域

	public final static String SERVERQUESTCODE = "SERVERQUEST_CODE" ;// 服务请求域
																		// 服务请求代码
																		// 通卡分配的单位号
																		// 如果是平台接入，则为机构的单位号

	public final static String SERVERQUESTPROCESS = "SERVERQUEST_PROCESS" ;// 服务请求域
																			// 服务请求过程标识

	public final static String SERVERQUESTPARAM = "SERVERQUEST_PARAM" ;// 服务请求域
																		// 服务请求参数

	public final static String SERVERQUESTATS = "SERVERQUEST_ATS" ;// 服务请求域 ATS

	public final static String SERVERQUESTAID = "SERVERQUEST_AID" ;// 服务请求域
																	// 应用AID

	public final static String SERVERQUESTDEVICE = "SERVERQUEST_DEVICE" ;// 服务请求域
																			// 设备标识

	public final static String SERVERQUESTMOBILETYPE = "SERVERQUEST_MOBILETYPE" ;// 服务请求域
																					// 手机型号

	public final static String SERVERRESPONSE = "SERVER_RESPONSE" ;// 服务响应数据域

	public final static String ICRESPONSE = "SE_RESPONSE" ;// IC卡响应数据域

	public final static String REQTYPE = "ReqType" ;// 交易请求类型

	public final static String RECORNUM = "RecordNum" ;// 个人化指令条数

	public final static String PACKNUM = "PackNum" ;// 交互数据包数

	public final static String RESERVED = "Reserved" ;// 保留域

	public final static String REQTYPEID = "ReqTypeId" ;// 指令请求类型

	public final static String REQUNUM = "RequNum" ;// 请求索引标识

	public final static String RESPNUM = "RespNum" ;// 对应请求索引标识

	public final static String UID = "UID" ;

	public final static String UNITNUMBER = "UNIT_NUMBER" ;

	public final static String APKINSTALLTAG = "APKINSTALL_TAG" ;

	public final static String APKID = "APK_ID" ;

	public final static String APKNAME = "APK_NAME" ;

	public final static String APKVERSION = "APK_ VERSION" ;

	public final static String APKDES = "APK_DES" ;

	public final static String APKLOG = "APK_LOG" ;

	public final static String CARDAPDU = "CardApdu" ;// 个人化的APDU指令\

	public final static String ISREMOTE = "IsRemote" ;

	/** 虚拟交易序列号 */
	public final static String tradeNos = "TRADE_NOS" ;

	public final static String appAid = "APP_AID" ;

	public final static String scope = "SP_CODE" ;

	public final static String validDate = "VALID_DATE" ;

	public final static String appRamSpace = "APP_RAM_SPACE" ;

	public final static String appNvmSpace = "APP_NVM_SPACE" ;

	public final static String privilege = "PRIVILEGE" ;

	public final static String appSecurityLevel = "APP_SECURITY_LEVEL" ;

	public final static String chkOrgCode = "CHK_ORG_CODE" ;

	public final static String appIcoUrl = "APP_ICO_URL" ;

	public final static String appUrl = "APP_URL" ;

	public final static String appApkUrl = "appApkUrl" ;

	public final static String pushDate = "PUSH_DATE" ;

	public final static String offlineDate = "OFFLINE_DATE" ;

	public final static String isShow = "IS_SHOW" ;

	public final static String partnerOrgCode = "partnerOrgCode" ;

	public final static String appQueryResult = "appQueryResult" ;
	
	public final static String checkSeidResult = "checkSeidResult" ;

}
