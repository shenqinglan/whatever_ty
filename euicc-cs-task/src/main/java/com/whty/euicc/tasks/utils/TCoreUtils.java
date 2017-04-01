package com.whty.euicc.tasks.utils;

import com.whty.euicc.common.utils.StringUtils;
import com.whty.euicc.tasks.constant.TCoreConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TCore指令工具类
 * 
 * @author Ocea
 * 
 */
public class TCoreUtils {

	private static final Logger logger = LoggerFactory
			.getLogger(TCoreUtils.class);

	/**
	 * 获得长度L编码<br>
	 * 0 - 127 : '00' - '7F'<br>
	 * 128 - 2147483647 : '8180' - '847FFFFFFF'<br>
	 * 
	 * @param strLength
	 *            注意，这里是字节长度，不是字符长度。调用前要算出字节长度
	 * @return
	 */
	public static String getLength4TCore(int strLength) {
		String len = "";
		String hexLen = Integer.toHexString(strLength).toUpperCase();
		logger.debug("GetHexLength : {}", hexLen);
		int hexLength = hexLen.length();
		if (hexLength % 2 == 1) {
			// 长度为奇数时，高位补0
			hexLen = "0" + hexLen;
			hexLength++;
		}
		hexLength = hexLength / 2;
		if (strLength <= 127) {
			// short类型表示
			len = hexLen;
		} else {
			// long类型表示，字节长度不会超过0x8，而int类型字节长度不超过0x4，故数值不会溢出
			len = "8" + String.valueOf(hexLength) + hexLen;
		}
		logger.debug("GetLengthForTCore : {}", len);
		return len;
	}

	/**
	 * 获得基本指令<br>
	 * 依照TLV格式封装指令
	 * 
	 * @param str
	 * @param type
	 * @return
	 */
	public static String getBaseCommand4TCore(String str, String type) {
		String commandStr = "";
		// 拼接TLV格式指令
		commandStr = type + getLength4TCore(str.length() / 2) + str;
		logger.debug("GetTypeCommand4TCore : {}", commandStr);
		return commandStr;
	}

	/**
	 * 打开通道指令
	 * 
	 * @param targetSdAid
	 * @return
	 */
	public static String openSession4TCore(String targetSdAid) {
		// 拼接报文
		String commandStr = "";
		// Version
		commandStr = commandStr
				+ getBaseCommand4TCore(
						TCoreConstant.DefaultValue.SecurityVersion,
						TCoreConstant.INTEGER);
		// TargetSD
		commandStr = commandStr
				+ getBaseCommand4TCore(targetSdAid, TCoreConstant.TCORE_ID);
		// OpenSession
		commandStr = getBaseCommand4TCore(commandStr, TCoreConstant.SEQUENCE);
		logger.debug("OpenSession_Command : {}", commandStr);
		return commandStr;
	}

	/**
	 * 关闭通道指令
	 * 
	 * @param targetSdAid
	 * @return
	 */
	public static String closeSession4TCore(String targetSdAid) {
		// 拼接报文
		String commandStr = "";
		// Version
		commandStr = commandStr
				+ getBaseCommand4TCore(
						TCoreConstant.DefaultValue.SecurityVersion,
						TCoreConstant.INTEGER);
		// TargetSD
		commandStr = commandStr
				+ getBaseCommand4TCore(targetSdAid, TCoreConstant.TCORE_ID);
		// OpenSession
		commandStr = getBaseCommand4TCore(commandStr, TCoreConstant.SEQUENCE);
		logger.debug("OpenSession_Command : {}", commandStr);
		return commandStr;
	}

	/**
	 * 封装完整指令
	 * 
	 * @param command
	 * @return
	 */
	public static String getCommand4TCore(String command) {
		// 封装操作层
		command = operationLayer4TCore(command);
		// 封装安全层
		command = securityLayer4TCore(command);
		return command;
	}

