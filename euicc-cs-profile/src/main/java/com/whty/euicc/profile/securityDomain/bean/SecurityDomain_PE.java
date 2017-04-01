package com.whty.euicc.profile.securityDomain.bean;

import com.whty.euicc.profile.parent.JavaBean;
/**
 * SecurityDomain元素头
 * @author Administrator
 *
 */
public class SecurityDomain_PE extends JavaBean{
	private String tag = "A6";

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "SecurityDomain_PE [tag=" + tag + ", getTag()=" + getTag() + "]";
	}
	
	
	
	
}
