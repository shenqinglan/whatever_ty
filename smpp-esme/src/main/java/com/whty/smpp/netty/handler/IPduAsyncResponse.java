package com.whty.smpp.netty.handler;

import com.whty.smpp.netty.pdu.PduRequest;
import com.whty.smpp.netty.pdu.PduResponse;
/**
 * 
 * @ClassName IPduAsyncResponse
 * @author Administrator
 * @date 2017-3-10 下午1:38:17
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public interface IPduAsyncResponse {
	
    public PduRequest getRequest();

    public PduResponse getResponse();
    
    public int getWindowSize();

    public long getWindowWaitTime();
    
    public long getResponseTime();
    
    public long getEstimatedProcessingTime();
}
