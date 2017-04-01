package com.whty.euicc.data.pojo;

// Copyright (C) 2012 WHTY

import java.io.Serializable;


public class LogsUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private String opType;

    private String opDate;

    private String remark;

    private String account;

    private String type;


    private String startTime;

    private String endTime;

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public String getOpDate() {
        return opDate;
    }

    public void setOpDate(String opDate) {
        this.opDate = opDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public Long getAppId() {
//        return appId;
//    }
//
//    public void setAppId(Long appId) {
//        this.appId = appId;
//    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
