package com.whty.efs.euicc.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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
import com.whty.efs.packets.message.EuiccMsg;
import com.whty.efs.packets.message.MsgHeader;
import com.whty.efs.packets.message.MsgSend;
import com.whty.efs.packets.message.request.ReceiveNotificationBySmsReqBody;


/**
 * 短信发送和收取
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/smsHw")
public class HuaWeiSmsController {
	private Logger logger = LoggerFactory.getLogger(HuaWeiSmsController.class);
	/**
	 * 调用短信网关服务发送短信
	 * @param report 是否需要状态报告 0：不需要 1:需要
	 * @param src 短信接入号
	 * @param dest 手机号码
	 * @param msg 短信内容
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/sendSms")
	public String sendSms(@RequestParam(required = false,defaultValue = "1") String report,
			@RequestParam(required = false,defaultValue = "10659818017705") String src,
			@RequestParam(required = true) String dest,
			@RequestParam(required = true) String msg) throws IOException{
		RespMessage respMessage = new RespMessage(ErrorCode.SUCCESS,"success");
		logger.info("report:{},src:{},dest:{},msg:{}",report,src,dest,msg);
		try {
			String send_sms_ip = SpringPropertyPlaceholderConfigurer.getStringProperty("HW_SEND_SMS_IP");
			String send_sms_port = SpringPropertyPlaceholderConfigurer.getStringProperty("HW_SEND_SMS_PORT");
			Socket socket = new Socket(send_sms_ip, Integer.parseInt(send_sms_port));
			MsgSend msgSend = new MsgSend();
			msgSend.setReport(report);
			msgSend.setSrc(src);
			msgSend.setDest(dest);
			msgSend.setMsg(msg);
			Gson gson = new Gson();
			String submit = gson.toJson(msgSend);
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(submit);
        	
        	DataInputStream input = new DataInputStream(socket.getInputStream());
        	String resp = input.readUTF();
			logger.info("resp:{}",resp);
			out.close();
			input.close();
			socket.close();
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
	 * @param src
	 * @param msg
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/receiveSms")
	public String receiveSms(
			String src,
			String msg) throws IOException{
		RespMessage respMessage = new RespMessage(ErrorCode.SUCCESS,"success");
		logger.info("src:{},msg:{}",src,msg);

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new Gson().toJson(respMessage);
	}

}
