package com.whty.efs.webservice.interceptor;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.util.StringUtils;
/**
 * http://blog.csdn.net/yczz/article/details/16809859
 * @author Administrator
 *
 */
public class ESOutInterceptor extends AbstractPhaseInterceptor<Message>{ 
    private static final Logger log = LoggerFactory.getLogger(ESOutInterceptor.class); 
 
    public ESOutInterceptor() { 
        //这儿使用pre_stream，意思为在流关闭之前 
        super(Phase.PRE_STREAM); 
    } 
 
    public void handleMessage(Message message) { 
 
        try { 
 
            OutputStream os = message.getContent(OutputStream.class); 
 
            CachedStream cs = new CachedStream(); 
 
            message.setContent(OutputStream.class, cs); 
 
            message.getInterceptorChain().doIntercept(message); 
 
            CachedOutputStream csnew = (CachedOutputStream) message.getContent(OutputStream.class); 
            InputStream in = csnew.getInputStream(); 
             
            String xml = IOUtils.toString(in); 
            xml = StringUtils.replace(xml, "ns2:", "ns:").replace("env:Header", "soap:Header")
            		.replace("xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\"", "")
            		.replace("xmlns:ns2=\"http://namespaces.gsma.org/esim-messaging/3\"", "")
            		.replace("xmlns:ns3=\"http://www.w3.org/2000/09/xmldsig#\"", "");
            //这里对xml做处理，处理完后同理，写回流中 
            IOUtils.copy(new ByteArrayInputStream(xml.getBytes()), os); 
             
            cs.close(); 
            os.flush(); 
 
            message.setContent(OutputStream.class, os); 
 
 
        } catch (Exception e) { 
            log.error("Error when split original inputStream. CausedBy : " + "\n" + e); 
        } 
    } 
 
    private class CachedStream extends CachedOutputStream { 
 
        public CachedStream() { 
 
            super(); 
 
        } 
 
        protected void doFlush() throws IOException { 
 
            currentStream.flush(); 
 
        } 
 
        protected void doClose() throws IOException { 
 
        } 
 
        protected void onWrite() throws IOException { 
 
        } 
 
    } 
 

}
