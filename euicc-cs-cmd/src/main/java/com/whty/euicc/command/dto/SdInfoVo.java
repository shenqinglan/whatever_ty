package com.whty.euicc.command.dto;

import java.util.Date;

public class SdInfoVo {



    /**
     * 应用AID,13-15个字节为TAR
     */
    private String sdAid;

    /**
     * 安全域类型 00：sub ISD，01：SSD 02 sub SSD
     */
    private String domainType;

    /**
     * 合作伙伴ID
     */
    private String spCode;

    /**
     * 更改时间
     */
    private Date updatedDate;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 默认00（两字节） 应用权限的每个位有如下的含义：b8=1：表示应用是一个安全域。b7=1：表示安全域有DAP验证的能力。b6=1：
     * 表示安全域有委托管理的权限。b5=1：表示应用有锁定卡的权限。b4=1：表示应用有终止卡的权限。b3=1：
     * 表示应用有缺省被选定的权限。b2=1：表示应用有CVM管理的权限。b1=1：表示安全域有强制DAP验证的能力。
     */
    private String privilege;
    
    /**
     * 
     */
    private String loadFileAid;
    
    /**
     * AC规则
     */
    private Integer acAid;
    
    /**
     * ENC初始索引号
     */
    private String sEncId;
    /**
     * ENC初始版本号
     */
    private String sEncVer;
    /**
     * MAC初始索引号
     */
    private String sMacId;
    /**
     * MAC初始版本号
     */
    private String sMacVer;
    /**
     * DEK初始索引号
     */
    private String sDekId;
    /**
     * DEK初始版本号
     */
    private String sDekVer;
    /**
     * DAP初始索引号
     */
    private String sDapId;
    /**
     * DAP初始版本号
     */
    private String sDapVer;
    /**
     * TOKEN初始索引号
     */
    private String sTokenId;
    /**
     * TOKEN初始版本号
     */
    private String sTokenVer;
    /**
     * 便条初始索引号
     */
    private String sReceiptId;
    /**
     * 便条初始版本号
     */
    private String sReceiptVer;
    
    /**
     * 介质类型
     */
    private String cardType;
    
    /**
     * 上一级SD的AID
     */
    private String targetSdAid;
    /**
     * 操作员编号
     */
    private String optUserId;
    /**
     * 操作时间
     */
    private String optDate;
    
    /**
     * 安全域状态 01:可使用;02:锁定;03:待删除
     */
    private String status;
    
    /**
     * 安全域空间大小
     */
    private String sdSpace;
    
    /**
     * RSA算法
     */
    private String rsaAlg;
    


    /**
     * 可执行模块AID
     */
    private String moduleAid; 


    


    
    
    
    /**
     * 安装参数
     */
    private String parameter;

    
    /**
     * @return 应用AID,13-15个字节为TAR
     */
    public String getSdAid() {
        return sdAid;
    }

    /**
     * @param sdAid
     *            应用AID,13-15个字节为TAR
     */
    public void setSdAid(String sdAid) {
        this.sdAid = sdAid;
    }

    /**
     * @return 安全域类型 0x00，主安全域； 0x01，自有辅安全域； 0x02，第三方安全域（DAP）；
     *         0x03，第三方安全域(TOKEN)。
     */
    public String getDomainType() {
        return domainType;
    }

    /**
     * @param domainType
     *            安全域类型 0x00，主安全域； 0x01，自有辅安全域； 0x02，第三方安全域（DAP）；
     *            0x03，第三方安全域(TOKEN)。
     */
    public void setDomainType(String domainType) {
        this.domainType = domainType;
    }

    /**
     * @return 合作伙伴ID
     */
    public String getSpCode() {
        return spCode;
    }

    /**
     * @param spCode
     *            合作伙伴ID
     */
    public void setSpCode(String spCode) {
        this.spCode = spCode;
    }

    /**
     * @return 更改时间
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate
     *            更改时间
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return 创建时间
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate
     *            创建时间
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return 默认00（两字节）
     *         应用权限的每个位有如下的含义：b8=1：表示应用是一个安全域。b7=1：表示安全域有DAP验证的能力。b6=1：
     *         表示安全域有委托管理的权限。b5=1：表示应用有锁定卡的权限。b4=1：表示应用有终止卡的权限。b3=1：
     *         表示应用有缺省被选定的权限。b2=1：表示应用有CVM管理的权限。b1=1：表示安全域有强制DAP验证的能力。
     */
    public String getPrivilege() {
        return privilege;
    }

