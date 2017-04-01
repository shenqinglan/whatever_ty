package com.whty.euicc.profile.pukCodes.bean;

import com.whty.euicc.profile.parent.JavaBean;
/**
 * PukCodes元素头
 * @author Administrator
 *
 */
public class PukCodes_PE extends JavaBean{
	private String tag = "A3";

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "PukCodes_PE [tag=" + tag + ", length=" + length + ", number="
				+ number + ", value=" + value + "]";
	}
	
	
	
	
	
	
}
