package com.whty.euicc.profile.securityDomain.bean;
import com.whty.euicc.profile.parent.JavaBean;
/**
 * SecurityDomain子元素
 * 结构类型：简单类型
 * @author Administrator
 *
 */
public class Ts102226SIMFileAccessToolkitParameter extends JavaBean{
	private String tag ="CA";

	

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}



	@Override
	public String toString() {
		return "Ts102226SIMFileAccessToolkitParameter [length=" + length + ", tag=" + tag + ", value="
				+ value + "]";
	}
	
	
	
}