    /**
     * @param privilege
     *            默认00（两字节）
     *            应用权限的每个位有如下的含义：b8=1：表示应用是一个安全域。b7=1：表示安全域有DAP验证的能力。b6=1：
     *            表示安全域有委托管理的权限。b5=1：表示应用有锁定卡的权限。b4=1：表示应用有终止卡的权限。b3=1：
     *            表示应用有缺省被选定的权限。b2=1：表示应用有CVM管理的权限。b1=1：表示安全域有强制DAP验证的能力。
     */
    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    /**
     * @return 安全域安装文件AID
     */
    public String getLoadFileAid() {
        return loadFileAid;
    }

    /**
     * @param loadFileAid
     *            安全域安装文件AID
     */
    public void setLoadFileAid(String loadFileAid) {
        this.loadFileAid = loadFileAid;
    }

    /**
     * @return 可执行模块AID
     */
    public String getModuleAid() {
        return moduleAid;
    }

    /**
     * @param moduleAid
     *            可执行模块AID
     */
    public void setModuleAid(String moduleAid) {
        this.moduleAid = moduleAid;
    }

    /**
     * @return 安装参数
     */
    public String getParameter() {
        return parameter;
    }

    /**
     * @param parameter
     *            安装参数
     */
    public void setParameter(String parameter) {
        this.parameter = parameter;
    }



	public Integer getAcAid() {
		return acAid;
	}

	public void setAcAid(Integer acAid) {
		this.acAid = acAid;
	}

	public String getsEncId() {
		return sEncId;
	}

	public void setsEncId(String sEncId) {
		this.sEncId = sEncId;
	}

	public String getsEncVer() {
		return sEncVer;
	}

	public void setsEncVer(String sEncVer) {
		this.sEncVer = sEncVer;
	}

	public String getsMacId() {
		return sMacId;
	}

	public void setsMacId(String sMacId) {
		this.sMacId = sMacId;
	}

	public String getsMacVer() {
		return sMacVer;
	}

	public void setsMacVer(String sMacVer) {
		this.sMacVer = sMacVer;
	}

	public String getsDekId() {
		return sDekId;
	}

	public void setsDekId(String sDekId) {
		this.sDekId = sDekId;
	}

	public String getsDekVer() {
		return sDekVer;
	}

	public void setsDekVer(String sDekVer) {
		this.sDekVer = sDekVer;
	}

	public String getsDapId() {
		return sDapId;
	}

	public void setsDapId(String sDapId) {
		this.sDapId = sDapId;
	}

	public String getsDapVer() {
		return sDapVer;
	}

	public void setsDapVer(String sDapVer) {
		this.sDapVer = sDapVer;
	}

	public String getsTokenId() {
		return sTokenId;
	}

	public void setsTokenId(String sTokenId) {
		this.sTokenId = sTokenId;
	}

	public String getsTokenVer() {
		return sTokenVer;
	}

	public void setsTokenVer(String sTokenVer) {
		this.sTokenVer = sTokenVer;
	}

	public String getsReceiptId() {
		return sReceiptId;
	}

	public void setsReceiptId(String sReceiptId) {
		this.sReceiptId = sReceiptId;
	}

	public String getsReceiptVer() {
		return sReceiptVer;
	}

	public void setsReceiptVer(String sReceiptVer) {
		this.sReceiptVer = sReceiptVer;
	}


	public String getTargetSdAid() {
		return targetSdAid;
	}

	public void setTargetSdAid(String targetSdAid) {
		this.targetSdAid = targetSdAid;
	}

	public String getOptUserId() {
		return optUserId;
	}

	public void setOptUserId(String optUserId) {
		this.optUserId = optUserId;
	}

	public String getOptDate() {
		return optDate;
	}

	public void setOptDate(String optDate) {
		this.optDate = optDate;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSdSpace() {
		return sdSpace;
	}

	public void setSdSpace(String sdSpace) {
		this.sdSpace = sdSpace;
	}

	public String getRsaAlg() {
		return rsaAlg;
	}

	public void setRsaAlg(String rsaAlg) {
		this.rsaAlg = rsaAlg;
	}

	
    

}
