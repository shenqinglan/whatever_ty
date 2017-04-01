/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.ga.entity;

import java.util.Date;

/**
 * Entity
 * @author liuwsh
 * @version 2017-02-17
 */
public class IdNoInfo {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String no;
	
	public IdNoInfo() {
		
	}
	
	public IdNoInfo(String no) {
		this.no = no;
	}
	
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the no
	 */
	public String getNo() {
		return no;
	}
	/**
	 * @param no the no to set
	 */
	public void setNo(String no) {
		this.no = no;
	}	
	
	
	
	
	
	
}