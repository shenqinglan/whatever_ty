package com.whty.efs.plugins.tycard.service.impl;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.whty.efs.common.exception.InvalidParameterException;
import com.whty.efs.common.spring.SpringPropertyPlaceholderConfigurer;
import com.whty.efs.common.util.StringUtils;
import com.whty.efs.packets.interfaces.PersonialMsgHandler;
import com.whty.efs.packets.message.EuiccEntity;
import com.whty.efs.packets.message.Header;
import com.whty.efs.packets.message.request.AppPersonialBody;
import com.whty.efs.packets.message.request.Rapdu;
import com.whty.efs.packets.message.request.RequestMsgBody;
import com.whty.efs.packets.message.response.AppPersonialResp;
import com.whty.efs.packets.message.response.Capdu;
import com.whty.efs.packets.message.response.ResponseMsgBody;
import com.whty.efs.plugins.tycard.constant.Constant;
import com.whty.efs.plugins.tycard.exception.TaskException;
import com.whty.efs.plugins.tycard.message.request.PersonalSendMsg;
import com.whty.efs.plugins.tycard.message.response.PersonalRevMsg;
import com.whty.efs.plugins.tycard.util.PersonSocketUtil;
import com.whty.efs.plugins.tycard.util.TaskAssert;

@Service("tycard_personal")
public class PersonalServiceImpl implements PersonialMsgHandler{
	
	private static final Logger logger = LoggerFactory.getLogger(PersonalServiceImpl.class);
	private static List<String> statusArrayList=new ArrayList<String>();
	
	@SuppressWarnings("unchecked")
	public EuiccEntity<ResponseMsgBody> appPersonial(
			EuiccEntity<RequestMsgBody> reqEntity) throws Exception {
		AppPersonialBody body = (AppPersonialBody)reqEntity.getBody();
		Header header = reqEntity.getHeader();
		AppPersonialResp  respBody=null;
		if (CollectionUtils.isEmpty(body.getrApdu())){
			respBody=startAppPersonalizeRequest(body);
		} else{
			respBody=appPersonalizeHandle(body);
		}
		@SuppressWarnings("rawtypes")
		EuiccEntity appPersonialResp = new EuiccEntity<AppPersonialResp>(header, respBody);
		return appPersonialResp;
	}
	
	/**
	 * 个人化开始
	 * @param body
	 * @throws InvalidParameterException
	 * @throws TaskException 
	 */
	private AppPersonialResp startAppPersonalizeRequest(AppPersonialBody body) throws  InvalidParameterException, TaskException{
		AppPersonialResp respBody = new AppPersonialResp();
		try {			
			TaskAssert.isTrue(body!=null, "已安装应用列表中找不到此应用");
			String sd_aid=SpringPropertyPlaceholderConfigurer.getStringProperty("sd_aid");
			PersonalSendMsg psg = new PersonalSendMsg(Constant.CmdType.ONE, body.getCardNO(),sd_aid, body.getAppAID(),body.getAppAID());
			putToken(psg);
			PersonSocketUtil personSocketUtil = new PersonSocketUtil();
			String rMsg = personSocketUtil.run(PersonSocketUtil.convertSendMsg(psg));
			TaskAssert.isTrue(StringUtils.isNotNull(rMsg), "连接个人化系统出错");
			PersonalRevMsg pm = analysis(rMsg);
			String cmdType = pm.getCmdType();
			if ("80001002".equals(cmdType)) {
				logger.debug("正常返回apdu指令"+pm.getCapdu());
				// 截取APDU指令
				List<String> capdus = analysisApdus(pm.getCapdu());
				TaskAssert.isTrue(!CollectionUtils.isEmpty(capdus), "个人化中apdu指令解析失败");
				List<Capdu> cApduList = new ArrayList<Capdu>();
				Capdu capdu = new Capdu();
//				capdu.setApdu("FFEEDDCC");
//				cApduList.add(capdu);
				for(String apdu:capdus){
					capdu = new Capdu();
					capdu.setApdu(apdu);
					cApduList.add(capdu);
				}
				respBody.setcApdu(cApduList);
				// 继续个人化
			} else {
				throw new TaskException("个人化异常结束");
			}
		} catch (Exception e) {
			throw new TaskException(e);
		}
		return respBody;
		
	}
	
