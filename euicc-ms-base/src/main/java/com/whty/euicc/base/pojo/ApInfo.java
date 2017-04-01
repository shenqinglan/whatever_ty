package com.whty.euicc.base.pojo;

import java.util.Date;

public class ApInfo {
    private String userId;

    private String spName;

    private String spCode;

    private String linker;

    private String phone;

    private String address;

    private String zipcode;

    private String status;

    private String regCode;

    private Date regCodeDate;

    private String filePath;

    private String centerAddr;

    private String fileName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName == null ? null : spName.trim();
    }

    public String getSpCode() {
        return spCode;
    }

    public void setSpCode(String spCode) {
        this.spCode = spCode == null ? null : spCode.trim();
    }

    public String getLinker() {
        return linker;
    }

    public void setLinker(String linker) {
        this.linker = linker == null ? null : linker.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode == null ? null : zipcode.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRegCode() {
        return regCode;
    }

    public void setRegCode(String regCode) {
        this.regCode = regCode == null ? null : regCode.trim();
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
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getCenterAddr() {
        return centerAddr;
    }

    public void setCenterAddr(String centerAddr) {
        this.centerAddr = centerAddr == null ? null : centerAddr.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }
}