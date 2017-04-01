package com.whty.euicc.profile.isim.bean;

import com.whty.euicc.profile.parent.JavaBean;

/**
 * Isim子元素
 * 结构类型：简单类型
 * @author Administrator
 *
 */
public class Mandated extends JavaBean{
	private String tag ="80";
	private String length ="00";
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}


	@Override
	public String toString() {
		return "Mandated [length=" + length + ", tag=" + tag + ", number="
				+ number + ", value=" + value + "]";
	}
}
