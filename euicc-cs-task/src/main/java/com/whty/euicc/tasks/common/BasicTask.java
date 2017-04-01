// Copyright (C) 2012 WHTY
package com.whty.euicc.tasks.common;

import com.whty.cache.CacheUtil;
import com.whty.euicc.common.exception.InvalidParameterException;
import com.whty.euicc.common.utils.SecurityException;
import com.whty.euicc.packets.message.response.Capdu;
import com.whty.euicc.tasks.exception.TaskException;

import java.util.List;

/**
 * 原子任务基类
 * 
 * @author cy
 * 
 */
public abstract class BasicTask extends Task{

	
	/**
	 * apdu响应码
	 * 
	 * @author dengjun
	 * 
	 */
	public interface Compsta {
		/**
		 * 00
		 */
		public static final String COMMON = "00";

		/**
		 * 026283
		 */
		public static final String RESP6283 = "026283";

		public static final String RESPA826A886A80 = "066A826A886A80";

		public static final String RESP6200 = "026200";

	}

	/**
	 * 判断原子任务执行情况 (默认为0)
	 */
	private String status = "0";

	/**
	 * 事件值 (默认为0)
	 */
	private int event = 0;
	/**
	 * 默认第一行业（其他行业目前没有用到）
	 */
//	protected int interindustry = GPConstant.cla1_Interindustry;

	/**
	 * 命令为GPcommand（暂时也是GP命令不变）
	 */
//	protected String command = GPConstant.cla1_command1;


	/**
	 * 报文返回状态
	 */
	//protected boolean cFlag = true;

	/**
	 * status默认是0
	 * 
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * status 默认是0
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

//	public int getInterindustry() {
//		return interindustry;
//	}
//
//	public void setInterindustry(int interindustry) {
//		this.interindustry = interindustry;
//	}

//	public String getCommand() {
//		return command;
//	}
//
//	public void setCommand(String command) {
//		this.command = command;
//	}

	/**
	 * 原子任务事件值
	 * @return
	 */
	public int getEvent() {
		return event;
	}

	/**
	 * 原子任务事件值<br>
	 * <br>
	 * 执行原子任务，发生需要切换流程的事件，<br>
	 * 根据设置的事件值，转向指定流程<br>
	 * @param event
	 */
	public void setEvent(int event) {
		this.event = event;
	}

	/**
	 * 获取当前命令名称<br>
	 * 需要时,由子类重载
	 */
	public String getCmdName() {
		return super.getLastCmd();
	}

	/**
	 * 获取当前命令正确响应码<br>
	 * 需要时,由子类重载
	 */
	public String getCmdResCode() {
		return super.getLastCmdCode();
	}


	/**
	 * 执行方法
	 * 
	 * @throws TaskException
	 * @throws InvalidParameterException
	 * @throws SecurityException 
	 */
	public abstract void run() throws TaskException, InvalidParameterException, SecurityException;

	/**
	 * 任务运行后处理<br>
	 * 
	 * 需要时,子类可重载此方法
	 */
	public void afterRun() {
		this.setLastCmd(this.getCmdName(), this.getCmdResCode());
	}

//	public String getCLA() {
//		return GPMethods.getCLA(this.interindustry, this.command, 0/* this.channel */);
//	}



	
	

	
	
	
	
	private static final String CAPDU_REDIS = "CAPDU_REDIS";

	protected boolean deleteCapdu(String sessionId) {
		String key = sessionId + CAPDU_REDIS;
		CacheUtil.remove(key);
		return true;
	}

	protected void cacheCapdu(String sessionId, List<Capdu> capdus) {
		String key = sessionId + CAPDU_REDIS;
		
		CacheUtil.put(key, capdus);
	}

	
	@SuppressWarnings("unchecked")
	protected List<Capdu> getCapdu(String sessionId) {
		String key = sessionId + CAPDU_REDIS;
		Object result = CacheUtil.get(key, Object.class);
		if(null == result){
			return null;
		}else{
			return (List<Capdu>) result;
		}
	}
	
}
