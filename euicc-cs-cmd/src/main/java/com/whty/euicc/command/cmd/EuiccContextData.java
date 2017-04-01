package com.whty.euicc.command.cmd;

import com.whty.euicc.command.dto.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EuiccContextData {

	/**
	 * 卡端会话信息
	 */
	private KeySessionInfo keySessionInfo;
	/**
	 * db.本地应用数据
	 */
	private AppBaseInfo localeAppInfo;
	/**
	 * db.共享应用数据
	 */
	private AppBaseInfo sharedAppInfo;

	/**
	 * 所属安全域
	 */
	private String sdAid;

	/**
	 * 安全域密钥是否已被替换 默认安全域没有替换密钥
	 */
	private boolean isPutkey = false;

	/**
	 * 安全域通道
	 */
	private String scp;

	/**
	 * db.安全域信息
	 */
	private SdInfoVo sdInfo;

	/**
	 * 卡批次
	 */
	private CardBatchInfoVo cardBatchInfo;

	/**
	 * db.卡信息
	 */
	private CardDto cardDto;
	/**
	 * 密钥信息
	 */
	private SdKeys sdKeys;
	/**
	 * 卡上辅助安全域状态
	 */
	private String cardSssdStatus;
	/**
	 * 卡上应用状态
	 */
	private String cardAppStatus;

	private String lastCmd;
	private String lastCmdCode;
	/** 原子任务之间的参数传递 外部认证，主机产生的随机数 */
	private String externalAuthenticateRandom;
	/** 原子任务之间的参数传递 外部认证，卡片初始数据 */
	private String initializeData;

	private String endFlag;

	public String getEndFlag() {
		return endFlag;
	}

	public void setEndFlag(String endFlag) {
		this.endFlag = endFlag;
	}

	private String skid;

	public String getSkid() {
		return skid;
	}

	public void setSkid(String skid) {
		this.skid = skid;
	}

	private int operNum;

	public int getOperNum() {
		return operNum;
	}

	public void setOperNum(int operNum) {
		this.operNum = operNum;
	}

	public void addOperNum() {
		this.operNum = operNum + 1;
	}

	private String apduIndex;
	private String skidkey;
	private String perProecss;

	public String getApduIndex() {
		return apduIndex;
	}

	public void setApduIndex(String apduIndex) {
		this.apduIndex = apduIndex;
	}

	public String getSkidkey() {
		return skidkey;
	}

	public void setSkidkey(String skidkey) {
		this.skidkey = skidkey;
	}

	public String getPerProecss() {
		return perProecss;
	}

	public void setPerProecss(String perProecss) {
		this.perProecss = perProecss;
	}

	private String tradeNo;

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public KeySessionInfo getKeySessionInfo() {
		return keySessionInfo;
	}

	public void setKeySessionInfo(KeySessionInfo keySessionInfo) {
		this.keySessionInfo = keySessionInfo;
	}

	public AppBaseInfo getLocaleAppInfo() {
		return localeAppInfo;
	}

	public void setLocaleAppInfo(AppBaseInfo localeAppInfo) {
		this.localeAppInfo = localeAppInfo;
	}

	public AppBaseInfo getSharedAppInfo() {
		return sharedAppInfo;
	}

	public void setSharedAppInfo(AppBaseInfo sharedAppInfo) {
		this.sharedAppInfo = sharedAppInfo;
	}

	public SdInfoVo getSdInfo() {
		return sdInfo;
	}

	public void setSdInfo(SdInfoVo sdInfo) {
		this.sdInfo = sdInfo;
	}

	public String getCardSssdStatus() {
		return cardSssdStatus;
	}

	public void setCardSssdStatus(String cardSssdStatus) {
		this.cardSssdStatus = cardSssdStatus;
	}

	public String getCardAppStatus() {
		return cardAppStatus;
	}

	public void setCardAppStatus(String cardAppStatus) {
		this.cardAppStatus = cardAppStatus;
	}

	public String getLastCmd() {
		return lastCmd;
	}

	public String getLastCmdCode() {
		return lastCmdCode;
	}

	public void setLastCmd(String lastCmd, String lastCmdCode) {
		this.lastCmd = lastCmd;
		this.lastCmdCode = lastCmdCode;
	}

	public String getExternalAuthenticateRandom() {
		return externalAuthenticateRandom;
	}

	public void setExternalAuthenticateRandom(String externalAuthenticateRandom) {
		this.externalAuthenticateRandom = externalAuthenticateRandom;
	}

	public String getInitializeData() {
		return initializeData;
	}

	public void setInitializeData(String initializeData) {
		this.initializeData = initializeData;
	}

	public void clear() {
		this.localeAppInfo = null;
		this.sharedAppInfo = null;
		this.cardDto = null;
	}

	public void destory() {
		this.clear();
	}

	Map<String, String> appStatusMap = new HashMap<String, String>();

	public void setAppStatusMap(Map<String, String> paids) {
		this.appStatusMap = paids;
	}

	public Map<String, String> getAppStatusMap() {
		return this.appStatusMap;
	}

	List<String> dataList = new ArrayList<String>();

	public List<String> getDataList() {
		return dataList;
	}

	public void setDataList(List<String> dataList) {
		this.dataList = dataList;
	}

	public void addData(String data) {
		this.dataList.add(data);
	}

	public CardDto getCardDto() {
		return cardDto;
	}

	public void setCardDto(CardDto cardDto) {
		this.cardDto = cardDto;
	}

	public SdKeys getSdKeys() {
		return sdKeys;
	}

	public void setSdKeys(SdKeys sdKeys) {
		this.sdKeys = sdKeys;
	}

	public String getSdAid() {
		return sdAid;
	}

	public void setSdAid(String sdAid) {
		this.sdAid = sdAid;
	}

	public String getScp() {
		return scp;
	}

	public void setScp(String scp) {
		this.scp = scp;
	}

	public CardBatchInfoVo getCardBatchInfo() {
		return cardBatchInfo;
	}

	public void setCardBatchInfo(CardBatchInfoVo cardBatchInfo) {
		this.cardBatchInfo = cardBatchInfo;
	}

	public boolean isPutkey() {
		return isPutkey;
	}

	public void setPutkey(boolean isPutkey) {
		this.isPutkey = isPutkey;
	}

}
