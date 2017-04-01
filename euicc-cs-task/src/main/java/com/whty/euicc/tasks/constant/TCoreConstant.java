package com.whty.euicc.tasks.constant;

public abstract class TCoreConstant {

	public final static String COMMAND_ID = "00";

	public final static String BOOLEAN = "01";

	public final static String INTEGER = "02";

	public final static String OCTET_STRING = "04";

	public final static String PRINTABLE_STRING = "12";

	public final static String UTF8_STRING = "19";

	public final static String SEQUENCE = "30";

	public final static String TCORE_ID = "43";

	public final static String OBJECT_ID = "41";

	public final static String TCORE_SD_STATE = "53";

	public final static String TCORE_APP_STATE = "55";

	public final static String OPERATION_TAG = "60";

	public final static String ENCRYPTION_PARAMS = "66";

	public final static String IDVERIFICATION_PARAMS = "68";

	public final static String CRYPTOGRAPHIC_DATA = "6B";

	public final static String CONTENT = "7B";

	public final static String SECURITY_TAG = "7A";

	public final static String INSTALL_TA_TAG = "7F41";

	public final static String UNINSTALL_TA_TAG = "7F42";

	public final static String INSTALL_SD_TAG = "7F4A";

	public final static String UNINSTALL_SD_TAG = "7F4B";

	public final static String OPEN_SESSION_ID = "0000";

	public final static String CLOSE_SESSION_ID = "FFFF";

	/**
	 * closeSession
	 */
	public static final String CLOSE_SESSION = "C1";

	/**
	 * openSession
	 */
	public static final String OPEN_SESSION = "O1";

	/**
	 * 成功
	 */
	public static final String SUCCESS = "00000000";
	/**
	 * 失败
	 */
	public static final String FAIL = "FFFF";
	/**
	 * 删除sd
	 */
	public static final String DELETE_SD = "D1";
	/**
	 * 删除cap
	 */
	public static final String DELETE_CAP = "D2";
	/**
	 * 安装sd
	 */
	public static final String INSTALL_SD = "I1";
	/**
	 * 安装cap
	 */
	public static final String INSTALL_CAP = "I2";

	// 默认参数值
	public interface DefaultValue {

		public final static String SecurityVersion = "00000001";

		public final static String OperationVersion = "10000000";

		public final static String NullObject = "0500";

		public final static String ContentParam = "7F0000";
	}

	// 生命周期状态
	public interface LifeCycleState {

		public final static String Blocked = "80";

		public final static String AccessibleRestricted = "08";

		public final static String AccessibleActive = "09";
	}

	/**
	 * app下载相关标识
	 */
	public interface AppDownloadFlag {
		// 下载成功
		public static final String download_success = "0";
		// 下载失败
		public static final String download_fail = "1";
		// 下载标识 1
		public static final String download_type = "1";
	}

}
