package com.whty.euicc.profile.gsm_access.bean;
import com.whty.euicc.profile.parent.JavaBean;
/**
 * Gsm_access子元素
 * 结构类型：简单类型
 * @author Administrator
 *
 */
public class DoNotCreate extends JavaBean{
	private String tag ="80";
	private String length = "00";
	private String value;
	
	public DoNotCreate() {
		
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
		return "DoNotCreate [length=" + length + ", tag=" + tag + ", value="
				+ value + "]";
	}
}
