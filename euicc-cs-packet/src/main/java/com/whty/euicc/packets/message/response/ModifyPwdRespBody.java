package com.whty.euicc.packets.message.response;

/**
 * 密码修改响应
 * @author dengzm
 *
 */
public class ModifyPwdRespBody extends BaseRespBody{
	private String result;
	private String description;
	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
