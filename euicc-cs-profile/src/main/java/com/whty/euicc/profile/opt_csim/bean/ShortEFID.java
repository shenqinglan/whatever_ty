package com.whty.euicc.profile.opt_csim.bean;
import com.whty.euicc.profile.parent.JavaBean;
/**
 * Opt-csim子元素
 * 结构类型：简单类型
 * @author Administrator
 *
 */
public class ShortEFID extends JavaBean{
	private String tag ="88";
	private String length;
	private String value;
	
	public ShortEFID() {
		
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
		return "ShortEFID [length=" + length + ", tag=" + tag + ", value="
				+ value + "]";
	}
}
