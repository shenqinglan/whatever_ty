package com.whty.smpp.pdu;
/**
 * @ClassName BindReceiver
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public interface Marshaller {

/*
 * All response PDUs must implement this interface such that they can produce
 * a properly formatted byte array corresponding to their attribute values and the
 * SMPP format for that PDU
 */
	public byte [] marshall() throws Exception;

}
