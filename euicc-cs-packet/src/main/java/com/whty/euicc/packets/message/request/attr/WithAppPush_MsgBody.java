package com.whty.euicc.packets.message.request.attr;

import java.util.Date;

public interface WithAppPush_MsgBody {
   
	public String getAppAID();
	
	public void setAppAID(String appAID) ;

	public String getAppVersion() ;

	public void setAppVersion(String appVersion);

	public String getPartnerOrgCode() ;

	public void setPartnerOrgCode(String partnerOrgCode);

	public String getAppName() ;

	public void setAppName(String appName);

	public String getAppType() ;

	public void setAppType(String appType) ;

	public String getAppDesc() ;

	public void setAppDesc(String appDesc);

	public String getScope() ;

	public void setScope(String scope) ;

	public Integer getRamSpace() ;

	public void setRamSpace(Integer ramSpace) ;

	public Integer getNvmSpace();

	public void setNvmSpace(Integer nvmSpace) ;

	public String getAppPermission() ;

	public void setAppPermission(String appPermission) ;

	public String getAppSecurityLevel();

	public void setAppSecurityLevel(String appSecurityLevel);

	public String getChkOrgCode() ;

	public void setChkOrgCode(String chkOrgCode) ;

	public Date getAppRegisterTime() ;

	public void setAppRegisterTime(Date appRegisterTime) ;

	public String getIcoUrl() ;

	public void setIcoUrl(String icoUrl) ;

	public String getIogoUrl() ;
	
	public void setIogoUrl(String iogoUrl);
	
	public String getApkUrl() ;

	public void setApkUrl(String apkUrl) ;

	public Date getValidate() ;

	
	public void setValidate(Date validate) ;
	
}
