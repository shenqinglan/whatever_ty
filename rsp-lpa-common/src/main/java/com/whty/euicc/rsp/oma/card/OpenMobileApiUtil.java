package com.whty.euicc.rsp.oma.card;



import org.simalliance.openmobileapi.Channel;
import org.simalliance.openmobileapi.Reader;
import org.simalliance.openmobileapi.SEService;
import org.simalliance.openmobileapi.SEService.CallBack;
import org.simalliance.openmobileapi.Session;

import android.content.Context;
import android.util.Log;

import com.whty.euicc.rsp.oma.callback.OMAServiceCallBack;
import com.whty.euicc.rsp.oma.common.CommonMethods;
import com.whty.euicc.rsp.oma.common.LogUtil;

public class OpenMobileApiUtil extends ASimCardTransmit {

	private static final String TAG = "OpenMobileApiUtil";

	private SEService seService = null;
	private Session session = null;
	private Channel channel = null;
	private Context context;
	public static OpenMobileApiUtil openMobileApiUtil;

	/**
	 * 表示当前手机系统所支持的所有的Reader
	 */
	private Reader[] allSupportReaders;
	/**
	 * 表示当前用户选择使用的Reader，如果只支持一个Reader，则该Reader作为默认Reader，如果有多个，则由用户选择
	 */
	private Reader userSelectReader;
	private OMAServiceCallBack callBack;

	private OpenMobileApiUtil(Context context) {
		this.context = context;
	}

	public static OpenMobileApiUtil getOpenMobileApiUtil(Context context) {
		if (openMobileApiUtil == null) {
			openMobileApiUtil = new OpenMobileApiUtil(context);
		}
		return openMobileApiUtil;
	}

	private void initSeService(OMAServiceCallBack callBack) {
		LogUtil.e(TAG, "OpenMobileApiUtil SEService init");
		this.callBack = callBack;
		SEServiceCallback seCallback = new SEServiceCallback();
		new SEService(context, seCallback);
	}

	/**
	 * 
	 * *************************************************************************
	 * ****<br>
	 * 工程名称: MIDAm二期 <br>
	 * 模块功能：SEService的回调函数，在回调当中初始化SEService和Session<br>
	 * 作 者: 杨明<br>
	 * 单 位：武汉天喻信息 研发中心 <br>
	 * 开发日期：2013-9-9 下午7:24:17 <br>
	 * *************************************************************************
	 * ****<br>
	 */
	public class SEServiceCallback implements CallBack {

		@Override
		public void serviceConnected(SEService service) {
			LogUtil.e(TAG, "SEServiceCallback serviceConnected");
			seService = service;
			Reader[] readers = seService.getReaders();
			for (int i = 0; i < readers.length; i++) {
				Reader reader = readers[i];
				String readerName = reader.getName();
				LogUtil.e(TAG, "readerName: " + readerName);
				
				if( (reader.getName().regionMatches(true, 0, "SIM", 0, 3)) //modify by yxj
					|| (reader.getName().regionMatches(true, 0, "USIM", 0, 4)) 
					|| (reader.getName().regionMatches(true, 0, "UICC", 0, 4)) ){
					userSelectReader = reader;
					break;					
				}
				/*if (readerName.startsWith("SIM")) {
					userSelectReader = reader;
				}*/
			}
			initSession();
			// 连上服务之后，获取所有当前设备支持的Reader
			// getAllReaders();
			callBack.omaServiceCallBack();
		}
	}

	public void getAllReaders() {
		Reader[] readers = seService.getReaders();
		allSupportReaders = readers;
	}

	/**
	 * 
	 * ********************************************************************<br>
	 * 方法功能：关闭通道和会话<br>
	 * 参数说明：<br>
	 * 作 者：杨明<br>
	 * 开发日期：2013-9-9 下午7:24:49<br>
	 * 修改日期：<br>
	 * 修改人：<br>
	 * 修改说明：<br>
	 * ********************************************************************<br>
	 */
	public void closeChannelAndSession() {
		if (channel != null) {
			channel.close();
			channel = null;
		}
		if (session != null) {
			session.closeChannels();
			session.close();
			session = null;
		}
	}

	/**
	 * 
	 * ********************************************************************<br>
	 * 方法功能：初始化会话，可以根据会话获取到通道<br>
	 * 参数说明：<br>
	 * 作 者：杨明<br>
	 * 开发日期：2013-9-9 下午7:24:59<br>
	 * 修改日期：<br>
	 * 修改人：<br>
	 * 修改说明：<br>
	 * ********************************************************************<br>
	 */
	public void initSession() {
		if (session == null) {
			if (userSelectReader != null
					&& !userSelectReader.isSecureElementPresent()) {
				LogUtil.e(TAG,
						"userSelectReader isSecureElementPresent response false");
			}
			try {
				session = userSelectReader.openSession();
			} catch (Exception e) {
				LogUtil.e(TAG, "userSelectReader open Session happen exception");
			}
		}
	}