	/**
	 * 封装安全层指令
	 * 
	 * @param command
	 * @return
	 */
	public static String securityLayer4TCore(String command) {
		// SecurityLayer Start
		String securityCmdStr = "";
		// Version
		securityCmdStr = securityCmdStr
				+ getBaseCommand4TCore(
						TCoreConstant.DefaultValue.SecurityVersion,
						TCoreConstant.INTEGER);
		// Content
		securityCmdStr = securityCmdStr
				+ getBaseCommand4TCore(TCoreConstant.DefaultValue.ContentParam
						+ command, TCoreConstant.CONTENT);
		// SecurityLayer End
		securityCmdStr = getBaseCommand4TCore(securityCmdStr,
				TCoreConstant.SECURITY_TAG);
		return securityCmdStr;
	}

	/**
	 * 封装操作层指令
	 * 
	 * @param command
	 * @return
	 */
	public static String operationLayer4TCore(String command) {
		// OperationLayer Start
		String operationCmdStr = "";
		// Version
		operationCmdStr = operationCmdStr
				+ getBaseCommand4TCore(
						TCoreConstant.DefaultValue.OperationVersion,
						TCoreConstant.INTEGER);
		// Command
		operationCmdStr = operationCmdStr + command;
		// OperationLayer End
		operationCmdStr = getBaseCommand4TCore(operationCmdStr,
				TCoreConstant.OPERATION_TAG);
		return operationCmdStr;
	}

	/**
	 * TCore应用安装指令
	 * 
	 * @param appAid
	 * @param targetSdAid
	 * @param loadFileDates
	 * @param encryptionParams
	 * @param idVerificationParams
	 * @return
	 */
	public static String installTA4TCore(String appAid, String targetSdAid,
			String loadFileDates, String encryptionParams,
			String idVerificationParams) {
		// InsatllTA Start
		String installTACmdStr = "";
		// Id
		installTACmdStr = installTACmdStr
				+ getBaseCommand4TCore(appAid, TCoreConstant.TCORE_ID);
		// TargetSD
		installTACmdStr = installTACmdStr
				+ getBaseCommand4TCore(targetSdAid, TCoreConstant.TCORE_ID);
		// InitialState
		installTACmdStr = installTACmdStr
				+ getBaseCommand4TCore(
						TCoreConstant.LifeCycleState.AccessibleActive,
						TCoreConstant.TCORE_APP_STATE);
		// ApplicationFile
		installTACmdStr = installTACmdStr
				+ getBaseCommand4TCore(loadFileDates,
						TCoreConstant.OCTET_STRING);
		// EncryptionParams
		if (StringUtils.isNotNull(encryptionParams)) {
			installTACmdStr = installTACmdStr
					+ getBaseCommand4TCore(encryptionParams,
							TCoreConstant.ENCRYPTION_PARAMS);
		} else {
			installTACmdStr = installTACmdStr
					+ TCoreConstant.DefaultValue.NullObject;
		}
		// IdVerificationParams
		if (StringUtils.isNotNull(idVerificationParams)) {
			installTACmdStr = installTACmdStr
					+ getBaseCommand4TCore(idVerificationParams,
							TCoreConstant.IDVERIFICATION_PARAMS);
		} else {
			installTACmdStr = installTACmdStr
					+ TCoreConstant.DefaultValue.NullObject;
		}
		// InstallTA End
		installTACmdStr = getBaseCommand4TCore(installTACmdStr,
				TCoreConstant.INSTALL_TA_TAG);
		return installTACmdStr;
	}

	/**
	 * TCore应用卸载指令
	 * 
	 * @param appAid
	 * @return
	 */
	public static String uninstallTA4TCore(String appAid) {
		// UninstallTA Start
		String uninstallTACmdStr = "";
		// Id
		uninstallTACmdStr = uninstallTACmdStr
				+ getBaseCommand4TCore(appAid, TCoreConstant.TCORE_ID);
		// UninstallTA End
		uninstallTACmdStr = getBaseCommand4TCore(uninstallTACmdStr,
				TCoreConstant.UNINSTALL_TA_TAG);
		return uninstallTACmdStr;
	}

