package com.whty.euicc.profile.mf.bean;
import com.whty.euicc.profile.parent.JavaBean;
/**
 * mf元素头
 * @author Administrator
 *
 */
public class Mf_PE extends JavaBean{
	private String tag = "B0";
	private String length;
	private String value;
	
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
		return "Mf_PE [length=" + length + ", tag=" + tag + ", value=" + value
				+ "]";
	}
	
}
