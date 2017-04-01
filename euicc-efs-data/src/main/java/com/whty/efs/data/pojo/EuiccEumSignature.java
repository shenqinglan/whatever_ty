package com.whty.efs.data.pojo;

public class EuiccEumSignature {
    private String signatureId;

    private String singedInfoId;

    private String keyInfoId;

    private String id;

    private String valueTypeValue;

    private String valueTypeId;

    public String getSignatureId() {
        return signatureId;
    }

    public void setSignatureId(String signatureId) {
        this.signatureId = signatureId == null ? null : signatureId.trim();
    }

    public String getSingedInfoId() {
        return singedInfoId;
    }

    public void setSingedInfoId(String singedInfoId) {
        this.singedInfoId = singedInfoId == null ? null : singedInfoId.trim();
    }

    public String getKeyInfoId() {
        return keyInfoId;
    }

    public void setKeyInfoId(String keyInfoId) {
        this.keyInfoId = keyInfoId == null ? null : keyInfoId.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getValueTypeValue() {
        return valueTypeValue;
    }

    public void setValueTypeValue(String valueTypeValue) {
        this.valueTypeValue = valueTypeValue == null ? null : valueTypeValue.trim();
    }

    public String getValueTypeId() {
        return valueTypeId;
    }

    public void setValueTypeId(String valueTypeId) {
        this.valueTypeId = valueTypeId == null ? null : valueTypeId.trim();
    }
}