package com.whty.euicc.profile.application.bean;
import com.whty.euicc.profile.parent.JavaBean;
/**
 * Application子元素
 * 结构类型：复杂结构
 * @author Administrator
 *
 */
public class ProcessData_1 extends JavaBean{
	private String tag ="30";

	

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}



	@Override
	public String toString() {
		return "ProcessData_1 [length=" + length + ", tag=" + tag + ", value="
				+ value + "]";
	}
	
	
	
}
