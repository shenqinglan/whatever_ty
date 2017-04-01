// Copyright (C) 2012 WHTY
package com.whty.euicc.tasks.constant;

/**
 * GP常量表
 * 
 * @author Administrator 80F24000 024F00
 */
public class GPConstant {

public static final String LEVEL00 = "00";
	
	public static final String LEVEL01 = "01";
	
	public static final String LEVEL03 = "03";
	// **************************************2012-06-07 by
	// cy*****************************************************************
	/** 0表示第一行业；1表示其他行业 CLA位b7 */
	public final static byte cla1_Interindustry = 0;
	public final static byte cla2_Interindustry = 1;
	// 第一行业CLA [b8:命令类型][b7:0][b6b5:00][b4b3:安全信息][b2b1：通道]
	/**
	 * Command defined in ISO/IEC 7816<BR>
	 * CLA位b8为0
	 */
	public final static String cla1_command0 = "0";
	/**
	 * GP command<BR>
	 * CLA位b8为1
	 */
	public final static String cla1_command1 = "1";

	/**
	 * No secure messaging<BR>
	 * CLA位b4b3为00
	 */
	public final static String cla1_secure0 = "00";
	/**
	 * Secure messaging - GlobalPlatform proprietary<BR>
	 * CLA位b4b3为01
	 */
	public final static String cla1_secure1 = "01";
	/**
	 * Secure messaging - ISO/IEC 7816 standard,command header<b> not processed
	 * (no C-MAC)</b><BR>
	 * CLA位b4b3为10
	 */
	public final static String cla1_secure2 = "10";
	/**
	 * Secure messaging - ISO/IEC 7816 standard,command header<b> authenticated
	 * (C-MAC)</b><BR>
	 * CLA位b4b3为11
	 */
	public final static String cla1_secure3 = "11";

	// 其他行业CLA [b8:命令类型][b7:1][b6:安全信息][b5:0][b4b3b2b1:通道]
	/**
	 * Command defined in ISO/IEC 7816<BR>
	 * CLA位b8为0
	 */
	public final static String cla2_command0 = "0";
	/**
	 * GP command<BR>
	 * CLA位b8为1
	 */
	public final static String cla2_command1 = "1";
	/**
	 * No secure messaging<BR>
	 * CLA位b6为0
	 */
	public final static String cla2_secure0 = "0";
	/**
	 * Secure messaging - ISO/IEC 7816 or GlobalPlatform proprietary<BR>
	 * CLA位b6为1
	 */
	public final static String cla2_secure1 = "1";

	// 命令类型INS
	/** DELETE="E4" */
	public final static String DELETE = "E4";
	/** GETDATA0="CA" */
	public final static String GETDATA0 = "CA"; // 偶数
	/** GETDATA1="CB" */
	public final static String GETDATA1 = "CB"; // 奇数
	/** GETSTATUS="F2" */
	public final static String GETSTATUS = "F2";
	/** INSTALL="E6" */
	public final static String INSTALL = "E6";
	/** LOAD="E8" */
	public final static String LOAD = "E8";
	/** MANAGECHANNEL="70" */
	public final static String MANAGECHANNEL = "70";
	/** PUTKEY="D8" */
	public final static String PUTKEY = "D8";
	/** SELECT="A4" */
	public final static String SELECT = "A4";
	/** SETSTATUS="F0" */
	public final static String SETSTATUS = "F0";
	/** STOREDATA="E2" */
	public final static String STOREDATA = "E2";
	/** INITIALIZEUPDATE="50" */
	public final static String INITIALIZEUPDATE = "50";
	/** EXTERNALAUTHENTICATE="82" */
	public final static String EXTERNALAUTHENTICATE = "82";
	/** 管理安全环境命令=“22” */
	public final static String MANAGESECURITYENVIRONMENT = "22";
	/** PERFORMSECURITYOPERATION="2A" */
	public final static String PERFORMSECURITYOPERATION = "2A";
	/** GETCHALLENGE="84" */
	public final static String GETCHALLENGE = "84";
	/** INTERNALAUTHENTICATE="88" */
	public final static String INTERNALAUTHENTICATE = "88";
	
