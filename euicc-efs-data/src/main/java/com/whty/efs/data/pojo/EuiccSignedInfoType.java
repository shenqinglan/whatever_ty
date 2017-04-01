package com.whty.efs.data.pojo;

public class EuiccSignedInfoType {
    private String signedInfoId;

    private String signatureHMacOutputLength;

    private String signatureAlgorithm;

    private String id;

    public String getSignedInfoId() {
        return signedInfoId;
    }

    public void setSignedInfoId(String signedInfoId) {
        this.signedInfoId = signedInfoId == null ? null : signedInfoId.trim();
    }

    public String getSignatureHMacOutputLength() {
        return signatureHMacOutputLength;
    }

    public void setSignatureHMacOutputLength(String signatureHMacOutputLength) {
        this.signatureHMacOutputLength = signatureHMacOutputLength == null ? null : signatureHMacOutputLength.trim();
    }

    public String getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    public void setSignatureAlgorithm(String signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm == null ? null : signatureAlgorithm.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
}