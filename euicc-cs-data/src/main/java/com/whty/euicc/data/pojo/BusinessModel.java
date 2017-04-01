// Copyright (C) 2012 WHTY
package com.whty.euicc.data.pojo;

import java.io.Serializable;

/**
 * @version1.1 去掉id
 * @author Administrator
 *
 */
public class BusinessModel implements Serializable {
 

    /**
	 * 
	 */
	private static final long serialVersionUID = 7309189260742146675L ;


    /**
     * 业务发起第一次命令类型（不同通道的第一次相同命令类型的进入同一业务模板，在模板内消化差异性）
     */
    private String code;

    /**
     * 接入方编码
     */
    private String platformCode;

    /**
     * 0：有效，1：无效（有效的模板在系统运行过程中才可以使用，无效的模板才可以编辑）
     */
    private String status;

    /**
     * 全局唯一。文件路径(在系统参数管理配置)
     */
    private String xml;

    /**
     * 0：子模板，1：父模板
     */
    private String type;

    private String description;
    /**
     * 关键路径节点数
     */
    private int steps;
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * @return 业务发起第一次命令类型（不同通道的第一次相同命令类型的进入同一业务模板，在模板内消化差异性）
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     *            业务发起第一次命令类型（不同通道的第一次相同命令类型的进入同一业务模板，在模板内消化差异性）
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return 接入方编码
     */
    public String getPlatformCode() {
        return platformCode;
    }

    /**
     * @param platformCode
     *            接入方编码
     */
    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    /**
     * @return 0：有效，1：无效（有效的模板在系统运行过程中才可以使用，无效的模板才可以编辑）
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            0：有效，1：无效（有效的模板在系统运行过程中才可以使用，无效的模板才可以编辑）
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return 全局唯一。文件路径(在系统参数管理配置)
     */
    public String getXml() {
        return xml;
    }

    /**
     * @param xml
     *            全局唯一。文件路径(在系统参数管理配置)
     */
    public void setXml(String xml) {
        this.xml = xml;
    }

    /**
     * @return 0：子模板，1：父模板
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            0：子模板，1：父模板
     */
    public void setType(String type) {
        this.type = type;
    }

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}


}
