package com.whty.euicc.data.pojo;

import java.util.Date;

public class EuiccIsdP {
    private String pId;

    private String eid;

    private String isdPState;

    private Date createDate;

    private Date updateDate;

    private String isdPLoadfileAid;

    private String isdPModuleAid;

    private String allocatedMemory;

    private String connectivityParams;

    private String isdPAid;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId == null ? null : pId.trim();
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid == null ? null : eid.trim();
    }

    public String getIsdPState() {
        return isdPState;
    }

    public void setIsdPState(String isdPState) {
        this.isdPState = isdPState == null ? null : isdPState.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getIsdPLoadfileAid() {
        return isdPLoadfileAid;
    }

    public void setIsdPLoadfileAid(String isdPLoadfileAid) {
        this.isdPLoadfileAid = isdPLoadfileAid == null ? null : isdPLoadfileAid.trim();
    }

    public String getIsdPModuleAid() {
        return isdPModuleAid;
    }

    public void setIsdPModuleAid(String isdPModuleAid) {
        this.isdPModuleAid = isdPModuleAid == null ? null : isdPModuleAid.trim();
    }

    public String getAllocatedMemory() {
        return allocatedMemory;
    }

    public void setAllocatedMemory(String allocatedMemory) {
        this.allocatedMemory = allocatedMemory == null ? null : allocatedMemory.trim();
    }

    public String getConnectivityParams() {
        return connectivityParams;
    }

    public void setConnectivityParams(String connectivityParams) {
        this.connectivityParams = connectivityParams == null ? null : connectivityParams.trim();
    }

    public String getIsdPAid() {
        return isdPAid;
    }

    public void setIsdPAid(String isdPAid) {
        this.isdPAid = isdPAid == null ? null : isdPAid.trim();
    }
}