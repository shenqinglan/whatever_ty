package com.whty.euicc.profile.opt_isim.bean;
import com.whty.euicc.profile.parent.JavaBean;
/**
 * Opt-isim子元素
 * 结构类型：简单类型
 * @author Administrator
 *
 */
public class DfName extends JavaBean{
	private String tag ="84";
	private String length;
	private String value;
	
	public DfName() {
		
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
		return "DfName [length=" + length + ", tag=" + tag + ", value="
				+ value + "]";
	}
}
