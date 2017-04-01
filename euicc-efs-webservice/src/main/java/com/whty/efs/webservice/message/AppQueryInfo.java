package com.whty.efs.webservice.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>AppQueryInfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="AppQueryInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rspnMsg" type="{http://www.tathing.com}RspnMsg"/>
 *         &lt;element name="appAID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="appVersion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="seID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="appName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="appType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="appDesc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="providerID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="providerName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="onlineDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="icoUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="logoUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppQueryInfo", propOrder = {
	    "rspnMsg",
	    "appAID",
	    "appVersion",
	    "partnerOrgCode",
	    "appName",
	    "appType",
	    "appDesc",
	    "scope",
	    "validate",
	    "ramSpace",
	    "nvmSpace",
	    "appPermission",
	    "appSecurityLevel",
	    "chkOrgCode",
	    "appRegisterTime",
	    "icoURL",
	    "logoURL",
	    "apkURL"
	})
public class AppQueryInfo {
	
	@XmlElement(required = true)
    protected RspnMsg rspnMsg;
    @XmlElement(required = true)
    protected String appAID;
    @XmlElement(required = true)
    protected String appVersion;
    @XmlElement(required = true)
    protected String partnerOrgCode;
    @XmlElement(required = true)
    protected String appName;
    @XmlElement(required = true)
    protected String appType;
    @XmlElement(required = true)
    protected String appDesc;
    @XmlElement(required = false)
    protected String scope;
    @XmlElement(required = false)
    protected String validate;
    @XmlElement(required = true)
    protected String ramSpace;
    @XmlElement(required = true)
    protected String nvmSpace;
    @XmlElement(required = true)
    protected String appPermission;
    @XmlElement(required = false)
    protected String appSecurityLevel;
    @XmlElement(required = false)
    protected String chkOrgCode;
    @XmlElement(required = true)
    protected String appRegisterTime;
    @XmlElement(required = false)
    protected String icoURL;
    @XmlElement(required = false)
    protected String logoURL;
    @XmlElement(required = false)
    protected String apkURL;

    /**
     * 获取rspnMsg属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RspnMsg }
     *     
     */
    public RspnMsg getRspnMsg() {
        return rspnMsg;
    }

    /**
     * 设置rspnMsg属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RspnMsg }
     *     
     */
    public void setRspnMsg(RspnMsg value) {
        this.rspnMsg = value;
    }

    /**
     * 获取appAID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppAID() {
        return appAID;
    }

    /**
     * 设置appAID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppAID(String value) {
        this.appAID = value;
    }

    /**
     * 获取appVersion属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppVersion() {
        return appVersion;
    }

    /**
     * 设置appVersion属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppVersion(String value) {
        this.appVersion = value;
    }

    /**
     * 获取seID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
//    public String getSeID() {
//        return seID;
//    }

    /**
     * 设置seID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
//    public void setSeID(String value) {
//        this.seID = value;
//    }

    /**
     * 获取appName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 设置appName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppName(String value) {
        this.appName = value;
    }

    /**
     * 获取appType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppType() {
        return appType;
    }

    /**
     * 设置appType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppType(String value) {
        this.appType = value;
    }

    /**
     * 获取appDesc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppDesc() {
        return appDesc;
    }

    /**
     * 设置appDesc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppDesc(String value) {
        this.appDesc = value;
    }

    /**
     * 获取providerID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
//    public String getProviderID() {
//        return providerID;
//    }

    /**
     * 设置providerID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
//    public void setProviderID(String value) {
//        this.providerID = value;
//    }

    /**
     * 获取providerName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
//    public String getProviderName() {
//        return providerName;
//    }

    /**
     * 设置providerName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
//    public void setProviderName(String value) {
//        this.providerName = value;
//    }

    /**
     * 获取onlineDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
//    public Date getOnlineDate() {
//        return onlineDate;
//    }

    /**
     * 设置onlineDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
//    public void setOnlineDate(Date value) {
//        this.onlineDate = value;
//    }


    /**
     * 获取partnerOrgCode属性的值
     * 
     * @return
     *  possible object is
     *     {@link String }
     */
    public String getPartnerOrgCode() {
		return partnerOrgCode;
	}

	/**
	 * 设置partnerOrgCode属性的值
	 * 
	 * @param partnerOrgCode
	 *  possible object is
     *     {@link String }
	 */
	public void setPartnerOrgCode(String partnerOrgCode) {
		this.partnerOrgCode = partnerOrgCode;
	}

	/**
	 * 获取scope属性的值
	 * 
	 * @return
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * 设置scope属性的值
	 * 
	 * @param scope
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * 获得validate属性的值
	 * 
	 * @return
	 */
	public String getValidate() {
		return validate;
	}

	/**
	 * 设置validate属性的值
	 * 
	 * @param validate
	 */
	public void setValidate(String validate) {
		this.validate = validate;
	}

	/**
	 * 获得ramSpace属性的值
	 * 
	 * @return
	 */
	public String getRamSpace() {
		return ramSpace;
	}

	/**
	 * 设置ramSpace属性的值
	 * 
	 * @param ramSpace
	 */
	public void setRamSpace(String ramSpace) {
		this.ramSpace = ramSpace;
	}

	/**
	 * 获得nvmSpace属性的值
	 * 
	 * @return
	 */
	public String getNvmSpace() {
		return nvmSpace;
	}

	/**
	 * 设置nvmSpace属性的值
	 * 
	 * @param nvmSpace
	 */
	public void setNvmSpace(String nvmSpace) {
		this.nvmSpace = nvmSpace;
	}

	/**
	 * 获得appPermission属性的值
	 * 
	 * @return
	 */
	public String getAppPermission() {
		return appPermission;
	}

	/**
	 * 设置appPermission属性的值
	 * 
	 * @param appPermission
	 */
	public void setAppPermission(String appPermission) {
		this.appPermission = appPermission;
	}

	/**
	 * @return
	 */
	public String getAppSecurityLevel() {
		return appSecurityLevel;
	}

	/**
	 * @param appSecurityLevel
	 */
	public void setAppSecurityLevel(String appSecurityLevel) {
		this.appSecurityLevel = appSecurityLevel;
	}

	/**
	 * @return
	 */
	public String getChkOrgCode() {
		return chkOrgCode;
	}

	/**
	 * @param chkOrgCode
	 */
	public void setChkOrgCode(String chkOrgCode) {
		this.chkOrgCode = chkOrgCode;
	}

	/**
	 * @return
	 */
	public String getAppRegisterTime() {
		return appRegisterTime;
	}

	/**
	 * @param appRegisterTime
	 */
	public void setAppRegisterTime(String appRegisterTime) {
		this.appRegisterTime = appRegisterTime;
	}

	/**
	 * @return
	 */
	public String getApkURL() {
		return apkURL;
	}

	/**
	 * @param apkURL
	 * 		allowed object is
     *     {@link String }
	 */
	public void setApkURL(String apkURL) {
		this.apkURL = apkURL;
	}

	/**
     * 设置icoUrl属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIcoUrl(String value) {
        this.icoURL = value;
    }
    
    /**
     * 获取icoUrl属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIcoUrl() {
        return icoURL;
    }
    /**
     * 获取logoUrl属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogoUrl() {
        return logoURL;
    }

    /**
     * 设置logoUrl属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogoUrl(String value) {
        this.logoURL = value;
    }
}
