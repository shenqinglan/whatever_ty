// Copyright (C) 2012 WHTY
package com.whty.euicc.bizflow.template;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BizTemplate implements Serializable{

	private static final long serialVersionUID = -2020143833791895628L;
	
	private String name;
	private Boolean status;
	private List<XmlStep> xmlStep;
	private int stepIndex = -1;

	public BizTemplate() {
		this.xmlStep = new ArrayList<XmlStep>();
	}

	public void addXmlStep(XmlStep xmlStep) {
		this.xmlStep.add(++this.stepIndex, xmlStep);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public Boolean getStatus() {
		return status;
	}

	public List<XmlStep> getXmlStep() {
		return xmlStep;
	}

	public XmlStep getCurXmlStep() {
		if (this.stepIndex == -1) {
			return null;
		}
		return this.xmlStep.get(stepIndex);
	}
}
