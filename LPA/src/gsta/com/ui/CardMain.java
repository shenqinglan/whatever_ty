package gsta.com.ui;

import gsta.com.apdu.ApduUtils;
import gsta.com.apdu.ToTLV;
import gsta.com.callback.OMAServiceCallBack;
import gsta.com.common.CommonMethods;
import gsta.com.common.LogUtil;
import gsta.com.manager.SwpSimCardManager;
import gsta.com.packet.message.request.AuthenticateClientReq;
import gsta.com.packet.message.request.CancelSessionReq;
import gsta.com.packet.message.request.GetBoundProfilePackageReq;
import gsta.com.packet.message.request.HandleNotificationReq;
import gsta.com.utils.RATBean;
import gsta.com.utils.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;

public class CardMain {
	private static final String TAG = "CardMain";
	private SwpSimCardManager cardManager;
	private Context context;
	/** 选择应用AID */
	private String AID;
	/** 指令执行成功标识 */
	private static final String SUCCESS_FLAG = "9000";
	
	public CardMain(Context context,String AID) {
		this.context = context;
		this.AID=AID;
	}

	/**
	 * 实例化卡片
	 * @param callBack
	 */
	public void initCard(OMAServiceCallBack callBack) {
		cardManager = SwpSimCardManager.getSwpSimCardManager(context);
		cardManager.initCard(callBack);
	}
	
	/**
	 * 连接卡片
	 * @return 0代表连接成功，1代表连接失败
	 */
	public int connectCard() {
		int value = -1;
		String resStr = cardManager.selectApplication(CommonMethods
				.str2bytes(AID));
		String inputConver = resStr.substring(resStr.length()-4);
		value = CommonMethods.commonResConvert(inputConver);
		LogUtil.e(TAG, "connectCard result: " + value);
		return value;
	}
	
	/**
	 * 关闭卡连接
	 */
	public void disConnectCard(){
		cardManager.closeChannelAndSession();
		LogUtil.e(TAG, "----------disConnectCard---------");
	}
	
	/**
	 * 关闭SEService
	 */
	public void closeSEService(){
		cardManager.closeCard();
		LogUtil.e(TAG, "SEService is shutdown");
	}
	
	/**
	 * ES10a.getEuiccConfiguredAddresses
	 * @return
	 */
	public String getEuiccConfiguredAddresses() {
		String apdu = ApduUtils.getEuiccConfiguredAddressesApdu();
		String euiccConfiguredAddresses = "";
		String apduResponseStr = cardManager.transmitApdu(AID, apdu, true);
		if (apduResponseStr.endsWith(SUCCESS_FLAG)) {
			LogUtil.e(TAG, "getEuiccConfiguredAddresses Success!");
			int lenStr = apduResponseStr.length();
			euiccConfiguredAddresses = apduResponseStr.substring(0, lenStr - 4).toUpperCase();
			LogUtil.e(TAG, "euiccConfiguredAddresses is " + euiccConfiguredAddresses);
		}else{
			throw new RuntimeException("error:card response is not 9000");
		}
		return ToTLV.toTLV("83", ToTLV.determineTLV(euiccConfiguredAddresses, "80", "81"));
	}
	
	/**
	 * ES10a.setDefaultDpAddressApdu
	 * @param dpAddrNew
	 * @return
	 */
	public String setDefaultDpAddressApdu(String dpAddrNew){
		String apdu = ApduUtils.setDefaultDpAddressApdu(dpAddrNew);
		String dpAddress = "";
		String apduResponseStr = cardManager.transmitApdu(AID, apdu, true);
		if (apduResponseStr.endsWith(SUCCESS_FLAG)) {
			LogUtil.e(TAG, "setDefaultDpAddressApdu Success!");
			int lenStr = apduResponseStr.length();
			dpAddress = apduResponseStr.substring(0, lenStr - 4).toUpperCase();
			LogUtil.e(TAG, "dpAddress is " + dpAddress);
		}else{
			throw new RuntimeException("error:card response is not 9000");
		}
		return dpAddress;
		
	}
	
