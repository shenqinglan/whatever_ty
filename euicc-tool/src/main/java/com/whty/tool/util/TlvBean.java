package com.whty.tool.util;

public class TlvBean {
	private String tag;
	private String length;
	private String value;
	public TlvBean(String tag, String length, String value) {
		super();
		this.tag = tag;
		this.length = length;
		this.value = value;
	}
	
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	

	
}
