package com.whty.euicc.data.pojo;

public class CardInfo {
	
	private String eid;
	
	private String eumId;

	private String productiondate;
	
	private String platformtype;
	
	private String platformversion;
	
	private int remainingmemory;
	
	private int availablememoryforprofiles;
	
	private String smsrId;
	
	private String ecasdId;
	
	private String phoneNo;

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}
	
	public String getEumId() {
		return eumId;
	}

	public void setEumId(String eumId) {
		this.eumId = eumId;
	}

	public String getProductiondate() {
		return productiondate;
	}

	public void setProductiondate(String productiondate) {
		this.productiondate = productiondate;
	}

	public String getPlatformtype() {
		return platformtype;
	}

	public void setPlatformtype(String platformtype) {
		this.platformtype = platformtype;
	}

	public String getPlatformversion() {
		return platformversion;
	}

	public void setPlatformversion(String platformversion) {
		this.platformversion = platformversion;
	}

	public int getRemainingmemory() {
		return remainingmemory;
	}

	public void setRemainingmemory(int remainingmemory) {
		this.remainingmemory = remainingmemory;
	}

	public int getAvailablememoryforprofiles() {
		return availablememoryforprofiles;
	}

	public void setAvailablememoryforprofiles(int availablememoryforprofiles) {
		this.availablememoryforprofiles = availablememoryforprofiles;
	}

	public String getSmsrId() {
		return smsrId;
	}

	public void setSmsrId(String smsrId) {
		this.smsrId = smsrId;
	}

	public String getEcasdId() {
		return ecasdId;
	}

	public void setEcasdId(String ecasdId) {
		this.ecasdId = ecasdId;
	}
	
	public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo == null ? null : phoneNo.trim();
    }
}
