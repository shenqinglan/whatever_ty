package com.whty.efs.data.pojo;

public class EuiccReferenceTransformType {
    private String transformId;

    private String referenceId;

    private String xpath;

    private String algorithm;

    public String getTransformId() {
        return transformId;
    }

    public void setTransformId(String transformId) {
        this.transformId = transformId == null ? null : transformId.trim();
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId == null ? null : referenceId.trim();
    }

    public String getXpath() {
        return xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath == null ? null : xpath.trim();
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm == null ? null : algorithm.trim();
    }
}