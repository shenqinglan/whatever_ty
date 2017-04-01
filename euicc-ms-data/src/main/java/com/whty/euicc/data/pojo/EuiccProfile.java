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
    
    private String cardEid;//页面查询条件
    
    private String optType;//页面查询条件

    private String phoneNo;
    
    private String smscenterNo;
    
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

	public String getCardEid() {
		return cardEid;
	}

	public void setCardEid(String cardEid) {
		this.cardEid = cardEid;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
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

	public String getSmscenterNo() {
		return smscenterNo;
	}

	public void setSmscenterNo(String smscenterNo) {
		this.smscenterNo = smscenterNo;
	}
}