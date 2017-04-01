package com.whty.euicc.profile.usim.bean;

import com.whty.euicc.profile.parent.JavaBean;
/**
 * Usim子元素
 * 结构类型：复杂结构
 * @author Administrator
 *
 */
public class Adf_usim_1 extends JavaBean{
	private String tag = "A2";

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "Adf_usim_1 [tag=" + tag + ", length=" + length + ", number="
				+ number + ", value=" + value + "]";
	}
	
	
	
}
