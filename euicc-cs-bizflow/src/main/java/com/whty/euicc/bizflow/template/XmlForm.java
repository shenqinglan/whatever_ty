// Copyright (C) 2012 WHTY
package com.whty.euicc.bizflow.template;

import java.io.Serializable;

public class XmlForm implements Serializable {
	private static final long serialVersionUID = -47427154471754119L;
	
	private String command;
    private String description;
    private String code;
    private String last;

    public XmlForm() {
        super();
    }

    public XmlForm(String command, String description,
            String code, String last) {
        super();
        this.command = command;
        this.description = description;
        this.code = code;
        this.last = last;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
