package com.whty.euicc.common.utils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/** 
 * 非法字符过滤器 
 * 1.所有非法字符配置在web.xml中，如需添加新字符，请自行配置 
 * 2.请注意请求与相应时的编码格式设置，否则遇到中文时，会出现乱码(GBK与其子集应该没问题) 
 * @author dengzm
 * 
 */ 
public class CharFilter implements Filter {
	
	private static final Logger logger = LoggerFactory
			.getLogger(CharFilter.class);
	
	private String encoding;  
	private String[] illegalChars;
	@Override
	public void destroy() {
		encoding = null;  
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,  
            FilterChain filterChain) throws IOException, ServletException {  
        HttpServletRequest req = (HttpServletRequest)request;  
        HttpServletResponse res = (HttpServletResponse) response;  
        
          
        //必须手动指定编码格式  
        req.setCharacterEncoding(encoding);  
        String tempURL = req.getRequestURI();   
        logger.debug(tempURL);  
        Enumeration params = req.getParameterNames();  
          
        //是否执行过滤  true：执行过滤  false：不执行过滤  
        boolean executable = true;  
        HashMap m=new HashMap(req.getParameterMap()); 
        //对参数名与参数进行判断
        Iterator<Map.Entry> it = m.entrySet().iterator();
        while (it.hasNext()) {
        	Map.Entry entry = it.next();
        	String paraName=(String) entry.getKey();
        	//密码不过滤  
            if(paraName.toLowerCase().contains("password")){  
                executable = false;  
            }
            
            if(paraName.toLowerCase().contains("oldPass")){  
                executable = false;  
            }
            
            if(paraName.toLowerCase().contains("newPass")){  
                executable = false;  
            }
            
            if(executable){  
                String[] paramValues = req.getParameterValues(paraName);  
                for (int i = 0; i < paramValues.length; i++) {
                	paramValues[i]=filterDangerString(paramValues[i]);
                	m.put(paraName, paramValues[i]); 
                }
            }  
        }
        RequestWrapper wrapRequest=new RequestWrapper(req,m); 
        filterChain.doFilter(wrapRequest, response);  
    }  
  
	public String filterDangerString(String value) {
        if (value == null) {
            return null;
        }
        for (int i = 0; i < illegalChars.length; i++) {
        	value = value.replaceAll(illegalChars[i], "");
		}
        value = value.replaceAll("%", "\\\\%");
        return value;
    }
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		encoding = filterConfig.getInitParameter("encoding");
		illegalChars = filterConfig.getInitParameter("illegalChars").split(",");
	}
}
