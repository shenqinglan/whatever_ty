package com.whty.euicc.profile.header.bean;
import com.whty.euicc.profile.parent.JavaBean;
/**
 * header子元素
 * 结构类型：简单类型
 * @author Administrator
 *
 */
public class Tuak256 extends JavaBean{
	public String tag = "90";
	public String length = "00";
	public String value;
	
	public Tuak256() {
		
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
		return "Javacard [length=" + length + ", tag=" + tag + ", value="
				+ value + ", number=" + number + "]";
	}
	
	
}
