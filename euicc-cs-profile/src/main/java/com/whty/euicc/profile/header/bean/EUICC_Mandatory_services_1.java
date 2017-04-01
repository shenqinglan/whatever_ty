package com.whty.euicc.profile.header.bean;
import com.whty.euicc.profile.parent.JavaBean;
/**
 * header子元素
 * 结构类型：复杂结构
 * @author Administrator
 *
 */
public class EUICC_Mandatory_services_1 extends JavaBean{
	public String tag ="A5";
	public String length;
	public String value;
	
	public EUICC_Mandatory_services_1() {
		
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
		return "EUICC_Mandatory_services_1 [length=" + length + ", tag=" + tag
				+ ", value=" + value + ", number=" + number + "]";
	}
}
