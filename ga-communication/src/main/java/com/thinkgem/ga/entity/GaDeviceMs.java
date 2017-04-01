/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.ga.entity;


/**
 * 终端设备Entity
 * @author liuwsh
 * @version 2017-02-27
 */
public class GaDeviceMs {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String moteId;		// 中继id
	private String apId;
	private String deviceId;		// 设备号
	private String longitude;		// 经度
	private String latitude;		// 纬度
	private String address;		// 地址
	private String status;		// 状态
	private String remarks;
	/**
	 * @return the moteId
	 */
	public String getMoteId() {
		return moteId;
	}
	/**
	 * @param moteId the moteId to set
	 */
	public void setMoteId(String moteId) {
		this.moteId = moteId;
	}
	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}
	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * @return the apId
	 */
	public String getApId() {
		return apId;
	}
	/**
	 * @param apId the apId to set
	 */
	public void setApId(String apId) {
		this.apId = apId;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
}