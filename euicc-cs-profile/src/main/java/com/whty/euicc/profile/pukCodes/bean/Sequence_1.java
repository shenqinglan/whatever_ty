package com.whty.euicc.profile.pukCodes.bean;

import com.whty.euicc.profile.parent.JavaBean;
/**
 * PukCodes子元素
 * 结构类型:复杂结构
 * @author Administrator
 *
 */
public class Sequence_1 extends JavaBean{
	private String tag  ="30";

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "Sequence_1 [tag=" + tag + ", length=" + length + ", number="
				+ number + ", value=" + value + "]";
	}
	
	
	
	
}
