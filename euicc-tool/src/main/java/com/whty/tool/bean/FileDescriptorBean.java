package com.whty.tool.bean;

public class FileDescriptorBean {
	private String recordNum ;
	private String nextStr;
	public FileDescriptorBean(String recordNum, String nextStr) {
		super();
		this.recordNum = recordNum;
		this.nextStr = nextStr;
	}
	
	public String getRecordNum() {
		return recordNum;
	}
	public void setRecordNum(String recordNum) {
		this.recordNum = recordNum;
	}
	public String getNextStr() {
		return nextStr;
	}
	public void setNextStr(String nextStr) {
		this.nextStr = nextStr;
	}


}
