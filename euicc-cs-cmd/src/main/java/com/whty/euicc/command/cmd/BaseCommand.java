package com.whty.euicc.command.cmd;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.whty.euicc.command.dto.BusinessVo;
import com.whty.euicc.common.exception.InvalidParameterException;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.response.Capdu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class BaseCommand<Request,Response> {
	private static final Logger logger = LoggerFactory.getLogger(BaseCommand.class);
	
	private static Gson GSON = new GsonBuilder().create() ;

	/*==属性区域==================================*/
	
	/**
	 * 请求报文头
	 */
	private MsgHeader msgHeader = new MsgHeader();
	/**
	 * 请求报文体
	 */
	private Request requestBody;

	public BaseCommand(MsgHeader msgHeader,Request requestBody){
		this.msgHeader = msgHeader;
		this.requestBody = requestBody;
	}

	/**
	 * 加载上一环节的中间数据到cmd中
	 * 
	 * @param businessVo
	 */
	public void loadBizData(BusinessVo businessVo) {
		byte[] params = businessVo.getParam();
		if (null != params && params.length > 0) {
			setContext(GSON.fromJson(new String(params), EuiccContextData.class));
		}
		logger.debug("设置交易参考号：{}",businessVo.getSessionId());
		// 设置交易参考号
		getMsgHeader().setTradeRefNO(businessVo.getSessionId());
	}	
	
	/**
	 * 响应报文体
	 */
	private Response responseBody;
	
	/**
	 * 上下文数据（多个任务之间传递的数据）
	 */
	private EuiccContextData context = new EuiccContextData();
	
	public EuiccContextData getContext() {
		return context;
	}
	public void setContext(EuiccContextData context){
		this.context = context;
	}
	/**
	 * 过程数据，不用保存、不用持久化
	 */
	private ProcessData processData = new ProcessData();
	

	/**
	 * <全局变量>
	 * 卡空间类型标识
	 */
	private String cardSpaceTag;
	

	public String getCardSpaceTag() {
		return cardSpaceTag;
	}

	public void setCardSpaceTag(String cardSpaceTag) {
		this.cardSpaceTag = cardSpaceTag;
	}

	/*==方法区域==================================*/
	/**
	 * 获取报文消息头
	 * 
	 * @return
	 */
	public MsgHeader getMsgHeader() {
		return msgHeader ;
	}
	
	/**
	 * 获取请求消息体
	 *
	 * @return
	 *
	 * @author baojw@whty.com.cn
	 * @date 2014年10月11日 下午4:12:49
	 */
	public Request getRequestBody(){
		return requestBody;
	}
	
	/**
	 * 构造响应对象<抽象方法>
	 * @return
	 */
	public abstract Response buildResponseBody() ;

	/**
	 * 校验请求消息的有效性<抽象方法>
	 * 
	 * @throws InvalidParameterException
	 */
	public abstract boolean checkRequestMsg() throws InvalidParameterException;
	
	
	

	
	/**
	 * 获取响应消息体
	 * @return
	 * 
	 * @author baojw@whty.com.cn
	 * @date 2014年10月11日 下午4:12:49
	 */	
	public Response getResponseBody(){
		if(null == responseBody){
			this.responseBody = buildResponseBody();
		}
		return responseBody;
	}

	/**
	 * 添加 CAPDU 到 过程数据池中
	 * @param capdu
	 */
	public void addCapdu(Capdu capdu){
		this.processData.addCapdu(capdu);

	}
	
	/**
	 * 获取所有CAPDU
	 * @return
	 */
	public List<Capdu> getCapduList() {
		return this.processData.getCapdus();
	}	
	
	/**
	 * 清除过程数据
	 *
	 * @author baojw@whty.com.cn
	 * @date 2014年10月14日 下午7:03:33
	 */
	public void clear(){
		this.context.clear();
		this.processData.clear();
	}
	
	/**
	 * 
	 */
	public void clearProcessData(){
		this.processData.clear();
	}
	
	/**
	 * 销毁过程数据
	 */
	public void destroy(){
		this.context.destory();
		this.processData.destroy();
	}
	
	/**
	 * 将数据渲染输出为响应对象
	 */
	public Response render(){
		return this.getResponseBody();
	}
}