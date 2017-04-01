// Copyright (C) 2012 WHTY
package com.whty.euicc.command.dto;

public class KeySessionInfo {

	/** 主机8字节随机数 */
	private String hostrand;

	/** C-MAC会话密钥 */
	private String sessionMac;

	/** 加密会话密钥 */
	private String sessionEnc;

	/** 数据加密会话密钥 */
	private String sessionDek;

	/** 最新ICV 默认为8字节的0x00 */
	private String icv;

	/** 计算MAC的初始值是否动态变化，false 固定为设置值，true 动态变化 (后续计算使用前一次得到的MAC作为初始值 默认) */
	private boolean variable;

	/** 安全级别 目前只支持0x00、0x01、0x03三种安全级别 */
	private int security;

	/** 安全通道协议 目前只支持0x01、0x02 */
	private int protocol;

	/** 安全策略 目前只支持0x05、0x15 */
	private int i;

	/** 主安全域AID */
	private String isdAid;

	/** 静态密钥DEK */
	private String kdek;

	private String sessionMacDiv;
	private String sessionEncDiv;
	private String sessionDekDiv;
	private String sessionFlag;
	private String keyVer;// 密钥版本号
	private String category;

	/**
	 * @return 主机8字节随机数
	 */
	public String getHostrand() {
		return hostrand;
	}

	/**
	 * @param hostrand
	 *            主机8字节随机数
	 */
	public void setHostrand(String hostrand) {
		this.hostrand = hostrand;
	}

	/**
	 * @return C-MAC会话密钥
	 */
	public String getSessionMac() {
		return sessionMac;
	}

	/**
	 * @param sessionMac
	 *            C-MAC会话密钥
	 */
	public void setSessionMac(String sessionMac) {
		this.sessionMac = sessionMac;
	}

	/**
	 * @return 加密会话密钥
	 */
	public String getSessionEnc() {
		return sessionEnc;
	}

	/**
	 * @param sessionEnc
	 *            加密会话密钥
	 */
	public void setSessionEnc(String sessionEnc) {
		this.sessionEnc = sessionEnc;
	}

	/**
	 * @return 数据加密会话密钥
	 */
	public String getSessionDek() {
		return sessionDek;
	}

	/**
	 * @param sessionDek
	 *            数据加密会话密钥
	 */
	public void setSessionDek(String sessionDek) {
		this.sessionDek = sessionDek;
	}

	/**
	 * @return 最新ICV 默认为8字节的0x00
	 */
	public String getIcv() {
		return icv;
	}

	/**
	 * @param icv
	 *            最新ICV 默认为8字节的0x00
	 */
	public void setIcv(String icv) {
		this.icv = icv;
	}

	/**
	 * 计算MAC的初始值是否动态变化<br>
	 * 
	 * @return false 固定为设置值<br>
	 *         true 动态变化 (后续计算使用前一次得到的MAC作为初始值 默认)
	 */
	public boolean isVariable() {
		return variable;
	}

	/**
	 * @param variable
	 *            计算MAC的初始值是否动态变化，false 固定为设置值，true 动态变化 (后续计算使用前一次得到的MAC作为初始值 默认)
	 */
	public void setVariable(boolean variable) {
		this.variable = variable;
	}

	/**
	 * @return 安全级别 目前只支持0x00、0x01、0x03三种安全级别
	 */
	public int getSecurity() {
		return security;
	}

	/**
	 * @param security
	 *            安全级别 目前只支持0x00、0x01、0x03三种安全级别
	 */
	public void setSecurity(int security) {
		this.security = security;
	}

	/**
	 * @return 安全通道协议 目前只支持0x01、0x02
	 */
	public int getProtocol() {
		return protocol;
	}

	/**
	 * @param protocol
	 *            安全通道协议 目前只支持0x01、0x02
	 */
	public void setProtocol(int protocol) {
		this.protocol = protocol;
	}

	/**
	 * @return 安全策略 目前只支持0x05、0x15
	 */
	public int getI() {
		return i;
	}

	/**
	 * @param i
	 *            安全策略 目前只支持0x05、0x15
	 */
	public void setI(int i) {
		this.i = i;
	}

	public String getIsdAid() {
		return isdAid;
	}

	public void setIsdAid(String isdAid) {
		this.isdAid = isdAid;
	}

	public String getKdek() {
		return kdek;
	}

	public void setKdek(String kdek) {
		this.kdek = kdek;
	}

	public String getSessionMacDiv() {
		return sessionMacDiv;
	}

	public void setSessionMacDiv(String sessionMacDiv) {
		this.sessionMacDiv = sessionMacDiv;
	}

	public String getSessionEncDiv() {
		return sessionEncDiv;
	}

	public void setSessionEncDiv(String sessionEncDiv) {
		this.sessionEncDiv = sessionEncDiv;
	}

	public String getSessionDekDiv() {
		return sessionDekDiv;
	}

	public void setSessionDekDiv(String sessionDekDiv) {
		this.sessionDekDiv = sessionDekDiv;
	}

	public String getSessionFlag() {
		return sessionFlag;
	}

	public void setSessionFlag(String sessionFlag) {
		this.sessionFlag = sessionFlag;
	}

	public String getKeyVer() {
		return keyVer;
	}

	public void setKeyVer(String keyVer) {
		this.keyVer = keyVer;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
