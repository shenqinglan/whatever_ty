package com.whty.euicc.profile.isim.bean;
import com.whty.euicc.profile.parent.JavaBean;
/**
 * Isim子元素
 * 结构类型：简单类型
 * @author Administrator
 *
 */
public class Identification extends JavaBean{
	private String tag ="81";

	
	public Identification() {
		
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}



	@Override
	public String toString() {
		return "Identification [length=" + length + ", tag=" + tag + ", value="
				+ value + "]";
	}
	
	
	
}