	/**
	 * ES10b.getEUICCInfo
	 * @return
	 */
	public String getEUICCInfo(int euiccInfo1Or2) {
		String apdu = null;
		String euiccInfo = null;
		String apduResponseStr = null;
		if(euiccInfo1Or2 == 1){
			apdu = ApduUtils.getEuiccInfo1Apdu();
			apduResponseStr = cardManager.transmitApdu(AID, apdu, true);
			if(apduResponseStr.endsWith(SUCCESS_FLAG)){
				LogUtil.e(TAG, "getEUICCInfo1 Success!");
				euiccInfo = apduResponseStr.substring(0, apduResponseStr.length() - 4);
				LogUtil.e(TAG, "euiccInfo is " + euiccInfo);
			}else{
				throw new RuntimeException("error:card response is not 9000");
			}
		}else{
			apdu = ApduUtils.getEuiccInfo2Apdu();
			apduResponseStr = cardManager.transmitApdu(AID, apdu, true);
			if(StringUtils.equals("61", apduResponseStr.substring(apduResponseStr.length()-4, apduResponseStr.length()-2))){
				String remainResponse = cardManager.transmitApdu(AID, "00c0000000", true);
				apduResponseStr = apduResponseStr.substring(0, apduResponseStr.length()-4) + remainResponse.substring(0, remainResponse.length()-4);
			}else{
				throw new RuntimeException("error: getEuiccInfo2Apdu() card response is not 61**");
			}
		}
		
		euiccInfo = apduResponseStr.toUpperCase();
		return euiccInfo;
	}
	
	/**
	 * ES10b.getEUICCChallenge
	 * @return
	 */
	public String getEUICCChallenge() {
		String apdu = ApduUtils.getEUICCChallengeApdu();
		String eUICCChallenge = "";
		String apduResponseStr = cardManager.transmitApdu(AID, apdu, true);
		if (apduResponseStr.endsWith(SUCCESS_FLAG)) {
			LogUtil.e(TAG, "getEUICCChallenge Success!");
			eUICCChallenge = apduResponseStr.substring(0).toUpperCase();
			LogUtil.e(TAG, "eUICCChallenge is " + eUICCChallenge);
		}else{
			throw new RuntimeException("error:card response is not 9000");
		}
		return ToTLV.toTLV("81", ToTLV.determineTLV(eUICCChallenge, "80", "9000"));
	}
	
	/**
	 * ES10b.authenticateServer
	 * @return
	 */
	public AuthenticateClientReq authenticateServer(String smdpSigned1, String smdpSignature1, String euiccCiPKIdToBeUsed, String cert, String ctxParams1) {
		List<String> apdu = ApduUtils.authenticateServerApdu(smdpSigned1, smdpSignature1, euiccCiPKIdToBeUsed, cert, ctxParams1);
		AuthenticateClientReq authenticateClientReq = new AuthenticateClientReq();
		StringBuilder result = new StringBuilder();
		String authenticateServerResponse = "";
		String apduResponseStr = "";
		for(int i=0; i<apdu.size()-1; i++){
			apduResponseStr = cardManager.transmitApdu(AID, apdu.get(i), true);
			if(apduResponseStr.endsWith(SUCCESS_FLAG)){
				LogUtil.e(TAG, "Success: authenticateServer apduPart response is 9000");
			}else{
				throw new RuntimeException("error: authenticateServer apduPart response is not 9000");
			}
		}
		
		apduResponseStr = cardManager.transmitApdu(AID, apdu.get(apdu.size()-1), true).toUpperCase();
		
		//返回结果长度大于256字节，分多次获取
		while(StringUtils.equals("61", apduResponseStr.substring(apduResponseStr.length()-4, apduResponseStr.length()-2))){
			result.append(apduResponseStr.substring(0, apduResponseStr.length()-4));
			apduResponseStr = cardManager.transmitApdu(AID, "00c0000000", true);
		}
		//最后一条返回9000
		result.append(apduResponseStr.substring(0, apduResponseStr.length()-4));
		
		LogUtil.e(TAG, "authenticateServer Success!");
		authenticateServerResponse = result.toString();
		LogUtil.e(TAG, "authenticateServerResponse is " + authenticateServerResponse);
		
		String transactionId = ToTLV.toTLV("80", ToTLV.determineTLV(authenticateServerResponse, "80", "83"));
		authenticateClientReq.setTransactionId(transactionId);
		int index = authenticateServerResponse.indexOf("3082");
		int index5F37 = authenticateServerResponse.indexOf("5F37");
		String euiccSign1 = authenticateServerResponse.substring(index, index5F37);
		authenticateClientReq.setEuiccSigned1(euiccSign1);
		String euiccSignature1 = authenticateServerResponse.substring(index5F37,index5F37+134);
		authenticateClientReq.setEuiccSignature1(euiccSignature1);
		index5F37 +=  euiccSignature1.length();
		String euiccCertificate = authenticateServerResponse.substring(index5F37, authenticateServerResponse.indexOf("3082", index5F37+10));
		authenticateClientReq.setEuiccCertificate(euiccCertificate);
		index5F37 += euiccCertificate.length();
		authenticateClientReq.setEumCertificate(authenticateServerResponse.substring(index5F37));
		return authenticateClientReq;
	}
	
