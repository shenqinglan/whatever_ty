// Copyright (C) 2012 WHTY
package com.whty.euicc.base.interceptor;

import com.whty.euicc.base.service.BaseLogsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


/**
 * 验证用户登陆拦截器
 * 
 */
public class LoginInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory
			.getLogger(LoginInterceptor.class);
	
	@Autowired
	BaseLogsService baseLogsService;

	/**
	 * 验证用户登录拦截器
	 * 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
		// 如果session中没有user对象
		if (null == request.getSession().getAttribute("userName")) {
			String requestedWith = request.getHeader("x-requested-with");
			// ajax请求
			if (requestedWith != null && "XMLHttpRequest".equals(requestedWith)) {
				// response.setHeader("session-status", "timeout");

				PrintWriter wirter = response.getWriter();
				wirter.write("timeout");
				wirter.flush();
				wirter.close();

			} else {
				// 普通页面请求
				response.sendRedirect(request.getContextPath() + "/");
			}
			return false;
		}
		return true;

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object handler, Exception ex)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
			ModelAndView modelAndView) throws Exception {
	}

}
