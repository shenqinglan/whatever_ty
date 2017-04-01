package com.whty.euicc.data.pojo;

import java.util.List;

public class EuiccCardExcelInfo {
	private List<EuiccCard> euiccCardList;
	private List<Scp80Info> scp80InfoList;
	private List<Scp81Info> scp81InfoList;
	private List<Scp03Info> scp03InfoList;
	private List<IsdPInfo> isdPInfoList;
	
	public List<EuiccCard> getEuiccCardList() {
		return euiccCardList;
	}
	public void setEuiccCardList(List<EuiccCard> euiccCardList) {
		this.euiccCardList = euiccCardList;
	}
	public List<Scp80Info> getScp80InfoList() {
		return scp80InfoList;
	}
	public void setScp80InfoList(List<Scp80Info> scp80InfoList) {
		this.scp80InfoList = scp80InfoList;
	}
	public List<Scp81Info> getScp81InfoList() {
		return scp81InfoList;
	}
	public void setScp81InfoList(List<Scp81Info> scp81InfoList) {
		this.scp81InfoList = scp81InfoList;
	}
	public List<Scp03Info> getScp03InfoList() {
		return scp03InfoList;
	}
	public void setScp03InfoList(List<Scp03Info> scp03InfoList) {
		this.scp03InfoList = scp03InfoList;
	}
	public List<IsdPInfo> getIsdPInfoList() {
		return isdPInfoList;
	}
	public void setIsdPInfoList(List<IsdPInfo> isdPInfoList) {
		this.isdPInfoList = isdPInfoList;
	}
}
