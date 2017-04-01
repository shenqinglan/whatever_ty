// Copyright (C) 2012 WHTY
package com.whty.euicc.bizflow.template;

import java.io.Serializable;


public class XmlResult implements Serializable{
	private static final long serialVersionUID = -2668141988295127555L;
	
	private String description;
    private String status;
    private String step;

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getStep() {
        return step;
    }

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStep(String step) {
		this.step = step;
	}

}
