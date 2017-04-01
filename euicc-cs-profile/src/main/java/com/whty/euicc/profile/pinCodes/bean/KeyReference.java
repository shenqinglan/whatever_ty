package com.whty.euicc.profile.pinCodes.bean;

import com.whty.euicc.profile.parent.JavaBean;
/**
 * PinCodes子元素
 * 结构类型：简单类型
 * @author Administrator
 *
 */
public class KeyReference extends JavaBean{
	private String tag = "80";
	
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "KeyReference [tag=" + tag + ", length=" + length + ", number="
				+ number + ", value=" + value + "]";
	}
	
	
	
}
