package com.whty.euicc.profile.application.bean;
import com.whty.euicc.profile.parent.JavaBean;
/**
 * Application子元素
 * 结构类型：简单类型
 * @author Administrator
 *
 */
public class NonVolatileDataLimitC8 extends JavaBean{
	private String tag ="C8";

	
	public NonVolatileDataLimitC8() {
		
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}



	@Override
	public String toString() {
		return "NonVolatileDataLimitC8 [length=" + length + ", tag=" + tag + ", value="
				+ value + "]";
	}
	
	
	
}
