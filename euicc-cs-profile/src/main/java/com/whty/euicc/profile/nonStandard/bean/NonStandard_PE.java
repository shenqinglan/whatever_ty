package com.whty.euicc.profile.nonStandard.bean;
import com.whty.euicc.profile.parent.JavaBean;
/**
 * NonStandard元素头
 * @author Administrator
 *
 */
public class NonStandard_PE extends JavaBean{
	public String tag = "A9";
	public String length;
	public String value;
	
	public NonStandard_PE() {
		
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
				+ value + ", number=" + number + "]";
	}
	
	
}
