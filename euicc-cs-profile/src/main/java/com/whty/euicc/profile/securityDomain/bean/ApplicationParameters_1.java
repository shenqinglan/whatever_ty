package com.whty.euicc.profile.securityDomain.bean;
import com.whty.euicc.profile.parent.JavaBean;
/**
 * SecurityDomain子元素
 * 结构类型：复杂结构
 * @author Administrator
 *
 */
public class ApplicationParameters_1 extends JavaBean{
	private String tag ="EA";

	

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}



	@Override
	public String toString() {
		return "ApplicationParameters_1 [length=" + length + ", tag=" + tag + ", value="
				+ value + "]";
	}
	
	
	
}
