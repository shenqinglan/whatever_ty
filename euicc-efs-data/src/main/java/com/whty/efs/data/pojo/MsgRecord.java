package com.whty.efs.data.pojo;

import java.util.Date;

public class MsgRecord {
	
	public String channel_code;  //渠道编码
	public String tag_code;      //接口类型编码
	public String mac_value;     //mac值
	public String request_data;   //请求数据
	public String response_date;  //响应数据
	public String transkey;       //transkey因子
	public Date   opt_date;      //创建时间
	

	public MsgRecord() {
		super();
	}
	
    
	
	public String getChannel_code() {
		return channel_code;
	}



	public void setChannel_code(String channel_code) {
		this.channel_code = channel_code;
	}



	public String getTag_code() {
		return tag_code;
	}



	public void setTag_code(String tag_code) {
		this.tag_code = tag_code;
	}



	public String getMac_value() {
		return mac_value;
	}



	public void setMac_value(String mac_value) {
		this.mac_value = mac_value;
	}



	public String getRequest_data() {
		return request_data;
	}



	public void setRequest_data(String request_data) {
		this.request_data = request_data;
	}



	public String getResponse_date() {
		return response_date;
	}



	public void setResponse_date(String response_date) {
		this.response_date = response_date;
	}



	public String getTranskey() {
		return transkey;
	}



	public void setTranskey(String transkey) {
		this.transkey = transkey;
	}



	public Date getOpt_date() {
		return opt_date;
	}



	public void setOpt_date(Date opt_date) {
		this.opt_date = opt_date;
	}



	

}
