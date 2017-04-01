package com.whty.euicc.data.pojo;

public class EuiccProfile {
    private String iccid;

    private String isdPAid;

    private String mnoId;

    private String fallbackAttribute;


    private String imsi;
    
    private String msisdn;

    private String state;

    private String smdpId;

    private String profileType;

    private String allocatedMemory;

    private String freeMemory;

    private String pol2Id;

    private String eid;
    
    private String phoneNo;
	
	private String smscenterNo;
	
	private String isdPIdNo;
	
	private String isdPImageNo;
	
	private String applicationProviderNo;
	
	private String tokenIdNo;
	
	private String deleteTokenKey;

    public String getIsdPIdNo() {
		return isdPIdNo;
	}

	public void setIsdPIdNo(String isdPIdNo) {
		this.isdPIdNo = isdPIdNo;
	}

	public String getIsdPImageNo() {
		return isdPImageNo;
	}

	public void setIsdPImageNo(String isdPImageNo) {
		this.isdPImageNo = isdPImageNo;
	}

	public String getApplicationProviderNo() {
		return applicationProviderNo;
	}

	public void setApplicationProviderNo(String applicationProviderNo) {
		this.applicationProviderNo = applicationProviderNo;
	}

	public String getTokenIdNo() {
		return tokenIdNo;
	}

	public void setTokenIdNo(String tokenIdNo) {
		this.tokenIdNo = tokenIdNo;
	}

	public String getDeleteTokenKey() {
		return deleteTokenKey;
	}

	public void setDeleteTokenKey(String deleteTokenKey) {
		this.deleteTokenKey = deleteTokenKey;
	}

	public String getSmscenterNo() {
		return smscenterNo;
	}

	public void setSmscenterNo(String smscenterNo) {
		this.smscenterNo = smscenterNo;
	}

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public String getIsdPAid() {
        return isdPAid;
    }

    public void setIsdPAid(String isdPAid) {
        this.isdPAid = isdPAid == null ? null : isdPAid.trim();
    }

    public String getMnoId() {
        return mnoId;
    }

    public void setMnoId(String mnoId) {
        this.mnoId = mnoId == null ? null : mnoId.trim();
    }

    public String getFallbackAttribute() {
        return fallbackAttribute;
    }

    public void setFallbackAttribute(String fallbackAttribute) {
        this.fallbackAttribute = fallbackAttribute == null ? null : fallbackAttribute.trim();
    }



    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getSmdpId() {
        return smdpId;
    }

    public void setSmdpId(String smdpId) {
        this.smdpId = smdpId == null ? null : smdpId.trim();
    }

    public String getProfileType() {
        return profileType;
    }

    public void setProfileType(String profileType) {
        this.profileType = profileType == null ? null : profileType.trim();
    }

    public String getAllocatedMemory() {
        return allocatedMemory;
    }

    public void setAllocatedMemory(String allocatedMemory) {
        this.allocatedMemory = allocatedMemory == null ? null : allocatedMemory.trim();
    }

    public String getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(String freeMemory) {
        this.freeMemory = freeMemory == null ? null : freeMemory.trim();
    }

    public String getPol2Id() {
        return pol2Id;
    }

    public void setPol2Id(String pol2Id) {
        this.pol2Id = pol2Id == null ? null : pol2Id.trim();
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid == null ? null : eid.trim();
    }
    
    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo == null ? null : phoneNo.trim();
    }

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
}