	/**
	 * ES10b.getRAT
	 * @return
	 */
	public List<RATBean> getRAT() {
		String apdu = ApduUtils.getRATApdu();
		String RAT = "";
		String apduResponseStr = cardManager.transmitApdu(AID, apdu, true);
		List<RATBean> list = new ArrayList<RATBean>();
		if (apduResponseStr.endsWith(SUCCESS_FLAG)) {
			LogUtil.e(TAG, "getRAT Success!");
			int lenStr = apduResponseStr.length();
			RAT = apduResponseStr.substring(0, lenStr - 4).toUpperCase();
			LogUtil.e(TAG, "RAT is " + RAT);
			
			int ratLen = Integer.parseInt(RAT.substring(8, 10), 16) * 2;
			RAT = RAT.substring(10);
			String rat1 = "";
			while(ratLen != 0){
				RATBean ratBean = new RATBean();
				try {
					rat1 = ToTLV.determineTLV(RAT, "30", "30");
					
				} catch (Exception e) {
					rat1 = RAT.substring(RAT.indexOf("30") + 4);
				}
				ratBean.setPprId(ToTLV.determineTLV(rat1, "80", "A1"));
				ratBean.setAllowedOperator(ToTLV.determineTLV(rat1, "80", "82"));
				ratBean.setEndUserConsent(rat1.substring(rat1.indexOf("82") + 4));
				list.add(ratBean);
				ratLen = ratLen - (4 + rat1.length());
				RAT = RAT.substring(4 + rat1.length());
			}
			
		}else{
			throw new RuntimeException("error:card response is not 9000");
		}
		return list;
	}
	
	/**
	 * ES10b.cancelSession
	 * @return
	 */
	public CancelSessionReq cancelSession(String transactionId, String reason) {
		String apdu = ApduUtils.cancelSessionApdu(transactionId, reason);
		CancelSessionReq cancelSessionReq = new CancelSessionReq();
		String cancelSessionResponse = "";
		String apduResponseStr = cardManager.transmitApdu(AID, apdu, true);
		if (apduResponseStr.endsWith(SUCCESS_FLAG)) {
			LogUtil.e(TAG, "cancelSession Success!");
			cancelSessionResponse = apduResponseStr.substring(0, apduResponseStr.length() - 4).toUpperCase();
			LogUtil.e(TAG, "cancelSessionResponse is " + cancelSessionResponse);
		}else{
			throw new RuntimeException("error:card response is not 9000");
		}
		
		int index30 = cancelSessionResponse.indexOf("30");
		int index5F37 = cancelSessionResponse.indexOf("5F37");
		cancelSessionReq.setTransactionId(ToTLV.toTLV("80", ToTLV.determineTLV(cancelSessionResponse, "80", "81")));
		cancelSessionReq.setEuiccCancelSessionSigned(cancelSessionResponse.substring(index30, index5F37));
		cancelSessionReq.setEuiccCancelSessionSignature(cancelSessionResponse.substring(index5F37));
		return cancelSessionReq;
	}
	
