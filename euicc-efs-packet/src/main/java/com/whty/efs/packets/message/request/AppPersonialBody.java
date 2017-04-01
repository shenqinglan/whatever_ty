package com.whty.efs.packets.message.request;

import java.util.List;

import com.whty.efs.packets.message.MsgType;
import com.whty.efs.packets.message.request.attr.WithAppAidVersion_MsgBody;

/**
 * 应用个人化请求报文体
 * @author gaofeng
 *
 */
@MsgType("tath.008.002.01")
public class AppPersonialBody extends RequestMsgBody implements WithAppAidVersion_MsgBody{

	/**合作机构编码*/
	private String partnerOrgCode   ;
	/**应用AID*/
	private String appAID  ;
	/**应用版本*/
	private String appVersion  ;
	/**卡号*/
	private String cardNO   ;
	/**主账号*/
	private String pan  ;
	/**ATS
	 * 初次请求时必须上传，占用16字节，COS标识、芯片厂商标识、COS版本、UID
	 * */
	private String ats;
	/**持卡人姓名*/
	private String cardholderName  ;
	/**证件类型01-身份证; 02-军官证; 03-护照; 04-回乡证; 05-台胞证; 06-警官证; 07-士兵证; 99-其他以10进制形式表示*/
	private String identity  ;
	/**证件号码*/
	private String identityNumber  ;
	/**手机号码*/
	private Integer phoneNumber  ;
	/**设备标识 初次请求时必须上传，手机IMEI国际移动设备身份码的缩写，国际移动装备辨识码，是由15位数字组成的"电子串号"*/
	private String deviceID  ;
	/**手机型号*/
	private String phoneModel  ;
	/**响应APDU脚本*/
	private List<Rapdu> rApdu;
	/** 申请单号 */
	private String processId;
	/**指令类型*/
	private String apduType;
	
	public String getAts() {
		return ats;
	}
	public void setAts(String ats) {
		this.ats = ats;
	}
	
	public String getPartnerOrgCode() {
		return partnerOrgCode;
	}
	public void setPartnerOrgCode(String partnerOrgCode) {
		this.partnerOrgCode = partnerOrgCode;
	}
	public String getAppAID() {
		return appAID;
	}
	public void setAppAID(String appAID) {
		this.appAID = appAID;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getCardNO() {
		return cardNO;
	}
	public void setCardNO(String cardNO) {
		this.cardNO = cardNO;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getCardholderName() {
		return cardholderName;
	}
	public void setCardholderName(String cardholderName) {
		this.cardholderName = cardholderName;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getIdentityNumber() {
		return identityNumber;
	}
	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}
	public Integer getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(Integer phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	public String getPhoneModel() {
		return phoneModel;
	}
	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}
	public List<Rapdu> getrApdu() {
		return rApdu;
	}
	public void setrApdu(List<Rapdu> rApdu) {
		this.rApdu = rApdu;
	}
	
	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
//		sb.append("&seId=").append(seId);
//		sb.append("&appAid=").append(appAid);
//		sb.append("&appVersion=").append(appVersion);
//		sb.append("&seResponseApduList=").append(seResponseApduList);
//		sb.append("&apdu=").append(apdu);
//		sb.append("&processId=").append(processId);
		return sb.toString();
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getApduType() {
		return apduType;
	}
	public void setApduType(String apduType) {
		this.apduType = apduType;
	}
}
