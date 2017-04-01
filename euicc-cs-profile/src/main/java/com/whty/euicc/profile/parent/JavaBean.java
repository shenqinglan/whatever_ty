package com.whty.euicc.profile.parent;
/**
 * 父类javabean
 * @author Administrator
 *
 */
public class JavaBean {
	public String tag;
	public String length;
	public String value;
	public String  number= "";
	
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
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "JavaBean [length=" + length + ", tag=" + tag + ", value="
				+ value + "]";
	}
	
	
	
	
	
	
	
}
