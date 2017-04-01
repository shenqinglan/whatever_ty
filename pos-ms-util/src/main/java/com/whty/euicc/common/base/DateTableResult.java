package com.whty.euicc.common.base;

public class DateTableResult {
	
	public DateTableResult(Object data, String draw, int recordsTotal){
		this.data = data;
		this.draw = draw;
		this.recordsTotal = recordsTotal;
		this.recordsFiltered = recordsTotal;
	}

	private Object data;
	private String draw;
	private int recordsTotal;
	private int recordsFiltered;
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getDraw() {
		return draw;
	}
	public void setDraw(String draw) {
		this.draw = draw;
	}
	public int getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public int getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
}
