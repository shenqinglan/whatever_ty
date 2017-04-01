package com.whty.euicc.data.pojo;

import java.util.Date;

public class IsdPInfo {
    private String pId;
	private String isdPAid;
	private String eid;
	private String isdPState;
	private Date createDt;
	private Date updateDt;
	private String isdPLoadfileAid;
	private String isdPModuleAid;
	private String allocatedMemory;
	private String connectivityParam;
	
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getIsdPAid() {
		return isdPAid;
	}
	public void setIsdPAid(String isdPAid) {
		this.isdPAid = isdPAid;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
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
	public String getAllocatedMemory() {
		return allocatedMemory;
	}
	public void setAllocatedMemory(String allocatedMemory) {
		this.allocatedMemory = allocatedMemory;
	}
	public String getConnectivityParam() {
		return connectivityParam;
	}
	public void setConnectivityParam(String connectivityParam) {
		this.connectivityParam = connectivityParam;
	}
}
