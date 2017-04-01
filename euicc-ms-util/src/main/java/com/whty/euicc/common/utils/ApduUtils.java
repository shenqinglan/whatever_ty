package com.whty.euicc.common.utils;

import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * apdu工具类
 *
 * @author dengzm
 * @since 2014-11-07
 *
 */
public class ApduUtils {

	private static final Logger logger = LoggerFactory
			.getLogger(ApduUtils.class);

	/**
	 * 复位指令 与手机端约定的指令
	 *
	 * @return
	 */
	public static String reset() {
		return "FFEEDDCC";
	}

	/**
	 * GP
	 *
	 * @param cla
	 * @param p1
	 *            02:For load 0C:For install
	 * @param data
	 * @return
	 */
	public static String install(String cla, String p1, String data) {
		return madeCommandMsg(cla, GPConstant.INSTALL, p1, "00", data);
	}

	public static String getApduWithLength(String data) {
		if (StringUtils.isNotNull(data)) {
			StringBuilder apdu = new StringBuilder("");
			apdu.append(
					Converts.intToHex(data.length() / 2, GPConstant.SUB_TLV_LEN))
					.append(data);
			return apdu.toString();
		} else {
			return "00";
		}
	}

	/**
	 * GP 11.9
	 *
	 * @param cla
	 *            '00' - '03' or '40' - '4F'
	 * @param data
	 * @return
	 */
	public static String select(String data) {
		/**
		 * p1 04 Select by name GP 11.9.2.1 p2 00 First or only occurrence GP
		 * 11.9.2.2
		 */
		return madeCommandMsg("00", GPConstant.SELECT, "04", "00", data);
	}

	/**
	 * GP 11.10
	 *
	 * @param cla
	 * @param data
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static String setStatus(String data, String p1, String p2) {
		return madeCommandMsg("80", GPConstant.SETSTATUS, p1, p2, data);
	}

	/**
	 * GP 11.10.2.1 40:applications, including Security Domains
	 *
	 * @param cla
	 * @param data
	 * @return
	 */
	public static String setAppOrSdStatus(String data, String p2) {
		return setStatus(data, "40", p2);
	}

	/**
	 * GP 11.10.2.1 80: Issuer Security Domain
	 *
	 * @param cla
	 * @param data
	 * @return
	 */
	public static String setIsdStatus(String data, String p2) {
		return setStatus(data, "80", p2);
	}

	/**
	 * GP 11.10.2.1
	 *
	 * @return
	 */
	public static String setIsdAndItsApp(String data, String p2) {
		return setStatus(data, "6F", p2);
	}

	/**
	 * GP 9.8
	 *
	 * @param cla
	 * @param data
	 * @param p2
	 * @return
	 */
	public static String putkey(String data, String p1, String p2) {
		/**
		 * GP9.8.2.1 p1:00 值‘00’表示一个新的密钥或一组密钥将被加入（新的密钥版本号是在命令消息的数据字段中指示的）。
		 */
		return madeCommandMsg("80", GPConstant.PUTKEY, p1, p2, data);
	}

	/**
	 * GP 11.4
	 *
	 * @param cla
	 * @param data
	 * @param p1
	 * @return
	 */
	public static String getStatus(String data, String p1) {
		/**
		 * GP 9.4.2.3 tag‘4F’将被用于表示应用标识符（AID）。根据引用控制参数P1使用检索标准‘4F’ ‘00’，GET
		 * STATUS命令可以找到所有满足选定的搜索标准的记录。
		 */
		StringBuilder cmd = new StringBuilder("");
		cmd.append("4F").append(getApduWithLength(data));
		return madeCommandMsg("80", GPConstant.GETSTATUS, p1, "00",
				cmd.toString());
	}

	/**
	 * GP 9.4.2.1 ‘40’：只有应用和安全域。
	 *
	 * @param cla
	 * @param data
	 * @return
	 */
	public static String getAppOrSdStatus(String data) {
		return getStatus(data, "40");
	}

	/**
	 * GP 9.4.2.1 ‘80’：只有发行者安全域。在这种情况下检索标准是被忽略的，并且发行者安全域信息被返回。
	 *
	 * @param cla
	 * @param data
	 * @return
	 */
	public static String getIsdStatus(String data) {
		return getStatus(data, "80");
	}

