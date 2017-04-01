package com.whty.efs.data.pojo;

public class BaseInfo {
    
    public String  channel_code ;  //渠道编码: 01厦门,02洪城
    public String  channel_name ;  // 渠道名称
    public String  tag_code     ;  // 接口类型编码
    public String tag_name     ;   // 接口类型名称
    public String  enable_flag  ;  // 启用标示  00:启用   01:禁止 
     
	public BaseInfo() {
		super();
	}
	public String getChannel_code() {
		return channel_code;
	}
	public void setChannel_code(String channel_code) {
		this.channel_code = channel_code;
	}
	public String getChannel_name() {
		return channel_name;
	}
	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}
	public String getTag_code() {
		return tag_code;
	}
	public void setTag_code(String tag_code) {
		this.tag_code = tag_code;
	}
	public String getTag_name() {
		return tag_name;
	}
	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}
	public String getEnable_flag() {
		return enable_flag;
	}
	public void setEnable_flag(String enable_flag) {
		this.enable_flag = enable_flag;
	}
}
