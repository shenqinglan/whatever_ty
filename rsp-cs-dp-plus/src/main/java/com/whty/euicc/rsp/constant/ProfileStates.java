package com.whty.euicc.rsp.constant;

/**
 * profile状态
 * @author 11
 *
 */
public class ProfileStates {
	public final static String PROFILE_AVAILABLE = "Available";          //profile在DP+存储中可用
	public final static String PROFILE_ALLOCATED = "Allocated";          //经过ES2+.downloadOrder()后的状态（未绑定EID）
	public final static String PROFILE_LINKED = "Linked";                //经过ES2+.downloadOrder()后的状态（绑定了EID）
	public final static String PROFILE_CONFIRMED = "Confirmed";          //经过ES2+.confirmOrder()后的状态（无论是否与EID绑定），ES2+.confirmOrder()入参的releaseFlag为false时
	public final static String PROFILE_RELEASED = "Released";            //profile已准备好下载安装，ES2+.confirmOrder()入参的releaseFlag为true时
	public final static String PROFILE_DOWNLOADED = "Downloaded";        //Bound profile被传递到LPA 
	public final static String PROFILE_INSTALLED = "Installed";          //profile被成功安装到卡上
	public final static String PROFILE_ERROR = "Error";                  //profile未被成功安装
	public final static String PROFILE_UNAVAILABLE = "Unavailable";      //profile再也不能被DP+重用

}
