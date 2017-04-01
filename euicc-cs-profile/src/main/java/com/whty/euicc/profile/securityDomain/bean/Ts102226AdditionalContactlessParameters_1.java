package com.whty.euicc.profile.securityDomain.bean;
import com.whty.euicc.profile.parent.JavaBean;
/**
 * SecurityDomain子元素
 * 结构类型：复杂结构
 * @author Administrator
 *
 */
public class Ts102226AdditionalContactlessParameters_1 extends JavaBean{
	private String tag ="A0";

	

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}



	@Override
	public String toString() {
		return "Ts102226AdditionalContactlessParameters_1 [length=" + length + ", tag=" + tag + ", value="
				+ value + "]";
	}
	
	
	
}
