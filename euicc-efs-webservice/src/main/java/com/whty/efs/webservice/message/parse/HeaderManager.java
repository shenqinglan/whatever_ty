package com.whty.efs.webservice.message.parse;

import com.whty.efs.packets.message.Header;

public class HeaderManager {
	private static ThreadLocal<Header> header = new ThreadLocal<Header>();
 
    public static Header getHeader() {
        return HeaderManager.header.get();
    }
 
    public static void setHeader(Header header) {
    	HeaderManager.header.set(header);
    }
}
