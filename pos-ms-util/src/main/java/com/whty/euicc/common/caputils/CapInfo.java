// Copyright (C) 2012 WHTY
package com.whty.euicc.common.caputils;

import java.util.List;

/**
 *
 * @author wux
 * @author cx
 * @version 1.0
 */
public class CapInfo {

    /** 主包AID */
    private String packageAID;
    private List<?> appletAID;
    /** 实例AID */
    private String instanceAID;
    private String capData;
    /**  */
    private String appAID;
    private String capFileName;

    public String getCapFileName() {
		return capFileName;
	}

	public void setCapFileName(String capFileName) {
		this.capFileName = capFileName;
	}

	/**
     * @return 主包AID
     */
    public String getPackageAID() {
        return packageAID;
    }

    /**
     * @param packageAID
     *            主包AID
     */
    public void setPackageAID(String packageAID) {
        this.packageAID = packageAID;
    }

    /**
     * @return
     */
    public List<?> getAppletAID() {
        return appletAID;
    }

    /**
     * @param appletAID
     */
    public void setAppletAID(List<?> appletAID) {
        this.appletAID = appletAID;
    }

    /**
     * @return
     */
    public String getInstanceAID() {
        return instanceAID;
    }

    /**
     * @param instanceAID
     */
    public void setInstanceAID(String instanceAID) {
        this.instanceAID = instanceAID;
    }

    /**
     * @return
     */
    public String getCapData() {
        return capData;
    }

    /**
     * @param capData
     */
    public void setCapData(String capData) {
        this.capData = capData;
    }

    /**
     * @return
     */
    public String getAppAID() {
        return appAID;
    }

    /**
     * @param appAID
     *
     */
    public void setAppAID(String appAID) {
        this.appAID = appAID;
    }

}