	/**
	 * TCore创建安全域指令
	 * 
	 * @param sdAid
	 * @param targetSdAid
	 * @param privileges
	 * @param authorityName
	 * @param authorityInfoUrl
	 * @param cryptographicData
	 * @param idVerificationParams
	 * @return
	 */
	public static String installSD4TCore(String sdAid, String targetSdAid,
			String privileges, String authorityName, String authorityInfoUrl,
			String cryptographicData, String idVerificationParams) {
		// InstallSD Start
		String installSDCmdStr = "";
		// Id
		installSDCmdStr = installSDCmdStr
				+ getBaseCommand4TCore(sdAid, TCoreConstant.TCORE_ID);
		// TargetSD
		installSDCmdStr = installSDCmdStr
				+ getBaseCommand4TCore(targetSdAid, TCoreConstant.TCORE_ID);
		// InitialState
		installSDCmdStr = installSDCmdStr
				+ getBaseCommand4TCore(
						TCoreConstant.LifeCycleState.AccessibleActive,
						TCoreConstant.TCORE_SD_STATE);
		// Privileges
		installSDCmdStr = installSDCmdStr
				+ getBaseCommand4TCore(privileges, TCoreConstant.OCTET_STRING);
		// AuthorityName
		if (StringUtils.isNotNull(authorityName)) {
			installSDCmdStr = installSDCmdStr
					+ getBaseCommand4TCore(authorityName,
							TCoreConstant.UTF8_STRING);
		} else {
			installSDCmdStr = installSDCmdStr
					+ TCoreConstant.DefaultValue.NullObject;
		}
		// AuthorityInfoUrl
		if (StringUtils.isNotNull(authorityInfoUrl)) {
			installSDCmdStr = installSDCmdStr
					+ getBaseCommand4TCore(authorityInfoUrl,
							TCoreConstant.UTF8_STRING);
		} else {
			installSDCmdStr = installSDCmdStr
					+ TCoreConstant.DefaultValue.NullObject;
		}
		// CryptographicData
		if (StringUtils.isNotNull(cryptographicData)) {
			installSDCmdStr = installSDCmdStr
					+ getBaseCommand4TCore(cryptographicData,
							TCoreConstant.CRYPTOGRAPHIC_DATA);
		} else {
			installSDCmdStr = installSDCmdStr
					+ TCoreConstant.DefaultValue.NullObject;
		}
		// IdVerificationParams
		if (StringUtils.isNotNull(idVerificationParams)) {
			installSDCmdStr = installSDCmdStr
					+ getBaseCommand4TCore(idVerificationParams,
							TCoreConstant.IDVERIFICATION_PARAMS);
		} else {
			installSDCmdStr = installSDCmdStr
					+ TCoreConstant.DefaultValue.NullObject;
		}
		// InstallSD End
		installSDCmdStr = getBaseCommand4TCore(installSDCmdStr,
				TCoreConstant.INSTALL_SD_TAG);
		return installSDCmdStr;
	}

	/**
	 * TCore删除辅助安全域指令
	 * 
	 * @param sdAid
	 * @return
	 */
	public static String uninstallSD4TCore(String sdAid) {
		// UninstallSD Start
		String uninstallSDCmdStr = "";
		// Id
		uninstallSDCmdStr = uninstallSDCmdStr
				+ getBaseCommand4TCore(sdAid, TCoreConstant.TCORE_ID);
		// UninstallSD End
		uninstallSDCmdStr = getBaseCommand4TCore(uninstallSDCmdStr,
				TCoreConstant.UNINSTALL_SD_TAG);
		return uninstallSDCmdStr;
	}
}
