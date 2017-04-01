package com.whty.efs.data.pojo;

public class EuiccCanonicalizationMethodType {
    private String typeId;

    private String signedInfoId;

    private String content;

    private String algorithm;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    public String getSignedInfoId() {
        return signedInfoId;
    }

    public void setSignedInfoId(String signedInfoId) {
        this.signedInfoId = signedInfoId == null ? null : signedInfoId.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm == null ? null : algorithm.trim();
    }
}