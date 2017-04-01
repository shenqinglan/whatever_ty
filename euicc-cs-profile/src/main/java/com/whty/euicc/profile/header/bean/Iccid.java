package com.whty.euicc.profile.header.bean;
import com.whty.euicc.profile.parent.JavaBean;
/**
 * header子元素
 * 结构类型：简单类型
 * @author Administrator
 *
 */
public class Iccid extends JavaBean{
	public String tag ="83";
	public String length;
	public String value;
	
	public Iccid() {
		
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
		return "Iccid [length=" + length + ", tag=" + tag + ", value=" + value
				+ ", number=" + number + "]";
	}
}
