/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2017-1-20
 * Id: PduAsyncResponseImpl.java,v 1.0 2017-1-20 上午10:19:33 Administrator
 */
package com.whty.smpp.netty.handler;

import com.cloudhopper.commons.util.HexUtil;
import com.cloudhopper.commons.util.windowing.WindowFuture;
import com.whty.smpp.netty.pdu.PduRequest;
import com.whty.smpp.netty.pdu.PduResponse;

/**
 * @ClassName PduAsyncResponseImpl
 * @author Administrator
 * @date 2017-1-20 上午10:19:33
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class PduAsyncResponseImpl implements IPduAsyncResponse {
	
	private final WindowFuture<Integer,PduRequest,PduResponse> future;

	/**
	 * @param future
	 */
	public PduAsyncResponseImpl(
			WindowFuture<Integer, PduRequest, PduResponse> future) {
		super();
		this.future = future;
	}

	/** 
	 * @author Administrator
	 * @date 2017-1-20
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 * @see com.whty.smpp.netty.handler.IPduAsyncResponse#getRequest()
	 */
	@Override
	public PduRequest getRequest() {
		return future.getRequest();
	}

	/** 
	 * @author Administrator
	 * @date 2017-1-20
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 * @see com.whty.smpp.netty.handler.IPduAsyncResponse#getResponse()
	 */
	@Override
	public PduResponse getResponse() {
		return future.getResponse();
	}

	/** 
	 * @author Administrator
	 * @date 2017-1-20
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 * @see com.whty.smpp.netty.handler.IPduAsyncResponse#getWindowSize()
	 */
	@Override
	public int getWindowSize() {
		return future.getWindowSize();
	}

	/** 
	 * @author Administrator
	 * @date 2017-1-20
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 * @see com.whty.smpp.netty.handler.IPduAsyncResponse#getWindowWaitTime()
	 */
	@Override
	public long getWindowWaitTime() {
		return future.getOfferToAcceptTime();
	}

	/** 
	 * @author Administrator
	 * @date 2017-1-20
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 * @see com.whty.smpp.netty.handler.IPduAsyncResponse#getResponseTime()
	 */
	@Override
	public long getResponseTime() {
		return future.getAcceptToDoneTime();
	}

	/** 
	 * @author Administrator
	 * @date 2017-1-20
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 * @see com.whty.smpp.netty.handler.IPduAsyncResponse#getEstimatedProcessingTime()
	 */
	@Override
	public long getEstimatedProcessingTime() {
		long responseTime = getResponseTime();
        if (responseTime == 0 || future.getWindowSize() == 0) {
            return 0;
        }
        return (responseTime / future.getWindowSize());
	}
	
	@Override
    public String toString() {
        StringBuilder buf = new StringBuilder(100);
        buf.append("smpp_async_resp: seqNum [0x");
        buf.append(HexUtil.toHexString(this.future.getKey()));
        buf.append("] windowSize [");
        buf.append(getWindowSize());
        buf.append("] windowWaitTime [");
        buf.append(getWindowWaitTime());
        buf.append(" ms] responseTime [");
        buf.append(getResponseTime());
        buf.append(" ms] estProcessingTime [");
        buf.append(getEstimatedProcessingTime());
        buf.append(" ms] reqType [");
        buf.append(getRequest().getName());
        buf.append("] respType [");
        buf.append(getResponse().getName());
        buf.append("]");
        return buf.toString();
    }
}
