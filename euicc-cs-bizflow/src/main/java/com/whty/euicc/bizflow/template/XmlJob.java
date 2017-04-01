// Copyright (C) 2012 WHTY
package com.whty.euicc.bizflow.template;

import java.io.Serializable;

public class XmlJob implements Serializable{

	private static final long serialVersionUID = 1871712328676990270L;
	
	private String description;
	private String code;

	public String getDescription() {
		return description;
	}

	public String getCode() {
		return code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
