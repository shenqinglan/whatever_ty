package com.whty.euicc.data.pojo;

import com.whty.euicc.common.constant.SmsTriggerState;

public class SmsTrigger {
	
	private String eid;
	private String phoneNo;
	private String iccid;
	private String isdPAid;
	private String eventType;//实际路由处理类
	private String noticeType;//卡片通知处理类型
	private String noticeConstant;//卡片通知内容
	private String state=SmsTriggerState.BEGIN;
	private String smsState=SmsTriggerState.BEGIN;;
	private int step=0;
	private int allStep=0;
	private String data;
	private String profileData;
	private int profileNum=1;
	private String seqCounter;//序列计数器
	private String smsCenterNo;//短信中心号码
	
	private String tpud;//短信内容

	private boolean processResult;
	private String errorMsg;
	
	
	/*person isdp cert*/
	private String eskDp;
	private String epkDp;
	private String certDpEcdsa;
	private String epkDpEcka;
	private String signature;
	
	/*sm-sr change cert*/
	private String eskSr;
	private String epkSr;
	private String certSrEcdsa;
	private String epkSrEcka;
	
	/*SM-SR Addressing Parameters Update*/
	private String rId;
	private String isdRAid;
	private String srAddressPara;
	
    public String getrId() {
		return rId;
	}
	public void setrId(String rId) {
		this.rId = rId;
	}
	public String getIsdRAid() {
		return isdRAid;
	}
	public void setIsdRAid(String isdRAid) {
		this.isdRAid = isdRAid;
	}
	public String getSrAddressPara() {
		return srAddressPara;
	}
	public void setSrAddressPara(String srAddressPara) {
		this.srAddressPara = srAddressPara;
	}
	
    	public String getSeqCounter() {
		return seqCounter;
	}
	public void setSeqCounter(String seqCounter) {
		this.seqCounter = seqCounter;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSmsState() {
		return smsState;
	}
	public void setSmsState(String smsState) {
		this.smsState = smsState;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public int getAllStep() {
		return allStep;
	}
	public void setAllStep(int allStep) {
		this.allStep = allStep;
	}
	public String getIsdPAid() {
		return isdPAid;
	}
	public void setIsdPAid(String isdPAid) {
		this.isdPAid = isdPAid;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public boolean isProcessResult() {
		return processResult;
	}
	public void setProcessResult(boolean processResult) {
		this.processResult = processResult;
	}
	public String getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	public String getNoticeConstant() {
		return noticeConstant;
	}
	public void setNoticeConstant(String noticeConstant) {
		this.noticeConstant = noticeConstant;
	}
	public String getEskDp() {
		return eskDp;
	}
	public void setEskDp(String eskDp) {
		this.eskDp = eskDp;
	}
	public String getEpkDp() {
		return epkDp;
	}
	public void setEpkDp(String epkDp) {
		this.epkDp = epkDp;
	}
	public String getCertDpEcdsa() {
		return certDpEcdsa;
	}
	public void setCertDpEcdsa(String certDpEcdsa) {
		this.certDpEcdsa = certDpEcdsa;
	}
	public String getEpkDpEcka() {
		return epkDpEcka;
	}
	public void setEpkDpEcka(String epkDpEcka) {
		this.epkDpEcka = epkDpEcka;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getProfileData() {
		return profileData;
	}
	public void setProfileData(String profileData) {
		this.profileData = profileData;
	}
	public int getProfileNum() {
		return profileNum;
	}
	public void setProfileNum(int profileNum) {
		this.profileNum = profileNum;
	}
	public String getEskSr() {
		return eskSr;
	}
	public void setEskSr(String eskSr) {
		this.eskSr = eskSr;
	}
	public String getEpkSr() {
		return epkSr;
	}
	public void setEpkSr(String epkSr) {
		this.epkSr = epkSr;
	}
	public String getCertSrEcdsa() {
		return certSrEcdsa;
	}
	public void setCertSrEcdsa(String certSrEcdsa) {
		this.certSrEcdsa = certSrEcdsa;
	}
	public String getEpkSrEcka() {
		return epkSrEcka;
	}
	public void setEpkSrEcka(String epkSrEcka) {
		this.epkSrEcka = epkSrEcka;
	}
	public String getSmsCenterNo() {
		return smsCenterNo;
	}
	public void setSmsCenterNo(String smsCenterNo) {
		this.smsCenterNo = smsCenterNo;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public String getTpud() {
		return tpud;
	}
	public void setTpud(String tpud) {
		this.tpud = tpud;
	}
}
