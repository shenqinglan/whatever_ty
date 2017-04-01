package com.whty.rsp_lpa_app.tls;

import com.whty.euicc.rsp.packets.message.MessageHelper;
import com.whty.euicc.rsp.packets.message.request.AuthenticateClientReq;
import com.whty.euicc.rsp.packets.message.request.CancelSessionReq;
import com.whty.euicc.rsp.packets.message.request.GetBoundProfilePackageReq;
import com.whty.euicc.rsp.packets.message.request.HandleNotificationReq;
import com.whty.euicc.rsp.packets.message.request.InitiateAuthenticationReq;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;
import com.whty.euicc.tls.client.HttpsShakeHandsClient;

public class TlsManager {
	
	private static TlsManager instance;
	private String type = "application/json";
	private String initiateAuthenticationPath = "/gsma/rsp2/es9plus/initiateAuthentication";
	private String authenticateClientPath = "/gsma/rsp2/es9plus/authenticateClient";
	private String cancelSessionPath = "/gsma/rsp2/es9plus/cancelSession";
	private String getBoundProfilePackagePath = "/gsma/rsp2/es9plus/getBoundProfilePackage";
	private String handleNotificationPath = "/gsma/rsp2/es9plus/handleNotification";
	
	public static TlsManager getInstance() {
		if (instance == null) {
			instance = new TlsManager();
		}
		return instance;
	}
	
	/**
	 * ES9+.initiateAuthentication
	 * @param euiccChallenge
	 * @param euiccInfo1
	 * @param smdpAddress
	 * @return
	 */
	public String initiateAuthentication(String euiccChallenge, String euiccInfo1, String smdpAddress, String ip, int port) {
		try {
			InitiateAuthenticationReq req = new InitiateAuthenticationReq();
			req.setEuiccChallenge(euiccChallenge);
			req.setEuiccInfo1(euiccInfo1);
			req.setSmdpAddress(smdpAddress);
			String body = MessageHelper.gs.toJson(req);
			String data = EuiccMsgParse.prepareHttpParam(initiateAuthenticationPath, type, body);
			String result = new HttpsShakeHandsClient().clientTestByRsp(data, ip, port);
			return toJsonString(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ES9+.authenticateClient
	 * @param transactionId
	 * @param euiccSigned1
	 * @param euiccSignature1
	 * @param euiccCertificate
	 * @param eumCertificate
	 * @return
	 */
	public String authenticateClient(String transactionId, String euiccSigned1, String euiccSignature1, 
			String euiccCertificate, String eumCertificate, String ip, int port) {
		try {
			AuthenticateClientReq req = new AuthenticateClientReq();
			req.setTransactionId(transactionId);
			req.setEuiccSigned1(euiccSigned1);
			req.setEuiccSignature1(euiccSignature1);
			req.setEuiccCertificate(euiccCertificate);
			req.setEumCertificate(eumCertificate);
			String body = MessageHelper.gs.toJson(req);
			String data = EuiccMsgParse.prepareHttpParam(authenticateClientPath, type, body);
			String result = new HttpsShakeHandsClient().clientTestByRsp(data, ip, port);
			return toJsonString(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ES9+.cancelSession
	 * @param transactionId
	 * @param cancelSessionResponse
	 * @return
	 */
	public String cancelSession(String transactionId, String euiccCancelSessionSigned, 
			String euiccCancelSessionSignature, String ip, int port) {
		try {
			CancelSessionReq req = new CancelSessionReq();
			req.setTransactionId(transactionId);
			req.setEuiccCancelSessionSigned(euiccCancelSessionSigned);
			req.setEuiccCancelSessionSignature(euiccCancelSessionSignature);
			String body = MessageHelper.gs.toJson(req);
			String data = EuiccMsgParse.prepareHttpParam(cancelSessionPath, type, body);
			String result = new HttpsShakeHandsClient().clientTestByRsp(data, ip, port);
			return toJsonString(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ES9+.getBoundProfilePackage
	 * @param transactionId
	 * @param euiccSigned2
	 * @param euiccSignature2
	 * @return
	 */
	public String getBoundProfilePackage(String transactionId, String euiccSigned2, String euiccSignature2, String ip, int port) {
		try {
			GetBoundProfilePackageReq req = new GetBoundProfilePackageReq();
			req.setTransactionId(transactionId);
			req.setEuiccSigned2(euiccSigned2);
			req.setEuiccSignature2(euiccSignature2);
			String body = MessageHelper.gs.toJson(req);
			String data = EuiccMsgParse.prepareHttpParam(getBoundProfilePackagePath, type, body);
			String result = new HttpsShakeHandsClient().clientTestByRsp(data, ip, port);
			return toJsonString(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ES9+.handleNotification
	 * @param pendingNotification
	 * @return
	 */
	public String handleNotification(String profileInstallationResultData, String euiccSignPIR, String ip, int port) {
		try {
			HandleNotificationReq req = new HandleNotificationReq();
			req.setProfileInstallationResultData(profileInstallationResultData);
			req.setEuiccSignPIR(euiccSignPIR);
			String body = MessageHelper.gs.toJson(req);
			String data = EuiccMsgParse.prepareHttpParam(handleNotificationPath, type, body);
			String result = new HttpsShakeHandsClient().clientTestByRsp(data, ip, port);
			return toJsonString(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 截取返回结果
	 * @param msg
	 * @return
	 */
	private String toJsonString(String msg) {
		int index = msg.indexOf("{");
		return msg.substring(index);
	}
}
