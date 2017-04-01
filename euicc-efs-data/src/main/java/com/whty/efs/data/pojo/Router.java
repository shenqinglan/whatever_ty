package com.whty.efs.data.pojo;

import java.io.Serializable;


/**
 * 路由信息bean
 * @author gaofeng
 *
 */
public class Router implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5475069186165736100L;
	/** 接收方*/
	private String receiver;	
	/** 转发地址*/
	private String receiver_url;
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	/** 实现bean_id*/
	private String bean_id;
	public String getReceiver_url() {
		return receiver_url;
	}
	public void setReceiver_url(String receiver_url) {
		this.receiver_url = receiver_url;
	}
	public String getBean_id() {
		return bean_id;
	}
	public void setBean_id(String bean_id) {
		this.bean_id = bean_id;
	}
}
