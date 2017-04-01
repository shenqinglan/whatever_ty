package com.whty.euicc.profile.securityDomain.bean;
import com.whty.euicc.profile.parent.JavaBean;
/**
 * SecurityDomain子元素
 * 结构类型：简单类型
 * @author Administrator
 *
 */
public class VolatileMemoryQuotaC7 extends JavaBean{
	private String tag ="C7";

	

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}



	@Override
	public String toString() {
		return "VolatileMemoryQuotaC7 [length=" + length + ", tag=" + tag + ", value="
				+ value + "]";
	}
	
	
	
}
