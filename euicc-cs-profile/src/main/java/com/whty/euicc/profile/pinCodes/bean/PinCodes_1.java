package com.whty.euicc.profile.pinCodes.bean;

import com.whty.euicc.profile.parent.JavaBean;
/**
 * PinCodes子元素
 * 结构类型：复杂结构
 * @author Administrator
 *
 */
public class PinCodes_1 extends JavaBean{
	private String tag = "A1";

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "PukCodes_1 [tag=" + tag + ", length=" + length + ", number="
				+ number + ", value=" + value + "]";
	}
	
	
	
	
	
	
}