	/**
	 * 拆分loaddata的数据
	 *
	 * @param dataStr
	 *            数据域
	 * @param index
	 *            起始索引
	 * @param changeLastBlock
	 *            是否改变最后一个块标示
	 * @return
	 */
	public static Vector<String> splitLoadData(String dataStr, int index,
			int splitLen) {
		Vector<String> datas = new Vector<String>();
		int m = 0;
		String cmd = "";
		while (m < dataStr.length()) {

			String data = "";
			String p1 = "";
			// 块长度108字节 用245以防CAP过大。
			int j = m + (splitLen) * 2;
			// p1 80:最后块；00：中间块
			if (j >= dataStr.length()) {
				p1 = "80";
				data = new String(dataStr.substring(m));
			} else {
				p1 = "00";
				data = new String(dataStr.substring(m, j));
			}
			// p2 块号，并且将按照顺序从‘00’到‘FF’进行编码。
			String p2 = Converts.intToHex(index, GPConstant.SUB_TLV_LEN);
			cmd = madeCommandMsg("80", GPConstant.LOAD, p1, p2, data);
			datas.add(cmd);
			m = j;
			index++;
		}
		return datas;
	}

	/**
	 * 安装ssd
	 *
	 * @param loadFileAid
	 * @param moduleId
	 * @param appAid
	 * @param privilege
	 * @param installParam
	 * @return
	 */
	public static String installSsd(String loadFileAid, String moduleId,
			String appAid, String privilege, String installParam) {
		//
		StringBuilder data = new StringBuilder("");
		data.append(getApduWithLength(loadFileAid))
				.append(getApduWithLength(moduleId))
				.append(getApduWithLength(appAid))
				.append(getApduWithLength(privilege))
				.append(getApduWithLength(installParam)).append("00");
		return install("80", "0C", data.toString());
	}

	/**
	 * 删除安全域信息
	 *
	 * @param data
	 * @return
	 */
	public static String deleteSd(String data) {
		StringBuilder cmd = new StringBuilder("");
		cmd.append("4F").append(getApduWithLength(data));
		return delete("00", cmd.toString());
	}

	public static String delete(String p2, String data) {
		/**
		 * p1: 00 Last (or only) command
		 */
		String p1 = "00";
		String cla = "80";
		return madeCommandMsg(cla, GPConstant.DELETE, p1, p2, data);
	}

	/**
	 * INITIALIZE UPDATE GP2.2 D.4.1.2 apdu执行成功返回9000
	 *
	 * @param cla
	 * @param data
	 * @return
	 * @throws TaskException
	 */
	public static String initializeUpdate(String rand, String keyVer) {
		/**
		 * p1 密钥版本号 00 the first available key chosen by the Security Domain
		 * will be used. p2 密钥标识 00
		 */
		return madeCommandMsg("80", GPConstant.INITIALIZEUPDATE, keyVer, "00",
				rand);

	}



	/**
	 * 封装指令
	 *
	 * @param cla
	 * @param ins
	 * @param p1
	 *            密钥版本号
	 * @param p2
	 * @param data
	 * @param le
	 * @return
	 */
	public static String madeCommandMsg(String cla, String ins, String p1,
			String p2, String data) {
		String le = "00";
		StringBuilder cmd = new StringBuilder("");
		if (GPConstant.card_terminated.equals(p2)) {
			cmd.append(cla).append(ins).append(p1).append(p2).append(le)
					.append("00");
			return cmd.toString();
		}
		cmd.append(cla).append(ins).append(p1).append(p2)
				.append(getApduWithLength(data)).append(le);
		return cmd.toString();
	}

	/**
	 * GP 11.1.7
	 *
	 * @param cla
	 *            '00' - '03' or '40' - '4F'
	 * @param data
	 * @return
	 */
	public static String registryCF(String data) {

		StringBuilder cmd = new StringBuilder("");
		cmd.append("80").append(GPConstant.INSTALL).append("40").append("00")
				.append(getApduWithLength(data)).append("00");
		return cmd.toString();
	}

	public static void main(String[] args){
		String s = ApduUtils.select("A0000003330000000000000000000001");
		System.out.println(s);
	}
}
