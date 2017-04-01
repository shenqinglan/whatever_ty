package com.whty.euicc.command.cmd;

import com.whty.euicc.packets.message.response.Capdu;

import java.util.ArrayList;
import java.util.List;

/**
 * 过程数据对象
 * 
 * @author bjw@whty.com.cn
 * @date 2014年10月23日 上午9:34:07
 */
public class ProcessData {

	public ProcessData() {
		this.capdus = new ArrayList<Capdu>();
	}

	private List<Capdu> capdus;

	public List<Capdu> getCapdus() {
		return capdus;
	}

	public void setCapdus(List<Capdu> capdus) {
		this.capdus = capdus;
	}

	public void addCapdu(Capdu capdu) {
		this.capdus.add(capdu);
	}

	/**
	 * 销毁属性数据
	 */
	public void destroy() {
		this.capdus = null;
	}
	public void clear() {
		this.capdus.clear();
	}
}