	/**
	 * ES10b.prepareDownload
	 * @return
	 */
	public GetBoundProfilePackageReq prepareDownload(String smdpSigned2, String smdpSignature2, String cert, String code) {
		List<String> apdu = ApduUtils.prepareDownloadApdu(smdpSigned2, smdpSignature2, cert, code);
		GetBoundProfilePackageReq getBoundProfilePackageReq = new GetBoundProfilePackageReq();
		String prepareDownloadResponse = "";
		String apduResponseStr = "";
		
		//apdu分多次发送，除最后一条外其余仅返回9000
		for(int i=0; i<apdu.size()-1; i++){
			apduResponseStr = cardManager.transmitApdu(AID, apdu.get(i), true);
			if(apduResponseStr.endsWith(SUCCESS_FLAG)){
				LogUtil.e(TAG, "Success: prepareDownload apduPart response is 9000");
			}else{
				throw new RuntimeException("error: prepareDownload apduPart response is not 9000");
			}
		}
		//卡返回值小于256字节，一次获取
		apduResponseStr = cardManager.transmitApdu(AID, apdu.get(apdu.size()-1), true).toUpperCase();
		if (apduResponseStr.endsWith(SUCCESS_FLAG)) {
			LogUtil.e(TAG, "prepareDownload Success!");
			prepareDownloadResponse = apduResponseStr.substring(0, apduResponseStr.length() - 4);
			LogUtil.e(TAG, "prepareDownloadResponse is " + prepareDownloadResponse);
		}else {
			throw new RuntimeException("error:prepareDownload final card response is not 9000");
		}
		
		int index30 = prepareDownloadResponse.indexOf("30");
		int index5F37 = prepareDownloadResponse.indexOf("5F37");
		getBoundProfilePackageReq.setTransactionId(ToTLV.toTLV("80", ToTLV.determineTLV(prepareDownloadResponse, "80", "5F49")));
		getBoundProfilePackageReq.setEuiccSigned2(prepareDownloadResponse.substring(index30, index5F37));
		getBoundProfilePackageReq.setEuiccSignature2(prepareDownloadResponse.substring(index5F37));
		return getBoundProfilePackageReq;
	}
	
	/**
	 * ES10b.loadBoundProfilePackage
	 * @return
	 */
	public HandleNotificationReq loadBoundProfilePackage(List<String[]> data) {
		List<String> apdu = ApduUtils.loadBoundProfilePackageApdu(data);
		HandleNotificationReq handleNotificationReq = new HandleNotificationReq();
		String profileInstallationResult = "";
		String apduResponseStr = "";
		
		//apdu分多次发送，除最后一条外其余仅返回9000
		for(int i=0; i<apdu.size()-1; i++){
			apduResponseStr = cardManager.transmitApdu(AID, apdu.get(i), true).toUpperCase();
			if(apduResponseStr.endsWith(SUCCESS_FLAG)){
				LogUtil.e(TAG, "Success: loadBoundProfilePackage apduPart response is 9000");
			}else{
				throw new RuntimeException("error: loadBoundProfilePackage apduPart response is not 9000");
			}
		}
		//卡返回值小于256字节，一次获取
		apduResponseStr = cardManager.transmitApdu(AID, apdu.get(apdu.size()-1), true);
		if (apduResponseStr.endsWith(SUCCESS_FLAG)) {
			LogUtil.e(TAG, "loadBoundProfilePackage Success!");
			profileInstallationResult = apduResponseStr.substring(0, apduResponseStr.length() - 4).toUpperCase();
			LogUtil.e(TAG, "loadBoundProfilePackage is " + profileInstallationResult);
		}else {
			throw new RuntimeException("loadBoundProfilePackage final card response is not 9000");
		}
		
		int indexBF27 = profileInstallationResult.indexOf("BF27");
		int index5F37 = profileInstallationResult.indexOf("5F37");
		handleNotificationReq.setProfileInstallationResultData(profileInstallationResult.substring(indexBF27, index5F37));
		handleNotificationReq.setEuiccSignPIR(profileInstallationResult.substring(index5F37));
		return handleNotificationReq;
	}
	
	/**
	 * ES10b.removeNotificationFromList
	 * @return
	 */
	public String removeNotificationFromList(String notifycounter) {
		String apdu = ApduUtils.removeNotificationFromListApdu(notifycounter);
		String notificationSentResponse = "";
		String apduResponseStr = cardManager.transmitApdu(AID, apdu, true);
		if (apduResponseStr.endsWith(SUCCESS_FLAG)) {
			LogUtil.e(TAG, "removeNotificationFromList Success!");
			int lenStr = apduResponseStr.length();
			notificationSentResponse = apduResponseStr.substring(0, lenStr - 4).toUpperCase();
			LogUtil.e(TAG, "notificationSentResponse is " + notificationSentResponse);
		}else{
			throw new RuntimeException("error:card response is not 9000");
		}
		return notificationSentResponse;
	}
	
