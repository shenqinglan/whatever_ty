package com.whty.euicc.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
/**
 * IP工具类
 * @author Administrator
 *
 */
public class IpUtil {
	
	/**
	 * 
	 * @return
	 */
	public static String getLocalIp(){
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}  
	}
	
	/**
     * 获取浏览器端IP.
     * 参考：http://xuechenyoyo.iteye.com/blog/586007。
     * x-cluster-client-ip/x-forwarded-for/WL-Proxy-Client-IP/Proxy-Client-IP
     * @param request
     * @return
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-real-ip");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return org.apache.commons.lang3.StringUtils.substringBefore(ip, ",");
    }
}
