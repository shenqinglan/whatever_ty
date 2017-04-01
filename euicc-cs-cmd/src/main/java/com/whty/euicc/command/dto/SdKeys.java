package com.whty.euicc.command.dto;


public class SdKeys {
	/**
	 * token密钥版本
	 */
	private String sTokenVer;
	/**
	 * token密钥
	 */
	private String sTokenId;
	/**
	 * dap密钥版本 dap:
	 */
	private String sDapVer;
	/**
	 * dap密钥
	 */
	private String sDapId;

	/**
	 * 安全域内部密钥
	 */
	private String sEncId;
	private String sEncVer;
	private String sMacId;
	private String sMacVer;
	private String sDekId;
	private String sDekVer;


	public SdKeys(String sTokenId, String sTokenVer, String sDapId, String sDapVer, String sEncId, String sEncVer, String sMacId, String sMacVer, String sDekId, String sDekVer) {
		this.sTokenId = sTokenId;
		this.sTokenVer = sTokenVer;
		this.sDapId = sDapId;
		this.sDapVer = sDapVer;
		this.sEncId = sEncId;
		this.sEncVer = sEncVer;
		this.sMacId = sMacId;
		this.sMacVer = sMacVer;
		this.sDekId = sDekId;
		this.sDekVer = sDekVer;
	}

	public String getsTokenVer() {
		return sTokenVer;
	}

	public void setsTokenVer(String sTokenVer) {
		this.sTokenVer = sTokenVer;
	}

	public String getsTokenId() {
		return sTokenId;
	}

	public void setsTokenId(String sTokenId) {
		this.sTokenId = sTokenId;
	}

	public String getsDapVer() {
		return sDapVer;
	}

	public void setsDapVer(String sDapVer) {
		this.sDapVer = sDapVer;
	}

	public String getsDapId() {
		return sDapId;
	}

	public void setsDapId(String sDapId) {
		this.sDapId = sDapId;
	}

	public String getsEncId() {
		return sEncId;
	}

	public void setsEncId(String sEncId) {
		this.sEncId = sEncId;
	}

	public String getsEncVer() {
		return sEncVer;
	}

	public void setsEncVer(String sEncVer) {
		this.sEncVer = sEncVer;
	}

	public String getsMacId() {
		return sMacId;
	}

	public void setsMacId(String sMacId) {
		this.sMacId = sMacId;
	}

	public String getsMacVer() {
		return sMacVer;
	}

	public void setsMacVer(String sMacVer) {
		this.sMacVer = sMacVer;
	}

	public String getsDekId() {
		return sDekId;
	}

	public void setsDekId(String sDekId) {
		this.sDekId = sDekId;
	}

	public String getsDekVer() {
		return sDekVer;
	}

	public void setsDekVer(String sDekVer) {
		this.sDekVer = sDekVer;
	}

}