	/** CFFLAG="CF01" */
	public final static String CFFLAG = "CF01";

	/** VERIFYPIN="20" */
	public final static String VERIFYPIN = "20";

	/** 普通子TLV串中长度L的占的字符个数 2 */
	public static final int SUB_TLV_LEN = 2;
	/** 节点子TLV串中长度L的占的字符个数 4 */
	public static final int NODE_TLV_LEN = 4;
	/** 每个APDU指令的LV串中长度L占的字符个数 4 */
	public static final int APDU_TLV_LEN = 4;
	/** 空字符串 */
	public static final String EMPTY_STR = "";
	/** 返回结果 正确代码 0000 */
	public static final String SUCCESS_CODE = "0000";

	public static final String load_file_privilege = "00";
	// 权限第2和第3字节默认值
	public static final String defaultAPPprivilege23 = "0000";
	public static final String defaultSSDprivilege23 = "8000";
	public static final String defaultISDprivilege23 = "FE80";

	// 应用状态
	public static final String app_installed = "03";
	public static final String app_selectable = "07";
	public static final String app_specific_state = "0F";
	public static final String app_locked_start = "1000";
	// 安全域状态
	public static final String sd_installed = "03";
	public static final String sd_selectable = "07";
	public static final String sd_personalized = "0F";
	public static final String sd_lock_start = "1000";
	// 卡片状态
	public static final String card_op_ready = "01";
	public static final String card_installed = "07";
	public static final String card_secured = "0F";
	public static final String card_locked = "7F";
	public static final String card_terminated = "FF";
	/** CAP文件里是否含有C4 tag */
	public static final boolean c4_in_loadfile = true;
	/**
	 *  “i” = ‘05’，显式安全通道模式，C-MAC作用在修改后的APDU指令上，ICV为0，不对ICV进行加密，使用3个安全通道密钥<br>
	 *  “i” = ‘15’，显式安全通道模式，C-MAC作用在修改后的APDU指令上，ICV为0，对ICV进行加密，使用3个安全通道密钥<br>
	 * 选项‘15’是对‘05’的加强，因此推荐实现选项‘15’。
	 */
	public static final String scp01_i = "05"; // "i" = '05':"i" = '15':
	/**
	 *  “i” = ‘15’，显式发起模式，修改后APDU的C-MAC，ICV设置为0，为C_MAC会话加密ICV，3个安全通道密钥，
	 * 未指定卡Challenge生成方法，无R-MAC。<br>
	 * “i” =
	 * ‘1A’，隐式发起模式，未修改的APDU的C-MAC，ICV设置为AID的MAC值，为C_MAC会话加密ICV，1个安全通道基本密钥，无R
	 * -MAC。<br>
	 * “i” =
	 * ‘55’，显式发起模式，修改后APDU的C-MAC，ICV设置为0，为C-MAC会话加密ICV，3个安全通道密钥，众所周知的伪随机数算法
	 * （卡Challenge），无R-MAC<br>
	 */
	public static final String scp02_i = "15"; // "i" = '15':"i" = '1A':"i" =
												// '55':
	public static final String scp02 = "02";
	public static final String scp01 = "01";
	public static final String scp10 = "10";
	/** 通道10的安全模式 */
	public static final String scp10_i = "02";
	/**
	 * ‘81’: External (Off-Card Entity) Authentication only;‘C1’: External and
	 * Internal (Mutual) Authentication
	 */
	public static final String scp10_manageSecurityEnvironment_P1 = "81";
	/**
	 * ‘A4’: Authentication: no certificate verification will be performed by
	 * the card;‘B6’: Digital signature: certificate verification will be
	 * performed by the card
	 */
	public static final String scp10_manageSecurityEnvironment_P2 = "A4";
	/**
	 * ‘AE’: non self descriptive card verifiable certificate (only the
	 * concatenated value fields are certified) ‘BE’: self descriptive card
	 * verifiable certificate (the TLV data elements are certified)
	 */
	public static final String scp10_PSOForVerifyCertificate_P2 = "AE";
	// GETDATA命令的tag
	/** 获得卡片空间大小TAG,卡片内容管理中可用的扩展卡片资源信息 */
	// Extended Card Resources Information available for Card Content Management

