package com.whty.euicc.tls.Bean;

public class TLVBean {
	private String taglen;
	private String value;
	
	public TLVBean(String taglen, String value) {
		super();
		this.taglen = taglen;
		this.value = value;
	}
	public String getTaglen() {
		return taglen;
	}
	public void setTaglen(String taglen) {
		this.taglen = taglen;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
