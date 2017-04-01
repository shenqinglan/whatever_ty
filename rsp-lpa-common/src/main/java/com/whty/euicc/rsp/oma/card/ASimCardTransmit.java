package com.whty.euicc.rsp.oma.card;


import org.simalliance.openmobileapi.Reader;

import com.whty.euicc.rsp.oma.callback.OMAServiceCallBack;


public abstract class ASimCardTransmit {
	/**
	 * 打开通道的方法
	 */
	public abstract void openChannel(OMAServiceCallBack callBack);

	/**
	 * 关闭通道的方法
	 */
	public abstract void closeChannel();

	/**
	 * 关闭服务的方法
	 */
	public abstract void closeService();

	/**
	 * 卡片复位的方法的方法
	 */
	public abstract int cardAtr();

	/**
	 * 判断卡片是否连接的方法
	 */
	public abstract boolean cardConnect();

	/**
	 * 发送APDU的方法
	 */
	public abstract short transmitApdu(byte[] aid, byte[] apdu, short apduLen,
			byte[] apduRes, short[] apduResLen, boolean useLogicalChannel);

	/**
	 * 打开连接，选择应用
	 */
	public abstract String selectApplication(byte[] aid);

	/**
	 * 判断某一个应用是否安装的方法
	 */
	public abstract boolean judgeAppInstalled(byte[] aid);

	public abstract Reader[] getAllSupportReaders();

	public abstract void setSelectReader(Reader reader);
}
