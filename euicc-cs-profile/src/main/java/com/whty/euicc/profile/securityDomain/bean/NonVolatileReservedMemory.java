package com.whty.euicc.profile.securityDomain.bean;
import com.whty.euicc.profile.parent.JavaBean;
/**
 * SecurityDomain子元素
 * 结构类型：简单类型
 * @author Administrator
 *
 */
public class NonVolatileReservedMemory extends JavaBean{
	private String tag ="D8";

	

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}



	@Override
	public String toString() {
		return "NonVolatileReservedMemory [length=" + length + ", tag=" + tag + ", value="
				+ value + "]";
	}
	
	
	
}