	/** 暂时在原子任务执行之前根据不同的卡片放不同的值 */
	public static final String getCardSpaceTag = "FF20";
	public static final String getCardSpaceTag1 = "FF21";

	public static final String getSdSpaceTag = "2F00";
	public static final String getCardSEID = "0042"; // ??????

	// 设置状态命令类别
	/** 应用个人化 =00 */
	public static final String setStatusAppPerson = "00";
	/** 应用锁定==01 */
	public static final String setStatusAppLock = "01";
	/** 应用解锁==02 */
	public static final String setStatusAppUnLock = "02";
	/** 安全域锁定==03 */
	public static final String setStatusSDLock = "03";
	/** 安全域个人化 */
	public static final String setStatusSDPerson = "08";
	/** 安全域解锁==04 */
	public static final String setStatusSDUnLock = "04";
	/** 卡片锁定==05 */
	public static final String setStatusSELock = "05";
	/** 卡片解锁==06 */
	public static final String setStatusSEUnLock = "06";
	/** 卡片终止==07 */
	public static final String setStatusSETerminate = "07";
	/** 卡片安装状态 */
	public static final String setStatusSEInstall = "09";
	/** 卡片正常状态 */
	public static final String setStatusSESecure = "10";
	/** putKey的KEYTYPE */
	public static final String putKeyType = "80";
	/** 命令中密钥校验值长度L为3，即取实际计算校验值结果的最高3字节作为V，目前移动、公司卡片都是用3 */
	public static final int putKeyKCVLen = 3;
	// *********************************************************************

	// 主安全域，token的密钥版本和索引
	public static final String tokenKeyVersion = "70";
	public static final String tokenKeyId = "01";
	public static final String dapKeyVersion = "73"; // 71 双芯片卡|||73 移动
	public static final String dapKeyId = "01";
	public static final String sencKeyId = "01";
	public static final String smacKeyId = "02";
	public static final String sdekKeyId = "03";
	public static final String sKeyVersion = "01"; // 10 双芯片卡|||01 移动

	/** apdu响应码 */
	public static final String responseApduSuccess = "9000";
	/** 外边认证算法2包含字符 */
	public static final String ea2String = "|swp-sd|";
	/** 主安全域建立安全通道的密钥，默认版本号 */
	// public static final String isd_default_keyVersion="00";

	public static final String icvRand = "0000000000000000";
	public static final String skeyicv = "000000000000000000000000";
	public static final String maciserror = "Mac is error";
	public static final String securityleveliserror = "Security level is error";
	public static final String checkcardfail = "Check card cryptogram fail";

	public static final String defaultSdKeys = "404142434445464748494A4B4C4D4E4F";
	public static final String defaultDapKeys = "909192939495969798999A9B9C9D9E9F";
	
	public static final String defaultIsdAid = "A0000001510000";
	
//	public static final String keyTokene = "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010001";
	//public static final String keyTokenn = "C852223004951C6EF6A2DA554FC6FF4B01CE2E6840E4BAB466CD119022D0336C340A2E60D89CD28AAC6BAE3EDA2047B556F16C1495ECBD5FFCA87F6129040EA5C9EDCA940A9942260137A51D11466F111679F2C690F5AFDC6F6C82C34D5A00F51C22E5F4974673601DAECF167A5133FA2CBF1C76D085A3CE64867D6A208F0767";
//	public static final String keyTokenn = "E0651C7F333696CA88D02C65AAD4A0989AD9DEC467C13959070823182ABF739AFE95FA597B1CBFE93D793971CDAF19D0EEF8D503899C12FCB8B24978351EE942B50CF6FC75A8CD481D2BBC58852D35FF2D4072CE7878E8553F318D24B05E3120EB9F30CCCED0F2EF1D3D33FE5CF70B8BCE0E096D414B0F966C6E22C40BC5DE17";
}
