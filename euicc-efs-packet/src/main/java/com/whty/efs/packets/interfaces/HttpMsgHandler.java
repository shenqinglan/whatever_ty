package com.whty.efs.packets.interfaces;

public interface HttpMsgHandler {
	public byte[] handler(byte[] data) throws Exception;
}
