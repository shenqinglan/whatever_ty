package com.whty.euicc.profile.securityDomain.bean;

import com.whty.euicc.profile.parent.JavaBean;
/**
 * SecurityDomain子元素
 * 结构类型：复杂结构
 * @author Administrator
 *
 */
public class SdPersoData_1 extends JavaBean{
	private String tag = "A3";

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "SdPersoData_1 [tag=" + tag + ", length=" + length + ", number="
				+ number + ", value=" + value + "]";
	}
	
	
	
}
