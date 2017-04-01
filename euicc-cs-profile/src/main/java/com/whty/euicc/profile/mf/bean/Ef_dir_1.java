package com.whty.euicc.profile.mf.bean;
import com.whty.euicc.profile.parent.JavaBean;
/**
 * mf子元素
 * 结构类型：复杂结构
 * @author Administrator
 *
 */
public class Ef_dir_1 extends JavaBean{
	private String tag ="A5";
	private String length;
	private String value;
	
	public Ef_dir_1() {
		
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
		return "Ef_dir [length=" + length + ", tag=" + tag + ", value="
				+ value + "]";
	}
}
