package com.whty.euicc.base.dto;

/**
 * 操作日志查询条件
 * @ClassName: BaseLogsCondDto
 * @author: xlping
 * @since: 2015年7月16日
 * @Description:TODO
 */
public class BaseLogsCondDto {
	
	private String userId;

	private String userAccount;
	
	private String dayStart;
	
	private String dayEnd;

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getDayStart() {
		return dayStart;
	}

	public void setDayStart(String dayStart) {
		this.dayStart = dayStart;
	}

	public String getDayEnd() {
		return dayEnd;
	}

	public void setDayEnd(String dayEnd) {
		this.dayEnd = dayEnd;
	}	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