	/**
	 * ES10c.getProfilesInfo
	 * @return
	 */
	public List<Map<String, String>> getProfilesInfo(String isdp1) {
		String apdu = ApduUtils.getProfilesInfoApdu(isdp1);
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		String profilesInfo = "";
		String apduResponseStr = cardManager.transmitApdu(AID, apdu, true).toUpperCase();
		if (apduResponseStr.endsWith(SUCCESS_FLAG)) {
			LogUtil.e(TAG, "getProfilesInfo Success!");
			int lenStr = apduResponseStr.length();
			profilesInfo = apduResponseStr.substring(0, lenStr - 4).toUpperCase();
			
			int listLen = Integer.parseInt(profilesInfo.substring(8, 10), 16) * 2;
			profilesInfo = profilesInfo.substring(10).replace("E3", "01").replace("93", "01");
			LogUtil.e(TAG, "profilesInfo is " + profilesInfo);
			String map1 = "";
			while(listLen != 0){
				Map<String, String> map = new HashMap<String, String>();
				try {
					map1 = ToTLV.determineTLV(profilesInfo, "01", "01");
				} catch (Exception e) {
					map1 = profilesInfo.substring(4);
				}
				if (map1.indexOf("90") % 2 == 0){
					map.put("iccid", ToTLV.determineTLV(map1, "5A", "9F70"));
					String state = ToTLV.determineTLV(map1, "9F70", "90");
					if ("00".equals(state)) {
						state = "禁用";
					} else if ("01".equals(state)) {
						state = "启用";
					}
					map.put("state", state);
					map.put("nickName", Tools.hexDecode(map1.substring(36)));
				}else {
					map.put("iccid", ToTLV.determineTLV(map1, "5A", "9F70"));
					String state =  map1.substring(30);
					if ("00".equals(state)) {
						state = "禁用";
					} else if ("01".equals(state)) {
						state = "启用";
					}
					map.put("state", state);
					map.put("nickName", "");
				}
				list.add(map);
				listLen = listLen - (4 + map1.length());
				profilesInfo = profilesInfo.substring(4 + map1.length());
			}

		}else{
			throw new RuntimeException("error:card response is not 9000");
		}
		
		return list;
	}
	
	/**
	 * ES10c.enableProfile
	 * @return
	 */
	public boolean enableProfile(String iccidOrISDPaid, String refreshflag) {
		
		//启用profile
		return enable(iccidOrISDPaid, refreshflag);
	}
	
	public String removeEnableNotification(String iccidOrISDPaid, String result) {
		//检查profile应该是激活的
		//检查当前是否有已激活的profile
				
		boolean bool = result.toUpperCase().indexOf("9F700101") % 2 == 0;

		result = checkState(iccidOrISDPaid, result);
		
		//判断profile状态
		if(!StringUtils.equals(result, "01")){
			throw new RuntimeException("profile未处于禁用状态");
		}
		
		if(bool){
			//获取通知计数器
			result = getNotifyCounter(result);
			
			String nextnotifycounter = ApduUtils.nextNotifyCounterApdu(result);
			
			//移除通知
			result = removeNotification(result, nextnotifycounter);
		}else{
			//获取通知计数器
			result = getNotifyCounter(result);                 //IccGp_notifycounter
			
			//检查通知类型
			String notifyTypeResult = checkNotifyType(result);
			
			if(!StringUtils.equals(notifyTypeResult, "notification Enable")){
				throw new RuntimeException("error: notifyType is error");
			}else{
				result = removeNotification(result);
			}
			
		}
		return result;
	}

	private String removeNotification(String result) {
		String apdu;
		String apduResponseStr;
		apdu = ApduUtils.removeNotificationApdu(result);
		apduResponseStr = cardManager.transmitApdu(AID, apdu, true).toUpperCase();
		if(StringUtils.equals(apduResponseStr, "BF30038001009000")){
			result = "1";
		}else{
			throw new RuntimeException("error: removeNotificationApdu()");
		}
		return result;
	}

