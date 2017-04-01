package com.whty.efs.data.pojo;

public class EuiccReferenceType {
    private String referenceId;

    private String digestValue;

    private String id;

    private String uri;

    private String type;

    private String signedInfoId;

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId == null ? null : referenceId.trim();
    }

    public String getDigestValue() {
        return digestValue;
    }

    public void setDigestValue(String digestValue) {
        this.digestValue = digestValue == null ? null : digestValue.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri == null ? null : uri.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getSignedInfoId() {
        return signedInfoId;
    }

    public void setSignedInfoId(String signedInfoId) {
        this.signedInfoId = signedInfoId == null ? null : signedInfoId.trim();
    }
}