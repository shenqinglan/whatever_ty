package com.whty.euicc.rsp.tls;

import com.google.gson.Gson;
import com.whty.euicc.rsp.packets.message.request.AuthenticateClientReq;
import com.whty.euicc.rsp.packets.message.request.CancelSessionReq;
import com.whty.euicc.rsp.packets.message.request.GetBoundProfilePackageReq;
import com.whty.euicc.rsp.packets.message.request.HandleNotificationReq;
import com.whty.euicc.rsp.packets.message.request.InitiateAuthenticationReq;
import com.whty.euicc.rsp.packets.message.response.AuthenticateClientResp;
import com.whty.euicc.rsp.packets.message.response.GetBoundProfilePackageResp;
import com.whty.euicc.rsp.packets.message.response.InitiateAuthenticationResp;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;
import com.whty.tls.client.ecc.EccCardShakeHandsClient;

public class TlsManager {
	
	private String type = "application/json";
	private String initiateAuthenticationPath = "/gsma/rsp2/es9plus/initiateAuthentication";
	private String authenticateClientPath = "/gsma/rsp2/es9plus/authenticateClient";
	private String cancelSessionPath = "/gsma/rsp2/es9plus/cancelSession";
	private String getBoundProfilePackagePath = "/gsma/rsp2/es9plus/getBoundProfilePackage";
	private String handleNotificationPath = "/gsma/rsp2/es9plus/handleNotification";
	private String ip;
	private int port;
	private static final Gson gs = new Gson();
	
	public TlsManager(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	/**
	 * ES9+.initiateAuthentication
	 * @param euiccChallenge
	 * @param euiccInfo1
	 * @param smdpAddress
	 * @return
	 */
	public InitiateAuthenticationResp initiateAuthentication(String euiccChallenge, String euiccInfo1, String smdpAddress) {
		try {
			InitiateAuthenticationReq req = new InitiateAuthenticationReq();
			req.setEuiccChallenge(euiccChallenge);
			req.setEuiccInfo1(euiccInfo1);
			req.setSmdpAddress(smdpAddress);
			String body = gs.toJson(req);
			String data = EuiccMsgParse.prepareHttpParam(initiateAuthenticationPath, type, body);
			String data2 = EuiccMsgParse.prepareHttpParam(initiateAuthenticationPath, type, "");
			System.out.println("data: "+data);
			String result = new EccCardShakeHandsClient().clientTestByRsp(data, ip, port, data2, null);
			if (result != null && !"".equals(result)) {
				return gs.fromJson(toJsonString(result), InitiateAuthenticationResp.class);
			}
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
	public AuthenticateClientResp authenticateClient(String transactionId, String euiccSigned1, String euiccSignature1, 
			String euiccCertificate, String eumCertificate) {
		try {
			AuthenticateClientReq req = new AuthenticateClientReq();
			req.setTransactionId(transactionId);
			req.setEuiccSigned1(euiccSigned1);
			req.setEuiccSignature1(euiccSignature1);
			req.setEuiccCertificate(euiccCertificate);
			req.setEumCertificate(eumCertificate);
			String body = gs.toJson(req);
			String data = EuiccMsgParse.prepareHttpParam(authenticateClientPath, type, body);
			String data2 = EuiccMsgParse.prepareHttpParam(authenticateClientPath, type, "");
			String result = new EccCardShakeHandsClient().clientTestByRsp(data, ip, port, data2, null);
			if (result != null && !"".equals(result)) {
				return gs.fromJson(toJsonString(result), AuthenticateClientResp.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ES9+.cancelSession
	 * @param transactionId
	 * @param euiccCancelSessionSigned
	 * @param euiccCancelSessionSignature
	 * @return
	 */
	public String cancelSession(String transactionId, String euiccCancelSessionSigned, 
			String euiccCancelSessionSignature) {
		try {
			CancelSessionReq req = new CancelSessionReq();
			req.setTransactionId(transactionId);
			req.setEuiccCancelSessionSigned(euiccCancelSessionSigned);
			req.setEuiccCancelSessionSignature(euiccCancelSessionSignature);
			String body = gs.toJson(req);
			String data = EuiccMsgParse.prepareHttpParam(cancelSessionPath, type, body);
			String data2 = EuiccMsgParse.prepareHttpParam(cancelSessionPath, type, "");
			String result = new EccCardShakeHandsClient().clientTestByRsp(data, ip, port, data2, null);
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
	public GetBoundProfilePackageResp getBoundProfilePackage(String transactionId, String euiccSigned2, String euiccSignature2) {
		try {
			GetBoundProfilePackageReq req = new GetBoundProfilePackageReq();
			req.setTransactionId(transactionId);
			req.setEuiccSigned2(euiccSigned2);
			req.setEuiccSignature2(euiccSignature2);
			String body = gs.toJson(req);
			String data = EuiccMsgParse.prepareHttpParam(getBoundProfilePackagePath, type, body);
			
			GetBoundProfilePackageReq req2 = new GetBoundProfilePackageReq();
			req2.setTransactionId(transactionId);
			String body2 = gs.toJson(req2);
			String data2 = EuiccMsgParse.prepareHttpParam(getBoundProfilePackagePath, type, body2);
			
			String data3 = EuiccMsgParse.prepareHttpParam(getBoundProfilePackagePath, type, "");
			String result = new EccCardShakeHandsClient().clientTestByRsp(data, ip, port, data2, data3);
			if (result != null && !"".equals(result)) {
				return gs.fromJson(toJsonString(result), GetBoundProfilePackageResp.class);
			}
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
	public String handleNotification(String profileInstallationResultData, String euiccSignPIR) {
		try {
			HandleNotificationReq req = new HandleNotificationReq();
			req.setProfileInstallationResultData(profileInstallationResultData);
			req.setEuiccSignPIR(euiccSignPIR);
			String body = gs.toJson(req);
			String data = EuiccMsgParse.prepareHttpParam(handleNotificationPath, type, body);
			String data2 = EuiccMsgParse.prepareHttpParam(handleNotificationPath, type, "");
			String result = new EccCardShakeHandsClient().clientTestByRsp(data, ip, port, data2, null);
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
