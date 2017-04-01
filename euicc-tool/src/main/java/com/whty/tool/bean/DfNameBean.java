package com.whty.tool.bean;

public class DfNameBean {
	private String currString;
	public DfNameBean(String currString, String nextString) {
		super();
		this.currString = currString;
		this.nextString = nextString;
	}
	private String nextString;
	
	
	
	public String getCurrString() {
		return currString;
	}
	public void setCurrString(String currString) {
		this.currString = currString;
	}
	public String getNextString() {
		return nextString;
	}
	public void setNextString(String nextString) {
		this.nextString = nextString;
	}
	

}
