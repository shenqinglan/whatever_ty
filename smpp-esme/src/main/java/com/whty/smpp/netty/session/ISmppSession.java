package com.whty.smpp.netty.session;

import com.whty.framework.netty.NettySession;
import com.whty.smpp.netty.handler.ISmppSessionListener;
import com.whty.smpp.netty.pdu.BaseBind;
import com.whty.smpp.netty.pdu.BaseBindResp;
import com.whty.smpp.netty.pdu.EnquireLink;
import com.whty.smpp.netty.pdu.EnquireLinkResp;
import com.whty.smpp.netty.pdu.SubmitSm;
import com.whty.smpp.netty.pdu.SubmitSmResp;
/**
 * @ClassName ISmppSession
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public interface ISmppSession extends NettySession{
	
	public ISmppSessionListener getSmppSessionListener();
    
    public BaseBindResp bind(BaseBind request, long timeoutInMillis);
    
    public void unbind(long timeoutMillis);
    
    public void close();
    
    public void destroy();
    
    public EnquireLinkResp enquireLink(EnquireLink request, long timeoutMillis);
    
    public SubmitSmResp submit(SubmitSm request, long timeoutMillis);
}
