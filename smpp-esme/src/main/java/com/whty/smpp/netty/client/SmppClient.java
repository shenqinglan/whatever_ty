package com.whty.smpp.netty.client;

import com.whty.smpp.netty.handler.ISmppSessionHandler;
import com.whty.smpp.netty.session.ISmppSession;
import com.whty.smpp.netty.session.SmppSessionConfiguration;

/**
 * Interface representing an SmppClient.
 * 
 * @author joelauer (twitter: @jjlauer or <a href="http://twitter.com/jjlauer" target=window>http://twitter.com/jjlauer</a>)
 */
public interface SmppClient {

    public ISmppSession bind(SmppSessionConfiguration config, ISmppSessionHandler sessionHandler) ;

    
    public void destroy();

}
