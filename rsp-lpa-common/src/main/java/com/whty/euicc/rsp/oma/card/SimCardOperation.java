package com.whty.euicc.rsp.oma.card;


import org.simalliance.openmobileapi.Reader;

import android.content.Context;

import com.whty.euicc.rsp.oma.callback.OMAServiceCallBack;

public class SimCardOperation {
	private static final String TAG = "SimCardOperation";
	private static SimCardOperation simCardOperation = null;
	private ASimCardTransmit simCardTransmit;

	private SimCardOperation(Context context) {
		simCardTransmit = OpenMobileApiUtil.getOpenMobileApiUtil(context);
	}

	/**
	 * 
	 * ********************************************************************<br>
	 * 方法功能：单例模式获取SimOperation对象<br>
	 * 参数说明：<br>
	 * 作 者：杨明<br>
	 * 开发日期：2013-9-9 下午7:24:29<br>
	 * 修改日期：<br>
	 * 修改人：<br>
	 * 修改说明：<br>
	 * ********************************************************************<br>
	 */
	public static SimCardOperation getSimOperation(Context context) {
		if (simCardOperation == null) {
			simCardOperation = new SimCardOperation(context);
		}
		return simCardOperation;
	}

	public void resetSimCardOperation(Context context) {
		if (simCardOperation != null) {
			simCardOperation = null;
		}
	}

	/**
	 * 
	 * ********************************************************************<br>
	 * 方法功能：关闭连接的通道、会话和SEService<br>
	 * 参数说明：<br>
	 * 作 者：杨明<br>
	 * 开发日期：2013-9-9 下午7:24:09<br>
	 * 修改日期：<br>
	 * 修改人：<br>
	 * 修改说明：<br>
	 * ********************************************************************<br>
	 */
	public void closeConnection() {
		simCardTransmit.closeChannel();
	}

	public void closeService() {
		simCardTransmit.closeService();
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
		simCardTransmit.closeChannel();
	}

	/**
	 * 
	 * ********************************************************************<br>
	 * 方法功能：判断SEService是否连接上<br>
	 * 参数说明：<br>
	 * 作 者：杨明<br>
	 * 开发日期：2013-9-9 下午7:25:07<br>
	 * 修改日期：<br>
	 * 修改人：<br>
	 * 修改说明：<br>
	 * ********************************************************************<br>
	 */
	public boolean serviceConnected() {
		return simCardTransmit.cardConnect();
	}

	/**
	 * 
	 * ********************************************************************<br>
	 * 方法功能：打开SEService连接，注册回调函数，等待服务连接上<br>
	 * 参数说明：<br>
	 * 作 者：杨明<br>
	 * 开发日期：2013-9-9 下午7:25:24<br>
	 * 修改日期：<br>
	 * 修改人：<br>
	 * 修改说明：<br>
	 * ********************************************************************<br>
	 */
	public void openConnection(OMAServiceCallBack callBack) {
		simCardTransmit.openChannel(callBack);
	}

	public int getCardAtr() {
		return simCardTransmit.cardAtr();
	}

	public short sendApdu(byte[] aid, byte[] apdu, short apduLen,
			byte[] apduRes, short[] apduResLen, boolean useLogicChannel) {
		return simCardTransmit.transmitApdu(aid, apdu, apduLen, apduRes,
				apduResLen, useLogicChannel);
	}

	public String selectApplication(byte[] aid) {
		return simCardTransmit.selectApplication(aid);
	}

	/**
	 * 
	 * ********************************************************************<br>
	 * 方法功能：判断逻辑通道能不能打开，如果能打开，则表明卡上存在该应用，不能则表明不存在该应用<br>
	 * 参数说明：<br>
	 * 作 者：杨明<br>
	 * 开发日期：2013-9-12 下午5:04:10<br>
	 * 修改日期：<br>
	 * 修改人：<br>
	 * 修改说明：<br>
	 * ********************************************************************<br>
	 */
	public boolean judgeAppInstalled(byte[] aid) {
		return simCardTransmit.judgeAppInstalled(aid);
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
	public Reader[] getAllSupportReaders() {
		return simCardTransmit.getAllSupportReaders();
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
	public void setSelectReader(Reader reader) {
		simCardTransmit.setSelectReader(reader);
	}
}
