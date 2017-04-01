package com.whty.euicc.base.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public abstract class BaseController {
	
	private static final Logger logger  = LoggerFactory.getLogger(BaseController.class);
	 
	 
	public void writeJSONResult(Object result, HttpServletResponse response) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(result));
		} catch (IOException e) {
			logger.error("response回写失败", e);
		}
	}
	
	public void writeJSONResult(Object result, HttpServletResponse response, String datePattern) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			Gson gson = new GsonBuilder().setDateFormat(datePattern).create();
			response.getWriter().write(gson.toJson(result));
		} catch (IOException e) {
			logger.error("response回写失败", e);
		}
	}
	
	public void writeJSONArrayResult(List result, HttpServletResponse response) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("appliaction/json");
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(result));
		} catch (IOException e) {
			logger.error("response回写失败", e);
		}
	}
	
	public void writeJSONArrayResult(String result, HttpServletResponse response) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("appliaction/json");
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(result));
		} catch (IOException e) {
			logger.error("response回写失败", e);
		}
	}
}
