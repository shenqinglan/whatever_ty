package gsta.com.manager;

import gsta.com.callback.OMAServiceCallBack;
import gsta.com.card.SimCardOperation;
import gsta.com.common.CommonMethods;
import gsta.com.common.LogUtil;

import org.simalliance.openmobileapi.Reader;

import android.content.Context;

public class SwpSimCardManager {
	private static final String TAG = "SwpSimCardManager";
	private SimCardOperation simOperation;
	private Context context;

	/**
	 * 卡应用AID
	 */
	private static SwpSimCardManager swpSimCardManager = null;

	public static SwpSimCardManager getSwpSimCardManager(Context context) {
		if (swpSimCardManager == null) {
			swpSimCardManager = new SwpSimCardManager(context);
		}
		return swpSimCardManager;
	}

	private SwpSimCardManager(Context context) {
		this.context = context;
		simOperation = SimCardOperation.getSimOperation(context);
	}

	public void initCard(OMAServiceCallBack callBack) {
		simOperation.openConnection(callBack);
	}

	public void closeCard() {
		simOperation.closeService();
	}

	public boolean cardConnected() {
		return simOperation.serviceConnected();
	}

	public short getCardAtr() {
		return (short) simOperation.getCardAtr();
	}

	public String transmitApdu(String isdAid, String apdu,
			boolean useLogicalChannel) {
		String apduResponseStr = "";
		try {
			byte[] aid = CommonMethods.str2bytes(isdAid);
			byte[] apduBytes = CommonMethods.str2bytes(apdu);
			short apduLength = (short) apduBytes.length;
			byte[] resBuf = new byte[5000];
			short[] resLenBuf = new short[1];
			LogUtil.e(TAG, "send apdu aid: " + isdAid+" apdu "+apdu);			
			short flag = simOperation.sendApdu(aid, apduBytes, apduLength,
					resBuf, resLenBuf, useLogicalChannel);
			byte[] data = new byte[resLenBuf[0]];
			System.arraycopy(resBuf, 0, data, 0, resLenBuf[0]);
			apduResponseStr = CommonMethods.bytesToHexString(data);
			LogUtil.e(TAG, "send apdu res: " + apduResponseStr + " resLen："
					+ resLenBuf[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return apduResponseStr;
	}

	public String selectApplication(byte[] aid) {
		boolean selectFlag = false;
		String resStr = simOperation.selectApplication(aid);
		if (resStr.endsWith("9000")) {
			selectFlag = true;
		}
		return resStr;
	}

	public void closeChannelAndSession() {
		simOperation.closeChannelAndSession();
	}

	public Reader[] getDeviceSupportReaders() {
		return simOperation.getAllSupportReaders();
	}

	public void setSelectReader(Reader reader) {
		simOperation.setSelectReader(reader);
	}

}
