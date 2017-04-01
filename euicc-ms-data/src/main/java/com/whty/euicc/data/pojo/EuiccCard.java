package com.whty.euicc.data.pojo;

import java.util.Date;

public class EuiccCard {
	
	private String eid;
	
	private String eumId;

	private String productiondate;
	
	private String platformtype;
	
	private String platformversion;
	
	private int remainingmemory;
	
	private int availablememoryforprofiles;
	
	private String smsrId;
	
	private String ecasdId;
	
	private String capabilityId;
	
	private String catTpSupport;
	
	private String catTpVersion;
	
	private String httpSupport;
	
	private String httpVersion;
	
	private String securePacketVersion;
	
	private String remoteProvisioningVersion;
	
	private String rId;
	
	private String isdRAid;

	private String pol1Id;
	
	private String pId;
	
	private String isdPAid;

	private String isdPState;
	
	private Date createDt;
	
	private Date updateDt;
	
	private String isdPLoadfileAid;
	
	private String isdPModuleAid;
	
	private String connectivityParam;
	
	private String certEcasdEcka;
	
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

	public String getCapabilityId() {
		return capabilityId;
	}

	public void setCapabilityId(String capabilityId) {
		this.capabilityId = capabilityId;
	}

	public String getCatTpSupport() {
		return catTpSupport;
	}

	public void setCatTpSupport(String catTpSupport) {
		this.catTpSupport = catTpSupport;
	}

	public String getCatTpVersion() {
		return catTpVersion;
	}

	public void setCatTpVersion(String catTpVersion) {
		this.catTpVersion = catTpVersion;
	}

	public String getHttpSupport() {
		return httpSupport;
	}

	public void setHttpSupport(String httpSupport) {
		this.httpSupport = httpSupport;
	}

	public String getHttpVersion() {
		return httpVersion;
	}

	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}

	public String getSecurePacketVersion() {
		return securePacketVersion;
	}

	public void setSecurePacketVersion(String securePacketVersion) {
		this.securePacketVersion = securePacketVersion;
	}

	public String getRemoteProvisioningVersion() {
		return remoteProvisioningVersion;
	}

	public void setRemoteProvisioningVersion(String remoteProvisioningVersion) {
		this.remoteProvisioningVersion = remoteProvisioningVersion;
	}

	public String getIsdRAid() {
		return isdRAid;
	}

	public void setIsdRAid(String isdRAid) {
		this.isdRAid = isdRAid;
	}

	public String getPol1Id() {
		return pol1Id;
	}

	public void setPol1Id(String pol1Id) {
		this.pol1Id = pol1Id;
	}

	public String getIsdPAid() {
		return isdPAid;
	}

	public void setIsdPAid(String isdPAid) {
		this.isdPAid = isdPAid;
	}

	public String getIsdPState() {
		return isdPState;
	}

	public void setIsdPState(String isdPState) {
		this.isdPState = isdPState;
	}

	

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public Date getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

	public String getIsdPLoadfileAid() {
		return isdPLoadfileAid;
	}

	public void setIsdPLoadfileAid(String isdPLoadfileAid) {
		this.isdPLoadfileAid = isdPLoadfileAid;
	}

	public String getIsdPModuleAid() {
		return isdPModuleAid;
	}

	public void setIsdPModuleAid(String isdPModuleAid) {
		this.isdPModuleAid = isdPModuleAid;
	}

	public String getConnectivityParam() {
		return connectivityParam;
	}

	public void setConnectivityParam(String connectivityParam) {
		this.connectivityParam = connectivityParam;
	}

	public String getrId() {
		return rId;
	}

	public void setrId(String rId) {
		this.rId = rId;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getCertEcasdEcka() {
		return certEcasdEcka;
	}

	public void setCertEcasdEcka(String certEcasdEcka) {
		this.certEcasdEcka = certEcasdEcka;
	}
	public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo == null ? null : phoneNo.trim();
    }
    
}
