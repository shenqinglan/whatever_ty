/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2017-1-20
 * Id: SessionSmppHandlerImpl.java,v 1.0 2017-1-20 上午10:54:50 Administrator
 */
package com.whty.smpp.netty.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.framework.http.HTTPWeb;
import com.whty.framework.http.HttpUtil;
import com.whty.framework.spring.SpringPropertyPlaceholderConfigurer;
import com.whty.framework.utils.StringUtil;
import com.whty.smpp.netty.constants.SmppConstants;
import com.whty.smpp.netty.pdu.DataSm;
import com.whty.smpp.netty.pdu.DeliverSm;
import com.whty.smpp.netty.pdu.PduRequest;
import com.whty.smpp.netty.pdu.PduResponse;

/**
 * @ClassName SessionSmppHandlerImpl
 * @author Administrator
 * @date 2017-1-20 上午10:54:50
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class SessionSmppHandlerImpl implements ISmppSessionHandler {
	
	private static Logger logger = LoggerFactory.getLogger(SessionSmppHandlerImpl.class);

	/** 
	 * @author Administrator
	 * @date 2017-1-20
	 * @param pduRequest
	 * @return
	 * @Description TODO(ESME接受到deliver消息后的处理)
	 * @see com.whty.smpp.netty.handler.ISmppSessionHandler#firePduRequestReceived(com.whty.smpp.netty.pdu.PduRequest)
	 */
	@Override
	public PduResponse firePduRequestReceived(PduRequest pduRequest) {
		PduResponse response = pduRequest.createResponse();
		logger.info("channel get request from smsc. pduRequest >>> {}", pduRequest);
        if (pduRequest.getCommandId() == SmppConstants.CMD_ID_DELIVER_SM) {
    		 final DeliverSm mo = (DeliverSm) pduRequest;
             System.out.println("receive deliver_sm");
             String receiveSmsUrl = SpringPropertyPlaceholderConfigurer.getStringProperty("receive_sms_url");
     		Map<String, String> argsMap = new HashMap<String, String>() {{ 
     		    put( "src" , mo.getSourceAddress().getAddress()); 
     		    put("msg", StringUtil.bytes2HexString(mo.getShortMessage()));
     		}}; 
     		String resp = HttpUtil.post(receiveSmsUrl, argsMap);
     		System.out.println(resp);
    		
        } else if (pduRequest.getCommandId() == SmppConstants.CMD_ID_DATA_SM) {
        	final DataSm mo = (DataSm) pduRequest;
            System.out.println("receive data_sm");
            //String receiveSmsUrl = SpringPropertyPlaceholderConfigurer.getStringProperty("receive_smsNetty_url");
            String receiveSmsUrl = "http://www.sohu.com";
    		Map<String, String> argsMap = new HashMap<String, String>() {{ 
    		    put( "src" , mo.getSourceAddress().getAddress()); 
    		    put( "msg" , new String(mo.getShortMessage()));
    		}}; 
    		String resp = HTTPWeb.post(receiveSmsUrl, argsMap);
    		System.out.println(resp);
        }

        return response;
	}
}
