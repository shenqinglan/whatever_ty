package com.whty.euicc.profile.pinCodes.bean;

import com.whty.euicc.profile.parent.JavaBean;
/**
 * PinCodes元素头
 * @author Administrator
 *
 */
public class PinCodes_PE extends JavaBean{
	private String tag = "A2";

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "PinCodes_PE [tag=" + tag + ", length=" + length + ", number="
				+ number + ", value=" + value + "]";
	}
	
	
	
	
	
}
