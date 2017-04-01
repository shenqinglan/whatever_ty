package com.whty.oauth.interfaces.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.whty.oauth.interfaces.util.HttpUtil;

@RestController
@RequestMapping("/euicc-efs-container")
public class SmsController {
	private Logger logger = LoggerFactory.getLogger(SmsController.class);
	
	@Value("${sendSms_url}")
	private String sendSms_url;
	
	@Value("${localRecv_url}")
	private String localRecv_url;
	
	/**
	 * 调用短信网关服务发送短信
	 * @param report 是否需要状态报告 0：不需要 1:需要
	 * @param tp_pid GSM协议类型 0:普通短信127：数据短信
	 * @param tp_udhi GSM协议类型 0:普通短信 1：数据短信
	 * @param msg_fmt 信息格式
	 * @param src 短信接入号
	 * @param dest 手机号码
	 * @param msg 短信内容
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/sms/sendSms")
	public String sendSms(@RequestParam(required = false,defaultValue = "1") String report,
			@RequestParam(required = false,defaultValue = "127") String tp_pid,
			@RequestParam(required = false,defaultValue = "1") String tp_udhi,
			@RequestParam(required = false,defaultValue = "246") String msg_fmt,
			@RequestParam(required = false,defaultValue = "10659818017705") String src,
			@RequestParam(required = true) String dest,
			@RequestParam(required = true) String msg) throws IOException{
		System.out.println("sendSms_url : "+sendSms_url);
		
		logger.info("report:{},tp_pid:{},tp_udhi:{},msg_fmt:{},src:{},dest:{},msg:{}",report,tp_pid,tp_udhi,msg_fmt,src,dest,msg);
		//String url = "http://219.239.243.146:9000/mt";
		final String smsString = "027000004815160112120000012ED63D4AF47605E4E38E353E37C90947FD62932B26E0840F8D3C219FBC7737D741B0494608CBCD01ECD28E0E7C85E472FBBC9821B7710B3ABF520D00AA0375CB";
		Map<String, String> argsMap = new HashMap<String, String>(); 
		argsMap.put( "report" , report ); 
		argsMap.put( "tp_pid" , tp_pid ); 
		argsMap.put( "tp_udhi" , tp_udhi ); 
		argsMap.put( "msg_fmt" , msg_fmt ); 
		argsMap.put( "src" , src ); 
		argsMap.put( "dest" , dest ); 
		argsMap.put( "msg" , StringUtils.defaultString(msg, smsString)); 
		
		try {
			String resp = HttpUtil.post(sendSms_url, argsMap);
			logger.info("resp:{}",resp);
		} catch (Exception e) {
			e.printStackTrace();
			return "9999";
		}
		
		return "0000";
	}
	
	/**
	 * http://localhost:8080/oauth-interface/sms/receiveSms?report=0&tp_pid=127&tp_udhi=1&msg_fmt=4&src=17771816413&dest=1065981807705
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
	@RequestMapping(value="/sms/receiveSms")
	public String receiveSms(@RequestParam(required = false,defaultValue = "0") String report,
			@RequestParam(required = false,defaultValue = "127") String tp_pid,
			@RequestParam(required = false,defaultValue = "1") String tp_udhi,
			@RequestParam(required = false,defaultValue = "4") String msg_fmt,
			@RequestParam(required = false,defaultValue = "10659818017705") String dest,
			String src,
			String msg,
			@RequestParam(required = false) String reserve) throws IOException{
		logger.info("dest:{},msg:{}",dest,msg);
		
		if(StringUtils.equals("0", report)){	
			Map<String, String> argsMap = new HashMap<String, String>(); 
			argsMap.put( "src" , src ); 
			argsMap.put( "msg" , msg ); 

			try {
				 HttpUtil.post(localRecv_url, argsMap);
			} catch (Exception e) {
				e.printStackTrace();
				return "9999";
			}          
		}
		
		return "0000";
	}
}
