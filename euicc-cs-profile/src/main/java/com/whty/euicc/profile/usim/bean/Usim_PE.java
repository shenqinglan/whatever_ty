package com.whty.euicc.profile.usim.bean;

import com.whty.euicc.profile.parent.JavaBean;
/**
 * Usim元素头
 * @author Administrator
 *
 */
public class Usim_PE extends JavaBean{
	private String tag = "B3";

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "Usim_PE [tag=" + tag + ", length=" + length + ", number="
				+ number + ", value=" + value + "]";
	}
	
	
}
