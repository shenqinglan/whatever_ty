package com.whty.euicc.data.pojo;

public class RootKeyInfo {
    private String rootKeyId;

    private String companyName;

    private String rootKey;

    public String getRootKeyId() {
        return rootKeyId;
    }

    public void setRootKeyId(String rootKeyId) {
        this.rootKeyId = rootKeyId == null ? null : rootKeyId.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getRootKey() {
        return rootKey;
    }

    public void setRootKey(String rootKey) {
        this.rootKey = rootKey == null ? null : rootKey.trim();
    }
}