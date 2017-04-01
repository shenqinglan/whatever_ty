package com.whty.euicc.profile.mf.bean;
/**
 * mf元素的javabean
 * @author Administrator
 *
 */
public class JavaBean {
	public String tag;
	public String length;
	public String value;
	
	public JavaBean() {
		
	}

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "JavaBean [length=" + length + ", tag=" + tag + ", value="
				+ value + "]";
	}
	
	
	
	
	
	
	
}
