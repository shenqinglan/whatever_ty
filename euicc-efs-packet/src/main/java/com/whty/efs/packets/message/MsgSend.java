package com.whty.efs.packets.message;

public class MsgSend {
	
	private String report;
	private String src;
	private String dest;
	private String msg;
	
	/**
	 * 
	 */
	public MsgSend() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param report
	 * @param src
	 * @param dest
	 * @param msg
	 */
	public MsgSend(String report, String src, String dest, String msg) {
		super();
		this.report = report;
		this.src = src;
		this.dest = dest;
		this.msg = msg;
	}
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getDest() {
		return dest;
	}
	public void setDest(String dest) {
		this.dest = dest;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