	/**
	 * 
	 * ********************************************************************<br>
	 * 方法功能：返回当前手机系统支持的所有的Reader<br>
	 * 参数说明：<br>
	 * 作 者：杨明<br>
	 * 开发日期：2014-3-27 下午5:38:10<br>
	 * 修改日期：<br>
	 * 修改人：<br>
	 * 修改说明：<br>
	 * ********************************************************************<br>
	 */
	@Override
	public Reader[] getAllSupportReaders() {
		return allSupportReaders;
	}

	/**
	 * 
	 * ********************************************************************<br>
	 * 方法功能：设置用户选择的Reader，如果只有一个，则默认选择该Reader<br>
	 * 参数说明：<br>
	 * 作 者：杨明<br>
	 * 开发日期：2014-3-27 下午6:48:30<br>
	 * 修改日期：<br>
	 * 修改人：<br>
	 * 修改说明：<br>
	 * ********************************************************************<br>
	 */
	@Override
	public void setSelectReader(Reader reader) {
		this.userSelectReader = reader;
		LogUtil.e(TAG, "userSelectReader name: " + userSelectReader.getName());
	}

	@Override
	public void openChannel(OMAServiceCallBack callBack) {
		if (seService == null) {
			initSeService(callBack);
		}
	}

	@Override
	public void closeChannel() {
		closeChannelAndSession();
	}

	@Override
	public void closeService() {
		closeChannelAndSession();

		if (seService != null && seService.isConnected()) {
			seService.shutdown();
			seService = null;
		}

	}

	@Override
	public int cardAtr() {
		if (session == null) {
			initSession();
		}
		if (session != null) {
			byte[] atrRes = session.getATR();
			LogUtil.e(TAG,
					"swp-sim atr：" + CommonMethods.bytesToHexString(atrRes));
			// 表示取atr成功
			return 0;
		} else {
			// 表示取atr失败
			return -1;
		}
	}

	@Override
	public boolean cardConnect() {
		if (seService == null) {
			return false;
		} else {
			return seService.isConnected();
		}
	}

	@Override
	public short transmitApdu(byte[] aid, byte[] apdu, short apduLen,
			byte[] apduRes, short[] apduResLen, boolean useLogicalChannel) {
		short flag = -1;
		try {
			if (session == null) {
				initSession();
			}
			if (session != null) {
				if (useLogicalChannel) {
					// 打开通道
					if (channel == null) {
						Log.e(TAG,
								"logicalChannel aid: "
										+ CommonMethods.bytesToHexString(aid));
						channel = session.openLogicalChannel(aid);
					}
					if (channel.isClosed()) {
						channel = session.openLogicalChannel(aid);
					}
				} else {
					// 打开通道
					if (channel == null) {
						Log.e(TAG,
								"basicChannel aid: "
										+ CommonMethods.bytesToHexString(aid));
						channel = session.openBasicChannel(aid);
					}
					if (channel.isClosed()) {
						channel = session.openBasicChannel(aid);
					}
				}
				// 利用通道传输指令，并将返回值赋给传入的参数
				byte[] res = channel.transmit(apdu);
				byte[] selectResponse = channel.getSelectResponse();
				LogUtil.e(TAG, "selectResponse:" + selectResponse);
				if (res == null || (res != null && res.length == 0)) {
					// EEEE表示eSe没有任何回复
					res = CommonMethods.str2bytes("EEEE");
				}
				LogUtil.e(TAG, "APDU指令:" + CommonMethods.bytesToHex(apdu)
						+ "返回-->" + CommonMethods.bytesToHex(res));
				System.arraycopy(res, 0, apduRes, 0, res.length);
				apduResLen[0] = (short) res.length;
				flag = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String selectApplication(byte[] aid) {
		byte[] selectRes = null;
		String selectResStr = "";
		try {
			if (session == null) {
				initSession();
			}
			if (session != null) {
				// 打开通道
				if (channel == null) {
					Log.e(TAG,	"openLogicalChannel logicalChannel aid: "
									+ CommonMethods.bytesToHexString(aid));
					channel = session.openLogicalChannel(aid);
					selectRes = channel.getSelectResponse();
				}
				if (channel.isClosed()) {
					channel = session.openLogicalChannel(aid);
					selectRes = channel.getSelectResponse();
				}
				selectResStr = CommonMethods.bytesToHex(selectRes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return selectResStr;
	}

	@Override
	public boolean judgeAppInstalled(byte[] aid) {
		try {
			if (session == null) {
				initSession();
			}
			if (session != null) {
				Log.e(TAG, "aid: " + CommonMethods.bytesToHexString(aid));
				channel = session.openLogicalChannel(aid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