	private String checkState(String iccidOrISDPaid, String result) {
		String apdu;
		String apduResponseStr;
		apdu = ApduUtils.getProfilesInfo_profileStateApdu(iccidOrISDPaid);
		apduResponseStr = cardManager.transmitApdu(AID, apdu, true).toUpperCase();
		if(apduResponseStr.endsWith(SUCCESS_FLAG) && apduResponseStr.contains("BF2D02A000")){
			result = "6A88";
			LogUtil.e(TAG, "getProfilesInfo_profileStateApdu result is " + result);
		}else if(StringUtils.equals(apduResponseStr.substring(0, 20), "BF2D08A006E3049F7001")){
			result = apduResponseStr.substring(20, 20 + 2);
			LogUtil.e(TAG, "getProfilesInfo_profileStateApdu result is " + result);
		}else{
			throw new RuntimeException("error: getProfilesInfo_profileStateApdu()");
		}
		return result;
	}

	private boolean enable(String iccidOrISDPaid, String refreshflag) {
		String apdu;
		String apduResponseStr;
		String result;
		apdu = ApduUtils.enableProfileApdu(iccidOrISDPaid, refreshflag);
		apduResponseStr = cardManager.transmitApdu(AID, apdu, true).toUpperCase();
		LogUtil.e(TAG, "enableResponseStr: "+apduResponseStr);
		if(StringUtils.equals(refreshflag, "00") && apduResponseStr.indexOf("BF31038001009000") != -1){
			LogUtil.e(TAG, "enableProfile Success!");
			result = apduResponseStr.substring(0, apduResponseStr.length() - 4);
			LogUtil.e(TAG, "enableResult is " + result);
			return true;
		}else if(StringUtils.equals(refreshflag, "FF") && apduResponseStr.indexOf("BF310380010091") != -1){
			LogUtil.e(TAG, "enableProfile Success!");
			result = apduResponseStr.substring(0, apduResponseStr.length() - 4);
			LogUtil.e(TAG, "enableResult is " + result);
			return true;
		}else{
			LogUtil.e(TAG, "enableProfile Failure!");
			return false;
		}
	}

	public String checkAnyEnableProfile() {
		String apdu = ApduUtils.checkIfAnyEnabledProfileApdu();
		String result = "";
		String apduResponseStr = cardManager.transmitApdu(AID, apdu, true);
		if(apduResponseStr.endsWith(SUCCESS_FLAG)){
			LogUtil.e(TAG, "checkIfAnyEnabledProfile Success!");
			result = apduResponseStr.substring(0, apduResponseStr.length()-4);
			LogUtil.e(TAG, "checkIfAnyEnabledProfile is " + result);
		}else{
			LogUtil.e(TAG, "返回结果长度大于256字节，分多次获取");
		}
		return result;
	}

	private String checkNotifyType(String result) {
		String apdu;
		String apduResponseStr;
		int counterlen = result.length()/2;
		apdu = ApduUtils.getNotifyTypeApdu();
		apduResponseStr = cardManager.transmitApdu(AID, apdu, true).toUpperCase();
		String notifyTypeResult = "";
		if(StringUtils.equals(apduResponseStr, "BF2802A0009000")){
			notifyTypeResult = null;
		}else{
			String notifyType = apduResponseStr.substring((12 + counterlen) * 2, (12 + counterlen) * 2 + 4);
			if(StringUtils.equals(notifyType, "0780")){
				notifyTypeResult = "notification Install";
			}else if(StringUtils.equals(notifyType, "0640")){
				notifyTypeResult = "notification Enable";
			}else if(StringUtils.equals(notifyType, "0520")){
				notifyTypeResult = "notification Disable";
			}else if(StringUtils.equals(notifyType, "0410")){
				notifyTypeResult = "notification Delete";
			}else{
				throw new RuntimeException("error: getNotifyTypeApdu()");
			}
		}
		return notifyTypeResult;
	}

