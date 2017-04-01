package com.whty.efs.euicc.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.whty.efs.common.bean.RespMessage;
import com.whty.efs.common.exception.ErrorCode;
import com.whty.efs.common.https.BaseHttp;
import com.whty.efs.common.spring.SpringPropertyPlaceholderConfigurer;
import com.whty.efs.common.util.HttpUtil;
import com.whty.efs.packets.message.EuiccMsg;
import com.whty.efs.packets.message.MsgHeader;
import com.whty.efs.packets.message.request.ReceiveNotificationBySmsReqBody;


/**
 * 短信发送和收取
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/sms")
public class SmsController {
	private Logger logger = LoggerFactory.getLogger(SmsController.class);
	
	public final static String sendUrl =SpringPropertyPlaceholderConfigurer.getStringProperty("sendSms_url");
	public final static String receiveUrl = SpringPropertyPlaceholderConfigurer.getStringProperty("localRecv_url");
	/**
	 * 调用短信网关服务发送短信
	 * @param report 是否需要状态报告 0：不需要 1:需要
	 * @param tp_pid GSM协议类型 0:普通短信127：数据短信
	 * @param tp_udhi GSM协议类型 0:普通短信 1：数据短信
	 * @param msg_fmt 信息格式
	 * @param src 短信接入号
	 * @param dest 手机号码
	 * @param msg 短信内容
	 * @param userid 业务平台标识
	 * @param Platform 业务平台
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/sendSms")
	public String sendSms(@RequestParam(required = false,defaultValue = "1") String report,
			@RequestParam(required = false,defaultValue = "127") String tp_pid,
			@RequestParam(required = false,defaultValue = "1") String tp_udhi,
			@RequestParam(required = false,defaultValue = "246") String msg_fmt,
			@RequestParam(required = false,defaultValue = "10659818017705") String src,
			@RequestParam(required = true) String dest,
			@RequestParam(required = true) String msg,
			@RequestParam(required = false,defaultValue = "") String userid,
			@RequestParam(required = false,defaultValue = "") String Platform) throws IOException{
		RespMessage respMessage = new RespMessage(ErrorCode.SUCCESS,"success");
		logger.info("report:{},tp_pid:{},tp_udhi:{},msg_fmt:{},src:{},dest:{},msg:{},userid:{},Platform:{}",report,tp_pid,tp_udhi,msg_fmt,src,dest,msg,userid,Platform);
//		String url = "http://219.239.243.146:9000/mt";
		final String smsString = "027000004815160112120000012ED63D4AF47605E4E38E353E37C90947FD62932B26E0840F8D3C219FBC7737D741B0494608CBCD01ECD28E0E7C85E472FBBC9821B7710B3ABF520D00AA0375CB";
		Map<String, String> argsMap = new HashMap<String, String>(); 
		argsMap.put( "report" , report ); 
		argsMap.put( "tp_pid" , tp_pid ); 
		argsMap.put( "tp_udhi" , tp_udhi ); 
		argsMap.put( "msg_fmt" , msg_fmt ); 
		argsMap.put( "src" , src ); 
		argsMap.put( "dest" , dest ); 
		argsMap.put( "msg" , StringUtils.defaultString(msg, smsString)); 
		argsMap.put( "userid" , userid ); 
		argsMap.put( "Platform" , Platform ); 

		try {
			String resp = HttpUtil.post(sendUrl, argsMap);
			logger.info("resp:{}",resp);
		} catch (Exception e) {
			e.printStackTrace();
			respMessage = new RespMessage(ErrorCode.BUSI_9999,e.getMessage());
		}
		
		return new Gson().toJson(respMessage);
	}
	
	/**
	 * http://localhost:8080/euicc-efs-container/sms/receiveSms?report=0&tp_pid=127&tp_udhi=1&msg_fmt=4&src=17771816413&dest=1065981807705
	 * &msg=027000004D150200101200000100000000000045AAB54EB13D7749E1354C10890010120123412340123456789012244D01024E0200004F10A0000005591010FFFFFFFF890000130014083A45990057918503
	 * 调用短信网关服务发送短信
	 * @param report 0:上行短信，1，状态报告
	 * @param tp_pid GSM协议类型 0:普通短信127：数据短信
	 * @param tp_udhi GSM协议类型 0:普通短信 1：数据短信
	 * @param msg_fmt
	 * @param dest
	 * @param src
	 * @param msg
	 * @param reserve
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/receiveSms")
	public String receiveSms(String report,
			String tp_pid,
			String tp_udhi,
			String msg_fmt,
			String dest,
			String src,
			String msg,
			@RequestParam(required = false) String reserve) throws IOException{
		RespMessage respMessage = new RespMessage(ErrorCode.SUCCESS,"success");
		logger.info("report:{},tp_pid:{},tp_udhi:{},msg_fmt:{},src:{},dest:{},msg:{},userid:{},Platform:{}",report,tp_pid,tp_udhi,msg_fmt,src,dest,msg);
		if(StringUtils.equals("0", report)){
			/*截取短信内容中的tar值以区分是快捷认证还是esim,
			 * 在esim中，若卡片主动上行，该截取内容为spi&kic的值
			 */
			String tarValue = msg.substring(12, 18);
			
			if (StringUtils.startsWith(msg, "027100") && StringUtils.equals("CA0101", tarValue)) {//快捷认证
				logger.info("tarvalue:{}",tarValue);
				Map<String, String> argsMap = new HashMap<String, String>(); 
				argsMap.put( "src" , src ); 
				argsMap.put( "msg" , msg ); 

				try {
					 HttpUtil.post(receiveUrl, argsMap);
				} catch (Exception e) {
					e.printStackTrace();
					respMessage = new RespMessage(ErrorCode.BUSI_9999,e.getMessage());
				}    
				
			}else {//esim
				MsgHeader header = new MsgHeader("receiveNotificationBySms");
		        ReceiveNotificationBySmsReqBody requestBody = new ReceiveNotificationBySmsReqBody();
		        //requestBody.setTpud("E1354C10890010120123412340123456789012244D01024E0200004F10A0000005591010FFFFFFFF890000130014081122334455667788");
		        requestBody.setPhoneNo(src);
		        requestBody.setTpud(msg);
				EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
				String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
				byte[] msgBype;
				try {
					msgBype = BaseHttp.doPostBySr(json);
					System.out.println(new String(msgBype));
				} catch (Exception e) {
					e.printStackTrace();
					respMessage = new RespMessage(ErrorCode.BUSI_9999,e.getMessage());
				}
			}
		}
		return new Gson().toJson(respMessage);
	}

}
