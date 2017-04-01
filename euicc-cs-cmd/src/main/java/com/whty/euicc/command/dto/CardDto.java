package com.whty.euicc.command.dto;

/**
 * cardinfo 对应的 dto对象
 * 
 * @author dengzm category:卡片类型（正式卡片、测试卡片） batch:卡批次
 *         cardType:卡类型(swp、ese、tcore、sd) isd:主安全域 initKeyVer:初始密钥版本
 *
 */
public class CardDto {

	public CardDto(String isdAID, String keyVer) {
		this.isdAID = isdAID;
		this.keyVer = keyVer;
	}

	public CardDto(String category, String batch, String isdAID,
			String cardType, String status, String rsaAlg, String keyVer) {
		this.category = category;
		this.batch = batch;
		this.cardType = cardType;
		this.isdAID = isdAID;
		this.status = status;
		this.rsaAlg = rsaAlg;
		this.keyVer = keyVer;
	}

	/**
	 * 卡片类型:正式卡片、测试卡片
	 */
	private String category;
	/**
	 * 
	 */
	private String batch;
	/**
	 * isd
	 */
	private String isdAID;

	/**
	 * 卡类型
	 */
	private String cardType;

	/**
	 * 卡状态
	 */
	private String status;

	/**
	 * Rsa算法
	 */
	private String rsaAlg;

	/**
	 * 版本号
	 */
	private String keyVer;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getIsdAID() {
		return isdAID;
	}

	public void setIsdAID(String isdAID) {
		this.isdAID = isdAID;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRsaAlg() {
		return rsaAlg;
	}

	public void setRsaAlg(String rsaAlg) {
		this.rsaAlg = rsaAlg;
	}

	/**
	 * @return the keyVer
	 */
	public String getKeyVer() {
		return keyVer;
	}

	/**
	 * @param keyVer
	 *            the keyVer to set
	 */
	public void setKeyVer(String keyVer) {
		this.keyVer = keyVer;
	}

}
