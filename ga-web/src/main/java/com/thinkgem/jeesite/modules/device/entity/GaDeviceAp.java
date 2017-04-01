/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.device.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 基站设备Entity
 * @author liuwsh
 * @version 2017-02-28
 */
public class GaDeviceAp extends DataEntity<GaDeviceAp> {
	
	private static final long serialVersionUID = 1L;
	private String deviceId;		// 设备号
	private String longitude;		// 经度
	private String latitude;		// 纬度
	private String address;		// 地址
	private String status;		// 状态
	
	public GaDeviceAp() {
		super();
	}

	public GaDeviceAp(String id){
		super(id);
	}

	@Length(min=1, max=64, message="设备号长度必须介于 1 和 64 之间")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	@Length(min=0, max=64, message="经度长度必须介于 0 和 64 之间")
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	@Length(min=0, max=64, message="纬度长度必须介于 0 和 64 之间")
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	@Length(min=0, max=64, message="地址长度必须介于 0 和 64 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=1, max=1, message="状态长度必须介于 1 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}