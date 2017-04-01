package com.whty.tool.bean;

public class PositionBean {
	private String  valueLength;
	
	private int currPosition;
	
	
	public String getValueLength() {
		return valueLength;
	}
	public void setValueLength(String valueLength) {
		this.valueLength = valueLength;
	}

	public int getCurrPosition() {
		return currPosition;
	}
	public void setCurrPosition(int currPosition) {
		this.currPosition = currPosition;
	}
	
	public PositionBean(String valueLength, int currPosition) {
		super();
		this.valueLength = valueLength;
		this.currPosition = currPosition;
	}
}
