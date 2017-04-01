package com.whty.euicc.base.pojo;

public class ApprovalUserInfo {
    private String approvalUserId;

    private Integer totalApprovalAmount;

    private Integer approvalAmount;

    private Integer realAmount;

    private String approvalState;
    
    private String userAccount;

    public String getApprovalUserId() {
        return approvalUserId;
    }

    public void setApprovalUserId(String approvalUserId) {
        this.approvalUserId = approvalUserId == null ? null : approvalUserId.trim();
    }

    public Integer getTotalApprovalAmount() {
        return totalApprovalAmount;
    }

    public void setTotalApprovalAmount(Integer totalApprovalAmount) {
        this.totalApprovalAmount = totalApprovalAmount;
    }

    public Integer getApprovalAmount() {
        return approvalAmount;
    }

    public void setApprovalAmount(Integer approvalAmount) {
        this.approvalAmount = approvalAmount;
    }

    public Integer getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(Integer realAmount) {
        this.realAmount = realAmount;
    }

    public String getApprovalState() {
        return approvalState;
    }

    public void setApprovalState(String approvalState) {
        this.approvalState = approvalState == null ? null : approvalState.trim();
    }

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
}