	private String removeNotification(String result, String nextnotifycounter) {
		String apdu;
		String apduResponseStr;
		apdu = ApduUtils.removeNotificationApdu(result);
		apduResponseStr = cardManager.transmitApdu(AID, apdu, true).toUpperCase();
		if(StringUtils.equals(apduResponseStr, "BF30038001009000")){
			result = "1";
			LogUtil.e(TAG, "RemoveNotification(IccGp_notifycounter) result is " + result);
			
			apdu = ApduUtils.removeNotificationApdu(nextnotifycounter);
			apduResponseStr = cardManager.transmitApdu(AID, apdu, true).toUpperCase();;
			if(StringUtils.equals(apduResponseStr, "BF30038001009000")){
				result = "1";
				LogUtil.e(TAG, "RemoveNotification(Nextnotifycounter) result is " + result);
			}else{
				throw new RuntimeException("error: RemoveNotification(Nextnotifycounter)");
			}
		}else{
			throw new RuntimeException("error: RemoveNotification(IccGp_notifycounter)");
		}
		return result;
	}

	private String getNotifyCounter(String result) {
		String apdu;
		String apduResponseStr;
		apdu = ApduUtils.getNotifyCounter();
		apduResponseStr = cardManager.transmitApdu(AID, apdu, true).toUpperCase();
		if(apduResponseStr.contains("BF2802A000")){
			result = null;
		}else{
			if(!StringUtils.equals("80", apduResponseStr.substring(16, 16 + 2))){
				throw new RuntimeException("error: getNotifyCounter()");
			}else{
				String len80Hex = apduResponseStr.substring(18, 18 + 2);
				int len80 = Integer.valueOf(len80Hex, 16);
				result = apduResponseStr.substring(20, 20 + len80 * 2);    //IccGp_notifycounter
				LogUtil.e(TAG, "getNotifyCounter result is " + result);
			}
		}
		return result;
	}
	
	/**
	 * ES10c.disableProfile
	 * @return
	 */
	public boolean disableProfile(String iccidOrISDPaid, String refreshflag) {
		//发送禁用profile apdu
		return disable(iccidOrISDPaid, refreshflag);
	}
	
	public String removeDisableNotification(String iccidOrISDPaid) {
		//检查profile应该是去激活的
		String result = "";
		result = checkState(iccidOrISDPaid, result);
		
		if(StringUtils.equals(result, "00")){
			result = getNotifyCounterAndRemoveNoti(result);
		}else if(StringUtils.equals(result, "6A88")){
			result = getNotifyCounterAndRemoveNoti(result);
			String iccGp_notifycounter = getNotifyCounter(result);
			String notifyType = checkNotifyType(result);
			if(!StringUtils.equals(notifyType, "notification Delete")){
				throw new RuntimeException("error: checkNotifyType()");
			}
			result = removeNotification(iccGp_notifycounter);
			if(!StringUtils.equals(result, "1")){
				throw new RuntimeException("error: removeNotification()");
			}
		}
		
		return result;
	}

	private String getNotifyCounterAndRemoveNoti(String result) {
		String iccGp_notifycounter = getNotifyCounter(result);
		String notifyType = checkNotifyType(result);
		if(!StringUtils.equals(notifyType, "notification Disable")){
			throw new RuntimeException("error: checkNotifyType()");
		}
		result = removeNotification(iccGp_notifycounter);
		if(!StringUtils.equals(result, "1")){
			throw new RuntimeException("error: removeNotification()");
		}
		return result;
	}

	private boolean disable(String iccidOrISDPaid, String refreshflag) {
		String apdu = ApduUtils.disableProfileApdu(iccidOrISDPaid, refreshflag);
		String result = "";
		String apduResponseStr = cardManager.transmitApdu(AID, apdu, true).toUpperCase();
		if (StringUtils.equals(refreshflag, "00") && StringUtils.equals(apduResponseStr, "BF32038001009000")) {
			LogUtil.e(TAG, "disableProfile Success!");
			result = apduResponseStr.substring(0, apduResponseStr.length() - 4);
			LogUtil.e(TAG, "disableResult is " + result);
			return true;
		}else if(StringUtils.equals(refreshflag, "FF") && StringUtils.equals(apduResponseStr.substring(0, apduResponseStr.length()-2), "BF320380010091")){
			LogUtil.e(TAG, "disableProfile Success!");
			result = apduResponseStr.substring(0, apduResponseStr.length() - 4);
			LogUtil.e(TAG, "disableResult is " + result);
			return true;
		}else{
			//throw new RuntimeException("error: disableProfileApdu()");
			return false;
		}
	}
	
