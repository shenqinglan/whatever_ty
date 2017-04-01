// Copyright (C) 2012 WHTY
package com.whty.euicc.base.pojo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ApInfoUser implements Serializable {

    private String spName;
    private String spCode;
    private String linker;
    private String phone;
    private String address;
    private String zipcode;
    private String status;
    private String userId;
    private String regCode;
    private Date regCodeDate;
    private String filePath;
    private String fileName;
    private String account;

    private String centerAddr;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }


    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public String getSpCode() {
        return spCode;
    }

    public void setSpCode(String spCode) {
        this.spCode = spCode;
    }

    public String getLinker() {
        return linker;
    }

    public void setLinker(String linker) {
        this.linker = linker;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRegCode() {
        return regCode;
    }

    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }

    public Date getRegCodeDate() {
        return regCodeDate;
    }

    public void setRegCodeDate(Date regCodeDate) {
        this.regCodeDate = regCodeDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCenterAddr() {
        return centerAddr;
    }

    public void setCenterAddr(String centerAddr) {
        this.centerAddr = centerAddr;
    }
}
