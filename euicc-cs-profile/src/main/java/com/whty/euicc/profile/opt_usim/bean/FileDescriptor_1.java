package com.whty.euicc.profile.opt_usim.bean;
import com.whty.euicc.profile.parent.JavaBean;
/**
 * Opt-usim子元素
 * 结构类型：复杂结构
 * @author Administrator
 *
 */
public class FileDescriptor_1 extends JavaBean{
	private String tag ="A1";
	private String length;
	private String value;
	
	public FileDescriptor_1() {
		
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
		return "FileDescriptor_1 [length=" + length + ", tag=" + tag
				+ ", value=" + value + ", number=" + number + "]";
	}
}