	/**
	 * ES10c.deleteProfile
	 * @return
	 */
	public boolean deleteProfile(String iccidOrISDPaid) {
		String apdu = ApduUtils.deleteProfileApdu(iccidOrISDPaid);
		String result = "";
		String apduResponseStr = cardManager.transmitApdu(AID, apdu, true).toUpperCase();
		if (StringUtils.equals(apduResponseStr, "BF33038001009000")) {
			LogUtil.e(TAG, "deleteProfile Success!");
			result = apduResponseStr.substring(0, apduResponseStr.length() - 4);
			LogUtil.e(TAG, "deleteResult is " + result);
			return true;
		}else{
			return false;
		}
		
		
	}
	
	public String removeDeleteNotification(String iccidOrISDPaid) {
		String result = "";
		result = checkState(iccidOrISDPaid, result);
		
		if(!StringUtils.equals(result, "6A88")){
			throw new RuntimeException("error: checkState()");
		}
		
		String iccGp_notifycounter = getNotifyCounter(result);
		String notifyType = checkNotifyType(iccGp_notifycounter);
		if(!StringUtils.equals(notifyType, "notification Delete")){
			throw new RuntimeException("error: checkNotifyType()");
		}
		
		result = removeNotification(iccGp_notifycounter);
		if(!StringUtils.equals(result, "1")){
			throw new RuntimeException("error: removeNotification()");
		}
		
		return result;
	}
	
	public String removeInstallNotification(String iccidOrISDPaid) {
		String result = "";
		String iccGp_notifycounter = getNotifyCounter(result);
		String notifyType = checkNotifyType(iccGp_notifycounter);
		if(!StringUtils.equals(notifyType, "notification Install")){
			throw new RuntimeException("error: checkNotifyType()");
		}
		
		result = removeNotification(iccGp_notifycounter);
		if(!StringUtils.equals(result, "1")){
			throw new RuntimeException("error: removeNotification()");
		}
		
		return result;
	}
	
	/**
	 * ES10c.eUICCMemoryReset
	 * @return
	 */
	public String eUICCMemoryReset(String resetOptions) {
		String apdu = ApduUtils.eUICCMemoryResetApdu(resetOptions);
		String resetResult = "";
		String apduResponseStr = cardManager.transmitApdu(AID, apdu, true);
		if (apduResponseStr.endsWith(SUCCESS_FLAG)) {
			LogUtil.e(TAG, "eUICCMemoryReset Success!");
			int lenStr = apduResponseStr.length();
			resetResult = apduResponseStr.substring(0, lenStr - 4);
			LogUtil.e(TAG, "resetResult is " + resetResult);
		}else{
			throw new RuntimeException("error:card response is not 9000");
		}
		return resetResult;
	}
	/**
	 * ES10c.getEID
	 * @return
	 */
	public String getEID() {
		String apdu = ApduUtils.getEIDApdu();
		String eid = "";
		String apduResponseStr = cardManager.transmitApdu(AID, apdu, true);
		if (apduResponseStr.endsWith(SUCCESS_FLAG)) {
			LogUtil.e(TAG, "getEID Success!");
			int lenStr = apduResponseStr.length();
			eid = apduResponseStr.substring(0, lenStr - 4);
			LogUtil.e(TAG, "eid is " + eid);
		}else{
			throw new RuntimeException("error:card response is not 9000");
		}
		return eid;
	}
	/**
	 * ES10c.setNickname
	 * @return
	 */
	public String setNickname(String iccid, String profileNickname) {
		String apdu = ApduUtils.setNicknameApdu(iccid, profileNickname);
		String setNicknameResult = "";
		String apduResponseStr = cardManager.transmitApdu(AID, apdu, true);
		if (apduResponseStr.endsWith(SUCCESS_FLAG)) {
			LogUtil.e(TAG, "setNickname Success!");
			int lenStr = apduResponseStr.length();
			setNicknameResult = apduResponseStr.substring(0, lenStr - 4);
			LogUtil.e(TAG, "setNicknameResult is " + setNicknameResult);
		}else{
			throw new RuntimeException("error:card response is not 9000");
		}
		return setNicknameResult;
	}
	
}
