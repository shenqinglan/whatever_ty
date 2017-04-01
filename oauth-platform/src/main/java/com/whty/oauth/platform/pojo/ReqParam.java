package com.whty.oauth.platform.pojo;

/**
 * @ClassName ReqParam
 * @author Administrator
 * @date 2017-3-8 上午11:17:59
 * @Description TODO(请求参数封装)
 */
public class ReqParam {

	private String phoneNo;
	private String format;
	private String authType;
	private String authData;
	private String transactionId;
	private String userType;
	
	/**
	 * @return the phoneNo
	 */
	public String getPhoneNo() {
		return phoneNo;
	}
	/**
	 * @param phoneNo the phoneNo to set
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}
	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}
	/**
	 * @return the authType
	 */
	public String getAuthType() {
		return authType;
	}
	/**
	 * @param authType the authType to set
	 */
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	/**
	 * @return the authData
	 */
	public String getAuthData() {
		return authData;
	}
	/**
	 * @param authData the authData to set
	 */
	public void setAuthData(String authData) {
		this.authData = authData;
	}
	/**
	 * @return the trasactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}
	/**
	 * @param trasactionId the trasactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}
	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
}
