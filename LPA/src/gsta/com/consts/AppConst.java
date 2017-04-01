package gsta.com.consts;

import android.R.integer;

public final class AppConst {

	// 证书来源
	public final static String SSL_CERT_SOURCE_DEFAULT = "DEFAULT";
	public final static String SSL_CERT_SOURCE_CUSTOM = "CUSTOM";
	public final static String SSL_CERT_SOURCE_DEBUG = "DEBUG";

	public static final String CAPACKAGENAME = "caPackageName";
	public static final String CASiGNATURE = "caSignature";
	public static final String ACTION_TYPE_KEY = "actionType";
	public static final int TA_INSTALL = 0;
	public static final int TA_DELETE = 1;
	public static final int TEE_INIT = 2;

	/** 01：下载TA **/
	public static final String DOWNLOAD_OPER = "01";
	/** 02：删除TA **/
	public static final String DELETE_OPER = "02";
	/** 03：更新TA **/
	public static final String UPDATE_OPER = "03";
	/** 00：结束TA **/
	public static final String END_OPER = "00";

	public static final String TEEBEAN_KEY = "teeBean";

	public static final byte[] ROOT_UUID = { (byte) 0x22, (byte) 0x22,
			(byte) 0x22, (byte) 0x22, (byte) 0x33, (byte) 0x33, (byte) 0x33,
			(byte) 0x33, (byte) 0x44, (byte) 0x44, (byte) 0x44, (byte) 0x44,
			(byte) 0x44, (byte) 0x44, (byte) 0x44, (byte) 0x44 };

	/** 执行成功标识 */
	public static final String SUCCESS_FLAG = "00000000";

	/** 选择应用AID */
	public static final String AID = "11223344556677";

	/** 获取keyId指令 */
	public static final String GET_KEYID = "8002000000";

	/** 获取Sig(Rand1)指令 */
	public static final String GET_SIG_RAND_1 = "8003000010";

	/** 发送Crypt(Rand2)指令 */
	public static final String POST_CRYPT_RAND_2 = "8004000080";

	/** 获取Crypt(Rand3)指令 */
	public static final String GET_CRYPT_RAND_3 = "8004010080";
	
	/** 使usim卡回复到8002之前的状态*/
	public static final String CHANGE_STATE = "8006010100";

	/** 执行Tee初始化指令 */
	public static final String TEE_INIT_APDU = "77190204010000003011020101600c0204010000007f7003020102";
	
	/** 判断初始化是否指令 */
	public static final String TEE_ISINIT_APDU = "77190204010000003011020101600c0204010000007f7003020100";
	
	public static String SEID = "";
	/** 算法的類型*/
	public static final int SM2=0;
	public static final int RSA1024=1;
	public static final int RSA2048=2;
	public static final int RSA=3;
	
	/*区分命令是否带mac*/
	public static final int NOMAC=0;
	public static final int HASMAC=1;
	
	
	public static final String SUCCESS = "9000";
	
	/*公共*/
	public static final String CHANNLE_ERROR = "6a88";
	public static final String NOAPP_ERROR = "6a82";
	public static final String INPUTLEN_ERROR = "6700";
	public static final String MAC_ERROR = "6988";
	public static final String INPUTENC_ERROR = "6f00";
	public static final String AUTH_ERROR = "63cx";	
	public static final String NOSPACE_ERROR = "6a84";
	public static final String SAFESTATE_ERROR = "6982";
	public static final String APPLOCK_ERROR = "6a81";
	public static final String NORAND_ERROR = "6984";
	
	
	/*国密*/
	public static final String GUOMI_NOSPACE_ERROR = "6a84";
	public static final String GUOMI_UIDLEN_ERROR = "6601";
	public static final String GUOMI_CONTAINERID_ERROR = "6602";
	public static final String GUOMI_UNOTEXIST_ERROR = "6a82";
	public static final String GUOMI_CONTANTNOTEXIST_ERROR = "6a83";
	public static final String GUOMI_KEYPAIRNOTEXIST_ERROR = "6603";
	public static final String GUOMI_CERNOTEXIST_ERROR = "6604";
	public static final String GUOMI_ASYTYPE_ERROR = "6605";
	public static final String GUOMI_SIGEN_ERROR = "6300";
	public static final String GUOMI_DENCRY_ERROR = "6f00";
	public static final String GUOMI_PINLOCK_ERROR = "6983";
	public static final String GUOMI_ULOCK_ERROR = "6a81";
	public static final String GUOMI_AUTY_ERROR = "63cx";
	
	/*RSA*/
	public static final String RSA_NOSUPPORT_ERROR = "6981";
	public static final String RSA_NOSPACE_ERROR = "6a84";
	public static final String RSA_UIDLEN_ERROR = "6601";
	public static final String RSA_CONTAINERID_ERROR = "6602";
	public static final String RSAI_UNOTEXIST_ERROR = "6a82";
	public static final String RSA_CONTANTNOTEXIST_ERROR = "6a83";
	public static final String RSA_KEYPAIRNOTEXIST_ERROR = "6603";
	public static final String RSA_CERNOTEXIST_ERROR = "6604";
	public static final String RSA_ASYTYPE_ERROR = "6605";
	public static final String RSA_SIGEN_ERROR = "6300";
	public static final String RSA_DENCRY_ERROR = "6f00";
	public static final String RSA_PINLOCK_ERROR = "6983";
	public static final String RSA_ULOCK_ERROR = "6a81";
	public static final String RSA_AUTY_ERROR = "63cx";
	
	/*单向存储*/
	public static final String SIGLE_DATALOCK_ERROR = "6901";
	public static final String SIGLE_WRITEDATA_ERROR = "6903";
	public static final String SIGLE_DATANOTEXSIT_ERROR = "6904";
	public static final String SIGLE_DATAIDLEN_ERROR = "6906";
	public static final String SIGLE_COMMPARE_ERROR = "69ax";

	
}
