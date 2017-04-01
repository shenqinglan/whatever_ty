// Copyright (C) 2012 WHTY
package com.whty.euicc.bizflow.template;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class XmlStep implements Serializable{
	private static final long serialVersionUID = -2599475158816893559L;
	// xmlStep所在的模版名称
    private String xmlFileName;
    private String stepid;
    private String name;
    private XmlForm xmlForm;
    private XmlJob xmlJob;
    private XmlTo xmlTo;
    private Map<String,XmlResult> resultMap;

    public XmlStep() {
        resultMap  = new HashMap<String, XmlResult>();
    }

    public String getStepid() {
        return stepid;
    }

    public void setStepid(String stepid) {
        this.stepid = stepid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public XmlForm getXmlForm() {
        return xmlForm;
    }

    public void setXmlForm(XmlForm xmlForm) {
        this.xmlForm = xmlForm;
    }

    public XmlJob getXmlJob() {
        return xmlJob;
    }

    public void setXmlJob(XmlJob xmlJob) {
        this.xmlJob = xmlJob;
    }

    public XmlTo getXmlTo() {
        return xmlTo;
    }

    public void setXmlTo(XmlTo xmlTo) {
        this.xmlTo = xmlTo;
    }

    public XmlResult getXmlResult(String status) {
        return this.resultMap.get(status);
    }

    public void addXmlResult(XmlResult xmlResult) {
        this.resultMap.put(xmlResult.getStatus(),xmlResult);
    }

    public String getXmlFileName() {
        return xmlFileName;
    }

    public void setXmlFileName(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

}
