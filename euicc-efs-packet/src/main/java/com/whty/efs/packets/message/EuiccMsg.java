package com.whty.efs.packets.message;
import com.whty.efs.common.util.Converts;
import com.whty.efs.packets.constant.Constant;
import com.whty.efs.packets.message.response.BaseRespBody;
import com.whty.efs.packets.message.response.ResponseMsgBody;
import com.whty.efs.packets.message.response.RspnMsg;


/**
 * 请求报文
 * 
 * @author gaofeng
 * 
 */
public class EuiccMsg<B extends MsgBody> {

	public EuiccMsg() {
	}

	/**
	 * 将TsmMsg转成字节数组
	 * @return
	 */
	public byte[] toBytes(){
		return Converts.bytesToHex(Constant.gson.toJson(this, EuiccMsg.class).getBytes(Constant.UTF8)).getBytes();
	}
	
	public EuiccMsg(MsgHeader header, B body) {
		this.header = header;
		this.body = body;
	}
	
	
	/** 报文头 */
	private MsgHeader header;
	/** 报文体 */
	private B body;

	public MsgHeader getHeader() {
		return header;
	}

	public void setHeader(MsgHeader header) {
		this.header = header;
	}

	public B getBody() {
		return body;
	}

	public void setBody(B body) {
		this.body = body;
	}
	
	
	/**
	 * 输出逐层异常信息
	 * 
	 * @param e
	 * @return
	 */
	private static StringBuilder printStackTrace(Throwable e, StringBuilder sb) {

		synchronized (e) {
			sb.append(e.getMessage());
			sb.append("::");

			Throwable ourCause = e.getCause();
			if (ourCause != null)
				printStackTrace(ourCause, sb);
		}
		
		return sb;
	}
}
