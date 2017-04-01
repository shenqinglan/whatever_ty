package com.whty.efs.data.pojo;

public class EuiccRetrievalTransformType {
    private String transformId;

    private String retrievalId;

    private String xpath;

    private String algorithm;

    public String getTransformId() {
        return transformId;
    }

    public void setTransformId(String transformId) {
        this.transformId = transformId == null ? null : transformId.trim();
    }

    public String getRetrievalId() {
        return retrievalId;
    }

    public void setRetrievalId(String retrievalId) {
        this.retrievalId = retrievalId == null ? null : retrievalId.trim();
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