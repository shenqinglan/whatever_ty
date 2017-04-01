package com.whty.euicc.profile.header.bean;
import com.whty.euicc.profile.parent.JavaBean;
/**
 * Header元素头
 * @author Administrator
 *
 */
public class Header_PE extends JavaBean{
	public String tag = "A0";
	public String length;
	public String value;
	
	public Header_PE() {
		
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
		return "Header_PE [length=" + length + ", tag=" + tag + ", value="
				+ value + "]";
	}
	
	
}
