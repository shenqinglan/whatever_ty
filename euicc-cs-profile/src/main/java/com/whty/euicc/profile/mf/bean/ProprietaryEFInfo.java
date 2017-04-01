package com.whty.euicc.profile.mf.bean;
import com.whty.euicc.profile.parent.JavaBean;
/**
 * mf子元素
 * 结构类型：简单类型
 * @author Administrator
 *
 */
public class ProprietaryEFInfo extends JavaBean{
	private String tag ="85";
	private String length;
	private String value;
	
	public ProprietaryEFInfo() {
		
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
		return "ProprietaryEFInfo [length=" + length + ", tag=" + tag + ", value="
				+ value + "]";
	}
}
