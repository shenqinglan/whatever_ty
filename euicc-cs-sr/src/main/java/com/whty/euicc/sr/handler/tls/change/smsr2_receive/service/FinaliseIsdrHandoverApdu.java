package com.whty.euicc.sr.handler.tls.change.smsr2_receive.service;

import org.springframework.stereotype.Service;

import com.whty.euicc.common.apdu.ToTLV;
import com.whty.euicc.common.exception.EuiccBusiException;

/**
 * sr2拼装apdu
 * @author 11
 *
 */
@Service
public class FinaliseIsdrHandoverApdu {
	
	private final String CLA="80";
	private final String INS="E4";
	private final String P1="00";
	private final String P2="00";
	private String Lc="";
	private String Data="";
	private final String Le="00";
	
	/**
	 * 拼apdu
	 * @return
	 */
	public byte[] deleteCommand(){
		Data = new StringBuilder().append("F203010103").append("F203410102").toString();
		Lc = ToTLV.toTLV(Data);
		StringBuilder builder=new StringBuilder().append(CLA).append(INS).append(P1)
				.append(P2).append(Lc).append(Le);
		String apdu = ToTLV.toTLV("22", builder.toString());
		return apdu.getBytes();
	}
	public boolean checkDeleteCommandResp(String eid,String requestStr) {
		System.out.println("checking >>> "+"OKOKOKOKOK");
		int endOfDbl0D0A=requestStr.indexOf("\r\n\r\n")+4;
		System.out.println(endOfDbl0D0A);
		String strData = requestStr.substring(endOfDbl0D0A, requestStr.length()-4);
		if(strData.indexOf("9000")==-1){
			throw new EuiccBusiException("0101","卡片返回值不为9000");
		}
		return true;
	}
	
}