	private AppPersonialResp appPersonalizeHandle(AppPersonialBody body) throws  Exception{
		AppPersonialResp respBody = new AppPersonialResp();
		logger.debug("个人化指令处理");
		//判断状态码
		List<String> apduStatusList= statusArrayList;
		int index = 0;
		List<Rapdu> rapdus=body.getrApdu();
		if (!CollectionUtils.isEmpty(rapdus)){
			String rapdu = null;
			for(Rapdu response:rapdus){
				rapdu = response.getApdu();
				if(rapdu==null||"".equals(rapdu.trim())||rapdu.length()<4){
					throw new TaskException("个人化操作异常");
				}else{
					String apduStatus=rapdu.substring(rapdu.length()-4);
					if(CollectionUtils.isEmpty(apduStatusList)||apduStatusList.get(index)==null){
						throw new TaskException("个人化操作异常");
					}
					if(apduStatusList.get(index).toUpperCase().indexOf(apduStatus.toUpperCase())<0){
						logger.debug("appPersonalizeHandle failure====="+apduStatusList.get(index).toUpperCase()+","+apduStatus.toUpperCase());
						throw new TaskException("个人化操作异常");
					}else{
						index++;
					}
				}
			}
			//拼接个人化请求报文
			try {
				PersonSocketUtil personSocketUtil = new PersonSocketUtil();
				String apduLast = rapdus.get(rapdus.size() - 1).getApdu();
				PersonalSendMsg psg = new PersonalSendMsg(Constant.CmdType.TWO, body.getAppAID(), body.getCardNO(), apduLast);
				//
				String rMsg = personSocketUtil.run(PersonSocketUtil.convertSendMsg(psg));
				TaskAssert.isTrue(StringUtils.isNotNull(rMsg), "连接个人化系统出错");
				PersonalRevMsg pm = analysis(rMsg);
				String cmdType = pm.getCmdType();
				if ("80001003".equals(cmdType)){
					String endStatus = pm.getStatus();      //获取个人化结束的状态码
			    	logger.debug("个人化结束，状态码："+endStatus);
			    	if("00000000".equals(endStatus)){
			    		logger.debug("个人化正常结束");
			    		//
			    		//endAppPersonalization(cardAppInfo);  		
			    		PersonalSendMsg psgEnd = new PersonalSendMsg(Constant.CmdType.THREE, body.getAppAID(), body.getCardNO());
			    		personSocketUtil.runSuccess(PersonSocketUtil.convertSendMsg(psgEnd));
			    		
			    	}
				} else if ("80001002".equals(cmdType)){
					logger.debug("个人化尚未结束，继续个人化操作,正常返回apdu指令"+pm.getCapdu());
					String capdu = pm.getCapdu(); // 截取APDU指令
					List<String> capduList= analysisApdus(capdu);
					TaskAssert.isTrue(!CollectionUtils.isEmpty(capduList), "个人化中apdu指令解析失败");
					List<Capdu> cApduList = new ArrayList<Capdu>();
					for(String apdu:capduList){
						logger.debug("个人化返回apdu:  "+apdu);
						//addApdu(apdu1,false);
						Capdu capduOne = new Capdu();
						capduOne.setApdu(apdu);
						cApduList.add(capduOne);
					}
					respBody.setcApdu(cApduList);
				} else{
					throw new TaskException("个人化异常结束");
				}
			} catch (IOException e) {
				throw new TaskException(e);
			}
			
		}
		return respBody;
	}
	
	/**
     * 解析个人化接收报文
     * @param rMsg
     * @return
     */
    public static PersonalRevMsg analysis(String rMsg){
    	PersonalRevMsg pm = new PersonalRevMsg();
    	int ICardLength = 0;
    	int IApduLength = 0;
    	if(rMsg!=null && !"".equals(rMsg)){
    		if(rMsg.length()>=8){
    			String msgLength = rMsg.substring(0, 8);
    			pm.setMsgLength(msgLength);
    			rMsg = new String(rMsg.substring(8));
    		}
    		if(rMsg.length()>=8){
    			pm.setCmdType(rMsg.substring(0, 8));
    			rMsg = new String(rMsg.substring(8));
    		}
    		if(rMsg.length()>=32){
    			pm.setRemark(rMsg.substring(0, 32));
    			rMsg = new String(rMsg.substring(32));
    		}
    		if(rMsg.length()>=8){
    			String cardLength = rMsg.substring(0, 8);
    			ICardLength = Integer.parseInt(cardLength, 16) * 2;
    			pm.setCardLength(cardLength);
    			rMsg = new String(rMsg.substring(8));
    		}
    		if((rMsg.length())>=ICardLength){
    			pm.setCardNumber(rMsg.substring(0, ICardLength));
    			rMsg = new String(rMsg.substring(ICardLength));
    		}
    		if("80001002".equals(pm.getCmdType())){
        		if(rMsg.length()>=8){
        			String apduLength = new String(rMsg.substring(0, 8));
        			IApduLength = Integer.parseInt(apduLength, 16) * 2;
        			pm.setApduLength(apduLength);
        			rMsg = new String(rMsg.substring(8));
        		}
        		if(rMsg.length()>0){
        			pm.setCapdu(new String(rMsg.substring(0, IApduLength)));
        		}
    		}else if("80001003".equals(pm.getCmdType())){
    			if(rMsg.length()>=16){
    				pm.setStatus(new String(rMsg.substring(8, 16)));
    			}
    		}

    	}
    	return pm;
    }
    
    private void putToken(PersonalSendMsg psg){
		// 获取应用版本权限
    	psg.setTokenFlag("01");// 00:不需要Token 01:需要Token
    	psg.setTokenAlg("4");
	}
    
    private  List<String> analysisApdus(String capdus){
		//定义返回
    	//List<List<String>>[] listArr = new ArrayList[2];
    	//定义返回的APDU指令
    	List<String> apduList = new ArrayList<String>();
    	//定义返回的指令响应码
    	List<String> statusList = new ArrayList<String>();
    	boolean flag = true;
    	while(flag){
    		if(capdus != null && !"".equals(capdus)){
    			//APDU指令长度
    			int apduLength = Integer.valueOf(capdus.substring(0, 4), 16);
    			if(apduLength > 0){
    				//APDU指令
    				String apdu = capdus.substring(4, (apduLength * 2) + 4);
//    				logger.debug("apdu===" + apdu);
    				apduList.add(apdu);
        			capdus = capdus.substring((apduLength * 2) + 4);
        			//指令响应码长度
        			int statusLength = Integer.valueOf(capdus.substring(0, 4), 16);
        			if(statusLength > 0){
        				//指令响应码
        				String status = capdus.substring(4, (statusLength * 2) + 4);
            			capdus = capdus.substring((statusLength * 2) + 4);
//            			logger.debug("status===" + status);
            			statusList.add(status);
        			}
    			}
        	}else{
        		flag = false;
        	}
    	}
    	//设置返回[APDU指令, 指令响应码]
    	//setDataList(statusList);
    	statusArrayList=statusList;
    	return apduList;
    }

}
