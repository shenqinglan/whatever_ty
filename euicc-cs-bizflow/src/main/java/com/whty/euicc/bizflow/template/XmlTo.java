// Copyright (C) 2012 WHTY
package com.whty.euicc.bizflow.template;

import java.io.Serializable;


public class XmlTo implements Serializable{
	private static final long serialVersionUID = -3331000882494497175L;
	
	private String command;
    private String description;
    private String code;

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

	public void setCommand(String command) {
		this.command = command;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
